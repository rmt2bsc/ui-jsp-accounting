<!-- 
   This include provides base creditor data for viewing and/or editing.
   It is expected to be used as a JSP include directive, meaning that it is inserted
   into another JSP at compile time.   
   
   This include requires the following data to be available on the user's request:
   1.  A entity bean of type, Creditor, which is identified as "this.cred".
   2.  A Double data type identified as "Balance".
 -->
  	<table width="80%" border="0">			
		<caption align="left"><h3><%=pageTitle%></h3></caption>
		<tr>
			<td width="12%" class="clsTableFormHeader">Account Number:</td>
			<td width="21%">
			   <beanlib:InputControl type="hidden" name="CreditorId" value="#subsidiary.CreditorId"/>													
			   <beanlib:InputControl type="hidden" name="AccountNumber" value="#subsidiary.AccountNumber"/>
			   <beanlib:InputControl value="#subsidiary.AccountNumber"/>
			</td>
			<td width="12%" class="clsTableFormHeader">Credit Limit:</td>
			<td width="21%"> 
				<beanlib:InputControl name="CreditLimit" type="text" value="#subsidiary.CreditLimit" format="#,##0.00;(#,##0.00)" tabIndex="1"/>						   
			</td>
			<td width="12%" class="clsTableFormHeader">Balance:</td>
			<td width="21%"> 
			   <beanlib:InputControl value="#subsidiary.Balance" format="$#,##0.00;($#,##0.00)"/>
			</td>					
		</tr>				
		
		<tr>
			<td width="12%" class="clsTableFormHeader">Creditor Id:</td>
			<td width="21%"> 
			   <beanlib:InputControl value="#subsidiary.CreditorId"/>
			</td>
			<td width="12%" class="clsTableFormHeader">Creditor Type:</td>
			<td width="24%"> 
				 <beanlib:InputControl dataSource="<%=AccountingConst.CLIENT_CREDITORTYPE_LIST %>"
									   type="select"
									   name="CreditorTypeId"
									   codeProperty="CreditorTypeId"
									   displayProperty="Description"
	                                   tabIndex="2"
									   selectedValue="#subsidiary.CreditorTypeId"/>
			</td>
			<td width="12%" class="clsTableFormHeader">APR:</td>
			<td width="21%">
				<beanlib:InputControl type="text" name="Apr" value="#subsidiary.Apr" tabIndex="3"/>
			</td>
		</tr>					
	</table>