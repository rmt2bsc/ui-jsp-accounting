package com.action.gl.subsidiary;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.rmt2.jaxb.LookupCodesResponse;
import org.rmt2.jaxb.ReplyStatusType;

import com.AccountingConst;
import com.SystemException;
import com.api.constants.GeneralConst;
import com.api.constants.RMT2ServletConst;
import com.api.jsp.action.AbstractActionHandler;
import com.api.persistence.DatabaseException;
import com.api.util.RMT2Money;
import com.api.util.RMT2String2;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.ICommand;
import com.api.web.Request;
import com.api.web.Response;
import com.entity.GeneralCodes;
import com.entity.GeneralCodesFactory;

/**
 * This abstract class provides common GL Subsidiary functionality needed to
 * serve various descendant user interfaces.
 * 
 * @author Roy Terrell
 * 
 */
public abstract class AbstractSubsidiaryAction extends AbstractActionHandler implements ICommand {
    private Logger logger;
    protected Double balance;
    private Object lookupBusType;
    private Object lookupBusServ;

    /**
     * Default constructor
     * 
     */
    public AbstractSubsidiaryAction() {
        super();
        logger = Logger.getLogger(AbstractSubsidiaryAction.class);
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
    public AbstractSubsidiaryAction(Context _context, Request _request) throws SystemException, DatabaseException {
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
     * Retrieves a lists of General Code records based on general code groups
     * Business Entity Type and Business Services Type.
     * 
     * @throws ActionCommandException
     */
    protected void setupLookupData() throws ActionCommandException {
        this.lookupBusServ = this.getLookupData(AccountingConst.CODEGROUP_KEY_BUS_SERV);
        this.lookupBusType = this.getLookupData(AccountingConst.CODEGROUP_KEY_BUS_TYPE);
    }
    
    /**
     * Retrieves a list of General Code records based on general code group.
     * 
     * @param codeGroupId
     *            the id of the group to gather general codes.
     * @return List<{@link GeneralCodes}>
     * @throws ActionCommandException
     */
    private List<GeneralCodes> getLookupData(int codeGroupId) throws ActionCommandException {
        GeneralCodes code = GeneralCodesFactory.create();
        code.setCodeGrpId(codeGroupId);

        // Call SOAP web service to get complete list of codes based on a
        // particular group
        try {
            // UI-37: added login id and session id parameters to the callSave
            // method invocation
            LookupCodesResponse response = CodeSoapRequests.callGet(code, this.loginId, this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.msg = rst.getMessage();
                return null;
            }
            List<GeneralCodes> results = GeneralCodesFactory.create(response.getDetailCodes());
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
        // Attempt to locate and obtain current selected row on JSP.
        String rowStr = this.request.getParameter("selCbx");
        this.selectedRow = 0;
        if (RMT2String2.isNotEmpty(rowStr)) {
            // Get index of the row that is to be processed from the
            // HttpServeltRequest object
            this.selectedRow = RMT2Money.stringToNumber(rowStr).intValue();
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
     * <td>BUSINESS ENTITY TYPES</td>
     * <td>XML from contact application</td>
     * <td>Business Entity Type data</td>
     * <td>businesstypes</td>
     * </tr>
     * <tr>
     * <td>BUSINESS SERVTYPES</td>
     * <td>XML from contact application</td>
     * <td>Business Service Type data</td>
     * <td>businessservicetypes</td>
     * </tr>
     * <tr>
     * <td>SUBSIDIARY BALANCE</td>
     * <td>Double</td>
     * <td>Subsidiary's Balance</td>
     * <td>balance</td>
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
        this.request.setAttribute(AccountingConst.CLIENT_BUSTYPES, this.lookupBusType);
        this.request.setAttribute(AccountingConst.CLIENT_BUSSERVTYPES, this.lookupBusServ);
        this.request.setAttribute(AccountingConst.CLIENT_SUBSIDIARYBALANCE, this.balance);
        this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

}