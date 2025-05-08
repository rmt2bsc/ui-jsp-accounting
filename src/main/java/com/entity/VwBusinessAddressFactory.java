package com.entity;

import java.util.ArrayList;
import java.util.List;

import org.rmt2.jaxb.AddressType;
import org.rmt2.jaxb.BusinessType;



/**
 * A factory that creates new VwBusinessAddress related instances.
 * 
 * @author roy.terrell
 * 
 */
public class VwBusinessAddressFactory {

    /**
     * Create a new instance of a VwBusinessAddress class.
     * 
     * @return {@link VwBusinessAddress}
     */
    public static VwBusinessAddress create() {
        try {
            return new VwBusinessAddress();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create a new instance of a VwBusinessAddress class.
     * 
     * @param item
     *            {@link CodeGroupType}
     * @return {@link VwBusinessAddress}
     */
    public static VwBusinessAddress create(BusinessType item) {
        try {
            VwBusinessAddress o = VwBusinessAddressFactory.create();
            o.setBusinessId(item.getBusinessId() == null ? 0 : item.getBusinessId().intValue());
            o.setBusEntityTypeId(item.getEntityType() == null || item.getEntityType().getCodeId() == null ? 0 : item
                    .getEntityType().getCodeId().intValue());
            o.setBusServTypeId(item.getServiceType() == null || item.getServiceType().getCodeId() == null ? 0 : item
                    .getServiceType().getCodeId().intValue());
            o.setBusLongname(item.getLongName());
            o.setBusShortname(item.getShortName());
            o.setBusContactFirstname(item.getContactFirstname());
            o.setBusContactLastname(item.getContactLastname());
            o.setBusContactPhone(item.getContactPhone());
            o.setBusContactExt(item.getContactExt());
            o.setContactEmail(item.getContactEmail());
            o.setBusTaxId(item.getTaxId());
            o.setBusWebsite(item.getWebsite());

            if (item.getAddress() != null) {
                AddressType a = item.getAddress();
                o.setAddrId(a.getAddrId() == null ? 0 : a.getAddrId().intValue());
                o.setAddr1(a.getAddr1());
                o.setAddr2(a.getAddr2());
                o.setAddr3(a.getAddr3());
                o.setAddr4(a.getAddr4());

                if (a.getZip() != null) {
                    o.setZipCity(a.getZip().getCity());
                    o.setZipState(a.getZip().getState());
                    o.setAddrZip(a.getZip().getZipcode() == null ? 0 : a.getZip().getZipcode().intValue());
                }
                o.setAddrZipext(a.getZipExt() == null ? 0 : a.getZipExt().intValue());
                o.setAddrPhoneHome(a.getPhoneHome());
                o.setAddrPhoneWork(a.getPhoneWork());
                o.setAddrPhoneExt(a.getPhoneWorkExt());
                o.setAddrPhoneMain(a.getPhoneMain());
                o.setAddrPhoneCell(a.getPhoneCell());
                o.setAddrPhoneFax(a.getPhoneFax());
                o.setAddrPhonePager(a.getPhonePager());
            }

            return o;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Create a List of new VwBusinessAddress instances.
     * 
     * @param items
     *            List of {@link BusinessType}
     * @return List<{@link VwBusinessAddress}>} or null when <I>items</i> is
     *         found to be null or when an error occurs.
     */
    public static List<VwBusinessAddress> create(List<BusinessType> items) {
        if (items == null) {
            return null;
        }

        try {
            List<VwBusinessAddress> obj = new ArrayList<>();
            for (BusinessType item : items) {
                obj.add(VwBusinessAddressFactory.create(item));
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

}
