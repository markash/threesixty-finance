package com.github.markash.threesixty.financial.shared.operations;

import java.security.BasicPermission;

public class ReadAllocatePermission extends BasicPermission {

    private static final long serialVersionUID = 1L;

    public ReadAllocatePermission() {
        super(ReadAllocatePermission.class.getSimpleName());
    }
}
