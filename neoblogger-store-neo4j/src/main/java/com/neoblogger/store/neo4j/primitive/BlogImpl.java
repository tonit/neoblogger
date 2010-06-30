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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.graphdb.Node;

/**
 *
 */
public class BlogImpl implements Blog
{

    private static Log LOG = LogFactory.getLog( BlogImpl.class );
    private Node m_node;
    public static final String ID = "id";

    public BlogImpl( Node node )
    {
        LOG.debug( "new Blog from node " + node );
        m_node = node;
    }
}