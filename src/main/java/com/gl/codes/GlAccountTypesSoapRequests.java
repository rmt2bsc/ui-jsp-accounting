package com.gl.codes;

import java.util.Date;

import org.rmt2.constants.ApiHeaderNames;
import org.rmt2.constants.ApiTransactionCodes;
import org.rmt2.jaxb.AccountingGeneralLedgerRequest;
import org.rmt2.jaxb.AccountingGeneralLedgerResponse;
import org.rmt2.jaxb.GlAccounttypeType;
import org.rmt2.jaxb.GlCriteriaGroup;
import org.rmt2.jaxb.GlCriteriaType;
import org.rmt2.jaxb.HeaderType;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.util.HeaderTypeBuilder;

import com.AccountingUIException;
import com.api.messaging.webservice.soap.client.SoapJaxbClientWrapper;

/**
 * Help class for constructing and invoking SOAP calls pertaining to General
 * Ledger Account Types.
 * 
 * @author appdev
 *
 */
public class GlAccountTypesSoapRequests {
    private static final String MSG = "SOAP invocation error occurred regarding server-side messaging for country operation";

    /**
     * SOAP call to fetch all accounting general ledger account types records.
     * 
     * @param parms
     *            {@link StateCriteria}
     * @return {@link AccountingGeneralLedgerResponse}
     * @throws AccountingUIException
     */
    public static final AccountingGeneralLedgerResponse callGet() throws AccountingUIException {
        // Retrieve one or more accounting general ledger account types records
        // from the database
        ObjectFactory fact = new ObjectFactory();
        AccountingGeneralLedgerRequest req = fact.createAccountingGeneralLedgerRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_GL)
                .withTransaction(ApiTransactionCodes.GL_ACCOUNT_TYPE_GET)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .build();

        GlCriteriaType criteria = fact.createGlCriteriaType();
        GlAccounttypeType criteriaAcctType = fact.createGlAccounttypeType();

        // TODO: If needed in the future, comment out one or more of these lines
        // to add extra account type criteria.

        // GlBalancetypeType gbtt = GlAccountBalanceTypeBuilder.Builder.create()
        // .withAcctBalanceTypeId(1).build();
        // GlAccounttypeType criteriaAcctType =
        // GlAccounttypeTypeBuilder.Builder.create()
        // .withAcctTypeId(111)
        // .withDescription("GL Account Type Description Test").build();
        // criteriaAcctType.setBalanceType(gbtt);

        criteria.setAcctType(criteriaAcctType);

        GlCriteriaGroup criteriaGroup = fact.createGlCriteriaGroup();
        criteriaGroup.setGlCriteria(criteria);
        req.setCriteria(criteriaGroup);
        req.setHeader(head);

        AccountingGeneralLedgerResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AccountingUIException(GlAccountTypesSoapRequests.MSG, e);
        }
    }


}
