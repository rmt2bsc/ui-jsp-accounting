<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.AccountingConst" %>
<%@ page import="com.action.inventory.ItemConst" %>
<%@ page import="com.api.constants.RMT2ServletConst" %>
<%@ page import="com.entity.VwItemMaster" %>

<gen:InitAppRoot id="APP_ROOT"/>

 <%
   	  VwItemMaster item = (VwItemMaster) request.getAttribute("item");
	  String pageTitle = "Inventory Item Master Maintenance- " + (item.getId() > 0 ? "Edit" : "Add");
// 	  String vendorCriteria = "creditor_type_id = " + AccountingConst.CREDITOR_TYPE_VENDOR;
  %>
  
  
<html>
  <head>
    <title><%=pageTitle %></title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	<script>
	   function handleOverrideRetailChange(item) {
		  if (item.checked == true) {
			  item.value = "1";
		  }
		  else {
			  item.value = "0";
		  }
	   }
	</script>
  </head>
 				
  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

     <!--  Begin Search Criteria section -->
     <form name="EditForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/Inventory.Edit">
        <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:60%; height:50%">
           <table width="100%" cellpadding="0" cellspacing="2">
               <tr>
                  <td>&nbsp;</td>
               </tr>            
               <tr>
                  <td width="95%">
                     <font size="4" style="color:blue">Item Data</font>
                     <div id="ItemLayer" style="border-style:double;border-color:#999999; width:95%; height:60%">
                        <table width="100%" cellpadding="0" cellspacing="2">
                           <tr>
                              <td width="25%" class="clsTableFormHeader">Item Number</td>
                              <td width="25%">
               					  <beanlib:InputControl value="#item.Id"/>
               					  <beanlib:InputControl type="hidden" name="Id" value="#item.Id"/>
                              </td>
                              <td width="25%" align="right" class="clsTableFormHeader">Item Status</td>
                              <td width="25%">
                                  <font color="red">
                                     <beanlib:InputControl dataSource="item" value="#item.ItemStatus"/>
                                  </font>
                              </td>
                           </tr>                           
                           <tr>
                              <td class="clsTableFormHeader">Item Name</td>
                              <td colspan="3">
                                  <beanlib:InputControl dataSource="item" 
                                                        type="text" 
                                                        name="Description" 
                                                        value="#item.Description" 
                                                        size="65%" 
                                                        maxLength="80"/>
                              </td>
                           </tr>
                           <tr>
                              <td class="clsTableFormHeader">Item Type</td>
                              <td>
                                  <beanlib:InputControl dataSource="<%=ItemConst.CLIENT_DATA_ITEMTYPELIST %>" 
								                        type="select" 
							     	 			        name="ItemTypeId" 
								 				  	    codeProperty="ItemTypeId" 
													    displayProperty="Description" 
													    selectedValue="#item.ItemTypeId"/>
                              </td>
                              <td align="right" class="clsTableFormHeader">Active</td>
                              <td>
                                 <input type="checkbox" name="Active" value="1" <%=item.getActive() == 1 ? "checked" : ""%>>
                              </td>
                           </tr>                           
                           <tr>
                              <td class="clsTableFormHeader">Unit Cost</td>
                              <td>
               					  <beanlib:InputControl dataSource="item" 
               					                        type="text" 
               					                        name="UnitCost" 
               					                        value="#item.UnitCost" 
               					                        style="text-align:right"/>
                              </td>
                              <td align="right" class="clsTableFormHeader">Qty On Hand</td>
                              <td>
                                  <beanlib:InputControl dataSource="item" 
                                                        type="text" 
                                                        name="QtyOnHand" 
                                                        value="#item.QtyOnHand" 
                                                        style="text-align:right"/>
                              </td>
                           </tr>                           
                           <tr>
                              <td class="clsTableFormHeader">Retail Price</td>
                              <td>
               					  <beanlib:InputControl dataSource="item" 
               					                        type="text" 
               					                        name="RetailPrice" 
               					                        value="#item.RetailPrice" 
               					                        style="text-align:right"/>
                              </td>
                              <td align="right" class="clsTableFormHeader">Mark Up</td>
                              <td>
                                  <beanlib:InputControl dataSource="item" 
                                                        type="text" 
                                                        name="Markup" 
                                                        value="#item.Markup" 
                                                        style="text-align:right"/>
                              </td>
                           </tr>                                                      
                           <tr>
                              <td class="clsTableFormHeader">Override Retail</td>
                              <td colspan="3" align="left">
                                  <beanlib:InputControl dataSource="item" 
                                                        type="checkbox" 
                                                        name="OverrideRetail"
                                                        value="#item.OverrideRetail"
                                                        checkedValue="1"
                                                        onClick="handleOverrideRetailChange(this)"/>
                              </td>
                           </tr>                                                                                 
                        </table>
                     </div>
                  </td>
               </tr>
               <tr>
                  <td>&nbsp;</td>
               </tr>
               <tr>
                  <td>
                     <font size="4" style="color:blue">Vendor Data</font>
                     <div id="VendorLayer" align="center" style="border-style:double;border-color:#999999; width:50%; height:20%">
                        <table width="100%" cellpadding="0" cellspacing="2">
                           <tr>
                              <td width="30%" class="clsTableFormHeader">Vendor Name</td>
                              <td>
                                  <beanlib:InputControl dataSource="<%=ItemConst.CLIENT_DATA_VENDORLIST %>" 
					                       type="select" 
				     	 			       name="VendorId" 
					 					   codeProperty="CreditorId" 
										   displayProperty="Longname" 
										   selectedValue="#item.VendorId"/>
                              </td>
                           </tr>
                           <tr>
                              <td class="clsTableFormHeader">Vendor Item No.</td>
                              <td>
                                 <beanlib:InputControl dataSource="item" 
                                                       type="text" 
                                                       name="VendorItemNo" 
                                                       value="#item.VendorItemNo" 
                                                       size="30" 
                                                       maxLength="25"/>
                              </td>
                           </tr>                                                
                           <tr>
                              <td class="clsTableFormHeader">Serial Number</td>
                              <td>
                                 <beanlib:InputControl dataSource="item" 
                                                       type="text" 
                                                       name="ItemSerialNo" 
                                                       value="#item.ItemSerialNo" 
                                                       size="30" 
                                                       maxLength="25"/>
                              </td>
                           </tr>                                                
                        </table>
                     </div>                  
                  </td>
               </tr>
               <tr>
                  <td>&nbsp;</td>
               </tr>     
               
               <gen:Evaluate  expression="#item.Id">
				  <gen:When expression="0">
				     <tr><td></td></tr>
				  </gen:When>
				  <gen:WhenElse>
					   <tr>
		                  <td>
	                        <table width="100%" cellpadding="0" cellspacing="2">
	                           <tr>
	                              <td width="30%" valign="top" class="clsTableFormHeader">Reason for Change</td>
	                              <td width="70%">
	                                  <TEXTAREA name="Reason" rows="2" cols="40"></TEXTAREA>
	                              </td>
	                           </tr>
	                        </table>
		                  </td>
		               </tr>               
				  </gen:WhenElse>
			   </gen:Evaluate>          
            </table>
         </div>
           	
         <br>
	     <!-- Display any messages -->
		  <table>
			<tr>
			  <td colspan="3">
			     <font color="red">
				     <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
				   </font>
			  </td>
			</tr>
	      </table>
	    		
	    <br>
        <!-- Display command buttons -->  
        <table width="100%" cellpadding="0" cellspacing="0">
           <tr>
              <td>&nbsp;</td>
           </tr>               	        
		   <tr>
		      <td colspan="2">
		         <input name="save" type="button" value="Save" style="width:90" onClick="handleAction('_self', document.EditForm, this.name)">
		         <input name="add" type="button" value="New" style="width:90" onClick="handleAction('_self', document.EditForm, this.name)">
		         <input name="delete" type="button" value="Delete" style="width:90" onClick="handleAction('_self', document.EditForm, this.name)">
		         <input name="back" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.EditForm, this.name)">
		      </td>
	       </tr>		
           <tr>
              <td>&nbsp;</td>
           </tr>               	        		       
        </table>            
		<input name="clientAction" type="hidden">	    
     </form>
     <br>
 </body>
</html>
