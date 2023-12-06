<!-- 
   This include provides base customer data for viewing and/or editing.
   It is expected to be used as a JSP include directive, meaning that it is inserted
   into another JSP at compile time.   
   
   This include requires the following data to be available on the user's request:
   1.  A entity bean of type, Customer, which is identified as "customer".
   2.  A Double data type identified as "customerbalance".
 -->
 
<table width="90%" border="0">
	<caption align="left"> <h3><%=pageTitle%></h3> </caption>
	<tr> 
		<td width="19%"  class="clsTableFormHeader"> 
			<div align="right">
			   <font size="2">
				 <b>Account Number:</b>
			   </font>
			</div>
		</td>	
		<td width="81%">
			<beanlib:InputControl type="hidden" name="CustomerId" value="#subsidiary.CustomerId"/>
			<beanlib:InputControl type="hidden" name="AccountNo" value="#subsidiary.AccountNo"/>
		    <beanlib:InputControl value="#subsidiary.AccountNo"/>
		</td>									 
	</tr>
	<tr> 
		<td width="19%"  class="clsTableFormHeader"> 
			<div align="right">
			   <font size="2">
				 <b>Balance:</b>
			   </font>
			</div>
		</td>	
		<td width="81%">
		   <beanlib:InputControl value="#subsidiary.balance" format="$#,##0.00;($#,##0.00)"/>
		</td>									 
	</tr>				
	<tr> 
		<td width="19%"  class="clsTableFormHeader"> 
			<div align="right">
			   <font size="2">
				 <b>Credit Limit:</b>
			   </font>
			</div>
		</td>	
		<td width="81%">
		  <beanlib:InputControl type="text" name="CreditLimit" value="#subsidiary.CreditLimit" tabIndex="1"/>
		</td>									 
	</tr>				
	<tr> 
		<td width="19%"  class="clsTableFormHeader"> 
			<div align="right">
			   <font size="2">
				 <b>Description:</b>
			   </font>
			</div>
		</td>	
		<td width="81%">
		   <beanlib:InputControl type="text" name="Description" value="#subsidiary.Description" tabIndex="2"/>
		</td>									 
	</tr>				
</table>