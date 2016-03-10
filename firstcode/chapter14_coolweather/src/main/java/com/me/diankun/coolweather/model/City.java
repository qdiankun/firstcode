package com.me.diankun.coolweather.model;

public class City
{

	private int id;
	private String cityName;
	private String cityCode;
	private String provinceId;

	
	public int getId()
	{
		return id;
	}


	public void setId(int id)
	{
		this.id = id;
	}


	public String getCityName()
	{
		return cityName;
	}


	public void setCityName(String cityName)
	{
		this.cityName = cityName;
	}


	public String getCityCode()
	{
		return cityCode;
	}


	public void setCityCode(String cityCode)
	{
		this.cityCode = cityCode;
	}


	public String getProvinceId()
	{
		return provinceId;
	}


	public void setProvinceId(String provinceId)
	{
		this.provinceId = provinceId;
	}


	@Override
	public String toString()
	{
		return "City [id=" + id + ", cityName=" + cityName + ", cityCode="
				+ cityCode + ", provinceId=" + provinceId + "]";
	}
	
}
