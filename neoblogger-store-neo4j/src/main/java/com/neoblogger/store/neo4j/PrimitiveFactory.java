package com.neoblogger.store.neo4j;

import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.primitive.Blog;
import org.neo4j.graphdb.Node;

/**
 * Created by IntelliJ IDEA.
 * User: tonit
 * Date: Jun 29, 2010
 * Time: 7:23:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PrimitiveFactory
{

    Author getAuthor( Node node );

    Blog getBlog( Node node );
}
