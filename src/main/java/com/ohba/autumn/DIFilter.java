package com.ohba.autumn;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import lombok.extern.slf4j.Slf4j;

import com.google.inject.servlet.GuiceFilter;

@Slf4j
@WebFilter("/*")
public class DIFilter extends GuiceFilter {
	// this allows our Dependency Injection to be used on 
	// any endpoint that might be called
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.warn("_D_I_FILTER_ init");
		super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		log.warn("_D_I_FILTER_ doFilter");
		
		super.doFilter(request, response, chain);
		
	}

	@Override
	public void destroy() {
		log.warn("_D_I_FILTER_ destroy");
		super.destroy();
	}
	
}
