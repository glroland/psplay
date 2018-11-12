<%@ page import = "java.io.*,java.util.*,com.glroland.psplay.poc.*,java.text.*" %>
<%
	String expirationDateStr = "";
	String accessToken = "";
	String refreshToken = "";

	SonosToken token = (SonosToken)session.getAttribute(Constants.Session.TOKEN);
	if (token != null)
	{
		DateFormat dateFormat = new SimpleDateFormat("M-d-yyyy h:mm:ss a");  
		expirationDateStr = dateFormat.format(token.getExpireDate());  
		
		accessToken = token.getAccessToken();
		refreshToken = token.getRefreshToken();
	}
%>
<html>
<body>

<table border="0" align="center">
<%
	if(token != null)
	{
%>
<tr>
	<td>Access Token:</td>
	<td><%= accessToken %></td>
</tr>
<tr>
	<td>Refresh Token:</td>
	<td><%= refreshToken %></td>
</tr>
<tr>
	<td>Expires In:</td>
	<td><%= expirationDateStr %></td>
</tr>
<%
	}
	else
	{
%>
<tr>
	<td colspan="2" align="center">
		Unauthenticated
	</td>
</tr>
<%
	}
%>
<tr>
	<td colspan="2" align="center">
		<br/>
	</td>
</tr>
<tr>
	<td colspan="2" align="center">
		<a href="auth_code.jsp">Enroll</a>
	</td>
</tr>
<tr>
	<td colspan="2" align="center">
		<a href="refresh_token.jsp">Refresh Token</a>
	</td>
</tr>
</table>

</body>
</html>