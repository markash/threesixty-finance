package com.github.markash.threesixty.financial.server.helloworld;

import com.github.markash.threesixty.financial.server.ServerSession;
import com.github.markash.threesixty.financial.shared.accounts.AccountsFormData;
import com.github.markash.threesixty.financial.shared.finance.IAccountService;

/**
 * 
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 *
 */
public class AccountService implements IAccountService {

	@Override
	public AccountsFormData load(AccountsFormData input) {
		StringBuilder msg = new StringBuilder();
		msg.append("Hello ").append(ServerSession.get().getUserId()).append('!');
		input.getMessage().setValue(msg.toString());
		return input;
	}
}
