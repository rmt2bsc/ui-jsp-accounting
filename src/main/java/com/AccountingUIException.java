package com;


public class AccountingUIException extends RMT2RuntimeException {
    private static final long serialVersionUID = 3146419736970380825L;

    /**
     * Default constructor that creates an AddressbookUIException object with a
     * null message.
     * 
     */
    public AccountingUIException() {
        super();
    }

    /**
     * Creates an AddressbookUIException with a message.
     * 
     * @param msg
     *            The text message.
     */
    public AccountingUIException(String msg) {
        super(msg);
    }

    /**
     * Creates an AddressbookUIException with a message and a code.
     * 
     * @param msg
     *            The text message.
     * @param code
     *            The integer code.
     */
    public AccountingUIException(String msg, int code) {
        super(msg, code);
    }

    /**
     * Creates an AddressbookUIException using an Exception.
     * 
     * @param e
     *            An Exception object.
     */
    public AccountingUIException(Exception e) {
        super(e);
    }

    /**
     * Creates a new AddressbookUIException with a the specified message and the
     * causing throwable instance.
     * 
     * @param msg
     *            the message that explains the error.
     * @param cause
     *            the cause (which is saved for later retrieval by the
     *            Throwable.getCause() method). (A null value is permitted, and
     *            indicates that the cause is nonexistent or unknown.)
     * 
     */
    public AccountingUIException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
