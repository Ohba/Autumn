package com.ohba.autumn;

import javax.servlet.annotation.WebFilter;

import com.google.inject.servlet.GuiceFilter;

@WebFilter("/*")
public class DIFilter extends GuiceFilter {
	// this allows our Dependency Injection to be used on 
	// any endpoint that might be called
}
