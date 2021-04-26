package com.neusoft.ehrss.liaoning.util;

import javax.servlet.http.HttpServletRequest;

public class UrlPathUtils {

	public static String genDomainName(HttpServletRequest request) {
		StringBuffer url = new StringBuffer();
		String scheme = request.getScheme();
		int port = request.getServerPort();
		if (port < 0) {
			port = 80; // Work around java.net.URL bug
		}

		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		return url.toString();
	}

}
