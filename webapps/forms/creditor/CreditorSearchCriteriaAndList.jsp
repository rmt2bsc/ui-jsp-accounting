
    <font size="3" style="color:blue">Selection Criteria</font>
	<div style="border-style:groove;border-color:#999999; background-color:buttonface; width:60%; height:175px">
		<table width="40%" border="0">
			<tr> 
				<td width="33%" bgcolor="#FFCC00"> 
					<div align="right"><font size="2"><b>Creditor Type:</b></font></div>
				</td>
				<td width="67%">
					<beanlib:InputControl dataSource="<%=AccountingConst.CLIENT_CREDITORTYPE_LIST %>" 
					                     type="select" 
				     	 				 name="qry_CreditorTypeId" 
					 					 codeProperty="CreditorTypeId" 
										 displayProperty="Description" 
										 selectedValue="#QUERY_BEAN.CustomObj.Qry_CreditorTypeId"/>
				</td>
			</tr>    
			<tr> 
				<td width="10%" bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Creditor ID:</font></b></div>
				</td>
				<td width="21%"> 
				  <beanlib:InputControl type="text" name="qry_CreditorId" value="#QUERY_BEAN.CustomObj.qry_CreditorId"/>
				</td>
			</tr>	
			<tr> 
				<td width="10%" bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Account Number :</font></b></div>
				</td>
				<td width="21%"> 
				  <beanlib:InputControl type="text" name="qry_AccountNo" value="#QUERY_BEAN.CustomObj.qry_AccountNo"/>
				</td>
			</tr>
			<tr> 
				<td width="10%" bgcolor="#FFCC00"> 
					<div align="right"><b><font size="2">Name:</font></b></div>
				</td>
				<td width="21%"> 
				  <beanlib:InputControl type="text" name="qry_Name" value="#QUERY_BEAN.CustomObj.qry_Name" size="40"/>
				</td>
			</tr>									
		</table>
		<br>
	  </div>	  
	 <br>         
		 
   <font size="3" style="color:blue">Search Results</font>
   <div style="border-style:groove; border-color:#999999; background-color:buttonface; width:60%; height:450px; overflow:auto">
   		<table  width="100%" border="0" bgcolor="white" cellpadding="0" cellspacing="0"> 
			 <tr>
				 <th width="3%" class="clsTableListHeader">&nbsp;</th>
				 <th width="7%" class="clsTableListHeader">Id</th>
				 <th width="30%" class="clsTableListHeader" style="text-align:left">Creditor Name</th>
				 <th width="15%" class="clsTableListHeader" style="text-align:left">Acct No.</th>
				 <th width="10%" class="clsTableListHeader" style="text-align:left">Type</th>
				 <th width="10%" class="clsTableListHeader" style="text-align:right">Credit Limit</th>
				 <th width="15%" class="clsTableListHeader" style="text-align:right">Balance</th>
				 <th width="5%"  class="clsTableListHeader">&nbsp;</th>
			 </tr>
			 
			 <beanlib:LoopRows bean="beanObj" list="<%=GeneralConst.CLIENT_DATA_LIST %>">
			     <gen:ColorBarLines evenColor="#CCFFCC" oddColor="#FFFFFF"/>
				 <td  align="center" class="clsTableListHeader">
					 <beanlib:InputControl type="radio" name="selCbx" value="rowid"/>
					 <beanlib:InputControl type="hidden" name="CreditorId" value="#beanObj.CreditorId" uniqueName="yes"/>                                  
					 <beanlib:InputControl type="hidden" name="BusinessId" value="#beanObj.BusinessId" uniqueName="yes"/>                                  
				 </td>   
				 <td  align="center" >
					<font size="2">
					  <beanlib:InputControl value="#beanObj.CreditorId"/>
					</font>
				 </td>             
				 <td>
					 <font size="2">
						<beanlib:InputControl value="#beanObj.Longname"/>
					</font>
				 </td>							 
				 <td>
					 <font size="2">
						<beanlib:InputControl value="#beanObj.AccountNumber"/>
					 </font>
				 </td>					 
				 <td>
					 <font size="2">
						 <gen:Evaluate expression="#beanObj.CreditorTypeId">
							<gen:When expression="1">Vendor</gen:When>
							<gen:When expression="2">Creditor</gen:When>
						 </gen:Evaluate>
					 </font>
				 </td>					 
				 <td align="right">
					<font size="2">
					  <beanlib:InputControl value="#beanObj.CreditLimit" format="$#,##0.00;($#,##0.00)"/>
					</font>
				 </td>
				 <td align="right">
					 <font size="2">
						<beanlib:InputControl value="#beanObj.Balance" format="$#,##0.00;($#,##0.00)"/>
					 </font>
				 </td>
 				 <td>&nbsp;</td>

				 </tr>
			 </beanlib:LoopRows>
			 <% if (pageContext.getAttribute("ROW") == null) {
					out.println("<tr><td colspan=8 align=center>Data Not Found</td></tr>");
				}
			 %>
		 </table>
      </div>
  <db:Dispose/>