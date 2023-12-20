package com.action.gl.subsidiary.customer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.SystemException;
import com.api.constants.GeneralConst;
import com.api.constants.RMT2ServletConst;
import com.api.persistence.DatabaseException;
import com.api.security.RMT2TagQueryBean;
import com.api.util.RMT2Money;
import com.api.util.RMT2String2;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.Request;
import com.api.web.Response;
import com.api.web.util.RMT2WebUtility;
import com.entity.Customer;
import com.entity.CustomerCriteria;

/**
 * An relational database implementation of {@link com.gl.customer.CustomerApi
 * CustomerApi}. It provides functionality that creates, updates, deletes, and
 * queries customer account entities.
 * 
 * @author Roy Terrell
 *
 */
public class CustomerSearchAction extends AbstractCustomerAction {
    private static final String COMMAND_NEWSEARCH = "Customer.Search.newsearch";

    private static final String COMMAND_SEARCH = "Customer.Search.search";

    public static final String COMMAND_LIST = "Customer.Search.list";

    private static final String COMMAND_EDIT = "Customer.Search.edit";

    private static final String COMMAND_ADD = "Customer.Search.add";

    private static final String COMMAND_BACK = "Customer.Search.add";

    public static final String COMMAND_PUBLICFETCH = "Customer.Search.publicfetch";

    private static final int CRITERIATYPE_CONTACT = 100;

    private static final int CRITERIATYPE_CUSTOMER = 400;

    private static final int CRITERIATYPE_ALL = 300;

    static private Logger logger;

    private int criteriaType;

    /**
     * Default constructor
     *
     */
    public CustomerSearchAction() {
        super();
    }

    /**
     * Main constructor for this action handler.
     * 
     * @param context
     *            The application context to be associated with this action
     *            handler
     * @param request
     *            The request object sent by the client to be associated with
     *            this action handler
     * @throws SystemException
     */
    public CustomerSearchAction(Context context, Request request) throws SystemException, DatabaseException {
        super(context, request);
        this.init(this.context, this.request);
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is instantiated using the default constructor.
     * 
     * @param context
     *            The application context to be associated with this action
     *            handler
     * @param request
     *            The request object sent by the client to be associated with
     *            this action handler
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
        super.init(context, request);
        logger = Logger.getLogger("CustomerSearchAction");
    }

    /**
     * Processes the client's request using request, response, and command.
     *
     * @param request
     *            The Request object
     * @param response
     *            The Response object
     * @param command
     *            Command issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionCommandException {
        super.processRequest(request, response, command);
        if (command.equalsIgnoreCase(CustomerSearchAction.COMMAND_NEWSEARCH)) {
            this.doNewSearch();
        }
        if (command.equalsIgnoreCase(CustomerSearchAction.COMMAND_SEARCH)) {
            this.doSearch();
        }
        if (command.equalsIgnoreCase(CustomerSearchAction.COMMAND_EDIT)) {
            this.editData();
        }
        if (command.equalsIgnoreCase(CustomerSearchAction.COMMAND_ADD)) {
            this.addData();
        }
        if (command.equalsIgnoreCase(CustomerSearchAction.COMMAND_BACK)) {
            this.doBack();
        }
    }

    /**
     * Creates an instance of {@link com.bean.criteria.CustomerCriteria
     * CustomerCriteria}, which is used to track the user's selection criteria
     * input. This method uses introspection to gather user's input into the
     * criteria object.
     */
    protected Object doCustomInitialization() throws ActionCommandException {
        CustomerCriteria criteriaObj = CustomerCriteria.getInstance();
        if (!this.isFirstTime()) {
            try {
                RMT2WebUtility.packageBean(this.request, criteriaObj);
            } catch (SystemException e) {
                this.msg = "Problem gathering Creditor Search request parameters:  " + e.getMessage();
                CustomerSearchAction.logger.log(Level.ERROR, this.msg);
                throw new ActionCommandException(this.msg);
            }
        }
        this.validateCriteria(criteriaObj);

        return criteriaObj;
    }

