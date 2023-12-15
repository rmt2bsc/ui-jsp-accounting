<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.action.xact.sales.SalesConst" %>
<%@ page import="com.api.constants.GeneralConst" %>
<%@ page import="com.api.constants.GeneralConst" %>


<gen:InitAppRoot id="APP_ROOT"/>

<html>
  <title>Customer Sales Order History</title>
  <head>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    <link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="<%=APP_ROOT%>/css/RMT2General.css">   
  </head>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2General.js"></script>
  <script Language="JavaScript" src="<%=APP_ROOT%>/js/RMT2Menu.js"></script>   
  

  <%
	 String pageTitle = "Customer Order History:&nbsp;&nbsp;&nbsp; ";
  %>
  
  <body>
     <form name="DataForm" method="POST" action="<%=APP_ROOT%>/unsecureRequestProcessor/SalesCustomerOrderList.OrderList">
	     <beanlib:InputControl type="hidden" value="#beanObj.customerId" name="CustomerId"/>
		   <table width="55%" border="3">
	       <caption align="left">
 					  <h3><%=pageTitle%><font color="blue"><beanlib:InputControl value="#beanObj.Description"/></font></h3> 
			   </caption>
			      <tr>
				       <td>
					   
    					  <div id="Layer1" style="width:100%; height:650px; z-index:1; overflow:auto; background-color: buttonface" >
							 <table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
								 <tr>
									 <th class="clsTableListHeader">&nbsp;</th>
									 <th class="clsTableListHeader" >Order No.</th>
									 <th class="clsTableListHeader">Date Entered</th>
									 <th class="clsTableListHeader" >Invoice Id</th>
									 <th class="clsTableListHeader" >Trans. Id</th>
									 <th class="clsTableListHeader">Trans. Date</th>
									 <th class="clsTableListHeader" style="text-align:right">Order Amount</th>
									 <th class="clsTableListHeader" style="text-align:center">Order Status</th>
									 <th class="clsTableListHeader" style="text-align:left">Invoice Number</th>
								 </tr>
				   

								 <beanlib:LoopRows bean="beanObj" list="<%=SalesConst.CLIENT_DATA_ORDERLIST%>">
									  <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>

										 <td width="5%" align="center" class="clsTableListHeader">
											 <beanlib:InputControl type="radio" name="selCbx" value="rowid"/>
											 <beanlib:InputControl type="hidden" name="SalesOrderId" value="#beanObj.SalesOrderId" uniqueName="yes"/>
											 <beanlib:InputControl type="hidden" name="CustomerId" value="#beanObj.CustomerId" uniqueName="yes"/>   
											 <beanlib:InputControl type="hidden" name="XactId" value="#beanObj.XactId" uniqueName="yes"/>                                                                 
											 <beanlib:InputControl type="hidden" name="OrderStatusId" value="#beanObj.OrderStatusId" uniqueName="yes"/>                                                                 
										 </td>   
										 <td width="10%" align="center" >
											<font size="2">
											  <beanlib:InputControl value="#beanObj.SalesOrderId"/>
											</font>
										 </td>
											 <td width="10%" align="left">
											<font size="2"> 
											  <beanlib:InputControl value="#beanObj.SalesOrderDate" format="MM-dd-yyyy"/>
											</font>
										 </td>     
										 <td width="10%" align="center">
											<font size="2"> 
											  <beanlib:InputControl value="#beanObj.InvoiceId"/>
											</font>
										 </td>
										 <td width="10%" align="center">
											<font size="2"> 
											  <beanlib:InputControl value="#beanObj.XactId"/>
											</font>
										 </td>
										 <td width="10%" align="center">
											<font size="2"> 
											  <beanlib:InputControl value="#beanObj.InvoiceDate" format="MM-dd-yyyy"/>
											</font>
										 </td>
										 <td width="10%" align="right">
											<font size="2">
											   <beanlib:InputControl value="#beanObj.OrderTotal" format="$#,##0.00;($#,##0.00)"/>
											</font>
										 </td>
										 <td width="20%" align="center">
											<font size="2">
											   <beanlib:InputControl dataSource="beanObj" value="#beanObj.OrderStatusDescr" />
											</font>
										</td>
										 <td width="15%" align="left" >
											<font size="2">
											   <beanlib:InputControl dataSource="beanObj" value="#beanObj.InvoiceNo"/>
											</font>
										</td>							
									 </tr>
								 </beanlib:LoopRows>
			
								 <% if (pageContext.getAttribute("ROW") == null) {
									   out.println("<tr><td colspan=7 align=center>Data Not Found</td></tr>");
									}
								 %>
							 </table>
						  </div>	
					   </td>
				  </tr>
			   </table>
			  <br>
			  
			  <!-- Display any messages -->
			  <table>
				 <tr>
		  		   <td>
				     <font color="red">
					     <gen:ShowPageMessages dataSource="<%=RMT2ServletConst.REQUEST_MSG_MESSAGES%>"/>
				     </font>
			 	   </td>
				 </tr>
			  </table>
			  <br>
			 
  		      <table>
		        <tr>
		          <td> 
				      <input type="button" name="<%=GeneralConst.REQ_EDIT%>" value="Edit" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">
 					  <input type="button" name="<%=GeneralConst.REQ_PRINT%>" value="Print Invoice" style="width:90" onClick="handleAction('_blank', document.DataForm, this.name)">
					  <input type="button" name="<%=GeneralConst.REQ_BACK%>" value="Back" style="width:90" onClick="handleAction('_self', document.DataForm, this.name)">	 					
			      </td>
			    </tr>
		      </table>
 		      <input name="clientAction" type="hidden">		  
     </form>
  </body>
</html>
