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

import com.neoblogger.api.NeoBloggerAuthorizationException;
import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.primitive.Blog;
import com.neoblogger.store.neo4j.NodePojo;
import org.neo4j.graphdb.Node;

/**
 *
 */
public class AuthorImpl implements Author, NodePojo
{

    final private Node m_node;
    public static final String PROPERTY_OPENID = "OPENID";
    public static final String PROPERTY_FULLNAME = "FULLNAME";

    public AuthorImpl( Node node )
    {
        m_node = node;
    }

    @Override
    public Node getNode()
    {
        return m_node;
    }

    public String toString()
    {
        return "[ AUTHOR node='" + m_node + "' ]";
    }

    @Override
    public Author registerAsAuthor( Blog blog )
        throws NeoBloggerAuthorizationException
    {

        return this;
    }
}
