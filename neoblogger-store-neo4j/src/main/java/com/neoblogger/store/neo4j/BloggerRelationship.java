package com.neoblogger.store.neo4j;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by IntelliJ IDEA.
 * User: tonit
 * Date: Jun 29, 2010
 * Time: 7:20:49 PM
 * To change this template use File | Settings | File Templates.
 */
public enum BloggerRelationship implements RelationshipType
{

    PART_OF, MEMBER

}
