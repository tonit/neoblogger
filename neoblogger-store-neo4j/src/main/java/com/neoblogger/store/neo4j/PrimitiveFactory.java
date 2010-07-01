package com.neoblogger.store.neo4j;

import com.neoblogger.api.primitive.BloggerPrimitive;
import org.neo4j.graphdb.Node;

/**
 * Used to Convert Application Primitives into Nodes and vice versa.
 */
public interface PrimitiveFactory
{

    <T extends BloggerPrimitive> T get( Class<T> t, Node node );

    Node get( BloggerPrimitive primitive );
}
