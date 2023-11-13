<%@ taglib uri="/rmt2-generaltaglib" prefix="gen" %>
<%@ taglib uri="/rmt2-beantaglib" prefix="beanlib" %>
<%@ page import="com.api.util.RMT2Utility" %>
<%@ page import="com.AccountingConst" %>


<gen:InitAppRoot id="APP_ROOT"/>

<html>
<head>
<title>General Ledger Account Types</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<script>
	function handleAction(_action) {
     DataForm.target = "ListFrame2";
  	 DataForm.clientAction.value = "catglist";
     DataForm.submit();
	}
</script>

<body bgcolor="#FFFFCC" text="#000000">
<form name="DataForm" method="post" action="<%=APP_ROOT%>/unsecureRequestProcessor/AccountsConsole.Accounts">
  <table width="80%" border="0">
    <tr bgcolor="FFCC00"> 
      <td width="7%" bgcolor="FFCC00">&nbsp; </td>
      <td width="93%"> 
        <div align="left">
            <b>
                <font size="2">GL Account Type List</font>
            </b>
        </div>
      </td>
    </tr>
    
     <beanlib:LoopRows bean="item" list="<%=AccountingConst.CLIENT_DATA_ACCTTYPE_LIST %>">
			<td width="7%" align="center" bgcolor="FFCC00">
			  <beanlib:InputControl type="radio" name="selCbx" value="rowid" onClick="handleAction(this)"/>
              <beanlib:InputControl type="hidden" name="AcctTypeId" value="#item.AcctTypeId" uniqueName="yes"/>					  
			</td>
			<td width="93%">
              <beanlib:InputControl value="#item.Description"/>					
			</td>
	  </tr>
	  </beanlib:LoopRows>
  </table>
  <input type="hidden" name="clientAction">
</form>
<db:Dispose/>
</body>
</html>
