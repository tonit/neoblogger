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
import com.neoblogger.api.primitive.Blog;
import com.neoblogger.store.neo4j.BloggerRelationship;
import com.neoblogger.store.neo4j.PrimitiveFactory;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.traversal.Position;
import org.neo4j.graphdb.traversal.ReturnFilter;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.kernel.TraversalFactory;

/**
 * Convenience Class for converting Iterable<S> to Iterable<T>
 */
public abstract class AdapterIterable<S, T> implements Iterable<T>
{

    private Iterable<S> m_iiter;

    public AdapterIterable( Iterable<S> iterable )
    {
        m_iiter = iterable;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new Iterator<T>()
        {

            Iterator<S> m_iterator = m_iiter.iterator();

            @Override
            public boolean hasNext()
            {
                return m_iterator.hasNext();
            }

            @Override
            public T next()
            {
                return convert( m_iterator.next() );
            }

            @Override
            public void remove()
            {
                m_iterator.remove();
            }
        };
    }

    public abstract T convert( S source );

  

    static public Iterable<Node> iter( final Node startNode, final BloggerRelationship type )
    {
        return new Iterable<Node>()
        {

            @Override
            public Iterator<Node> iterator()
            {

                return new Iterator<Node>()
                {
                    Iterator<Position> iterator = TraversalFactory.createTraversalDescription()
                        .sourceSelector( TraversalFactory.postorderBreadthFirstSelector() )
                        .prune( TraversalFactory.pruneAfterDepth( 1 ) )
                        .filter( ReturnFilter.ALL_BUT_START_NODE )
                        .relationships( type )
                        .traverse( startNode ).iterator();

                    @Override
                    public boolean hasNext()
                    {

                        return iterator.hasNext();
                    }

                    @Override
                    public Node next()
                    {
                        return iterator.next().node();
                    }

                    @Override
                    public void remove()
                    {
                        iterator.remove();
                    }
                };


            }

        };
    }

}
