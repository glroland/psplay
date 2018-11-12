<%@ page import = "java.io.*,java.util.*,com.glroland.psplay.poc.*" %>
<%
	String state = UUID.randomUUID().toString();
	session.setAttribute(Constants.Session.AUTH_REQUEST_ID, state);
%>
<html>
<body>
<form method="get" action="https://api.sonos.com/login/v3/oauth">
<table align="center" border="0">
<tr>
	<td>API Key:</td>
	<td><input type="text" style="width: 300px" name="<%= Constants.SonosCodes.CreateAuthorizationCode.Request.APP_KEY %>" value="<%= Config.getInstance().getSonosClientId() %>" /></td>
</tr>
<tr>
	<td>State:</td>
	<td><input type="text" style="width: 300px" name="<%= Constants.SonosCodes.CreateAuthorizationCode.Request.STATE %>" value="<%= session.getAttribute(Constants.Session.AUTH_REQUEST_ID) %>" /></td>
</tr>
<tr>
	<td>Redirect URL:</td>
	<td><input type="text" style="width: 300px" name="<%= Constants.SonosCodes.CreateAuthorizationCode.Request.REDIRECT_URL %>" id="requestUrl" value="<%= Config.getInstance().getRedirectUrl() %>" /></td>
</tr>
<tr>
	<td colspan="2" align="center">
		<input type="hidden" name="<%= Constants.SonosCodes.CreateAuthorizationCode.Request.RESPONSE_TYPE %>" value="<%= Constants.SonosCodes.CreateAuthorizationCode.Request.RESPONSE_TYPE_CODE %>" />
		<input type="hidden" name="<%= Constants.SonosCodes.CreateAuthorizationCode.Request.SCOPE %>" value="<%= Constants.SonosCodes.CreateAuthorizationCode.Request.SCOPE_VALUE_ALL %>" />
		<input type="submit" value="Sign in with Sonos" />
	</td>
</tr>
</table>
</form>

</body>
</html>
