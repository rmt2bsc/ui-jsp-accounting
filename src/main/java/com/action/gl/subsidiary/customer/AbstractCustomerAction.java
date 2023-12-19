package com.action.gl.subsidiary.customer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.rmt2.jaxb.AccountingTransactionResponse;
import org.rmt2.jaxb.ReplyStatusType;

import com.AccountingConst;
import com.SystemException;
import com.action.gl.subsidiary.AbstractSubsidiaryAction;
import com.api.constants.GeneralConst;
import com.api.persistence.DatabaseException;
import com.api.util.RMT2Money;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.ICommand;
import com.api.web.Request;
import com.api.web.Response;
import com.api.web.util.RMT2WebUtility;
import com.entity.Customer;
import com.entity.CustomerCriteria;
import com.entity.CustomerFactory;

/**
 * This abstract class provides common functionality needed to serve various
 * user interfaces pertaining to Customer maintenance.
 * 
 * @author Roy Terrell
 * 
 */
public abstract class AbstractCustomerAction extends AbstractSubsidiaryAction implements ICommand {
    
    private Logger logger;

    /** An ArrayList of Customers */
    protected List<Customer> customers;

    /** Customer */
    protected Customer cust;

    /** Customer's Business profile */
    private Object custDetail;
    protected int customerId;

    /**
     * Default constructor
     * 
     */
    public AbstractCustomerAction() {
        super();
        logger = Logger.getLogger(AbstractCustomerAction.class);
    }

    /**
     * Main constructor for this action handler.
     * 
     * @param _context
     *            The servlet context to be associated with this action handler
     * @param _request
     *            The request object sent by the client to be associated with
     *            this action handler
     * @throws SystemException
     */
    public AbstractCustomerAction(Context _context, Request _request) throws SystemException, DatabaseException {
        super(_context, _request);
        this.init(this.context, this.request);
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is instantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
        super.init(_context, _request);
    }

    /**
     * Processes the client's request using request, response, and command.
     * 
     * @param request
     *            The HttpRequest object
     * @param response
     *            The HttpResponse object
     * @param command
     *            Command issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionCommandException {
        super.processRequest(request, response, command);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.api.jsp.action.ICommonAction#add()
     */
    @Override
    public void add() throws ActionCommandException {
        this.setupLookupData();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.api.jsp.action.ICommonAction#edit()
     */
    @Override
    public void edit() throws ActionCommandException {
        this.setupLookupData();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.api.jsp.action.ICommonAction#delete()
     */
    @Override
    public void delete() throws ActionCommandException, DatabaseException {
        this.setupLookupData();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.api.jsp.action.ICommonAction#save()
     */
    @Override
    public void save() throws ActionCommandException, DatabaseException {
        this.setupLookupData();        
    }

    /**
     * @return the cust
     */
    public Object getCust() {
        return cust;
    }

    /**
     * Fetches the list customers from the database using the where clause
     * criteria previously stored on the session during the phase of the request
     * to builds the query predicate.
     * 
     * @param criteria
     *            {@link CustomerCriteria}
     * @return List<{@link Customer}>
     * @throws ActionCommandException
     */
    protected List<Customer> getCustomers(CustomerCriteria criteria) throws ActionCommandException {
        // Call SOAP web service to get a list of Customers based on selection
        // criteria
        try {
            AccountingTransactionResponse response = CustomerSoapRequests.callGet(criteria, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            this.msg = rst.getMessage();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.throwActionError(rst.getMessage(), rst.getExtMessage());
            }
            List<Customer> results = null;
            if (response.getProfile() != null && response.getProfile().getCustomers() != null) {
                results = CustomerFactory.create(response.getProfile().getCustomers().getCustomer());
            }
            else {
                results = new ArrayList<>();
            }
            this.msg += ": " + results.size();
            return results;
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }
    }


    /**
     * Obtains common key creditor related data items from the client JSP.
     */
    public void receiveClientData() throws ActionCommandException {
        super.receiveClientData();

        // Attempt to locate and obtain customer ID from the JSP.
        String temp = this.getInputValue("CustomerId", null);
        this.customerId = RMT2Money.stringToNumber(temp).intValue();
        
        // Attempt to locate and obtain creditor profile data from the JSP.
        try {
            this.cust = CustomerFactory.create();
            RMT2WebUtility.packageBean(this.request, this.cust);
        } catch (Exception e) {
            throw new ActionCommandException(e.getMessage());
        }
    }

    /**
     * <p>
     * Gathers data for the following objects and returns the data to the client
     * via the HttpServletRequest object to be handle at the discretion of the
     * client:
     * <p>
     * <table width="100%" border="1" cellspacing="0" cellpadding="0">
     * <tr>
     * <td><strong>Attribute</strong></td>
     * <td><strong>Type</strong></td>
     * <td><strong>Description</strong></td>
     * <td><strong>Attribute Name</strong></td>
     * </tr>
     * <tr>
     * <td>CREDITOR</td>
     * <td>{@link Creditor}</td>
     * <td>Creditor data</td>
     * <td>listdata</td>
     * </tr>
     * <tr>
     * <td>CREDITOR LIST</td>
     * <td>List of {@link CreditorExt}</td>
     * <td>List of Creditors</td>
     * <td>listdata</td>
     * </tr>
     * <tr>
     * <td>BUSINESS CONTACT</td>
     * <td>XML from contact application</td>
     * <td>Business and Address data</td>
     * <td>business</td>
     * </tr>
     * </table>
     * 
     * @throws ActionCommandException
     */
    public void sendClientData() throws ActionCommandException {
        super.sendClientData();
        this.request.setAttribute(AccountingConst.CLIENT_DATA_SUBSIDIARY, this.cust);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.customers);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_BUSINESS, this.custDetail);
    }
}