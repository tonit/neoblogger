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
package com.neoblogger.intern;

import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.BlogService;
import com.neoblogger.api.NeoBlogger;
import com.neoblogger.api.NeoBloggerStorageService;

/**
 *
 */
public class NeoBloggerImpl implements NeoBlogger
{

    final private NeoBloggerStorageService m_storageService;

    public NeoBloggerImpl( NeoBloggerStorageService storageService )
    {
        m_storageService = storageService;
    }

    @Override
    public NeoBlogger register( Author author )
    {

        // just add this author to the backend
        return this;
    }

    @Override
    public NeoBlogger unregister( Author author )
    {
        return this;
    }

    @Override
    public BlogService login( Author author )
    {
        return createAuthorizedBlogService( author );
    }

    private BlogServiceImpl createAuthorizedBlogService( Author author )
    {
        return new BlogServiceImpl( author, m_storageService );
    }

    @Override
    public NeoBlogger logout( Author author )
    {
        // hear we would invalidate any session tokens etc.
        // But since we dont have that, we don't mess with those things at all.
        return this;
    }
}
