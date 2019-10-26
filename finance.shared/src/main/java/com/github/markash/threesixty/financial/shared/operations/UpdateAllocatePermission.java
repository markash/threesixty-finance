package com.github.markash.threesixty.financial.shared.operations;

import java.security.BasicPermission;

public class UpdateAllocatePermission extends BasicPermission {

    private static final long serialVersionUID = 1L;

    public UpdateAllocatePermission() {
        super(UpdateAllocatePermission.class.getSimpleName());
    }
}
