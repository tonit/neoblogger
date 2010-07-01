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
package com.neoblogger.store.neo4j;

import com.neoblogger.api.primitive.Author;
import org.junit.Test;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNull.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 */
public class Neo4JBlogServiceTest
{

    @Test
    public void initialStateAndBehaviour()
        throws Exception
    {
        BloggerContext ctx = mock( BloggerContext.class );
        Neo4JBlogService service = new Neo4JBlogService( ctx );

        Author auth = service.registerAuthor( "foo", "bar" );
        assertThat( auth, is( notNullValue() ) );

        assertThat( service.getBlogs().iterator().hasNext(), is( false ) );

    }

    @Test( expected = AssertionError.class )
    public void wrongLogin()
        throws Exception
    {
        BloggerContext ctx = mock( BloggerContext.class );
        Neo4JBlogService service = new Neo4JBlogService( ctx );

        service.login( null );
    }

}
