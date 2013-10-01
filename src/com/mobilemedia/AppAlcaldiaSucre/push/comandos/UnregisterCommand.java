/**
 * AUTO_COPYRIGHT_SUB_TAG
 */
package com.mobilemedia.AppAlcaldiaSucre.push.comandos;

import com.mobilemedia.AppAlcaldiaSucre.AppAlcaldiaSucre;
import com.mobilemedia.AppAlcaldiaSucre.push.ContentProviderProtocol;
import com.mobilemedia.AppAlcaldiaSucre.push.PersistentStorage;
import com.mobilemedia.AppAlcaldiaSucre.push.lib.BpasProtocol;

public class UnregisterCommand extends NetworkCommand {

    private boolean onSimSwap;
    private boolean isEnterprise;
    
    public UnregisterCommand( boolean onSimSwap, boolean isEnterprise ) {
        super( );
        this.onSimSwap = onSimSwap;
        this.isEnterprise = isEnterprise;
    }

    protected void execute() throws Exception {
        // update the registered state
        PersistentStorage.setRegistered( false );
        // unsubscribe from our Content Provider
        if( !onSimSwap ) {
            ContentProviderProtocol.performCommand( ContentProviderProtocol.CMD_UNSUBSCRIBE, isEnterprise, tx );
        }
        // unregister from BPS
        BpasProtocol bpasProtocol = (BpasProtocol) AppGenericaBB.getInstance();
        bpasProtocol.unregister( tx, isEnterprise );
    }

    public String getCommandName() {
        return "Cancelar Suscripción";
    }

    public boolean isOnSimSwap() {
        return onSimSwap;
    }
    
}