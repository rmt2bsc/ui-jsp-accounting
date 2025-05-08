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
        if (item.getItem() != null) {
            if (item.getItem().getOverrideRetail() != null && item.getItem().getOverrideRetail().intValue() == 1
                    && item.getItem().getItemType() != null & item.getItem().getItemType().getItemTypeId() != null
                    && item.getItem().getItemType().getItemTypeId().intValue() == 2) {
                soi.setRetailPrice(item.getItem().getRetailPrice() != null ? item.getItem().getRetailPrice()
                        .doubleValue() : 0);
            }
            else {
                soi.setRetailPrice(item.getUnitCost() != null && item.getMarkup() != null
                        ? (item.getUnitCost().doubleValue() * item.getMarkup().doubleValue()) : 0);
            }

            if (item.getUnitCost() != null) {
                soi.setInitUnitCost(item.getUnitCost().doubleValue());
            }
            if (item.getItem().getMarkup() != null) {
                soi.setInitMarkup(item.getItem().getMarkup().doubleValue());
            }
            if (item.getItem().getQtyOnHand() != null) {
                soi.setQtyOnHand(item.getItem().getQtyOnHand().intValue());
            }
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
