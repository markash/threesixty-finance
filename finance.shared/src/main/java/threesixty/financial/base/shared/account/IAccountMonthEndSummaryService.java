package threesixty.financial.base.shared.account;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import com.github.markash.threesixty.financial.shared.accounts.AccountMonthEndSummaryTablePageData;

@TunnelToServer
public interface IAccountMonthEndSummaryService extends IService {

	AccountMonthEndSummaryTablePageData getAccountMonthEndSummaryTableData(SearchFilter filter);
}
