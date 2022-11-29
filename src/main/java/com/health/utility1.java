package com.health;

import javax.servlet.http.HttpServletRequest;

public class utility1 {
	public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
