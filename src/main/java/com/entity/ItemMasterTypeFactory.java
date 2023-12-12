package com.entity;

import java.util.ArrayList;
import java.util.List;

import org.rmt2.jaxb.InventoryItemtypeType;



/**
 * A factory that creates new Inventory Item Master Type related instances.
 * 
 * @author roy.terrell
 * 
 */
public class ItemMasterTypeFactory {

    /**
     * Create a new instance of a ItemMasterType class.
     * 
     * @return {@link ItemMasterType}
     */
    public static ItemMasterType create() {
        try {
            return new ItemMasterType();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create a new instance of a ItemMasterType class.
     * 
     * @param item
     *            {@link InventoryItemtypeType}
     * @return {@link ItemMasterType}
     */
    public static ItemMasterType create(InventoryItemtypeType item) {
        try {
            ItemMasterType obj = ItemMasterTypeFactory.create();
            obj.setItemTypeId(item.getItemTypeId() == null ? 0 : item.getItemTypeId().intValue());
            obj.setDescription(item.getDescription());
            return obj;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Create a List of new ItemMasterType instances.
     * 
     * @param items
     *            List of {@link InventoryItemtypeType}
     * @return List<{@link ItemMasterType}>} or null when <I>items</i> is found
     *         to be null or when an error occurs.
     */
    public static List<ItemMasterType> create(List<InventoryItemtypeType> items) {
        if (items == null) {
            return null;
        }

        try {
            List<ItemMasterType> obj = new ArrayList<>();
            for (InventoryItemtypeType item : items) {
                obj.add(ItemMasterTypeFactory.create(item));
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

}
