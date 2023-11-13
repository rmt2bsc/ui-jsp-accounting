package com.action.gl.codes;

import java.math.BigInteger;
import java.util.Date;

import org.rmt2.constants.ApiHeaderNames;
import org.rmt2.constants.ApiTransactionCodes;
import org.rmt2.jaxb.AccountingGeneralLedgerRequest;
import org.rmt2.jaxb.AccountingGeneralLedgerResponse;
import org.rmt2.jaxb.GlAccountcatgType;
import org.rmt2.jaxb.GlCriteriaGroup;
import org.rmt2.jaxb.GlCriteriaType;
import org.rmt2.jaxb.HeaderType;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.util.HeaderTypeBuilder;

import com.AccountingUIException;
import com.api.messaging.webservice.soap.client.SoapJaxbClientWrapper;
import com.entity.VwAccount;

/**
 * Help class for constructing and invoking SOAP calls pertaining to General
 * Ledger Accounts.
 * 
 * @author appdev
 *
 */
public class VwAccountSoapRequests {
    private static final String MSG = "SOAP invocation error occurred regarding server-side messaging for GL Account operation";

    /**
     * SOAP call to fetch all accounting general ledger account records.
     * 
     * @param parms
     *            {@link VwAccount}
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link AccountingGeneralLedgerResponse}
     * @throws AccountingUIException
     */
    public static final AccountingGeneralLedgerResponse callGet(VwAccount parms, String loginId, String sessionId)
            throws AccountingUIException {

        ObjectFactory fact = new ObjectFactory();
        AccountingGeneralLedgerRequest req = fact.createAccountingGeneralLedgerRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_GL)
                .withTransaction(ApiTransactionCodes.GL_ACCOUNT_GET)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        GlCriteriaType criteria = fact.createGlCriteriaType();
        GlAccountcatgType criteriaAcctCatgType = null;

        if (parms.getId() > 0) {
            criteria.setAcctId(BigInteger.valueOf(parms.getId()));
        }
        if (parms.getAcctCatId() > 0) {
            criteriaAcctCatgType = fact.createGlAccountcatgType();
            criteriaAcctCatgType.setAcctCatgId(BigInteger.valueOf(parms.getAcctCatId()));
        }

        if (criteriaAcctCatgType != null) {
            criteria.setAcctCatg(criteriaAcctCatgType);
        }

        GlCriteriaGroup criteriaGroup = fact.createGlCriteriaGroup();
        criteriaGroup.setGlCriteria(criteria);
        req.setCriteria(criteriaGroup);
        req.setHeader(head);

        AccountingGeneralLedgerResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AccountingUIException(VwAccountSoapRequests.MSG, e);
        }
    }


}
