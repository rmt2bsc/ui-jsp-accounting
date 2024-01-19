package com.action.xact.sales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.rmt2.jaxb.AccountingTransactionResponse;
import org.rmt2.jaxb.InventoryResponse;
import org.rmt2.jaxb.ReplyStatusType;
import org.rmt2.jaxb.SalesOrderListType;

import testcases.bean.Xact;

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
import com.entity.Customer;
import com.entity.CustomerCriteria;
import com.entity.CustomerFactory;
import com.entity.ItemMasterCriteria;
import com.entity.SalesOrderInvoiceCriteria;
import com.entity.SalesOrderItemsFactory;
import com.entity.VwItemMaster;
import com.entity.VwItemMasterFactory;
import com.entity.VwSalesOrderInvoice;
import com.entity.VwSalesOrderInvoiceFactory;

/**
 * This class provides action handlers needed to serve the request of the
 * Customer Sales Order Console user interface.
 * 
 * @author Roy Terrell
 *
 */
public class CustomerSalesConsoleAction extends CustomerSalesSearchAction {
    private static final String COMMAND_VEIWORDERS = "SalesCustomerConsole.SalesConsole.vieworders";

    private static final String COMMAND_NEWORDER = "SalesCustomerConsole.SalesConsole.neworder";

    private static final String COMMAND_PAYMENT = "SalesCustomerConsole.SalesConsole.acceptpayment";

    private static final String COMMAND_VIEWTRANSACTIONS = "SalesCustomerConsole.SalesConsole.view";

    private static final String COMMAND_BACK = "SalesCustomerConsole.SalesConsole.back";

    private Logger logger;

    protected List custOrders;

    private List payments;

    private Xact xact;

    protected int salesOrderId;

    protected VwSalesOrderInvoice salesOrder;

    protected SalesOrderListType jaxbSalesOrderList;

    protected List SalesOrderItems;

    protected List itemsService;

    protected List itemsMerchandise;

