<%@ page import = "java.io.*,java.util.*,com.glroland.psplay.poc.*" %>
<%
	String refreshToken = "";
	SonosToken token = (SonosToken)session.getAttribute(Constants.Session.TOKEN);
	if (token != null)
	{
		refreshToken = token.getRefreshToken();
	}
%>
<html>
<body onLoad="onLoad()">
<form method="post" action="/psplay-poc/refresh">
<table align="center" border="0">
<tr>
	<td>Token:</td>
	<td><input type="text" style="width: 300px" name="<%= Constants.SonosCodes.RefreshToken.Request.REFRESH_TOKEN %>" value="<%= refreshToken %>" /></td>
</tr>
<tr>
	<td colspan="2" align="center">
		<input type="submit" value="Refresh Token" />
	</td>
</tr>
</table>
</form>

</body>
</html>
