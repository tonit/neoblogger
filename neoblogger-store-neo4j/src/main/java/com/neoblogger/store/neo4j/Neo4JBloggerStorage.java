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

import com.neoblogger.api.NeoBloggerStorageService;
import com.neoblogger.api.primitive.Primitive;
import com.neoblogger.api.primitive.Relationship;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * Use neo4j db
 */
public class Neo4JBloggerStorage implements NeoBloggerStorageService
{

    private EmbeddedGraphDatabase m_graphDb;

    public Neo4JBloggerStorage()
    {
        // open an instance
        m_graphDb = new EmbeddedGraphDatabase( "target/db" );
    }

    @Override
    public void close()
    {
        m_graphDb.shutdown();
    }

    @Override
    public boolean pathExists( Relationship publishes, Primitive... p )
    {
        return false;
    }

    @Override
    public boolean makePath( Relationship publishes, Primitive... p )
    {
        return false;
    }

    @Override
    public void remove( Primitive primitive )
    {

    }
}
