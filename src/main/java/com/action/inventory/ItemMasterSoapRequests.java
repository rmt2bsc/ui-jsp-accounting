package com.action.inventory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.log4j.Logger;
import org.rmt2.constants.ApiHeaderNames;
import org.rmt2.constants.ApiTransactionCodes;
import org.rmt2.jaxb.CreditorType;
import org.rmt2.jaxb.HeaderType;
import org.rmt2.jaxb.InventoryCriteriaGroup;
import org.rmt2.jaxb.InventoryDetailGroup;
import org.rmt2.jaxb.InventoryItemStatusType;
import org.rmt2.jaxb.InventoryItemType;
import org.rmt2.jaxb.InventoryItemtypeType;
import org.rmt2.jaxb.InventoryRequest;
import org.rmt2.jaxb.InventoryResponse;
import org.rmt2.jaxb.InventoryStatusHistoryType;
import org.rmt2.jaxb.ItemCriteriaType;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.util.HeaderTypeBuilder;
import org.rmt2.util.accounting.inventory.InventoryItemStatusHistTypeBuilder;
import org.rmt2.util.accounting.inventory.InventoryItemStatusTypeBuilder;
import org.rmt2.util.accounting.inventory.InventoryItemTypeBuilder;
import org.rmt2.util.accounting.inventory.InventoryItemtypeTypeBuilder;
import org.rmt2.util.accounting.subsidiary.CreditorTypeBuilder;

import com.AccountingUIException;
import com.action.gl.subsidiary.SubsidiarySoapRequests;
import com.api.messaging.webservice.soap.client.SoapJaxbClientWrapper;
import com.api.util.RMT2String2;
import com.entity.ItemMasterCriteria;
import com.entity.VwItemMaster;

/**
 * Help class for constructing and invoking SOAP calls pertaining to the
 * Accounting Inventory Item Master.
 * 
 * 
 * @author Roy Terrell
 *
 */
public class ItemMasterSoapRequests extends SubsidiarySoapRequests {
    private static final Logger logger = Logger.getLogger(ItemMasterSoapRequests.class);
    private static final String MSG = "SOAP invocation error occurred regarding server-side messaging";

    /**
     * SOAP call to fetch one or more inventory items.
     * 
     * @param parms
     *            {@link ItemMasterCriteria}
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link InventoryResponse}
     * @throws AccountingUIException
     */
    public static final InventoryResponse callGet(ItemMasterCriteria parms, String loginId, String sessionId)
            throws AccountingUIException {
        // Retrieve all code group records from the database
        ObjectFactory fact = new ObjectFactory();
        InventoryRequest req = fact.createInventoryRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_INV)
                .withTransaction(ApiTransactionCodes.INVENTORY_ITEM_MASTER_GET)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        ItemCriteriaType criteria = fact.createItemCriteriaType();

        if (parms != null) {
            int intTemp = 0;
            if (RMT2String2.isNotEmpty(parms.getQry_Id())) {
                criteria.setItemId(BigInteger.valueOf(Integer.valueOf(parms.getQry_Id())));
            }
            if (RMT2String2.isNotEmpty(parms.getQry_Description())) {
                criteria.setItemName(parms.getQry_Description());
            }
            if (RMT2String2.isNotEmpty(parms.getQry_VendorId())) {
                intTemp = BigInteger.valueOf(Integer.valueOf(parms.getQry_VendorId())).intValue();
                CreditorType ct = CreditorTypeBuilder.Builder.create()
                        .withCreditorId(intTemp)
                        .build();
                criteria.setVendor(ct);
            }
            if (RMT2String2.isNotEmpty(parms.getQry_ItemTypeId())) {
                intTemp = BigInteger.valueOf(Integer.valueOf(parms.getQry_ItemTypeId())).intValue();
                InventoryItemtypeType iit = InventoryItemtypeTypeBuilder.Builder.create()
                        .withItemTypeId(intTemp)
                        .build();
                criteria.setItemType(iit);
            }
            if (RMT2String2.isNotEmpty(parms.getQry_VendorItemNo())) {
                criteria.setVendorItemNo(parms.getQry_VendorItemNo());
            }
            if (RMT2String2.isNotEmpty(parms.getQry_ItemSerialNo())) {
                criteria.setItemSerialNo(parms.getQry_ItemSerialNo());
            }
            if (RMT2String2.isNotEmpty(parms.getQry_UnitCost())) {
                criteria.setUnitCost(BigDecimal.valueOf(Integer.valueOf(parms.getQry_UnitCost())));
                criteria.setUnitCostPredicate(parms.getQryRelOp_UnitCost());
            }
            if (RMT2String2.isNotEmpty(parms.getQry_QtyOnHand())) {
                criteria.setQtyOnHand(BigInteger.valueOf(Integer.valueOf(parms.getQry_QtyOnHand())));
                criteria.setQtyOnHandPredicate(parms.getQryRelOp_QtyOnHand());
            }
            if (RMT2String2.isNotEmpty(parms.getQry_ItemStatusId())) {
                intTemp = BigInteger.valueOf(Integer.valueOf(parms.getQry_ItemStatusId())).intValue();
                InventoryItemStatusType iit = InventoryItemStatusTypeBuilder.Builder.create()
                        .withStatusId(intTemp)
                        .build();
                criteria.getStatus().add(iit);
            }
            if (RMT2String2.isNotEmpty(parms.getQry_Active())) {
                criteria.setActive(BigInteger.valueOf(Integer.valueOf(parms.getQry_Active())));
            }
        }

