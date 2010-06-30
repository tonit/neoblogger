package com.neoblogger.intern;

import com.neoblogger.api.BlogService;
import com.neoblogger.api.NeoBloggerLocator;
import org.apache.commons.discovery.tools.DiscoverSingleton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EmbeddedNeoBloggerLocator implements NeoBloggerLocator
{

    private static Log LOG = LogFactory.getLog( EmbeddedNeoBloggerLocator.class );
    private volatile BlogService m_service;

    public EmbeddedNeoBloggerLocator()
    {

    }

    /**
     * This is more a servicelocator
     *
     * @return a valid neo blogger instance.
     */
    @Override
    public synchronized BlogService get()
    {
        LOG.debug( "Get Blogger Service" );
        if( m_service == null )
        {
            m_service = (BlogService) DiscoverSingleton.find( BlogService.class );

        }
        return m_service;
    }

    @Override
    public synchronized void detach()
    {
        LOG.debug( "Detach" );
        m_service.close();
        m_service = null;
    }


}