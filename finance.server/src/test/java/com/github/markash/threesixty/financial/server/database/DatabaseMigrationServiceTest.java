package com.github.markash.threesixty.financial.server.database;

import org.eclipse.scout.rt.testing.server.runner.RunWithServerSession;
import org.junit.Test;

import com.github.markash.threesixty.financial.server.ServerSession;

//@RunWithSubject("anonymous")
//@RunWith(ServerTestRunner.class)
@RunWithServerSession(ServerSession.class)
public class DatabaseMigrationServiceTest {

    @Test
    public void testMigrate() {
        
        DatabaseMigrationService service = new DatabaseMigrationService();
        service.migrate();
        
    }

}
