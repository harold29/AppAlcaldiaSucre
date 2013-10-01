/**
 * AUTO_COPYRIGHT_SUB_TAG
 */
package com.mobilemedia.AppAlcaldiaSucre.push.comandos;

import com.mobilemedia.AppAlcaldiaSucre.AppAlcaldiaSucre;
import com.mobilemedia.AppAlcaldiaSucre.push.ContentProviderProtocol;
import com.mobilemedia.AppAlcaldiaSucre.push.PersistentStorage;
import com.mobilemedia.AppAlcaldiaSucre.push.lib.BpasProtocol;

public class RegisterCommand extends NetworkCommand {
    
    private boolean isEnterprise;

    public RegisterCommand( boolean isEnterprise ) {
        super( );
        this.isEnterprise = isEnterprise;
    }

    protected void execute() throws Exception {
        // first we register with Content Provider: (con MobileMediaNetworks)
        ContentProviderProtocol.performCommand( ContentProviderProtocol.CMD_SUBSCRIBE, isEnterprise, tx );
        // if the registration is successful we register with BPS (con Blackberry)
        BpasProtocol bpasProtocol = (BpasProtocol) AppAlcaldiaSucre.getInstance();
        bpasProtocol.register( tx, isEnterprise );
        // update the registered state
        PersistentStorage.setRegistered( true );
    }

    public String getCommandName() {
        return "Registro";
    }
}