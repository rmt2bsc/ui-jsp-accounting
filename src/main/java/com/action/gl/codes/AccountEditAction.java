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
import com.api.persistence.db.DatabaseConnectionBean;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.ICommand;
import com.api.web.Request;
import com.api.web.Response;
import com.api.web.util.RMT2WebUtility;
import com.entity.VwAccount;
import com.entity.VwAccountFactory;

/**
 * Action handler provides functionality to respond to requests pertaining to
 * the General Ledger Account Maintenance page. The following request types are
 * serviced: save , delete an application, and back.
 * 
 * @author Roy Terrell
 * 
 */
public class AccountEditAction extends AbstractActionHandler implements ICommand {
    private static final String COMMAND_SAVE = "Account.Edit.save";

    private static final String COMMAND_DELETE = "Account.Edit.delete";

    private static final String COMMAND_BACK = "Account.Edit.back";

    private static Logger logger = Logger.getLogger("AccountEditAction");

    // private BasicGLApi api;

    private VwAccount acct;

    private Object catg;

    private List acctList;

    /**
     * Default constructor responsible for initializing the logger.
     * 
     * @throws SystemException
     */
    public AccountEditAction() throws SystemException {
        super();
    }

    /**
     * Performs post initialization and sets up a BasciGLApi reference.
     * 
     * @throws SystemException
     */
    protected void init(Context _context, Request _request) throws SystemException {
        super.init(_context, _request);
    }

    /**
     * Processes the client's requests to save changes made to a GL Account,
     * delete a GL Account, and to navigate back to the GL Account List page.
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
        if (command.equalsIgnoreCase(AccountEditAction.COMMAND_SAVE)) {
            this.saveData();
        }
        if (command.equalsIgnoreCase(AccountEditAction.COMMAND_DELETE)) {
            this.deleteData();
        }
        if (command.equalsIgnoreCase(AccountEditAction.COMMAND_BACK)) {
            this.doBack();
        }
    }


    /**
     * Creates a new or modifies an exising GlAccount record by persiting
     * changes to the database.
     * 
     * @throws ActionCommandException
     */
    public void save() throws ActionCommandException {

        // Call SOAP web service to persist GL Account Category data changes to
        // the database
        try {
            AccountingGeneralLedgerResponse response = VwAccountSoapRequests.callSave(this.acct, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            this.msg = rst.getMessage();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.msg = rst.getMessage();
                return;
            }
            List<VwAccount> results = null;
            if (response.getProfile() != null) {
                results = VwAccountFactory.create(response.getProfile().getAccount());
                // For display purposes, update the account id value to capture
                // the primary key value for the current record that was updated
                this.acct.setId(results.get(0).getId());
                this.acct.setAcctNo(results.get(0).getAcctNo());
            }
            else {
                this.acct = VwAccountFactory.create();
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }
    }

    /**
     * Delete an general ledger account from the database using the account id.
     * 
     * @param appId
     *            The id of the account
     * @return Total number of rows deleted.
     * @throws ActionCommandException
     */
    public void delete() throws ActionCommandException {
        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // BasicGLApi api =
        // GeneralLedgerFactory.createBasicGLApi((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // this.refreshPage((DatabaseConnectionBean) tx.getConnector());
        // try {
        // api.deleteAccount(((VwAccount) this.acct).getId());
        // tx.commitUOW();
        // this.msg = "GL Account was deleted successfully";
        // return;
        // } catch (GLException e) {
        // tx.rollbackUOW();
        // this.msg = "Error occurred deleting  GL Accoung";
        // logger.error(this.msg);
        // throw new ActionCommandException(this.msg, e);
        // } finally {
        // api.close();
        // tx.close();
        // api = null;
        // tx = null;
        // }
    }

    /**
     * Navigates the user to the general ledger account list page.
     * 
     * @throws ActionCommandException
     */
    protected void doBack() throws ActionCommandException {
        this.receiveClientData();
        this.acctList = this.getAccounts(this.acct.getAcctCatId());
        this.sendClientData();
        this.msg = null;
    }

    /**
     * Retrieves the current account record in extended form after an insert or
     * an update operation.
     * 
     * @param conBean
     *            The database connection
     * @throws ActionCommandException
     */
    private void refreshPage(DatabaseConnectionBean conBean) throws ActionCommandException {
        // BasicGLApi api = GeneralLedgerFactory.createBasicGLApi(conBean,
        // this.request);
        // int id = 0;
        // try {
        // // Get account view object.
        // id = ((GlAccounts) this.acct).getAcctId();
        // this.acct = api.findByIdExt(id);
        // } catch (GLException e) {
        // this.msg = "Error occurred retrieving data to refresh GL account, " +
        // id;
        // logger.error(this.msg);
        // throw new ActionCommandException(this.msg, e);
        // } finally {
        // // Do not close api since its connection object is shared with the
        // // caller
        // api = null;
        // }
    }

    /**
     * Obtains a list of {@link com.bean.VwAccount VwAccount} objects by
     * category id which will be presented on the Account list page when the
     * <i>Back</i> button is clicked.
     * 
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
     * Gathers data from the user's request and packages the GL Account data
     * into a data object.
     * 
     * @throws ActionCommandException
     */
    protected void receiveClientData() throws ActionCommandException {
        this.acct = VwAccountFactory.create();
        try {
            RMT2WebUtility.packageBean(this.request, this.acct);
        } catch (SystemException e) {
            throw new ActionCommandException(e);
        }
    }

    /**
     * Sends GL Account data in various forms to the client as data objects and
     * any server messages via the request object. A single account record, a
     * single account category record, a list of accounts, and any server
     * messages are placed onto the request as
     * {@link GeneralConst#CLIENT_DATA_RECORD CLIENT_DATA_RECORD},
     * <i>acctCatg</i>, {@link GeneralConst#CLIENT_DATA_LIST CLIENT_DATA_LIST},
     * and {@linkRMT2ServletConst#REQUEST_MSG_INFO REQUEST_MSG_INFO},
     * respectively.
     * 
     * @throws ActionCommandException
     */
    protected void sendClientData() throws ActionCommandException {
        this.request.setAttribute(GeneralConst.CLIENT_DATA_RECORD, this.acct);
        this.request.setAttribute("acctCatg", this.catg);
        this.request.setAttribute(GeneralConst.CLIENT_DATA_LIST, this.acctList);
        this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO, this.msg);
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

}