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

import com.github.markash.threesixty.financial.shared.operations.IImportService;
import com.github.markash.threesixty.financial.shared.operations.ImportFormData;

@RunWithSubject("anonymous")
@RunWith(ClientTestRunner.class)
@RunWithClientSession(TestEnvironmentClientSession.class)
public class ImportFormTest {

  @BeanMock
  private IImportService mockSvc;

  @Before
  public void setup() {
    ImportFormData answer = new ImportFormData();
    Mockito.when(mockSvc.prepareCreate(ArgumentMatchers.any())).thenReturn(answer);
    Mockito.when(mockSvc.create(ArgumentMatchers.any())).thenReturn(answer);
    Mockito.when(mockSvc.load(ArgumentMatchers.any())).thenReturn(answer);
    Mockito.when(mockSvc.store(ArgumentMatchers.any())).thenReturn(answer);
  }

  @Test
  public void loadAccountData() {
      
      ImportFormData formData = new ImportFormData();
      mockSvc.load(formData);
      
      Assert.assertNotNull(formData);
  }
}
