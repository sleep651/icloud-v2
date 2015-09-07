/**
 * Copyright By 2007 TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.core.domain;


/**
 * Teamsun
 * @author guojf
 * @since 2007-5-25
 * @version 1.0 
 */

public class TreeVO {
    private Long id; //root
    private String text;//text:"树的根"
    private boolean leaf;//判断是否是叶子节点
    private String cls;//文件夹类型
    private String href;//href
    private String hrefTarget;//hrefTarget:"_blank"
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getHrefTarget() {
		return hrefTarget;
	}
	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}

}
