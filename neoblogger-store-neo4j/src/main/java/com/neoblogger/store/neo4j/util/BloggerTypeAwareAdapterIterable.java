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
package com.neoblogger.store.neo4j.util;

import com.neoblogger.api.primitive.BloggerPrimitive;
import com.neoblogger.store.neo4j.PrimitiveFactory;
import org.neo4j.graphdb.traversal.Position;

/**
 * Type infering Iterable Adapter that is specific for converting {@link  Position}s iterables to {@link BloggerPrimitive}s iterables.
 */
public class BloggerTypeAwareAdapterIterable<S extends Position, T extends BloggerPrimitive> extends AdapterIterable<S, T>
{

    private PrimitiveFactory m_primitiveFactory;
    private Class<T> m_type;

    public BloggerTypeAwareAdapterIterable( Class<T> type, PrimitiveFactory primitiveFactory, Iterable<S> sIterable )
    {
        super( sIterable );
        m_primitiveFactory = primitiveFactory;
        m_type = type;
    }

    @Override
    public T convert( S source )
    {
        return m_primitiveFactory.get( m_type, source.node() );
    }
}
