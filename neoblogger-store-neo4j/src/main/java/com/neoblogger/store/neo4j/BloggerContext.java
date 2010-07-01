package com.neoblogger.store.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

/**
 * Convenience Container for Blogger Services to access the most important backend services and entities.
 */
public interface BloggerContext
{

    /**
     * @return valid Neo4J GraphDatabaseService
     */
    GraphDatabaseService getDatabaseService();

    /**
     * @return valid reference node of all {@link com.neoblogger.api.primitive.Author}-Nodes
     */
    Node getAuthorReferenceNode();

    /**
     * @return valid reference node of all {@link com.neoblogger.api.primitive.Blog}-Nodes
     */
    Node getBlogReferenceNode();

    /**
     * @return valid reference node of all {@link com.neoblogger.api.primitive.Article}-Nodes
     */
    Node getArticleReferenceNode();

    /**
     * @return a primitive factory
     */
    PrimitiveFactory getPrimitiveFactory();

}
