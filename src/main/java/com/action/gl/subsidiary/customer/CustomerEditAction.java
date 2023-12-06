package com.action.gl.subsidiary.customer;

import org.apache.log4j.Logger;

import com.SystemException;
import com.api.persistence.DatabaseException;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.Request;
import com.api.web.Response;
import com.entity.CustomerCriteria;

/**
 * This class provides action handlers needed to serve Customer Maintenance Edit
 * user interface requests.
 * 
 * @author Roy Terrell
 *
 */
public class CustomerEditAction extends AbstractCustomerAction {
    private static final String COMMAND_SAVE = "Customer.Edit.save";

    private static final String COMMAND_BACK = "Customer.Edit.back";

    private Logger logger;

    /**
     * Default constructor
     *
     */
    public CustomerEditAction() {
        super();
        logger = Logger.getLogger("CustomerEditAction");
    }

    /**
     * Main constructor for this action handler.
     * 
     * @param context
     *            The servlet context to be associated with this action handler
     * @param request
     *            The request object sent by the client to be associated with
     *            this action handler
     * @throws SystemException
     */
    public CustomerEditAction(Context context, Request request) throws SystemException, DatabaseException {
        super(context, request);
        logger = Logger.getLogger(CustomerEditAction.class);
        this.init(this.context, this.request);
    }

    /**
     * Initializes this object using _conext and _request. This is needed in the
     * event this object is instantiated using the default constructor.
     * 
     * @throws SystemException
     */
    protected void init(Context context, Request request) throws SystemException {
        super.init(context, request);
    }

    /**
     * Obtains key customer data entered by the user from the request object
     */
    public void receiveClientData() throws ActionCommandException {
        super.receiveClientData();

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

        if (command.equalsIgnoreCase(CustomerEditAction.COMMAND_SAVE)) {
            this.saveData();
        }
        if (command.equalsIgnoreCase(CustomerEditAction.COMMAND_BACK)) {
            this.doBack();
        }
    }

    /**
     * Applies customer edit changes to the database.
     * 
     * @throws ActionCommandException
     */
    public void save() throws ActionCommandException {
        // int busId = 0;
        //
        // // Update business contact data
        // JaxbAccountingFactory jaxbUtil = new JaxbAccountingFactory();
        // BusinessType bt = jaxbUtil.createtBusinessType(request);
        // RMT2SessionBean userSession = (RMT2SessionBean)
        // this.request.getSession().getAttribute(RMT2ServletConst.SESSION_BEAN);
        // try {
        // busId = jaxbUtil.updateBusinessContactData(bt,
        // userSession.getLoginId());
        // } catch (MessagingException e) {
        // throw new ActionCommandException(e);
        // }
        //
        // // Update Creditor data.
        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // Customer customer = (Customer) this.cust;
        // boolean error = false;
        // boolean customerNew = customer.getCustomerId() == 0;
        //
        // try {
        // this.msg = "Customer profile was updated successfully";
        // try {
        // customer.setBusinessId(busId);
        // super.save();
        // } catch (Exception e) {
        // this.logger.log(Level.ERROR, e.getMessage());
        // this.msg = e.getMessage();
        // error = true;
        // }
        // if (error) {
        // if (customerNew) {
        // // Send request to the Contacts system to delete the contact
        // // record just added above when update for new customer
        // // fails.
        // try {
        // jaxbUtil.deleteBusinessContactData(busId, userSession.getLoginId());
        // } catch (MessagingException e) {
        // this.logger.log(Level.ERROR, e.getMessage());
        // this.msg += ".  " + e.getMessage();
        // throw new ActionCommandException(this.msg);
        // }
        // }
        // }
        // tx.commitUOW();
        // return;
        // } catch (ActionCommandException e) {
        // tx.rollbackUOW();
        // throw e;
        // } finally {
        // tx.close();
        // tx = null;
        // this.edit();
        // }
    }

    /**
     * Fetches the list customers from the database using the where clause
     * criteria previously stored on the session during the phase of the request
     * to builds the query predicate.
     * 
     * @throws ActionCommandException
     */
    protected void doBack() throws ActionCommandException {
        CustomerCriteria criteria = (CustomerCriteria) this.query.getCustomObj();
        this.customers = this.getCustomers(criteria);
        this.sendClientData();
    }
}