    /**
     * Handler method that responds to the client's request to display a new
     * customer search page.
     * 
     * @throws ActionCommandException
     */
    protected void doNewSearch() throws ActionCommandException {
        this.setFirstTime(true);
        this.startSearchConsole();
        this.customers = new ArrayList<>();
        this.query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
        this.sendClientData();
    }

    /**
     * Handler method that responds to the client's request to perform a
     * customer search using the selection criteria entered by the user.
     * 
     * @throws ActionCommandException
     */
    protected void doSearch() throws ActionCommandException {
        this.setFirstTime(false);
        this.buildXMLSearchCriteria();
        this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
        CustomerCriteria criteria = (CustomerCriteria) this.query.getCustomObj();
        this.customers = this.getCustomers(criteria);
        this.sendClientData();
    }


    @Override
    public void add() throws ActionCommandException {
        super.add();
    }

    @Override
    public void edit() throws ActionCommandException {
        super.edit();
        if (this.selectedRow < 0) {
            throw new ActionCommandException("Client must select a row to edit");
        }

        // Make SOAP call to get selected customer's profile
        this.getCurrentCustomerProfile();
        return;
    }

    /**
     * Get the profile of the customer belonging to the current session
     * 
     * @throws ActionCommandException
     */
    protected void getCurrentCustomerProfile() throws ActionCommandException {
        CustomerCriteria c = CustomerCriteria.getInstance();
        c.setQry_CustomerId(String.valueOf(this.customerId));
        List<Customer> list = this.getCustomers(c);
        if (list != null && list.size() > 0) {
            this.cust = list.get(0);
        }
        return;
    }

    /**
     * Determines if search is to be performed using business contact criteria
     * or customer criteria based on the data submitted by the client. Criteria
     * is only valid when the availability of contact or customer criteria is
     * mutually exclusive. Otherwise, an error is thrown.
     * 
     * @param criteria
     *            Business contact or customer selection criteria data.
     * @throws ActionCommandException
     *             When both the customer and contact criteria is present.
     */
    private void validateCriteria(CustomerCriteria criteria) throws ActionCommandException {
        boolean useCredParms = false;
        boolean useContactParms = false;

        if (RMT2String2.isNotEmpty(criteria.getQry_CustomerId()) && !RMT2Money.isNumeric(criteria.getQry_CustomerId())) {
            this.msg = "Customer Id field must be numeric when used as selection criteria";
            this.logger.log(Level.ERROR, this.msg);
            throw new ActionCommandException(this.msg);
        }
        if (RMT2String2.isNotEmpty(criteria.getQry_BusinessId()) && !RMT2Money.isNumeric(criteria.getQry_BusinessId())) {
            this.msg = "Business Contact Id field must be numer when used as selection criteria";
            this.logger.log(Level.ERROR, this.msg);
            throw new ActionCommandException(this.msg);
        }
        useCredParms = (!criteria.getQry_AccountNo().equals(""));
        useContactParms = (!criteria.getQry_TaxId().equals("") || !criteria.getQry_Name().equals("") || !criteria
                .getQry_PhoneMain().equals(""));

        if (useCredParms && useContactParms) {
            this.msg = "The availability of customer and business contact criteria must be mutually exclusive";
            this.logger.log(Level.ERROR, this.msg);
            throw new ActionCommandException(this.msg);
        }
        if (useCredParms) {
            this.criteriaType = CustomerSearchAction.CRITERIATYPE_CUSTOMER;
        }
        if (useContactParms) {
            this.criteriaType = CustomerSearchAction.CRITERIATYPE_CONTACT;
        }
        if (!useContactParms && !useCredParms) {
            this.criteriaType = CustomerSearchAction.CRITERIATYPE_ALL;
        }
    }

    /**
     * Retrieve the selected customer id from a list of customers.
     * 
     * @throws ActionCommandException
     *             Problem Identifying Customer Id from a list of customers
     */
    public void receiveClientData() throws ActionCommandException {
        super.receiveClientData();
    }

    /**
     * Preserves the user's input values regarding the selection criteria.
     */
    public void sendClientData() throws ActionCommandException {
        super.sendClientData();
        this.session.setAttribute(GeneralConst.CLIENT_DATA_CRITERIA, this.query.getCustomObj());
    }
}