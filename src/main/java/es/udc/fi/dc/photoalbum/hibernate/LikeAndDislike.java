package es.udc.fi.dc.photoalbum.hibernate;

//TODO
public class LikeAndDislike {
	private Integer id;
	private Integer like;
	private Integer dislike;
	
	public LikeAndDislike(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLike() {
		return like;
	}

	public void setLike(Integer like) {
		this.like = like;
	}

	public Integer getDislike() {
		return dislike;
	}

	public void setDislike(Integer dislike) {
		this.dislike = dislike;
	}
}
