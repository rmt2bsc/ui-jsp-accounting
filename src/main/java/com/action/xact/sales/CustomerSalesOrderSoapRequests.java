package com.action.xact.sales;

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
import org.rmt2.jaxb.SalesOrderCriteria;
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
import com.entity.SalesOrderInvoiceCriteria;

/**
 * Help class for constructing and invoking SOAP calls pertaining to the
 * Accounting Sales Orders.
 * 
 * 
 * @author Roy Terrell
 *
 */
public class CustomerSalesOrderSoapRequests extends SubsidiarySoapRequests {
    private static final Logger logger = Logger.getLogger(CustomerSalesOrderSoapRequests.class);
    private static final String MSG = "SOAP invocation error occurred regarding server-side messaging";

    /**
     * SOAP call to fetch one or more customers.
     * 
     * @param parms
     *            {@link SalesOrderInvoiceCriteria}
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link AccountingTransactionResponse}
     * @throws AccountingUIException
     */
    public static final AccountingTransactionResponse callGet(SalesOrderInvoiceCriteria parms, String loginId, String sessionId)
            throws AccountingUIException {
        // Retrieve all code group records from the database
        ObjectFactory fact = new ObjectFactory();
        AccountingTransactionRequest req = fact.createAccountingTransactionRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_XACT)
                .withTransaction(ApiTransactionCodes.ACCOUNTING_SALESORDER_GET_CUSTOMER_SPECIFIC)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        SalesOrderCriteria criteria = fact.createSalesOrderCriteria();
        criteria.setTargetLevel(XactCustomCriteriaTargetType.FULL);

        if (parms.isQry_FullQuery()) {
            criteria.setTargetLevel(XactCustomCriteriaTargetType.FULL);
        }
        else {
            criteria.setTargetLevel(XactCustomCriteriaTargetType.HEADER);
        }

        if (parms != null) {
            if (RMT2String2.isNotEmpty(parms.getQry_CustomerId())) {
                // This is the only property of SalesORderCriteria that is
                // required.
                criteria.setCustomerId(BigInteger.valueOf(Integer.valueOf(parms.getQry_CustomerId())));
            }
            if (RMT2String2.isNotEmpty(parms.getQry_SalesOrderId())) {
                criteria.setSalesOrderId(BigInteger.valueOf(Integer.valueOf(parms.getQry_SalesOrderId())));
            }
            if (RMT2String2.isNotEmpty(parms.getQry_XactId())) {
                criteria.setXactId(Integer.valueOf(parms.getQry_XactId()));
            }
        }
        TransactionCriteriaGroup criteriaGroup = fact.createTransactionCriteriaGroup();
        criteriaGroup.setSalesCriteria(criteria);
        req.setCriteria(criteriaGroup);
        req.setHeader(head);

        AccountingTransactionResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AuthenticationException(CustomerSalesOrderSoapRequests.MSG, e);
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
            throw new AuthenticationException(CustomerSalesOrderSoapRequests.MSG, e);
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
            throw new AuthenticationException(CustomerSalesOrderSoapRequests.MSG, e);
        }
    }
}
