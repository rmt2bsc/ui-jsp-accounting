<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.api.util.RMT2Utility" %>
<%@ page import="com.action.xact.sales.SalesConst" %>
<%@ page import="com.api.constants.GeneralConst" %>
<%@ page import="com.api.constants.RMT2ServletConst" %>


<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Edit Customer Sales Order Details</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
    <script type='text/javascript' language='javascript' src="<%=APP_ROOT%>/js/datetimepicker.js"></script>
    <script Language="JavaScript">
			function enableReason() {
			   var obj;
			   
			   obj = event.srcElement;
			   if (obj.checked == true) {
			      ReasonLabel.style.visibility = "visible";
			      this.document.DataForm.Reason.style.visibility = "visible";
			      InvoiceAndReceiveLabel.style.visibility = "visible";
			      this.document.DataForm.InvoiceAndReceive.style.visibility = "visible";
			   }
			   else {
			      ReasonLabel.style.visibility = "hidden";
			      this.document.DataForm.Reason.style.visibility = "hidden";
			      InvoiceAndReceiveLabel.style.visibility = "hidden";
			      this.document.DataForm.InvoiceAndReceive.style.visibility = "hidden";
			   }
			   return;
			}
    </script>
  </head>


   <%          
		 String pageTitle = "Customer Sales Order Edit";
	%>
	
  <body bgcolor="#FFFFFF" text="#000000" onLoad="initPage()">
    <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/SalesCustomerOrderEdit.OrderEdit">
	     <beanlib:InputControl type="hidden" name="CustomerId" value="#salesorder.CustomerId"/>                                  
		   <beanlib:InputControl type="hidden" name="BusinessId" value="#salesorder.BusinessId"/>                                  
		   <beanlib:InputControl type="hidden" name="OrderId" value="#salesorder.SoId"/>                                  
		   <beanlib:InputControl type="hidden" name="XactId" value="#salesorder.XactId"/>
		   <beanlib:InputControl type="hidden" name="Invoiced" value="#salesorder.Invoiced"/>                                              
	      
		  <h3><strong><%=pageTitle%></strong></h3>
	  
		  <table width="60%" border="0">
		     <tr> 
				<td align="right" width="30%" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Customer Id</b>
					 </font>
				</td>
				<td width="70%">
				   <beanlib:InputControl value="#salesorder.CustomerId"/> 
				</td>
			</tr>    
			 <tr> 
				<td align="right" width="30%" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Account Number</b>
					 </font>
				</td>
				<td width="70%">
				   <beanlib:InputControl value="#salesorder.AccountNo"/> 
				    <beanlib:InputControl type="hidden" name="AccountNo" value="#salesorder.AccountNo"/>
				</td>
			</tr>    
			<tr> 
				<td align="right" width="35%" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Account Name</b>
					</font>
				 </td>
				 <td width="65%">
				     <beanlib:InputControl value="#salesorder.Description"/> 
				     <beanlib:InputControl type="hidden" name="Longname" value="#salesorder.Description"/>
				 </td>
			</tr>   
			<tr> 
				<td align="right" width="35%" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Order Number</b>
					</font>
				 </td>
				 <td width="65%">
				    <beanlib:InputControl value="#salesorder.SalesOrderId"/> 
				    <beanlib:InputControl type="hidden" name="SalesOrderId" value="#salesorder.SalesOrderId"/>
				</td>
			</tr>   			
			<tr> 
				<td align="right" width="35%" bgcolor="#FFCC00"> 
					<font size="2">
						<b>Sales Order Date</b>
					</font>
				</td>
				<td width="70%">
				   <beanlib:InputControl id="SalesOrderDate" type="text" name="EffectiveDate" value="#salesorder.SalesOrderDate" format="MM-dd-yyyy"/>
				   <a href="javascript:NewCal('SalesOrderDate','mmddyyyy')">
				      <img src="<%=APP_ROOT%>/images/cal.gif" width="16" height="16" border="0" alt="Pick sales order date">
				   </a>
				</td>
			</tr>    			
		</table>
		<br>
	  
    <div id="OuterLayer"></div>
			<table width="50%" border="3" bgcolor="#AACCCC" >
				<tr> 
					<td align="left" width="50%" bgcolor="#0000CC">
						 <font size="3" color="white">
							<b>Items</b>
						</font>
					 </td>
				</tr>
				<tr> 
					<td width="100%"> 
						 <div id="SrvcLayer" style="position:relative; width:100%; height:40%; z-index:1; overflow:auto;">
							 <table width="100% border="0">
							    <tr>
								     <th width="6%" align="left" bgcolor="#FFCC00">Del</th>
								     <th width="64%" align="left" bgcolor="#FFCC00">Item Name</th>
									 <th width="10%"  align="center" bgcolor="#FFCC00">Price</th>
									 <th width="10%"  align="center" bgcolor="#FFCC00">Order Qty</th>
									 <th width="10%"  align="center" bgcolor="#FFCC00">Qty Avail</th>
								 </tr>
								<beanlib:LoopRows bean="item" list="<%=SalesConst.CLIENT_DATA_SALESORDER_ITEMS %>">
									<tr>
									     <td>
										     <beanlib:InputControl dataSource="item" type="checkbox" name="selCbx" value="rowid" uniqueName="yes"/>
											 <beanlib:InputControl dataSource="item" type="hidden" name="rowId" value="rowid"/>
										 </td>
										 <td>
										      <beanlib:InputControl type="hidden" name="SalesOrderId" value="#item.SoId" uniqueName="yes"/>                                  
										      <beanlib:InputControl type="hidden" name="ItemId" value="#item.ItemId" uniqueName="yes"/>                                  										 
  											  <beanlib:InputControl value="#item.ItemName"/>                                  
  											  <beanlib:InputControl type="hidden" name="ItemName" value="#item.ItemName" uniqueName="yes"/>  
										 </td>
										 <td  align="right" valign="bottom">
											  <beanlib:InputControl value="#item.InitUnitCost" style="text-align:right" format="#,##0.00;(#,##0.00)"/>                                  
											  <beanlib:InputControl type="hidden" name="RetailPrice" value="#item.InitUnitCost" uniqueName="yes"/>
										 </td>
										 <td  align="right" valign="bottom">
											  <beanlib:InputControl type="text" name="OrderQty" value="#item.OrderQty" style="text-align:right" size="5" uniqueName="yes"/>                                  
										 </td>
										 <td  align="center" valign="bottom">
											  <beanlib:InputControl value="#item.QtyOnHand"/> 
											  <beanlib:InputControl type="hidden" name="QtyOnHand" value="#item.QtyOnHand" uniqueName="yes"/>                                 
											  <beanlib:InputControl type="hidden" name="InitMarkup" value="#item.InitMarkup" uniqueName="yes"/>
										 </td>
									 </tr>
								 </beanlib:LoopRows>
							  </table>					 
						 </div> 
					</td>
				</tr>	
			</table>
			
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
		  				  
			<div id="OrderTotalLayer" style="position:relative; top:25px; width:100%; height:80px; z-index:1">
			  <table width="80%" cellpadding="0" cellspacing="0">
				  <tr>
					<td width="14%" align="right">
					    <font size="2">
						    <b>Order Total:</b>
						</font>
					</td>
					<td width="1%"><img src="/images/clr.gif" width="1"></td>
					<td width="85%" align="left">
   					    <font size="3" color="blue">
   					      <strong>
                            <beanlib:InputControl value="#salesorder.OrderTotal" format="$#,##0.00;($#,##0.00)"/>
                          </strong>
						  <beanlib:InputControl type="hidden" name="XactAmount" value="#salesorder.OrderTotal"/>                                  
					    </font>
					</td>
				 </tr>		
				 
				<gen:Evaluate expression="#salesorder.Invoiced">
				  <gen:When expression="0">
				    <tr>
						<td align="right">
							<font size="2">
								<b>Invoice:</b>
							</font>
						</td>
						<td><img src="/images/clr.gif" width="1"></td>
						<td align="left">
							<font size="3" color="blue">
						  	 <beanlib:InputControl type="checkbox" name="Invoiced" value="#salesorder.Invoiced" checkedValue="1" onClick="enableReason()"/> 
							</font>
						</td>
					</tr>
					<tr>
						<td id="InvoiceAndReceiveLabel" align="right" style="visibility:hidden">
							<font size="2">
								<b>Invoice as Receipt:</b>
							</font>
						</td>
						<td><img src="/images/clr.gif" width="1"></td>
						<td align="left">
							<font size="3" color="blue">
						  	 <beanlib:InputControl type="checkbox" name="InvoiceAndReceive" value="1" style="visibility:hidden"/> 
							</font>
						</td>
					</tr>
				    <tr>
						<td id="ReasonLabel" align="right" style="visibility:hidden">
							<font size="2">
								<b>Invoice Reason:</b>
							</font>
						</td>
						<td><img src="/images/clr.gif" width="1"></td>
						<td align="left">
							<font size="3" color="blue">
							   <beanlib:InputControl type="text" name="Reason" value="#salesorder.Reason" size="90" maxLength="100" style="visibility:hidden"/>
							</font>
						</td>
					 </tr>						 	
				  </gen:When>
				  <gen:When expression="1">
				    <tr>
						<td id="ReasonLabel" align="right">
							<font size="2">
								<b>Invoice Reason:</b>
							</font>
						</td>
						<td><img src="/images/clr.gif" width="1"></td>
						<td align="left">
							<font size="3" color="black">
							   <beanlib:InputControl value="#salesorder.Reason"/>
							</font>
						</td>
					 </tr>						 	
				  </gen:When>
		  	</gen:Evaluate>
		  </table>			   
		 </div>	  <!-- end ButtonLayer div -->
     
		  <!-- Display command buttons -->
			<div id="ButtonLayer" style="position:relative; top:50px; width:100%; height:30px; z-index:1">
			  <table width="100%" cellpadding="0" cellspacing="0">
				  <tr>
					<td colspan="2">
						<gen:Evaluate expression="#salesorder.Invoiced">
						  <gen:When expression="0">
  						  <input name="<%=GeneralConst.REQ_ADD%>" type="button" value="Add Item" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
   						  <input name="<%=GeneralConst.REQ_SAVE%>" type="button" value="Update" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
						  </gen:When>
				  	</gen:Evaluate>
					  <input name="<%=GeneralConst.REQ_DELETE%>" type="button" value="Delete" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
					  <input name="<%=GeneralConst.REQ_BACK%>" type="button" value="Order History" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
					</td>
				 </tr>		
			  </table>			   
			 </div>	  <!-- end ButtonLayer div -->

		</div>  <!-- end OuterLayer div -->
	 <input name="clientAction" type="hidden">
   </form>
 </body>
</html>
