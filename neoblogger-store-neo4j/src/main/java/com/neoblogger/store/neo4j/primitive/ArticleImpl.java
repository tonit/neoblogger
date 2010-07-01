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
package com.neoblogger.store.neo4j.primitive;

import com.neoblogger.api.primitive.Article;
import com.neoblogger.store.neo4j.NodePojo;
import com.neoblogger.store.neo4j.util.WithTransaction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

/**
 * Pojo with direct underlying node
 */
public class ArticleImpl implements Article, NodePojo
{

    private Node m_node;
    public static final String TITLE = "TITLE";
    public static final String TEXT = "TEXT";
    final private GraphDatabaseService m_service;

    public ArticleImpl( Node node, GraphDatabaseService service )
    {
        m_node = node;
        m_service = service;
    }

    @Override
    public Node getNode()
    {
        return m_node;
    }

    @Override
    public Article setTitle( String value )
    {
        WithTransaction.setProperty( m_service, m_node, TITLE, value );
        return this;
    }

    @Override
    public Article setText( String value )
    {
        WithTransaction.setProperty( m_service, m_node, TEXT, value );
        return this;
    }

    @Override
    public String getTitle()
    {
        return (String) m_node.getProperty( TITLE );
    }

    @Override
    public String getText()
    {
        return (String) m_node.getProperty( TITLE );
    }


}
