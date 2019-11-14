package threesixty.financial.base.shared.account;

import java.security.BasicPermission;

public class ReadAccountPermission extends BasicPermission {

    private static final long serialVersionUID = 1L;

    public ReadAccountPermission() {
        super(ReadAccountPermission.class.getSimpleName());
    }
}
