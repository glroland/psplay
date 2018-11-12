package com.glroland.psplay.poc;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public abstract class BaseHttpServlet extends HttpServlet {

	private static final long serialVersionUID = -7430883122364940333L;

	private static final Logger log = Logger.getLogger(BaseHttpServlet.class.getName());

	public String getParameter(HttpServletRequest req, final String paramName)
	{
		// validate input arguments
		if (req == null)
		{
			String msg = "Input parameter HttpServletRequest is null";
			log.severe(msg);
			throw new IllegalArgumentException(msg);
		}
		if ((paramName == null) || (paramName.length() == 0))
		{
			String msg = "Input parameter paramName is null or empty";
			log.severe(msg);
			throw new IllegalArgumentException(msg);
		}
		
		// get parameter value
		return req.getParameter(paramName);
	}

	public String getRequiredParameter(HttpServletRequest req, final String paramName)
	{
		// get parameter value
		String paramValue = getParameter(req, paramName);
		if (paramValue == null)
		{
			String msg = "Input parameter value (" + paramName + ") was unspecified";
			log.severe(msg);
			throw new RuntimeException(msg);
		}
		if (paramValue.length() == 0)
		{
			String msg = "Input parameter value (" + paramName + ") was specified but is empty while being required";
			log.severe(msg);
			throw new RuntimeException(msg);
		}

		return paramValue;
	}
	
	public String getRequestBody(HttpServletRequest req)
	{
		try
		{
			BufferedReader reader = req.getReader();
			try
			{
				StringBuffer buf = new StringBuffer();
				String line = null;
				while ((line = reader.readLine()) != null)
					buf.append(line);
				
				return buf.toString();
			}
			finally
			{
				reader.close();
			}
		}
		catch (IOException e)
		{
			String msg = "Caught IOException while reading body of request";
			log.throwing(this.getClass().getName(), "getRequestBody(): " + msg, e);
			throw new RuntimeException(msg, e);
		}
	}
}
