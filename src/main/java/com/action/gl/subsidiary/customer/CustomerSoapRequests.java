package com.action.gl.subsidiary.customer;

import java.math.BigInteger;
import java.util.Date;

import org.apache.log4j.Logger;
import org.rmt2.constants.ApiHeaderNames;
import org.rmt2.constants.ApiTransactionCodes;
import org.rmt2.jaxb.AccountingTransactionRequest;
import org.rmt2.jaxb.AccountingTransactionResponse;
import org.rmt2.jaxb.AddressType;
import org.rmt2.jaxb.BusinessType;
import org.rmt2.jaxb.CodeDetailType;
import org.rmt2.jaxb.CustomerCriteriaType;
import org.rmt2.jaxb.CustomerType;
import org.rmt2.jaxb.HeaderType;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.jaxb.TransactionCriteriaGroup;
import org.rmt2.jaxb.TransactionDetailGroup;
import org.rmt2.jaxb.XactCustomCriteriaTargetType;
import org.rmt2.jaxb.ZipcodeType;
import org.rmt2.util.HeaderTypeBuilder;
import org.rmt2.util.accounting.subsidiary.CustomerTypeBuilder;
import org.rmt2.util.addressbook.AddressTypeBuilder;
import org.rmt2.util.addressbook.BusinessTypeBuilder;
import org.rmt2.util.addressbook.CodeDetailTypeBuilder;
import org.rmt2.util.addressbook.ZipcodeTypeBuilder;

import com.AccountingUIException;
import com.action.gl.subsidiary.SubsidiarySoapRequests;
import com.api.messaging.webservice.soap.client.SoapJaxbClientWrapper;
import com.api.security.authentication.web.AuthenticationException;
import com.api.util.RMT2String2;
import com.entity.Customer;
import com.entity.CustomerCriteria;

/**
 * Help class for constructing and invoking SOAP calls pertaining to the
 * Accounting Customers.
 * 
 * 
 * @author Roy Terrell
 *
 */
public class CustomerSoapRequests extends SubsidiarySoapRequests {
    private static final Logger logger = Logger.getLogger(CustomerSoapRequests.class);
    private static final String MSG = "SOAP invocation error occurred regarding server-side messaging";

    /**
     * SOAP call to fetch one or more customers.
     * 
     * @param parms
     *            {@link CustomerCriteria}
     *            <p>
     *            Currently, the following fields are recognized as selection
     *            criteria:
     *            <p>
     *            <table width="100%" border="1" cellspacing="0" cellpadding="0">
     *            <tr>
     *            <td><strong>Field</strong></td>
     *            <td><strong>Type</strong></td>
     *            <td><strong>Description</strong></td>
     *            <td><strong>Attribute Name</strong></td>
     *            </tr>
     *            <tr>
     *            <td>Customer id</td>
     *            <td>Numeric</td>
     *            <td>Creditor's unique identifier</td>
     *            <td>qry_CustomerId</td>
     *            </tr>
     *            <tr>
     *            <td>Account Number</td>
     *            <td>String</td>
     *            <td>Creditor's account number. Conducts incremental searches</td>
     *            <td>qry_AccountNo</td>
     *            </tr>
     *            <tr>
     *            <td>Business Id</td>
     *            <td>Numeric</td>
     *            <td>Business contact Id</td>
     *            <td>qry_BusinessId</td>
     *            </tr>
     *            <tr>
     *            <td>Tax Id</td>
     *            <td>String</td>
     *            <td>Creditor's Tax Id or EIN Number. Conducts incremental
     *            searches</td>
     *            <td>qry_TaxId</td>
     *            </tr>
     *            <tr>
     *            <td>Business Name</td>
     *            <td>String</td>
     *            <td>Name of creditor. Conducts incremental searches</td>
     *            <td>qry_Name</td>
     *            </tr>
     *            <tr>
     *            <td>Company Phone Number</td>
     *            <td>String</td>
     *            <td>The main phone number for creditor. Conducts exact match
     *            searches</td>
     *            <td>qry_PhoneMain</td>
     *            </tr>
     *            </table>
     * 
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link AccountingTransactionResponse}
     * @throws AccountingUIException
     */
    public static final AccountingTransactionResponse callGet(CustomerCriteria parms, String loginId, String sessionId)
            throws AccountingUIException {
        // Retrieve all code group records from the database
        ObjectFactory fact = new ObjectFactory();
        AccountingTransactionRequest req = fact.createAccountingTransactionRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_SUBSIDIARY)
                .withTransaction(ApiTransactionCodes.SUBSIDIARY_CUSTOMER_GET)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        CustomerCriteriaType criteria = fact.createCustomerCriteriaType();
        criteria.setTargetLevel(XactCustomCriteriaTargetType.FULL);

