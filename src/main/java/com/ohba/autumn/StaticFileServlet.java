package com.ohba.autumn;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import net.balusc.webapp.FileServlet;

@Slf4j
@WebServlet(urlPatterns={"/*"},initParams= {@WebInitParam(name="basePath",value="public")})
public class StaticFileServlet extends FileServlet {
	
	/*
	 * I'm extending this FileServlet so that I don't change it's code thus complying with the GNU Lesser GPL license
	 * 
	 * FileServlet is from http://balusc.blogspot.com/2009/02/fileservlet-supporting-resume-and.html
	 * 
	 * Also, for Servlet 3.0 Spec with heavy use of annotations we found that we need to do this:
	 * From: http://www.java.net/node/700651
	 * agksmehx: "I am facing another problem. I have a @WebServlet("") and a @WebFilter(urlPatterns = {"", "/", "/*"}, asyncSupported = true)
	 * 				But the filter is not being invoked for the servlet :-("
	 * swchan2: "Look like there may be an issue here. As a workaround, one can associate a filter with a servlet by name as follows:
	 * 				@WebFilter(servletNames={"myservlet"}) @WebServlet(name="myservlet", urlPatterns={""})"
	 * 
	 * you will notice that pattern followed between this servlet and the DIFilter.java
	 */
	
	@Override
	public void init() throws ServletException {
		log.warn("in init, basePath is: {} :: {}", getInitParameter("basePath"), getServletContext().getRealPath(getInitParameter("basePath")) );
		super.init();
	}
	
	@Override
	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.warn(request.getPathInfo());
		super.doHead(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.warn(request.getPathInfo());
		super.doGet(request, response);
	}
	
}
