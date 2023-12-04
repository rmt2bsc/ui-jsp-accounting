package com.action.gl.subsidiary.customer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.SystemException;
import com.api.constants.GeneralConst;
import com.api.constants.RMT2ServletConst;
import com.api.persistence.DatabaseException;
import com.api.persistence.db.DatabaseConnectionBean;
import com.api.security.RMT2TagQueryBean;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.Request;
import com.api.web.Response;
import com.entity.Customer;
import com.entity.CustomerCriteria;
import com.entity.CustomerFactory;

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

    private Logger logger;

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
     * event this object is inistantiated using the default constructor.
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
        if (command.equalsIgnoreCase(CustomerSearchAction.COMMAND_LIST)) {
            this.listData();
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
     * Returns selection criteria that is sure to retrun an empty result set
     * once applied to the sql that pertains to the data source of the customer
     * search page.
     */
    protected String doInitialCriteria(RMT2TagQueryBean _query) throws ActionCommandException {
        return "customer_id = -1";
    }

    /**
     * Creates an instance of {@link com.bean.criteria.CustomerCriteria
     * CustomerCriteria}, which is used to track the user's selection criteria
     * input. This method uses introspection to gather user's input into the
     * cretieria object.
     */
    protected Object doCustomInitialization() throws ActionCommandException {
        CustomerCriteria criteriaObj = CustomerCriteria.getInstance();
        if (!this.isFirstTime()) {
            try {
                DataSourceAdapter.packageBean(this.request, criteriaObj);
                this.setBaseView("CustomerView");
            } catch (SystemException e) {
                this.msg = "Problem gathering Customer Search request parameters:  " + e.getMessage();
                logger.log(Level.ERROR, this.msg);
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
        this.customers = new ArrayList();
        this.query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
        this.sendClientData();
    }

    /**
     * Handler method that responds to the client's request to perform a
     * customer search using the selection criterai entered by the user.
     * 
     * @throws ActionCommandException
     */
    protected void doSearch() throws ActionCommandException {
        this.setFirstTime(false);
        this.buildSearchCriteria();
        this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
    }

    /**
     * Builds selection criteria using either direct customer or business
     * contact data values. Customer criteria is represented by <i>account
     * number</i>. <i>Name</i>, <i>tax id</i>, and <i>main phone number</i> are
     * the only properties recognized as business contact data. Customer and
     * business contact data are mutual exclusive when determining which
     * criteria group is active.
     * 
     * @param query
     *            {@link RMT2TagQueryBean} object containing the key/value pair
     *            data items used to build the service parameters.
     * @param searchMode
     *            Set to either {@link RMT2ServletConst.SEARCH_MODE_NEW} or
     *            {@link RMT2ServletConst.SEARCH_MODE_OLD}
     * @throws ActionCommandException
     *             When customer values and business contact values are
     *             discovered for the same search transaction.
     */
    protected void doPostCustomInitialization(RMT2TagQueryBean query, int searchMode) throws ActionCommandException {
        CustomerCriteria cc = (CustomerCriteria) query.getCustomObj();
        StringBuffer sql = new StringBuffer();

        if (this.criteriaType == CRITERIATYPE_CONTACT) {
            if (!cc.getQry_Name().equals("")) {
                sql.append(CustomerConst.CRITERIATAGS_CONTACT[0]);
                sql.append(" = ");
                sql.append(cc.getQry_Name().trim());
            }
            if (!cc.getQry_PhoneMain().equals("")) {
                if (sql.length() > 0) {
                    sql.append(" and ");
                }
                sql.append(CustomerConst.CRITERIATAGS_CONTACT[1]);
                sql.append(" = ");
                sql.append(cc.getQry_PhoneMain().trim());
            }
            if (!cc.getQry_TaxId().equals("")) {
                if (sql.length() > 0) {
                    sql.append(" and ");
                }
                sql.append(CustomerConst.CRITERIATAGS_CONTACT[2]);
                sql.append(" = ");
                sql.append(cc.getQry_TaxId().trim());
            }
        }
        if (this.criteriaType == CRITERIATYPE_CUSTOMER) {
            if (!cc.getQry_AccountNo().equals("")) {
                sql.append(CustomerConst.CRITERIATAGS_CUSTOMER[0]);
                sql.append(" like ");
                sql.append(cc.getQry_AccountNo().trim());
            }
        }
        query.setWhereClause(null);
        query.setWhereClause(sql.toString());
        return;
    }

    /**
     * Fetches the list customers from the database using the where clause
     * criteria previously stored on the session during the phase of the request
     * to builds the query predicate.
     * 
     * @throws ActionCommandException
     */
    protected void listData() throws ActionCommandException {
        DatabaseTransApi tx = DatabaseTransFactory.create();
        CustomerApi api = CustomerFactory.createApi((DatabaseConnectionBean) tx.getConnector(), this.request);
        try {
            // String criteria = this.query.getWhereClause();
            CustomerCriteria criteria = (CustomerCriteria) this.query.getCustomObj();
            this.customers = (List) api.findCustomerBusiness(criteria);

            if (this.customers == null) {
                this.customers = new ArrayList();
            }
        } catch (Exception e) {
            throw new ActionCommandException(e);
        } finally {
            api.close();
            tx.close();
            api = null;
            tx = null;
        }
        this.sendClientData();
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
     *             When bothe the customer and contact criteria is present.
     */
    private void validateCriteria(CustomerCriteria criteria) throws ActionCommandException {
        boolean useCredParms = false;
        boolean useContactParms = false;

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
        int custId;
        String temp = null;

        try {
            if (this.command.equalsIgnoreCase(CustomerSearchAction.COMMAND_ADD)) {
                return;
            }
            temp = this.getInputValue("CustomerId", null);
            custId = Integer.parseInt(temp);
            this.cust = CustomerFactory.createCustomer();
            ((Customer) this.cust).setCustomerId(custId);
        } catch (NumberFormatException e) {
            this.msg = "Problem Identifying Customer Id from a list of customers.  The customer id was found to be [" + temp
                    + "]";
            this.logger.log(Level.ERROR, this.msg);
            throw new ActionCommandException(this.msg);
        }
    }

    /**
     * Preserves the user's input values regarding the selection criteria.
     */
    public void sendClientData() throws ActionCommandException {
        super.sendClientData();
        this.session.setAttribute(GeneralConst.CLIENT_DATA_CRITERIA, this.query.getCustomObj());
    }
}