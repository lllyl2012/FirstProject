package com.hr.utils;

import java.util.UUID;

import javax.servlet.http.HttpSession;

public final class CSRFTokenUtil {
	
    static final String CSRF_PARAM_NAME = "CSRFToken";

	public static final String CSRF_TOKEN_FOR_SESSION_ATTR_NAME = CSRFTokenUtil.class.getName() + ".tokenval";

	/**
	 * 从session中获得token，如果session中没有，则生成一个token
	 * @author volume
	 * @param session
	 * @return
	 */
	public static String getTokenForSession(HttpSession session) {
		String token = null;

		synchronized (session) {
			token = (String) session.getAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
			if (null == token) {
				token = UUID.randomUUID().toString();
				session.setAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME, token);
			}
		}
		return token;
	}

	/**
	 * 重新创建token
	 * @author volume
	 * @param session
	 */
	public static void reCreateTokenForSession(HttpSession session) {
		synchronized (session) {
			session.setAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME, UUID.randomUUID().toString());
		}
	}

	/** 
     * 检查token是否正确 
     * @author volume
     * @param token 令牌 
     */  
    public static boolean checkToken(String token, HttpSession session){  
        if(token==null || "".equals(token)){  
            return false;  
        }  
        String r_token = (String)session.getAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
        if(r_token==null){  
            return false;  
        }  
        if(!token.equals(r_token)){  
            return false;  
        }  
        return true;  
    } 
    
    /** 
     * 注销Token 
     * @author volume
     * @param token 令牌 
     */  
    public static void clearToken(HttpSession session){  
    	session.setAttribute(CSRFTokenUtil.CSRF_TOKEN_FOR_SESSION_ATTR_NAME, null);
    }  
}
