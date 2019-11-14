package threesixty.financial.base.shared.account;

import java.security.BasicPermission;

public class CreateAccountPermission extends BasicPermission {

    private static final long serialVersionUID = 1L;

    public CreateAccountPermission() {
        super(CreateAccountPermission.class.getSimpleName());
    }
}
