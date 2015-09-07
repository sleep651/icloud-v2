package com.teamsun.core.utils.regx;
/**
 * ubb过滤
 * @author  stef_wu
 *
 */
public class FilterDirector {

	private FilterBuilder builder;

	public FilterDirector(FilterBuilder builder) {
		this.builder = builder;
	}

	public void construct() {
		builder.buildFilter();
	}

}
