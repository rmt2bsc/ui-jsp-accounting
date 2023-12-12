package com.action.inventory;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.rmt2.jaxb.AccountingTransactionResponse;
import org.rmt2.jaxb.InventoryResponse;
import org.rmt2.jaxb.ReplyStatusType;

import com.AccountingUIException;
import com.SystemException;
import com.action.gl.subsidiary.creditor.CreditorConst;
import com.action.gl.subsidiary.creditor.CreditorSoapRequests;
import com.api.constants.GeneralConst;
import com.api.jsp.action.AbstractActionHandler;
import com.api.persistence.DatabaseException;
import com.api.util.RMT2Money;
import com.api.web.ActionCommandException;
import com.api.web.Context;
import com.api.web.ICommand;
import com.api.web.Request;
import com.api.web.Response;
import com.api.web.util.RMT2WebUtility;
import com.entity.Creditor;
import com.entity.CreditorCriteria;
import com.entity.CreditorFactory;
import com.entity.ItemMasterCriteria;
import com.entity.ItemMasterStatus;
import com.entity.ItemMasterStatusFactory;
import com.entity.ItemMasterType;
import com.entity.ItemMasterTypeFactory;
import com.entity.VwItemMaster;
import com.entity.VwItemMasterFactory;

/**
 * This abstract class provides common functionality needed to serve various
 * user interfaces pertaining to Inventory Master Item maintenance.
 * 
 * @author Roy Terrell
 * 
 */
public abstract class AbstractInventoryAction extends AbstractActionHandler implements ICommand {
    
    private Logger logger;

    /** list of vendors */
    protected Object vendorList;

    /** list of item master types */
    protected Object itemTypeList;

    /** list of item master statuses */
    protected Object itemStatusList;

    /** A list of item master records */
    protected List<VwItemMaster> items;

    /** A single item master record */
    protected VwItemMaster item;

    /** Current Item Master ID */
    protected int itemMasterId;

