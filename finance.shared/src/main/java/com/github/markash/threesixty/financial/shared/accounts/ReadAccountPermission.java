package com.github.markash.threesixty.financial.shared.accounts;

import java.security.BasicPermission;

public class ReadAccountPermission extends BasicPermission {

    private static final long serialVersionUID = 1L;

    public ReadAccountPermission() {
        super(ReadAccountPermission.class.getSimpleName());
    }
}
