package com.action.gl.codes;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.rmt2.jaxb.AccountingGeneralLedgerResponse;
import org.rmt2.jaxb.ReplyStatusType;

import com.AccountingConst;
import com.AccountingUIException;
import com.SystemException;
import com.api.constants.GeneralConst;
import com.api.constants.RMT2ServletConst;
import com.api.jsp.action.AbstractActionHandler;
import com.api.util.RMT2Money;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.ICommand;
import com.api.web.Request;
import com.api.web.Response;
import com.api.web.util.RMT2WebUtility;
import com.entity.GeneralLedgerCriteria;
import com.entity.GlAccountTypes;
import com.entity.GlAccountTypesFactory;
import com.entity.VwCategory;
import com.entity.VwCategoryFactory;

/**
 * Action handler provides functionality to respond to requests pertaining to
 * the account category console page. The following request types are serviced:
 * refresh, list, edit, add, delete, and back.
 * 
 * @author Roy Terrell
 * 
 */
public class CatgConsoleAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_ACCTTYPECATG_REFRESH = "AcctCatgConsole.AcctTypeCatg.refresh";

    private static final String COMMAND_ACCTTYPECATG_LIST = "AcctCatgConsole.AcctTypeCatg.list";

    private static final String COMMAND_ACCTTYPECATG_EDIT = "AcctCatgConsole.AcctTypeCatg.edit";

    private static final String COMMAND_ACCTTYPECATG_ADD = "AcctCatgConsole.AcctTypeCatg.add";

    private static final String COMMAND_DELETE = "App.Edit.delete";

    private static final String COMMAND_BACK = "App.Edit.back";

    private Logger logger;

    private Object acctTypeList;

    private GlAccountTypes acctType;

    private VwCategory catg;

    private Object acctCatgList;

    private int acctTypeId;

    /**
     * Default constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public CatgConsoleAction() throws SystemException {
        super();
        logger = Logger.getLogger("CatgConsoleAction");
    }

    /**
     * Performs post initialization and sets up an BasicGLApi reference.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
        super.init(_context, _request);
    }

    /**
     * Processes the client's requests to manage the GL Account Category console
     * page.
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
        if (command.equalsIgnoreCase(CatgConsoleAction.COMMAND_ACCTTYPECATG_REFRESH)) {
            this.refreshConsole();
        }
        if (command.equalsIgnoreCase(CatgConsoleAction.COMMAND_ACCTTYPECATG_LIST)) {
            this.listCategories();
        }
        if (command.equalsIgnoreCase(CatgConsoleAction.COMMAND_ACCTTYPECATG_EDIT)) {
            this.editData();
        }
        if (command.equalsIgnoreCase(CatgConsoleAction.COMMAND_ACCTTYPECATG_ADD)) {
            this.addData();
        }
        if (command.equalsIgnoreCase(CatgConsoleAction.COMMAND_DELETE)) {
            this.deleteData();
        }
        if (command.equalsIgnoreCase(CatgConsoleAction.COMMAND_BACK)) {
            this.doBack();
        }
    }



    /**
     * Prepares the page for first time presentation by creating a new
     * {@link com.bean.GlAccountType GlAccountType} and an empty List of
     * representing Account Categories.
     * 
     * @throws ActionCommandException
     */
    protected void refreshConsole() throws ActionCommandException {
        this.acctTypeList = this.getAccountTypes();
        this.acctCatgList = new ArrayList();
        this.sendClientData();
    }

    /**
     * Uses account type id from the user's request to obtain a list of GL
     * Account Categories that will be* displayed on the account edit page when
     * an account type is selected.
     *
     * @throws ActionCommandException
     */
    protected void listCategories() throws ActionCommandException {
        this.receiveClientData();
        this.acctCatgList = this.getAccountCategories();
        
        // This logic is used to capture the account type description field
        // since this field is not available in the GL Account Type List HTML
        // Frame (GlAccountType ORM class)
        List<VwCategory> list = (List<VwCategory>) this.acctCatgList;
        String temp = this.getInputValue("Description", null);
        for (VwCategory item : list) {
            item.setAccttypedescr(temp);
        }

        // Send data to client
        this.sendClientData();
    }

    /**
     * Drives the process of fetching the list of GL Account Type Categories
     * 
     * @return List<{@link VwCategory}
     * @throws ActionCommandException
     */
    private List<VwCategory> getAccountCategories() throws ActionCommandException {
        int acctTypeId = this.acctType.getAcctTypeId();
        GeneralLedgerCriteria criteria = new GeneralLedgerCriteria();
        criteria.setAcctTypeId(acctTypeId);

        // Call SOAP web service to get complete list of GL Account Categories
        // based on a
        // particular GL Account Type
        try {
            // UI-37: Added parameters, login id and session id to callGet
            // method.
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
     * Retrieves all Country records.
     * 
     * @return List<{@link Country}>
     * @throws AccountingUIException
     */
    private List<GlAccountTypes> getAccountTypes() throws AccountingUIException {
        try {
            // UI-37: added login id and session id parameters to the callSave
            // method invocation
            AccountingGeneralLedgerResponse response = GlAccountTypesSoapRequests.callGet(this.loginId, this.session.getId());

            // Get message text from reply status
            ReplyStatusType rst = response.getReplyStatus();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.msg = rst.getMessage();
                return null;
            }

            List<GlAccountTypes> results = null;
            if (response.getProfile() != null && response.getProfile().getAccountType() != null) {
                results = GlAccountTypesFactory.create(response.getProfile().getAccountType());
            }
            else {
                results = new ArrayList<>();
            }
            return results;
        } catch (AccountingUIException e) {
            throw e;
        }
    }

    /**
     * Prepares for a add operation by creating a new
     * {@link com.bean.VwCategory VwCategory} and populating it with
     * {@link com.bean.GlAccountTypes GlAccountTypes} data obtained from the
     * database using account type id. This object will be displayed on GL
     * Account Category Maintenance page for editing.
     * 
     * @throws ActionCommandException
     */
    public void add() throws ActionCommandException {
        this.receiveClientData();
        this.catg = VwCategoryFactory.create();
        this.catg.setAccttypeid(this.acctType.getAcctTypeId());
    }

    /**
     * Obtains a single GL Account Category object fro the database using the
     * category id from the client. This object will be used to display an
     * account category record on the GL Account Category Maintenance page for
     * editing.
     * 
     * @throws ActionCommandException
     */
    public void edit() throws ActionCommandException {
        this.receiveClientData();

        // Capture the GL Account Type data from the VwCategory objects
        // contained in the HTML 'EditFrame'
        this.acctType = GlAccountTypesFactory.create();
        this.acctType.setAcctTypeId(this.catg.getAccttypeid());
        this.acctType.setDescription(this.catg.getAccttypedescr());
    }

    /**
     * Gathers data from the user's request and packages the data into the
     * appropriate data object (account type or account category).
     * 
     * @throws ActionCommandException
     */
    protected void receiveClientData() throws ActionCommandException {
        String rowStr = this.request.getParameter("selCbx");
        int selectedRow = 0;

        // Client must select a row to edit.
        if (rowStr == null) {
            throw new ActionCommandException("Client must select a row to edit");
        }

        try {
            // Get index of the row that is to be processed from the
            // HttpServeltRequest object
            selectedRow = RMT2Money.stringToNumber(rowStr).intValue();

            // Retrieve values from the request object into the User object.
            if (this.command.equalsIgnoreCase(CatgConsoleAction.COMMAND_ACCTTYPECATG_LIST)
                    || this.command.equalsIgnoreCase(CatgConsoleAction.COMMAND_ACCTTYPECATG_ADD)) {
                this.acctType = GlAccountTypesFactory.create();
                try {
                    // Update criteria object with user input.
                    RMT2WebUtility.packageBean(this.request, this.acctType, selectedRow);
                } catch (Exception e) {
                    logger.log(Level.ERROR, e.getMessage());
                    throw new ActionCommandException(e.getMessage());
                }
            }

            // Retrieve account category record for edit.
            if (this.command.equalsIgnoreCase(CatgConsoleAction.COMMAND_ACCTTYPECATG_EDIT)) {
                this.catg = VwCategoryFactory.create();
                try {
                    // Update criteria object with user input.
                    RMT2WebUtility.packageBean(this.request, this.catg, selectedRow);
                } catch (Exception e) {
                    logger.log(Level.ERROR, e.getMessage());
                    throw new ActionCommandException(e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new ActionCommandException(e);
        }
    }

    /**
     * Sends GL Account Category data from the GL Account Category console page
     * in various forms to the client as data objects and any server messages
     * via the request object.
     * <p>
     * A single account type object, a single account category object, a list of
     * account category objects, and any server messages as
     * {@link AccountingConst#CLIENT_DATA_ACCTYPE_LIST},
     * {@link AccountingConst#CLIENT_DATA_ACCTYPE_RECORD},
     * {@link GeneralConst#CLIENT_DATA_RECORD CLIENT_DATA_RECORD},
     * {@link GeneralConst#CLIENT_DATA_LIST CLIENT_DATA_LIST}, and
     * {@linkRMT2ServletConst#REQUEST_MSG_INFO REQUEST_MSG_INFO}, respectively.
     * 
     * @throws ActionCommandException
     */
    protected void sendClientData() throws ActionCommandException {
        this.session.setAttribute(AccountingConst.CLIENT_DATA_ACCTYPE_LIST, this.acctTypeList);
        this.session.setAttribute(AccountingConst.CLIENT_DATA_ACCTYPE_RECORD, this.acctType);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.catg);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.acctCatgList);
        this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

    /**
     * No Action
     * 
     * @throws ActionCommandException
     */
    public void save() throws ActionCommandException {
        return;
    }

    /**
     * No Action
     * 
     * @throws ActionCommandException
     */
    protected void doBack() throws ActionCommandException {
        return;
    }

    /**
     * No Action
     * 
     * @throws ActionCommandException
     */
    public void delete() throws ActionCommandException {
        return;
    }

}