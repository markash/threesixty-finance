package threesixty.financial.base.client.dashboard.account;

import threesixty.financial.base.client.dashboard.AbstractDashboardTile;

public class AccountDashboardTile extends AbstractDashboardTile {
    
    
    public AccountDashboardTile(
            final String group) {
        
        setGroup(group);
    }
    
    @Override
    protected String getConfiguredLabel() {
        return "Test";
    }
   
    @Override
    public String getContent() {
        String content = 
                "<div class='card'>"
                + " <div class='header'><h4><b>Header</b></h4></div>"
                + " <div class='body'>Body</div>"
                + " <div class='footer'>Footer</div>"
                + "</div>";
        
        return content;
    }
}
