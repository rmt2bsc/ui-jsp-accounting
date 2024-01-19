package com.entity;

import com.SystemException;
import com.api.persistence.db.orm.OrmBean;
import com.api.util.assistants.EqualityAssistant;
import com.api.util.assistants.HashCodeAssistant;

/**
 * Peer object that maps to the customer database table/view.
 *
 * @author auto generated.
 */
public class Customer extends OrmBean {

    // Property name constants that belong to respective DataSource,
    // CustomerView

    /**
     * The property name constant equivalent to property, CustomerId, of
     * respective DataSource view.
     */
    public static final String PROP_CUSTOMERID = "CustomerId";
    /**
     * The property name constant equivalent to property, AcctId, of respective
     * DataSource view.
     */
    public static final String PROP_ACCTID = "AcctId";
    /**
     * The property name constant equivalent to property, AccountNo, of
     * respective DataSource view.
     */
    public static final String PROP_ACCOUNTNO = "AccountNo";
    /**
     * The property name constant equivalent to property, PersonId, of
     * respective DataSource view.
     */
    public static final String PROP_PERSONID = "PersonId";
    /**
     * The property name constant equivalent to property, BusinessId, of
     * respective DataSource view.
     */
    public static final String PROP_BUSINESSID = "BusinessId";
    /**
     * The property name constant equivalent to property, CreditLimit, of
     * respective DataSource view.
     */
    public static final String PROP_CREDITLIMIT = "CreditLimit";
    /**
     * The property name constant equivalent to property, Description, of
     * respective DataSource view.
     */
    public static final String PROP_DESCRIPTION = "Description";
    /**
     * The property name constant equivalent to property, Active, of respective
     * DataSource view.
     */
    public static final String PROP_ACTIVE = "Active";
    /**
     * The property name constant equivalent to property, DateCreated, of
     * respective DataSource view.
     */
    public static final String PROP_DATECREATED = "DateCreated";
    /**
     * The property name constant equivalent to property, DateUpdated, of
     * respective DataSource view.
     */
    public static final String PROP_DATEUPDATED = "DateUpdated";
    /**
     * The property name constant equivalent to property, UserId, of respective
     * DataSource view.
     */
    public static final String PROP_USERID = "UserId";
    /**
     * The property name constant equivalent to property, IpCreated, of
     * respective DataSource view.
     */
    public static final String PROP_IPCREATED = "IpCreated";
    /**
     * The property name constant equivalent to property, IpUpdated, of
     * respective DataSource view.
     */
    public static final String PROP_IPUPDATED = "IpUpdated";

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

    public static final String PROP_BALANCE = "Balance";

    /** The javabean property equivalent of database column customer.customer_id */
    private int customerId;
    /** The javabean property equivalent of database column customer.acct_id */
    private int acctId;
    /** The javabean property equivalent of database column customer.account_no */
    private String accountNo;
    /** The javabean property equivalent of database column customer.business_id */
    private int businessId;
    /**
     * The javabean property equivalent of database column customer.credit_limit
     */
    private double creditLimit;
    private double balance;
    /** The javabean property equivalent of database column customer.description */
    private String description;
    /** The javabean property equivalent of database column customer.active */
    private int active;
    /**
     * The javabean property equivalent of database column customer.date_created
     */
    private java.util.Date dateCreated;
    /**
     * The javabean property equivalent of database column customer.date_updated
     */
    private java.util.Date dateUpdated;
    /** The javabean property equivalent of database column customer.user_id */
    private String userId;
    /** The javabean property equivalent of database column customer.ip_created */
    private String ipCreated;
    /** The javabean property equivalent of database column customer.ip_updated */
    private String ipUpdated;

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

    // Getter/Setter Methods

    /**
     * Default constructor.
     */
    public Customer() throws SystemException {
        super();
    }

    /**
     * Stubbed initialization method designed to implemented by developer.
     */
    public void initBean() throws SystemException {
    }

    /**
     * Sets the value of member variable customerId
     */
    public void setCustomerId(int value) {
        this.customerId = value;
    }

