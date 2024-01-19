package com.entity;


import com.SystemException;
import com.api.persistence.db.orm.OrmBean;
import com.api.util.assistants.EqualityAssistant;
import com.api.util.assistants.HashCodeAssistant;


/**
 * Peer object that maps to the creditor database table/view.
 *
 * @author auto generated.
 */
public class Creditor extends OrmBean {


	// Property name constants that belong to respective DataSource, CreditorView

/** The property name constant equivalent to property, CreditorId, of respective DataSource view. */
  public static final String PROP_CREDITORID = "CreditorId";
/** The property name constant equivalent to property, AcctId, of respective DataSource view. */
  public static final String PROP_ACCTID = "AcctId";
/** The property name constant equivalent to property, CreditorTypeId, of respective DataSource view. */
  public static final String PROP_CREDITORTYPEID = "CreditorTypeId";
    public static final String PROP_CREDITORTYPEDESC = "CreditorTypeDesc";
/** The property name constant equivalent to property, BusinessId, of respective DataSource view. */
  public static final String PROP_BUSINESSID = "BusinessId";
/** The property name constant equivalent to property, AccountNumber, of respective DataSource view. */
  public static final String PROP_ACCOUNTNUMBER = "AccountNumber";
/** The property name constant equivalent to property, CreditLimit, of respective DataSource view. */
  public static final String PROP_CREDITLIMIT = "CreditLimit";
/** The property name constant equivalent to property, Apr, of respective DataSource view. */
  public static final String PROP_APR = "Apr";
/** The property name constant equivalent to property, Active, of respective DataSource view. */
  public static final String PROP_ACTIVE = "Active";
/** The property name constant equivalent to property, DateCreated, of respective DataSource view. */
  public static final String PROP_DATECREATED = "DateCreated";
/** The property name constant equivalent to property, DateUpdated, of respective DataSource view. */
  public static final String PROP_DATEUPDATED = "DateUpdated";
/** The property name constant equivalent to property, UserId, of respective DataSource view. */
  public static final String PROP_USERID = "UserId";
/** The property name constant equivalent to property, IpCreated, of respective DataSource view. */
  public static final String PROP_IPCREATED = "IpCreated";
/** The property name constant equivalent to property, IpUpdated, of respective DataSource view. */
  public static final String PROP_IPUPDATED = "IpUpdated";
/** The property name constant equivalent to property, ExtAccountNumber, of respective DataSource view. */
  public static final String PROP_EXTACCOUNTNUMBER = "ExtAccountNumber";
    public static final String PROP_BALANCE = "Balance";

    /**
     * The property name constant equivalent to property, EntityTypeId, of
     * respective DataSource view.
     */
    public static final String PROP_ENTITYTYPEID = "EntityTypeId";
    /**
     * The property name constant equivalent to property, ServTypeId, of
     * respective DataSource view.
     */
    public static final String PROP_SERVTYPEID = "ServTypeId";
    public static final String PROP_ENTITYTYPENAME = "EntityTypeName";
    public static final String PROP_SERVTYPENAME = "ServTypeName";
    /**
     * The property name constant equivalent to property, Longname, of
     * respective DataSource view.
     */
    public static final String PROP_LONGNAME = "Longname";
    /**
     * The property name constant equivalent to property, Shortname, of
     * respective DataSource view.
     */
    public static final String PROP_SHORTNAME = "Shortname";
    /**
     * The property name constant equivalent to property, ContactFirstname, of
     * respective DataSource view.
     */
    public static final String PROP_CONTACTFIRSTNAME = "ContactFirstname";
    /**
     * The property name constant equivalent to property, ContactLastname, of
     * respective DataSource view.
     */
    public static final String PROP_CONTACTLASTNAME = "ContactLastname";
    /**
     * The property name constant equivalent to property, ContactPhone, of
     * respective DataSource view.
     */
    public static final String PROP_CONTACTPHONE = "ContactPhone";
    /**
     * The property name constant equivalent to property, ContactExt, of
     * respective DataSource view.
     */
    public static final String PROP_CONTACTEXT = "ContactExt";
    /**
     * The property name constant equivalent to property, ContactEmail, of
     * respective DataSource view.
     */
    public static final String PROP_CONTACTEMAIL = "ContactEmail";
    /**
     * The property name constant equivalent to property, TaxId, of respective
     * DataSource view.
     */
    public static final String PROP_TAXID = "TaxId";
    /**
     * The property name constant equivalent to property, Website, of respective
     * DataSource view.
     */
    public static final String PROP_WEBSITE = "Website";


