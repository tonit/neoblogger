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

import com.neoblogger.api.primitive.Article;
import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.primitive.Blog;

/**
 * Domain Service
 *
 * A Blog Service is per User Session. So those things like authorization and user context data is implicit.
 * This is a the main service facade of Neo Blogger, a neo4j demo.
 */
public interface BlogService
{

    Author createAuthor( String openID, String fullname )
        throws NeoBloggerAuthorizationException;

    /**
     * For now we just make all blogs traversable. (later)
     *
     * @return means to get the actual blogs you have at least read access to.
     *
     * @throws NeoBloggerAuthorizationException
     *          raised if you are not allowed to do this action.
     */
    Iterable<Blog> blogs()
        throws NeoBloggerAuthorizationException;

    AuthorizedBlogService login( Author author )
        throws NeoBloggerAuthorizationException;

    void close();
}
