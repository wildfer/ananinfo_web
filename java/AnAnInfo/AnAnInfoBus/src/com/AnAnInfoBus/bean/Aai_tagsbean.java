
package com.AnAnInfoBus.bean;

/** aai_tags
	ID    INT UNSIGNED(10)
	TAG_NAME    VARCHAR(255)
	TYPE    INT(1)
	ISHOT    INT(10)
	STATUS    INT(11)
	CREATE_DT    INT UNSIGNED(10)
*/
public class Aai_tagsbean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id = null;
	private String tag_name = null;
	private Integer type = null;
	private Integer ishot = null;
	private Integer status = null;
	private Integer create_dt = null;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setTag_name(String tag_name){
		this.tag_name=tag_name;
	}
	public String getTag_name(){
		return tag_name;
	}
	public void setType(Integer type){
		this.type=type;
	}
	public Integer getType(){
		return type;
	}
	public void setIshot(Integer ishot){
		this.ishot=ishot;
	}
	public Integer getIshot(){
		return ishot;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
	public void setCreate_dt(Integer create_dt){
		this.create_dt=create_dt;
	}
	public Integer getCreate_dt(){
		return create_dt;
	}
}
