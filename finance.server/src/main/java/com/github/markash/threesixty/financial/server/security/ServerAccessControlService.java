package com.github.markash.threesixty.financial.server.security;

import java.security.AllPermission;
import java.security.Permissions;

import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.shared.security.RemoteServiceAccessPermission;

import com.github.markash.threesixty.financial.shared.security.AccessControlService;

/**
 * 
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 */
@Replace
public class ServerAccessControlService extends AccessControlService {

	@Override
	protected Permissions execLoadPermissions(String userId) {
		Permissions permissions = new Permissions();
		permissions.add(new RemoteServiceAccessPermission("*.shared.*", "*"));
		
		// TODO [mpash]: Fill access control service
		permissions.add(new AllPermission());
		return permissions;
	}
}
