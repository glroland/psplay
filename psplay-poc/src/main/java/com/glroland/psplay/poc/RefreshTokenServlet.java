package com.glroland.psplay.poc;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class RefreshTokenServlet extends BaseHttpServlet {

	private static final long serialVersionUID = -5637300449294739450L;
	
	private static final Logger log = Logger.getLogger(RefreshTokenServlet.class.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// purge authentication token first
		req.getSession().setAttribute(Constants.Session.TOKEN, null);

		// get parameters
		String inputRefreshToken = getRequiredParameter(req, Constants.SonosCodes.RefreshToken.Request.REFRESH_TOKEN);

		// initialize connection to sonos for the token refresh request
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod("https://api.sonos.com/login/v3/oauth/access");
		NameValuePair[] data = 
			{ 
					new NameValuePair(Constants.SonosCodes.RefreshToken.Request.GRANT_TYPE, Constants.SonosCodes.RefreshToken.Request.GRANT_TYPE_VALUE), 
					new NameValuePair(Constants.SonosCodes.RefreshToken.Request.REFRESH_TOKEN, inputRefreshToken) 
			};
		method.setRequestBody(data);
		String responseBody = null;
		try {
			int statusCode = httpClient.executeMethod(method);
			if ((statusCode < 200) || (statusCode >= 300)) 
			{
				String msg = "Refresh Token Request HTTP Post failed with status code: " + statusCode;
				log.severe(msg);
				throw new RuntimeException(msg);
			}
			responseBody = method.getResponseBodyAsString();
			log.info("Refresh Token Response Body: " + responseBody);
		} finally {
			method.releaseConnection();
		}

		// send request to sonos to refresh token
		String accessToken = "";
		String outputRefreshToken = "";
		int expiresIn = 0;

		// create new token
		SonosToken token = new SonosToken(accessToken, outputRefreshToken, expiresIn);
		req.getSession().setAttribute(Constants.Session.TOKEN, token);

		res.sendRedirect(Constants.Pages.HOME);
	}
}
