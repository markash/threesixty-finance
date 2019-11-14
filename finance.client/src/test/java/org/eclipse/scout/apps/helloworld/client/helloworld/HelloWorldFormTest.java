package org.eclipse.scout.apps.helloworld.client.helloworld;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.eclipse.scout.rt.client.testenvironment.TestEnvironmentClientSession;
import org.eclipse.scout.rt.testing.client.runner.ClientTestRunner;
import org.eclipse.scout.rt.testing.client.runner.RunWithClientSession;
import org.eclipse.scout.rt.testing.platform.mock.BeanMock;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.markash.threesixty.financial.shared.accounts.AccountFormData;
import com.github.markash.threesixty.financial.shared.accounts.AccountsFormData;

import threesixty.financial.base.shared.account.IAccountsService;

/**
 * Contains Tests for the {@link AccountsForm}.
 *
 * @author Mark Ashworth
 */
@RunWith(ClientTestRunner.class)
@RunWithSubject("anonymous")
@RunWithClientSession(TestEnvironmentClientSession.class)
public class HelloWorldFormTest {

	@BeanMock
	private IAccountsService m_mockSvc;

	/**
	 * Return a reference {@link AccountsFormData} on method
	 * {@link IAccountService#load(AccountsFormData)}.
	 */
	@Before
	public void setup() {
		AccountFormData result = new AccountFormData();
		result.getAccountId().setValue(1L);

		when(m_mockSvc.load(any())).thenReturn(result);
	}

	@Test
	public void getAccountsTableData() {
	  
//	    AccountForm result = new AccountForm();
//	    result.getOkButton().doClick();
//	    
//	    verify(m_mockSvc, atLeastOnce()).store(Mockito.any());
	}
}
