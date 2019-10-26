package com.github.markash.threesixty.financial.server.database;

import org.eclipse.scout.rt.platform.config.ConfigUtility;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.markash.threesixty.financial.shared.database.IDatabaseMigrationService;

/**
 * Migrate the database using Flyway
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 */
@TunnelToServer
public class DatabaseMigrationService implements IDatabaseMigrationService {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    
    @Override
    public void migrate() {
        
        Flyway flyway = configure();
        migrate(flyway);
    }

    private Flyway configure() {
        LOG.info("Configuring database migration.");
        
        try {
            
            final String mappingName = ConfigUtility.getProperty("scout.sql.jdbc.mappingName");
            return Flyway.configure().dataSource(mappingName, null, null).load();
            
        } catch (Exception x) {
            
            throw new ProcessingException("Unable to configure database migration.", x);
        }
    }
    
    private void migrate(
            final Flyway flyway) {
        
        LOG.info("Migrating database to the next version.");
        
        try {
            /* Attempt the migration of the database */
            flyway.migrate();
            
        } catch (FlywayException x) {
            
            if (x != null && x.getMessage().contains("Use baseline()")) {
                
                /* Database has existing tables so baseline and then retry */
                flyway.baseline();
                /* Migrate the database */
                flyway.migrate();
            } else {
                throw new ProcessingException("Unable to migrate database.", x);
            }
        }
    }
}
