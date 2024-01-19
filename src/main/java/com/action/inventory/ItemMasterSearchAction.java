package com.action.inventory;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.SystemException;
import com.api.constants.GeneralConst;
import com.api.constants.RMT2ServletConst;
import com.api.persistence.DatabaseException;
import com.api.security.RMT2TagQueryBean;
import com.api.util.RMT2Money;
import com.api.util.RMT2String2;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.Request;
import com.api.web.Response;
import com.api.web.util.RMT2WebUtility;
import com.entity.ItemMasterCriteria;
import com.entity.VwItemMasterFactory;

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

    public static final String COMMAND_BACK = "Inventory.Search.back";

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
        if (command.equalsIgnoreCase(ItemMasterSearchAction.COMMAND_BACK)) {
            this.doBack();
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
        ItemMasterCriteria criteria = (ItemMasterCriteria) this.query.getCustomObj();
        this.items = this.getInventory(criteria);
        this.sendClientData();

    }

    /**
     * OBtains the selection criteria from the clients's request.
     * 
     * @return Object which represents the custom object that is a member of
     *         {@link RMT2TagQueryBean}.
     * @throws ActionCommandException
     *             when one or more selection criteria fields are found to be
     *             invalid.
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

        this.validateCriteria(criteria);
        return criteria;
    }

    /**
     * Prepares the client for adding an item to inventory.
     * 
     * @throws ActionCommandException
     */
    public void add() throws ActionCommandException {
        super.add();
        this.item = VwItemMasterFactory.create();
        this.msg = null;
        return;
    }

    /**
     * Gathers the data necessary for editing the selected inventory item
     * 
     * @throws ItemMasterException
     */
    public void edit() throws ActionCommandException {
        super.edit();
        ItemMasterCriteria criteria = new ItemMasterCriteria();
        criteria.setQry_Id(String.valueOf(this.itemMasterId));
        this.items = this.getInventory(criteria);
        if (items.size() == 1) {
            this.item = this.items.get(0);
        }
        this.msg = null;
        return;
    }

    /**
     * Verifies that the Inventory Item Master Id and Unit Cost fields are
     * numeric when either is provided as input by the client.
     * 
     * @param criteria
     *            instance of {@link ItemMasterCriteria}
     * @throws ActionCommandException
     *             When either of the above input fields are found to not be
     *             numeric.
     */
    private void validateCriteria(ItemMasterCriteria criteria) throws ActionCommandException {
        if (RMT2String2.isNotEmpty(criteria.getQry_Id()) && !RMT2Money.isNumeric(criteria.getQry_Id())) {
            this.msg = "Invenotry Item Master Id field must be numeric when used as selection criteria";
            this.logger.log(Level.ERROR, this.msg);
            throw new ActionCommandException(this.msg);
        }
        if (RMT2String2.isNotEmpty(criteria.getQry_UnitCost())) {
            if (!RMT2Money.isNumeric(criteria.getQry_UnitCost())) {
                this.msg = "Unit Cost field must be numeric when used as selection criteria";
                this.logger.log(Level.ERROR, this.msg);
                throw new ActionCommandException(this.msg);
            }
            if (RMT2String2.isEmpty(criteria.getQryRelOp_UnitCost())) {
                this.msg = "Unit Cost field must be used with a relational operater when present";
                this.logger.log(Level.ERROR, this.msg);
                throw new ActionCommandException(this.msg);
            }
        }
        if (RMT2String2.isNotEmpty(criteria.getQry_QtyOnHand())) {
            if (!RMT2Money.isNumeric(criteria.getQry_QtyOnHand())) {
                this.msg = "Quantity on Hand field must be numeric when used as selection criteria";
                this.logger.log(Level.ERROR, this.msg);
                throw new ActionCommandException(this.msg);
            }
            if (RMT2String2.isEmpty(criteria.getQryRelOp_QtyOnHand())) {
                this.msg = "Quantity on Hand field must be used with a relational operater when present";
                this.logger.log(Level.ERROR, this.msg);
                throw new ActionCommandException(this.msg);
            }
        }
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