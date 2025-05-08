<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ page import="com.api.util.RMT2Utility" %>
<%@ page import="com.AccountingConst" %>
<%@ page import="com.api.constants.GeneralConst" %>


<gen:InitAppRoot id="APP_ROOT"/>

<html>
<head>
<title>General Ledger Account Types</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<script>
	function handleAction(_action) {
     GLAcctTypeListForm.target = "EditFrame";
  	 GLAcctTypeListForm.clientAction.value = "list";
	 GLAcctTypeListForm.submit();
	}
	
	function handleAction3(_action) {
	     GLAcctTypeListForm.target = "EditFrame";
	  	 GLAcctTypeListForm.clientAction.value = _action;
		 GLAcctTypeListForm.submit();
		}
</script>
											
<body bgcolor="#FFFFCC" text="#000000">
<form name="GLAcctTypeListForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/AcctCatgConsole.AcctTypeCatg">
  <table width="20%" border="1">
	  <caption>
		 <strong>GL Account Type List</strong>
	  </caption>  
    <tr bgcolor="FFCC00"> 
      <td width="14%" bgcolor="FFCC00">&nbsp; </td>
      <td width="86%"> 
        <div align="center">
            <b>
                <font size="2">Description</font>
            </b>
        </div>
      </td>
    </tr>
    
    <beanlib:LoopRows bean="item" list="<%=AccountingConst.CLIENT_DATA_ACCTTYPE_LIST %>">
		<td width="14%" bgcolor="FFCC00">
			<beanlib:InputControl type="radio" name="selCbx" value="rowid" onClick="handleAction(this)"/>
			<beanlib:InputControl type="hidden" name="AcctTypeId" value="#item.AcctTypeId" uniqueName="yes"/>					  
			<beanlib:InputControl type="hidden" name="Description" value="#item.Description" uniqueName="yes"/>
		</td>
		<td width="86%">
			<beanlib:InputControl value="#item.Description"/>					
		</td>
	 </tr>
     </beanlib:LoopRows>
  </table>
  <br>
  <input type="button" name="<%=GeneralConst.REQ_ADD%>" value="Add Category" style=width:90 onClick="handleAction3(this.name)">
  <input type="hidden" name="clientAction">
</form>

</body>
</html>
