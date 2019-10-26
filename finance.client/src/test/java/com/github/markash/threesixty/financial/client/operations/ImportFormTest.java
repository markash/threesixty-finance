package com.github.markash.threesixty.financial.client.operations;

import org.eclipse.scout.rt.client.testenvironment.TestEnvironmentClientSession;
import org.eclipse.scout.rt.testing.client.runner.ClientTestRunner;
import org.eclipse.scout.rt.testing.client.runner.RunWithClientSession;
import org.eclipse.scout.rt.testing.platform.mock.BeanMock;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.Before;
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
  private IImportService m_mockSvc;

  @Before
  public void setup() {
    ImportFormData answer = new ImportFormData();
    Mockito.when(m_mockSvc.prepareCreate(ArgumentMatchers.any())).thenReturn(answer);
    Mockito.when(m_mockSvc.create(ArgumentMatchers.any())).thenReturn(answer);
    Mockito.when(m_mockSvc.load(ArgumentMatchers.any())).thenReturn(answer);
    Mockito.when(m_mockSvc.store(ArgumentMatchers.any())).thenReturn(answer);
  }

  // TODO [mpash] add test cases
}
