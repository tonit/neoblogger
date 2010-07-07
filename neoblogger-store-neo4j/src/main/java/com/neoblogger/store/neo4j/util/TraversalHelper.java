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

import org.neo4j.commons.Predicate;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.traversal.Position;

/**
 *
 */
public class TraversalHelper
{

    public static Predicate<Position> ignoreSingleNodeFilter( final Node node )
    {
        return new Predicate<Position>()
        {
            @Override
            public boolean accept( Position item )
            {
                return ( item.node() != node );
            }
        };
    }

}
