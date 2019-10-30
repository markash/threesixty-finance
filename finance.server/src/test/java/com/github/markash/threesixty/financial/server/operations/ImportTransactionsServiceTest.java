package com.github.markash.threesixty.financial.server.operations;

import java.util.List;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.BeanMetaData;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.testing.platform.BeanTestingHelper;
import org.eclipse.scout.rt.testing.platform.mock.BeanMock;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.eclipse.scout.rt.testing.server.runner.RunWithServerSession;
import org.eclipse.scout.rt.testing.server.runner.ServerTestRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.github.markash.threesixty.financial.server.ServerSession;
import com.github.markash.threesixty.financial.shared.operations.IImportTransactionsRepository;
import com.github.markash.threesixty.financial.shared.operations.IImportTransactionsService;
import com.github.markash.threesixty.financial.shared.operations.ImportTransactionLine;
import com.github.markash.threesixty.financial.shared.operations.ImportTransactionLineParser;


@RunWithSubject("anonymous")
@RunWith(ServerTestRunner.class)
@RunWithServerSession(ServerSession.class)
public class ImportTransactionsServiceTest {

    @BeanMock
    private IImportTransactionsRepository mockSvc;

    
    private static final String CONTENT = 
            "Date,Description,Amount,Balance\r\n" + 
            "20190201,CREDIT INTEREST,4.45,106618.25\r\n" + 
            "20190201,POS PURCHASE (EFFEC 31012019) C*FLM EATERY THE MARC SANDT CARD NO.  3648,-87.60,106530.65\r\n" + 
            "20190201,ACB DEBIT:EXTERNAL MULTID FOR-WEBONLINE 04841240,-37.50,106493.15\r\n" + 
            "20190201,ACB DEBIT:EXTERNAL M-CHOICE M-CHOICE94762891,-809.00,105684.15\r\n" + 
            "20190201,ACB DEBIT:EXTERNAL A ATKV KKASHW0001 ASH0007,-516.00,105168.15\r\n" + 
            "20190201,ACB DEBIT:EXTERNAL VIRGIN ACT4002112768 161347,-175.00,104993.15\r\n" + 
            "20190201,ACB DEBIT:EXTERNAL MULTID FOR-WEBONLINE 04841275,-19.00,104974.15";
    
    @Before
    public void setup() {
       
        BeanMetaData meta = new BeanMetaData(IImportTransactionsRepository.class, mockSvc);
        
        BeanTestingHelper.get().registerBean(meta);
    }
    
    @Test
    public void parseCsv() throws Exception {
    
        BinaryResource resource = new BinaryResource("test.csv", CONTENT.getBytes());
        List<ImportTransactionLine> lines = new ImportTransactionLineParser().apply(resource);
        
        Assert.assertEquals(7, lines.size());
    }
    
    @Test
    public void testReadingCsv() throws Exception {
        
        BinaryResource resource = new BinaryResource("test.csv", CONTENT.getBytes());
        
        List<ImportTransactionLine> lines = new ImportTransactionLineParser().apply(resource);
        
        BEANS.get(IImportTransactionsService.class).importFile(lines);
        
        Mockito.verify(mockSvc).importTransactionHistory(lines);
        Mockito.verify(mockSvc).updateFullTextIndex();
        Mockito.verify(mockSvc).deleteDuplicateTransactionHistory();
    }
}
