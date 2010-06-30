package com.neoblogger.api;

import com.neoblogger.api.primitive.Article;
import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.primitive.Blog;

/**
 * Created by IntelliJ IDEA.
 * User: tonit
 * Date: Jun 29, 2010
 * Time: 7:09:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AuthorizedBlogService
{

    /**
     * @param id
     */
    Blog createBlog( String id );

    /**
     * @param blog you want to delete.
     *
     * @return this for fluent api usage.
     *
     * @throws NeoBloggerAuthorizationException
     *          raised if you are not allowed to do this action.
     */
    BlogService deleteBlog( Blog blog )
        throws NeoBloggerAuthorizationException;

    /**
     * @param blogTarget blog you want to push publish the article in
     * @param article    The content that should be published.
     *
     * @return this for fluent api usage.
     *
     * @throws NeoBloggerAuthorizationException
     *          raised in case you are not registered as author to blogTarget
     */
    BlogService publishArticle( Blog blogTarget, Article article )
        throws NeoBloggerAuthorizationException;

    /**
     * @param s
     */
    Article createArticle( String s );
}
