package com.jt.common.vo;
/**通过此对象对控制层数据进行封装 
 * 1)正常数据
 * 2)异常数据
 * */
public class JsonResult {
	private static final int SUCCESS=1;
	private static final int ERROR=0;
    /**状态码*/
	private int state=SUCCESS;
	/**状态信息*/
	private String message;
	/**具体数据*/
	private Object data;
	
	public JsonResult() {
		message="Action OK";
	}
	public JsonResult(Object data){
		this.data=data;
	}
	public JsonResult(String message){
		this.message=message;
	}
	public JsonResult(Throwable exp){
		this.state=ERROR;
		this.message=exp.getMessage();
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
