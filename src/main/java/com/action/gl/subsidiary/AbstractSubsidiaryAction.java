package com.action.gl.subsidiary;

import org.apache.log4j.Logger;

import com.AccountingConst;
import com.SystemException;
import com.api.jsp.action.AbstractActionHandler;
import com.api.persistence.DatabaseException;
import com.api.util.RMT2Money;
import com.api.util.RMT2String2;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.ICommand;
import com.api.web.Request;
import com.api.web.Response;

/**
 * This abstract class provides common functionality needed to serve various
 * user interfaces pertaining to Creditor maintenance.
 * 
 * @author Roy Terrell
 * 
 */
public abstract class AbstractSubsidiaryAction extends AbstractActionHandler implements ICommand {
    private Logger logger;

    protected Double balance;

    private Object busEntityTypes;

    private Object busServTypes;



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
     * </table>
     * 
     * @throws ActionCommandException
     */
    public void sendClientData() throws ActionCommandException {
        this.request.setAttribute(AccountingConst.CLIENT_BUSTYPES, this.busEntityTypes);
        this.request.setAttribute(AccountingConst.CLIENT_BUSSERVTYPES, this.busServTypes);
        this.request.setAttribute(AccountingConst.CLIENT_SUBSIDIARYBALANCE, this.balance);
    }
}