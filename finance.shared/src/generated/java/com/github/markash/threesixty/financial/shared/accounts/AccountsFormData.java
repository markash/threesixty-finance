package com.github.markash.threesixty.financial.shared.accounts;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications
 * recommended.
 */
@Generated(value = "com.github.markash.threesixty.financial.client.accounts.AccountsForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class AccountsFormData extends AbstractFormData {

	private static final long serialVersionUID = 1L;

	public Message getMessage() {
		return getFieldByClass(Message.class);
	}

	public static class Message extends AbstractValueFieldData<String> {

		private static final long serialVersionUID = 1L;
	}
}
