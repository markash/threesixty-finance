package com.github.markash.threesixty.financial.server;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform.State;

import threesixty.financial.base.shared.database.IDatabaseMigrationService;

import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;

public class ServerPlatformListener implements IPlatformListener {

    @Override
    public void stateChanged(
            final PlatformEvent event) {
        
        if (event.getState() == State.PlatformStarted) {
     
            BEANS.get(IDatabaseMigrationService.class).migrate();
        }
    }
}
