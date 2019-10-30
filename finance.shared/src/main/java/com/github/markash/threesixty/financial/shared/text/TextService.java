package com.github.markash.threesixty.financial.shared.text;

import org.eclipse.scout.rt.platform.text.TEXTS;

/**
 * Text Service provides the most common text keys used by the application
 * 
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 */
public class TextService {
    
    private static final String AUTHORIZATION_FAILED = "AuthorizationFailed";
    
    /**
     * Authorization failed text
     * @return The text
     */
    public static final String AUTHORIZATION_FAILED() {
        
        return TEXTS.get(AUTHORIZATION_FAILED);
    }
}
