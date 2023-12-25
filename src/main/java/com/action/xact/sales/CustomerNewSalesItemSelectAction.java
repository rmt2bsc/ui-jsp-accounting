package com.action.xact.sales;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.rmt2.jaxb.InventoryResponse;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.jaxb.ReplyStatusType;
import org.rmt2.jaxb.SimpleItemListType;

import com.SystemException;
import com.action.inventory.ItemConst;
import com.action.inventory.ItemMasterSoapRequests;
import com.api.constants.GeneralConst;
import com.api.persistence.DatabaseException;
import com.api.util.RMT2Money;
import com.api.util.RMT2String2;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.Request;
import com.api.web.Response;
import com.entity.ItemMasterCriteria;
import com.entity.SalesOrderItemsFactory;
import com.entity.VwItemMaster;
import com.entity.VwItemMasterFactory;
import com.entity.VwSalesOrderInvoice;
import com.entity.VwSalesOrderInvoiceFactory;

/**
 * This class provides functionality to serve the requests of the Customer New
 * Sales Order Item Selection user interface.
 * 
 * @author Roy Terrell
 *
 */
public class CustomerNewSalesItemSelectAction extends CustomerSalesConsoleAction {

    private static final String COMMAND_NEXT = "SalesCustomerItemSelection.Select.next";

    private static final String COMMAND_BACK = "SalesCustomerItemSelection.Select.back";

    private static Logger logger;

    private ObjectFactory f;

    /**
     * Default constructor
     *
     */
    public CustomerNewSalesItemSelectAction() {
        super();
        CustomerNewSalesItemSelectAction.logger = Logger.getLogger(CustomerNewSalesItemSelectAction.class);
        this.f = new ObjectFactory();
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
    public CustomerNewSalesItemSelectAction(Context _context, Request _request) throws SystemException, DatabaseException {
        super(_context, _request);
        this.init(this.context, this.request);
    }

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

        if (command.equalsIgnoreCase(CustomerNewSalesItemSelectAction.COMMAND_NEXT)) {
            this.addData();
        }
        if (command.equalsIgnoreCase(CustomerNewSalesItemSelectAction.COMMAND_BACK)) {
            this.doBack();
        }
    }

    /**
     * Gathers sales order item selections, which exist as a String array of
     * item numbers, from the user's request and builds a collection of sales
     * order item objects to be associated with the new sales order.
     * 
     * @throws ActionCommandException
     */
    public void add() throws ActionCommandException {
        this.receiveClientData();

        // Call SOAP web service to get the details of the list of selected
        // Inventory Item Master records based on selection criteria
        Integer items[] = this.getSelectedItems();
        ItemMasterCriteria criteria = ItemMasterCriteria.getInstance();
        criteria.setQry_ItemIdList(items);

        List<VwItemMaster> newItems = null;
        try {
            InventoryResponse response = ItemMasterSoapRequests.callGet(criteria, this.loginId, this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            this.msg = rst.getMessage();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.throwActionError(rst.getMessage(), rst.getExtMessage());
            }
            if (response.getProfile() != null && response.getProfile().getInvItem() != null) {
                newItems = VwItemMasterFactory.create(response.getProfile().getInvItem());
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }

        // Setup Sales order profile
        if (this.salesOrderId > 0) {
            // Call SOAP web service to get existing sales order and items.
            List<VwSalesOrderInvoice> results = this.getCustomerSalesOrderFull();
            if (results != null && results.size() == 1) {
                this.salesOrder = results.get(0);
            }
        }
        else {
            // Create new sales order profile for customer
            this.salesOrder = VwSalesOrderInvoiceFactory.create();
            this.salesOrder.setAccountNo(this.cust.getAccountNo());
            this.salesOrder.setCustomerId(this.cust.getCustomerId());
            this.salesOrder.setSalesOrderId(this.salesOrderId);
            this.salesOrder.setDescription(this.cust.getLongname());
        }

        // Added new items to sales order profile
        List<com.entity.SalesOrderItems> newItemList = new ArrayList<>();
        for (VwItemMaster item : newItems) {
            com.entity.SalesOrderItems soi = SalesOrderItemsFactory.create();
            soi.setItemId(item.getId());
            soi.setItemName(item.getDescription());
            soi.setOrderQty(0);
            soi.setQtyOnHand(item.getQtyOnHand());
            soi.setInitUnitCost(item.getRetailPrice() > 0 && item.getOverrideRetail() == 1 ? item.getRetailPrice() : item
                    .getUnitCost());
            newItemList.add(soi);
        }
        this.salesOrder.setLineItems(newItemList);
    }

    /**
     * Queries the client's request (Item Selection Page) and obtains all
     * service and merchandise items selected by the user.
     * 
     * @return String[] A list of Item Master id's as type String.
     */
    private Integer[] getSelectedItems() {
        SimpleItemListType silt = this.f.createSimpleItemListType();

        List<Integer> list = new ArrayList<>();
        String temp[] = null;

        // Get selected service items
        temp = this.request.getParameterValues(ItemConst.SEL_NEW_ITEM_SRVC);
        if (temp != null) {
            logger.log(Level.DEBUG, "Total Service items selected: " + temp.length);
            for (String strVal : temp) {
                if (RMT2String2.isNotEmpty(strVal) && RMT2Money.isNumeric(strVal)) {
                    Integer intVal = Integer.valueOf(strVal);
                    list.add(intVal);
                    logger.log(Level.DEBUG, " Selected Service item: " + intVal);
                }
            }
        }

        // Get selected merchandise items.
        temp = this.request.getParameterValues(ItemConst.SEL_NEW_ITEM_MERCH);
        if (temp != null) {
            logger.log(Level.DEBUG, "Total Merchandise items selected: " + temp.length);
            for (String strVal : temp) {
                if (RMT2String2.isNotEmpty(strVal) && RMT2Money.isNumeric(strVal)) {
                    Integer intVal = Integer.valueOf(strVal);
                    list.add(intVal);
                    logger.log(Level.DEBUG, " Selected Merchandise item: " + intVal);
                }
            }
        }
        Integer items[] = new Integer[list.size()];
        items = (Integer[]) list.toArray(items);
        logger.log(Level.INFO, "Total Items selected: " + items.length);
        return items;
    }

    /**
     * Retrieves customer detail data which is needed for navigating the user
     * back to the customer sales console.
     * 
     * @throws ActionCommandException
     */
    protected void doBack() throws ActionCommandException {
        this.receiveClientData();
        this.getCurrentCustomerProfile();
        this.sendClientData();
    }

    /**
     * Retrieves basic customer, sales order, and transaction data from the
     * client's request.
     */
    public void receiveClientData() throws ActionCommandException {
        super.receiveClientData();
    }

    /**
     * Packages detail Sales Order data into the HttpServlerRequest object which
     * is required to serve as the response to the client.
     * 
     * @throws ActionCommandException
     */
    public void sendClientData() throws ActionCommandException {
        super.sendClientData();

        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // this.salesApi = SalesFactory.createApi((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // try {
        // super.sendClientData();
        // tx.commitUOW();
        // }
        // catch (ActionCommandException e) {
        // tx.rollbackUOW();
        // }
        // finally {
        // this.salesApi.close();
        // this.salesApi = null;
        // tx.close();
        // tx = null;
        // }
    }
}