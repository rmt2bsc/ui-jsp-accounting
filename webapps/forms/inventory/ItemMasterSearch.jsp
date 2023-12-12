<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.entity.ItemMasterCriteria" %>
<%@ page import="com.api.security.RMT2TagQueryBean" %>
<%@ page import="com.AccountingConst" %>
<%@ page import="com.action.inventory.ItemConst" %>
<%@ page import="com.api.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>
<html>
  <head>
    <title>Transaction Search Criteria</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>
  
  <%
	  String pageTitle = "Item Master Maintenance Console";
  %>
				
  <body bgcolor="#FFFFFF" text="#000000">
     <h3><strong><%=pageTitle%></strong></h3>

     <!--  Begin Search Criteria section -->
     <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/Inventory.Search">
        <font size="4" style="color:blue">Selection Criteria</font>
        <div style="border-style:groove;border-color:#999999; background-color:buttonface; width:90%; height:120px">
            <table width="100%" cellpadding="0" cellspacing="2">
               <tr>
                  <td width="15%" class="clsTableFormHeader">Item No.</td>
                  <td width="35%">
                     <beanlib:InputControl type="text" name="qry_Id" value="#QUERY_BEAN.CustomObj.qry_Id"/>
                  </td>
                  <td width="15%" class="clsTableFormHeader">Item Name</td>
                  <td width="35%">
                     <beanlib:InputControl type="text" name="qry_Description" size="55" value="#QUERY_BEAN.CustomObj.qry_Description"/>
                  </td>                  
               </tr>                           
               <tr>
                  <td class="clsTableFormHeader">Vendor</td>
                  <td>
                      <beanlib:InputControl dataSource="<%=ItemConst.CLIENT_DATA_VENDORLIST %>" 
					                       type="select" 
				     	 			       name="qry_VendorId" 
					 					   codeProperty="CreditorId" 
										   displayProperty="Longname" 
										   selectedValue="QUERY_BEAN.CustomObj.qry_VendorId"/>
                  </td>
                  <td class="clsTableFormHeader">Item Type</td>
                  <td>
                     <beanlib:InputControl dataSource="<%=ItemConst.CLIENT_DATA_ITEMTYPELIST %>" 
					                       type="select" 
				     	 			       name="qry_ItemTypeId" 
					 					   codeProperty="ItemTypeId" 
										   displayProperty="Description" 
										   selectedValue="QUERY_BEAN.CustomObj.qry_ItemTypeId"/>
                  </td>                  
               </tr>
               
               <tr>
                  <td class="clsTableFormHeader">Vendor Item No.</td>
                  <td>
                     <beanlib:InputControl type="text" name="qry_VendorItemNo" size="40" value="#QUERY_BEAN.CustomObj.qry_VendorItemNo"/>
                  </td>
                  <td class="clsTableFormHeader">Serial No.</td>
                  <td>
                      <beanlib:InputControl type="text" name="qry_ItemSerialNo" size="40" value="#QUERY_BEAN.CustomObj.qry_ItemSerialNo"/>
                  </td>                  
               </tr>               
               
               <tr>
                  <td class="clsTableFormHeader">Unit Cost</td>
                  <td>
                     <gen:CondOps name="qryRelOp_UnitCost" 
                                  selectedValue="#QUERY_BEAN.getQryRelOp_UnitCost()"/>
                     <beanlib:InputControl type="text" name="qry_UnitCost" size="15" value="#QUERY_BEAN.CustomObj.qry_UnitCost"/>
                  </td>
                  <td class="clsTableFormHeader">Qty On Hand</td>
                  <td>
                     <beanlib:InputControl type="text" name="qry_QtyOnHand" size="10" value="#QUERY_BEAN.CustomObj.qry_QtyOnHand"/>
                  </td>                  
               </tr>                              
               
               <tr>
                  <td class="clsTableFormHeader">Item Status</td>
                  <td>
                     <beanlib:InputControl dataSource="<%=ItemConst.CLIENT_DATA_ITEMSTATUSLIST %>" 
					                       type="select" 
				     	 			       name="qry_ItemStatusId" 
					 					   codeProperty="ItemStatusId" 
										   displayProperty="Description" 
										   selectedValue="#QUERY_BEAN.CustomObj.qry_ItemStatusId"/>
                  </td>
                  <td>&nbsp;</td>
                  <td>
                     <table width="15%" border="0">
				        <tr>
				            <th>Active</th>
				            <td>
				                <input type="radio" 
				                       name="qry_Active" 
				                       value="1"
				                       size="10" 
					                <gen:Evaluate  expression="#QUERY_BEAN.CustomObj.qry_Active">
										<gen:When expression="1">checked</gen:When>
									</gen:Evaluate> >
				            </td>
							<th>Inactive</th>
				            <td>
				                <input type="radio" 
				                       name="qry_Active" 
				                       value="0" 
				                       size="10" 
 						               <gen:Evaluate  expression="#QUERY_BEAN.CustomObj.qry_Active">
										<gen:When expression="0">checked</gen:When>
								   </gen:Evaluate>  >
				            </td>					            
				        </tr>
			         </table>
                  </td>                  
               </tr>            
            </table>
        </div>
        <br>
        
        <!-- Display command buttons -->  
        <table width="100%" cellpadding="0" cellspacing="0">
		   <tr>
		      <td colspan="2">
		         <input name="search" type="button" value="Search" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
		         <input name="reset" type="reset" value="Clear" style="width:90">
		      </td>
	       </tr>		
       </table>            
	   <input name="clientAction" type="hidden">        
     </form>
     <br>
     
     <!--  Begin Search Criteria section -->
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/Inventory.Search">
        <font size="4" style="color:blue">Search Results</font>
        <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:90%; height:480px; overflow:auto">
            <table width="100%" cellpadding="0" cellspacing="0">
				<tr>
				   <th width="3%" class="clsTableListHeader">&nbsp;</th>
				   <th width="6%" class="clsTableListHeader" style="text-align:left">Item No.</th>
				   <th width="39%" class="clsTableListHeader"  style="text-align:left">Item Name</th>
				   <th width="2%" class="clsTableListHeader">&nbsp;</th>
				   <th width="8%" class="clsTableListHeader" style="text-align:left">Item Type</th>
				   <th width="8%" class="clsTableListHeader" style="text-align:right">Unit Cost</th>
				   <th width="10%" class="clsTableListHeader" style="text-align:right">Qty On Hand</th>
				   <th width="8%" class="clsTableListHeader" style="text-align:center">Override</th>
				   <th width="2%" class="clsTableListHeader">&nbsp;</th>
				   <th width="8%" class="clsTableListHeader" style="text-align:left">Status</th>
				   <th width="6%" class="clsTableListHeader" style="text-align:left">Vendor</th>
			   </tr>
               
   			   <beanlib:LoopRows bean="item" list="<%=ItemConst.CLIENT_DATA_ITEMS %>">
			     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
			        <td class="clsTableListHeader">
			            <beanlib:InputControl  type="radio" name="selCbx" value="rowid"/>
					    <beanlib:InputControl type="hidden" name="Id" value="#item.Id" uniqueName="yes"/>
			        <td>
			            <beanlib:InputControl  value="#item.Id"/> 
			        </td>
			        <td>
			            <beanlib:InputControl  value="#item.Description"/> 
			        </td>
			        <td align="right">&nbsp;</td>
			        <td>
			            <beanlib:InputControl  value="#item.ItemTypeDescription"/> 
			        </td>
			        <td align="right">
			            <font color="blue">
			               <beanlib:InputControl  value="#item.UnitCost" format="$#,##0.00;($#,##0.00)"/> 
			            </font>
			        </td>
			        <td align="right">
			            <font color="blue">
			               <beanlib:InputControl  value="#item.QtyOnHand" format="#,##0;(#,##0)"/> 
			            </font>
			        </td>
					<td align="center">
					   <gen:Evaluate  expression="#item.OverrideRetail">
						  <gen:When expression="1">Yes</gen:When>
						  <gen:WhenElse>No</gen:WhenElse>
				       </gen:Evaluate>
			        </td>			        
			        <td align="right">&nbsp;</td>			        
			        <td>
			             <beanlib:InputControl  value="#item.ItemStatus"/> 
			        </td>			        			        			        			        			        
			        <td>
			            <beanlib:InputControl  value="#item.VendorId"/> 
			        </td>			        
			     </tr>
			   </beanlib:LoopRows> 
			   <% if (pageContext.getAttribute("ROW") == null) {
				     out.println("<tr><td colspan=10 align=center>Data Not Found</td></tr>");
				  }
			   %>			   
			   
            </table>
        </div>       
        <br>
        
        <!-- Display command buttons -->  
        <table width="100%" cellpadding="0" cellspacing="0">
		   <tr>
		      <td colspan="2">
		         <input name="edit" type="button" value="Edit" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
		         <input name="add" type="button" value="Add" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
		         <input name="back" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
		      </td>
	       </tr>		
        </table>            
		<input name="clientAction" type="hidden">
     </form>
 </body>
</html>
