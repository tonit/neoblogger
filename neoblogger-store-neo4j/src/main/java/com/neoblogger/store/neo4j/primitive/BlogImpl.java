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

import com.neoblogger.api.primitive.Blog;
import com.neoblogger.store.neo4j.NodePojo;
import com.neoblogger.store.neo4j.util.WithTransaction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class BlogImpl implements Blog, NodePojo
{

    private static Logger LOG = LoggerFactory.getLogger( BlogImpl.class );
    public static final String TITLE = "TITLE";

    private Node m_node;
    private GraphDatabaseService m_service;

    public BlogImpl( Node node, GraphDatabaseService service )
    {
        LOG.debug( "new Blog from node " + node );
        m_node = node;
        m_service = service;
    }

    public String toString()
    {
        return "[ BLOG node='" + m_node + "' ]";
    }

    @Override
    public Node getNode()
    {
        return m_node;
    }

    @Override
    public Blog setTitle( String value )
    {
        WithTransaction.setProperty( m_service, m_node, TITLE, value );
        return this;
    }
}
