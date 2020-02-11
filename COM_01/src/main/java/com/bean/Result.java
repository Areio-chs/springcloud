package com.bean;

/**
 * 通用的返回的类
 * 
 * @author lfy
 * 
 */
public class Result<Data> {
	//状态码   100-成功    200-失败
	private int code;
	//提示信息
	private String msg;

	private Data data;

	public static Result success(){
		Result result = new Result();
		result.setCode(100);
		result.setMsg("处理成功！");
		return result;
	}
	
	public static Result fail(){
		Result result = new Result();
		result.setCode(200);
		result.setMsg("处理失败！");
		return result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
