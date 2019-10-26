package com.github.markash.threesixty.financial.shared.operations;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IImportService extends IService {

  ImportFormData prepareCreate(ImportFormData formData);

  ImportFormData create(ImportFormData formData);

  ImportFormData load(ImportFormData formData);

  ImportFormData store(ImportFormData formData);
}
