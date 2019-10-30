package com.github.markash.threesixty.financial.shared.database;

import org.eclipse.scout.rt.platform.config.AbstractBooleanConfigProperty;

public class DatabaseMigrationConfigProperties {

    private DatabaseMigrationConfigProperties() {
        
    }
    
    public static class MigrateProperty extends AbstractBooleanConfigProperty {

        @Override
        public String getKey() {
          return "scout.sql.jdbc.migrate";
        }

        @Override
        public String description() {
          return "Property to specify if the database should be migrated. Default is false.";
        }

        @Override
        public Boolean getDefaultValue() {
          return Boolean.FALSE;
        }
    }
}
