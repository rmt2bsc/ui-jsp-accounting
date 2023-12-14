<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.api.constants.GeneralConst" %>

<gen:InitAppRoot id="APP_ROOT"/>

<jsp:useBean id="QUERY_BEAN" scope="session" class="com.api.security.RMT2TagQueryBean"/>

<html>
  <head>
    <title>Customer Sales Order Search</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">
	<script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
    <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>
  </head>

  <%
	 String pageTitle = "Sales On Account - Customer Search";
  %>
				      
  <body bgcolor="#FFFFFF" text="#000000">
   <form name="SearchForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/SalesCustomerSearch.Search">
      <h3><%=pageTitle%></h3>
 	  <%@include file="/forms/customer/CustomerSearchCriteriaAndList.jsp"%>

      <br>
      <!-- Display Search List command buttons -->         
	  <table width="100%" cellpadding="0" cellspacing="0">
		  <tr>
			<td colspan="2">
			  <input name="<%=GeneralConst.REQ_EDIT%>" type="button" value="Sales Console" style="width:120" onClick="handleAction('_self', document.SearchForm, this.name)">
			  <input name="<%=GeneralConst.REQ_BACK%>" type="button" value="Back" style="width:90" onClick="handleAction('_self', document.SearchForm, this.name)">
    		</td>
		  </tr>		
	  </table>
	  <input name="clientAction" type="hidden">
   </form>
 </body>
 </html>
