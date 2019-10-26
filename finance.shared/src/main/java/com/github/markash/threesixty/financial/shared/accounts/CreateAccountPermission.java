package com.github.markash.threesixty.financial.shared.accounts;

import java.security.BasicPermission;

public class CreateAccountPermission extends BasicPermission {

    private static final long serialVersionUID = 1L;

    public CreateAccountPermission() {
        super(CreateAccountPermission.class.getSimpleName());
    }
}
