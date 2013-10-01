/**
 * AUTO_COPYRIGHT_SUB_TAG
 */
package com.mobilemedia.AppAlcaldiaSucre.push.comandos;

import com.mobilemedia.AppAlcaldiaSucre.push.ContentProviderProtocol;

public class ResumeCommand extends NetworkCommand {

	private boolean isEnterprise;
    
    public ResumeCommand( boolean isEnterprise ) {
        super( );
        this.isEnterprise = isEnterprise;
    }

    protected void execute() throws Exception {
        // first we register with Content Provider
        ContentProviderProtocol.performCommand( ContentProviderProtocol.CMD_RESUME, isEnterprise, tx );
    }

    public String getCommandName() {
        return "Resumen";
    }

}