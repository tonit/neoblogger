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

import java.util.Iterator;
import com.neoblogger.api.AuthorizedBlogService;
import com.neoblogger.api.BlogService;
import com.neoblogger.api.NeoBloggerAuthorizationException;
import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.primitive.Blog;
import com.neoblogger.store.neo4j.primitive.AuthorImpl;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.traversal.Position;
import org.neo4j.graphdb.traversal.ReturnFilter;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.TraversalFactory;

/**
 * Neo4J based BlogService
 */
public class Neo4JBlogService implements BlogService
{

    private static final String REFERENCE_NODE_AUTHORS = "authors";
    private static final Object REFERENCE_NODE_BLOGS = "blogs";

    private EmbeddedGraphDatabase m_graphDb;

    final private Node m_refAuthors;
    final private Node m_refBlogs;

    private static final String ROLE = "role";
    private PrimitiveFactory m_primitiveFactory;

    public Neo4JBlogService()
    {
        // open an instance
        m_graphDb = new EmbeddedGraphDatabase( "target/db" );
        m_primitiveFactory = new DefaultPrimitiveFactory();
        Transaction tx = m_graphDb.beginTx();
        try
        {
            m_refAuthors = m_graphDb.createNode();
            m_refAuthors.setProperty( ROLE, REFERENCE_NODE_AUTHORS );

            m_refBlogs = m_graphDb.createNode();
            m_refBlogs.setProperty( ROLE, REFERENCE_NODE_BLOGS );
            tx.success();
        } finally
        {
            tx.finish();
        }
    }

    public void close()
    {
        m_graphDb.shutdown();
    }

    @Override
    public Author createAuthor( String openID, String fullname )
        throws NeoBloggerAuthorizationException
    {
        Author author = null;
        Transaction tx = m_graphDb.beginTx();
        try
        {
            Node node = m_graphDb.createNode();
            node.setProperty( AuthorImpl.PROPERTY_OPENID, openID );
            node.setProperty( AuthorImpl.PROPERTY_FULLNAME, fullname );
            m_refAuthors.createRelationshipTo( node, BloggerRelationship.MEMBER );
            author = m_primitiveFactory.getAuthor( node );
            tx.success();
        } finally
        {
            tx.finish();
        }

        return author;
    }

    @Override
    public Iterable<Blog> blogs()
        throws NeoBloggerAuthorizationException
    {
        return new Iterable<Blog>()
        {

            @Override
            public Iterator<Blog> iterator()
            {

                return new Iterator<Blog>()
                {
                    Iterator<Position> iterator = TraversalFactory.createTraversalDescription()
                        .sourceSelector( TraversalFactory.postorderBreadthFirstSelector() )
                        .prune( TraversalFactory.pruneAfterDepth( 1 ) )
                        .filter( ReturnFilter.ALL_BUT_START_NODE )
                        .traverse( m_refBlogs ).iterator();

                    @Override
                    public boolean hasNext()
                    {

                        return iterator.hasNext();
                    }

                    @Override
                    public Blog next()
                    {
                        return m_primitiveFactory.getBlog( iterator.next().node() );
                    }

                    @Override
                    public void remove()
                    {
                        iterator.remove();
                    }
                };


            }

        };

    }

    @Override
    public AuthorizedBlogService login( Author author )
        throws NeoBloggerAuthorizationException
    {
        return new Neo4JAuthorizedBlogService( m_graphDb, author, m_refBlogs );
    }


}
