<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.api.constants.GeneralConst" %>
<%@ page import="com.action.xact.sales.SalesConst" %>


<gen:InitAppRoot id="APP_ROOT"/>

<jsp:useBean id="QUERY_BEAN" scope="session" class="com.api.security.RMT2TagQueryBean"/>

<html>
	<head>
		<title>Customer Sales Order Console</title>
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Expires" content="-1">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
		<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	</head>
	
	<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2Contact.js"></script>
	<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
	<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
	<script type="text/javascript" src="<%=APP_ROOT%>/js/RMT2AcctXact.js"></script>
		
    <%
         // Retrieve data to be maintained from the request object.
		 String pageTitle = "Customer Sales On Account Console";
     %>

	<body bgcolor="#FFFFFF" text="#000000">
		<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/SalesCustomerConsole.SalesConsole">
        
        <!-- Display base customer section -->
        <%@include file="/forms/customer/CustomerView.jsp"%>
        <br>
           
        <!-- Display business contact data for customer -->			
		<%@include file="../../../includes/BusinessContactViewSection.jsp"%>
		<br>				
		    
	    <table>
		  <tr>
			 <td><img src="<%=APP_ROOT%>/images/clr.gif" height="10"></td>
			 <td><img src="<%=APP_ROOT%>/images/clr.gif" height="10"></td>
			 <td><img src="<%=APP_ROOT%>/images/clr.gif" height="10"></td>
		  </tr>
		  <tr>
   			 <td>
			    <input type="button" name="<%=SalesConst.REQ_VIEWORDERS%>" value="Order History" style="width:120"  onClick="handleAction('_self', document.DataForm, this.name)">
          		<input type="button" name="<%=SalesConst.REQ_NEW%>" value="New Order" style="width:120" onClick="handleAction('_self', document.DataForm, this.name)">
          		<input type="button" name="<%=SalesConst.REQ_PAYMENT%>" value="Receive Payment" style="width:120" onClick="handleAction('_self', document.DataForm, this.name)">
		      	<input type="button" name="<%=SalesConst.REQ_VIEW%>" value="Transactions" style="width:120" onClick="handleAction('_self', document.DataForm, this.name)">
		        <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
		        <a href="/ui-jsp-accounting/">Main Menu</a>
			 </td>
		  </tr>
		</table>
        <input name="clientAction" type="hidden">
	</form>
</body>
</html>
