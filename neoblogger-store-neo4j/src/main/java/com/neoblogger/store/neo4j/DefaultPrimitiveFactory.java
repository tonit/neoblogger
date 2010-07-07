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

import com.neoblogger.api.primitive.Article;
import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.primitive.Blog;
import com.neoblogger.api.primitive.BloggerPrimitive;
import com.neoblogger.store.neo4j.primitive.ArticleImpl;
import com.neoblogger.store.neo4j.primitive.AuthorImpl;
import com.neoblogger.store.neo4j.primitive.BlogImpl;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * Factory for Neo4J backed primitives.
 * Is able to convert Primitives back and forth as long as they are produced by {@link Neo4JBlogService) and its products.
 *
 * This factory looks like quite common when working with neo4j as suggested by the Design Guide.
 * However implementation needs to be registry based of cause and much more elaborate.
 */
@SuppressWarnings( "unchecked" )
public class DefaultPrimitiveFactory implements PrimitiveFactory
{

    private GraphDatabaseService m_service;

    /**
     * @param dbService database service be injected into newly created primitives. This way they can participate in transaction lifecycle
     */
    public DefaultPrimitiveFactory( GraphDatabaseService dbService )
    {
        m_service = dbService;
    }

    @Override
    public <T extends BloggerPrimitive> T get( Class<T> t, Node node )
    {
        // flat type mapping, play the poor man's game. No service dynamics here.
        if( t == Author.class )
        {
            return (T) new AuthorImpl( node, m_service );
        }
        else if( t == Blog.class )
        {
            return (T) new BlogImpl( node, m_service );
        }
        else if( t == Article.class )
        {
            return (T) new ArticleImpl( node, m_service );
        }
        else
        {
            throw new IllegalArgumentException( "Type " + t + " not supported by this Factory." );
        }
    }

    @Override
    public Node get( BloggerPrimitive primitive )
    {
        // assume those are valid NodePojo primitves.
        return ( (NodePojo) primitive ).getNode();
    }
}
