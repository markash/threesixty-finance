package com.github.markash.threesixty.financial.shared.operations;

import java.security.BasicPermission;

public class UpdateImportPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public UpdateImportPermission() {
    super(UpdateImportPermission.class.getSimpleName());
  }
}