    /**
     * Default constructor
     * 
     */
    public AbstractInventoryAction() {
        super();
        logger = Logger.getLogger(AbstractInventoryAction.class);
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
    public AbstractInventoryAction(Context _context, Request _request) throws SystemException, DatabaseException {
        super(_context, _request);
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
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.api.jsp.action.ICommonAction#add()
     */
    @Override
    public void add() throws ActionCommandException {
        this.setupLookupData();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.api.jsp.action.ICommonAction#edit()
     */
    @Override
    public void edit() throws ActionCommandException {
        this.setupLookupData();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.api.jsp.action.ICommonAction#delete()
     */
    @Override
    public void delete() throws ActionCommandException, DatabaseException {
        this.setupLookupData();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.api.jsp.action.ICommonAction#save()
     */
    @Override
    public void save() throws ActionCommandException, DatabaseException {
        this.setupLookupData();        
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
     * Retrieves a lists of General Code records based on general code groups
     * Business Entity Type and Business Services Type.
     * 
     * @throws ActionCommandException
     */
    protected void setupLookupData() throws ActionCommandException {
        this.vendorList = this.getVendors();
        this.itemTypeList = this.getItemTypes();
        this.itemStatusList = this.getItemStatuses();
    }

    /**
     * Call SOAP web service to get a list of Creditors based on selection
     * criteria
     * 
     * @return List<{@link Creditor}>
     * @throws AccountingUIException
     */
    private List<Creditor> getVendors() throws AccountingUIException {
        try {
            CreditorCriteria criteria = CreditorCriteria.getInstance();
            criteria.setQry_CreditorTypeId(String.valueOf(CreditorConst.CREDITORTYPE_VENDOR));
            AccountingTransactionResponse response = CreditorSoapRequests.callGet(criteria, this.loginId,
                    this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.throwActionError(rst.getMessage(), rst.getExtMessage());
            }
            List<Creditor> results = null;
            if (response.getProfile() != null && response.getProfile().getCreditors() != null) {
                results = CreditorFactory.create(response.getProfile().getCreditors().getCreditor());
            }
            else {
                results = new ArrayList<>();
            }
            this.msg += ": " + results.size();
            return results;
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new AccountingUIException(e.getMessage());
        }
    }

    /**
     * Call SOAP web service to get a list of Item Master Types
     * 
     * @return List<{@link ItemMasterType}>
     * @throws AccountingUIException
     */
    private List<ItemMasterType> getItemTypes() throws AccountingUIException {
        try {
            InventoryResponse response = ItemTypeSoapRequests.callGet(this.loginId, this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.throwActionError(rst.getMessage(), rst.getExtMessage());
            }
            List<ItemMasterType> results = null;
            if (response.getProfile() != null && response.getProfile().getInvItemType() != null) {
                results = ItemMasterTypeFactory.create(response.getProfile().getInvItemType());
            }
            else {
                results = new ArrayList<>();
            }
            this.msg += ": " + results.size();
            return results;
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new AccountingUIException(e.getMessage());
        }
    }

    /**
     * Call SOAP web service to get a list of Item Master Statuses
     * 
     * @return List<{@link ItemMasterStatus}>
     * @throws AccountingUIException
     */
    private List<ItemMasterStatus> getItemStatuses() throws AccountingUIException {
        try {
            InventoryResponse response = ItemStatusSoapRequests.callGet(this.loginId, this.session.getId());
            ReplyStatusType rst = response.getReplyStatus();
            if (rst.getReturnCode().intValue() == GeneralConst.RC_FAILURE) {
                this.throwActionError(rst.getMessage(), rst.getExtMessage());
            }
            List<ItemMasterStatus> results = null;
            if (response.getProfile() != null && response.getProfile().getInvItemStatus() != null) {
                results = ItemMasterStatusFactory.create(response.getProfile().getInvItemStatus());
            }
            else {
                results = new ArrayList<>();
            }
            this.msg += ": " + results.size();
            return results;
        } catch (Exception e) {
            logger.log(Level.ERROR, e.getMessage());
            throw new AccountingUIException(e.getMessage());
        }
    }

    /**
     * Obtains common key item master related data items from the client JSP.
     */
    public void receiveClientData() throws ActionCommandException {
        // Attempt to locate and obtain item master ID from the JSP.
        String temp = this.getInputValue("Id", null);
        this.itemMasterId = RMT2Money.stringToNumber(temp).intValue();

        // Attempt to locate and obtain item master profile data from the JSP.
        try {
            this.item = VwItemMasterFactory.create();
            RMT2WebUtility.packageBean(this.request, this.item);
        } catch (Exception e) {
            throw new ActionCommandException(e.getMessage());
        }
    }

    /**
     * <p>
     * Gathers data for the following objects and returns the data to the client
     * via the HttpServletRequest object to be handle at the discretion of the
     * client:
     * <p>
     * <table width="100%" border="1" cellspacing="0" cellpadding="0">
     * <tr>
     * <td><strong>Attribute</strong></td>
     * <td><strong>Type</strong></td>
     * <td><strong>Description</strong></td>
     * <td><strong>Attribute Name</strong></td>
     * </tr>
     * <tr>
     * <td>CREDITOR</td>
     * <td>{@link Creditor}</td>
     * <td>Creditor data</td>
     * <td>listdata</td>
     * </tr>
     * <tr>
     * <td>CREDITOR LIST</td>
     * <td>List of {@link CreditorExt}</td>
     * <td>List of Creditors</td>
     * <td>listdata</td>
     * </tr>
     * <tr>
     * <td>BUSINESS CONTACT</td>
     * <td>XML from contact application</td>
     * <td>Business and Address data</td>
     * <td>business</td>
     * </tr>
     * </table>
     * 
     * @throws ActionCommandException
     */
    public void sendClientData() throws ActionCommandException {
        this.request.setAttribute(ItemConst.CLIENT_DATA_ITEM, this.item);
        this.request.setAttribute(ItemConst.CLIENT_DATA_ITEMS, this.items);
        this.request.setAttribute(ItemConst.CLIENT_DATA_VENDORLIST, this.vendorList);
        this.request.setAttribute(ItemConst.CLIENT_DATA_ITEMTYPELIST, this.itemTypeList);
        this.request.setAttribute(ItemConst.CLIENT_DATA_ITEMSTATUSLIST, this.itemStatusList);
    }
}