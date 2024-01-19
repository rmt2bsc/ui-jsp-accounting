package com.entity;

import com.SystemException;
import com.api.jsp.action.AncestorQueryCriteria;

/**
 * Peer object that manages sales order selection criteria.
 * 
 * @author Roy Terrell.
 */
public class SalesOrderInvoiceCriteria extends AncestorQueryCriteria {
    private static final long serialVersionUID = -7440305695999872651L;

    private String qry_SalesOrderId;

    private String qry_CustomerId;

    private String qry_XactId;

    private boolean qry_FullQuery;


    /**
     * Default constructor.
     * 
     * @author Roy Terrell.
     */
    private SalesOrderInvoiceCriteria() throws SystemException {
        super();
    }

    /**
     * Creates an instance of CreditorCriteria class.
     * 
     * @return {@link CustomerCriteria}
     */
    public static SalesOrderInvoiceCriteria getInstance() {
        try {
            return new SalesOrderInvoiceCriteria();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Initializes all properties to spaces. This is required so that the JSP
     * client will not display null literals in its respective input fields.
     *
     * @author Roy Terrell.
     */
    public void initBean() throws SystemException {
        super.initBean();
        // Initialize to spaces so that JSP client will not display null
        this.qry_SalesOrderId = "";
        this.qry_XactId = "";
        this.qry_CustomerId = "";
    }

    /**
     * @return the Customer Id
     */
    public String getQry_CustomerId() {
        return qry_CustomerId;
    }

    /**
     * @param value
     *            the Customer Id to set
     */
    public void setQry_CustomerId(String value) {
        this.qry_CustomerId = value;
    }

    /**
     * @return the Sales Order Id
     */
    public String getQry_SalesOrderId() {
        return qry_SalesOrderId;
    }

    /**
     * Set the sales order id
     * 
     * @param value
     *            the Sales Order Id to set
     */
    public void setQry_SalesOrderId(String value) {
        this.qry_SalesOrderId = value;
    }


    /**
     * @param qryBusinessId
     *            the qry_BusinessId to set
     */
    public void setQry_XactId(String value) {
        qry_XactId = value;
    }

    /**
     * @return the qry_BusinessIdList
     */
    public String getQry_XactId() {
        return qry_XactId;
    }

    public boolean isQry_FullQuery() {
        return qry_FullQuery;
    }

    public void setQry_FullQuery(boolean qry_FullQuery) {
        this.qry_FullQuery = qry_FullQuery;
    }

}