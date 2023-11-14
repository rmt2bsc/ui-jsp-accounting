package com.entity;

import java.util.ArrayList;
import java.util.List;

import org.rmt2.jaxb.GlBalancetypeType;

/**
 * A factory that creates new GlBalanceType related instances.
 * 
 * @author roy.terrell
 * 
 */
public class GlBananceTypeFactory {

    /**
     * Create a new instance of a GlBalanceType class.
     * 
     * @return {@link GlBalanceType}
     */
    public static GlBalanceType create() {
        try {
            return new GlBalanceType();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create a new instance of a GlBalanceType class.
     * 
     * @param item
     *            {@link GlBalancetypeType}
     * @return {@link GlBalanceType}
     */
    public static GlBalanceType create(GlBalancetypeType item) {
        try {
            GlBalanceType o = GlBananceTypeFactory.create();
            o.setAcctBaltypeId(item.getAccountBaltypeId() != null ? item.getAccountBaltypeId().intValue() : 0);
            o.setLongDesc(item.getLongDescription());
            o.setShortDesc(item.getShortDescription());
            return o;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Create a List of new GlBalanceType instances.
     * 
     * @param items
     *            List of {@link GlBalancetypeType}
     * @return List<{@link GlBalanceType}>} or null when <I>items</i> is found
     *         to be null or when an error occurs.
     */
    public static List<GlBalanceType> create(List<GlBalancetypeType> items) {
        if (items == null) {
            return null;
        }

        try {
            List<GlBalanceType> obj = new ArrayList<>();
            for (GlBalancetypeType item : items) {
                obj.add(GlBananceTypeFactory.create(item));
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

}
