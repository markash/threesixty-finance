package com.github.markash.threesixty.financial.shared.operations;

import java.io.Serializable;

public class ImportResult implements Serializable {
  private static final long serialVersionUID = -7632861880110014792L;
  
  private String fileName;
  private int noFileRows;
  private int noImportedRows;
  private boolean successful;
  
  public String getFileName() {
    return fileName;
  }
  
  public void setFileName(
      final String fileName) {
    
    this.fileName = fileName;
  }

  public int getNoFileRows() {
    return noFileRows;
  }

  public void setNoFileRows(
      final int noFileRows) {
    
    this.noFileRows = noFileRows;
  }

  public int getNoImportedRows() {
    return noImportedRows;
  }

  public void setNoImportedRows(
      final int noImportedRows) {
    this.noImportedRows = noImportedRows;
  }

  public boolean isSuccessful() {
    return successful;
  }

  public void setSuccessful(
      final boolean successful) {
    
    this.successful = successful;
  }  
}
