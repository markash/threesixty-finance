package threesixty.financial.base.shared.exception;

public class DatabaseException extends Exception {
    private static final long serialVersionUID = 38968324346359851L;

    public DatabaseException() {
        super();
    }

    public DatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
