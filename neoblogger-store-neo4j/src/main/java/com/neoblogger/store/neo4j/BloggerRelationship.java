package com.neoblogger.store.neo4j;

import org.neo4j.graphdb.RelationshipType;

/**
 * Relationship types used in underlying Neo4J Graph
 */
public enum BloggerRelationship implements RelationshipType
{

    MEMBER, BLOGS, BLOG, ARTICLE, ARTICLES, CREATED, PUBLISHED_TO, AUTHORS
}
