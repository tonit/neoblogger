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
import com.neoblogger.api.NeoBloggerAuthorizationException;
import com.neoblogger.api.primitive.Article;
import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.primitive.Blog;
import com.neoblogger.api.primitive.BloggerPrimitive;
import com.neoblogger.store.neo4j.primitive.ArticleImpl;
import com.neoblogger.store.neo4j.primitive.BlogImpl;
import com.neoblogger.store.neo4j.util.BloggerTypeAwareAdapterIterable;
import com.neoblogger.store.neo4j.util.TraversalHelper;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.Position;

/**
 * Neo4J based authorized service layer.
 */
public class Neo4JAuthorizedBlogService implements AuthorizedBlogService
{

    final private BloggerContext m_context;
    final private Author m_author;

    public Neo4JAuthorizedBlogService( BloggerContext ctx, Author author )
    {
        m_context = ctx;
        m_author = author;
    }

    @Override
    public AuthorizedBlogService deleteBlog( Blog blog )
        throws NeoBloggerAuthorizationException
    {
        // detach all relationships we know if

        // try to delete the node:

        // if this fails we know there are dependencies on this blog that we cannot judge about ( inherent safety-net )
        return this;
    }

    @Override
    public AuthorizedBlogService publishArticle( Blog blogTarget, Article article )
        throws NeoBloggerAuthorizationException
    {
        Node start = convert( article ).getNode();
        Node end = convert( blogTarget ).getNode();
        Transaction tx = m_context.getDatabaseService().beginTx();
        try
        {
            start.createRelationshipTo( end, BloggerRelationship.PUBLISHED_TO );
            tx.success();
        } finally
        {
            tx.finish();
        }

        return this;
    }

    @Override
    public Blog createBlog()
    {
        Blog blog = null;
        Transaction tx = m_context.getDatabaseService().beginTx();
        try
        {
            Node node = m_context.getDatabaseService().createNode();
            m_context.getBlogReferenceNode().createRelationshipTo( node, BloggerRelationship.BLOG );
            blog = m_context.getPrimitiveFactory().get( Blog.class, node );
            tx.success();
        } finally
        {
            tx.finish();
        }

        return blog;
    }

    @Override
    public Iterable<Article> getArticles()
    {
        Node authorNode = convert( m_author ).getNode();
        return new BloggerTypeAwareAdapterIterable<Position, Article>( Article.class, m_context.getPrimitiveFactory(), TraversalHelper.traverse( authorNode, TraversalHelper.directChilds( Direction.OUTGOING, BloggerRelationship.CREATED ) ) );
    }

    @Override
    public Iterable<Article> getArticles( Blog blog )
    {
        Node blogNode = convert( blog ).getNode();
        return new BloggerTypeAwareAdapterIterable<Position, Article>( Article.class, m_context.getPrimitiveFactory(), TraversalHelper.traverse( blogNode, TraversalHelper.directChilds( Direction.INCOMING, BloggerRelationship.PUBLISHED_TO ) ) );
    }

    @Override
    public Article createArticle()
    {
        Article article = null;
        Transaction tx = m_context.getDatabaseService().beginTx();
        try
        {
            Node node = m_context.getDatabaseService().createNode();

            m_context.getArticleReferenceNode().createRelationshipTo( node, BloggerRelationship.ARTICLE );

            convert( m_author ).getNode().createRelationshipTo( node, BloggerRelationship.CREATED );
            article = m_context.getPrimitiveFactory().get( Article.class, node );
            tx.success();
        } finally
        {
            tx.finish();
        }

        return article;
    }

    // TODO make converting BloggerPromitive back to NodePojo more transparent    

    private NodePojo convert( BloggerPrimitive p )
    {
        return ( (NodePojo) p );
    }


}
