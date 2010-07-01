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

import java.util.Iterator;
import com.neoblogger.store.neo4j.BloggerRelationship;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.traversal.Position;
import org.neo4j.graphdb.traversal.ReturnFilter;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.kernel.TraversalFactory;

/**
 *
 */
public class TraversalHelper
{

    public static TraversalDescription directChilds( Direction direction, final BloggerRelationship type )
    {
        return TraversalFactory.createTraversalDescription()
            .sourceSelector( TraversalFactory.postorderBreadthFirstSelector() )
            .prune( TraversalFactory.pruneAfterDepth( 1 ) )

            .filter( ReturnFilter.ALL_BUT_START_NODE )
            .relationships( type, direction );

    }

    static public Iterable<Position> traverse( final Node startNode, final TraversalDescription descr )
    {
        return new Iterable<Position>()
        {
            @Override
            public Iterator<Position> iterator()
            {
                return descr.traverse( startNode ).iterator();
            }

            ;
        };
    }
}
