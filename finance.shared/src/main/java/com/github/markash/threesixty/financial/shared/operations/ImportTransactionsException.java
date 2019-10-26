package com.github.markash.threesixty.financial.shared.operations;

public class ImportTransactionsException extends Exception {
    private static final long serialVersionUID = -1584162904451842381L;

    public ImportTransactionsException() {
        super();
    }

    public ImportTransactionsException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ImportTransactionsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImportTransactionsException(String message) {
        super(message);
    }

    public ImportTransactionsException(Throwable cause) {
        super(cause);
    }
}
