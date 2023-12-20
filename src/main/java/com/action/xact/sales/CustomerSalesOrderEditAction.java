package com.action.xact.sales;

import java.util.List;

import org.apache.log4j.Logger;

import com.SystemException;
import com.api.persistence.DatabaseException;
import com.api.persistence.db.DatabaseConnectionBean;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.Request;
import com.api.web.Response;

/**
 * This class provides functionality to serve the requests of the Customer New
 * Sales Order Item Selection user interface.
 * 
 * @author Roy Terrell
 *
 */
public class CustomerSalesOrderEditAction extends CustomerSalesOrderListAction {

    private static final String COMMAND_ADD = "SalesCustomerOrderEdit.OrderEdit.add";

    private static final String COMMAND_SAVE = "SalesCustomerOrderEdit.OrderEdit.save";

    private static final String COMMAND_BACK = "SalesCustomerOrderEdit.OrderEdit.back";

    private static final String COMMAND_DELETE = "SalesCustomerOrderEdit.OrderEdit.delete";

    private boolean invoiceRequested;

    private boolean receivePaymentRequested;

    private static Logger logger;

    private List orderList;

    /**
     * Default constructor
     *
     */
    public CustomerSalesOrderEditAction() {
        super();
        CustomerSalesOrderEditAction.logger = Logger.getLogger(CustomerSalesOrderEditAction.class);
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
    public CustomerSalesOrderEditAction(Context _context, Request _request) throws SystemException, DatabaseException {
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

        if (command.equalsIgnoreCase(CustomerSalesOrderEditAction.COMMAND_ADD)) {
            this.add();
        }
        if (command.equalsIgnoreCase(CustomerSalesOrderEditAction.COMMAND_SAVE)) {
            this.saveData();
        }
        if (command.equalsIgnoreCase(CustomerSalesOrderEditAction.COMMAND_BACK)) {
            this.doBack();
        }
        if (command.equalsIgnoreCase(CustomerSalesOrderEditAction.COMMAND_DELETE)) {
            this.deleteData();
        }
    }



    /**
     * Drives the process of adding items to an existing sales order.
     * 
     * @throws ActionCommandException
     */
    public void add() throws ActionCommandException {
        super.add();
        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // this.salesApi = SalesFactory.createApi((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // this.xactApi = XactFactory.create((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // try {
        // try {
        // this.getServiceAndMerchandiseItems();
        // this.updateSalesOrder((DatabaseConnectionBean) tx.getConnector());
        // tx.commitUOW();
        // } catch (SalesOrderException e) {
        // if (e.getErrorCode() == 702) {
        // // Continue only if there were no items to process for order
        // // (error 702).
        // }
        // else {
        // throw e;
        // }
        // }
        //
        // // Forward call to Sales Console action handler.
        // CustomerSalesConsoleAction console = new
        // CustomerSalesConsoleAction(this.context, this.request);
        // console.setupNewSalesOrderSelection(this.salesOrder.getSoId());
        // return;
        // } catch (Exception e) {
        // tx.rollbackUOW();
        // throw new ActionCommandException(e.getMessage());
        // } finally {
        // this.salesApi.close();
        // this.salesApi = null;
        // this.xactApi.close();
        // this.xactApi = null;
        // tx.close();
        // tx = null;
        // }

    }

    /**
     * Drives the process of persisting sales order data derived fromthe
     * client's request.
     * 
     * @throws ActionCommandException
     */
    public void save() throws ActionCommandException {
        super.save();
        // this.getServiceAndMerchandiseItems();
        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // this.salesApi = SalesFactory.createApi((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // this.xactApi = XactFactory.create((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // try {
        // this.xact = this.updateSalesOrder((DatabaseConnectionBean)
        // tx.getConnector());
        // tx.commitUOW();
        // this.msg = "Sales Order was saved successfully";
        // return;
        // } catch (Exception e) {
        // this.msg = "Failure: " + e.getMessage();
        // tx.rollbackUOW();
        // throw new
        // ActionCommandException("Customer sales order save operation failed",
        // e);
        // } finally {
        // this.salesApi.close();
        // this.salesApi = null;
        // this.xactApi.close();
        // this.xactApi = null;
        // tx.close();
        // tx = null;
        // }

    }

    /**
     * Drives the process of deleting sales order data derived from the client's
     * request.
     * 
     * @throws ActionCommandException
     */
    public void delete() throws ActionCommandException {
        // Get customer order details so that record can be displayed after
        // delete operation.
        // this.prepareCustomerSalesConsole();
        //
        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // this.salesApi = SalesFactory.createApi((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // try {
        // super.delete();
        // this.salesApi.deleteSalesOrder(this.salesOrder);
        // tx.commitUOW();
        // this.msg = "Sales Order was deleted successfully";
        // } catch (SalesOrderException e) {
        // tx.rollbackUOW();
        // throw new ActionCommandException(e);
        // } finally {
        // this.salesApi.close();
        // tx.close();
        // this.salesApi = null;
        // tx = null;
        // }
    }

    /**
     * Initializes the sales order item helper object with service and
     * merchandise item objects.
     * 
     * @throws ActionCommandException
     */
    private void getServiceAndMerchandiseItems() throws ActionCommandException {
        List items;

        // Get existing sales order items
        // try {
        // items = this.httpHelper.getHttpXactItems(this.salesOrder,
        // ItemConst.ITEMTYPE_SRVC);
        // this.itemHelper.packageItemsByTypes(items);
        // items = this.httpHelper.getHttpXactItems(this.salesOrder,
        // ItemConst.ITEMTYPE_MERCH);
        // this.itemHelper.packageItemsByTypes(items);
        // } catch (SystemException e) {
        // CustomerSalesOrderEditAction.logger.log(Level.ERROR, e.getMessage());
        // throw new ActionCommandException(e);
        // }
    }

    /**
     * Applies Sales Order changes to the database. This method requires that
     * the mandatory request data (Customer, sales order base, sales order
     * items, and etc) are gathered before its invocation. The invoicing of the
     * sales order is handle here provided that the this method was invoked from
     * the user's request to explicitly save the sales order.
     * 
     * @return A valid transaction object when sales order has been invoiced.
     *         Otherwise, null is returned.
     * @throws ActionCommandException
     * @throws DatabaseException
     * @throws SalesOrderException
     */
    // private Xact updateSalesOrder(DatabaseConnectionBean con) throws
    // ActionCommandException, DatabaseException,
    // SalesOrderException {
    // Xact xact = null;
    // int salesOrderId = 0;
    // boolean isOkToInvoice = false;

    // try {
    // // Get all items assoicated with the order from the UI
    // ArrayList items = (ArrayList) this.itemHelper.getItems();
    //
    // // Create/Modifiy the order
    // this.xact = XactFactory.createXact();
    // salesOrderId = this.salesApi.maintainSalesOrder(this.salesOrder,
    // this.customer, items);
    //
    // // Refresh the sales order object by retrieving latest sales order
    // // record
    // this.salesOrder = (SalesOrder)
    // this.salesApi.findSalesOrderById(salesOrderId);
    //
    // // Sales Order cannot be invoiced when client is requesting to add
    // // inventory items.
    // isOkToInvoice =
    // (this.command.equalsIgnoreCase(CustomerSalesOrderEditAction.COMMAND_SAVE));
    // xact = XactFactory.createXact();
    // if (isOkToInvoice) {
    // CustomerSalesOrderEditAction.logger.log(Level.DEBUG,
    // "Sales Order is eligible to be invoice");
    // if (this.salesOrder.getInvoiced() == 0 && this.invoiceRequested) {
    // CustomerSalesOrderEditAction.logger.log(Level.DEBUG,
    // "Ready to invoice Sales Order: " + salesOrderId);
    //
    // // Get transaction data from client, mainly the transaction
    // // reason
    // this.httpHelper.getHttpCombined();
    // xact = this.httpHelper.getXact();
    //
    // // Invoice sales order.
    // this.salesApi.invoiceSalesOrder(this.salesOrder, xact);
    // CustomerSalesOrderEditAction.logger.log(Level.INFO,
    // "Sales Order successfully invoiced: " + salesOrderId);
    //
    // // Apply cash receipt transaction if requested by user
    // if (this.receivePaymentRequested) {
    // this.applyCustomerPayment(xact.getXactAmount(), con);
    // this.salesApi.changeSalesOrderStatus(salesOrderId,
    // SalesConst.STATUS_CODE_CLOSED);
    // }
    //
    // // Get Transaction object for the inovice
    // try {
    // xact = this.xactApi.findXactById(xact.getXactId());
    // if (xact == null) {
    // xact = XactFactory.createXact();
    // }
    // } catch (XactException e) {
    // CustomerSalesOrderEditAction.logger.log(Level.ERROR,
    // "SalesOrderException " + e.getMessage());
    // throw new SalesOrderException(e);
    // }
    // }
    // }
    // return xact;
    // } catch (SalesOrderException e) {
    // throw new SalesOrderException("Sales Order Update failed", e);
    // } catch (Exception e) {
    // CustomerSalesOrderEditAction.logger.log(Level.ERROR, e.getMessage());
    // throw new
    // SalesOrderException("Sales Order Update failed due to general exception",
    // e);
    // }
    // }

    /**
     * Applies cash receipt transaction for the invoiced sales order.
     * 
     * @throws ActionCommandException
     */
    private void applyCustomerPayment(double amount, DatabaseConnectionBean con) throws ActionCommandException {
        // Xact xact = XactFactory.createXact();
        // xact.setXactAmount(amount);
        // xact.setReason("Full payment received for sales order #" +
        // this.salesOrder.getSoId());
        // xact.setXactTypeId(XactConst.XACT_TYPE_CASHPAY);
        //
        // CashReceiptsApi crApi = ReceiptsFactory.createCashReceiptsApi(con,
        // this.request);
        // try {
        // int xactId = crApi.maintainCustomerPayment(xact,
        // this.customer.getCustomerId());
        // this.msg =
        // "Sales order payment was applied successfully.  New Transaction id for cash payment: "
        // + xactId;
        // logger.log(Level.DEBUG, this.msg);
        // } catch (CashReceiptsException e) {
        // this.msg = "Sales Order payment failed.  " + e.getMessage();
        // logger.log(Level.ERROR, this.msg);
        // throw new ActionCommandException(e.getMessage());
        // } finally {
        // // Do not invoke the close method for crApi variable since it will
        // // close
        // // (return to the connection pool) the connection object that is
        // // shared by the caller.
        // crApi = null;
        // }
    }

    /**
     * Retrieves customer detail data which is needed for navigating the user
     * back to the customer sales console.
     * 
     * @throws ActionCommandException
     */
    protected void doBack() throws ActionCommandException {
        this.doSalesOrderHistory();
    }

    /**
     * Retrieves sales order data from the client's UI page.
     */
    public void receiveClientData() throws ActionCommandException {
        super.receiveClientData();
        String temp;

        // Get Invoiced indicator for single row page of client
        temp = this.request.getParameter("Invoiced");
        this.invoiceRequested = (temp != null);
        // Get receive payment indicator for single row page of client
        temp = this.request.getParameter("InvoiceAndReceive");
        this.receivePaymentRequested = (temp != null);
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
        // this.request.setAttribute(SalesConst.CLIENT_DATA_ORDERLIST,
        // this.orderList);
        // this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO,
        // this.msg);
        // tx.commitUOW();
        // } catch (ActionCommandException e) {
        // tx.rollbackUOW();
        // } finally {
        // this.salesApi.close();
        // this.salesApi = null;
        // tx.close();
        // tx = null;
        // }
    }
}