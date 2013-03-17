package com.ohba.autumn;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

import lombok.val;

import org.junit.Test;

import com.ohba.autumn.utils.NullAwareBeanUtils;

public class NullAwareBeanUtilsTest {
	
	@Test
	public void shouldNotCopyNull() throws IllegalAccessException, InvocationTargetException {
		
		val one = new TestBean(), two = new TestBean();
		one.setFirstname("Danny");
		two.setLastname("Langford");

		NullAwareBeanUtils.INSTANCE.copyProperties(two, one);
		
		assertEquals("Danny", two.getFirstname());
		assertEquals("Langford", two.getLastname());
		
	}

}
