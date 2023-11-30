package com.action.gl.subsidiary.creditor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.SystemException;
import com.api.constants.GeneralConst;
import com.api.constants.RMT2ServletConst;
import com.api.persistence.DatabaseException;
import com.api.security.RMT2TagQueryBean;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.Request;
import com.api.web.Response;
import com.api.web.util.RMT2WebUtility;
import com.entity.Creditor;
import com.entity.CreditorCriteria;

/**
 * This class provides functionality needed to serve the requests of the
 * Creditor Search user interface
 * 
 * @author Roy Terrell
 * 
 */
public class CreditorSearchAction extends AbstractCreditorAction {
    private static final String COMMAND_NEWSEARCH = "Creditor.Search.newsearch";

    private static final String COMMAND_SEARCH = "Creditor.Search.search";

    private static final String COMMAND_EDIT = "Creditor.Search.edit";

    private static final String COMMAND_ADD = "Creditor.Search.add";

    private static final String COMMAND_BACK = "Creditor.Search.back";

    private static final int CRITERIATYPE_CONTACT = 100;

    private static final int CRITERIATYPE_CREDITOR = 200;

    private static final int CRITERIATYPE_ALL = 300;

    private static Logger logger;

    private int criteriaType;



    /**
     * Default constructor
     * 
     */
    public CreditorSearchAction() {
        super();
        CreditorSearchAction.logger = Logger.getLogger(CreditorSearchAction.class);
        this.criteriaType = CreditorSearchAction.CRITERIATYPE_ALL;
    }

    /**
     * Creates a CreditorSearchAction that is aware of the Application context
     * and user's request.
     * 
     * @param context
     *            The servlet context to be associated with this action handler
     * @param request
     *            The request object sent by the client to be associated with
     *            this action handler
     * @throws SystemException
     * @throws DatabaseException
     */
    public CreditorSearchAction(Context context, Request request) throws SystemException, DatabaseException {
        super(context, request);
        this.init(this.context, this.request);
    }

    /**
     * Set the response object.
     * 
     * @param response
     */
    public void setResponse(Response response) {
        this.response = response;
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
        if (command.equalsIgnoreCase(CreditorSearchAction.COMMAND_NEWSEARCH)) {
            this.doNewSearch();
        }
        if (command.equalsIgnoreCase(CreditorSearchAction.COMMAND_SEARCH)) {
            this.doSearch();
        }
        if (command.equalsIgnoreCase(CreditorSearchAction.COMMAND_ADD)) {
            this.addData();
        }
        if (command.equalsIgnoreCase(CreditorSearchAction.COMMAND_EDIT)) {
            this.editData();
        }
        if (command.equalsIgnoreCase(CreditorSearchAction.COMMAND_BACK)) {
            this.doBack();
        }
    }

    /**
     * Handler method that responds to the client's request to display a new
     * creditor search page.
     * 
     * @throws ActionCommandException
     */
    protected void doNewSearch() throws ActionCommandException {
        this.setFirstTime(true);
        this.startSearchConsole();
        this.creditors = new ArrayList();
        this.credTypeList = this.getCreditorTypes();
        this.query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
        this.sendClientData();
    }

    /**
     * Handler method that responds to the client's request to perform a
     * creditor search using the selection criteria entered by the user.
     * 
     * @throws ActionCommandException
     */
    protected void doSearch() throws ActionCommandException {
        this.setFirstTime(false);
        this.buildXMLSearchCriteria();
        this.query.setQuerySource("CreditorView");
        this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
        CreditorCriteria criteria = (CreditorCriteria) this.query.getCustomObj();
        this.credTypeList = this.getCreditorTypes();
        this.creditors = this.getCreditors(criteria);
        this.sendClientData();
    }

    /**
     * Creates an instance of CreditorCriteria, which is used to track the
     * user's selection criteria input. This method uses introspection to gather
     * user's input into the criteria object.
     */
    protected Object doCustomInitialization() throws ActionCommandException {
        CreditorCriteria criteriaObj = CreditorCriteria.getInstance();
        if (!this.isFirstTime()) {
            try {
                RMT2WebUtility.packageBean(this.request, criteriaObj);
            } catch (SystemException e) {
                this.msg = "Problem gathering Creditor Search request parameters:  " + e.getMessage();
                CreditorSearchAction.logger.log(Level.ERROR, this.msg);
                throw new ActionCommandException(this.msg);
            }
        }
        this.validateCriteria(criteriaObj);

        return criteriaObj;
    }

