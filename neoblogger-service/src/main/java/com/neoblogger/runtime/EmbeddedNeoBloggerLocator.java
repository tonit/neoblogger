package com.neoblogger.runtime;

import com.neoblogger.api.BlogService;
import com.neoblogger.api.ServiceFactory;
import org.apache.commons.discovery.tools.DiscoverSingleton;

public class EmbeddedNeoBloggerLocator
{

    /**
     * This is more a servicelocator
     *
     * @return a valid neo blogger instance.
     */
    public static ServiceFactory<BlogService> get()
    {
        return (ServiceFactory<BlogService>) DiscoverSingleton.find( ServiceFactory.class );
    }


}