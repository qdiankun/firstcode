package com.me.diankun.coolweather.model;

public class Country
{

	private int id;
	private String countryName;
	private String countryCode;
	private String cityId;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getCountryName()
	{
		return countryName;
	}

	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}

	public String getCountryCode()
	{
		return countryCode;
	}

	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}

	public String getCityId()
	{
		return cityId;
	}

	public void setCityId(String cityId)
	{
		this.cityId = cityId;
	}

	@Override
	public String toString()
	{
		return "Country [id=" + id + ", countryName=" + countryName
				+ ", countryCode=" + countryCode + ", cityId=" + cityId + "]";
	}

}
