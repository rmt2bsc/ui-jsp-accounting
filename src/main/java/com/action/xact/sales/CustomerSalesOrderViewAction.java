package com.action.xact.sales;

import java.util.List;

import org.apache.log4j.Logger;

import com.SystemException;
import com.api.persistence.DatabaseException;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.Request;
import com.api.web.Response;

/**
 * This class provides functionality to serve the requests of the Customer Sales
 * Order View user interface.
 * 
 * @author Roy Terrell
 *
 */
public class CustomerSalesOrderViewAction extends CustomerSalesOrderListAction {

    private static final String COMMAND_BACK = "SalesCustomerOrderView.OrderView.vieworders";

    private static final String COMMAND_CANCEL = "SalesCustomerOrderView.OrderView.cancelorder";

    private static final String COMMAND_CLOSE = "SalesCustomerOrderView.OrderView.closeorder";

    private static final String COMMAND_REFUND = "SalesCustomerOrderView.OrderView.salesrefund";

    private static final String COMMAND_PRINT = "SalesCustomerOrderView.OrderView.print";

    private static Logger logger;

    private List orderList;

    /**
     * Default constructor
     *
     */
    public CustomerSalesOrderViewAction() {
        super();
        CustomerSalesOrderViewAction.logger = Logger.getLogger(CustomerSalesOrderViewAction.class);
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
    public CustomerSalesOrderViewAction(Context _context, Request _request) throws SystemException, DatabaseException {
        super(_context, _request);
        this.init(this.context, this.request);
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

        if (command.equalsIgnoreCase(CustomerSalesOrderViewAction.COMMAND_BACK)) {
            this.doBack();
        }
        if (command.equalsIgnoreCase(CustomerSalesOrderViewAction.COMMAND_CLOSE)) {
            this.doClose();
        }
        if (command.equalsIgnoreCase(CustomerSalesOrderViewAction.COMMAND_CANCEL)) {
            this.doCancel();
        }
        if (command.equalsIgnoreCase(CustomerSalesOrderViewAction.COMMAND_REFUND)) {
            this.doRefund();
        }
        if (command.equalsIgnoreCase(CustomerSalesOrderViewAction.COMMAND_PRINT)) {
            this.doPrint();
        }
    }

    /**
     * Cancels a sales order using sales order data originating from the user's
     * request.
     * 
     * @return The transaction id of the cancelled sales order
     * @throws ActionCommandException
     */
    public int doCancel() throws ActionCommandException {
        int xactId = 0;
        this.receiveClientData();

        // Cancel the order
        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // this.salesApi = SalesFactory.createApi((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // try {
        // xactId = super.cancel();
        // tx.commitUOW();
        // this.refreshItems();
        // this.msg = "Order has been successfully cancelled";
        // this.sendClientData();
            return xactId;
        // } catch (SalesOrderException e) {
        // CustomerSalesOrderViewAction.logger.log(Level.ERROR, e.getMessage());
        // tx.rollbackUOW();
        // throw new ActionCommandException(e.getMessage());
        // } finally {
        // this.salesApi.close();
        // tx.close();
        // this.salesApi = null;
        // tx = null;
        // }
    }

    /**
     * Refunds a sales order using sales order data originating from the user's
     * request.
     * 
     * @return The transaction id of the cancelled sales order
     * @throws ActionCommandException
     */
    public int doRefund() throws ActionCommandException {
        int xactId = 0;
        this.receiveClientData();

        // // Cancel the order
        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // this.salesApi = SalesFactory.createApi((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // try {
        // xactId = super.refund();
        // tx.commitUOW();
        // this.refreshItems();
        // this.msg = "Order has been successfully refunded";
        // this.sendClientData();
            return xactId;
        // } catch (SalesOrderException e) {
        // CustomerSalesOrderViewAction.logger.log(Level.ERROR, e.getMessage());
        // tx.rollbackUOW();
        // throw new ActionCommandException(e.getMessage());
        // } finally {
        // this.salesApi.close();
        // tx.close();
        // this.salesApi = null;
        // tx = null;
        // }
    }

    /**
     * Closes an invoiced sales order using sales order data originating from
     * the user's request. Closing a sales order is synonomous to identifying an
     * invoice as being paid.
     * 
     * @return Always returns 1 upon success.
     * @throws ActionCommandException
     */
    public int doClose() throws ActionCommandException {
        this.receiveClientData();

        // Close the order
        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // this.salesApi = SalesFactory.createApi((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // try {
        // super.closeOrder();
        // tx.commitUOW();
        // this.refreshItems();
        // this.msg = "Order has been successfully closed";
        // this.sendClientData();
            return 1;
        // } catch (SalesOrderException e) {
        // CustomerSalesOrderViewAction.logger.log(Level.ERROR, e.getMessage());
        // tx.rollbackUOW();
        // throw new ActionCommandException(e.getMessage());
        // } finally {
        // this.salesApi.close();
        // tx.close();
        // this.salesApi = null;
        // tx = null;
        // }
    }

    /**
     * Prints the invoice.
     * 
     * @throws ActionCommandException
     */
    protected void doPrint() throws ActionCommandException {
        // SalesOrderInvoiceReportAction printer = new
        // SalesOrderInvoiceReportAction();
        // String command = "Reports.SalesOrderInvoiceReport.print";
        // printer.processRequest(this.request, this.response, command);
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

    private void refreshItems() throws ActionCommandException {
        // List items;
        // try {
        // // Get Current sales order status
        // this.sos = this.getCurrentSalesOrderStatus(salesOrder.getSoId());
        // // Get existing sales order items
        // items = (List)
        // this.salesApi.findSalesOrderItems(this.salesOrder.getSoId());
        // this.itemHelper.packageItemsByTypes(items);
        // } catch (Exception e) {
        // CustomerSalesOrderViewAction.logger.log(Level.ERROR, e.getMessage());
        // throw new ActionCommandException(e);
        // }
        return;
    }

    /**
     * Packages detail Sales Order data into the HttpServlerRequest object which
     * is required to serve as the response to the client.
     * 
     * @throws ActionCommandException
     */
    public void sendClientData() throws ActionCommandException {
        super.sendClientData();
        // this.request.setAttribute(SalesConst.CLIENT_DATA_ORDERLIST,
        // this.orderList);
        // this.request.setAttribute(RMT2ServletConst.REQUEST_MSG_INFO,
        // this.msg);
    }
}