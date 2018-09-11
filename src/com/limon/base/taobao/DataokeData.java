package com.limon.base.taobao;

import java.util.List;

public class DataokeData {
	private String api_type;
	private String update_time;
	private Integer total_num;
	private String api_content;
	private List<DataokeProduct> result;
	public String getApi_type() {
		return api_type;
	}
	public void setApi_type(String api_type) {
		this.api_type = api_type;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public Integer getTotal_num() {
		return total_num;
	}
	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}
	public String getApi_content() {
		return api_content;
	}
	public void setApi_content(String api_content) {
		this.api_content = api_content;
	}
	public List<DataokeProduct> getResult() {
		return result;
	}
	public void setResult(List<DataokeProduct> result) {
		this.result = result;
	}
}
