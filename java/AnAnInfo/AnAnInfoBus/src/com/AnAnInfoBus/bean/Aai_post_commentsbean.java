
package com.AnAnInfoBus.bean;

/** aai_post_comments
	ID    INT UNSIGNED(10)
	PID    INT UNSIGNED(10)
	UID    INT UNSIGNED(10)
	CONTENT    VARCHAR(21845)
	LIKE_COUNT    INT UNSIGNED(10)
	BAD_COUNT    INT UNSIGNED(10)
	CID    INT UNSIGNED(10)
	CREATE_DT    INT UNSIGNED(10)
	C_IP    VARCHAR(20)
	STATUS    TINYINT(3)
*/
public class Aai_post_commentsbean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id = null;
	private Integer pid = null;
	private Integer uid = null;
	private String content = null;
	private Integer like_count = null;
	private Integer bad_count = null;
	private Integer cid = null;
	private Integer create_dt = null;
	private String c_ip = null;
	private Integer status = null;

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
	public void setUid(Integer uid){
		this.uid=uid;
	}
	public Integer getUid(){
		return uid;
	}
	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return content;
	}
	public void setLike_count(Integer like_count){
		this.like_count=like_count;
	}
	public Integer getLike_count(){
		return like_count;
	}
	public void setBad_count(Integer bad_count){
		this.bad_count=bad_count;
	}
	public Integer getBad_count(){
		return bad_count;
	}
	public void setCid(Integer cid){
		this.cid=cid;
	}
	public Integer getCid(){
		return cid;
	}
	public void setCreate_dt(Integer create_dt){
		this.create_dt=create_dt;
	}
	public Integer getCreate_dt(){
		return create_dt;
	}
	public void setC_ip(String c_ip){
		this.c_ip=c_ip;
	}
	public String getC_ip(){
		return c_ip;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
}
