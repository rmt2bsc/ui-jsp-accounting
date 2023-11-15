package com.action.gl.creditor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.rmt2.jaxb.AccountingTransactionResponse;
import org.rmt2.jaxb.ReplyStatusType;

import com.AccountingConst;
import com.SystemException;
import com.api.constants.GeneralConst;
import com.api.constants.RMT2ServletConst;
import com.api.jsp.action.AbstractActionHandler;
import com.api.persistence.DatabaseException;
import com.api.persistence.db.DatabaseConnectionBean;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.ICommand;
import com.api.web.Request;
import com.api.web.Response;
import com.api.web.util.RMT2WebUtility;
import com.entity.Creditor;
import com.entity.CreditorCriteria;
import com.entity.CreditorFactory;
import com.entity.CreditorType;
import com.entity.CreditorTypeFactory;

/**
 * This abstract class provides common functionality needed to serve various
 * user interfaces pertaining to Creditor maintenance.
 * 
 * @author Roy Terrell
 * 
 */
public abstract class CreditorAction extends AbstractActionHandler implements ICommand {
    private Logger logger;

    /** Creditor's balance */
    private Double balance;

    /** An ArrayList of Creditors */
    protected List creditors;

    /** Creditor */
    protected Object cred;

    /** Creditor Extension */
    protected Object credExt;

    /** Creditor's Business profile */
    private Object credDetail;

    private Object busTypes;

    private Object busServTypes;

    protected List credTypeList;


    /**
     * Default constructor
     * 
     */
    public CreditorAction() {
        super();
        logger = Logger.getLogger("CreditorAction");
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
    public CreditorAction(Context _context, Request _request) throws SystemException, DatabaseException {
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


    /**
     * Retrieves a single instance of creditor's detail data and its associated
     * address from the database using the creditor's internal id.
     * 
     * @param creditorId
     *            The creditor's internal id which is generally the primary key.
     * @throws ActionCommandException
     */
    protected void fetchCreditorTypes() throws ActionCommandException {

    }

    /**
     * Obtains the creditor balance.
     * 
     * @param id
     *            The creditor Id
     * @param conBean
     *            The database connection
     * @return Double Creditor's balance
     */
    protected Double getBalance(int id, DatabaseConnectionBean conBean) {
        Double balance = null;
        // CreditorApi api = CreditorFactory.createApi(conBean, this.request);
        //
        // // Get Customer Balance
        // try {
        // balance = new Double(api.findCreditorBalance(id));
        // } catch (CreditorException e) {
        // logger.log(Level.INFO,
        // "Customer balance could not be obtained...defaulting to zero.");
        // balance = new Double(0);
        // } finally {
        // api = null;
        // }
        return balance;
    }

    /**
     * Create the data entities needed to represent a new creditor.
     */
    public void add() throws ActionCommandException {
        // Create query to where creditor is not found
        int creditorId = -1;
        // this.fetchCreditor(creditorId);
    }

    /**
     * Fetches data pertaining to the creditor and its contact profile. The
     * contact profile consists of business and address information.
     */
    public void edit() throws ActionCommandException {
        // Create query to where creditor is not found in the event creditor
        // object is null
        int creditorId = -1;
        if (this.cred != null) {
            creditorId = ((Creditor) this.cred).getCreditorId();
        }
        // this.fetchCreditor(creditorId);
        return;
    }

    /**
     * Applies creditor updates to the database.
     * 
     * @throws ActionCommandException
     */
    public void save() throws ActionCommandException {
        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // CreditorApi api = CreditorFactory.createApi((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // // Apply Creditor Updates to database
        // try {
        // api.maintainCreditor((Creditor) this.cred, null);
        // tx.commitUOW();
        // } catch (CreditorException e) {
        // tx.rollbackUOW();
        // throw new ActionCommandException(e);
        // } finally {
        // tx.close();
        // api = null;
        // tx = null;
        // }
    }

    /**
     * No Action
     */
    public void delete() throws ActionCommandException {

    }

    /**
     * @return the cred
     */
    public Object getCred() {
        return cred;
    }

    /**
     * Fetches the list creditors from the database using the where clause
     * criteria previously stored on the session during the phase of the request
     * to builds the query predicate.
     * 
     * @param criteria
     *            {@link CreditorCriteria}
     * @throws ActionCommandException
     */
    protected void getCreditors(CreditorCriteria criteria) throws ActionCommandException {
        // Call SOAP web service to get a list of Creditors based on selection
        // criteria
        try {
            AccountingTransactionResponse response = CreditorSoapRequests.callGet(criteria, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            this.msg = rst.getMessage();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.msg = rst.getMessage();
                return;
            }
            List<Creditor> results = null;
            if (response.getProfile() != null && response.getProfile().getCreditors() != null) {
                results = CreditorFactory.create(response.getProfile().getCreditors().getCreditor());
            }
            else {
                results = new ArrayList<>();
            }
            this.creditors = results;
            this.sendClientData();
            return;
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of Creditor type records.
     * 
     * @return List<{@link CreditorType}>
     * @throws ActionCommandException
     */
    protected List<CreditorType> getCreditorTypes() throws ActionCommandException {
        // Call SOAP web service to get complete list of codes based on a
        // particular group
        try {
            AccountingTransactionResponse response = CreditorTypeSoapRequests.callGet(null, this.loginId, this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.msg = rst.getMessage();
                return null;
            }
            List<CreditorType> results = null;
            if (response.getProfile() != null) {
                results = CreditorTypeFactory.create(response.getProfile().getCreditorTypes());
            }
            return results;
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }
    }

    /**
     * Obtains key creditor data entered by the user from the request object
     */
    public void receiveClientData() throws ActionCommandException {
        try {
            this.cred = CreditorFactory.create();
            RMT2WebUtility.packageBean(this.request, this.cred);
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
     * <tr>
     * <td>BUSINESS TYPES</td>
     * <td>XML from contact application</td>
     * <td>Business Type data</td>
     * <td>businesstypes</td>
     * </tr>
     * <tr>
     * <td>BUSINESS SERVTYPES</td>
     * <td>XML from contact application</td>
     * <td>Business Service Type data</td>
     * <td>businessservicetypes</td>
     * </tr>
     * <tr>
     * <td>CREDITOR BALANCE</td>
     * <td>Double</td>
     * <td>Creditor's Balance</td>
     * <td>creditorbalance</td>
     * </tr>
     * <tr>
     * <td>MESSAGE</td>
     * <td>String</td>
     * <td>Server message</td>
     * <td>info</td>
     * </tr>
     * </table>
     * 
     * @throws ActionCommandException
     */
    public void sendClientData() throws ActionCommandException {
        this.request.setAttribute(AccountingConst.CLIENT_CREDITORTYPE_LIST, this.credTypeList);
        this.request.setAttribute(AccountingConst.CLIENT_DATA_CREDITOR, this.cred);
        this.request.setAttribute(AccountingConst.CLIENT_DATA_CREDEXTT, this.credExt);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.creditors);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_BUSINESS, this.credDetail);
        this.request.setAttribute(AccountingConst.CLIENT_BUSTYPES, this.busTypes);
        this.request.setAttribute(AccountingConst.CLIENT_BUSSERVTYPES, this.busServTypes);
        this.request.setAttribute(AccountingConst.CLIENT_CREDITORBAL, this.balance);
        this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }
}