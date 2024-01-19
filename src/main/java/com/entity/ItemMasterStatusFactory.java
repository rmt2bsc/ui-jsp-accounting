package com.entity;

import java.util.ArrayList;
import java.util.List;

import org.rmt2.jaxb.InventoryItemStatusType;



/**
 * A factory that creates new Inventory Item Master Status related instances.
 * 
 * @author roy.terrell
 * 
 */
public class ItemMasterStatusFactory {

    /**
     * Create a new instance of a ItemMasterStatus class.
     * 
     * @return {@link ItemMasterStatus}
     */
    public static ItemMasterStatus create() {
        try {
            return new ItemMasterStatus();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create a new instance of a ItemMasterStatus class.
     * 
     * @param item
     *            {@link InventoryItemStatusType}
     * @return {@link ItemMasterStatus}
     */
    public static ItemMasterStatus create(InventoryItemStatusType item) {
        try {
            ItemMasterStatus obj = ItemMasterStatusFactory.create();
            obj.setItemStatusId(item.getItemStatusId() == null ? 0 : item.getItemStatusId().intValue());
            obj.setDescription(item.getDescription());
            return obj;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Create a List of new ItemMasterStatus instances.
     * 
     * @param items
     *            List of {@link InventoryItemStatusType}
     * @return List<{@link ItemMasterStatus}>} or null when <I>items</i> is
     *         found to be null or when an error occurs.
     */
    public static List<ItemMasterStatus> create(List<InventoryItemStatusType> items) {
        if (items == null) {
            return null;
        }

        try {
            List<ItemMasterStatus> obj = new ArrayList<>();
            for (InventoryItemStatusType item : items) {
                obj.add(ItemMasterStatusFactory.create(item));
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

}
