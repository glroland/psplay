package com.glroland.psplay.poc;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class SonosToken implements Serializable
{
	private static final long serialVersionUID = -2145708123632405354L;

	private String accessToken;
	private String refreshToken;
	private Date expireDate;
	
	public SonosToken(String accessToken, String refreshToken, int expiresIn)
	{
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, expiresIn);
		this.expireDate = calendar.getTime();
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	
	public boolean isExpired()
	{
		Date now = new Date();
		return now.getTime() >= expireDate.getTime();
	}
	
}
