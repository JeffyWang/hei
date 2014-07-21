package com.kzone.filter;

import com.kzone.bean.User;
import com.kzone.bo.Response;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.UserService;
import com.kzone.util.MD5Util;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeffy on 14-4-30.
 */
public class AccountFilter implements Filter {
    private Logger log = Logger.getLogger(AccountFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Response result = new Response();
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String uri = request.getRequestURI();
//        String path = request.getContextPath();
        log.debug("request uri is : [" + uri + "]");
//        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        HttpSession session = request.getSession();
        String loginName = (String) session.getAttribute(ParamsConstants.PARAM_USER_USERNAME);

        if(uri.contains("account") || loginName != null || uri.contains("index") || uri.equals("/")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            log.debug(ErrorCode.PERMISSION_ERR_MSG);
            response.sendError(HttpStatus.SC_UNAUTHORIZED, ErrorCode.PERMISSION_ERR_MSG);
//            response.sendRedirect(basePath + "index.html");
            return;
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
