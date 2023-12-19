package com.entity;

import java.util.ArrayList;
import java.util.List;

import org.rmt2.jaxb.SalesOrderItemType;

/**
 * A factory that creates new SalesOrderItems related instances.
 * 
 * @author roy.terrell
 * 
 */
public class SalesOrderItemsFactory {

    /**
     * Create a new instance of a SalesOrderItems class.
     * 
     * @return {@link SalesOrderItems}
     */
    public static SalesOrderItems create() {
        try {
            return new SalesOrderItems();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 
     * @param item
     * @return
     */
    public static SalesOrderItems create(SalesOrderItemType item) {
        SalesOrderItems soi = SalesOrderItemsFactory.create();
        soi.setSoItemId(item.getSalesOrderItemId() != null ? item.getSalesOrderItemId().intValue() : 0);
        if (item.getItem() != null) {
            soi.setItemId(item.getItem().getItemId() != null ? item.getItem().getItemId().intValue() : 0);
            soi.setItemName(item.getItem().getDescription());
        }
        soi.setOrderQty(item.getOrderQty() != null ? item.getOrderQty().doubleValue() : 0);

        if (item.getItem() != null && item.getItem().getOverrideRetail() != null
                && item.getItem().getOverrideRetail().intValue() == 1) {
            soi.setInitUnitCost(item.getItem().getRetailPrice() != null ? item.getItem().getRetailPrice()
                    .doubleValue() : 0);
        }
        else {
            soi.setInitUnitCost(item.getUnitCost() != null && item.getMarkup() != null
                    ? (item.getUnitCost().doubleValue() * item.getMarkup().doubleValue()) : 0);
        }
        return soi;
    }

    /**
     * 
     * @param items
     * @return
     */
    public static List<SalesOrderItems> create(List<SalesOrderItemType> items) {
        if (items == null) {
            return null;
        }

        List<SalesOrderItems> list = new ArrayList<>();
        for (SalesOrderItemType item : items) {
            SalesOrderItems results = SalesOrderItemsFactory.create(item);
            list.add(results);
        }
        return list;
    }
}
