/*
 * Copyright (C) 2010 Okidokiteam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.neoblogger.store.neo4j;

import com.neoblogger.api.BlogService;
import com.neoblogger.api.ServiceFactory;
import com.neoblogger.store.neo4j.util.NeoBloggerTraversalImpl;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.Position;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.TraversalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.neoblogger.store.neo4j.util.TraversalHelper.*;

/**
 * NeoJ specifc, threadsafe, lazily instantiating, stateful factory with a underlying EmbeddedGraphDatabase.
 */
public class Neo4JBloggerFactory implements ServiceFactory
{

    private static Logger LOG = LoggerFactory.getLogger( Neo4JBloggerFactory.class );

    private static final String DB_FOLDER = "target/db";
    private volatile EmbeddedGraphDatabase m_graphDb;

    @Override
    public synchronized BlogService create( boolean clear )
    {
        // open an instance and put it into the correct state.
        if( m_graphDb == null )
        {

            initDB( clear );
        }
        else
        {
            // you may better keep the BlogService returned in previous calls. However.. 
            LOG.warn( "Reusing DB instance." );

        }
        return new Neo4JBlogService( createDefaultBloggerContext() );

    }

    private void initDB( boolean clear )
    {
        LOG.debug( "Creating new DB instance." );
        m_graphDb = new EmbeddedGraphDatabase( DB_FOLDER );
        if( clear )
        {
            clear();
        }

        Transaction tx = m_graphDb.beginTx();
        try
        {
            prepareReferenceNode( BloggerRelationship.AUTHORS );
            prepareReferenceNode( BloggerRelationship.BLOGS );
            prepareReferenceNode( BloggerRelationship.ARTICLES );

            tx.success();
        } finally
        {
            tx.finish();
        }
    }

    private DefaultBloggerContext createDefaultBloggerContext()
    {
        return new DefaultBloggerContext( m_graphDb, createDefaultPrimitiveFactory(), createDefaultTraversalHelper() );
    }

    private DefaultPrimitiveFactory createDefaultPrimitiveFactory()
    {
        return new DefaultPrimitiveFactory( m_graphDb );
    }

    private NeoBloggerTraversal createDefaultTraversalHelper()
    {
        return new NeoBloggerTraversalImpl();
    }

    private void prepareReferenceNode( BloggerRelationship t )
    {
        if( m_graphDb.getReferenceNode().getSingleRelationship( t, Direction.OUTGOING ) == null )
        {
            LOG.debug( "Creating Reference Node " + t.name() );
            Node authorNode = m_graphDb.createNode();
            m_graphDb.getReferenceNode().createRelationshipTo( authorNode, t );
        }
    }

    @Override
    public synchronized void shutdown()
    {
        if( m_graphDb != null )
        {
            LOG.debug( "Shutdown DB." );

            m_graphDb.shutdown();
            m_graphDb = null;
        }
    }

    private synchronized void clear()
    {
        if( m_graphDb != null )
        {
            LOG.debug( "Clearing DB." );
            // clearing will just happpend occasionally and in tests, so we expect it all fits into a single transaction.
            // then we don't have to worry too much about loose relationships while deleting.

            Transaction tx = m_graphDb.beginTx();
            try
            {
                // We just detach everything connected to the ref node. 
                Node referenceNode = m_graphDb.getReferenceNode();

                for( Position p : TraversalFactory.createTraversalDescription()
                    .sourceSelector( TraversalFactory.postorderBreadthFirstSelector() )
                    .filter( ignoreSingleNodeFilter( referenceNode ) )
                    .traverse( referenceNode ) )
                {
                    Node n = p.node();
                    for( Relationship r : n.getRelationships() )
                    {
                        LOG.debug( "Clearing Relationship: " + r.getType() + " to node " + n );

                        r.delete();
                    }
                    LOG.debug( "Clearing Node: " + n );

                    n.delete();
                }

                tx.success();
            }
            finally
            {
                tx.finish();
                LOG.debug( "End Clearing DB." );

            }

        }
    }
}
