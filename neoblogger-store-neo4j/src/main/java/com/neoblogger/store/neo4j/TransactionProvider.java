package com.neoblogger.store.neo4j;

import org.neo4j.graphdb.Transaction;

/**
 * Extract GraphService beginTx into its own interface.
 */
public interface TransactionProvider
{

    Transaction beginTx();
}
