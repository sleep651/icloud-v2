package com.teamsun.core.utils.regx;
/**
 * ubb过滤
 * @author stef_wu
 *
 */
public interface FilterBuilder {

	public abstract void buildFilter();

	public abstract Filter getFilter();

}
