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

import com.neoblogger.api.primitive.Article;
import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.primitive.Blog;
import com.neoblogger.api.BlogService;
import com.neoblogger.api.NeoBloggerAuthorizationException;
import com.neoblogger.api.NeoBloggerStorageService;
import com.neoblogger.api.primitive.Relationship;

/**
 * Default Blog Service
 */
public class BlogServiceImpl implements BlogService
{

    final private Author m_author;
    final private NeoBloggerStorageService m_storage;

    public BlogServiceImpl( Author author, NeoBloggerStorageService storageService )
    {
        m_author = author;
        m_storage = storageService;
    }

    @Override
    public Iterable<Blog> blogs()
        throws NeoBloggerAuthorizationException
    {
        return null;
    }

    @Override
    public BlogService createBlog( Blog blog )
        throws NeoBloggerAuthorizationException
    {
        return this;
    }

    @Override
    public BlogService registerAsAuthor( Blog blog )
        throws NeoBloggerAuthorizationException
    {
        m_storage.makePath( Relationship.PUBLISHES, m_author, blog );
        return this;
    }

    @Override
    public BlogService deleteBlog( Blog blog )
        throws NeoBloggerAuthorizationException
    {
        m_storage.remove( blog );
        return this;
    }

    @Override
    public BlogService publishArticle( Blog blogTarget, Article article )
        throws NeoBloggerAuthorizationException
    {
        // check authorization
        if( !m_storage.pathExists( Relationship.PUBLISHES, m_author, blogTarget ) )
        {
            throw new NeoBloggerAuthorizationException( m_author + " is not allowed to publish an article to blog " + blogTarget + " yet." );
        }

        return this;
    }
}
