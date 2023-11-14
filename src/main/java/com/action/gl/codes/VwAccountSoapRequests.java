package com.action.gl.codes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.rmt2.constants.ApiHeaderNames;
import org.rmt2.constants.ApiTransactionCodes;
import org.rmt2.jaxb.AccountingGeneralLedgerRequest;
import org.rmt2.jaxb.AccountingGeneralLedgerResponse;
import org.rmt2.jaxb.GlAccountType;
import org.rmt2.jaxb.GlAccountcatgType;
import org.rmt2.jaxb.GlAccounttypeType;
import org.rmt2.jaxb.GlBalancetypeType;
import org.rmt2.jaxb.GlCriteriaGroup;
import org.rmt2.jaxb.GlCriteriaType;
import org.rmt2.jaxb.GlDetailGroup;
import org.rmt2.jaxb.HeaderType;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.util.HeaderTypeBuilder;
import org.rmt2.util.accounting.generalledger.GlAccountBalanceTypeBuilder;
import org.rmt2.util.accounting.generalledger.GlAccountCategoryTypeBuilder;
import org.rmt2.util.accounting.generalledger.GlAccountTypeBuilder;
import org.rmt2.util.accounting.generalledger.GlAccounttypeTypeBuilder;

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

    /**
     * SOAP call to persist accounting general ledger account data changes.
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
    public static final AccountingGeneralLedgerResponse callSave(VwAccount parms, String loginId, String sessionId)
            throws AccountingUIException {

        ObjectFactory fact = new ObjectFactory();
        AccountingGeneralLedgerRequest req = fact.createAccountingGeneralLedgerRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_GL)
                .withTransaction(ApiTransactionCodes.GL_ACCOUNT_UPDATE)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        GlBalancetypeType gbtt = GlAccountBalanceTypeBuilder.Builder.create()
                .withAcctBalanceTypeId(parms.getBalanceTypeId())
                .build();

        GlAccounttypeType gatt = GlAccounttypeTypeBuilder.Builder.create()
                .withAcctTypeId(parms.getAcctTypeId())
                .build();

        GlAccountcatgType gact = GlAccountCategoryTypeBuilder.Builder.create()
                .withAcctCatgId(parms.getAcctCatId())
                .withAccountType(gatt)
                .build();

        GlAccountType rec = GlAccountTypeBuilder.Builder.create()
                .withAcctId(BigInteger.valueOf(parms.getId()).intValue())
                .withAccountDescription(parms.getDescription())
                .withAccountName(parms.getName())
                .withAccountNumber(parms.getAcctNo())
                .withAcctSeq(parms.getAcctSeq())
                .withAccountCode(parms.getCode())
                .withAccountType(gatt)
                .withAccountCategory(gact)
                .withBalanceType(gbtt)
                .build();

        List<GlAccountType> acctList = new ArrayList<GlAccountType>();
        acctList.add(rec);

        GlDetailGroup details = fact.createGlDetailGroup();
        details.getAccount().addAll(acctList);
        req.setProfile(details);
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
