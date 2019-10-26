package com.github.markash.threesixty.financial.shared.finance;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import com.github.markash.threesixty.financial.shared.accounts.AccountsFormData;

/**
 * 
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 *
 */
@TunnelToServer
public interface IAccountService extends IService {
	AccountsFormData load(AccountsFormData input);
}
