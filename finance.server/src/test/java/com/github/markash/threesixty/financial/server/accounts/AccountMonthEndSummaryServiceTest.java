package com.github.markash.threesixty.financial.server.accounts;

import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.eclipse.scout.rt.testing.server.runner.RunWithServerSession;
import org.eclipse.scout.rt.testing.server.runner.ServerTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.markash.threesixty.financial.server.ServerSession;

@RunWithSubject("anonymous")
@RunWith(ServerTestRunner.class)
@RunWithServerSession(ServerSession.class)
public class AccountMonthEndSummaryServiceTest {

  @Test
	public void testNothing() {
	  
	}
}