        AddressType addrType = AddressTypeBuilder.Builder.create().build();
        BusinessType busType = BusinessTypeBuilder.Builder.create().build();
        CustomerType custType = CustomerTypeBuilder.Builder.create().build();
        boolean useCustomerType = false;
        boolean useBusinessType = false;
        boolean useAddressType = false;

        if (parms != null) {
            if (RMT2String2.isNotEmpty(parms.getQry_CustomerId())) {
                custType.setCustomerId(BigInteger.valueOf(Integer.valueOf(parms.getQry_CustomerId())));
                useCustomerType = true;
            }
            if (RMT2String2.isNotEmpty(parms.getQry_AccountNo())) {
                custType.setAccountNo(parms.getQry_AccountNo());
                useCustomerType = true;
            }
            if (RMT2String2.isNotEmpty(parms.getQry_BusinessId())) {
                busType.setBusinessId(BigInteger.valueOf(Integer.valueOf(parms.getQry_BusinessId())));
                useBusinessType = true;
            }
            if (RMT2String2.isNotEmpty(parms.getQry_TaxId())) {
                busType.setTaxId(parms.getQry_TaxId());
                useBusinessType = true;
            }
            if (RMT2String2.isNotEmpty(parms.getQry_Name())) {
                busType.setLongName(parms.getQry_Name());
                useBusinessType = true;
            }
            if (RMT2String2.isNotEmpty(parms.getQry_PhoneMain())) {
                addrType.setPhoneMain(parms.getQry_PhoneMain());
                useAddressType = true;
                useBusinessType = true;
            }
        }

        // Determine which objects to use as selection criteria
        if (useCustomerType) {
            criteria.setCustomer(custType);
        }
        if (useAddressType) {
            busType.setAddress(addrType);
        }
        if (useBusinessType) {
            criteria.setContactDetails(busType);
        }
        TransactionCriteriaGroup criteriaGroup = fact.createTransactionCriteriaGroup();
        criteriaGroup.setCustomerCriteria(criteria);
        req.setCriteria(criteriaGroup);
        req.setHeader(head);

        AccountingTransactionResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AuthenticationException(CustomerSoapRequests.MSG, e);
        }
    }

    /**
     * SOAP call to fetch the transaction history of one or more creditors.
     * <p>
     * At this time, this method only supports querying transaction history for
     * only one creditor at a time.
     * 
     * @param parms
     *            {@link CreditorCriteria}
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link AccountingTransactionResponse}
     * @throws AccountingUIException
     */
    public static final AccountingTransactionResponse callGetHistory(CustomerCriteria parms, String loginId, String sessionId)
            throws AccountingUIException {
        ObjectFactory fact = new ObjectFactory();
        AccountingTransactionRequest req = fact.createAccountingTransactionRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_SUBSIDIARY)
                .withTransaction(ApiTransactionCodes.SUBSIDIARY_CUSTOMER_TRAN_HIST_GET)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        CustomerCriteriaType criteria = fact.createCustomerCriteriaType();
        criteria.setCustomer(fact.createCustomerType());
        if (parms != null) {
            if (RMT2String2.isNotEmpty(parms.getQry_CustomerId())) {
                criteria.getCustomer().setCustomerId(BigInteger.valueOf(Integer.valueOf(parms.getQry_CustomerId())));
            }
        }

        TransactionCriteriaGroup criteriaGroup = fact.createTransactionCriteriaGroup();
        criteriaGroup.setCustomerCriteria(criteria);
        req.setCriteria(criteriaGroup);
        req.setHeader(head);

        AccountingTransactionResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
    }

    /**
     * SOAP call to apply data changes to a customer's profile.
     * 
     * @param data
     *            {@link Customer}
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link AccountingTransactionResponse}
     * @throws AccountingUIException
     */
    public static final AccountingTransactionResponse callSave(Customer data, String loginId, String sessionId)
            throws AccountingUIException {
        // Retrieve all code group records from the database
        ObjectFactory fact = new ObjectFactory();
        AccountingTransactionRequest req = fact.createAccountingTransactionRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_SUBSIDIARY)
                .withTransaction(ApiTransactionCodes.SUBSIDIARY_CUSTOMER_UPDATE)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        CodeDetailType cdtEntity = CodeDetailTypeBuilder.Builder.create()
                .withCodeId(data.getEntityTypeId())
                .build();
        
        CodeDetailType cdtServType = CodeDetailTypeBuilder.Builder.create()
                .withCodeId(data.getServTypeId())
                .build();
        
        ZipcodeType zip = ZipcodeTypeBuilder.Builder.create()
                .withZipcode(data.getZip())
                .withCity(data.getCity())
                .withState(data.getState())
                .build();

        AddressType addr = AddressTypeBuilder.Builder.create()
                .withAddrId(data.getAddrId())
                .withAddressLine1(data.getAddr1())
                .withAddressLine2(data.getAddr2())
                .withAddressLine3(data.getAddr3())
                .withAddressLine4(data.getAddr4())
                .withPhoneMain(data.getPhoneMain())
                .withPhoneFax(data.getPhoneFax())
                .withZipcode(zip)
                .build();
                
        BusinessType busType = BusinessTypeBuilder.Builder.create()
                .withBusinessId(data.getBusinessId())
                .withLongname(data.getLongname())
                .withContactEmail(data.getContactEmail())
                .withContactFirstname(data.getContactFirstname())
                .withContactLastname(data.getContactLastname())
                .withContactPhone(data.getContactPhone())
                .withContactPhoneExt(data.getContactExt())
                .withShortname(data.getShortname())
                .withTaxId(data.getTaxId())
                .withWebsite(data.getWebsite())
                .withEntityType(cdtEntity)
                .withServiceType(cdtServType)
                .withAddress(addr)
                .build();
        
        CustomerType custType = CustomerTypeBuilder.Builder.create()
                .withCustomerId(data.getCustomerId())
                .withAcctId(data.getAcctId())
                .withBusinessType(busType)
                .withAccountNo(data.getAccountNo())
                .withAcctDescription(data.getDescription())
                .withCreditLimit(data.getCreditLimit())
                .withBalance(data.getBalance())
                .withActive(data.getActive())
                .build();
                
        TransactionDetailGroup profileGroup = fact.createTransactionDetailGroup();
        profileGroup.setCustomers(fact.createCustomerListType());
        profileGroup.getCustomers().getCustomer().add(custType);
        req.setHeader(head);
        req.setProfile(profileGroup);

        AccountingTransactionResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AuthenticationException(CustomerSoapRequests.MSG, e);
        }
    }
    
    
    /**
     * SOAP call to delete one or more customer's profile.
     * <p>
     * Currently, this implementation only deletes a single customer profile.
     * 
     * @param data
     *            {@link Customer}
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link AccountingTransactionResponse}
     * @throws AccountingUIException
     */
    public static final AccountingTransactionResponse callDelete(Customer data, String loginId, String sessionId)
            throws AccountingUIException {

        ObjectFactory fact = new ObjectFactory();
        AccountingTransactionRequest req = fact.createAccountingTransactionRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_SUBSIDIARY)
                .withTransaction(ApiTransactionCodes.SUBSIDIARY_CUSTOMER_DELETE)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        CustomerCriteriaType criteria = fact.createCustomerCriteriaType();
        criteria.setCustomer(fact.createCustomerType());
        criteria.getCustomer().setCustomerId(BigInteger.valueOf(data.getCustomerId()));
        
        TransactionCriteriaGroup criteriaGroup = fact.createTransactionCriteriaGroup();
        criteriaGroup.setCustomerCriteria(criteria);
        req.setCriteria(criteriaGroup);
        req.setHeader(head);

        AccountingTransactionResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AuthenticationException(CustomerSoapRequests.MSG, e);
        }
    }
}
