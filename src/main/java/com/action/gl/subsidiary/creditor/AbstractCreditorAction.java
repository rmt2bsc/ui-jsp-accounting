package com.action.gl.subsidiary.creditor;

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
import com.entity.Creditor;
import com.entity.CreditorCriteria;
import com.entity.CreditorFactory;
import com.entity.CreditorType;
import com.entity.CreditorTypeFactory;

/**
 * This abstract class provides common functionality needed to serve various
 * user interfaces pertaining to Creditor.
 * 
 * @author Roy Terrell
 * 
 */
public abstract class AbstractCreditorAction extends AbstractSubsidiaryAction implements ICommand {
    
    private Logger logger;

    /** An ArrayList of Creditors */
    protected List<Creditor> creditors;

    /** Creditor */
    protected Creditor cred;

    /** Creditor's Business profile */
    private Object credDetail;

    protected List credTypeList;

    protected int creditorId;


    /**
     * Default constructor
     * 
     */
    public AbstractCreditorAction() {
        super();
        logger = Logger.getLogger(AbstractCreditorAction.class);
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
    public AbstractCreditorAction(Context _context, Request _request) throws SystemException, DatabaseException {
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
     * @return List<{@link Creditor}>
     * @throws ActionCommandException
     */
    protected List<Creditor> getCreditors(CreditorCriteria criteria) throws ActionCommandException {
        // Call SOAP web service to get a list of Creditors based on selection
        // criteria
        try {
            AccountingTransactionResponse response = CreditorSoapRequests.callGet(criteria, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            this.msg = rst.getMessage();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.throwActionError(rst.getMessage(), rst.getExtMessage());
            }
            List<Creditor> results = null;
            if (response.getProfile() != null && response.getProfile().getCreditors() != null) {
                results = CreditorFactory.create(response.getProfile().getCreditors().getCreditor());
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
                this.throwActionError(rst.getMessage(), rst.getExtMessage());
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
     * Retrieves a lists of creditor types and other useful lookup data sets.
     * 
     * @throws ActionCommandException
     */
    @Override
    protected void setupLookupData() throws ActionCommandException {
        super.setupLookupData();
        this.credTypeList = this.getCreditorTypes();
    }

    /**
     * Obtains common key creditor related data items from the client JSP.
     */
    public void receiveClientData() throws ActionCommandException {
        super.receiveClientData();

        // Attempt to locate and obtain creditor ID from the JSP.
        String temp = this.getInputValue("CreditorId", null);
        this.creditorId = RMT2Money.stringToNumber(temp).intValue();

        // Attempt to locate and obtain creditor profile data from the JSP.
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
     * </table>
     * 
     * @throws ActionCommandException
     */
    public void sendClientData() throws ActionCommandException {
        super.sendClientData();
        this.request.setAttribute(AccountingConst.CLIENT_CREDITORTYPE_LIST, this.credTypeList);
        this.request.setAttribute(AccountingConst.CLIENT_DATA_SUBSIDIARY, this.cred);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.creditors);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_BUSINESS, this.credDetail);
    }
}