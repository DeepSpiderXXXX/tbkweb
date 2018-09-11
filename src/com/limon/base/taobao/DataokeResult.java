package com.limon.base.taobao;

import java.util.List;

public class DataokeResult {
	private DataokeData data;
	private List<DataokeProduct> result;
	public List<DataokeProduct> getResult() {
		return result;
	}
	public void setResult(List<DataokeProduct> result) {
		this.result = result;
	}
	public DataokeData getData() {
		return data;
	}
	public void setData(DataokeData data) {
		this.data = data;
	}
}
