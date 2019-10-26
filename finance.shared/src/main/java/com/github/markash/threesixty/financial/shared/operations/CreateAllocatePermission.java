package com.github.markash.threesixty.financial.shared.operations;

import java.security.BasicPermission;

public class CreateAllocatePermission extends BasicPermission {

    private static final long serialVersionUID = 1L;

    public CreateAllocatePermission() {
        super(CreateAllocatePermission.class.getSimpleName());
    }
}
