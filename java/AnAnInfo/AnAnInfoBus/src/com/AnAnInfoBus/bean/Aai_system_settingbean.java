
package com.AnAnInfoBus.bean;

/** ms_system_setting
	SKEY    VARCHAR(50)
	SVALUE    VARCHAR(21845)
	SNAME    VARCHAR(100)
*/
public class Aai_system_settingbean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String skey = null;
	private String svalue = null;
	private String sname = null;

	public void setSkey(String skey){
		this.skey=skey;
	}
	public String getSkey(){
		return skey;
	}
	public void setSvalue(String svalue){
		this.svalue=svalue;
	}
	public String getSvalue(){
		return svalue;
	}
	public void setSname(String sname){
		this.sname=sname;
	}
	public String getSname(){
		return sname;
	}
}
