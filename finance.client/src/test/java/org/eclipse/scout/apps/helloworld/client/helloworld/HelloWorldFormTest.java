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

import com.github.markash.threesixty.financial.shared.accounts.AccountsFormData;
import com.github.markash.threesixty.financial.shared.finance.IAccountService;

/**
 * Contains Tests for the {@link AccountsForm}.
 *
 * @author mpash
 */
@RunWith(ClientTestRunner.class)
@RunWithSubject("anonymous")
@RunWithClientSession(TestEnvironmentClientSession.class)
public class HelloWorldFormTest {

	private static final String MESSAGE_VALUE = "testData";

	// Register a mock service for {@link IHelloWorldService}
	@BeanMock
	private IAccountService m_mockSvc;

	/**
	 * Return a reference {@link AccountsFormData} on method
	 * {@link IAccountService#load(AccountsFormData)}.
	 */
	@Before
	public void setup() {
		AccountsFormData result = new AccountsFormData();
		result.getMessage().setValue(MESSAGE_VALUE);

		when(m_mockSvc.load(any())).thenReturn(result);
	}

	@Test
	public void testNothing() {
	  
	}
	/**
	 * Tests that the {@link MessageField} is disabled.
	 */
//	@Test
//	public void testMessageFieldDisabled() {
//		AccountsForm frm = new AccountsForm();
//		assertFalse(frm.getMessageField().isEnabled());
//	}
//
//	/**
//	 * Tests that the {@link MessageField} is correctly filled after start.
//	 */
//	@Test
//	public void testMessageCorrectlyImported() {
//		AccountsForm frm = new AccountsForm();
//		frm.start();
//
//		assertEquals(MESSAGE_VALUE, frm.getMessageField().getValue());
//	}
}
