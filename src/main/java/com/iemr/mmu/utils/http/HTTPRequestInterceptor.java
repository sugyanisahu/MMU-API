package com.iemr.mmu.utils.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.iemr.mmu.utils.response.OutputResponse;
import com.iemr.mmu.utils.sessionobject.SessionObject;
import com.iemr.mmu.utils.validator.Validator;

@Component
public class HTTPRequestInterceptor extends HandlerInterceptorAdapter {
	private Validator validator;

	Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	private SessionObject sessionObject;

	@Autowired
	public void setSessionObject(SessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		boolean status = true;
		logger.debug("In preHandle we are Intercepting the Request");
		String authorization = request.getHeader("Authorization");
		logger.debug("RequestURI::" + request.getRequestURI() + " || Authorization ::" + authorization
				+ " || method :: " + request.getMethod());
		if (!request.getMethod().equalsIgnoreCase("OPTIONS")) {
			try {
				String[] requestURIParts = request.getRequestURI().split("/");
				String requestAPI = requestURIParts[requestURIParts.length - 1];
				switch (requestAPI) {
				// case "getBenCaseRecordFromDoctorGeneralOPD":
				// case "doctorData":
				case "startMasterDownload":
				case "checkMastersDownloadProgress":
				case "getVanDetailsForMasterDownload":
				case "userAuthenticate":
				case "superUserAuthenticate":
				case "userAuthenticateNew":
				case "userAuthenticateV1":
				case "forgetPassword":
				case "setForgetPassword":
				case "changePassword":
				case "saveUserSecurityQuesAns":
				case "doAgentLogout":
				case "userLogout":
				case "swagger-ui.html":
				case "ui":
				case "swagger-resources":
				case "api-docs":

					break;
				case "error":
					status = false;
					break;
				default:
					String remoteAddress = request.getHeader("X-FORWARDED-FOR");
					if (remoteAddress == null || remoteAddress.trim().length() == 0) {
						remoteAddress = request.getRemoteAddr();
					}
					validator.checkKeyExists(authorization, remoteAddress);
					break;
				}
			} catch (Exception e) {
				OutputResponse output = new OutputResponse();
				output.setError(e);
				response.setContentType(MediaType.APPLICATION_JSON);

				response.setContentLength(output.toString().length());
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.getOutputStream().print(output.toString());

				status = false;
			}
		}
		return status;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model)
			throws Exception {
		try {
			logger.debug("In postHandle we are Intercepting the Request");
			String authorization = request.getHeader("Authorization");
			logger.debug("RequestURI::" + request.getRequestURI() + " || Authorization ::" + authorization);
			if (authorization != null) {
				sessionObject.updateSessionObject(authorization, sessionObject.getSessionObject(authorization));
			}
		} catch (Exception e) {
			logger.debug("postHandle failed with error " + e.getMessage());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3)
			throws Exception {
		logger.debug("In afterCompletion Request Completed");
	}
}