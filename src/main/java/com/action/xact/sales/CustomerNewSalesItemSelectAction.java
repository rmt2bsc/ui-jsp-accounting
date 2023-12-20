package com.action.xact.sales;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.SystemException;
import com.api.persistence.DatabaseException;
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
public class CustomerNewSalesItemSelectAction extends CustomerSalesConsoleAction {

    private static final String COMMAND_ADD = "SalesCustomerItemSelection.Select.next";

    private static final String COMMAND_BACK = "SalesCustomerItemSelection.Select.back";

    private static Logger logger;

    /**
     * Default constructor
     *
     */
    public CustomerNewSalesItemSelectAction() {
        super();
        CustomerNewSalesItemSelectAction.logger = Logger.getLogger(CustomerNewSalesItemSelectAction.class);
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

        if (command.equalsIgnoreCase(CustomerNewSalesItemSelectAction.COMMAND_ADD)) {
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
        List existItems;
        super.add();
        CustomerNewSalesItemSelectAction.logger.log(Level.DEBUG, "Building original Sales Order");

        // Convert and add existing sales order items to the sales order item
        // helper
        // if (this.salesOrder.getSoId() > 0) {
        // DatabaseTransApi tx = DatabaseTransFactory.create();
        // this.salesApi = SalesFactory.createApi((DatabaseConnectionBean)
        // tx.getConnector(), this.request);
        // try {
        // existItems = (List)
        // this.salesApi.findSalesOrderItems(this.salesOrder.getSoId());
        // this.itemHelper.packageItemsByTypes(existItems);
        // }
        // catch (Exception e) {
        // throw new ActionCommandException(e.getMessage());
        // }
        // finally {
        // this.salesApi.close();
        // this.salesApi = null;
        // tx.close();
        // tx = null;
        // }
        // }
        //
        // CustomerNewSalesItemSelectAction.logger.log(Level.DEBUG,
        // "Building new Sales Order");
        // String selectedItems[] = this.httpHelper.getHttpNewItemSelections();
        //
        // try {
        // // Convert and add newly selected item numbers to the sales order
        // item helper
        // this.itemHelper.packageItemsByTypes(selectedItems);
        // }
        // catch (SystemException e) {
        // CustomerNewSalesItemSelectAction.logger.log(Level.ERROR,
        // e.getMessage());
        // throw new ActionCommandException(e);
        // }
        // return;
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