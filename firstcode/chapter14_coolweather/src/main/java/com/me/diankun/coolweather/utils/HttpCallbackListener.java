package com.me.diankun.coolweather.utils;

public interface HttpCallbackListener
{
	void onFinish(String response);

	void onError(Exception e);
}
