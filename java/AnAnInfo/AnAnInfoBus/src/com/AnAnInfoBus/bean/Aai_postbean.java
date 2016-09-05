
package com.AnAnInfoBus.bean;

/** aai_post
	ID    INT UNSIGNED(10)
	UID    INT UNSIGNED(10)
	IMG_COVER    VARCHAR(255)
	CONTENT    VARCHAR(21845)
	COMMENT_COUNT    INT UNSIGNED(10)
	LIKE_COUNT    INT UNSIGNED(10)
	BAD_COUNT    INT UNSIGNED(10)
	COLLECT_COUNT    INT UNSIGNED(10)
	SHARE_COUNT    INT UNSIGNED(10)
	VIEW_COUNT    INT UNSIGNED(10)
	HOT    TINYINT UNSIGNED(1)
	HOT_CON    VARCHAR(255)
	PUBLIC_DT    INT UNSIGNED(10)
	CREATE_DT    INT UNSIGNED(10)
	UPDATE_DT    INT UNSIGNED(10)
	STATUS    TINYINT(1)
	SORT    INT(10)
	STIME    VARCHAR(20)
	ETIME    VARCHAR(20)
	EXAMINE_COUNT    INT(11)
*/
public class Aai_postbean implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id = null;
	private Integer uid = null;
	private String img_cover = null;
	private String content = null;
	private Integer comment_count = null;
	private Integer like_count = null;
	private Integer bad_count = null;
	private Integer collect_count = null;
	private Integer share_count = null;
	private Integer view_count = null;
	private Integer hot = null;
	private String hot_con = null;
	private Integer public_dt = null;
	private Integer create_dt = null;
	private Integer update_dt = null;
	private Integer status = null;
	private Integer sort = null;
	private String stime = null;
	private String etime = null;
	private Integer examine_count = null;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setUid(Integer uid){
		this.uid=uid;
	}
	public Integer getUid(){
		return uid;
	}
	public void setImg_cover(String img_cover){
		this.img_cover=img_cover;
	}
	public String getImg_cover(){
		return img_cover;
	}
	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return content;
	}
	public void setComment_count(Integer comment_count){
		this.comment_count=comment_count;
	}
	public Integer getComment_count(){
		return comment_count;
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
	public void setCollect_count(Integer collect_count){
		this.collect_count=collect_count;
	}
	public Integer getCollect_count(){
		return collect_count;
	}
	public void setShare_count(Integer share_count){
		this.share_count=share_count;
	}
	public Integer getShare_count(){
		return share_count;
	}
	public void setView_count(Integer view_count){
		this.view_count=view_count;
	}
	public Integer getView_count(){
		return view_count;
	}
	public void setHot(Integer hot){
		this.hot=hot;
	}
	public Integer getHot(){
		return hot;
	}
	public void setHot_con(String hot_con){
		this.hot_con=hot_con;
	}
	public String getHot_con(){
		return hot_con;
	}
	public void setPublic_dt(Integer public_dt){
		this.public_dt=public_dt;
	}
	public Integer getPublic_dt(){
		return public_dt;
	}
	public void setCreate_dt(Integer create_dt){
		this.create_dt=create_dt;
	}
	public Integer getCreate_dt(){
		return create_dt;
	}
	public void setUpdate_dt(Integer update_dt){
		this.update_dt=update_dt;
	}
	public Integer getUpdate_dt(){
		return update_dt;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return status;
	}
	public void setSort(Integer sort){
		this.sort=sort;
	}
	public Integer getSort(){
		return sort;
	}
	public void setStime(String stime){
		this.stime=stime;
	}
	public String getStime(){
		return stime;
	}
	public void setEtime(String etime){
		this.etime=etime;
	}
	public String getEtime(){
		return etime;
	}
	public void setExamine_count(Integer examine_count){
		this.examine_count=examine_count;
	}
	public Integer getExamine_count(){
		return examine_count;
	}
}
