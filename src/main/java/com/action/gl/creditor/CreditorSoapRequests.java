package com.action.gl.creditor;

import java.math.BigInteger;
import java.util.Date;

import org.apache.log4j.Logger;
import org.rmt2.constants.ApiHeaderNames;
import org.rmt2.constants.ApiTransactionCodes;
import org.rmt2.jaxb.AccountingTransactionRequest;
import org.rmt2.jaxb.AccountingTransactionResponse;
import org.rmt2.jaxb.CreditorCriteriaType;
import org.rmt2.jaxb.HeaderType;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.jaxb.TransactionCriteriaGroup;
import org.rmt2.jaxb.XactCustomCriteriaTargetType;
import org.rmt2.util.HeaderTypeBuilder;

import com.AccountingUIException;
import com.api.messaging.webservice.soap.client.SoapJaxbClientWrapper;
import com.api.security.authentication.web.AuthenticationException;
import com.api.util.RMT2String2;
import com.entity.CreditorCriteria;

/**
 * Help class for constructing and invoking SOAP calls pertaining to the
 * Accounting Creditors.
 * 
 * @author Roy Terrell
 *
 */
public class CreditorSoapRequests {
    private static final Logger logger = Logger.getLogger(CreditorSoapRequests.class);
    private static final String MSG = "SOAP invocation error occurred regarding server-side messaging";

    /**
     * SOAP call to fetch general code(s).
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
                .withTransaction(ApiTransactionCodes.SUBSIDIARY_CREDITOR_GET)
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
            throw new AuthenticationException(CreditorSoapRequests.MSG, e);
        }
    }

}
