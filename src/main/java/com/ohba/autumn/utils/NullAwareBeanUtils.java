package com.ohba.autumn.utils;

import java.lang.reflect.InvocationTargetException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.beanutils.BeanUtilsBean;

@Slf4j
public class NullAwareBeanUtils extends BeanUtilsBean{
	
	public static NullAwareBeanUtils INSTANCE = new NullAwareBeanUtils();
	
	@Override
	public void copyProperties(Object dest, Object orig)
	        throws IllegalAccessException, InvocationTargetException {
		try {
			super.copyProperties(dest, orig);
		} catch(IllegalArgumentException e) {
			log.warn("potential problem copying properties: {}",e.getMessage());
		}
	}
	
    @Override
    public void copyProperty(Object dest, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
		if (value == null) {
			return;
		} else {
			super.copyProperty(dest, name, value);
		}
    }
}