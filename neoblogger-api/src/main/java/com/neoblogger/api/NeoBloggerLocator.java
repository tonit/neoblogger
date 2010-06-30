package com.neoblogger.api;

/**
 *
 */
public interface NeoBloggerLocator
{

    BlogService get();

    void detach();
}
