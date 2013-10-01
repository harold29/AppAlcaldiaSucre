/**
 * AUTO_COPYRIGHT_SUB_TAG
 */
package com.mobilemedia.AppAlcaldiaSucre.push.lib;

import java.io.IOException;

import com.mobilemedia.AppAlcaldiaSucre.push.comandos.NetworkCommand.Transaction;

/**
 * Network protocol for BPS server. Its implementation depends on handheld firmware and will use the latest API available.
 */
public interface BpasProtocol {

    /**
     * Registers with BPS server
     */
    public void register( Transaction tx, boolean isEnterprise ) throws Exception;

    /**
     * Unregisters from BPS server
     */
    public void unregister( Transaction tx, boolean isEnterprise ) throws Exception;

}
