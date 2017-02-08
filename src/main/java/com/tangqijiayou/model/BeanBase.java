package com.tangqijiayou.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class BeanBase implements Serializable{
	private static final long serialVersionUID = 1L;

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}