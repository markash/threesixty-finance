package com.github.markash.threesixty.financial.client.operations;

import java.math.BigDecimal;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBigDecimalColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import com.github.markash.threesixty.financial.client.operations.AllocateForm.MainBox.AccountField;
import com.github.markash.threesixty.financial.client.operations.AllocateForm.MainBox.AddAllocationButton;
import com.github.markash.threesixty.financial.client.operations.AllocateForm.MainBox.AllocationTableField;
import com.github.markash.threesixty.financial.client.operations.AllocateForm.MainBox.AmountField;
import com.github.markash.threesixty.financial.client.operations.AllocateForm.MainBox.CancelButton;
import com.github.markash.threesixty.financial.client.operations.AllocateForm.MainBox.CommentField;
import com.github.markash.threesixty.financial.client.operations.AllocateForm.MainBox.DateField;
import com.github.markash.threesixty.financial.client.operations.AllocateForm.MainBox.DescriptionField;
import com.github.markash.threesixty.financial.client.operations.AllocateForm.MainBox.OkButton;
import com.github.markash.threesixty.financial.client.operations.AllocateForm.MainBox.RemainingField;
import com.github.markash.threesixty.financial.client.operations.AllocateForm.MainBox.SplitField;
import com.github.markash.threesixty.financial.client.operations.AllocateForm.MainBox.TransactionIdField;
import com.github.markash.threesixty.financial.shared.accounts.AccountLookupCall;
import com.github.markash.threesixty.financial.shared.operations.AllocateFormData;
import com.github.markash.threesixty.financial.shared.operations.CreateAllocatePermission;
import com.github.markash.threesixty.financial.shared.operations.IAllocateService;
import com.github.markash.threesixty.financial.shared.operations.UpdateAllocatePermission;

