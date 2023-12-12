package com.action.inventory;

import java.util.ArrayList;

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
import com.entity.ItemMasterCriteria;

/**
 * This class provides action handlers to server Item Master Search requests.
 * 
 * @author Roy Terrell
 *
 */
public class ItemMasterSearchAction extends AbstractInventoryAction {
    private static final String COMMAND_NEWSEARCH = "Inventory.Search.newsearch";

    private static final String COMMAND_SEARCH = "Inventory.Search.search";

    private static final String COMMAND_EDIT = "Inventory.Search.edit";

    private static final String COMMAND_ADD = "Inventory.Search.add";

    public static final String COMMAND_LIST = "Inventory.Search.list";

    private Logger logger;

    /**
     * Default Constructor
     * 
     * @throws SystemException
     */
    public ItemMasterSearchAction() {
        super();
        this.logger = Logger.getLogger("ItemMasterSearchAction");
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
    public ItemMasterSearchAction(Context _context, Request _request) throws SystemException, DatabaseException {
        super(_context, _request);
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
        if (command.equalsIgnoreCase(ItemMasterSearchAction.COMMAND_NEWSEARCH)) {
            this.startConsole();
        }
        if (command.equalsIgnoreCase(ItemMasterSearchAction.COMMAND_ADD)) {
            this.addData();
        }
        if (command.equalsIgnoreCase(ItemMasterSearchAction.COMMAND_EDIT)) {
            this.editData();
        }
        if (command.equalsIgnoreCase(ItemMasterSearchAction.COMMAND_SEARCH)) {
            this.doSearch();
        }
        if (command.equalsIgnoreCase(ItemMasterSearchAction.COMMAND_LIST)) {
            this.listData();
        }
    }

    /**
     * Displays the Item Master Search Console for the first time rendering the
     * search criteria section with blank values and the Search Result Set
     * section with an empty result set.
     *
     */
    public void startConsole() throws ActionCommandException {
        this.setFirstTime(true);
        this.startSearchConsole();
        this.items = new ArrayList<>();
        this.query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
        this.setupLookupData();
        this.sendClientData();
        return;
    }

    /**
     * Drives the process of building selection criteria using the client's HTTP
     * request and storing the criteria onto the session object for later use.
     * 
     * @throws ActionCommandException
     */
    protected void doSearch() throws ActionCommandException {
        this.setFirstTime(false);
        this.buildXMLSearchCriteria();
        this.query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
        this.getSession().setAttribute(RMT2ServletConst.QUERY_BEAN, this.query);
        this.setupLookupData();
        this.sendClientData();

    }

    /**
     * OBtains the selection criteria from the clients's request.
     * 
     * @return Object which represents the custom object that is a member of
     *         {@link RMT2TagQueryBean}.
     * @throws ActionCommandException
     */
    protected Object doCustomInitialization() throws ActionCommandException {
        ItemMasterCriteria criteria = ItemMasterCriteria.getInstance();
        if (!this.isFirstTime()) {
            try {
                RMT2WebUtility.packageBean(this.request, criteria);
            } catch (Exception e) {
                throw new ActionCommandException(e.getMessage());
            }
        }
        return criteria;
    }

    /**
     * This method signals to the caller to ignore the selection criteria that
     * was setup for _filedName in the ancestor script. Typically, this is true
     * when _fieldName is pointing to the Active or Inactive fields.
     * 
     * @return String - Return "" which means to ignore the criteria of
     *         _fieldName which was setup in the ancestor script. Returns null
     *         to indicate that there is no custom criteria to be applied and to
     *         apply that which was built in the ancestor.
     */
    protected String buildCustomClientCriteria(String _fieldName) {
        if (_fieldName.equalsIgnoreCase("qry_Active") || _fieldName.equalsIgnoreCase("qry_Inactive")) {
            return "";
        }
        return null;
    }

    /**
     * Includes the item master values, "Active" and "Inactive", into the SQL
     * predicate, if applicable.
     * 
     * @return SQL predicate.
     */
    protected String postBuildCustomClientCriteria() {
        StringBuffer criteria = new StringBuffer(100);
        String temp = null;
        String temp2 = null;
        ArrayList activeInactive = new ArrayList();

        temp = this.request.getParameter("qry_Active");
        temp2 = this.request.getParameter("qry_Inactive");
        if (temp != null && !temp.equals("")) {
            activeInactive.add(temp);
        }
        if (temp2 != null && !temp2.equals("")) {
            activeInactive.add(temp2);
        }
        if (activeInactive.size() > 0) {
            if (criteria.length() > 0) {
                criteria.append(" and ");
            }
            criteria.append(" active in(");
            for (int ndx = 0; ndx < activeInactive.size(); ndx++) {
                if (ndx > 0) {
                    criteria.append(", ");
                }
                temp = (String) activeInactive.get(ndx);
                criteria.append(temp);
            }
            criteria.append(") ");
        }
        if (criteria.length() > 0) {
            return criteria.toString();
        }
        return null;
    }

    /**
     * Prepares the client for adding an item to inventory and retrieves a list
     * of vendors.
     * 
     * @throws ActionCommandException
     */
    public void add() throws ActionCommandException {
        super.add();
        this.setupLookupData();
        return;
    }

    /**
     * Uses data from the client's request object to retrieve target
     * VwItemMaster and ItemMasterStatusHist objects from the database for a
     * single Item Master record edit session.
     * <p>
     * The following objects are set on the request object identified as "item"
     * and "itemhistory", respectively: {@link VwItemMaster} and
     * {@link ItemMasterStatusHist}.
     * 
     * @throws ItemMasterException
     */
    public void edit() throws ActionCommandException {
        this.setupLookupData();
        return;
    }

    protected void listData() throws ActionCommandException {
        this.setupLookupData();
        this.sendClientData();
    }

    /**
     * Sends selection criteria data to the client.
     * 
     * @throws ActionCommandException
     */
    public void sendClientData() throws ActionCommandException {
        super.sendClientData();
        this.session.setAttribute(GeneralConst.CLIENT_DATA_CRITERIA, this.query.getCustomObj());
    }

}