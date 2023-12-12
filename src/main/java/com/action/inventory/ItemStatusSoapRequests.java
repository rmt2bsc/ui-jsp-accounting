package com.action.inventory;

import java.util.Date;

import org.apache.log4j.Logger;
import org.rmt2.constants.ApiHeaderNames;
import org.rmt2.constants.ApiTransactionCodes;
import org.rmt2.jaxb.HeaderType;
import org.rmt2.jaxb.InventoryCriteriaGroup;
import org.rmt2.jaxb.InventoryRequest;
import org.rmt2.jaxb.InventoryResponse;
import org.rmt2.jaxb.ItemStatusCriteriaType;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.util.HeaderTypeBuilder;

import com.AccountingUIException;
import com.action.gl.subsidiary.SubsidiarySoapRequests;
import com.api.messaging.webservice.soap.client.SoapJaxbClientWrapper;

/**
 * Help class for constructing and invoking SOAP calls pertaining to the
 * Accounting Inventory Item Master Statuses.
 * 
 * 
 * @author Roy Terrell
 *
 */
public class ItemStatusSoapRequests extends SubsidiarySoapRequests {
    private static final Logger logger = Logger.getLogger(ItemStatusSoapRequests.class);
    private static final String MSG = "SOAP invocation error occurred regarding server-side messaging";

    /**
     * SOAP call to fetch all inventory item statuses.
     * 
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link InventoryResponse}
     * @throws AccountingUIException
     */
    public static final InventoryResponse callGet(String loginId, String sessionId) throws AccountingUIException {
        ObjectFactory fact = new ObjectFactory();
        InventoryRequest req = fact.createInventoryRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_INV)
                .withTransaction(ApiTransactionCodes.INVENTORY_ITEM_STATUS_GET)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        ItemStatusCriteriaType criteria = fact.createItemStatusCriteriaType();
        InventoryCriteriaGroup criteriaGroup = fact.createInventoryCriteriaGroup();
        criteriaGroup.setItemStatusCriteria(criteria);
        req.setCriteria(criteriaGroup);
        req.setHeader(head);

        InventoryResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AccountingUIException(ItemStatusSoapRequests.MSG, e);
        }
    }

}
