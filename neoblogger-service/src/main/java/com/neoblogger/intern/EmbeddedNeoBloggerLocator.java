package com.neoblogger.intern;

import com.neoblogger.api.NeoBlogger;
import com.neoblogger.api.NeoBloggerLocator;
import com.neoblogger.api.NeoBloggerStorageService;
import org.apache.commons.discovery.tools.DiscoverSingleton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EmbeddedNeoBloggerLocator implements NeoBloggerLocator
{

    private static Log LOG = LogFactory.getLog( EmbeddedNeoBloggerLocator.class );
    final private NeoBloggerStorageService m_store;
    private NeoBlogger m_blogger;

    public EmbeddedNeoBloggerLocator()
    {
        m_store = (NeoBloggerStorageService) DiscoverSingleton.find( NeoBloggerStorageService.class );
        m_blogger = new NeoBloggerImpl( m_store );
    }

    /**
     * This is more a servicelocator
     *
     * @return a valid neo blogger instance.
     */
    @Override
    public NeoBlogger get()
    {
        LOG.debug( "Get Blogger Service" );
        return m_blogger;
    }

    @Override
    public void detach()
    {
        LOG.debug( "Detach Backend" );
        m_store.close();
    }

    private void registerShutdownHook( final NeoBloggerStorageService backend )
    {
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                detach();
            }
        }
        );
    }
}