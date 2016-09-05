
package com.AnAnInfoCmd.bean;

/** ms_cmd
	CMD_ID    VARCHAR(64)
	DESCRIBER    VARCHAR(1000)
	SERVICE_NAME    VARCHAR(64)
	CMD_CLASS    VARCHAR(64)
	STATUS    INT(11)
	CREATEDATE    INT(10)
	LASTUPDATE_TIME    INT(10)
*/
public class Aai_system_cmdbean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String cmd_id = null;
	private String describer = null;
	private String service_name = null;
	private String cmd_class = null;
	private Integer status = null;
	private Integer createdate = null;
	private Integer lastupdate_time = null;

	public void setCmd_id(String cmd_id){
		this.cmd_id=cmd_id;
	}
	public String getCmd_id(){
		return cmd_id;
	}
	public void setDescriber(String describer){
		this.describer=describer;
	}
	public String getDescriber(){
		return describer;
	}
	public void setService_name(String service_name){
		this.service_name=service_name;
	}
	public String getService_name(){
		return service_name;
	}
	public void setCmd_class(String cmd_class){
		this.cmd_class=cmd_class;
	}
	public String getCmd_class(){
		return cmd_class;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
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
