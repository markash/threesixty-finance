package com.github.markash.threesixty.financial.shared.operations;

import java.security.BasicPermission;

public class ReadImportPermission extends BasicPermission {

  private static final long serialVersionUID = 1L;

  public ReadImportPermission() {
    super(ReadImportPermission.class.getSimpleName());
  }
}
