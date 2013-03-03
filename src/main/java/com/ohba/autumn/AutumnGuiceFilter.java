package com.ohba.autumn;

import javax.servlet.annotation.WebFilter;

import com.google.inject.servlet.GuiceFilter;

@WebFilter("/*")
public class AutumnGuiceFilter extends GuiceFilter {
	
}
