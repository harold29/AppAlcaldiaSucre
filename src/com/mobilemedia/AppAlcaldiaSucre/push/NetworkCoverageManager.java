/**
 * AUTO_COPYRIGHT_SUB_TAG
 */
package com.mobilemedia.AppAlcaldiaSucre.push;

import com.mobilemedia.AppAlcaldiaSucre.push.comandos.NetworkCommand.Transaction;

import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.CoverageStatusListener;

/**
 * Listens for network coverage and provides blocking mechanism for network commands
 * 
 */
public class NetworkCoverageManager implements CoverageStatusListener {

    private static NetworkCoverageManager instance = new NetworkCoverageManager();

    private Transaction current;

    private NetworkCoverageManager() {
        CoverageInfo.addListener( this );
    }

    public static NetworkCoverageManager getInstance() {
        return instance;
    }

    public void coverageStatusChanged( int newCoverage ) {
        // called when device coverage changes
        // check whether we are in coverage and if so then start processing commands
        if( hasCoverage() ) {
            Transaction currentLocal = current;
            if( currentLocal != null ) {
                currentLocal.resumeAndNotify();
            }
        }
    }

    /**
     * Blocks the current thread until there is network coverage or the given transaction is canceled
     * 
     * @param tx
     *            current transaction
     */
    public void waitForNetworkCoverage( Transaction tx ) {
        if( current != null && current != tx ) {
            current.cancelAndNotify();
        }
        current = tx;
        while( !current.isCancelled() && !hasCoverage() ) {
            current.waitForNotification();
        }
        current = null;
    }

    /**
     * Check whether device is in coverage for BPS & CP registration
     */
    public static boolean hasCoverage() {
        boolean bisCoverage = CoverageInfo.isCoverageSufficient( CoverageInfo.COVERAGE_BIS_B );
        // the following constant is equal to CoverageInfo.COVERAGE_CARRIER in 4.2 code or
        // CoverageInfo.COVERAGE_DIRECT in later releases, both have the same value
        int carrierCode = 0x01;
        boolean carrierCoverage = CoverageInfo.isCoverageSufficient( carrierCode );
        return bisCoverage || carrierCoverage;
    }
}
