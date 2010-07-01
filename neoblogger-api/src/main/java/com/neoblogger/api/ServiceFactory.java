package com.neoblogger.api;

/**
 * Higher level admin service for a managing a service's lifecycle.
 * A factory implementation is usually stateful
 */
public interface ServiceFactory<T>
{

    /**
     * Retrieves a new service instance.
     * Depending on the implementation, the creation of the actual service might be lazy or created on factory init.
     *
     * @param clear wether to clear previous state upon startup.
     *
     * @return Service. Repetetive calls without calling {@link ServiceFactory#shutdown()} should return the same instance (no new service is being created).
     */
    T create( boolean clear );

    /**
     * If an active "service" is present (from previous {@link ServiceFactory#create(boolean)} call(s)), this instance is told to shutdown.
     * It might be save to reuse this factory now for creating new Service instances.
     */
    void shutdown();
}
