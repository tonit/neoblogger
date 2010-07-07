package com.neoblogger.store.neo4j;

import org.neo4j.commons.Predicate;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.traversal.Position;
import org.neo4j.graphdb.traversal.TraversalDescription;

/**
 *
 */
public interface NeoBloggerTraversal
{

    TraversalDescription directChilds( Direction direction, final BloggerRelationship type, Predicate<Position> filter );

    public Iterable<Position> traverse( final Node startNode, final TraversalDescription descr );

}
