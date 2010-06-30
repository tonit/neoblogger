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
import com.neoblogger.api.NeoBloggerAuthorizationException;
import com.neoblogger.api.NeoBloggerLocator;
import com.neoblogger.api.primitive.Author;
import com.neoblogger.intern.EmbeddedNeoBloggerLocator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Transaction;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

/**
 * Demo of service layer including real backend to show off the red path without a UI
 */
public class Demo1Test
{

    private NeoBloggerLocator m_blogger;

    @Test
    public void testCreateNewUserAccount()
        throws NeoBloggerAuthorizationException
    {
        // aquire the service
        BlogService neoBlogger = m_blogger.get();

        assertThat( neoBlogger.blogs().iterator().hasNext(), is( false ) );

        // interact
        Author author = neoBlogger.createAuthor( "donald.blogspot.com", "Donald Duck" );

        neoBlogger.login( author )
            .createBlog( "My Little Farm" );

        // m_blogger.detach();

        // neoBlogger = m_blogger.get();
        assertThat( neoBlogger.blogs().iterator().hasNext(), is( true ) );


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