        InventoryCriteriaGroup criteriaGroup = fact.createInventoryCriteriaGroup();
        criteriaGroup.setItemCriteria(criteria);
        req.setCriteria(criteriaGroup);
        req.setHeader(head);

        InventoryResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AccountingUIException(ItemMasterSoapRequests.MSG, e);
        }
    }



    /**
     * SOAP call to apply data changes to an Inventory Item Master record.
     * 
     * @param data
     *            {@link VwItemMaster}
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link InventoryResponse}
     * @throws AccountingUIException
     */
    public static final InventoryResponse callSave(VwItemMaster data, String loginId, String sessionId)
            throws AccountingUIException {
        ObjectFactory fact = new ObjectFactory();
        InventoryRequest req = fact.createInventoryRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_INV)
                .withTransaction(ApiTransactionCodes.INVENTORY_ITEM_MASTER_UPDATE)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        InventoryItemtypeType iit = fact.createInventoryItemtypeType();
        iit.setItemTypeId(BigInteger.valueOf(data.getItemTypeId()));

        InventoryStatusHistoryType isht = InventoryItemStatusHistTypeBuilder.Builder.create()
                .withItemId(data.getId())
                .withStatusHistId(0)
                .withReason(data.getReason())
                .build();
                
        InventoryItemType item = InventoryItemTypeBuilder.Builder.create()
                .withItemId(data.getId())
                .withActive(true)
                .withItemName(data.getDescription())
                .withItemSerialNo(data.getItemSerialNo())
                .withMarkup(data.getMarkup())
                .withUnitCost(data.getUnitCost())
                .withQtyOnHand(data.getQtyOnHand())
                .withVendorItemNo(data.getVendorItemNo())
                .withRetailPrice(data.getRetailPrice())
                .withOverrideRetail(data.getOverrideRetail())
                .withCreditorId(data.getVendorId())
                .withItemType(iit)
                .withStatusHistory(isht)
                .build();
        
        InventoryDetailGroup detailGroup = fact.createInventoryDetailGroup();
        detailGroup.getInvItem().add(item);
        req.setProfile(detailGroup);
        req.setHeader(head);

        InventoryResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AccountingUIException(ItemMasterSoapRequests.MSG, e);
        }
    }
    
    
    /**
     * SOAP call to delete one or more inventory item master profiles.
     * <p>
     * Currently, this implementation only deletes a single item master profile.
     * 
     * @param data
     *            {@link VwItemMaster}
     * @param loginId
     *            the id of logged in user
     * @param sessionId
     *            the web session id of the logged in user.
     * @return {@link InventoryResponse}
     * @throws AccountingUIException
     */
    public static final InventoryResponse callDelete(VwItemMaster data, String loginId, String sessionId)
            throws AccountingUIException {

        ObjectFactory fact = new ObjectFactory();
        InventoryRequest req = fact.createInventoryRequest();

        HeaderType head = HeaderTypeBuilder.Builder.create()
                .withApplication(ApiHeaderNames.APP_NAME_ACCOUNTING)
                .withModule(ApiTransactionCodes.MODULE_ACCOUNTING_INV)
                .withTransaction(ApiTransactionCodes.INVENTORY_ITEM_MASTER_DELETE)
                .withMessageMode(ApiHeaderNames.MESSAGE_MODE_REQUEST)
                .withDeliveryDate(new Date())
                .withRouting(ApiTransactionCodes.ROUTE_ACCOUNTING)
                .withDeliveryMode(ApiHeaderNames.DELIVERY_MODE_SYNC)
                .withUserId(loginId)
                .withSessionId(sessionId)
                .build();

        ItemCriteriaType criteria = fact.createItemCriteriaType();
        criteria.setItemId(BigInteger.valueOf(data.getId()));
        
        InventoryCriteriaGroup criteriaGroup = fact.createInventoryCriteriaGroup();
        criteriaGroup.setItemCriteria(criteria);
        req.setCriteria(criteriaGroup);
        req.setHeader(head);

        InventoryResponse response = null;
        try {
            response = SoapJaxbClientWrapper.callSoapRequest(req);
            return response;
        } catch (Exception e) {
            throw new AccountingUIException(ItemMasterSoapRequests.MSG, e);
        }
    }
}