/**
 * Allocate a transaction from history to the ledger
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 */
@FormData(value = AllocateFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class AllocateForm extends AbstractForm {

    public AllocateForm() {
        
        this.getRemainingField().setValue(BigDecimal.ZERO);
    }
    
    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Allocate");
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

    public TransactionIdField getTransactionIdField() {
        return getFieldByClass(TransactionIdField.class);
    }

    public DateField getDateField() {
        return getFieldByClass(DateField.class);
    }

    public DescriptionField getDescriptionField() {
        return getFieldByClass(DescriptionField.class);
    }

    public AmountField getAmountField() {
        return getFieldByClass(AmountField.class);
    }

  public AccountField getAccountField() {
      return getFieldByClass(AccountField.class);
    }
    
//    public BalanceField getBalanceField() {
//        return getFieldByClass(BalanceField.class);
//    }
    
    public CommentField getCommentField() {
        return getFieldByClass(CommentField.class);
    }

    public AllocationTableField getAllocationTableField() {
        return getFieldByClass(AllocationTableField.class);
    }

    public RemainingField getRemainingField() {
        return getFieldByClass(RemainingField.class);
    }

    public SplitField getSplitField() {
        return getFieldByClass(SplitField.class);
    }

    public OkButton getOkButton() {
        return getFieldByClass(OkButton.class);
    }

    public AddAllocationButton getAddAllocationButton() {
        return getFieldByClass(AddAllocationButton.class);
    }
    
    @Order(10)
    public class MainBox extends AbstractGroupBox {

        
        @Order(10)
        public class TransactionIdField extends AbstractLongField {
            @Override
            protected String getConfiguredLabel() { return TEXTS.get("Id"); }

            @Override
            protected boolean getConfiguredEnabled() { return false; }
        }


        @Order(20)
        public class DateField extends AbstractDateField {
            @Override
            protected String getConfiguredLabel() { return TEXTS.get("Date"); }
            
            @Override
            protected boolean getConfiguredEnabled() { return false; }
        }


        @Order(30)
        public class DescriptionField extends AbstractStringField {
            @Override
            protected String getConfiguredLabel() { return TEXTS.get("Description"); }

            @Override
            protected int getConfiguredMaxLength() { return 128; }
            
            @Override
            protected boolean getConfiguredEnabled() { return false; }
        }

        @Order(40)
        public class AccountField extends AbstractSmartField<Long> {
            @Override
            protected String getConfiguredLabel() { return TEXTS.get("AccountCode"); }
            
            protected java.lang.Class<? extends ILookupCall<Long>> getConfiguredLookupCall() { return AccountLookupCall.class; };
            
            @Override
            protected boolean getConfiguredMandatory() { return true; }
        }
        
        @Order(50)
        public class AmountField extends AbstractBigDecimalField {
            @Override
            protected String getConfiguredLabel() { return TEXTS.get("Amount"); }

            @Override
            protected boolean getConfiguredEnabled() { return false; }
        }

        @Order(60)
        public class CommentField extends AbstractStringField {
            @Override
            protected String getConfiguredLabel() { return TEXTS.get("Comment"); }

            @Override
            protected int getConfiguredMaxLength() { return 128; }
        }
        
        @Order(70)
        public class RemainingField extends AbstractBigDecimalField {
            @Override
            protected String getConfiguredLabel() { return TEXTS.get("Remainder"); }

            @Override
            protected boolean getConfiguredEnabled() { return false; }
            
            protected void recalculate(final BigDecimal allocated) {
                
                BigDecimal amount = getAmountField().getValue();
                
                BigDecimal remaining = amount.subtract(allocated == null ? BigDecimal.ZERO : allocated);
                
                setValue(remaining);
                
                fireValueChanged();
            }
        }

//        @Order(50)
//        public class BalanceField extends AbstractBigDecimalField {
//            @Override
//            protected String getConfiguredLabel() { return TEXTS.get("Balance"); }
//
//            @Override
//            public boolean getEnabledProperty() { return false; }
//        }
        
        @Order(80)
        public class SplitField extends AbstractBooleanField {
            @Override
            protected String getConfiguredLabel() {
                return TEXTS.get("SplitAllocation");
            }
            
            @Override
            protected void execChangedValue() {
                
                final boolean split = this.getValue();
                
                /* Toggle the single account fields not mandatory */
                getAccountField()
                    .setMandatory(!split);
                getAccountField()
                    .setEnabled(!split);
                getAmountField()
                    .setEnabled(!split);
                getCommentField()
                    .setEnabled(!split);
                
                /* Toggle the multiple allocation table enabled */
                getAllocationTableField()
                    .setEnabled(split);
                
                AddAllocationButton button = ((AllocateForm) getForm()).getAddAllocationButton();
                if (button != null) {
                    button.setEnabled(split);
                    
                    /* Add first row to the allocations table using the add allocation button */
                    if (getAllocationTableField().getTable().getRowCount() == 0) {
                        button.execClickAction();
                    }
                }
            }
        }
        
        @Order(90)
        public class AllocationTableField extends AbstractTableField<AllocationTableField.Table> {
            
            public class Table extends AbstractTable {

                public CommentColumn getCommentColumn() {
                    return getColumnSet().getColumnByClass(CommentColumn.class);
                }

                public AmountColumn getAmountColumn() {
                    return getColumnSet().getColumnByClass(AmountColumn.class);
                }

                public AccountColumn getAccountColumn() {
                    return getColumnSet().getColumnByClass(AccountColumn.class);
                }

                protected boolean getConfiguredAutoResizeColumns() { return true; };
                
                @Override
                protected void execContentChanged() {
                    
                    BigDecimal allocated = getTable()
                                        .getAmountColumn()
                                        .getValues(false)
                                        .stream()
                                        .filter(v -> v != null)
                                        .reduce(BigDecimal.ZERO, (i, v) -> i.add(v));
                    
                    
                    getRemainingField().recalculate(allocated);
                }
                
                @Order(10)
                public class AccountColumn extends AbstractSmartColumn<Long> {
                    @Override
                    protected String getConfiguredHeaderText() { return TEXTS.get("AccountCode"); }

                    @Override
                    protected int getConfiguredWidth() { return 100; }
                    
                    @Override
                    protected boolean getConfiguredMandatory() { return true; };
                    
                    @Override
                    protected boolean getConfiguredEditable() { return true; };
                    
                    @Override
                    protected java.lang.Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
                        return AccountLookupCall.class;
                    };
                }

                @Order(20)
                public class AmountColumn extends AbstractBigDecimalColumn {
                    @Override
                    protected String getConfiguredHeaderText() { return TEXTS.get("Amount"); }

                    @Override
                    protected int getConfiguredWidth() { return 100; }
                    
                    @Override
                    protected boolean getConfiguredEditable() { return true; };
                    
                }

                @Order(30)
                public class CommentColumn extends AbstractStringColumn {
                    @Override
                    protected String getConfiguredHeaderText() { return TEXTS.get("Comment"); }

                    @Override
                    protected int getConfiguredWidth() { return 100; }
                    
                    @Override
                    protected boolean getConfiguredEditable() { return true; };
                }                
            }

            @Override
            protected String getConfiguredLabel() { return TEXTS.get("Allocations"); }

            @Override
            protected boolean getConfiguredLabelVisible() { return false; }
            
            @Override
            protected boolean getConfiguredFillHorizontal() { return true; }
            
            @Override
            protected int getConfiguredGridH() { return 6; }
        }

        
        
        
        @Order(101)
        public class OkButton extends AbstractOkButton {
        }

        @Order(102)
        public class CancelButton extends AbstractCancelButton {
        }
        
        public class AddAllocationButton extends AbstractButton {
            
            protected String getConfiguredLabel() { return TEXTS.get("AddAllocation"); };
            
            @Override
            protected void execClickAction() {

                getAllocationTableField()
                    .getTable()
                    .addRow(true);
                
            }
        }
    }

    public class ModifyHandler extends AbstractFormHandler {

        @Override
        protected void execLoad() {
            IAllocateService service = BEANS.get(IAllocateService.class);
            AllocateFormData formData = new AllocateFormData();
            exportFormData(formData);
            formData = service.load(formData);
            importFormData(formData);

            setEnabledPermission(new UpdateAllocatePermission());
        }

        @Override
        protected void execStore() {
            IAllocateService service = BEANS.get(IAllocateService.class);
            AllocateFormData formData = new AllocateFormData();
            exportFormData(formData);
            service.store(formData);
        }
    }

    public class NewHandler extends AbstractFormHandler {

        @Override
        protected void execLoad() {
            IAllocateService service = BEANS.get(IAllocateService.class);
            AllocateFormData formData = new AllocateFormData();
            exportFormData(formData);
            formData = service.prepareCreate(formData);
            importFormData(formData);

            setEnabledPermission(new CreateAllocatePermission());
        }

        @Override
        protected void execStore() {
            IAllocateService service = BEANS.get(IAllocateService.class);
            AllocateFormData formData = new AllocateFormData();
            exportFormData(formData);
            service.create(formData);
        }
    }
}
