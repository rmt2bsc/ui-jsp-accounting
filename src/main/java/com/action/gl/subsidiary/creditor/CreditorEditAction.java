package com.action.gl.subsidiary.creditor;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.rmt2.jaxb.AccountingTransactionResponse;
import org.rmt2.jaxb.AddressBookResponse;
import org.rmt2.jaxb.ReplyStatusType;

import com.SystemException;
import com.action.gl.subsidiary.BusinessContactSoapRequests;
import com.api.constants.GeneralConst;
import com.api.persistence.DatabaseException;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.Request;
import com.api.web.Response;
import com.entity.Creditor;
import com.entity.CreditorCriteria;
import com.entity.CreditorFactory;

/**
 * This class provides functionality needed to serve the requests for the
 * Creditor Search user interface
 * 
 * @author Roy Terrell
 * 
 */
public class CreditorEditAction extends AbstractCreditorAction {
    private static final String COMMAND_SAVE = "Creditor.Edit.save";

    private static final String COMMAND_BACK = "Creditor.Edit.back";
    
    private static final String COMMAND_DELETE = "Creditor.Edit.delete";

    private static Logger logger;

    /**
     * Default constructor
     * 
     */
    public CreditorEditAction() {
        super();
        logger = Logger.getLogger("CreditorEditAction");
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
    public CreditorEditAction(Context _context, Request _request) throws SystemException, DatabaseException {
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
        if (command.equalsIgnoreCase(CreditorEditAction.COMMAND_SAVE)) {
            this.saveData();
        }
        if (command.equalsIgnoreCase(CreditorEditAction.COMMAND_BACK)) {
            this.doBack();
        }
        if (command.equalsIgnoreCase(CreditorEditAction.COMMAND_DELETE)) {
            this.deleteData();
        }
    }

    /**
     * Applies changes made to the creditor and its address to the database. A
     * web service from the Contacts application is used to apply updates to the
     * creditor's address information. After successfully updating the changes,
     * the creditor's profile is retrieved from the database as confirmation.
     * Special processing steps must occur when performing an insert, update,
     * and delete database operations using local and remote database contexts.
     * For inserts, the logic ensures that contact data (business and address)
     * updated remotely is properly rolled back in the event the base creditor
     * insert operation fails. For updates or deletes, the logic guarantees that
     * the base creditor is successfully persisted before the business and
     * address contact changes are persisted.
     * 
     * @throws ActionCommandException
     */
    @Override
    public void save() throws ActionCommandException {
        int contactId = 0;

        // Call SOAP web service to update creditor's business contact
        // information since creditor depends on business id.
        try {
            AddressBookResponse response = BusinessContactSoapRequests.callSave(this.cred, this.loginId,
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
                this.cred.setBusinessId(contactId);
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }

        // Call SOAP web service to persist Creditor data changes to the
        // database.
        try {
            AccountingTransactionResponse response = CreditorSoapRequests.callSave(this.cred, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.msg = rst.getMessage();
                return;
            }
            List<Creditor> results = null;
            if (response.getProfile() != null) {
                results = CreditorFactory.create(response.getProfile().getCreditors().getCreditor());

                // Retrieve newly updated creditor profile from database
                CreditorCriteria criteria = new CreditorCriteria();
                criteria.setQry_CreditorId(String.valueOf(results.get(0).getCreditorId()));
                List<Creditor> list = this.getCreditors(criteria);
                if (list != null && list.size() > 0) {
                    this.cred = list.get(0);
                }
            }
            else {
                this.cred = CreditorFactory.create();
            }
            super.save();

            // Delayed the assignment of the "creditor saved successfully"
            // confirmation message due to other web service calls are message
            // property as well.
            this.msg = rst.getMessage();
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }
    }

    /**
     * Deletes a single creditor profile from the system.
     * 
     * @throws ActionCommandException
     */
    @Override
    public void delete() throws ActionCommandException, DatabaseException {
        // Call SOAP web service to delete a Creditor data changes to
        // the database
        try {
            AccountingTransactionResponse response = CreditorSoapRequests.callDelete(this.cred, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            this.msg = rst.getMessage();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.msg = rst.getMessage();
                return;
            }
            List<Creditor> results = null;
            if (response.getProfile() != null) {
                results = CreditorFactory.create(response.getProfile().getCreditors().getCreditor());
                this.cred = results.get(0);
            }
            else {
                this.cred = CreditorFactory.create();
            }
            super.delete();
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }
    }

    /**
     * Navigates the user to the Creditor/Vendor search page. The selection
     * criteria that was used to fetch creditor list during the previous search
     * session is obtained from the web server session in order to perform the
     * query.
     * 
     * @throws ActionCommandException
     *             General database errors.
     */
    protected void doBack() throws ActionCommandException {
        CreditorCriteria criteria = (CreditorCriteria) this.query.getCustomObj();
        this.credTypeList = this.getCreditorTypes();
        this.creditors = this.getCreditors(criteria);
        this.sendClientData();
    }
}