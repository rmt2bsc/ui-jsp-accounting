package com.action.gl.subsidiary.customer;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.rmt2.jaxb.AccountingTransactionResponse;
import org.rmt2.jaxb.AddressBookResponse;
import org.rmt2.jaxb.ReplyStatusType;

import com.AccountingConst;
import com.AccountingUIException;
import com.SystemException;
import com.action.gl.subsidiary.BusinessContactSoapRequests;
import com.api.constants.GeneralConst;
import com.api.persistence.DatabaseException;
import com.api.util.RMT2String2;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.Request;
import com.api.web.Response;
import com.entity.Customer;
import com.entity.CustomerCriteria;
import com.entity.CustomerFactory;

/**
 * This class provides action handlers needed to serve Customer Maintenance Edit
 * user interface requests.
 * 
 * @author Roy Terrell
 *
 */
public class CustomerEditAction extends AbstractCustomerAction {
    private static final String COMMAND_SAVE = "Customer.Edit.save";

    private static final String COMMAND_DELETE = "Customer.Edit.delete";

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
        if (command.equalsIgnoreCase(CustomerEditAction.COMMAND_DELETE)) {
            this.deleteData();
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
        int contactId = 0;

        // Call SOAP web service to update creditor's business contact
        // information since creditor depends on business id.
        try {
            AddressBookResponse response = BusinessContactSoapRequests.callSave(this.cust, this.loginId,
                    this.session.getId());
            ReplyStatusType rst2 = response.getReplyStatus();
            if (rst2.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.msg = rst2.getMessage();
                return;
            }
            // Capture the business id property of the business contact
            // regardless if new or existing.
            if (response.getProfile() != null && response.getProfile().getCommonContacts() != null) {
                contactId = response.getProfile().getCommonContacts().get(0).getContactId().intValue();
                this.cust.setBusinessId(contactId);
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }

        // Call SOAP web service to persist Customer data changes to the
        // database.
        try {
            AccountingTransactionResponse response = CustomerSoapRequests.callSave(this.cust, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.throwActionError(rst.getMessage(), rst.getExtMessage());
            }
            List<Customer> results = null;
            if (response.getProfile() != null) {
                results = CustomerFactory.create(response.getProfile().getCustomers().getCustomer());

                // Retrieve newly updated customer profile from database
                CustomerCriteria criteria = CustomerCriteria.getInstance();
                criteria.setQry_CustomerId(String.valueOf(results.get(0).getCustomerId()));
                List<Customer> list = this.getCustomers(criteria);
                if (list != null && list.size() > 0) {
                    this.cust = list.get(0);
                }
            }
            else {
                this.cust = CustomerFactory.create();
            }
            super.save();

            // Delayed the assignment of the "customer saved successfully"
            // confirmation message due to other web service calls are message
            // property as well.
            this.msg = rst.getMessage();
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }
    }

    /**
     * Deletes a single customer profile from the system.
     * 
     * @throws ActionCommandException
     */
    @Override
    public void delete() throws ActionCommandException, DatabaseException {
        // Call SOAP web service to verify whether or not the customer has
        // transaction history.
        try {
            CustomerCriteria criteria = CustomerCriteria.getInstance();
            criteria.setQry_CustomerId(String.valueOf(this.cust.getCustomerId()));

            AccountingTransactionResponse response = CustomerSoapRequests.callGetHistory(criteria, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.throwActionError(rst.getMessage(), rst.getExtMessage());
            }

            // Abort if customer cannot not be found in the system
            if (rst.getRecordCount().intValue() > 0) {
                // Abort the process if customer has transaction history.
                if (response.getProfile() != null
                        && response.getProfile().getCustomers() != null
                        && response.getProfile().getCustomers().getCustomer() != null
                        && !response.getProfile().getCustomers().getCustomer().isEmpty()
                        && response.getProfile().getCustomers().getCustomer().get(0).getTransactions() != null
                        && !response.getProfile().getCustomers().getCustomer().get(0).getTransactions().getTransaction()
                                .isEmpty()) {
                    this.throwActionError(AccountingConst.MSG_SUBSIDIARY_HAS_HISTORY, null);
                }
            }
            else {
                this.throwActionError(AccountingConst.MSG_SUBSIDIARY_NOTFOUND, null);
            }
        } catch (AccountingUIException e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }

        // Capture the business ID
        int contactId = this.cust.getBusinessId();
        String addressResults = null;

        // Call SOAP web service to delete the Creditor profile.
        try {
            AccountingTransactionResponse response = CustomerSoapRequests.callDelete(this.cust, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            this.msg = rst.getMessage();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.throwActionError(rst.getMessage(), rst.getExtMessage());
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }

        // Call SOAP web service to delete the customer's business contact
        // profile from the Addressbook
        try {
            AddressBookResponse response = CustomerSoapRequests.callDeleteContactData(contactId, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.throwActionError(rst.getMessage(), rst.getExtMessage());
            }

            // Verify contact info was deleted.
            if (response.getProfile() != null) {
                if (response.getProfile().getCommonContacts() != null) {
                    // TODO: Don't quite know how to use this at the moment,
                    // though...
                    contactId = response.getProfile().getCommonContacts().get(0).getContactId().intValue();
                    addressResults = "The creditor's business contact profile was removed from the Addressbook";
                }
            }
            else {
                addressResults = "NOTE:  Records indicate the creditor's business contact profile has already been removed from the Addressbook prior to this transaction";
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }

        // Update the user's confirmation message
        if (RMT2String2.isNotEmpty(addressResults)) {
            this.msg += ":  " + addressResults;
        }
        super.delete();
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