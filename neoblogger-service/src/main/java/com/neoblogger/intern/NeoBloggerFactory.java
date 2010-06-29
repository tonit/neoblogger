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

/**
 *
 */
public class NeoBloggerFactory
{

    public static Author getAuthor( String name )
    {
        return new SimpleAuthor( name );
    }

    public static Blog createBlog( String name )
    {
        return new SimpleBlog( name );
    }

    public static Article createArticle( String name )
    {
        return new SimpleArticle( name );
    }
}
