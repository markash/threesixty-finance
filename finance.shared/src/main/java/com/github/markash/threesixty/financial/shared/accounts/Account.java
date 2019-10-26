package com.github.markash.threesixty.financial.shared.accounts;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String COL_ID = "AccountId";
	public static final String COL_NAME = "AccountName";
	public static final String COL_PARENT_ID = "ParentAccountId";
	public static final String COL_SIGN = "Sign";
	
	private Long id;
	private String name;
	private Long parentId;
	private Account parent;
	private String sign;
	
	public Account() {}
	
	public Account(
			final Long id, 
			final String name) {
		
		this.id = id;
		this.name = name;
	}
	
	public Account(
			final Long id, 
			final String name,
			final Long parentId) {
		
		this.id = id;
		this.name = name;
		this.parentId = parentId;
	}
	
	public Long getId() { return id; }
	public void setId(final Long id) { this.id = id; }
	
	public Long getParentId() { return parentId; }
	public void setParentId(final Long id) { this.parentId = id; }
	
	public String getName() { return name; }
	public void setName(final String name) { this.name = name; }
	
	public Account getParent() { return this.parent; }
	public void setParent(final Account parent) { this.parent = parent; }

	public String getSign() { return this.sign; }
    public void setSign(final String sign) { this.sign = sign; }
    
    public boolean isNegativeSign() {
    
        return getSign() != null && getSign().equals("-");
    }
    
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Account other = (Account) obj;
		return Objects.equals(id, other.id);
	}
}
