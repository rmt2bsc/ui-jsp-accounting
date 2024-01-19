package com.action.gl.codes;

import java.math.BigInteger;
import java.util.Date;

import org.rmt2.constants.ApiHeaderNames;
import org.rmt2.constants.ApiTransactionCodes;
import org.rmt2.jaxb.AccountingGeneralLedgerRequest;
import org.rmt2.jaxb.AccountingGeneralLedgerResponse;
import org.rmt2.jaxb.GlAccountcatgType;
import org.rmt2.jaxb.GlAccounttypeType;
import org.rmt2.jaxb.GlCriteriaGroup;
import org.rmt2.jaxb.GlCriteriaType;
import org.rmt2.jaxb.GlDetailGroup;
import org.rmt2.jaxb.HeaderType;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.util.HeaderTypeBuilder;
import org.rmt2.util.accounting.generalledger.GlAccountCategoryTypeBuilder;
import org.rmt2.util.accounting.generalledger.GlAccounttypeTypeBuilder;

import com.AccountingUIException;
import com.api.messaging.webservice.soap.client.SoapJaxbClientWrapper;
import com.entity.GeneralLedgerCriteria;
import com.entity.VwCategory;

/**
 * Help class for constructing and invoking SOAP calls pertaining to General
 * Ledger Account Category.
 * 
 * @author appdev
 *
 */
public class VwCategorySoapRequests {
    private static final String MSG = "SOAP invocation error occurred regarding server-side messaging for country operation";

    /**
     * SOAP call to fetch all accounting general ledger account types records.
     * 
     * @param parms
     *            {@link GeneralLedgerCriteria} Currently, should only provide
     *            selection criteria values for account type id and account
     *            category id.
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link AccountingGeneralLedgerResponse}
     * @throws AccountingUIException
     * 
     *             UI-37: Added loginId and sessionId parameters to method
     *             signature.
     */
    public static final AccountingGeneralLedgerResponse callGet(GeneralLedgerCriteria parms, String loginId, String sessionId)
            throws AccountingUIException {
        // Retrieve one or more accounting general ledger account types records
        // from the database
        ObjectFactory fact = new ObjectFactory();
        AccountingGeneralLedgerRequest req = fact.createAccountingGeneralLedgerRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_GL)
                .withTransaction(ApiTransactionCodes.GL_ACCOUNT_CATG_GET)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                // UI-37: Added login id and session id to the request
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        GlCriteriaType criteria = fact.createGlCriteriaType();

        GlAccountcatgType gact = fact.createGlAccountcatgType();

        if (parms.getAcctCatgId() > 0) {
            gact.setAcctCatgId(BigInteger.valueOf(parms.getAcctCatgId()));
        }
        if (parms.getAcctTypeId() > 0) {
            GlAccounttypeType gatt = GlAccounttypeTypeBuilder.Builder.create()
                    .withAcctTypeId(parms.getAcctTypeId())
                    .build();
            gact.setAcctType(gatt);
        }
        criteria.setAcctCatg(gact);

        GlCriteriaGroup criteriaGroup = fact.createGlCriteriaGroup();
        criteriaGroup.setGlCriteria(criteria);
        req.setCriteria(criteriaGroup);
        req.setHeader(head);

        AccountingGeneralLedgerResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AccountingUIException(VwCategorySoapRequests.MSG, e);
        }
    }

    /**
     * SOAP call to persist accounting general ledger account category record
     * changes.
     * 
     * @param parms
     *            {@link GeneralLedgerCriteria} Currently, should only provide
     *            selection criteria values for account type id and account
     *            category id.
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link AccountingGeneralLedgerResponse}
     * @throws AccountingUIException
     * 
     *             UI-37: Added loginId and sessionId parameters to method
     *             signature.
     */
    public static final AccountingGeneralLedgerResponse callSave(VwCategory parms, String loginId, String sessionId)
            throws AccountingUIException {
        // Retrieve one or more accounting general ledger account types records
        // from the database
        ObjectFactory fact = new ObjectFactory();
        AccountingGeneralLedgerRequest req = fact.createAccountingGeneralLedgerRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_GL)
                .withTransaction(ApiTransactionCodes.GL_ACCOUNT_CATG_UPDATE)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                // UI-37: Added login id and session id to the request
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        GlAccounttypeType gatt = GlAccounttypeTypeBuilder.Builder.create()
                .withAcctTypeId(parms.getAccttypeid())
                .build();
        GlAccountcatgType gact = GlAccountCategoryTypeBuilder.Builder.create()
                .withAcctCatgId(parms.getAcctcatid())
                .withAccountType(gatt)
                .withDescription(parms.getAcctcatgdescr())
                .build();

        GlDetailGroup detailGroup = fact.createGlDetailGroup();
        detailGroup.getAccountCategory().add(gact);
        req.setProfile(detailGroup);
        req.setHeader(head);

        AccountingGeneralLedgerResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AccountingUIException(VwCategorySoapRequests.MSG, e);
        }
    }

    /**
     * SOAP call to delete accounting general ledger account category record
     * changes.
     * 
     * @param parms
     *            {@link GeneralLedgerCriteria} Currently, should only provide
     *            selection criteria values for account type id and account
     *            category id.
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link AccountingGeneralLedgerResponse}
     * @throws AccountingUIException
     * 
     *             UI-37: Added loginId and sessionId parameters to method
     *             signature.
     */
    public static final AccountingGeneralLedgerResponse callDelete(VwCategory parms, String loginId, String sessionId)
            throws AccountingUIException {
        // Retrieve one or more accounting general ledger account types records
        // from the database
        ObjectFactory fact = new ObjectFactory();
        AccountingGeneralLedgerRequest req = fact.createAccountingGeneralLedgerRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_GL)
                .withTransaction(ApiTransactionCodes.GL_ACCOUNT_CATG_DELETE)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                // UI-37: Added login id and session id to the request
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        GlAccountcatgType gact = GlAccountCategoryTypeBuilder.Builder.create()
                .withAcctCatgId(parms.getAcctcatid())
                .build();

        GlDetailGroup detailGroup = fact.createGlDetailGroup();
        detailGroup.getAccountCategory().add(gact);
        req.setProfile(detailGroup);
        req.setHeader(head);

        AccountingGeneralLedgerResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AccountingUIException(VwCategorySoapRequests.MSG, e);
        }
    }
}
