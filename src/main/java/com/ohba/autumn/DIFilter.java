package com.ohba.autumn;

import javax.servlet.annotation.WebFilter;

import com.google.inject.servlet.GuiceFilter;

/*
 * by filtering "/*" ALL traffic will come through this filter
 * allowing your rest endpoints to live on any url pattern you choose
 */
@WebFilter("/*")
public class DIFilter extends GuiceFilter {
	
}
