package com.action.gl.subsidiary;

import java.math.BigInteger;
import java.util.Date;

import org.apache.log4j.Logger;
import org.rmt2.constants.ApiHeaderNames;
import org.rmt2.constants.ApiTransactionCodes;
import org.rmt2.jaxb.AddressBookRequest;
import org.rmt2.jaxb.AddressBookResponse;
import org.rmt2.jaxb.BusinessContactCriteria;
import org.rmt2.jaxb.ContactCriteriaGroup;
import org.rmt2.jaxb.HeaderType;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.util.HeaderTypeBuilder;

import com.AccountingConst;
import com.AccountingUIException;
import com.api.messaging.webservice.soap.client.SoapJaxbClientWrapper;

/**
 * Help class for constructing and invoking SOAP calls pertaining to the
 * Accounting Creditors.
 * 
 * @author Roy Terrell
 *
 */
public class SubsidiarySoapRequests {
    private static final Logger logger = Logger.getLogger(SubsidiarySoapRequests.class);

    /**
     * SOAP call to delete Business contacts.
     * 
     * @param contactId
     *            The business id the subsidiary used to identify the contact
     *            profile that is to be deleted.
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link AddressBookResponse}
     * @throws AccountingUIException
     */
    public static final AddressBookResponse callDeleteContactData(int contactId, String loginId, String sessionId)
            throws AccountingUIException {
        ObjectFactory fact = new ObjectFactory();
        AddressBookRequest req = fact.createAddressBookRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ADDRESSBOOK)
                .withModule(ApiTransactionCodes.MODULE_ADDRESSBOOK_PROFILE)
                .withTransaction(ApiTransactionCodes.CONTACTS_DELETE)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ADDRESSBOOK)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        ContactCriteriaGroup ccg = fact.createContactCriteriaGroup();
        BusinessContactCriteria criteria = fact.createBusinessContactCriteria();
        criteria.setContactId(BigInteger.valueOf(contactId));
        ccg.setBusinessCriteria(criteria);
        req.setCriteria(ccg);
        req.setHeader(head);

        AddressBookResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AccountingUIException(AccountingConst.MSG_SUBSIDIARY_CANNOT_DELETE_ADDRESS, e);
        }
    }
}
