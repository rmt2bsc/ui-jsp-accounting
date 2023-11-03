package com.entity;

import java.util.ArrayList;
import java.util.List;

import org.rmt2.jaxb.GlAccountcatgType;

/**
 * A factory that creates new VwCategory related instances.
 * 
 * @author roy.terrell
 * 
 */
public class VwCategoryFactory {

    /**
     * Create a new instance of a VwCategory class.
     * 
     * @return {@link VwCategory}
     */
    public static VwCategory create() {
        try {
            return new VwCategory();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create a new instance of a VwCategory class.
     * 
     * @param item
     *            {@link GlAccountcatgType}
     * @return {@link VwCategory}
     */
    public static VwCategory create(GlAccountcatgType item) {
        try {
            VwCategory o = VwCategoryFactory.create();
            o.setAcctcatid(item.getAcctCatgId() != null ? item.getAcctCatgId().intValue() : 0);
            o.setAcctcatgdescr(item.getDescription());
            if (item.getAcctType() != null) {
                o.setAccttypeid(item.getAcctType().getAcctTypeId() != null ? item.getAcctType().getAcctTypeId().intValue() : 0);
                o.setAccttypedescr(item.getAcctType().getDescription());
            }
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
     * Create a List of new VwCategory instances.
     * 
     * @param items
     *            List of {@link GlAccountcatgType}
     * @return List<{@link VwCategory}>} or null when <I>items</i> is found to
     *         be null or when an error occurs.
     */
    public static List<VwCategory> create(List<GlAccountcatgType> items) {
        if (items == null) {
            return null;
        }

        try {
            List<VwCategory> obj = new ArrayList<>();
            for (GlAccountcatgType item : items) {
                obj.add(VwCategoryFactory.create(item));
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

}
