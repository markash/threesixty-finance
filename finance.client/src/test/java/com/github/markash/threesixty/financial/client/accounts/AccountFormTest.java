package com.github.markash.threesixty.financial.client.accounts;

import org.eclipse.scout.rt.client.testenvironment.TestEnvironmentClientSession;
import org.eclipse.scout.rt.testing.client.runner.ClientTestRunner;
import org.eclipse.scout.rt.testing.client.runner.RunWithClientSession;
import org.eclipse.scout.rt.testing.platform.mock.BeanMock;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.github.markash.threesixty.financial.shared.accounts.AccountFormData;
import com.github.markash.threesixty.financial.shared.accounts.IAccountsService;

@RunWithSubject("anonymous")
@RunWith(ClientTestRunner.class)
@RunWithClientSession(TestEnvironmentClientSession.class)
public class AccountFormTest {

    @BeanMock
    private IAccountsService mockSvc;

    @Before
    public void setup() {
        AccountFormData answer = new AccountFormData();
        Mockito.when(mockSvc.prepareCreate(ArgumentMatchers.any())).thenReturn(answer);
        Mockito.when(mockSvc.create(ArgumentMatchers.any())).thenReturn(answer);
        Mockito.when(mockSvc.load(ArgumentMatchers.any())).thenReturn(answer);
        Mockito.when(mockSvc.store(ArgumentMatchers.any())).thenReturn(answer);
    }

    @Test
    public void loadAccountData() {
        
        AccountFormData formData = new AccountFormData();
        mockSvc.load(formData);
        
        Assert.assertNotNull(formData);
    }
}
