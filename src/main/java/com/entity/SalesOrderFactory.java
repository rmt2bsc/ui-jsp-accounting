package com.entity;

import java.util.ArrayList;
import java.util.List;

import org.rmt2.jaxb.SalesOrderType;

/**
 * A factory that creates new SalesOrder related instances.
 * 
 * @author roy.terrell
 * 
 */
public class SalesOrderFactory {

    /**
     * Create a new instance of a SalesOrder class.
     * 
     * @return {@link SalesOrder}
     */
    public static SalesOrder create() {
        try {
            return new SalesOrder();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create a new instance of a SalesOrder class.
     * 
     * @param item
     *            {@link SalesOrderType}
     * @return {@link SalesOrder}
     */
    public static SalesOrder create(SalesOrderType item) {
        try {
            SalesOrder o = SalesOrderFactory.create();
            o.setSoId(item.getSalesOrderId() != null ? item.getSalesOrderId().intValue() : 0);
            o.setCustomerId(item.getCustomerId() != null && item.getCustomerId() != null ? item.getCustomerId().intValue() : 0);
            o.setInvoiced(item.isInvoiced() ? 1 : 0);
            if (item.getStatus() != null) {
                o.setOrderStatusId(item.getStatus().getStatusId() != null ? item.getStatus().getStatusId().intValue() : 0);
                o.setOrderStatusDescr(item.getStatus().getDescription());
            }
            if (item.getEffectiveDate() != null) {
                o.setEffectiveDate(item.getEffectiveDate().toGregorianCalendar().getTime());
            }
            o.setOrderTotal(item.getOrderTotal() != null ? item.getOrderTotal().doubleValue() : 0);

            if (item.getInvoiceDetails() != null) {
                o.setInvoiced(item.getInvoiceDetails().getInvoiceId() != null ? 1 : 0);
            }

            return o;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Create a List of new SalesOrder instances.
     * 
     * @param items
     *            List of {@link SalesOrderType}
     * @return List<{@link SalesOrder}>} or null when <I>items</i> is found to
     *         be null or when an error occurs.
     */
    public static List<SalesOrder> create(List<SalesOrderType> items) {
        if (items == null) {
            return null;
        }

        try {
            List<SalesOrder> obj = new ArrayList<>();
            for (SalesOrderType item : items) {
                obj.add(SalesOrderFactory.create(item));
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

}
