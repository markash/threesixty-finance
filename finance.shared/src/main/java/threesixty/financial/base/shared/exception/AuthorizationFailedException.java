package threesixty.financial.base.shared.exception;

import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;

/**
 * A veto exception indicating that authorization failed
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 */
public class AuthorizationFailedException extends VetoException {
    private static final long serialVersionUID = 211160087158731144L;
    
    public AuthorizationFailedException() {
        super(TEXTS.get("AuthorizationFailed"));
    }
}
