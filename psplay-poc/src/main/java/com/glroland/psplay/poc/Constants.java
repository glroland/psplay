package com.glroland.psplay.poc;

public class Constants 
{
	public static class SonosCodes 
	{
		public static class CreateAuthorizationCode
		{
			public static class Request
			{
				public static final String APP_KEY = "client_id";
				public static final String REDIRECT_URL = "redirect_uri";
				public static final String RESPONSE_TYPE = "response_type";
				public static final String SCOPE = "scope";
				public static final String STATE = "state";
				
				public static final String RESPONSE_TYPE_CODE = "code";
				public static final String SCOPE_VALUE_ALL = "playback-control-all";
			}
			
			public static class Response
			{
				public static final String CODE = "code";
				public static final String STATE = "state";
			}
		}
		
		public static class CreateToken
		{
			public static class Request
			{
				public static final String GRANT_TYPE = "grant_type";
				public static final String CODE = "code";
				public static final String REDIRECT_URL = "redirect_uri";
				
				public static final String GRANT_TYPE_AUTH_CODE_VALUE = "authorization_code";
			}
			
			public static class Response
			{
				public static final String ACCESS_TOKEN = "access_token";
				public static final String TOKEN_TYPE = "token_type";
				public static final String REFRESH_TOKEN = "refresh_token";
				public static final String RESOURCE_OWNER = "resource_owner";
				public static final String EXPIRES_IN = "expires_in";
				public static final String SCOPE = "scope";
				
				public static final String TOKEN_TYPE_BEARER = "bearer";
			}
		}
		
		public static class RefreshToken
		{
			public static class Request
			{
				public static final String REFRESH_TOKEN = "refresh_token";
				public static final String GRANT_TYPE = "grant_type";
				
				public static final String GRANT_TYPE_VALUE = "refresh_token";
			}
			
			public static class Response
			{
				public static final String ACCESS_TOKEN = "access_token";
				public static final String TOKEN_TYPE = "token_type";
				public static final String REFRESH_TOKEN = "refresh_token";
				public static final String EXPIRES_IN = "expires_in";
				public static final String SCOPE = "scope";
				
				public static final String TOKEN_TYPE_BEARER = Constants.SonosCodes.CreateToken.Response.TOKEN_TYPE_BEARER;
			}
		}
		
	}
	
	public static class Session
	{
		public static final String AUTH_REQUEST_ID = "authRequestId";
		public static final String TOKEN = "sonosToken";
	}
	
	public static class Pages
	{
		public static final String HOME = "index.jsp";
	}
	
	public static class Config
	{
		public static final String FILENAME = "psplay.properties";
		
		public static final String KEY_REDIRECT_URL = "redirectUrl";
		public static final String KEY_SONOS_CLIENT_ID = "sonosClientId";
		public static final String KEY_SONOS_SECRET = "sonosSecret";
		public static final String KEY_SONOS_URL = "sonosUrl";
	}
}
