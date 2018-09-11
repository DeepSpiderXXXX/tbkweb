package com.limon.base.model;

import java.io.Serializable;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public class SysConfig implements Serializable {
	private static final long serialVersionUID = 9040724408994442379L;
	private Integer id;
	private String config_key;
	private String config_value;
	private String config_type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getConfig_key() {
		return config_key;
	}
	public void setConfig_key(String configKey) {
		config_key = configKey;
	}
	public String getConfig_value() {
		return config_value;
	}
	public void setConfig_value(String configValue) {
		config_value = configValue;
	}
	public String getConfig_type() {
		return config_type;
	}
	public void setConfig_type(String configType) {
		config_type = configType;
	}
}
