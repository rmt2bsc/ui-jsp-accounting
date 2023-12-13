package com.action.inventory;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.rmt2.jaxb.InventoryResponse;
import org.rmt2.jaxb.ReplyStatusType;

import com.SystemException;
import com.api.constants.GeneralConst;
import com.api.constants.RMT2ServletConst;
import com.api.persistence.DatabaseException;
import com.api.security.RMT2TagQueryBean;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.Request;
import com.api.web.Response;
import com.entity.ItemMasterCriteria;
import com.entity.VwItemMaster;
import com.entity.VwItemMasterFactory;

/**
 * This class provides action handlers to serve Item Master Edit/Add page
 * requests.
 * 
 * @author Roy Terrell
 *
 */
public class ItemMasterEditAction extends AbstractInventoryAction {
    private static final String COMMAND_SAVE = "Inventory.Edit.save";

    private static final String COMMAND_ADD = "Inventory.Edit.add";

    private static final String COMMAND_DELETE = "Inventory.Edit.delete";

    private static final String COMMAND_BACK = "Inventory.Edit.back";

    private Logger logger;

    /**
     * Default Constructor
     * 
     * @throws SystemException
     */
    public ItemMasterEditAction() throws SystemException {
        this.logger = Logger.getLogger("ItemMasterEditAction");
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
    public ItemMasterEditAction(Context _context, Request _request) throws SystemException, DatabaseException {
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
        if (command.equalsIgnoreCase(ItemMasterEditAction.COMMAND_SAVE)) {
            this.saveData();
        }
        if (command.equalsIgnoreCase(ItemMasterEditAction.COMMAND_ADD)) {
            this.addData();
        }
        if (command.equalsIgnoreCase(ItemMasterEditAction.COMMAND_DELETE)) {
            this.deleteData();
        }
        if (command.equalsIgnoreCase(ItemMasterEditAction.COMMAND_BACK)) {
            this.doBack();
        }
    }

    /**
     * Persist the changes made to an item master to the database.
     * 
     * @throws ActionCommandException
     */
    public void save() throws ActionCommandException {
        // Call SOAP web service to persist Creditor data changes to the
        // database.
        try {
            InventoryResponse response = ItemMasterSoapRequests.callSave(this.item, this.loginId, this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.throwActionError(rst.getMessage(), rst.getExtMessage());
            }
            List<VwItemMaster> results = null;
            if (response.getProfile() != null) {
                results = VwItemMasterFactory.create(response.getProfile().getInvItem());
                if (results != null && results.size() == 1) {
                    this.item = results.get(0);
                }
            }
            else {
                this.item = VwItemMasterFactory.create();
            }
            super.save();

            // Delayed the assignment of the
            // "inventory item master saved successfully"
            // confirmation message due to other web service calls are message
            // property as well.
            this.msg = rst.getMessage();
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }
    }


    /**
     * Creates a new VwItemMaster view object and retrieves a list of vendors.
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
     * Deletes an item master from the system. If the item targeted for deletion
     * is linked to one or more dependent entities, then the item is marked
     * inactive.
     * 
     * @throws ActionCommandException
     *             general database errors
     */
    public void delete() throws ActionCommandException {
        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // InventoryApi api =
        // InventoryFactory.createApi((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // int itemId = 0;
        // ItemMaster im = null;
        // boolean deleteFailed = false;
        //
        // this.itemHelper.refreshData();
        // this.vendorList = this.getVendorList();
        // itemId = this.itemHelper.getItem().getItemId();
        // im = this.itemHelper.getItem();
        //
        // try {
        // api.deleteItemMaster(itemId);
        // tx.commitUOW();
        // this.msg = "Item was deleted from the database successfully";
        // } catch (ItemMasterException e) {
        // tx.rollbackUOW();
        // deleteFailed = true;
        // }
        //
        // if (deleteFailed) {
        // try {
        // im.setActive(0);
        // api.maintainItemMaster(im, null);
        // tx.commitUOW();
        // this.msg =
        // "Item was not deleted from the database.   Instead marked inactive since item is linked to one or more orders.";
        // } catch (ItemMasterException e) {
        // this.msg = e.getMessage();
        // logger.log(Level.ERROR, this.msg);
        // tx.rollbackUOW();
        // }
        // }
        // tx.close();
        // api = null;
        // tx = null;
        return;
    }

    /**
     * Prepares the user to navigate back to the search page.
     * 
     * @throws ActionCommandException
     */
    protected void doBack() throws ActionCommandException {
        this.setupLookupData();
        this.query = (RMT2TagQueryBean) this.getSession().getAttribute(RMT2ServletConst.QUERY_BEAN);
        ItemMasterCriteria criteria = (ItemMasterCriteria) this.query.getCustomObj();
        this.items = this.getInventory(criteria);
        this.sendClientData();
    }

    /**
     * Sends vendor list to the client.
     * 
     * @throws ActionCommandException
     */
    public void sendClientData() throws ActionCommandException {
        super.sendClientData();
    }
}