package org.eclipse.scout.apps.helloworld.server.helloworld;

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

/**
 * @author mpash
 */
@RunWith(ServerTestRunner.class)
@RunWithSubject(HelloWorldServiceTest.SUBJECT_NAME)
@RunWithServerSession(ServerSession.class)
public class HelloWorldServiceTest {
	public static final String SUBJECT_NAME = "test_subject";

	@Test
	public void testMessageContainsSubjectName() {
		AccountsFormData input = new AccountsFormData();
		input = BEANS.get(IAccountService.class).load(input);

		Assert.assertNotNull(input.getMessage());
		Assert.assertEquals("Hello " + SUBJECT_NAME + "!", input.getMessage().getValue());
	}
}
