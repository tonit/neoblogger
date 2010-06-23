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
package com.neoblogger.api;

import java.util.Iterator;

/**
 * Domain Service
 *
 * A Blog Service is per User Session. So those things like authorization and user context data is implicit.
 * This is a the main service facade of Neo Blogger, a neo4j demo.
 */
public interface BlogService
{

    /**
     * For now we just make all blogs traversable. (later)
     *
     * @return means to get the actual blogs you have at least read access to.
     */
    Iterator<Blog> blogs();

    /**
     * @param blog blog that this user wants to create. He should get write access automatically.
     *
     * @return this for fluent api usage.
     */
    BlogService createBlog( Blog blog );

    /**
     * @param blog that you want to be able to write to.
     *             Implementations may implement pending handshake for blog owners aproval.
     *
     * @return this for fluent api usage.
     */
    BlogService registerAsAuthor( Blog blog );

    /**
     * @param blog you want to delete.
     *
     * @return this for fluent api usage.
     */
    BlogService deleteBlog( Blog blog );

    /**
     * @param blogTarget blog you want to push publish the article in
     * @param article    The content that should be published.
     *
     * @return this for fluent api usage.
     */
    BlogService publishArticle( Blog blogTarget, Article article );


}
