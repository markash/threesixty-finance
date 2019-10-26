package com.github.markash.threesixty.financial.shared.accounts;

import java.security.BasicPermission;

public class UpdateAccountPermission extends BasicPermission {

    private static final long serialVersionUID = 1L;

    public UpdateAccountPermission() {
        super(UpdateAccountPermission.class.getSimpleName());
    }
}
