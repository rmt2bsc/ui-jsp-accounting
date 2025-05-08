package com.action.inventory;

import com.RMT2Exception;

public class ItemMasterException extends RMT2Exception {
    private static final long serialVersionUID = 1L;

    public ItemMasterException() {
        super();
    }

    public ItemMasterException(String msg) {
        super(msg);
    }

    public ItemMasterException(int code) {
        super(code);
    }

    public ItemMasterException(String msg, int code) {
        super(msg, code);
    }

    public ItemMasterException(String msg, int code, String objectname, String methodname) {
        super(msg, code, objectname, methodname);
    }

    public ItemMasterException(Exception e) {
        super(e);
    }

    public ItemMasterException(String msg, Throwable e) {
        super(msg, e);
    }
}
