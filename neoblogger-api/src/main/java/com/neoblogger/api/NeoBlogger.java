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

/**
 * Domain Service
 * 
 * System wide service that deals with registration/unregistration of users.
 * As well as login / logout
 */
public interface NeoBlogger
{

    /**
     * @param author you want to register.
     *
     * @return this
     */
    NeoBlogger register( Author author );

    /**
     * @param author you want to unregister.
     *
     * @return this
     */
    NeoBlogger unregister( Author author );

    /**
     * Once logged in, you create access to the BlogService.
     *
     * @param author you want to log in
     *
     * @return the BlogService that can be used to create and browse content.
     */
    BlogService login( Author author );

    /**
     * @param author you want to log out
     *
     * @return this
     */
    NeoBlogger logout( Author author );


}
