
package com.AnAnInfoBus.bean;

/** aai_post_tags
	ID    INT UNSIGNED(10)
	PID    INT UNSIGNED(10)
	TGID    INT UNSIGNED(10)
	CREATE_DT    INT UNSIGNED(10)
*/
public class Aai_post_tagsbean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id = null;
	private Integer pid = null;
	private Integer tgid = null;
	private Integer create_dt = null;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setPid(Integer pid){
		this.pid=pid;
	}
	public Integer getPid(){
		return pid;
	}
	public void setTgid(Integer tgid){
		this.tgid=tgid;
	}
	public Integer getTgid(){
		return tgid;
	}
	public void setCreate_dt(Integer create_dt){
		this.create_dt=create_dt;
	}
	public Integer getCreate_dt(){
		return create_dt;
	}
}
