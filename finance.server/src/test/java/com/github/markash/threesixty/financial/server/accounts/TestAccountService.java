package com.github.markash.threesixty.financial.server.accounts;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.eclipse.scout.rt.testing.server.runner.RunWithServerSession;
import org.eclipse.scout.rt.testing.server.runner.ServerTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.markash.threesixty.financial.server.ServerSession;
import com.github.markash.threesixty.financial.shared.accounts.AccountsFormData;
import com.github.markash.threesixty.financial.shared.finance.IAccountService;

@RunWithSubject("anonymous")
@RunWith(ServerTestRunner.class)
@RunWithServerSession(ServerSession.class)
public class TestAccountService {

    @Test
    public void accountsFormDataLoad() {
        
        AccountsFormData formData = new AccountsFormData();
        BEANS.get(IAccountService.class).load(formData);
        
        Assert.assertNotNull(formData.getMessage().getValue());
    }
}
