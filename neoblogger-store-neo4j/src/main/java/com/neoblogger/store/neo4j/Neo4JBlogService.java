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

import com.neoblogger.api.AuthorizedBlogService;
import com.neoblogger.api.BlogService;
import com.neoblogger.api.NeoBloggerAuthorizationException;
import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.primitive.Blog;
import com.neoblogger.store.neo4j.primitive.AuthorImpl;
import com.neoblogger.store.neo4j.util.BloggerTypeAwareAdapterIterable;
import com.neoblogger.store.neo4j.util.TraversalHelper;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.Position;

/**
 * Neo4J based BlogService
 */
public class Neo4JBlogService implements BlogService
{

    final private BloggerContext m_context;

    public Neo4JBlogService( BloggerContext ctx )
    {
        m_context = ctx;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Author registerAuthor( String openID )
        throws NeoBloggerAuthorizationException
    {
        // TODO: check if author already exists using index service.
        Author author = null;
        Transaction tx = m_context.getDatabaseService().beginTx();
        try
        {
            Node node = m_context.getDatabaseService().createNode();
            node.setProperty( AuthorImpl.PROPERTY_OPENID, openID );
            m_context.getAuthorReferenceNode().createRelationshipTo( node, BloggerRelationship.MEMBER );
            author = m_context.getPrimitiveFactory().get( Author.class, node );
            tx.success();
        } finally
        {
            tx.finish();
        }

        return author;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Author getAuthor( String openID )
        throws NeoBloggerAuthorizationException
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Blog> getBlogs()
        throws NeoBloggerAuthorizationException
    {
        return new BloggerTypeAwareAdapterIterable<Position, Blog>( Blog.class, m_context.getPrimitiveFactory(), TraversalHelper.traverse( m_context.getBlogReferenceNode(), TraversalHelper.directChilds( Direction.OUTGOING, BloggerRelationship.BLOG ) ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorizedBlogService login( Author author )
        throws NeoBloggerAuthorizationException
    {
        return new Neo4JAuthorizedBlogService( m_context, author );
    }

}
