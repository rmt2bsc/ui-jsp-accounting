package com.entity;

import java.util.ArrayList;
import java.util.List;

import org.rmt2.jaxb.GlAccounttypeType;

/**
 * A factory that creates new GlAccountTypes related instances.
 * 
 * @author roy.terrell
 * 
 */
public class GlAccountTypesFactory {

    /**
     * Create a new instance of a GlAccountTypes class.
     * 
     * @return {@link GlAccountTypes}
     */
    public static GlAccountTypes create() {
        try {
            return new GlAccountTypes();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create a new instance of a GlAccountTypes class.
     * 
     * @param item
     *            {@link GlAccounttypeType}
     * @return {@link GlAccountTypes}
     */
    public static GlAccountTypes create(GlAccounttypeType item) {
        try {
            GlAccountTypes o = GlAccountTypesFactory.create();
            o.setAcctTypeId(item.getAcctTypeId() == null ? 0 : item.getAcctTypeId().intValue());
            o.setDescription(item.getDescription());
            o.setAcctBaltypeId(item.getBalanceType() != null && item.getBalanceType().getAccountBaltypeId() != null ? item
                    .getBalanceType().getAccountBaltypeId().intValue() : 0);
            if (item.getTracking() != null) {
                o.setDateCreated(item.getTracking().getDateCreated().toGregorianCalendar().getTime());
                o.setDateUpdated(item.getTracking().getDateUpdated().toGregorianCalendar().getTime());
                o.setUserId(item.getTracking().getUserId());
            }
            return o;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Create a List of new GlAccountTypes instances.
     * 
     * @param items
     *            List of {@link GlAccounttypeType}
     * @return List<{@link GlAccountTypes}>} or null when <I>items</i> is found
     *         to be null or when an error occurs.
     */
    public static List<GlAccountTypes> create(List<GlAccounttypeType> items) {
        if (items == null) {
            return null;
        }

        try {
            List<GlAccountTypes> obj = new ArrayList<>();
            for (GlAccounttypeType item : items) {
                obj.add(GlAccountTypesFactory.create(item));
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

}
