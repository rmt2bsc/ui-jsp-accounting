package com.entity;

import java.util.ArrayList;
import java.util.List;

import org.rmt2.jaxb.GlAccountType;

/**
 * A factory that creates new VwAccount related instances.
 * 
 * @author roy.terrell
 * 
 */
public class VwAccountFactory {

    /**
     * Create a new instance of a VwAccount class.
     * 
     * @return {@link VwAccount}
     */
    public static VwAccount create() {
        try {
            return new VwAccount();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create a new instance of a VwAccount class.
     * 
     * @param item
     *            {@link GlAccountType}
     * @return {@link VwAccount}
     */
    public static VwAccount create(GlAccountType item) {
        try {
            VwAccount o = VwAccountFactory.create();
            o.setId(item.getAcctId().intValue());
            o.setAcctSeq(item.getAcctSeq().intValue());
            o.setAcctNo(item.getAccountNo());
            o.setName(item.getAccountName());
            o.setCode(item.getAccountCode());
            o.setDescription(item.getAccountDescription());

            if (item.getAcctType() != null) {
                if (item.getAcctType().getAcctTypeId() != null) {
                    o.setAcctTypeId(item.getAcctType().getAcctTypeId().intValue());
                }
                o.setAccttypedescr(item.getAcctType().getDescription());
            }

            if (item.getAcctCatg() != null) {
                if (item.getAcctCatg().getAcctCatgId() != null) {
                    o.setAcctCatId(item.getAcctCatg().getAcctCatgId().intValue());
                }
                o.setAcctcatgdescr(item.getAcctCatg().getDescription());
            }

            o.setBalanceTypeId(item.getBalanceType().getAccountBaltypeId().intValue());

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
     * Create a List of new VwAccount instances.
     * 
     * @param items
     *            List of {@link GlAccountType}
     * @return List<{@link VwAccount}>} or null when <I>items</i> is found to be
     *         null or when an error occurs.
     */
    public static List<VwAccount> create(List<GlAccountType> items) {
        if (items == null) {
            return null;
        }

        try {
            List<VwAccount> obj = new ArrayList<>();
            for (GlAccountType item : items) {
                obj.add(VwAccountFactory.create(item));
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

}
