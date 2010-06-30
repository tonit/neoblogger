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
package com.neoblogger.api.primitive;

import com.neoblogger.api.NeoBloggerAuthorizationException;

/**
 * Domain Entity
 */
public interface Author
{

    /**
     * @param blog that you want to be able to write to.
     *             Implementations may implement pending handshake for blog owners aproval.
     *
     * @return this for fluent api usage.
     *
     * @throws com.neoblogger.api.NeoBloggerAuthorizationException
     *          raised if you are not allowed to do this action.
     */
    Author registerAsAuthor( Blog blog )
        throws NeoBloggerAuthorizationException;
}
