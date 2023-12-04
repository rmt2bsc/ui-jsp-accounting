package com.entity;

import java.util.ArrayList;
import java.util.List;

import org.rmt2.jaxb.AddressType;
import org.rmt2.jaxb.BusinessType;
import org.rmt2.jaxb.CustomerType;
import org.rmt2.jaxb.ZipcodeType;

/**
 * A factory that creates new Customer related instances.
 * 
 * @author roy.terrell
 * 
 */
public class CustomerFactory {

    /**
     * Create a new instance of a Customer class.
     * 
     * @return {@link Customer}
     */
    public static Customer create() {
        try {
            return new Customer();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create a new instance of a Customer class.
     * 
     * @param item
     *            {@link CustomerType}
     * @return {@link Customer}
     */
    public static Customer create(CustomerType item) {
        try {
            Customer o = CustomerFactory.create();
            o.setCustomerId(item.getCustomerId() == null ? 0 : item.getCustomerId().intValue());

            o.setAcctId(item.getAcctId() == null ? 0 : item.getAcctId().intValue());
            o.setAccountNo(item.getAccountNo());
            o.setCreditLimit(item.getCreditLimit() == null ? 0 : item.getCreditLimit().doubleValue());
            o.setActive(item.getActive() == null ? 0 : item.getActive().intValue());
            o.setBalance(item.getBalance() == null ? 0 : item.getBalance().doubleValue());
            o.setDescription(item.getAcctDescription());

            if (item.getTracking() != null) {
                if (item.getTracking().getDateCreated() != null) {
                    o.setDateCreated(item.getTracking().getDateCreated().toGregorianCalendar().getTime());
                }
                if (item.getTracking().getDateUpdated() != null) {
                    o.setDateUpdated(item.getTracking().getDateUpdated().toGregorianCalendar().getTime());
                }
                o.setUserId(item.getTracking().getUserId());
            }
            
            if (item.getBusinessContactDetails() != null) {
                // Business Contact details
                BusinessType bt = item.getBusinessContactDetails();
                o.setBusinessId(bt.getBusinessId() == null ? 0 : bt.getBusinessId().intValue());
                o.setLongname(bt.getLongName());
                o.setShortname(bt.getShortName());
                
                if (bt.getServiceType() != null) {
                    o.setServTypeId(bt.getServiceType().getCodeId() == null ? 0 : bt.getServiceType().getCodeId().intValue());
                    o.setServTypeName(bt.getServiceType().getLongdesc());
                }
                if (bt.getEntityType() != null) {
                    o.setEntityTypeId(bt.getEntityType().getCodeId() == null ? 0 : bt.getEntityType().getCodeId().intValue());
                    o.setEntityTypeName(bt.getEntityType().getLongdesc());
                }
                o.setContactFirstname(bt.getContactFirstname());
                o.setContactLastname(bt.getContactLastname());
                o.setContactPhone(bt.getContactPhone());
                o.setContactExt(bt.getContactExt());
                o.setContactEmail(bt.getContactEmail());
                o.setTaxId(bt.getTaxId());
                o.setWebsite(bt.getWebsite());

                // Address details
                if (bt.getAddress() != null) {
                    AddressType at = bt.getAddress();
                    o.setAddrId(at.getAddrId() == null ? 0 : at.getAddrId().intValue());
                    o.setAddr1(at.getAddr1());
                    o.setAddr2(at.getAddr2());
                    o.setAddr3(at.getAddr3());
                    o.setAddr4(at.getAddr4());
                    o.setZipext(at.getZipExt() == null ? 0 : at.getZipExt().intValue());
                    o.setPhoneCell(at.getPhoneCell());
                    o.setPhoneExt(at.getPhoneWorkExt());
                    o.setPhoneFax(at.getPhoneFax());
                    o.setPhoneHome(at.getPhoneHome());
                    o.setPhoneMain(at.getPhoneMain());
                    o.setPhonePager(at.getPhonePager());
                    o.setPhoneWork(at.getPhoneWork());

                    // Zip data
                    if (at.getZip() != null) {
                        ZipcodeType zt = at.getZip();
                        o.setZip(zt.getZipcode() == null ? 0 : zt.getZipcode().intValue());
                        o.setCity(zt.getCity());
                        o.setState(zt.getState());
                        o.setAreaCode(zt.getAreaCode());
                        o.setCounty(zt.getCountyName());
                    }
                }
            }
            return o;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Create a List of new Customer instances.
     * 
     * @param items
     *            List of {@link CustomerType}
     * @return List<{@link Customer}>} or null when <I>items</i> is found to be
     *         null or when an error occurs.
     */
    public static List<Customer> create(List<CustomerType> items) {
        if (items == null) {
            return null;
        }

        try {
            List<Customer> obj = new ArrayList<>();
            for (CustomerType item : items) {
                obj.add(CustomerFactory.create(item));
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

}
