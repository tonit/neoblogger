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
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.Position;
import org.neo4j.graphdb.traversal.PruneEvaluator;
import org.neo4j.graphdb.traversal.ReturnFilter;
import org.neo4j.kernel.TraversalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Neo4J based authorized service layer.
 */
public class Neo4JAuthorizedBlogService implements AuthorizedBlogService
{

    private static Logger LOG = LoggerFactory.getLogger( Neo4JAuthorizedBlogService.class );

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
        remove( blog );
        return this;
    }

    @Override
    public AuthorizedBlogService publishArticle( Blog blogTarget, Article article )
        throws NeoBloggerAuthorizationException
    {
        Node start = m_context.getPrimitiveFactory().get( article );
        Node end = m_context.getPrimitiveFactory().get( blogTarget );
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
        Node authorNode = m_context.getPrimitiveFactory().get( m_author );
        return new BloggerTypeAwareAdapterIterable<Position, Article>( Article.class, m_context.getPrimitiveFactory(), TraversalHelper.traverse( authorNode, TraversalHelper.directChilds( Direction.OUTGOING, BloggerRelationship.CREATED ) ) );
    }

    @Override
    public Iterable<Article> getArticles( Blog blog )
    {
        Node blogNode = m_context.getPrimitiveFactory().get( blog );
        return new BloggerTypeAwareAdapterIterable<Position, Article>( Article.class, m_context.getPrimitiveFactory(), TraversalHelper.traverse( blogNode, TraversalHelper.directChilds( Direction.INCOMING, BloggerRelationship.PUBLISHED_TO ) ) );
    }

    @Override
    public AuthorizedBlogService deleteArticle( Article article )
    {
        // delete all edges
        remove( article );
        return this;
    }

    private void remove( BloggerPrimitive article )
    {
        Transaction tx = m_context.getDatabaseService().beginTx();
        try
        {
            Node node = m_context.getPrimitiveFactory().get( article );

            for( Position p : TraversalFactory.createTraversalDescription()
                .sourceSelector( TraversalFactory.postorderBreadthFirstSelector() )
                .prune( TraversalFactory.pruneAfterDepth( 1 ) )
                .filter( ReturnFilter.ALL_BUT_START_NODE )
                .traverse( node ) )
            {
                Node n = p.node();
                for( Relationship r : n.getRelationships() )
                {
                    // TODO evaluate relationships. If there is anything we don't know of, cancel delete action.
                    r.delete();
                }

            }
            node.delete();

            tx.success();
        }
        finally
        {
            tx.finish();
        }
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

            m_context.getPrimitiveFactory().get( m_author ).createRelationshipTo( node, BloggerRelationship.CREATED );
            article = m_context.getPrimitiveFactory().get( Article.class, node );
            tx.success();
        } finally
        {
            tx.finish();
        }

        return article;
    }
}
