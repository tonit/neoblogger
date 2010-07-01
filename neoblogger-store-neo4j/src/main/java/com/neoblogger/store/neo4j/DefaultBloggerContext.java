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

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

/**
 *
 */
public class DefaultBloggerContext implements BloggerContext
{

    // Sub Reference Nodes for this Application's Primitives.
    final private Node m_refAuthors;
    final private Node m_refArticles;
    final private Node m_refBlogs;

    // Injected Services
    final private GraphDatabaseService m_graphService;
    final private PrimitiveFactory m_primitiveFactory;

    public DefaultBloggerContext( GraphDatabaseService service, PrimitiveFactory primitiveFactory )
    {
        m_graphService = service;
        m_refAuthors = m_graphService.getReferenceNode().getSingleRelationship( BloggerRelationship.AUTHORS, Direction.OUTGOING ).getEndNode();
        m_refBlogs = m_graphService.getReferenceNode().getSingleRelationship( BloggerRelationship.BLOGS, Direction.OUTGOING ).getEndNode();
        m_refArticles = m_graphService.getReferenceNode().getSingleRelationship( BloggerRelationship.BLOGS, Direction.OUTGOING ).getEndNode();
        m_primitiveFactory = primitiveFactory;
    }

    @Override
    public GraphDatabaseService getDatabaseService()
    {
        return m_graphService;
    }

    @Override
    public Node getAuthorReferenceNode()
    {
        return m_refAuthors;
    }

    @Override
    public Node getBlogReferenceNode()
    {
        return m_refBlogs;
    }

    @Override
    public Node getArticleReferenceNode()
    {
        return m_refArticles;
    }

    @Override
    public PrimitiveFactory getPrimitiveFactory()
    {
        return m_primitiveFactory;
    }
}
