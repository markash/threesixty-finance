package com.github.markash.threesixty.financial.shared.operations;

import java.security.BasicPermission;

public class CreateImportPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public CreateImportPermission() {
    super(CreateImportPermission.class.getSimpleName());
  }
}