    /**
     * Builds selection criteria based on business contact data values, if
     * available. Creditor criteria is represented by <i>creditor type id</i>
     * and <i>account number</i>. <i>Name</i>, <i>tax id</i>, and <i>main phone
     * number</i> are the only properties recognized as business contact data.
     * Creditor and business contact data are mutual exclusive when determining
     * which criteria group is active.
     * 
     * @param query
     *            {@link RMT2TagQueryBean} object containing the key/value pair
     *            data items used to build the service parameters.
     * @param searchMode
     *            Set to either {@link RMT2ServletConst.SEARCH_MODE_NEW} or
     *            {@link RMT2ServletConst.SEARCH_MODE_OLD}
     * @throws ActionCommandException
     *             When creditor values and business contact values are
     *             discovered for the same search transaction.
     */
    protected void doPostCustomInitialization(RMT2TagQueryBean query, int searchMode) throws ActionCommandException {
        // CreditorCriteria cc = (CreditorCriteria) query.getCustomObj();
        // StringBuffer sql = new StringBuffer();
        // if (this.criteriaType == CRITERIATYPE_CONTACT) {
        // if (!cc.getQry_Name().equals("")) {
        // sql.append(CreditorConst.CRITERIATAGS_CONTACT[0]);
        // sql.append(" = ");
        // sql.append(cc.getQry_Name().trim());
        // }
        // if (!cc.getQry_PhoneMain().equals("")) {
        // if (sql.length() > 0) {
        // sql.append(" and ");
        // }
        // sql.append(CreditorConst.CRITERIATAGS_CONTACT[1]);
        // sql.append(" = ");
        // sql.append(cc.getQry_PhoneMain().trim());
        // }
        // if (!cc.getQry_TaxId().equals("")) {
        // if (sql.length() > 0) {
        // sql.append(" and ");
        // }
        // sql.append(CreditorConst.CRITERIATAGS_CONTACT[2]);
        // sql.append(" = ");
        // sql.append(cc.getQry_TaxId().trim());
        // }
        // }
        // if (this.criteriaType == CRITERIATYPE_CREDITOR) {
        // if (!cc.getQry_AccountNo().equals("")) {
        // sql.append(CreditorConst.CRITERIATAGS_CREDITOR[0]);
        // sql.append(" like ");
        // sql.append(cc.getQry_AccountNo().trim());
        // }
        // if (!cc.getQry_CreditorTypeId().equals("")) {
        // if (sql.length() > 0) {
        // sql.append(" and ");
        // }
        // sql.append(CreditorConst.CRITERIATAGS_CREDITOR[1]);
        // sql.append(" = ");
        // sql.append(cc.getQry_CreditorTypeId().trim());
        // }
        // }
        // query.setWhereClause(null);
        // query.setWhereClause(sql.toString());
        // return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.action.gl.subsidiary.AbstractSubsidiaryAction#add()
     */
    @Override
    public void add() throws ActionCommandException {
        super.add();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.action.gl.creditor.CreditorAction#edit()
     */
    @Override
    public void edit() throws ActionCommandException {
        super.edit();
        if (this.selectedRow < 0) {
            throw new ActionCommandException("Client must select a row to edit");
        }

        // Make SOAP call to get selected creditor's profile
        CreditorCriteria c = new CreditorCriteria();
        c.setQry_CreditorId(String.valueOf(this.creditorId));
        List<Creditor> list = this.getCreditors(c);
        if (list != null && list.size() > 0) {
            this.cred = list.get(0);
        }
        return;
    }

    /**
     * Determines if search is to be performed using business contact criteria
     * or creditor criteria based on the data submitted by the client. Criteria
     * is only valid when the availability of contact or creditor criteria is
     * mutually exclusive. Otherwise, an error is thrown.
     * 
     * @param criteria
     *            Business contact or creditor selection criteria data.
     * @throws ActionCommandException
     *             When both the creditor and contact criteria is present.
     */
    private void validateCriteria(CreditorCriteria criteria) throws ActionCommandException {
        boolean useCredParms = false;
        boolean useContactParms = false;

        useCredParms = (!criteria.getQry_CreditorTypeId().equals("") || !criteria.getQry_AccountNo().equals(""));
        useContactParms = (!criteria.getQry_TaxId().equals("") || !criteria.getQry_Name().equals("") || !criteria
                .getQry_PhoneMain().equals(""));

        if (useCredParms && useContactParms) {
            this.msg = "The availability of creditor and business contact criteria must be mutually exclusive";
            CreditorSearchAction.logger.log(Level.ERROR, this.msg);
            throw new ActionCommandException(this.msg);
        }
        if (useCredParms) {
            this.criteriaType = CreditorSearchAction.CRITERIATYPE_CREDITOR;
        }
        if (useContactParms) {
            this.criteriaType = CreditorSearchAction.CRITERIATYPE_CONTACT;
        }
        if (!useContactParms && !useCredParms) {
            this.criteriaType = CreditorSearchAction.CRITERIATYPE_ALL;
        }
    }

    public void receiveClientData() throws ActionCommandException {
        super.receiveClientData();
    }

    public void sendClientData() throws ActionCommandException {
        super.sendClientData();
        this.session.setAttribute(GeneralConst.CLIENT_DATA_CRITERIA, this.query.getCustomObj());

    }
}