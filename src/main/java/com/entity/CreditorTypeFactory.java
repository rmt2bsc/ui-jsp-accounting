package com.entity;

import java.util.ArrayList;
import java.util.List;

import org.rmt2.jaxb.CreditortypeType;

/**
 * A factory that creates new CreditorType related instances.
 * 
 * @author roy.terrell
 * 
 */
public class CreditorTypeFactory {

    /**
     * Create a new instance of a CreditorType class.
     * 
     * @return {@link CreditorType}
     */
    public static CreditorType create() {
        try {
            return new CreditorType();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create a new instance of a CreditorType class.
     * 
     * @param item
     *            {@link CreditortypeType}
     * @return {@link CreditorType}
     */
    public static CreditorType create(CreditortypeType item) {
        if (item == null) {
            return null;
        }
        try {
            CreditorType o = CreditorTypeFactory.create();

            o.setCreditorTypeId(item.getCreditorTypeId() == null ? 0 : item.getCreditorTypeId().intValue());
            o.setDescription(item.getDescription());
            if (item.getTracking() != null) {
                if (item.getTracking().getDateCreated() != null) {
                    o.setDateCreated(item.getTracking().getDateCreated().toGregorianCalendar().getTime());
                }
                if (item.getTracking().getDateUpdated() != null) {
                    o.setDateUpdated(item.getTracking().getDateUpdated().toGregorianCalendar().getTime());
                }
                o.setUserId(item.getTracking().getUserId());
            }
            return o;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Create a List of new CreditorType instances.
     * 
     * @param items
     *            List of {@link CreditortypeType}
     * @return List<{@link CreditorType}>} or null when <I>items</i> is found to
     *         be null or when an error occurs.
     */
    public static List<CreditorType> create(List<CreditortypeType> items) {
        if (items == null) {
            return null;
        }

        try {
            List<CreditorType> obj = new ArrayList<>();
            for (CreditortypeType item : items) {
                obj.add(CreditorTypeFactory.create(item));
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

}
