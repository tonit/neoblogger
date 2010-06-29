package com.neoblogger.api;

import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.primitive.Blog;
import com.neoblogger.api.primitive.Primitive;
import com.neoblogger.api.primitive.Relationship;

/**
 * backend storage abstraction
 */
public interface NeoBloggerStorageService
{

    void close();

    boolean pathExists( Relationship publishes, Primitive... p );

    boolean makePath( Relationship publishes, Primitive... p );

    void remove( Primitive primitive );
}
