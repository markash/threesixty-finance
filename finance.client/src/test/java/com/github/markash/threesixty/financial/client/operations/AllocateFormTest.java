package com.github.markash.threesixty.financial.client.operations;

import org.eclipse.scout.rt.client.testenvironment.TestEnvironmentClientSession;
import org.eclipse.scout.rt.testing.client.runner.ClientTestRunner;
import org.eclipse.scout.rt.testing.client.runner.RunWithClientSession;
import org.eclipse.scout.rt.testing.platform.mock.BeanMock;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.github.markash.threesixty.financial.shared.operations.AllocateFormData;
import com.github.markash.threesixty.financial.shared.operations.IAllocateService;


@RunWithSubject("anonymous")
@RunWith(ClientTestRunner.class)
@RunWithClientSession(TestEnvironmentClientSession.class)
public class AllocateFormTest {

    @BeanMock
    private IAllocateService mockSvc;

    @Before
    public void setup() {
        AllocateFormData answer = new AllocateFormData();
        Mockito.when(mockSvc.load(ArgumentMatchers.any())).thenReturn(answer);
        Mockito.when(mockSvc.store(ArgumentMatchers.any())).thenReturn(answer);
    }

    @Test
    public void initialization() {
        
        AllocateFormData formData = new AllocateFormData();
        mockSvc.load(formData);
        
        Assert.assertNotNull(formData);
    }
}
