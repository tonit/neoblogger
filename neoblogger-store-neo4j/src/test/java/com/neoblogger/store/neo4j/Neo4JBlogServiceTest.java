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

import java.util.ArrayList;
import com.neoblogger.api.AuthorizedBlogService;
import com.neoblogger.api.primitive.Author;
import org.junit.Test;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.Position;
import org.neo4j.graphdb.traversal.TraversalDescription;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 */
public class Neo4JBlogServiceTest
{

    @Test
    public void initialGetBlogs()
        throws Exception
    {
        BloggerContext ctx = createDefaultMockBloggerContext();

        Neo4JBlogService service = new Neo4JBlogService( ctx );

        Author auth = service.registerAuthor( "foo" );
        assertThat( auth, is( notNullValue() ) );

        assertThat( service.getBlogs().iterator().hasNext(), is( false ) );

        // verify that it consults the db at least
        verify( ctx, atLeast( 1 ) ).getDatabaseService();

        // and it uses the provided ref sub node.
        verify( ctx, atLeast( 1 ) ).getBlogReferenceNode();


    }

    @Test
    public void testLogin()
        throws Exception
    {
        BloggerContext ctx = createDefaultMockBloggerContext();

        Neo4JBlogService service = new Neo4JBlogService( ctx );

        AuthorizedBlogService authorized = service.login( service.registerAuthor( "foo" ) );

        assertThat( authorized, is( notNullValue() ) );

        // verify that it consults the db at least
        verify( ctx, atLeast( 1 ) ).getDatabaseService();

        // and it uses the provided ref sub node.
        verify( ctx, atLeast( 1 ) ).getAuthorReferenceNode();

    }

    private BloggerContext createDefaultMockBloggerContext()
    {
        BloggerContext ctx = mock( BloggerContext.class );

        GraphDatabaseService db = mock( GraphDatabaseService.class );
        Transaction transaction = mock( Transaction.class );

        Node node = mock( Node.class );
        // use real factory for type convertions.
        PrimitiveFactory primitiveFactory = new DefaultPrimitiveFactory( db );
        Relationship relationship = mock( Relationship.class );

        NeoBloggerTraversal traversalHelper = mock( NeoBloggerTraversal.class );
        TraversalDescription traversalDescription = mock( TraversalDescription.class );

        when( traversalHelper.directChilds( any( Direction.class ), any( BloggerRelationship.class ) ) ).thenReturn( traversalDescription );
        when( traversalHelper.traverse( any( Node.class ), any( TraversalDescription.class ) ) ).thenReturn( new ArrayList<Position>() );

        when( node.createRelationshipTo( any( Node.class ), any( RelationshipType.class ) ) ).thenReturn( relationship );

        when( db.beginTx() ).thenReturn( transaction );
        when( db.createNode() ).thenReturn( node );

        when( ctx.getDatabaseService() ).thenReturn( db );
        when( ctx.getAuthorReferenceNode() ).thenReturn( node );
        when( ctx.getBlogReferenceNode() ).thenReturn( node );
        when( ctx.getArticleReferenceNode() ).thenReturn( node );
        when( ctx.getPrimitiveFactory() ).thenReturn( primitiveFactory );

        when( ctx.getSimplifiedTraversal() ).thenReturn( traversalHelper );

        return ctx;
    }

    @Test( expected = AssertionError.class )
    public void checkNullDependency()
        throws Exception
    {
        new Neo4JBlogService( null );
    }

    @Test( expected = AssertionError.class )
    public void checkNullLogin()
        throws Exception
    {
        Neo4JBlogService service = new Neo4JBlogService( createDefaultMockBloggerContext() );
        service.login( null );
    }

    @Test( expected = AssertionError.class )
    public void checkNullAuthor()
        throws Exception
    {
        Neo4JBlogService service = new Neo4JBlogService( createDefaultMockBloggerContext() );
        service.getAuthor( null );
    }

    @Test( expected = AssertionError.class )
    public void checkNullRegister()
        throws Exception
    {
        Neo4JBlogService service = new Neo4JBlogService( createDefaultMockBloggerContext() );
        service.registerAuthor( null );
    }

}
