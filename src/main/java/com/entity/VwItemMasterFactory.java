package com.entity;

import java.util.ArrayList;
import java.util.List;

import org.rmt2.jaxb.InventoryItemType;

/**
 * A factory that creates new VwItemMaster related instances.
 * 
 * @author roy.terrell
 * 
 */
public class VwItemMasterFactory {

    /**
     * Create a new instance of a VwItemMaster class.
     * 
     * @return {@link VwItemMaster}
     */
    public static VwItemMaster create() {
        try {
            return new VwItemMaster();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create a new instance of a VwItemMaster class.
     * 
     * @param item
     *            {@link InventoryItemType}
     * @return {@link VwItemMaster}
     */
    public static VwItemMaster create(InventoryItemType item) {
        try {
            VwItemMaster o = VwItemMasterFactory.create();
            o.setId(item.getItemId() != null ? item.getItemId().intValue() : 0);
            o.setVendorId(item.getCreditor() != null && item.getCreditor().getCreditorId() != null ? item.getCreditor()
                    .getCreditorId().intValue() : 0);
            if (item.getItemType() != null) {
                o.setItemTypeId(item.getItemType().getItemTypeId() != null ? item.getItemType().getItemTypeId().intValue() : 0);
                o.setItemTypeDescription(item.getItemType().getDescription());
            }
            if (item.getItemStatus() != null) {
                o.setItemStatusId(item.getItemStatus().getItemStatusId() != null ? item.getItemStatus().getItemStatusId()
                        .intValue() : 0);
                o.setItemStatus(item.getItemStatus().getDescription());
            }

            o.setDescription(item.getDescription());
            o.setVendorItemNo(item.getVendorItemNo());
            o.setItemSerialNo(item.getItemSerialNo());
            o.setQtyOnHand(item.getQtyOnHand() != null ? item.getQtyOnHand().intValue() : 0);
            o.setUnitCost(item.getUnitCost() != null ? item.getUnitCost().doubleValue() : 0);
            o.setMarkup(item.getMarkup() != null ? item.getMarkup().doubleValue() : 0);
            o.setRetailPrice(item.getRetailPrice() != null ? item.getRetailPrice().doubleValue() : 0);
            o.setOverrideRetail(item.getOverrideRetail() != null ? item.getOverrideRetail().intValue() : 0);
            o.setActive(item.getActive() != null ? item.getActive().intValue() : 0);

            if (item.getTracking() != null) {
                o.setUpdateUserid(item.getTracking().getUserId());
            }
            return o;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Create a List of new VwItemMaster instances.
     * 
     * @param items
     *            List of {@link InventoryItemType}
     * @return List<{@link VwItemMaster}>} or null when <I>items</i> is found to
     *         be null or when an error occurs.
     */
    public static List<VwItemMaster> create(List<InventoryItemType> items) {
        if (items == null) {
            return null;
        }

        try {
            List<VwItemMaster> obj = new ArrayList<>();
            for (InventoryItemType item : items) {
                obj.add(VwItemMasterFactory.create(item));
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

}
