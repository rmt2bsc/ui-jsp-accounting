<table width="35%" border="0">
 <tr> 
	<td align="right" width="30%"> 
		<font size="2">
			<b>Account Number:</b>
		 </font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#salesorder.AccountNo"/> 
	</td>
</tr>    
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Account Name:</b>
		</font>
	 </td>
	 <td width="65%">
		 <beanlib:InputControl value="#salesorder.Description"/> 
		 <beanlib:InputControl type="hidden" name="Longname" value="#salesorder.Description"/>
	 </td>
</tr>   
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Order Number:</b>
		</font>
	 </td>
	 <td width="65%">
		<beanlib:InputControl value="#salesorder.SalesOrderId"/> 
	</td>
</tr>   			
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Order Date:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#salesorder.SalesOrderDate" format="MM-dd-yyyy H:m:s"/>
	</td>
</tr>   
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Order Status:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#salesorder.OrderStatusDescr"/>
	   <beanlib:InputControl type="hidden" value="#salesorder.OrderStatusId" name="OrderStatusId"/>
	</td>
</tr>   
<tr> 
	<td align="right" width="35%" valign="top"> 
		<font size="2">
			<b>Order/Invoice Reason:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#salesorder.XactReason"/>
	   <beanlib:InputControl type="hidden" value="#salesorder.XactId" name="XactId"/>
	</td>
</tr>   
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Invoice No.:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#salesorder.InvoiceNo"/>
	</td>
</tr> 			
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Invoice Date:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#salesorder.InvoiceDate" format="MM-dd-yyyy H:m:s"/>
	</td>
</tr> 
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Reference No.:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#salesorder.EntityRefNo"/>
	</td>
</tr> 			  			 			
<tr> 
	<td align="right" width="35%"> 
		<font size="2">
			<b>Confirmation No.:</b>
		</font>
	</td>
	<td width="70%">
	   <beanlib:InputControl value="#salesorder.ConfirmNo"/>
	</td>
</tr> 			  			 			
</table>
<br>

<table width="60% border="0"  cellspacing="0" cellpadding="0">
<tr>
	 <th width="15%" align="left" bgcolor="#FFCC00">
		<font size="2">Item No.</font>
	 </th>
	 <th width="45%" align="left" bgcolor="#FFCC00">
		<font size="2">Item Name</font>
	 </th>
	 <th width="15%"  align="center" bgcolor="#FFCC00">
		<font size="2">Order Qty</font>
	 </th>				 
	 <th width="25%"  align="right" bgcolor="#FFCC00">
		<font size="2">Price</font>
	 </th>
 </tr>
 <beanlib:LoopRows bean="item" list="<%=SalesConst.CLIENT_DATA_SALESORDER_ITEMS %>">
    <%String test = "";%>
	<tr>
		 <td>
			  <beanlib:InputControl value="#item.ItemId"/>
		 </td>
		 <td>
			  <beanlib:InputControl value="#item.ItemName"/>                                  
		 </td>
		 <td  align="center" valign="bottom">
			  <beanlib:InputControl value="#item.OrderQty" format="#,##0"/>                                  
		 </td>
		 <td  align="right" valign="bottom">
			  <beanlib:InputControl value="#item.InitUnitCost" format="#,##0.00;(#,##0.00)"/>                                  
		 </td>
	 </tr>
  </beanlib:LoopRows>
  <tr>
	 <td colspan="4" align="right"><br></td>
  </tr>			 
  <tr>
	 <td colspan="3" align="right">
		 <font size="2"><b>Order Total</b></font>
	 </td>
	 <td  align="right" valign="bottom">
		 <beanlib:InputControl value="#salesorder.OrderTotal" format="$#,##0.00;($#,##0.00)"/>
	 </td>
 </tr>
</table>					