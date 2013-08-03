package com.forgeessentials.util;

public class FunctionHelper {
	public static String parseTime(int timeInSec)
	{
		String uptime = "";
		int weeks = timeInSec / (86400 * 7);
		int remainder = timeInSec % (86400 * 7);
		int days = remainder / 86400;
		remainder = timeInSec % 86400;
		int hours = remainder / 3600;
		remainder = timeInSec % 3600;
		int minutes = remainder / 60;
		int seconds = remainder % 60;

		if (weeks != 0)
		{
			uptime += weeks + " weeks ";
		}

		if (days != 0)
		{
			uptime += (days < 10 ? "0" : "") + days + " days ";
		}

		if (hours != 0)
		{
			uptime += (hours < 10 ? "0" : "") + hours + " h ";
		}

		if (minutes != 0)
		{
			uptime += (minutes < 10 ? "0" : "") + minutes + " min ";
		}

		uptime += (seconds < 10 ? "0" : "") + seconds + " sec.";

		return uptime;
	}

}
