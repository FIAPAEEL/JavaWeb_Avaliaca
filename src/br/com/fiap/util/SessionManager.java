package br.com.fiap.util;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class SessionManager {
	
	private static HttpSession session  = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	private static Map<String, String> requestGet = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	
	public static void addSucessoErro(String m, String bT, String bL){		
		session.setAttribute("mensagem", m);
		session.setAttribute("buttonText", bT);
		session.setAttribute("buttonLink", bL);
	}
	
	public static void createNewSession(){
		session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}
	
	public static String get(String parameter){		
	
		return requestGet.get(parameter);
	}
	
	public static Object getObject(String parameter){		
		
		return session.getAttribute(parameter);
	}
	
	public static void setAttribute(String parameter, Object object){		
		session.setAttribute(parameter, object);
	}
	
	public static void logOff(){
		session.invalidate();
	}
}
