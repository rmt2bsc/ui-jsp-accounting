<%@page import="com.action.inventory.ItemConst"%>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.action.inventory.ItemConst" %>
<%@ page import="com.action.xact.sales.SalesConst" %>
<%@ page import="com.api.constants.GeneralConst" %>
<%@ page import="com.api.util.RMT2Utility" %>

<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <head>
    <title>Sales Order Item Selection</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>

   <%
      String pageTitle = "New Sales On Account - Inventory Item Selection";
 	 %>
	
  <body bgcolor="#FFFFFF" text="#000000">
       <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/SalesCustomerItemSelection.Select">
          <beanlib:InputControl type="hidden" name="CustomerId" value="#subsidiary.CustomerId"/>
          <beanlib:InputControl type="hidden" name="OrderId" value="0"/>
          <beanlib:InputControl type="hidden" name="BusinessId" value="#subsidiary.BusinessId"/>
 	      <h3><strong><%=pageTitle%></strong></h3>
	  
		<table width="60%" border="0">
			<tr> 
				<td align="right" width="30%" bgcolor="#FFCC00"> 
				    <font size="2">
					   <b>Account Number</b>
					</font>
				</td>
				<td width="70%">
		   		   	<beanlib:InputControl value="#subsidiary.AccountNo"/>
		   		   	<beanlib:InputControl type="hidden" name="AccountNo" value="#subsidiary.AccountNo"/>
				</td>
			</tr>    
			<tr> 
				<td align="right" width="30%" bgcolor="#FFCC00"> 
				    <font size="2">
					   <b>Account Name</b>
					</font>
				</td>
				<td width="70%">
				   <beanlib:InputControl value="#subsidiary.Longname"/>
				   <beanlib:InputControl type="hidden" name="Longname" value="#subsidiary.Longname"/>
				</td>
			</tr>    
			<tr> 
				<td align="right" width="30%" bgcolor="#FFCC00"> 
				    <font size="2">
					   <b>Sales Order</b>
					</font>
				</td>
				<td width="70%">
				   <beanlib:InputControl value="0"/>
				</td>
			</tr>    
		</table>
		<br>
	  
		<table width="80%" border="0">
			<tr> 
				<td align="left" width="50%" >
				     <font size="2">
						<b>Services</b>
					</font>
				 </td>
				<td align="left" width="50%">
			        <font size="2">
						<b>Merchandise</b>
					</font>
				 </td>
			</tr>
			<tr> 
				<td width="50%" > 
					 <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_ITEMS_SERVICE %>"
										 type="select"
										 name="<%=ItemConst.SEL_NEW_ITEM_SRVC %>"
										 codeProperty="Id"
										 displayProperty="Description"
										 multiSelect="Yes"
										 size="15"
										 style="width:400"/>
				</td>
				<td width="50%"> 
					 <beanlib:InputControl dataSource="<%=SalesConst.CLIENT_DATA_ITEMS_MERCHANDISE %>"
										 type="select"
										 name="<%=ItemConst.SEL_NEW_ITEM_MERCH %>"
										 codeProperty="Id"
										 displayProperty="Description"
										 multiSelect="Yes"
										 size="15"
										 style="width:400"/>				
				</td>
			</tr>			
		</table>
		<br>
	  
	   <!-- Display command buttons -->
	  <table width="100%" cellpadding="0" cellspacing="0">
		  <tr>
			<td colspan="2">
			   <input name="<%=GeneralConst.REQ_BACK%>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
  			   <input name="<%=GeneralConst.REQ_NEXT%>" type="button" value="Next" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
			</td>
		 </tr>		
	  </table>
	 <input name="clientAction" type="hidden">
   </form>
 </body>
</html>
