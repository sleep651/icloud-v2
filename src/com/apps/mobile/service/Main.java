package com.apps.mobile.service;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import com.apps.mobile.domain.LhxcZiYou;

class Main {

	/**
	 * @param args
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws IntrospectionException
	 */
	public static void main(String[] args) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			IntrospectionException {
		LhxcZiYou lhxcZiYou = new LhxcZiYou();
		BeanInfo beans = java.beans.Introspector.getBeanInfo(LhxcZiYou.class);
		PropertyDescriptor[] propertyDescriptors = beans
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			String propertyName = (propertyDescriptors[i].getDisplayName())
					.toUpperCase();
			if (propertyName.equals("BASE_FEN")) {
				propertyDescriptors[i].getWriteMethod()
						.invoke(lhxcZiYou, 0);
			}

		}
		System.out.println();
	}

}
