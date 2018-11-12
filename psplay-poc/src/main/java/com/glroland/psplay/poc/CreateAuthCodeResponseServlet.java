package com.glroland.psplay.poc;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateAuthCodeResponseServlet extends BaseHttpServlet {

	private static final long serialVersionUID = 5085500666788768010L;
	
	private static final Logger log = Logger.getLogger(CreateAuthCodeResponseServlet.class.getName());
	
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		// get input params
		String code = getRequiredParameter(req, Constants.SonosCodes.CreateAuthorizationCode.Response.CODE);
		String state = getRequiredParameter(req, Constants.SonosCodes.CreateAuthorizationCode.Response.STATE);
		String body = getRequestBody(req);
		log.info("Authorization Token Recieved.  Code=" + code + " State=" + state + " Body=" + body + " FormMethod=" + req.getMethod());
				
		// validate that token is correctly associated with the prior request
		String expectedState = (String)req.getSession().getAttribute(Constants.Session.AUTH_REQUEST_ID);
		if (expectedState == null)
		{
			String msg = Constants.Session.AUTH_REQUEST_ID + " was empty but a value is required to continue";
			log.severe(msg);
			throw new RuntimeException(msg);
		}
		if (!state.equals(expectedState))
		{
			String msg = Constants.Session.AUTH_REQUEST_ID + " does not match the expected state, indicating a security risk.  Aborting...  Expected=" + expectedState + " Received=" + state;
			log.severe(msg);
			throw new RuntimeException(msg);
		}
		req.getSession().setAttribute(Constants.Session.AUTH_REQUEST_ID, null);
		
		// request a token based on the one itme use code
		SonosToken token = SonosGateway.createToken(Config.getInstance().getRedirectUrl(), code);
		req.getSession().setAttribute(Constants.Session.TOKEN, token);
		
		res.sendRedirect(Constants.Pages.HOME);
	}
}
