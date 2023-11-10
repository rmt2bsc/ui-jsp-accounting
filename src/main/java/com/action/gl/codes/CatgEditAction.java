package com.action.gl.codes;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.rmt2.jaxb.AccountingGeneralLedgerResponse;
import org.rmt2.jaxb.ReplyStatusType;

import com.SystemException;
import com.api.constants.GeneralConst;
import com.api.constants.RMT2ServletConst;
import com.api.jsp.action.AbstractActionHandler;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.ICommand;
import com.api.web.Request;
import com.api.web.Response;
import com.api.web.util.RMT2WebUtility;
import com.entity.GeneralLedgerCriteria;
import com.entity.VwCategory;
import com.entity.VwCategoryFactory;

/**
 * Action handler provides functionality to respond to requests pertaining to
 * the GL Account Category edit page. The following request types are serviced:
 * save, delete, and back.
 * 
 * @author Roy Terrell
 * 
 */
public class CatgEditAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_SAVE = "AcctCategory.Edit.save";

    private static final String COMMAND_DELETE = "AcctCategory.Edit.delete";

    private static final String COMMAND_BACK = "AcctCategory.Edit.back";

    private Logger logger;

    private Object catg;

    private Object acctType;

    private List acctCatgList;

    /**
     * Default constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public CatgEditAction() throws SystemException {
        super();
        logger = Logger.getLogger("CatgEditAction");
    }

    /**
     * Performs post initialization and sets up an BasciGLApi reference.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
        super.init(_context, _request);
    }

    /**
     * Processes the client's requests to save changes made to a GL Account
     * Category, delete a GL Account Category, and to navigate back to the GL
     * Account Category List page.
     * 
     * @param request
     *            The HttpRequest object
     * @param response
     *            The HttpResponse object
     * @param command
     *            Comand issued by the client.
     * @Throws SystemException when an error needs to be reported.
     */
    public void processRequest(Request request, Response response, String command) throws ActionCommandException {
        super.processRequest(request, response, command);
        if (command.equalsIgnoreCase(CatgEditAction.COMMAND_SAVE)) {
            this.saveData();
        }
        if (command.equalsIgnoreCase(CatgEditAction.COMMAND_DELETE)) {
            this.deleteData();
        }
        if (command.equalsIgnoreCase(CatgEditAction.COMMAND_BACK)) {
            this.doBack();
        }
    }

    /**
     * Creates a new or modifies an existing GL Account Category record by
     * persisting changes to the database.
     * 
     * @throws ActionCommandException
     */
    public void save() throws ActionCommandException {
        // Call SOAP web service to persist GL Account Category data changes to
        // the database
        try {
            // UI-37: added login id and session id parameters to the callSave
            // method invocation
            AccountingGeneralLedgerResponse response = VwCategorySoapRequests.callSave((VwCategory) this.catg, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            this.msg = rst.getMessage();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.msg = rst.getMessage();
                return;
            }
            List<VwCategory> results = null;
            if (response.getProfile() != null) {
                results = VwCategoryFactory.create(response.getProfile().getAccountCategory());
                // This line of code is needed for new categories.
                this.catg = results.get(0);
            }
            else {
                results = new ArrayList<>();
            }
            return;
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }
    }

    /**
     * Delete a GL Account Category record from the database using the id of the
     * category.
     * 
     * @param appId
     *            The id of the application
     * @return Total number of rows deleted.
     * @throws ApplicationException
     */
    public void delete() throws ActionCommandException {
        // Call SOAP web service to delete GL Account Category data changes to
        // the database
        try {
            // UI-37: added login id and session id parameters to the callSave
            // method invocation
            AccountingGeneralLedgerResponse response = VwCategorySoapRequests.callDelete((VwCategory) this.catg, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            this.msg = rst.getMessage();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.msg = rst.getMessage();
                return;
            }
            return;
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }
    }

    /**
     * Navigates the user to the Account Category List page.
     * 
     * @throws ActionCommandException
     */
    protected void doBack() throws ActionCommandException {
        this.receiveClientData();
        this.acctCatgList = this.getAccountCategories();
        this.sendClientData();
        this.msg = null;
    }

    /**
     * Drives the process of fetching the list of GL Account Type Categories
     * 
     * @return List<{@link VwCategory}
     * @throws ActionCommandException
     */
    private List<VwCategory> getAccountCategories() throws ActionCommandException {
        int acctTypeId = ((VwCategory) this.catg).getAccttypeid();
        GeneralLedgerCriteria criteria = new GeneralLedgerCriteria();
        criteria.setAcctTypeId(acctTypeId);

        // Call SOAP web service to get complete list of GL Account Categories
        // based on a
        // particular GL Account Type
        try {
            // UI-37: added login id and session id parameters to the callSave
            // method invocation
            AccountingGeneralLedgerResponse response = VwCategorySoapRequests.callGet(criteria, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            this.msg = rst.getMessage();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.msg = rst.getMessage();
                return null;
            }
            List<VwCategory> results = null;
            if (response.getProfile() != null) {
                results = VwCategoryFactory.create(response.getProfile().getAccountCategory());
            }
            else {
                results = new ArrayList<>();
            }
            return results;
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }
    }

    /**
     * No Action
     */
    public void edit() throws ActionCommandException {
        return;
    }

    /**
     * No Action
     */
    public void add() throws ActionCommandException {
        return;
    }

    /**
     * Gathers data from the user's request and packages the data into a single
     * GL Account Category object.
     * 
     * @throws ActionCommandException
     */
    protected void receiveClientData() throws ActionCommandException {
        this.catg = VwCategoryFactory.create();
        try {
            // Update VwCategory object with user input.
            RMT2WebUtility.packageBean(this.request, this.catg);
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }
    }

    /**
     * Sends GL Account Category data in various forms to the client as data
     * objects and any server messages via the request object. A single account
     * category record, a list of account category object, and any server
     * messages are placed onto the request as <i>acctType</i>,
     * {@link GeneralConst#CLIENT_DATA_RECORD CLIENT_DATA_RECORD},
     * {@link GeneralConst#CLIENT_DATA_LIST CLIENT_DATA_LIST}, and
     * {@linkRMT2ServletConst#REQUEST_MSG_INFO REQUEST_MSG_INFO}, respectively.
     * 
     * @throws ActionCommandException
     */
    protected void sendClientData() throws ActionCommandException {
        this.request.setAttribute("acctType", this.acctType);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.catg);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.acctCatgList);
        this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }
}