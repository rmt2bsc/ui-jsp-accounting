<!-- 
   This include provides contact data relative to a business entity and its address.
   It is expected to be used as a JSP include directive, meaning that it is inserted
   into another JSP at compile time.   
   
   This include requires the following data to be available on the user's request:
   1.  An XML representation of vw_business_address bean identified as "business".
   2.  An XML representation of genreal_codes bean identified as, "businesstypes".
       This XML document will contain all business types that will be displayed as
       as a HTML drop down data control.
   3.  An XML representation of general_codes bean identified as, "businessservicetypes".
       This XML document will contain all business service types that will be displayed
       as a HTML drop down data control
 -->
 
	<table width="90%" border="0">
		<tr> 
			<td width="10%" class="clsTableFormHeader">Business Id:</td>
			<td width="21%"> 
			   <beanlib:InputControl value="#subsidiary.BusinessId"/>	
			   <beanlib:InputControl type="hidden" name="BusinessId" value="#subsidiary.BusinessId"/>	
			</td>
			<td width="12%" class="clsTableFormHeader">Contact First Name:</td>
			<td width="24%"> 
			   <beanlib:InputControl value="#subsidiary.ContactFirstname"/>	
			</td>
		</tr>	
		<tr> 
			<td width="10%" class="clsTableFormHeader"> Business Name:</td>
			<td width="21%"> 
			   <beanlib:InputControl value="#subsidiary.Longname"/>							
			</td>
			<td width="12%" class="clsTableFormHeader">Contact Last Name:</td>
			<td width="21%"> 
			   <beanlib:InputControl value="#subsidiary.ContactLastname"/>
			</td>
		</tr>
		<tr> 
			<td width="12%" class="clsTableFormHeader">Business Type:</td>
			<td width="24%"> 
				 <beanlib:InputControl value="#subsidiary.EntityTypeName"/>									
			</td>
			<td width="10%" class="clsTableFormHeader">Contact Phone:</td>
			<td width="21%"> 
			  <beanlib:InputControl value="#subsidiary.ContactPhone"/>
			</td>							
		</tr>
		<tr> 
			<td width="10%" class="clsTableFormHeader">Service Type:</td>
			<td width="30%">
				 <beanlib:InputControl value="#subsidiary.ServTypeName"/>										
			</td>
			<td width="12%" class="clsTableFormHeader">Contact Phone Ext.</td>
			<td width="24%"> 
			  <beanlib:InputControl value="#subsidiary.ContactExt"/>
			</td>
		</tr>
		<tr>
			<td width="12%" class="clsTableFormHeader">Tax Id:</td>
			<td width="24%"> 
			   <beanlib:InputControl value="#subsidiary.TaxId"/>										
			</td>
			<td width="12%" class="clsTableFormHeader">Email:</td>
			<td colspan="3" width="21%"> 
			   <beanlib:InputControl value="#subsidiary.ContactEmail"/>													
			</td>			
		</tr>
		<tr>
			<td colspan="2"></td>
			<td width="12%" class="clsTableFormHeader">Web Site:</td>
			<td colspan="3" width="21%"> 
			   <beanlib:InputControl value="#subsidiary.Website"/>													
			</td>			
		</tr>
	</table>
	<br>

	<table width="90%" border="0">
		<tr> 
			<td class="clsTableFormHeader" width="10%">Address 1:</td>
			<td width="24%">
			   <beanlib:InputControl type="hidden" name="AddrId" value="#subsidiary.AddrId"/>						
			   <beanlib:InputControl value="#subsidiary.Addr1"/>						
			</td>
			<td class="clsTableFormHeader" width="10%">City:</td>
			<td width="24%">
			   <beanlib:InputControl value="#subsidiary.City"/>												
			</td>
		</tr>
		<tr>
			<td class="clsTableFormHeader" width="10%"> Address 2:</td>
			<td width="24%">
			   <beanlib:InputControl value="#subsidiary.addr2"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">State:</td>
			<td width="24%">
			   <beanlib:InputControl value="#subsidiary.State"/>																		
			</td>								
		</tr>
		<tr>
			<td class="clsTableFormHeader" width="10%">Address 3:</td>
			<td width="24%">
			   <beanlib:InputControl value="#subsidiary.Addr3"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">Main Phone:</td>
			<td width="24%">
			   <beanlib:InputControl value="#subsidiary.PhoneMain"/>						
			</td>
		</tr>              
		<tr> 
			<td class="clsTableFormHeader" width="10%">Address 4:</td>
			<td width="24%">
			   <beanlib:InputControl value="#subsidiary.Addr4"/>												
			</td>
			<td class="clsTableFormHeader" width="10%">Fax Phone:</td>
			<td width="24%">
			   <beanlib:InputControl value="#subsidiary.PhoneFax"/>												
			</td>
		</tr>
		<tr> 
			<td class="clsTableFormHeader" width="10%">Zip:</td>
			<td width="24%">
			   <beanlib:InputControl value="#subsidiary.Zip"/>&nbsp;-&nbsp;
			   <beanlib:InputControl value="#subsidiary.Zipext"/>						                         
			</td>
			<td width="10%">&nbsp; </td>
			<td width="24%">&nbsp;</td>
		</tr>
	</table>
