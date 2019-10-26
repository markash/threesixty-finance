package com.github.markash.threesixty.financial.shared.accounts;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IAccountMonthEndSummaryService extends IService {

	AccountMonthEndSummaryTablePageData getAccountMonthEndSummaryTableData(SearchFilter filter);
}
