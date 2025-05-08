package com.entity;

import java.util.ArrayList;
import java.util.List;

import org.rmt2.jaxb.CodeDetailType;



/**
 * A factory that creates new General Code type related instances.
 * 
 * @author roy.terrell
 * 
 */
public class GeneralCodesFactory {

    /**
     * Create a new instance of a GeneralCodes class.
     * 
     * @return {@link GeneralCodes}
     */
    public static GeneralCodes create() {
        try {
            return new GeneralCodes();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create a new instance of a GeneralCodes class.
     * 
     * @param item
     *            {@link CodeDetailType}
     * @return {@link GeneralCodes}
     */
    public static GeneralCodes create(CodeDetailType item) {
        try {
            GeneralCodes obj = GeneralCodesFactory.create();
            obj.setCodeId(item.getCodeId() == null ? 0 : item.getCodeId().intValue());
            obj.setCodeGrpId(item.getGroupId() == null ? 0 : item.getGroupId().intValue());
            obj.setLongdesc(item.getLongdesc());
            obj.setShortdesc(item.getShortdesc());
            return obj;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Create a List of new GeneralCodes instances.
     * 
     * @param items
     *            List of {@link CodeDetailType}
     * @return List<{@link GeneralCodes}>} or null when <I>items</i> is found to
     *         be null or when an error occurs.
     */
    public static List<GeneralCodes> create(List<CodeDetailType> items) {
        if (items == null) {
            return null;
        }

        try {
            List<GeneralCodes> obj = new ArrayList<>();
            for (CodeDetailType item : items) {
                obj.add(GeneralCodesFactory.create(item));
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

}
