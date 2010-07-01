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

import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.primitive.Blog;

/**
 * Domain Service
 *
 * A Blog Service is per User Session. So those things like authorization and user context data is implicit.
 * This is a the main service facade of Neo Blogger, a neo4j demo.
 *
 * Authorization uses an openID like abstraction. This is dead simple (model wise) and close to real world.
 *
 * On this service, you cannot do much. You'll get a more capabble service from {@login BlogService#login }
 */
public interface BlogService
{

    /**
     * Implementations may hook in user feedback for authorization. Once this method returns, you are authorized (have a author handle)
     *
     * @param openID primary credential
     *
     * @return an author instance you can use to {@link BlogService#login(com.neoblogger.api.primitive.Author)}
     *
     * @throws NeoBloggerAuthorizationException
     *          In case your registration has failed for any reason.
     */
    Author registerAuthor( String openID )
        throws NeoBloggerAuthorizationException;

    /**
     * Once registered, you can use your openID from registration to get back at a later time.
     *
     * @param openID primary identifier for login
     *
     * @return an author instance you can use to {@link BlogService#login(com.neoblogger.api.primitive.Author)}
     *
     * @throws NeoBloggerAuthorizationException
     *          In case you are not registered yet.
     */
    Author getAuthor( String openID )
        throws NeoBloggerAuthorizationException;

    /**
     * For now we just make all getBlogs traversable. (later)
     *
     * @return means to create the actual getBlogs you have at least read access to.
     *
     * @throws NeoBloggerAuthorizationException
     *          raised if you are not allowed to do this action.
     */
    Iterable<Blog> getBlogs()
        throws NeoBloggerAuthorizationException;

    /**
     * @param author you want to log in with
     *
     * @return a service that enables you to create blogs, publish articles and so on.
     *
     * @throws NeoBloggerAuthorizationException
     *          in case login failed.
     */
    AuthorizedBlogService login( Author author )
        throws NeoBloggerAuthorizationException;
}
