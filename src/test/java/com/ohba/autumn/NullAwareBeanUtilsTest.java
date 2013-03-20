package com.ohba.autumn;

import com.ohba.autumn.utils.NullAwareBeanUtils;
import java.lang.reflect.InvocationTargetException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class NullAwareBeanUtilsTest {
	
	@Test
	public void shouldNotCopyNull() throws IllegalAccessException, InvocationTargetException {
		
		TestBean one = new TestBean(), two = new TestBean();
		one.setFirstname("Danny");
		two.setLastname("Langford");

		NullAwareBeanUtils.INSTANCE.copyProperties(two, one);
		
		assertEquals("Danny", two.getFirstname());
		assertEquals("Langford", two.getLastname());
		
	}

}
