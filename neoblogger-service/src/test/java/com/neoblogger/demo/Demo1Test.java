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
package com.neoblogger.demo;

import com.neoblogger.api.AuthorizedBlogService;
import com.neoblogger.api.BlogService;
import com.neoblogger.api.ServiceFactory;
import com.neoblogger.api.NeoBloggerAuthorizationException;
import com.neoblogger.api.primitive.Article;
import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.primitive.Blog;
import com.neoblogger.runtime.EmbeddedNeoBloggerLocator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

/**
 * Demo of service layer including real backend to show off the red path without a UI
 */
public class Demo1Test
{

    private ServiceFactory<BlogService> m_blogger;

    @Test
    public void testCreateNewUserAccount()
        throws NeoBloggerAuthorizationException
    {
        // aquire the service
        BlogService neoBlogger = m_blogger.create( true );

        assertThat( neoBlogger.getBlogs().iterator().hasNext(), is( false ) );

        // interact
        Author author = neoBlogger.registerAuthor( "donald.blogspot.com", "Donald Duck" );

        neoBlogger.login( author )
            .createBlog().setTitle( "My Little Farm" );

        assertThat( neoBlogger.getBlogs().iterator().hasNext(), is( true ) );
    }

    @Test
    public void testSaveArticle()
        throws NeoBloggerAuthorizationException
    {
        // aquire the service
        BlogService neoBlogger = m_blogger.create( true );

        // interact
        Author author = neoBlogger.registerAuthor( "donald.blogspot.com", "Donald Duck" );

        AuthorizedBlogService s = neoBlogger.login( author );
        assertThat( s.getArticles().iterator().hasNext(), is( false ) );

        s.createArticle().setTitle( "Hello World" );
        assertThat( "article exists", s.getArticles().iterator().hasNext(), is( true ) );

    }

    @Test
    public void testPublishAnArticleToASingleBlog()
        throws NeoBloggerAuthorizationException
    {
        // aquire the service
        BlogService neoBlogger = m_blogger.create( true );

        // interact
        Author author = neoBlogger.registerAuthor( "donald.blogspot.com", "Donald Duck" );
        
        AuthorizedBlogService authorizedService = neoBlogger.login( author );
        Blog myOSSBlog = authorizedService.createBlog().setTitle( "My OSS Blog" );
        Blog myCompanyBlog = authorizedService.createBlog().setTitle( "My Company Blog" );

        Article article = authorizedService.createArticle().setTitle( "Hello World" );

        // before publish
        assertThat( authorizedService.getArticles( myOSSBlog ).iterator().hasNext(), is( false ) );

        authorizedService.publishArticle( myOSSBlog, article );

        // after publish
        assertThat( authorizedService.getArticles( myOSSBlog ).iterator().hasNext(), is( true ) );

        // but not on the other blog
        assertThat( authorizedService.getArticles( myCompanyBlog ).iterator().hasNext(), is( false ) );

    }

    @Before
    public void before()
    {
        m_blogger = EmbeddedNeoBloggerLocator.get();

    }

    @After
    public void after()
    {
        m_blogger.shutdown();
    }


}
