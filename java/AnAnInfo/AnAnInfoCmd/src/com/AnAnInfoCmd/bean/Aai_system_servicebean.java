
package com.AnAnInfoCmd.bean;

/** ms_service
	SERVICE_NAME    VARCHAR(64)
	SERVICE_IP    VARCHAR(32)
	SERVICE_PORT    INT(10)
	MAX_ACTIVE    INT(10)
	MAX_IDLE    INT(10)
	MIN_IDLE    INT(10)
	TIME_OUT    INT(10)
	CREATEDATE    INT(10)
	LASTUPDATE_TIME    INT(10)
*/
public class Aai_system_servicebean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String service_name = null;
	private String service_ip = null;
	private Integer service_port = null;
	private Integer max_active = null;
	private Integer max_idle = null;
	private Integer min_idle = null;
	private Integer time_out = null;
	private Integer createdate = null;
	private Integer lastupdate_time = null;

	public void setService_name(String service_name){
		this.service_name=service_name;
	}
	public String getService_name(){
		return service_name;
	}
	public void setService_ip(String service_ip){
		this.service_ip=service_ip;
	}
	public String getService_ip(){
		return service_ip;
	}
	public void setService_port(Integer service_port){
		this.service_port=service_port;
	}
	public Integer getService_port(){
		return service_port;
	}
	public void setMax_active(Integer max_active){
		this.max_active=max_active;
	}
	public Integer getMax_active(){
		return max_active;
	}
	public void setMax_idle(Integer max_idle){
		this.max_idle=max_idle;
	}
	public Integer getMax_idle(){
		return max_idle;
	}
	public void setMin_idle(Integer min_idle){
		this.min_idle=min_idle;
	}
	public Integer getMin_idle(){
		return min_idle;
	}
	public void setTime_out(Integer time_out){
		this.time_out=time_out;
	}
	public Integer getTime_out(){
		return time_out;
	}
	public void setCreatedate(Integer createdate){
		this.createdate=createdate;
	}
	public Integer getCreatedate(){
		return createdate;
	}
	public void setLastupdate_time(Integer lastupdate_time){
		this.lastupdate_time=lastupdate_time;
	}
	public Integer getLastupdate_time(){
		return lastupdate_time;
	}
}
