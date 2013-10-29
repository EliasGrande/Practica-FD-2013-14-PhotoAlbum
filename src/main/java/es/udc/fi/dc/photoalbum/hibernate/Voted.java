package es.udc.fi.dc.photoalbum.hibernate;
//TODO
public class Voted {
	private Integer id;
	private LikeAndDislike likeAndDislike;
	private User user;
	
	public Voted(){
	}
	
	public Voted(LikeAndDislike likeAndDislike, User user){
		this.likeAndDislike = likeAndDislike;
		this.user = user;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LikeAndDislike getLikeAndDislike() {
		return likeAndDislike;
	}
	public void setLikeAndDislike(LikeAndDislike likeAndDislike) {
		this.likeAndDislike = likeAndDislike;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
