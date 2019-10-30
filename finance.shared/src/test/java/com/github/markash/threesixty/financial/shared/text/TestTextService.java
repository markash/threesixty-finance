package com.github.markash.threesixty.financial.shared.text;

import org.junit.Assert;
import org.junit.Test;

public class TestTextService {

    @Test
    public void authorizationFailed() {
        
        final String authorizationFailed = TextService.authorizationFailed();
        
        Assert.assertNotNull(authorizationFailed);
    }
}
