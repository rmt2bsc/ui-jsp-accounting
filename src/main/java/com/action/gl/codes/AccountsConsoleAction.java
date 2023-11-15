package com.action.gl.codes;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.rmt2.jaxb.AccountingGeneralLedgerResponse;
import org.rmt2.jaxb.GlAccountcatgType;
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
import com.entity.VwAccount;
import com.entity.VwAccountFactory;
import com.entity.VwCategory;
import com.entity.VwCategoryFactory;

/**
 * Action handler provides functionality to respond to requests pertaining to
 * the account console page. The following request types are serviced: refresh,
 * category list, account list, account edit, account add, account delete, and
 * back.
 * 
 * @author Roy Terrell
 * 
 */
public class AccountsConsoleAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_ACCT_REFRESH = "AccountsConsole.Accounts.refresh";

    private static final String COMMAND_ACCT_CATGLIST = "AccountsConsole.Accounts.catglist";

    private static final String COMMAND_ACCT_LIST = "AccountsConsole.Accounts.list";

    private static final String COMMAND_ACCT_EDIT = "AccountsConsole.Accounts.edit";

    private static final String COMMAND_ACCT_ADD = "AccountsConsole.Accounts.add";

    private static final String COMMAND_DELETE = "App.Edit.delete";

    private static final String COMMAND_BACK = "App.Edit.back";

    private Logger logger;

    private VwAccount acct;

    private List acctTypeList;

    private VwCategory catg;

    private List catgList;

    private List acctList;

    private int acctTypeId;

    private String acctTypeDesc;

    private String acctCatgDesc;

    private int acctCatgId;

    /**
     * Default constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public AccountsConsoleAction() throws SystemException {
        super();
        logger = Logger.getLogger("AccountsConsoleAction");
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
     * Processes the client's requests to manage the General Ledger Account
     * Console: refresh, list categories, list accounts by category, edit
     * account, add account delete and return back to the main menu.
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
        if (command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_REFRESH)) {
            this.refreshConsole();
        }
        if (command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_CATGLIST)) {
            this.listCategories();
        }
        if (command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_LIST)) {
            this.listAccounts();
        }
        if (command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_EDIT)) {
            this.editData();
        }
        if (command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_ADD)) {
            this.addData();
        }
        if (command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_DELETE)) {
            this.deleteData();
        }
        if (command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_BACK)) {
            this.doBack();
        }
    }



    /**
     * Prepares the page for first time presentation by creating a new
     * {@link com.bean.GlAccountCategory GlAccountCategory}.
     * 
     * @throws ActionCommandException
     */
    private void refreshConsole() throws ActionCommandException {
        this.acctTypeList = this.getAccountTypes();
        List<GlAccountcatgType> items = new ArrayList<>();
        this.catgList = VwCategoryFactory.create(items);
        this.sendClientData();
    }

    /**
     * Uses account type id from the user's request to obtain a list of GL
     * Account Categories that will be* displayed on the account edit page when
     * an account type is selected.
     *
     * @throws ActionCommandException
     */
    private void listCategories() throws ActionCommandException {
        this.receiveClientData();
        this.catgList = this.getAccountCategories(this.acctTypeId);
        this.sendClientData();
    }

    /**
     * Uses data from the client's request to retrieve a list of accounts by
     * category id. This data is used to display the list of accounts when a
     * category is selected.
     * 
     * @throws ActionCommandException
     */
    private void listAccounts() throws ActionCommandException {
        this.receiveClientData();
        this.acctList = this.getAccounts(this.acctCatgId);

        this.catg = VwCategoryFactory.create();
        this.catg.setAccttypeid(this.acctTypeId);
        this.catg.setAcctcatid(this.acctCatgId);

        // Send data to client
        this.sendClientData();
    }


    /**
     * Prepares for a add operation by creating a new {@link com.bean.VwAccount
     * VwAccount} and populating it with {@link com.bean.VwCategory VwCategory}
     * data obtained from the database using category id. This object will be
     * displayed on GL Account Category Maintenance page for editing.
     * 
     * @throws ActionCommandException
     */
    public void add() throws ActionCommandException {
        this.receiveClientData();
    }

    /**
     * Obtains a single GL Account record to be displayed on the GL Account
     * Maintenance page for editing.
     * 
     * @throws ActionCommandException
     */
    public void edit() throws ActionCommandException {
        return;
    }

    /**
     * Navigates the user to the General Ledger main menu.
     * 
     * @throws ActionCommandException
     */
    protected void doBack() throws ActionCommandException {
        try {
            // Retrieve all application records from the database using unique
            // id.
            // this.data = this.api.getAll();
            this.sendClientData();
            this.request.removeAttribute(RMT2ServletConst.REQUEST_MSG_INFO);
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }
    }

    /**
     * Gathers data from the user's request and packages the data into the
     * appropriate data object (account type, account category, or account).
     * 
     * @throws ActionCommandException
     */
    protected void receiveClientData() throws ActionCommandException {
        String rowStr = this.request.getParameter("selCbx");
        this.selectedRow = 0;

        // Client must select a row to edit.
        if (rowStr == null && !this.command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_ADD)) {
            this.msg = "Client must select a row to edit";
            logger.error(this.msg);
            throw new ActionCommandException(this.msg);
        }
        else if (rowStr != null) {
            // Get index of the row that is to be processed from the
            // HttpServeltRequest object
            this.selectedRow = RMT2Money.stringToNumber(rowStr).intValue();
        }

        try {
            // Retrieve selected account type
            if (this.command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_CATGLIST)) {
                String temp = this.getInputValue("AcctTypeId", null);
                this.acctTypeId = RMT2Money.stringToNumber(temp).intValue();
                this.acctTypeDesc = this.getInputValue("Description", null);
            }

            // Retrieve Selected Category
            if (this.command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_LIST)) {
                String temp = this.getInputValue("AcctCatgId", null);
                this.acctCatgId = RMT2Money.stringToNumber(temp).intValue();
                temp = this.getInputValue("AcctTypeId", null);
                this.acctTypeId = RMT2Money.stringToNumber(temp).intValue();
                this.acctCatgDesc = this.getInputValue("Acctcatgdescr", null);
                this.acctTypeDesc = this.getInputValue("Accttypedescr", null);
            }

            // Retrieve account record from JSP for edit.
            if (this.command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_EDIT)) {
                this.acct = VwAccountFactory.create();
                RMT2WebUtility.packageBean(this.request, this.acct, this.selectedRow);
            }

            if (this.command.equalsIgnoreCase(AccountsConsoleAction.COMMAND_ACCT_ADD)) {
                this.acct = VwAccountFactory.create();
                RMT2WebUtility.packageBean(this.request, this.acct);
            }
        } catch (Exception e) {
            throw new ActionCommandException(e);
        }
    }

    /**
     * Sends GL Account data from the GL Account console page in various forms
     * to the client as data objects and any server messages via the request
     * object. A single account type record, a single account category record, a
     * single account record, a list of accounts by category, a list of account
     * categories by account type, and any server messages as <i>acctType</i>,
     * <i>acctCatg</i>, {@link GeneralConst#CLIENT_DATA_RECORD
     * CLIENT_DATA_RECORD}, {@link GeneralConst#CLIENT_DATA_LIST
     * CLIENT_DATA_LIST}, <i>catgList</i>, and
     * {@linkRMT2ServletConst#REQUEST_MSG_INFO REQUEST_MSG_INFO}, respectively.
     * 
     * @throws ActionCommandException
     */
    protected void sendClientData() throws ActionCommandException {
        this.session.setAttribute(AccountingConst.CLIENT_DATA_ACCTTYPE_LIST, this.acctTypeList);
        this.request.setAttribute("acctCatg", this.catg);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.acct);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.acctList);
        this.request.setAttribute(AccountingConst.CLIENT_DATA_ACCTCATG_LIST, this.catgList);
        this.request.setAttribute("AcctTypeDesc", this.acctTypeDesc);
        this.request.setAttribute("AcctCatgDesc", this.acctCatgDesc);
        this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
    }

    /**
     * Retrieves all Account Type records.
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
     * Drives the process of fetching the list of GL Account Type Categories
     * 
     * @param acctTypeId
     *            the account type id belonging to the selected row.
     * @return List<{@link VwCategory}
     * @throws ActionCommandException
     */
    private List<VwCategory> getAccountCategories(int acctTypeId) throws ActionCommandException {
        GeneralLedgerCriteria criteria = new GeneralLedgerCriteria();
        criteria.setAcctTypeId(acctTypeId);

        // Call SOAP web service to get complete list of GL Account Categories
        // based on a particular GL Account Type
        try {
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
     * Drives the process of fetching the list of GL Accounts
     * 
     * @param acctCatgId
     *            the account category id belonging to the selected row.
     * @return List<{@link VwAccount}
     * @throws ActionCommandException
     */
    private List<VwAccount> getAccounts(int acctCatgId) throws ActionCommandException {
        VwAccount criteria = VwAccountFactory.create();
        criteria.setAcctCatId(acctCatgId);

        // Call SOAP web service to get a list of GL Accounts by account
        // category
        try {
            AccountingGeneralLedgerResponse response = VwAccountSoapRequests.callGet(criteria, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            this.msg = rst.getMessage();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.msg = rst.getMessage();
                return null;
            }
            List<VwAccount> results = null;
            if (response.getProfile() != null) {
                results = VwAccountFactory.create(response.getProfile().getAccount());
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
     * 
     * @throws ActionCommandException
     */
    public void save() throws ActionCommandException {
        return;
    }

    /**
     * No Action
     * 
     * @throws ApplicationException
     */
    public void delete() throws ActionCommandException {
        return;
    }

}