    /**
     * The property name constant equivalent to property, AddrId, of respective
     * DataSource view.
     */
    public static final String PROP_ADDRID = "AddrId";
    /**
     * The property name constant equivalent to property, PersonId, of
     * respective DataSource view.
     */
    public static final String PROP_PERSONID = "PersonId";

    /**
     * The property name constant equivalent to property, Addr1, of respective
     * DataSource view.
     */
    public static final String PROP_ADDR1 = "Addr1";
    /**
     * The property name constant equivalent to property, Addr2, of respective
     * DataSource view.
     */
    public static final String PROP_ADDR2 = "Addr2";
    /**
     * The property name constant equivalent to property, Addr3, of respective
     * DataSource view.
     */
    public static final String PROP_ADDR3 = "Addr3";
    /**
     * The property name constant equivalent to property, Addr4, of respective
     * DataSource view.
     */
    public static final String PROP_ADDR4 = "Addr4";
    public static final String PROP_CITY = "City";
    public static final String PROP_STATE = "State";
    public static final String PROP_AREACODE = "AreaCode";
    public static final String PROP_COUNTY = "County";
    /**
     * The property name constant equivalent to property, Zip, of respective
     * DataSource view.
     */
    public static final String PROP_ZIP = "Zip";
    /**
     * The property name constant equivalent to property, Zipext, of respective
     * DataSource view.
     */
    public static final String PROP_ZIPEXT = "Zipext";
    /**
     * The property name constant equivalent to property, PhoneHome, of
     * respective DataSource view.
     */
    public static final String PROP_PHONEHOME = "PhoneHome";
    /**
     * The property name constant equivalent to property, PhoneWork, of
     * respective DataSource view.
     */
    public static final String PROP_PHONEWORK = "PhoneWork";
    /**
     * The property name constant equivalent to property, PhoneExt, of
     * respective DataSource view.
     */
    public static final String PROP_PHONEEXT = "PhoneExt";
    /**
     * The property name constant equivalent to property, PhoneMain, of
     * respective DataSource view.
     */
    public static final String PROP_PHONEMAIN = "PhoneMain";
    /**
     * The property name constant equivalent to property, PhoneCell, of
     * respective DataSource view.
     */
    public static final String PROP_PHONECELL = "PhoneCell";
    /**
     * The property name constant equivalent to property, PhoneFax, of
     * respective DataSource view.
     */
    public static final String PROP_PHONEFAX = "PhoneFax";
    /**
     * The property name constant equivalent to property, PhonePager, of
     * respective DataSource view.
     */
    public static final String PROP_PHONEPAGER = "PhonePager";


	/** The javabean property equivalent of database column creditor.creditor_id */
  private int creditorId;
/** The javabean property equivalent of database column creditor.acct_id */
  private int acctId;
/** The javabean property equivalent of database column creditor.creditor_type_id */
  private int creditorTypeId;

/** The javabean property equivalent of database column creditor.business_id */
  private int businessId;
/** The javabean property equivalent of database column creditor.account_number */
  private String accountNumber;
/** The javabean property equivalent of database column creditor.credit_limit */
  private double creditLimit;
    private double balance;
/** The javabean property equivalent of database column creditor.apr */
  private double apr;
/** The javabean property equivalent of database column creditor.active */
  private int active;
/** The javabean property equivalent of database column creditor.date_created */
  private java.util.Date dateCreated;
/** The javabean property equivalent of database column creditor.date_updated */
  private java.util.Date dateUpdated;
/** The javabean property equivalent of database column creditor.user_id */
  private String userId;
/** The javabean property equivalent of database column creditor.ip_created */
  private String ipCreated;
/** The javabean property equivalent of database column creditor.ip_updated */
  private String ipUpdated;
/** The javabean property equivalent of database column creditor.ext_account_number */
  private String extAccountNumber;
    /**
     * The javabean property equivalent of database column
     * business.entity_type_id
     */
    private int entityTypeId;
    /**
     * The javabean property equivalent of database column business.serv_type_id
     */
    private int servTypeId;
    private String entityTypeName;
    private String servTypeName;

