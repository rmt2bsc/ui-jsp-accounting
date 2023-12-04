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
import org.rmt2.jaxb.CreditorCriteriaType;
import org.rmt2.jaxb.CreditorType;
import org.rmt2.jaxb.CreditortypeType;
import org.rmt2.jaxb.HeaderType;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.jaxb.TransactionCriteriaGroup;
import org.rmt2.jaxb.TransactionDetailGroup;
import org.rmt2.jaxb.XactCustomCriteriaTargetType;
import org.rmt2.jaxb.ZipcodeType;
import org.rmt2.util.HeaderTypeBuilder;
import org.rmt2.util.accounting.subsidiary.CreditorTypeBuilder;
import org.rmt2.util.accounting.subsidiary.CreditortypeTypeBuilder;
import org.rmt2.util.addressbook.AddressTypeBuilder;
import org.rmt2.util.addressbook.BusinessTypeBuilder;
import org.rmt2.util.addressbook.CodeDetailTypeBuilder;
import org.rmt2.util.addressbook.ZipcodeTypeBuilder;

import com.AccountingUIException;
import com.action.gl.subsidiary.SubsidiarySoapRequests;
import com.api.messaging.webservice.soap.client.SoapJaxbClientWrapper;
import com.api.security.authentication.web.AuthenticationException;
import com.api.util.RMT2String2;
import com.entity.Creditor;
import com.entity.CreditorCriteria;

/**
 * Help class for constructing and invoking SOAP calls pertaining to the
 * Accounting Customers.
 * 
 * @author Roy Terrell
 *
 */
public class CustomerSoapRequests extends SubsidiarySoapRequests {
    private static final Logger logger = Logger.getLogger(CustomerSoapRequests.class);
    private static final String MSG = "SOAP invocation error occurred regarding server-side messaging";

    /**
     * SOAP call to fetch one or more creditors.
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
    public static final AccountingTransactionResponse callGet(CreditorCriteria parms, String loginId, String sessionId)
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

        CreditorCriteriaType criteria = fact.createCreditorCriteriaType();
        criteria.setTargetLevel(XactCustomCriteriaTargetType.FULL);
        if (parms != null) {
            if (RMT2String2.isNotEmpty(parms.getQry_CreditorTypeId())) {
                criteria.setCreditorTypeId(BigInteger.valueOf(Integer.valueOf(parms.getQry_CreditorTypeId())));
            }
            if (RMT2String2.isNotEmpty(parms.getQry_CreditorId())) {
                criteria.setCreditorId(BigInteger.valueOf(Integer.valueOf(parms.getQry_CreditorId())));
            }
            if (RMT2String2.isNotEmpty(parms.getQry_AccountNo())) {
                criteria.setAccountNo(parms.getQry_AccountNo());
            }
            if (RMT2String2.isNotEmpty(parms.getQry_Name())) {
                criteria.setBusinessName(parms.getQry_Name());
            }
        }

        TransactionCriteriaGroup criteriaGroup = fact.createTransactionCriteriaGroup();
        criteriaGroup.setCreditorCriteria(criteria);
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
    public static final AccountingTransactionResponse callGetHistory(CreditorCriteria parms, String loginId, String sessionId)
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

        CreditorCriteriaType criteria = fact.createCreditorCriteriaType();
        if (parms != null) {
            if (RMT2String2.isNotEmpty(parms.getQry_CreditorId())) {
                criteria.setCreditorId(BigInteger.valueOf(Integer.valueOf(parms.getQry_CreditorId())));
            }
        }

        TransactionCriteriaGroup criteriaGroup = fact.createTransactionCriteriaGroup();
        criteriaGroup.setCreditorCriteria(criteria);
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
     * SOAP call to apply data changes to a creditor's profile.
     * 
     * @param data
     *            {@link Creditor}
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link AccountingTransactionResponse}
     * @throws AccountingUIException
     */
    public static final AccountingTransactionResponse callSave(Creditor data, String loginId, String sessionId)
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
        
        CreditortypeType creditorType = CreditortypeTypeBuilder.Builder.create()
                .withCreditorTypeId(data.getCreditorTypeId())
                .build();
      
        CreditorType creditor = CreditorTypeBuilder.Builder.create()
                .withCreditorId(data.getCreditorId())
                .withAcctId(data.getAcctId())
                .withBusinessType(busType)
                .withCreditorytypeType(creditorType)
                .withAccountNo(data.getAccountNumber())
                .withExtAccountNo(data.getExtAccountNumber())
                .withApr(data.getApr())
                .withCreditLimit(data.getCreditLimit())
                .withBalance(data.getBalance())
                .withActive(data.getActive())
                .build();
                
        TransactionDetailGroup profileGroup = fact.createTransactionDetailGroup();
        profileGroup.setCreditors(fact.createCreditorListType());
        profileGroup.getCreditors().getCreditor().add(creditor);
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
     * SOAP call to delete one or more creditor's profile.
     * <p>
     * Currently, this implementation only deletes a single creditor profile.
     * 
     * @param data
     *            {@link Creditor}
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link AccountingTransactionResponse}
     * @throws AccountingUIException
     */
    public static final AccountingTransactionResponse callDelete(Creditor data, String loginId, String sessionId)
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

        CreditorCriteriaType criteria = fact.createCreditorCriteriaType();
        criteria.setCreditorId(BigInteger.valueOf(data.getCreditorId()));
        
        TransactionCriteriaGroup criteriaGroup = fact.createTransactionCriteriaGroup();
        criteriaGroup.setCreditorCriteria(criteria);
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
