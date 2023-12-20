package com.action.xact.sales;

import java.util.List;

import org.apache.log4j.Logger;

import com.SystemException;
import com.api.persistence.DatabaseException;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.Request;
import com.api.web.Response;
import com.entity.CustomerCriteria;
import com.entity.VwSalesOrderInvoice;

/**
 * This class provides functionality to serve the requests of the Customer Sales
 * Order List user interface.
 * 
 * @author Roy Terrell
 *
 */
public class CustomerSalesOrderListAction extends CustomerSalesConsoleAction {

    private static final String COMMAND_EDIT = "SalesCustomerOrderList.OrderList.edit";

    private static final String COMMAND_VIEW = "SalesCustomerOrderList.OrderList.view";

    private static final String COMMAND_PRINT = "SalesCustomerOrderList.OrderList.print";

    private static final String COMMAND_BACK = "SalesCustomerOrderList.OrderList.back";

    private static final String COMMAND_SEARCH = "SalesCustomerOrderList.OrderList.search";

    private static Logger logger;

    /**
     * Default constructor
     *
     */
    public CustomerSalesOrderListAction() {
        super();
        CustomerSalesOrderListAction.logger = Logger.getLogger(CustomerSalesOrderListAction.class);
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
    public CustomerSalesOrderListAction(Context _context, Request _request) throws SystemException, DatabaseException {
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

        if (command.equalsIgnoreCase(CustomerSalesOrderListAction.COMMAND_EDIT)
                || command.equalsIgnoreCase(CustomerSalesOrderListAction.COMMAND_VIEW)) {
            this.editData();
        }
        if (command.equalsIgnoreCase(CustomerSalesOrderListAction.COMMAND_PRINT)) {
            this.doPrint();
        }
        if (command.equalsIgnoreCase(CustomerSalesOrderListAction.COMMAND_BACK)) {
            this.doBack();
        }
        if (command.equalsIgnoreCase(CustomerSalesOrderListAction.COMMAND_SEARCH)) {
            this.doBackToSearch();
        }
    }



    /**
     * Gathers data pertaining to sales order total, sales invoice, sales order
     * status, and sales order items regardless of the mode (View or Edit) which
     * the sales order details interface will display data.
     * 
     * @throws ActionCommandException
     */
    public void edit() throws ActionCommandException {
        List<VwSalesOrderInvoice> results = this.getCustomerSalesOrderFull();
        if (results != null && results.size() == 1) {
            this.salesOrder = results.get(0);
        }
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
     * Navigates the user back to the customer search console JSP and performs
     * the previous customer query.
     * 
     * @throws ActionCommandException
     */
    private void doBackToSearch() throws ActionCommandException {
        CustomerCriteria criteria = (CustomerCriteria) this.query.getCustomObj();
        this.customers = this.getCustomers(criteria);
        this.sendClientData();
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
     * Retrieves basic customer, sales order, and transaction data from the
     * client's request. If transaction is founded to be invoiced, then the
     * transaction is retrieved from the database instead.
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
    }
}