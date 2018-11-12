package com.glroland.psplay.poc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SonosGateway {
	
	private static final Logger log = Logger.getLogger(SonosGateway.class.getName());

	private static String createAuthHeaderValue()
	{
		StringBuilder authHeaderPre = new StringBuilder();
		authHeaderPre.append(Config.getInstance().getSonosClientId());
		authHeaderPre.append(":");
		authHeaderPre.append(Config.getInstance().getSonosSecret());
		String authHeaderEnc = null;
		try
		{
			authHeaderEnc = new String(Base64.encodeBase64(authHeaderPre.toString().getBytes("UTF-8")), "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			String msg = "Unable to encode authentication header due to unknown error";
			log.log(Level.SEVERE, msg, e);
			throw new RuntimeException (msg, e);
		}
		StringBuilder authHeader = new StringBuilder();
		authHeader.append("Basic ");
		authHeader.append(authHeaderEnc);
		
		return authHeader.toString();
	}
	
	public static SonosToken createToken(String redirectUrl, String code)
	{
		// create auth header
		String authHeader = createAuthHeaderValue();
		log.info("Auth Header = " + authHeader);
				
		// initialize connection to sonos for the token refresh request
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod(Config.getInstance().getSonosUrl());
		NameValuePair[] data = 
			{ 
					new NameValuePair(Constants.SonosCodes.CreateToken.Request.GRANT_TYPE, Constants.SonosCodes.CreateToken.Request.GRANT_TYPE_AUTH_CODE_VALUE), 
					new NameValuePair(Constants.SonosCodes.CreateToken.Request.CODE, code),
					new NameValuePair(Constants.SonosCodes.CreateToken.Request.REDIRECT_URL, redirectUrl)
			};
		method.setRequestBody(data);
		method.addRequestHeader("Authorization", authHeader);
		String responseBody = null;
		try {
			int statusCode = httpClient.executeMethod(method);
			responseBody = method.getResponseBodyAsString();
			if ((statusCode < 200) || (statusCode >= 300)) 
			{
				String msg = "Create Token Request HTTP Post failed with status code: " + statusCode + " Body: " + responseBody;
				log.severe(msg);
				throw new RuntimeException(msg);
			}
			log.info("Refresh Token Response Body: " + responseBody);
		} catch (HttpException e)
		{
			String msg = "Caught HttpException while invoking Create Token service at Sonos";
			log.log(Level.SEVERE, msg, e);
			throw new RuntimeException(msg, e);
		}
		catch (IOException e)
		{
			String msg = "Caught IOException while invoking Create Token service at Sonos";
			log.log(Level.SEVERE, msg, e);
			throw new RuntimeException(msg, e);
		}
		finally {
			method.releaseConnection();
		}

		// parse response body
		log.info("Response Body: " + responseBody);
		Hashtable<String, String> response = parseResponseParams(responseBody);
		String accessToken = response.get(Constants.SonosCodes.CreateToken.Response.ACCESS_TOKEN);
		String refreshToken = response.get(Constants.SonosCodes.CreateToken.Response.REFRESH_TOKEN);
		String expiresInStr = response.get(Constants.SonosCodes.CreateToken.Response.EXPIRES_IN);
		int expiresIn = Integer.parseInt(expiresInStr);
		SonosToken token = new SonosToken(accessToken, refreshToken, expiresIn);
		
		return token;
	}
	
	private static Hashtable<String, String> parseResponseParams(String body)
	{
		// validate input
		if ((body == null) || (body.length() == 0))
		{
			String msg = "Input response body is empty and should not be!";
			log.severe(msg);
			throw new IllegalArgumentException(msg);
		}
		if (body.length() <= 2)
		{
			String msg = "Input response body must be at least 2! " + body.length();
			log.severe(msg);
			throw new IllegalArgumentException(msg);
		}
		body = body.trim();
		if (body.charAt(0) != '{') 
		{
			String msg = "Improperly formatted response string.  { expected at char pos 0: '" + body.charAt(0) + "' " + body;
			log.severe(msg);;
			throw new IllegalArgumentException(msg);
		}
		if (body.charAt(body.length() - 1) != '}') 
		{
			String msg = "Improperly formatted response string.  } expected at char pos " + (body.length() - 1) + ": '" + body.charAt(body.length() - 1) + "' " + body;
			log.severe(msg);;
			throw new IllegalArgumentException(msg);
		}
		body = body.substring(1, body.length() - 1);
		
		Hashtable<String, String> response = new Hashtable<String, String>();
		
		// parse the parameters
		String [] paramList = body.split(",");
		if (paramList != null)
		{
			for (String paramSet : paramList)
			{
				String [] param = paramSet.split(":");
				if (param != null)
				{
					if (param.length != 2)
					{
						String msg = "Malformated input parameter: " + paramSet;
						log.severe(msg);
						throw new RuntimeException(msg);
					}
					
					String key = param[0].replaceAll("\"", "");
					String value = param[1].replaceAll("\"", "");
					response.put(key, value);
				}
			}
		}
		
		return response;
	}
}
