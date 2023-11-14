package com.entity;


import com.SystemException;
import com.api.persistence.db.orm.OrmBean;
import com.api.util.assistants.EqualityAssistant;
import com.api.util.assistants.HashCodeAssistant;


/**
 * Peer object that maps to the gl_accounts database table/view.
 *
 * @author auto generated.
 */
public class GlAccounts extends OrmBean {




	// Property name constants that belong to respective DataSource, GlAccountsView

/** The property name constant equivalent to property, AcctId, of respective DataSource view. */
  public static final String PROP_ACCTID = "AcctId";
/** The property name constant equivalent to property, AcctTypeId, of respective DataSource view. */
  public static final String PROP_ACCTTYPEID = "AcctTypeId";
/** The property name constant equivalent to property, AcctCatgId, of respective DataSource view. */
  public static final String PROP_ACCTCATGID = "AcctCatgId";
/** The property name constant equivalent to property, AcctSeq, of respective DataSource view. */
  public static final String PROP_ACCTSEQ = "AcctSeq";
/** The property name constant equivalent to property, AcctNo, of respective DataSource view. */
  public static final String PROP_ACCTNO = "AcctNo";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";
/** The property name constant equivalent to property, Code, of respective DataSource view. */
  public static final String PROP_CODE = "Code";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, AcctBaltypeId, of respective DataSource view. */
  public static final String PROP_ACCTBALTYPEID = "AcctBaltypeId";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";



	/** The javabean property equivalent of database column gl_accounts.acct_id */
  private int acctId;
/** The javabean property equivalent of database column gl_accounts.acct_type_id */
  private int acctTypeId;
/** The javabean property equivalent of database column gl_accounts.acct_catg_id */
  private int acctCatgId;
/** The javabean property equivalent of database column gl_accounts.acct_seq */
  private int acctSeq;
/** The javabean property equivalent of database column gl_accounts.acct_no */
  private String acctNo;
/** The javabean property equivalent of database column gl_accounts.name */
  private String name;
/** The javabean property equivalent of database column gl_accounts.code */
  private String code;
/** The javabean property equivalent of database column gl_accounts.description */
  private String description;
/** The javabean property equivalent of database column gl_accounts.acct_baltype_id */
  private int acctBaltypeId;
/** The javabean property equivalent of database column gl_accounts.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column gl_accounts.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column gl_accounts.user_id */
  private String userId;



	// Getter/Setter Methods

/**
 * Default constructor.
 */
  public GlAccounts() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable acctId
 */
  public void setAcctId(int value) {
    this.acctId = value;
  }
/**
 * Gets the value of member variable acctId
 */
  public int getAcctId() {
    return this.acctId;
  }
/**
 * Sets the value of member variable acctTypeId
 */
  public void setAcctTypeId(int value) {
    this.acctTypeId = value;
  }
/**
 * Gets the value of member variable acctTypeId
 */
  public int getAcctTypeId() {
    return this.acctTypeId;
  }
/**
 * Sets the value of member variable acctCatgId
 */
  public void setAcctCatgId(int value) {
    this.acctCatgId = value;
  }
/**
 * Gets the value of member variable acctCatgId
 */
  public int getAcctCatgId() {
    return this.acctCatgId;
  }
/**
 * Sets the value of member variable acctSeq
 */
  public void setAcctSeq(int value) {
    this.acctSeq = value;
  }
/**
 * Gets the value of member variable acctSeq
 */
  public int getAcctSeq() {
    return this.acctSeq;
  }
/**
 * Sets the value of member variable acctNo
 */
  public void setAcctNo(String value) {
    this.acctNo = value;
  }
/**
 * Gets the value of member variable acctNo
 */
  public String getAcctNo() {
    return this.acctNo;
  }
/**
 * Sets the value of member variable name
 */
  public void setName(String value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 */
  public String getName() {
    return this.name;
  }
/**
 * Sets the value of member variable code
 */
  public void setCode(String value) {
    this.code = value;
  }
/**
 * Gets the value of member variable code
 */
  public String getCode() {
    return this.code;
  }
/**
 * Sets the value of member variable description
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 */
  public String getDescription() {
    return this.description;
  }
/**
 * Sets the value of member variable acctBaltypeId
 */
  public void setAcctBaltypeId(int value) {
    this.acctBaltypeId = value;
  }
/**
 * Gets the value of member variable acctBaltypeId
 */
  public int getAcctBaltypeId() {
    return this.acctBaltypeId;
  }
/**
 * Sets the value of member variable dateCreated
 */
  public void setDateCreated(java.util.Date value) {
    this.dateCreated = value;
  }
/**
 * Gets the value of member variable dateCreated
 */
  public java.util.Date getDateCreated() {
    return this.dateCreated;
  }
/**
 * Sets the value of member variable dateUpdated
 */
  public void setDateUpdated(java.util.Date value) {
    this.dateUpdated = value;
  }
/**
 * Gets the value of member variable dateUpdated
 */
  public java.util.Date getDateUpdated() {
    return this.dateUpdated;
  }
/**
 * Sets the value of member variable userId
 */
  public void setUserId(String value) {
    this.userId = value;
  }
/**
 * Gets the value of member variable userId
 */
  public String getUserId() {
    return this.userId;
  }

@Override
public boolean equals(Object obj) {
   if (this == obj) {
      return true;
   }
   if (obj == null) {
      return false;
   }
   if (getClass() != obj.getClass()) {
      return false;
   }
   final GlAccounts other = (GlAccounts) obj; 
   if (EqualityAssistant.notEqual(this.acctId, other.acctId)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.acctTypeId, other.acctTypeId)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.acctCatgId, other.acctCatgId)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.acctSeq, other.acctSeq)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.acctNo, other.acctNo)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.name, other.name)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.code, other.code)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.description, other.description)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.acctBaltypeId, other.acctBaltypeId)) {
      return false;
   }
   return true; 
} 

@Override
public int hashCode() {
   return HashCodeAssistant.combineHashCodes(HashCodeAssistant.hashObject(this.acctId),
               HashCodeAssistant.hashObject(this.acctTypeId),
               HashCodeAssistant.hashObject(this.acctCatgId),
               HashCodeAssistant.hashObject(this.acctSeq),
               HashCodeAssistant.hashObject(this.acctNo),
               HashCodeAssistant.hashObject(this.name),
               HashCodeAssistant.hashObject(this.code),
               HashCodeAssistant.hashObject(this.description),
               HashCodeAssistant.hashObject(this.acctBaltypeId));
} 

@Override
public String toString() {
   return "GlAccounts [acctId=" + acctId + 
          ", acctTypeId=" + acctTypeId + 
          ", acctCatgId=" + acctCatgId + 
          ", acctSeq=" + acctSeq + 
          ", acctNo=" + acctNo + 
          ", name=" + name + 
          ", code=" + code + 
          ", description=" + description + 
          ", acctBaltypeId=" + acctBaltypeId  + "]";
}

/**
 * Stubbed initialization method designed to implemented by developer.

 */
  public void initBean() throws SystemException {}
}