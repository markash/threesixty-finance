package com.github.markash.threesixty.financial.server.operations;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.eclipse.scout.rt.testing.server.runner.RunWithServerSession;
import org.eclipse.scout.rt.testing.server.runner.ServerTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.markash.threesixty.financial.server.ServerSession;
import com.github.markash.threesixty.financial.shared.operations.ImportResult;

import org.junit.Assert;

@RunWithSubject("anonymous")
@RunWith(ServerTestRunner.class)
@RunWithServerSession(ServerSession.class)
public class ImportTransactionsServiceTest {

    
    @Test
    public void testReadingCsv() throws Exception {
        
        String content = 
                "Date,Description,Amount,Balance\r\n" + 
                "20190201,CREDIT INTEREST,4.45,106618.25\r\n" + 
                "20190201,POS PURCHASE (EFFEC 31012019) C*FLM EATERY THE MARC SANDT CARD NO.  3648,-87.60,106530.65\r\n" + 
                "20190201,ACB DEBIT:EXTERNAL MULTID FOR-WEBONLINE 04841240,-37.50,106493.15\r\n" + 
                "20190201,ACB DEBIT:EXTERNAL M-CHOICE M-CHOICE94762891,-809.00,105684.15\r\n" + 
                "20190201,ACB DEBIT:EXTERNAL A ATKV KKASHW0001 ASH0007,-516.00,105168.15\r\n" + 
                "20190201,ACB DEBIT:EXTERNAL VIRGIN ACT4002112768 161347,-175.00,104993.15\r\n" + 
                "20190201,ACB DEBIT:EXTERNAL MULTID FOR-WEBONLINE 04841275,-19.00,104974.15";
        
        BinaryResource resource = new BinaryResource("test.csv", content.getBytes());
        
        ImportResult result = BEANS.get(ImportTransactionsService.class).importFile(resource);
        
        Assert.assertEquals(7, result.getNoFileRows());
        Assert.assertEquals(7, result.getNoImportedRows());
    }
}
