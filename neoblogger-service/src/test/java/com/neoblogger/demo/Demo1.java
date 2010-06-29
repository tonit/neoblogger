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

import com.neoblogger.api.NeoBloggerLocator;
import com.neoblogger.api.primitive.Article;
import com.neoblogger.api.primitive.Author;
import com.neoblogger.api.primitive.Blog;
import com.neoblogger.api.BlogService;
import com.neoblogger.api.NeoBlogger;

import static com.neoblogger.intern.NeoBloggerFactory.*;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import com.neoblogger.api.NeoBloggerAuthorizationException;
import com.neoblogger.intern.EmbeddedNeoBloggerLocator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Demo of service layer including real backend to show off the red path without a UI
 */
public class Demo1
{

    private NeoBloggerLocator m_blogger;

    @Test
    public void simpleInteractionTest()
        throws NeoBloggerAuthorizationException
    {
        // creating Pojos that are cheap to work with. No Service interaction.
        Author author = getAuthor( "Toni" );
        Blog toniBlog = createBlog( "My Blog" );
        Article article = createArticle( "My Little Farm" );

        // aquire the service
        NeoBlogger neoBlogger = m_blogger.get();

        // interact
        neoBlogger.login( author )
            .createBlog( toniBlog )
            .publishArticle( toniBlog, article );

        neoBlogger.logout( author );

        // Introduce another author
        Author authorGuest = getAuthor( "Peter" );
        Article articlePeter = createArticle( "Peters Little Farm" );

        BlogService service = neoBlogger.login( authorGuest );

        // try to blog without sufficient rights:
        try
        {
            service.publishArticle( toniBlog, articlePeter );
            fail( "Expected that Peter is not authorized to add an article to this blog, yet." );
        } catch( NeoBloggerAuthorizationException e )
        {
            // expected
        }

        // subscribe to all
        for( Blog blog : service.blogs() )
        {
            service.registerAsAuthor( blog );
        }

        // if we reached here we should be able to blog to the "My Blog" with peter
        service.publishArticle( toniBlog, articlePeter );

        try
        {
            service.deleteBlog( toniBlog );
            fail( "Expected that Peter is not authorized to delete the blog." );
        } catch( NeoBloggerAuthorizationException e )
        {
            // expected
        }

        neoBlogger.logout( authorGuest );
        BlogService toniService = neoBlogger.login( author );
        toniService.deleteBlog( toniBlog );

        // no more blogs
        assertThat( toniService.blogs().iterator().hasNext(), is( false ) );
        neoBlogger.logout( author );
    }

    @Before
    public void before()
    {
        m_blogger = new EmbeddedNeoBloggerLocator();
    }

    @After
    public void after()
    {
        m_blogger.detach();
    }


}
