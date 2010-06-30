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

import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.primitive.Blog;
import com.neoblogger.store.neo4j.primitive.AuthorImpl;
import com.neoblogger.store.neo4j.primitive.BlogImpl;
import org.neo4j.graphdb.Node;

/**
 * Factory for Neo4J backed primitives.
 */
public class DefaultPrimitiveFactory implements PrimitiveFactory
{

    @Override
    public Author getAuthor( Node node )
    {
        return new AuthorImpl( node );
    }

    @Override
    public Blog getBlog( Node node )
    {
        return new BlogImpl( node );
    }
}
