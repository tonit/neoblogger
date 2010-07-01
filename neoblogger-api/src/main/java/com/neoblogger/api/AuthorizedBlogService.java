package com.neoblogger.api;

import com.neoblogger.api.primitive.Article;
import com.neoblogger.api.primitive.Blog;

/**
 * Authorized Services. Assumes a valid author (=session) implicitly.
 */
public interface AuthorizedBlogService
{

    /**
     * @param blog you want to delete.
     *
     * @return this for fluent api usage.
     *
     * @throws NeoBloggerAuthorizationException
     *          raised if you are not allowed to do this action.
     */
    AuthorizedBlogService deleteBlog( Blog blog )
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
    AuthorizedBlogService publishArticle( Blog blogTarget, Article article )
        throws NeoBloggerAuthorizationException;

    /**
     * Create a new Article.
     */
    Article createArticle();

    /**
     * Create a new Blog
     */
    Blog createBlog();

    /**
     * @return All articles that this user wrote
     */
    Iterable<Article> getArticles();

    /**
     * @param blog
     *
     * @return All articles that are attached to this blog
     */
    Iterable<Article> getArticles( Blog blog );
}