    /** The javabean property equivalent of database column business.longname */
    private String longname;
    /** The javabean property equivalent of database column business.shortname */
    private String shortname;
    /**
     * The javabean property equivalent of database column
     * business.contact_firstname
     */
    private String contactFirstname;
    /**
     * The javabean property equivalent of database column
     * business.contact_lastname
     */
    private String contactLastname;
    /**
     * The javabean property equivalent of database column
     * business.contact_phone
     */
    private String contactPhone;
    /** The javabean property equivalent of database column business.contact_ext */
    private String contactExt;
    /**
     * The javabean property equivalent of database column
     * business.contact_email
     */
    private String contactEmail;
    /** The javabean property equivalent of database column business.tax_id */
    private String taxId;
    /** The javabean property equivalent of database column business.website */
    private String website;


    /** The javabean property equivalent of database column address.addr_id */
    private int addrId;
    /** The javabean property equivalent of database column address.person_id */
    private int personId;
    /** The javabean property equivalent of database column address.addr1 */
    private String addr1;
    /** The javabean property equivalent of database column address.addr2 */
    private String addr2;
    /** The javabean property equivalent of database column address.addr3 */
    private String addr3;
    /** The javabean property equivalent of database column address.addr4 */
    private String addr4;
    /** The javabean property equivalent of database column address.zip */
    private int zip;
    /** The javabean property equivalent of database column address.zipext */
    private int zipext;
    /** The javabean property equivalent of database column address.phone_home */
    private String phoneHome;
    /** The javabean property equivalent of database column address.phone_work */
    private String phoneWork;
    /** The javabean property equivalent of database column address.phone_ext */
    private String phoneExt;
    /** The javabean property equivalent of database column address.phone_main */
    private String phoneMain;
    /** The javabean property equivalent of database column address.phone_cell */
    private String phoneCell;
    /** The javabean property equivalent of database column address.phone_fax */
    private String phoneFax;
    /** The javabean property equivalent of database column address.phone_pager */
    private String phonePager;
    private String city;
    private String state;
    private String areaCode;
    private String county;
    private String creditorTypeDesc;

	// Getter/Setter Methods

/**
 * Default constructor.
 */
  public Creditor() throws SystemException {
	super();
 }

    /**
     * Stubbed initialization method designed to implemented by developer.
     */
    public void initBean() throws SystemException {
    }

/**
 * Sets the value of member variable creditorId
 */
  public void setCreditorId(int value) {
    this.creditorId = value;
  }
/**
 * Gets the value of member variable creditorId
 */
  public int getCreditorId() {
    return this.creditorId;
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
 * Sets the value of member variable creditorTypeId
 */
  public void setCreditorTypeId(int value) {
    this.creditorTypeId = value;
  }
/**
 * Gets the value of member variable creditorTypeId
 */
  public int getCreditorTypeId() {
    return this.creditorTypeId;
  }
/**
 * Sets the value of member variable businessId
 */
  public void setBusinessId(int value) {
    this.businessId = value;
  }
/**
 * Gets the value of member variable businessId
 */
  public int getBusinessId() {
    return this.businessId;
  }
/**
 * Sets the value of member variable accountNumber
 */
  public void setAccountNumber(String value) {
    this.accountNumber = value;
  }
/**
 * Gets the value of member variable accountNumber
 */
  public String getAccountNumber() {
    return this.accountNumber;
  }
/**
 * Sets the value of member variable creditLimit
 */
  public void setCreditLimit(double value) {
    this.creditLimit = value;
  }
/**
 * Gets the value of member variable creditLimit
 */
  public double getCreditLimit() {
    return this.creditLimit;
  }
/**
 * Sets the value of member variable apr
 */
  public void setApr(double value) {
    this.apr = value;
  }
/**
 * Gets the value of member variable apr
 */
  public double getApr() {
    return this.apr;
  }
/**
 * Sets the value of member variable active
 */
  public void setActive(int value) {
    this.active = value;
  }
/**
 * Gets the value of member variable active
 */
  public int getActive() {
    return this.active;
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
/**
 * Sets the value of member variable ipCreated
 */
  public void setIpCreated(String value) {
    this.ipCreated = value;
  }
/**
 * Gets the value of member variable ipCreated
 */
  public String getIpCreated() {
    return this.ipCreated;
  }
/**
 * Sets the value of member variable ipUpdated
 */
  public void setIpUpdated(String value) {
    this.ipUpdated = value;
  }
/**
 * Gets the value of member variable ipUpdated
 */
  public String getIpUpdated() {
    return this.ipUpdated;
  }
/**
 * Sets the value of member variable extAccountNumber
 */
  public void setExtAccountNumber(String value) {
    this.extAccountNumber = value;
  }
/**
 * Gets the value of member variable extAccountNumber
 */
  public String getExtAccountNumber() {
    return this.extAccountNumber;
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
   final Creditor other = (Creditor) obj; 
   if (EqualityAssistant.notEqual(this.creditorId, other.creditorId)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.acctId, other.acctId)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.creditorTypeId, other.creditorTypeId)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.businessId, other.businessId)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.accountNumber, other.accountNumber)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.creditLimit, other.creditLimit)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.apr, other.apr)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.active, other.active)) {
      return false;
   }
   if (EqualityAssistant.notEqual(this.extAccountNumber, other.extAccountNumber)) {
      return false;
   }
   return true; 
} 

