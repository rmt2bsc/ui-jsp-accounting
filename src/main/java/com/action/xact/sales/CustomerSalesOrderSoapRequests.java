package com.action.xact.sales;

import java.math.BigInteger;
import java.util.Date;

import org.apache.log4j.Logger;
import org.rmt2.constants.ApiHeaderNames;
import org.rmt2.constants.ApiTransactionCodes;
import org.rmt2.jaxb.AccountingTransactionRequest;
import org.rmt2.jaxb.AccountingTransactionResponse;
import org.rmt2.jaxb.CustomerCriteriaType;
import org.rmt2.jaxb.HeaderType;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.jaxb.SalesOrderCriteria;
import org.rmt2.jaxb.TransactionCriteriaGroup;
import org.rmt2.jaxb.TransactionDetailGroup;
import org.rmt2.jaxb.XactCustomCriteriaTargetType;
import org.rmt2.util.HeaderTypeBuilder;

import com.AccountingUIException;
import com.action.gl.subsidiary.SubsidiarySoapRequests;
import com.api.messaging.webservice.soap.client.SoapJaxbClientWrapper;
import com.api.security.authentication.web.AuthenticationException;
import com.api.util.RMT2String2;
import com.entity.Customer;
import com.entity.SalesOrderInvoiceCriteria;
import com.entity.VwSalesOrderInvoice;

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
     * SOAP call to apply data changes to a customer's sales order profile.
     * 
     * @param data
     *            {@link VwSalesOrderInvoice}
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link AccountingTransactionResponse}
     * @throws AccountingUIException
     */
    public static final AccountingTransactionResponse callSave(VwSalesOrderInvoice data, String loginId, String sessionId)
            throws AccountingUIException {
        // Retrieve all code group records from the database
        ObjectFactory fact = new ObjectFactory();
        AccountingTransactionRequest req = fact.createAccountingTransactionRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_SUBSIDIARY)
                .withTransaction(data.getSalesOrderId() > 0 ? ApiTransactionCodes.ACCOUNTING_SALESORDER_UPDATE
                                : ApiTransactionCodes.ACCOUNTING_SALESORDER_CREATE)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();


                
        TransactionDetailGroup profileGroup = fact.createTransactionDetailGroup();
        profileGroup.setCustomers(fact.createCustomerListType());
        // profileGroup.getCustomers().getCustomer().add(custType);
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
