   					  
	<table width="80%" border="0">			
		 <caption align="left"><h3><%=pageTitle%></h3></caption>
		<tr>
			<td width="10%" class="clsTableFormHeader">Account Number:</td>
			<td width="15%">
			    <input type="hidden" name="CreditorId">
			    <input type="hidden" name="AccountNumber">
			</td>
			<td width="10%" class="clsTableFormHeader">Credit Limit:</td>
			<td width="15%"> 
			   <input type="text" name="CreditLimit" value="0.00" tabIndex="1">
			</td>
			<td width="10%" class="clsTableFormHeader">Balance:</td>
			<td width="15%">0.00</td>					
			<td width="10%" class="clsTableFormHeader">Creditor Type:</td>
			<td width="15%"> 
				 <beanlib:InputControl dataSource="<%=AccountingConst.CLIENT_CREDITORTYPE_LIST %>"
									   type="select"
									   name="CreditorTypeId"
									   codeProperty="CreditorTypeId"
									   displayProperty="Description"
	                                   tabIndex="2"
									   selectedValue="#subsidiary.CreditorTypeId"/>
			</td>
			
		</tr>								
	</table>