package com.ohba.autumn;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter("/*")
public class AgahnimOtherFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.warn("_Agahnim_OTHER_FILTER_ init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		log.warn("_Agahnim_OTHER_FILTER_ doFilter");
		
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		log.warn("_Agahnim_OTHER_FILTER_ destroy");
		
	}
	
}
