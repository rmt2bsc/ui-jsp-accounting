package com.entity;

import java.util.ArrayList;
import java.util.List;

import org.rmt2.jaxb.SalesOrderType;

/**
 * A factory that creates new VwSalesOrderInvoice related instances.
 * 
 * @author roy.terrell
 * 
 */
public class VwSalesOrderInvoiceFactory {

    /**
     * Create a new instance of a VwSalesOrderInvoice class.
     * 
     * @return {@link VwSalesOrderInvoice}
     */
    public static VwSalesOrderInvoice create() {
        try {
            return new VwSalesOrderInvoice();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create a new instance of a VwSalesOrderInvoice class.
     * 
     * @param item
     *            {@link SalesOrderType}
     * @return {@link VwSalesOrderInvoice}
     */
    public static VwSalesOrderInvoice create(SalesOrderType item) {
        try {
            VwSalesOrderInvoice o = VwSalesOrderInvoiceFactory.create();
            o.setSalesOrderId(item.getSalesOrderId() != null ? item.getSalesOrderId().intValue() : 0);
            o.setCustomerId(item.getCustomerId() != null && item.getCustomerId() != null ? item.getCustomerId().intValue() : 0);
            o.setInvoiced(item.isInvoiced() ? 1 : 0);
            if (item.getStatus() != null) {
                o.setOrderStatusId(item.getStatus().getStatusId() != null ? item.getStatus().getStatusId().intValue() : 0);
                o.setOrderStatusDescr(item.getStatus().getDescription());
            }
            if (item.getEffectiveDate() != null) {
                o.setSalesOrderDate(item.getEffectiveDate().toGregorianCalendar().getTime());
            }
            o.setOrderTotal(item.getOrderTotal() != null ? item.getOrderTotal().doubleValue() : 0);
            o.setAccountNo(item.getCustomerAccountNo());
            o.setAcctId(0);
            o.setCreditLimit(0);
            o.setDescription(item.getCustomerName());
            if (item.getInvoiceDetails() != null) {
                o.setInvoiceId(item.getInvoiceDetails().getInvoiceId() != null ? item
                        .getInvoiceDetails().getInvoiceId().intValue() : 0);
                o.setInvoiceNo(item.getInvoiceDetails().getInvoiceNo());
                if (item.getInvoiceDetails().getInvoiceDate() != null) {
                    o.setInvoiceDate(item.getInvoiceDetails().getInvoiceDate().toGregorianCalendar().getTime());
                }
                if (item.getInvoiceDetails().getTransaction() != null
                        && item.getInvoiceDetails().getTransaction().getXactId() != null) {
                    o.setXactId(item.getInvoiceDetails().getTransaction().getXactId().intValue());
                }
            }

            return o;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Create a List of new VwSalesOrderInvoice instances.
     * 
     * @param items
     *            List of {@link SalesOrderType}
     * @return List<{@link VwSalesOrderInvoice}>} or null when <I>items</i> is
     *         found to be null or when an error occurs.
     */
    public static List<VwSalesOrderInvoice> create(List<SalesOrderType> items) {
        if (items == null) {
            return null;
        }

        try {
            List<VwSalesOrderInvoice> obj = new ArrayList<>();
            for (SalesOrderType item : items) {
                obj.add(VwSalesOrderInvoiceFactory.create(item));
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

}
