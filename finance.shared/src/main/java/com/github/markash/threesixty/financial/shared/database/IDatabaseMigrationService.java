package com.github.markash.threesixty.financial.shared.database;

import org.eclipse.scout.rt.platform.service.IService;

/**
 * 
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 */
public interface IDatabaseMigrationService extends IService {

    /**
     * Migrates the database version
     */
    void migrate();
}