    /**
     * Default constructor
     *
     */
    public CustomerSalesConsoleAction() {
        super();
        logger = Logger.getLogger(CustomerSalesConsoleAction.class);
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
    public CustomerSalesConsoleAction(Context _context, Request _request) throws SystemException, DatabaseException {
        super(_context, _request);
        logger = Logger.getLogger(CustomerSalesConsoleAction.class);
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

        if (command.equalsIgnoreCase(CustomerSalesConsoleAction.COMMAND_VEIWORDERS)) {
            this.doSalesOrderHistory();
        }
        if (command.equalsIgnoreCase(CustomerSalesConsoleAction.COMMAND_NEWORDER)) {
            this.doNewSalesOrder(0);
        }
        if (command.equalsIgnoreCase(CustomerSalesConsoleAction.COMMAND_PAYMENT)) {
            this.acceptPaymentOnAccount();
        }
        if (command.equalsIgnoreCase(CustomerSalesConsoleAction.COMMAND_VIEWTRANSACTIONS)) {
            this.getTransactions();
        }
        if (command.equalsIgnoreCase(CustomerSalesConsoleAction.COMMAND_BACK)) {
            this.doBack();
        }
    }

    /**
     * Gathers a list of sales orders for the target customer found in the
     * client's request and sends the results back to the client.
     * 
     * @throws ActionCommandException
     */
    protected void doSalesOrderHistory() throws ActionCommandException {
        this.receiveClientData();
        this.custOrders = this.getCustomerSalesOrderHeaders();
        this.sendClientData();
    }

    /**
     * Base method for retrieving a list inventory item master id's for a given
     * sales order.
     * <p>
     * Override this method in order to provided specific logic needed to obtain
     * the correct details of each of the sales order's line items.
     * 
     * @return null
     * @throws ActionCommandException
     */
    protected Integer[] getSelectedItems() throws ActionCommandException {
        return null;
    }

    /*
     * Retrieves the customer's sales order history using _cust.
     * 
     * @param _cust Customer object
     * 
     * @return An ArrayList of CombinedSalesOrder objects
     * 
     * @throws SalesOrderException
     */
    // protected List<Object> getCustomerSalesOrders(Customer cust) throws
    // ActionCommandException {
    // List<Object> list;
        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // this.salesApi = SalesFactory.createApi((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // try {
        // list = (List<Object>)
        // this.salesApi.findExtendedSalesOrderByCustomer(cust.getCustomerId());
        // return list;
        // } catch (SalesOrderException e) {
        // logger.log(Level.ERROR, "SalesOrderExeption occurred. " +
        // e.getMessage());
        // throw new ActionCommandException(e);
        // } finally {
        // this.salesApi.close();
        // tx.close();
        // this.salesApi = null;
        // tx = null;
        // }
    // }

    /*
     * Retrieves the customer's sales order history using _cust.
     * 
     * @param _cust Customer object
     * 
     * @return An ArrayList of CombinedSalesOrder objects
     * 
     * @throws SalesOrderException
     */
    protected List<Object> getCustomerInvoicedSalesOrders(Customer cust) throws ActionCommandException {
        // List<Object> list;
        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // this.salesApi = SalesFactory.createApi((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // try {
        // list = (List<Object>)
        // this.salesApi.findExtendedInvoicedSalesOrderByCustomer(cust.getCustomerId());
        // return list;
        // } catch (SalesOrderException e) {
        // logger.log(Level.ERROR, "SalesOrderExeption occurred. " +
        // e.getMessage());
        // throw new ActionCommandException(e);
        // } finally {
        // this.salesApi.close();
        // tx.close();
        // this.salesApi = null;
        // tx = null;
        // }
        return null;
    }

    /**
     * Uses <i>salesOrderId</i> to retrieve the all items that have not been
     * associated with a sales order.
     * 
     * @param salesOrderId
     *            The sales order id
     * @throws ActionCommandException
     *             System or Database errors.
     */
    public void doNewSalesOrder(int salesOrderId) throws ActionCommandException {
        this.receiveClientData();
        try {
            ItemMasterCriteria criteria = ItemMasterCriteria.getInstance();

            // Get service inventory items
            criteria.setQry_ItemTypeId(String.valueOf(ItemConst.ITEM_TYPE_SRVC));
            this.itemsService = this.getInventory(criteria);

            // Get merchandise inventory items
            criteria.setQry_ItemTypeId(String.valueOf(ItemConst.ITEM_TYPE_MERCH));
            this.itemsMerchandise = this.getInventory(criteria);

        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        } finally {
            this.sendClientData();
        }
    }

    /**
     * Prepares the client for a cash receipt transaction.
     * 
     * @throws ActionCommandException
     */
    protected void acceptPaymentOnAccount() throws ActionCommandException {
        this.receiveClientData();
        if (this.xact.getXactId() == 0) {
            // this.xact.setXactTypeId(XactConst.XACT_TYPE_CASHPAY);
        }
        this.calcCustomerBalance();

        this.custOrders = this.getCustomerInvoicedSalesOrders(this.cust);

        this.sendClientData();
    }

    /**
     * Retrieves a customer's transaction history including purchases, payments,
     * and reversals.
     * 
     * @return An array of all the customer's transactions
     * @throws SalesOrderException
     */
    public int getTransactions() throws ActionCommandException {
        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // this.xactApi = XactFactory.create((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // try {
        // this.receiveClientData();
        // this.payments =
        // this.xactApi.findCustomerXactHist(this.cust.getCustomerId());
        // this.calcCustomerBalance();
        // this.sendClientData();
        // return this.payments.size();
        // } catch (XactException e) {
        // logger.log(Level.ERROR, "XactException occurred. " + e.getMessage());
        // throw new ActionCommandException(e);
        // } finally {
        // this.xactApi.close();
        // tx.close();
        // this.xactApi = null;
        // tx = null;
        // }
        return 0;
    }

    /**
     * Makes call to customer api to calculate and capture customer's balance.
     * The balance is added to the sales order member variable. If sales orer
     * varialbe is invalid it is instantiated.
     * 
     */
    private void calcCustomerBalance() throws ActionCommandException {
        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // this.custApi = CustomerFactory.createApi((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // try {
        // if (this.so == null) {
        // // Create new sales order object.
        // this.so = SalesFactory.createSalesOrder();
        // }
        // double balance =
        // this.custApi.findCustomerBalance(this.cust.getCustomerId());
        // this.so.setOrderTotal(balance);
        // } catch (Exception e) {
        // throw new ActionCommandException(e.getMessage());
        // } finally {
        // this.custApi.close();
        // tx.close();
        // this.custApi = null;
        // tx = null;
        // }
    }

    /**
     * Navigate back to the customer search page.
     * 
     * @throws ActionCommandException.
     */
    protected void doBack() throws ActionCommandException {
        CustomerCriteria criteria = (CustomerCriteria) this.query.getCustomObj();
        this.customers = this.getCustomers(criteria);
        this.sendClientData();
    }

    /**
     * Gathers and returns a list of sales orders, which is in HEADER format,
     * for the target customer(s) found in the client's request and sends the
     * results back to the client.
     * 
     * @return List <{@link VwSalesOrderInvoice}>
     * @throws ActionCommandException
     */
    protected List<VwSalesOrderInvoice> getCustomerSalesOrderHeaders() throws ActionCommandException {
        // Make SOAP call to get selected customer's sales order history
        SalesOrderInvoiceCriteria criteria = SalesOrderInvoiceCriteria.getInstance();
        criteria.setQry_CustomerId(String.valueOf(this.customerId));
        criteria.setQry_FullQuery(false);
        return this.getCustomerSalesOrders(criteria);
    }

    /**
     * Gathers and returns a list of sales orders, which is in FULL format, for
     * the target customer(s) found in the client's request and sends the
     * results back to the client.
     * 
     * @return List <{@link VwSalesOrderInvoice}>
     * @throws ActionCommandException
     */
    protected List<VwSalesOrderInvoice> getCustomerSalesOrderFull() throws ActionCommandException {
        // Make SOAP call to get selected customer's sales order history
        SalesOrderInvoiceCriteria criteria = SalesOrderInvoiceCriteria.getInstance();
        criteria.setQry_CustomerId(String.valueOf(this.customerId));
        criteria.setQry_SalesOrderId(String.valueOf(this.salesOrderId));
        criteria.setQry_FullQuery(true);
        return this.getCustomerSalesOrders(criteria);
    }

    private List<VwSalesOrderInvoice> getCustomerSalesOrders(SalesOrderInvoiceCriteria criteria) throws ActionCommandException {
        // Make SOAP call to get customer's sales order history data
        try {
            AccountingTransactionResponse response = CustomerSalesOrderSoapRequests.callGet(criteria, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            this.msg = rst.getMessage();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.throwActionError(rst.getMessage(), rst.getExtMessage());
            }
            List<VwSalesOrderInvoice> results = null;
            if (response.getProfile() != null && response.getProfile().getSalesOrders() != null) {
                results = VwSalesOrderInvoiceFactory.create(response.getProfile());
                // Save JAXB SalesOrderList object graph to memory for later
                // use...
                this.jaxbSalesOrderList = response.getProfile().getSalesOrders();
            }
            else {
                results = new ArrayList<>();
            }
            this.msg += ": " + results.size();
            return results;
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }
    }

    protected void getCustomerSalesOrderForEdit() throws ActionCommandException {
        boolean oldSalesOrder = this.salesOrderId > 0;

        // Setup Sales order profile
        if (oldSalesOrder) {
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
            this.salesOrder.setInvoiced(0);
        }
        
        // Call SOAP web service to get the details of the list of selected
        // Inventory Item Master records based on selection criteria
        Integer items[] = this.getSelectedItems();
        ItemMasterCriteria criteria = ItemMasterCriteria.getInstance();
        criteria.setQry_ItemIdList(items);

        Map<Integer, VwItemMaster> newItems = null;
        try {
            InventoryResponse response = ItemMasterSoapRequests.callGet(criteria, this.loginId, this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            this.msg = rst.getMessage();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.throwActionError(rst.getMessage(), rst.getExtMessage());
            }
            if (response.getProfile() != null && response.getProfile().getInvItem() != null) {
                // Create Map of inventory item master records keyed by item id
                newItems = VwItemMasterFactory.createMap(response.getProfile().getInvItem());
            }
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }


        // Set item type id and quantity on hand for items belonging to an
        // existing sales order using item master object
        if (oldSalesOrder) {
            List<com.entity.SalesOrderItems> soItems = this.salesOrder.getLineItems();
            for (com.entity.SalesOrderItems item : soItems) {
                VwItemMaster im = newItems.get(item.getItemId());
                if (im != null) {
                    item.setItemTypeId(im.getItemTypeId());
                    item.setQtyOnHand(im.getQtyOnHand());
                }
            }
        }
        else {
            // For new sales orders, create sales order items from base
            // inventory master item data
            List<com.entity.SalesOrderItems> newItemList = new ArrayList<>();
            for (Integer key : newItems.keySet()) {
                VwItemMaster item = newItems.get(key);
                com.entity.SalesOrderItems soi = SalesOrderItemsFactory.create();
                soi.setItemId(item.getId());
                soi.setItemTypeId(item.getItemTypeId());
                soi.setItemName(item.getDescription());
                soi.setInitMarkup(item.getMarkup());
                soi.setOrderQty(0);
                soi.setQtyOnHand(item.getQtyOnHand());
                soi.setInitUnitCost(item.getUnitCost());
                soi.setRetailPrice(item.getRetailPrice());
                newItemList.add(soi);
            }
            this.salesOrder.setLineItems(newItemList);
        }
    }

    /**
     * Fetches the list inventory items from the database using the where clause
     * criteria previously stored on the session during the phase of the request
     * to builds the query predicate.
     * 
     * @param criteria
     *            {@link ItemMasterCriteria}
     * @return List<{@link VwItemMaster}>
     * @throws ActionCommandException
     */
    protected List<VwItemMaster> getInventory(ItemMasterCriteria criteria) throws ActionCommandException {
        // Call SOAP web service to get a list of Inventory Item Master records
        // based on selection criteria
        try {
            InventoryResponse response = ItemMasterSoapRequests.callGet(criteria, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            this.msg = rst.getMessage();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.throwActionError(rst.getMessage(), rst.getExtMessage());
            }
            List<VwItemMaster> results = null;
            if (response.getProfile() != null && response.getProfile().getInvItem() != null) {
                results = VwItemMasterFactory.create(response.getProfile().getInvItem());
            }
            else {
                results = new ArrayList<>();
            }
            this.msg += ": " + results.size();
            return results;
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new ActionCommandException(e.getMessage());
        }
    }

    /**
     * Retrieves the sales order data from the client's request.
     */
    public void receiveClientData() throws ActionCommandException {
        super.receiveClientData();
        // Attempt to locate and obtain creditor ID from the JSP.
        String custName = this.getInputValue("Longname", null);

        // Attempt to locate and obtain account number from the JSP.
        String acctNo = this.getInputValue("AccountNo", null);

        // Attempt to locate and obtain Sales Order ID from the JSP.
        String temp = this.getInputValue("SalesOrderId", null);
        this.salesOrderId = RMT2String2.isNotEmpty(temp) ? RMT2Money.stringToNumber(temp).intValue() : 0;

        this.cust = CustomerFactory.create();
        this.cust.setCustomerId(this.customerId);
        this.cust.setLongname(custName);
        this.cust.setAccountNo(acctNo);
    }

    /**
     * Sends the response of the client's request with an ArrayList of
     * CombinedSalesOrder objects and a CustomerWithName object which their
     * attribute names are required to be set as "orderhist" and "customer",
     * respectively.
     */
    public void sendClientData() throws ActionCommandException {
        super.sendClientData();

        this.request.setAttribute(SalesConst.CLIENT_DATA_ORDERLIST, this.custOrders);
        this.request.setAttribute(SalesConst.CLIENT_DATA_SALESORDER, this.salesOrder);
        this.request.setAttribute(SalesConst.CLIENT_DATA_SALESORDER_ITEMS,
                (this.salesOrder != null ? this.salesOrder.getLineItems() : null));

        this.request.setAttribute(SalesConst.CLIENT_DATA_ITEMS_SERVICE, this.itemsService);
        this.request.setAttribute(SalesConst.CLIENT_DATA_ITEMS_MERCHANDISE, this.itemsMerchandise);

        // this.request.setAttribute(SalesConst.CLIENT_DATA_CUSTOMER_EXT,
        // this.custExt);

        // if (this.itemHelper != null) {

        // }
        // this.request.setAttribute(XactConst.CLIENT_DATA_XACT, this.xact);
        // this.request.setAttribute(XactConst.CLIENT_DATA_SALESPAYMENT_HIST,
        // this.payments);
    }
}