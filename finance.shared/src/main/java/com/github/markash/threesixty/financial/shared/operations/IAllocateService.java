package com.github.markash.threesixty.financial.shared.operations;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface IAllocateService extends IService {

    AllocateFormData load(AllocateFormData formData);

    AllocateFormData store(AllocateFormData formData);
}