    /**
     * Gets the value of member variable customerId
     */
    public int getCustomerId() {
        return this.customerId;
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
     * Sets the value of member variable accountNo
     */
    public void setAccountNo(String value) {
        this.accountNo = value;
    }

    /**
     * Gets the value of member variable accountNo
     */
    public String getAccountNo() {
        return this.accountNo;
    }

    /**
     * Sets the value of member variable personId
     */
    public void setPersonId(int value) {
        this.personId = value;
    }

    /**
     * Gets the value of member variable personId
     */
    public int getPersonId() {
        return this.personId;
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
        final Customer other = (Customer) obj;
        if (EqualityAssistant.notEqual(this.customerId, other.customerId)) {
            return false;
        }
        if (EqualityAssistant.notEqual(this.acctId, other.acctId)) {
            return false;
        }
        if (EqualityAssistant.notEqual(this.accountNo, other.accountNo)) {
            return false;
        }
        if (EqualityAssistant.notEqual(this.personId, other.personId)) {
            return false;
        }
        if (EqualityAssistant.notEqual(this.businessId, other.businessId)) {
            return false;
        }
        if (EqualityAssistant.notEqual(this.creditLimit, other.creditLimit)) {
            return false;
        }
        if (EqualityAssistant.notEqual(this.description, other.description)) {
            return false;
        }
        if (EqualityAssistant.notEqual(this.active, other.active)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return HashCodeAssistant.combineHashCodes(HashCodeAssistant.hashObject(this.customerId),
                HashCodeAssistant.hashObject(this.acctId),
                HashCodeAssistant.hashObject(this.accountNo),
                HashCodeAssistant.hashObject(this.personId),
                HashCodeAssistant.hashObject(this.businessId),
                HashCodeAssistant.hashObject(this.creditLimit),
                HashCodeAssistant.hashObject(this.description),
                HashCodeAssistant.hashObject(this.active));
    }

    @Override
    public String toString() {
        return "Customer [customerId=" + customerId +
                ", acctId=" + acctId +
                ", accountNo=" + accountNo +
                ", personId=" + personId +
                ", businessId=" + businessId +
                ", creditLimit=" + creditLimit +
                ", description=" + description +
                ", active=" + active + "]";
    }

    /**
     * @return the entityTypeId
     */
    public int getEntityTypeId() {
        return entityTypeId;
    }

    /**
     * @param entityTypeId
     *            the entityTypeId to set
     */
    public void setEntityTypeId(int entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    /**
     * @return the servTypeId
     */
    public int getServTypeId() {
        return servTypeId;
    }

    /**
     * @param servTypeId
     *            the servTypeId to set
     */
    public void setServTypeId(int servTypeId) {
        this.servTypeId = servTypeId;
    }

    /**
     * @return the entityTypeName
     */
    public String getEntityTypeName() {
        return entityTypeName;
    }

    /**
     * @param entityTypeName
     *            the entityTypeName to set
     */
    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    /**
     * @return the servTypeName
     */
    public String getServTypeName() {
        return servTypeName;
    }

    /**
     * @param servTypeName
     *            the servTypeName to set
     */
    public void setServTypeName(String servTypeName) {
        this.servTypeName = servTypeName;
    }

    /**
     * @return the longname
     */
    public String getLongname() {
        return longname;
    }

    /**
     * @param longname
     *            the longname to set
     */
    public void setLongname(String longname) {
        this.longname = longname;
    }

    /**
     * @return the shortname
     */
    public String getShortname() {
        return shortname;
    }

    /**
     * @param shortname
     *            the shortname to set
     */
    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    /**
     * @return the contactFirstname
     */
    public String getContactFirstname() {
        return contactFirstname;
    }

    /**
     * @param contactFirstname
     *            the contactFirstname to set
     */
    public void setContactFirstname(String contactFirstname) {
        this.contactFirstname = contactFirstname;
    }

    /**
     * @return the contactLastname
     */
    public String getContactLastname() {
        return contactLastname;
    }

    /**
     * @param contactLastname
     *            the contactLastname to set
     */
    public void setContactLastname(String contactLastname) {
        this.contactLastname = contactLastname;
    }

    /**
     * @return the contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * @param contactPhone
     *            the contactPhone to set
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * @return the contactExt
     */
    public String getContactExt() {
        return contactExt;
    }

    /**
     * @param contactExt
     *            the contactExt to set
     */
    public void setContactExt(String contactExt) {
        this.contactExt = contactExt;
    }

    /**
     * @return the contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * @param contactEmail
     *            the contactEmail to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * @return the taxId
     */
    public String getTaxId() {
        return taxId;
    }

    /**
     * @param taxId
     *            the taxId to set
     */
    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    /**
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website
     *            the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return the addrId
     */
    public int getAddrId() {
        return addrId;
    }

    /**
     * @param addrId
     *            the addrId to set
     */
    public void setAddrId(int addrId) {
        this.addrId = addrId;
    }

    /**
     * @return the addr1
     */
    public String getAddr1() {
        return addr1;
    }

    /**
     * @param addr1
     *            the addr1 to set
     */
    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    /**
     * @return the addr2
     */
    public String getAddr2() {
        return addr2;
    }

    /**
     * @param addr2
     *            the addr2 to set
     */
    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    /**
     * @return the addr3
     */
    public String getAddr3() {
        return addr3;
    }

    /**
     * @param addr3
     *            the addr3 to set
     */
    public void setAddr3(String addr3) {
        this.addr3 = addr3;
    }

    /**
     * @return the addr4
     */
    public String getAddr4() {
        return addr4;
    }

    /**
     * @param addr4
     *            the addr4 to set
     */
    public void setAddr4(String addr4) {
        this.addr4 = addr4;
    }

    /**
     * @return the zip
     */
    public int getZip() {
        return zip;
    }

    /**
     * @param zip
     *            the zip to set
     */
    public void setZip(int zip) {
        this.zip = zip;
    }

    /**
     * @return the zipext
     */
    public int getZipext() {
        return zipext;
    }

    /**
     * @param zipext
     *            the zipext to set
     */
    public void setZipext(int zipext) {
        this.zipext = zipext;
    }

    /**
     * @return the phoneHome
     */
    public String getPhoneHome() {
        return phoneHome;
    }

    /**
     * @param phoneHome
     *            the phoneHome to set
     */
    public void setPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
    }

    /**
     * @return the phoneWork
     */
    public String getPhoneWork() {
        return phoneWork;
    }

    /**
     * @param phoneWork
     *            the phoneWork to set
     */
    public void setPhoneWork(String phoneWork) {
        this.phoneWork = phoneWork;
    }

    /**
     * @return the phoneExt
     */
    public String getPhoneExt() {
        return phoneExt;
    }

    /**
     * @param phoneExt
     *            the phoneExt to set
     */
    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
    }

    /**
     * @return the phoneMain
     */
    public String getPhoneMain() {
        return phoneMain;
    }

    /**
     * @param phoneMain
     *            the phoneMain to set
     */
    public void setPhoneMain(String phoneMain) {
        this.phoneMain = phoneMain;
    }

    /**
     * @return the phoneCell
     */
    public String getPhoneCell() {
        return phoneCell;
    }

    /**
     * @param phoneCell
     *            the phoneCell to set
     */
    public void setPhoneCell(String phoneCell) {
        this.phoneCell = phoneCell;
    }

    /**
     * @return the phoneFax
     */
    public String getPhoneFax() {
        return phoneFax;
    }

    /**
     * @param phoneFax
     *            the phoneFax to set
     */
    public void setPhoneFax(String phoneFax) {
        this.phoneFax = phoneFax;
    }

    /**
     * @return the phonePager
     */
    public String getPhonePager() {
        return phonePager;
    }

    /**
     * @param phonePager
     *            the phonePager to set
     */
    public void setPhonePager(String phonePager) {
        this.phonePager = phonePager;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the areaCode
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * @param areaCode
     *            the areaCode to set
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * @return the county
     */
    public String getCounty() {
        return county;
    }

    /**
     * @param county
     *            the county to set
     */
    public void setCounty(String county) {
        this.county = county;
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