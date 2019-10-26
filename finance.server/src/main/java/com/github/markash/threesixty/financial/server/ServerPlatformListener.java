package com.github.markash.threesixty.financial.server;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform.State;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;

import com.github.markash.threesixty.financial.shared.database.IDatabaseMigrationService;

public class ServerPlatformListener implements IPlatformListener {

    @Override
    public void stateChanged(
            final PlatformEvent event) {
        
        if (event.getState() == State.PlatformStarted) {
     
            BEANS.get(IDatabaseMigrationService.class).migrate();
        }
    }
}