@Override
public int hashCode() {
   return HashCodeAssistant.combineHashCodes(HashCodeAssistant.hashObject(this.creditorId),
               HashCodeAssistant.hashObject(this.acctId),
               HashCodeAssistant.hashObject(this.creditorTypeId),
               HashCodeAssistant.hashObject(this.businessId),
               HashCodeAssistant.hashObject(this.accountNumber),
               HashCodeAssistant.hashObject(this.creditLimit),
               HashCodeAssistant.hashObject(this.apr),
               HashCodeAssistant.hashObject(this.active),
               HashCodeAssistant.hashObject(this.extAccountNumber));
} 

@Override
public String toString() {
   return "Creditor [creditorId=" + creditorId + 
          ", acctId=" + acctId + 
          ", creditorTypeId=" + creditorTypeId + 
          ", businessId=" + businessId + 
          ", accountNumber=" + accountNumber + 
          ", creditLimit=" + creditLimit + 
          ", apr=" + apr + 
          ", active=" + active + 
          ", extAccountNumber=" + extAccountNumber  + "]";
}

    public int getEntityTypeId() {
        return entityTypeId;
    }

    public void setEntityTypeId(int entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public int getServTypeId() {
        return servTypeId;
    }

    public void setServTypeId(int servTypeId) {
        this.servTypeId = servTypeId;
    }

    public String getLongname() {
        return longname;
    }

    public void setLongname(String longname) {
        this.longname = longname;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getContactFirstname() {
        return contactFirstname;
    }

    public void setContactFirstname(String contactFirstname) {
        this.contactFirstname = contactFirstname;
    }

    public String getContactLastname() {
        return contactLastname;
    }

    public void setContactLastname(String contactLastname) {
        this.contactLastname = contactLastname;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactExt() {
        return contactExt;
    }

    public void setContactExt(String contactExt) {
        this.contactExt = contactExt;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getAddrId() {
        return addrId;
    }

    public void setAddrId(int addrId) {
        this.addrId = addrId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getAddr3() {
        return addr3;
    }

    public void setAddr3(String addr3) {
        this.addr3 = addr3;
    }

    public String getAddr4() {
        return addr4;
    }

    public void setAddr4(String addr4) {
        this.addr4 = addr4;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getZipext() {
        return zipext;
    }

    public void setZipext(int zipext) {
        this.zipext = zipext;
    }

    public String getPhoneHome() {
        return phoneHome;
    }

    public void setPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
    }

    public String getPhoneWork() {
        return phoneWork;
    }

    public void setPhoneWork(String phoneWork) {
        this.phoneWork = phoneWork;
    }

    public String getPhoneExt() {
        return phoneExt;
    }

    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
    }

    public String getPhoneMain() {
        return phoneMain;
    }

    public void setPhoneMain(String phoneMain) {
        this.phoneMain = phoneMain;
    }

    public String getPhoneCell() {
        return phoneCell;
    }

    public void setPhoneCell(String phoneCell) {
        this.phoneCell = phoneCell;
    }

    public String getPhoneFax() {
        return phoneFax;
    }

    public void setPhoneFax(String phoneFax) {
        this.phoneFax = phoneFax;
    }

    public String getPhonePager() {
        return phonePager;
    }

    public void setPhonePager(String phonePager) {
        this.phonePager = phonePager;
    }

    public String getEntityTypeName() {
        return entityTypeName;
    }

    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    public String getServTypeName() {
        return servTypeName;
    }

    public void setServTypeName(String servTypeName) {
        this.servTypeName = servTypeName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * @return the creditorTypeDesc
     */
    public String getCreditorTypeDesc() {
        return creditorTypeDesc;
    }

    /**
     * @param creditorTypeDesc
     *            the creditorTypeDesc to set
     */
    public void setCreditorTypeDesc(String creditorTypeDesc) {
        this.creditorTypeDesc = creditorTypeDesc;
    }

    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance
     *            the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

}