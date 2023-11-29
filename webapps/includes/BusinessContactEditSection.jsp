<!-- 
   This include provides contact data relative to a business entity and its address.
   It is expected to be used as a JSP include directive, meaning that it is inserted
   into another JSP at complie time.   
   
   This include requires the following data to be available on the user's request:
   1.  An XML representation of vw_business_address bean identified as "business".
   2.  An XML representation of genreal_codes bean identified as, "businesstypes".
       This XML document will contain all business types that will be displayed as
       as a HTML dropdown data control.
   3.  An XML representation of general_codes bean idenetified as, "businessservicetypes".
       This XML document will contain all business service types that will be displayed
       as a HTML dropdown data control
 -->
 
<beanlib:LoopRows bean="item" list="<%=AccountingConst.CLIENT_DATA_SUBSIDIARY%>">
	<table width="90%" border="0">
		<tr> 
			<td width="10%" class="clsTableFormHeader">Business Id:</td>
			<td width="21%"> 
			   <beanlib:InputControl value="#item.BusinessId"/>	
			   <beanlib:InputControl type="hidden" name="BusinessId2" value="#item.BusinessId"/>	
			</td>
			<td width="12%" class="clsTableFormHeader">Contact First Name:</td>
			<td width="24%"> 
			   <beanlib:InputControl type="text" name="ContactFirstname" value="#item.ContactFirstname" maxLength="25" tabIndex="14"/>	
			</td>
		</tr>	
		<tr> 
			<td width="10%" class="clsTableFormHeader"> Business Name:</td>
			<td width="21%"> 
			   <beanlib:InputControl type="text" name="Longname" value="#item.Longname" size="50" maxLength="80" tabIndex="10"/>							
			</td>
			<td width="12%" class="clsTableFormHeader">Contact Last Name:</td>
			<td width="21%"> 
			   <beanlib:InputControl type="text" name="ContactLastname" value="#item.ContactLastname"  maxLength="35" tabIndex="15"/>
			</td>
		</tr>
		<tr> 
			<td width="12%" class="clsTableFormHeader">Business Type:</td>
			<td width="24%"> 
				 <beanlib:InputControl dataSource="<%=AccountingConst.CLIENT_BUSTYPES %>"
									   type="select"
									   name="EntityTypeId"
									   codeProperty="code_id"
									   displayProperty="longdesc"
                                       tabIndex="11"
									   selectedValue="#item.busType"/>									
			</td>
			<td width="10%" class="clsTableFormHeader">Contact Phone:</td>
			<td width="21%"> 
			  <beanlib:InputControl type="text" name="ContactPhone" value="#item.ContactPhone" tabIndex="16"/>
			</td>							
		</tr>
		<tr> 
			<td width="10%" class="clsTableFormHeader">Service Type:</td>
			<td width="30%">
				 <beanlib:InputControl dataSource="<%=AccountingConst.CLIENT_BUSSERVTYPES %>"
									   type="select"
									   name="ServTypeId"
									   codeProperty="code_id"
									   displayProperty="longdesc"
                                       tabIndex="12"
		  							   selectedValue="#item.servType"/>										
			</td>
			<td width="12%" class="clsTableFormHeader">Contact Phone Ext.</td>
			<td width="24%"> 
			  <beanlib:InputControl type="text" name="ContactExt" value="#item.ContactExt"  maxLength="12" tabIndex="17"/>
			</td>
		</tr>
		<tr>
			<td width="12%" class="clsTableFormHeader">Tax Id:</td>
			<td width="24%"> 
			   <beanlib:InputControl type="text" name="TaxId" value="#item.TaxId" maxLength="15" tabIndex="13"/>										
			</td>
			<td width="12%" class="clsTableFormHeader">Email:</td>
			<td colspan="3" width="21%"> 
			   <beanlib:InputControl type="text" name="ContactEmail" value="#item.ContactEmail"  maxLength="50" tabIndex="18"/>													
			</td>			
		</tr>
		<tr>
			<td colspan="2"></td>
			<td width="12%" class="clsTableFormHeader">Web Site:</td>
			<td colspan="3" width="21%"> 
			   <beanlib:InputControl type="text" name="Website" value="#item.Website"  maxLength="100" tabIndex="18"/>													
			</td>			
		</tr>
	</table>
	<br>

	<table width="90%" border="0">
		<tr> 
			<td class="clsTableFormHeader" width="10%">Address 1:</td>
			<td width="24%">
			   <beanlib:InputControl type="hidden" name="AddrId" value="#item.AddrId"/>						
			   <beanlib:InputControl type="text" name="Addr1" value="#item.Addr1" size="40" maxLength="50" tabIndex="19"/>						
			</td>
			<td class="clsTableFormHeader" width="10%">City:</td>
			<td width="24%">
			   <beanlib:InputControl type="text" name="City" value="#item.City" style="border:none" readOnly="yes"/>												
			</td>
		</tr>
		<tr>
			<td class="clsTableFormHeader" width="10%"> Address 2:</td>
			<td width="24%">
			   <beanlib:InputControl type="text" name="Addr2" value="#item.addr2" size="40" maxLength="50" tabIndex="20"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">State:</td>
			<td width="24%">
			   <beanlib:InputControl type="text" name="State" value="#item.State" style="border:none" readOnly="yes"/>																		
			</td>								
		</tr>
		<tr>
			<td class="clsTableFormHeader" width="10%">Address 3:</td>
			<td width="24%">
			   <beanlib:InputControl type="text" name="Addr3" value="#item.Addr3" size="40" maxLength="50" tabIndex="21"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">Main Phone:</td>
			<td width="24%">
			   <beanlib:InputControl type="text" name="PhoneMain" value="#item.PhoneMain" maxLength="20" tabIndex="25"/>						
			</td>
		</tr>              
		<tr> 
			<td class="clsTableFormHeader" width="10%">Address 4:</td>
			<td width="24%">
			   <beanlib:InputControl type="text" name="Addr4" value="#item.Addr4" size="40" maxLength="50" tabIndex="22"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">Fax Phone:</td>
			<td width="24%">
			   <beanlib:InputControl type="text" name="PhoneFax" value="#item.PhoneFax" maxLength="20" tabIndex="26"/>												
			</td>
		</tr>
		<tr> 
			<td class="clsTableFormHeader" width="10%">Zip:</td>
			<td width="24%">
			   <beanlib:InputControl type="text" name="Zip" value="#item.Zip" size="10" maxLength="5" tabIndex="23"/>&nbsp;-&nbsp;
			   <beanlib:InputControl type="text" name="Zipext" value="#item.Zipext" size="10" maxLength="4" tabIndex="24"/>						                         
			</td>
			<td width="10%">&nbsp; </td>
			<td width="24%">&nbsp;</td>
		</tr>
	</table>
</beanlib:LoopRows>
