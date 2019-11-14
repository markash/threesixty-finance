package threesixty.financial.base.client.account;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import com.github.markash.threesixty.financial.shared.accounts.AccountFormData;

import threesixty.financial.base.client.account.AccountForm.MainBox.AccountIdField;
import threesixty.financial.base.client.account.AccountForm.MainBox.CancelButton;
import threesixty.financial.base.client.account.AccountForm.MainBox.OkButton;
import threesixty.financial.base.client.account.AccountForm.MainBox.ParentAccountField;
import threesixty.financial.base.shared.account.AccountLookupCall;
import threesixty.financial.base.shared.account.CreateAccountPermission;
import threesixty.financial.base.shared.account.IAccountsService;
import threesixty.financial.base.shared.account.UpdateAccountPermission;
import threesixty.financial.base.client.account.AccountForm.MainBox.AccountNameField;

@FormData(value = AccountFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class AccountForm extends AbstractForm {

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Account");
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public CancelButton getCancelButton() {
        return getFieldByClass(CancelButton.class);
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public ParentAccountField getParentAccountField() {
        return getFieldByClass(ParentAccountField.class);
    }
    
    public AccountIdField getAccountIdField() {
        return getFieldByClass(AccountIdField.class);
    }

    public AccountNameField getAccountNameField() {
        return getFieldByClass(AccountNameField.class);
    }

    public OkButton getOkButton() {
        return getFieldByClass(OkButton.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
            return 1;
        }
        
        @Order(40)
        public class ParentAccountField extends AbstractSmartField<Long> {
            @Override
            protected String getConfiguredLabel() { return TEXTS.get("ParentAccountCode"); }
            
            @Override
            protected java.lang.Class<? extends ILookupCall<Long>> getConfiguredLookupCall() { return AccountLookupCall.class; }
            
            @Override
            protected boolean getConfiguredMandatory() { return true; }
        }

        @Order(60)
        public class AccountIdField extends AbstractLongField {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("AccountId");
            }

            @Override
            protected Long getConfiguredMinValue() {
                return -999999999999L;
            }

            @Override
            protected Long getConfiguredMaxValue() {
                return 999999999999L;
            }
            
            @Override
            protected boolean getConfiguredVisible() {
                return false;
            }
        }

        @Order(50)
        public class AccountNameField extends AbstractStringField {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("AccountName");
            }

            @Override
            protected int getConfiguredMaxLength() {
                return 128;
            }
        }
        
        @Order(100000)
        public class OkButton extends AbstractOkButton {
        }

        @Order(101000)
        public class CancelButton extends AbstractCancelButton {
        }
    }

    public class ModifyHandler extends AbstractFormHandler {

        @Override
        protected void execLoad() {
            IAccountsService service = BEANS.get(IAccountsService.class);
            AccountFormData formData = new AccountFormData();
            exportFormData(formData);
            formData = service.load(formData);
            importFormData(formData);

            setEnabledPermission(new UpdateAccountPermission());
        }

        @Override
        protected void execStore() {
            IAccountsService service = BEANS.get(IAccountsService.class);
            AccountFormData formData = new AccountFormData();
            exportFormData(formData);
            service.store(formData);
        }
    }

    public class NewHandler extends AbstractFormHandler {

        @Override
        protected void execLoad() {
            IAccountsService service = BEANS.get(IAccountsService.class);
            AccountFormData formData = new AccountFormData();
            exportFormData(formData);
            formData = service.prepareCreate(formData);
            importFormData(formData);

            setEnabledPermission(new CreateAccountPermission());
        }

        @Override
        protected void execStore() {
            IAccountsService service = BEANS.get(IAccountsService.class);
            AccountFormData formData = new AccountFormData();
            exportFormData(formData);
            service.create(formData);
        }
    }
}
