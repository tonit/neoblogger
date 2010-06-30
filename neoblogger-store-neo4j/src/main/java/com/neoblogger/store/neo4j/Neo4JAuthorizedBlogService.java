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
import com.neoblogger.api.primitive.Article;
import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.primitive.Blog;
import com.neoblogger.store.neo4j.primitive.AuthorImpl;
import com.neoblogger.store.neo4j.primitive.BlogImpl;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * Created by IntelliJ IDEA.
 * User: tonit
 * Date: Jun 29, 2010
 * Time: 7:25:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Neo4JAuthorizedBlogService implements AuthorizedBlogService
{

    final private GraphDatabaseService m_graphDB;
    final private Author m_author;
    final private Node m_refBlogs;
    private PrimitiveFactory m_primitiveFactory;

    public Neo4JAuthorizedBlogService( GraphDatabaseService db, Author author, Node refBlogs )
    {
        m_graphDB = db;
        m_author = author;
        m_refBlogs = refBlogs;
        m_primitiveFactory = new DefaultPrimitiveFactory();


    }

    @Override
    public Blog createBlog( String id )
    {
        Blog blog = null;
        Transaction tx = m_graphDB.beginTx();
        try
        {
            Node node = m_graphDB.createNode();
            node.setProperty( BlogImpl.ID, id );
            m_refBlogs.createRelationshipTo( node, BloggerRelationship.MEMBER );
            blog = m_primitiveFactory.getBlog( node );
            tx.success();
        } finally
        {
            tx.finish();
        }

        return blog;
    }

    @Override
    public BlogService deleteBlog( Blog blog )
        throws NeoBloggerAuthorizationException
    {
        return null;
    }

    @Override
    public BlogService publishArticle( Blog blogTarget, Article article )
        throws NeoBloggerAuthorizationException
    {
        return null;
    }

    @Override
    public Article createArticle( String s )
    {
        return null;
    }


}
