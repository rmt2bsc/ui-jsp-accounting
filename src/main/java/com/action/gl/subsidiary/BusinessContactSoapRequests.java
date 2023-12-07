package com.action.gl.subsidiary;

import java.math.BigInteger;
import java.util.Date;

import org.rmt2.constants.ApiHeaderNames;
import org.rmt2.constants.ApiTransactionCodes;
import org.rmt2.jaxb.AddressBookRequest;
import org.rmt2.jaxb.AddressBookResponse;
import org.rmt2.jaxb.AddressType;
import org.rmt2.jaxb.BusinessType;
import org.rmt2.jaxb.CodeDetailType;
import org.rmt2.jaxb.ContactDetailGroup;
import org.rmt2.jaxb.HeaderType;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.jaxb.ZipcodeType;
import org.rmt2.util.HeaderTypeBuilder;

import com.AccountingUIException;
import com.api.messaging.webservice.soap.client.SoapJaxbClientWrapper;
import com.entity.Creditor;
import com.entity.Customer;

/**
 * Help class for constructing and invoking SOAP calls pertaining to business
 * contacts
 * 
 * @author appdev
 *
 */
public class BusinessContactSoapRequests {
    private static final String MSG = "SOAP invocation error occurred regarding server-side messaging for business contact operation";


    /**
     * SOAP call to save the creditor's business contact data changes.
     * 
     * @param data
     *            {@link Creditor}
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link AddressBookResponse}
     * @throws AccountingUIException
     */
    public static final AddressBookResponse callSave(Creditor data, String loginId, String sessionId)
            throws AccountingUIException {
        ObjectFactory fact = new ObjectFactory();
        AddressBookRequest req = fact.createAddressBookRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ADDRESSBOOK)
                .withModule(ApiTransactionCodes.MODULE_ADDRESSBOOK_PROFILE)
                .withTransaction(ApiTransactionCodes.CONTACTS_UPDATE)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ADDRESSBOOK)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        ContactDetailGroup cdg = fact.createContactDetailGroup();

        CodeDetailType cdtEntityType = fact.createCodeDetailType();
        cdtEntityType.setCodeId(BigInteger.valueOf(data.getEntityTypeId()));
        
        CodeDetailType cdtServType = fact.createCodeDetailType();
        cdtServType.setCodeId(BigInteger.valueOf(data.getServTypeId()));
        
        BusinessType obj = fact.createBusinessType();
        obj.setBusinessId(BigInteger.valueOf(data.getBusinessId()));
        obj.setLongName(data.getLongname());
        obj.setShortName(data.getShortname());
        obj.setContactFirstname(data.getContactFirstname());
        obj.setContactLastname(data.getContactLastname());
        obj.setContactEmail(data.getContactEmail());
        obj.setContactPhone(data.getContactPhone());
        obj.setContactExt(data.getContactExt());
        obj.setTaxId(data.getTaxId());
        obj.setEntityType(cdtEntityType);
        obj.setServiceType(cdtServType);
        obj.setWebsite(data.getWebsite());

        AddressType addr = fact.createAddressType();
        addr.setAddrId(BigInteger.valueOf(data.getAddrId()));
        addr.setAddr1(data.getAddr1());
        addr.setAddr2(data.getAddr2());
        addr.setAddr3(data.getAddr3());
        addr.setAddr4(data.getAddr4());
        addr.setPhoneMain(data.getPhoneMain());
        addr.setPhoneFax(data.getPhoneFax());

        ZipcodeType zip = fact.createZipcodeType();
        zip.setZipcode(BigInteger.valueOf(data.getZip()));

        addr.setZip(zip);
        obj.setAddress(addr);

        cdg.getBusinessContacts().add(obj);

        req.setProfile(cdg);
        req.setHeader(head);

        AddressBookResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AccountingUIException(BusinessContactSoapRequests.MSG, e);
        }
    }

    /**
     * SOAP call to save the customer's business contact data changes.
     * 
     * @param data
     *            {@link Customer}
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link AddressBookResponse}
     * @throws AccountingUIException
     */
    public static final AddressBookResponse callSave(Customer data, String loginId, String sessionId)
            throws AccountingUIException {
        ObjectFactory fact = new ObjectFactory();
        AddressBookRequest req = fact.createAddressBookRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ADDRESSBOOK)
                .withModule(ApiTransactionCodes.MODULE_ADDRESSBOOK_PROFILE)
                .withTransaction(ApiTransactionCodes.CONTACTS_UPDATE)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ADDRESSBOOK)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        ContactDetailGroup cdg = fact.createContactDetailGroup();

        CodeDetailType cdtEntityType = fact.createCodeDetailType();
        cdtEntityType.setCodeId(BigInteger.valueOf(data.getEntityTypeId()));

        CodeDetailType cdtServType = fact.createCodeDetailType();
        cdtServType.setCodeId(BigInteger.valueOf(data.getServTypeId()));

        BusinessType obj = fact.createBusinessType();
        obj.setBusinessId(BigInteger.valueOf(data.getBusinessId()));
        obj.setLongName(data.getLongname());
        obj.setShortName(data.getShortname());
        obj.setContactFirstname(data.getContactFirstname());
        obj.setContactLastname(data.getContactLastname());
        obj.setContactEmail(data.getContactEmail());
        obj.setContactPhone(data.getContactPhone());
        obj.setContactExt(data.getContactExt());
        obj.setTaxId(data.getTaxId());
        obj.setEntityType(cdtEntityType);
        obj.setServiceType(cdtServType);
        obj.setWebsite(data.getWebsite());

        AddressType addr = fact.createAddressType();
        addr.setAddrId(BigInteger.valueOf(data.getAddrId()));
        addr.setAddr1(data.getAddr1());
        addr.setAddr2(data.getAddr2());
        addr.setAddr3(data.getAddr3());
        addr.setAddr4(data.getAddr4());
        addr.setPhoneMain(data.getPhoneMain());
        addr.setPhoneFax(data.getPhoneFax());

        ZipcodeType zip = fact.createZipcodeType();
        zip.setZipcode(BigInteger.valueOf(data.getZip()));

        addr.setZip(zip);
        obj.setAddress(addr);

        cdg.getBusinessContacts().add(obj);

        req.setProfile(cdg);
        req.setHeader(head);

        AddressBookResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AccountingUIException(BusinessContactSoapRequests.MSG, e);
        }
    }
}
