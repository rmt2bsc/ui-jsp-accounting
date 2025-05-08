package com.action.gl.subsidiary;

import java.math.BigInteger;
import java.util.Date;

import org.apache.log4j.Logger;
import org.rmt2.constants.ApiHeaderNames;
import org.rmt2.constants.ApiTransactionCodes;
import org.rmt2.jaxb.HeaderType;
import org.rmt2.jaxb.LookupCodeCriteriaType;
import org.rmt2.jaxb.LookupCodesRequest;
import org.rmt2.jaxb.LookupCodesResponse;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.util.HeaderTypeBuilder;

import com.AccountingUIException;
import com.api.messaging.webservice.soap.client.SoapJaxbClientWrapper;
import com.api.security.authentication.web.AuthenticationException;
import com.entity.GeneralCodes;

/**
 * Help class for constructing and invoking SOAP calls pertaining to the
 * Addressbook general codes.
 * 
 * @author Roy Terrell
 *
 */
public class CodeSoapRequests {
    private static final Logger logger = Logger.getLogger(CodeSoapRequests.class);
    private static final String MSG = "SOAP invocation error occurred regarding server-side messaging";

    /**
     * SOAP call to fetch general code(s).
     * 
     * @param parms
     *            {@link GeneralCodes}
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link LookupCodesResponse}
     * @throws AddressbookUIException
     * 
     *             UI-37: Added loginId and sessionId parameters to method
     *             signature.
     */
    public static final LookupCodesResponse callGet(GeneralCodes parms, String loginId, String sessionId)
            throws AccountingUIException {
        // Retrieve all code group records from the database
        ObjectFactory fact = new ObjectFactory();
        LookupCodesRequest req = fact.createLookupCodesRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ADDRESSBOOK)
                .withModule(ApiTransactionCodes.MODULE_ADDRESSBOOK_LOOKUP)
                .withTransaction(ApiTransactionCodes.LOOKUP_CODE_GET)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ADDRESSBOOK)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                // UI-37: Added login id and session id to the request
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        LookupCodeCriteriaType criteria = fact.createLookupCodeCriteriaType();
        criteria.setAll(true);
        if (parms != null) {
            if (parms.getCodeId() > 0) {
                criteria.setCode(BigInteger.valueOf(parms.getCodeId()));
            }
            if (parms.getCodeGrpId() > 0) {
                criteria.setGroup(BigInteger.valueOf(parms.getCodeGrpId()));
            }
        }
        req.setCriteria(criteria);
        req.setHeader(head);

        LookupCodesResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AuthenticationException(CodeSoapRequests.MSG, e);
        }
    }
}
