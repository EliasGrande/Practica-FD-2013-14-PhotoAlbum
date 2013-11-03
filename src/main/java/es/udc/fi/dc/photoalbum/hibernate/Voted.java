package es.udc.fi.dc.photoalbum.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "VOTED")
@SuppressWarnings("serial")
public class Voted implements Serializable{

	public static final String LIKE = "LIKE";
	public static final String DISLIKE = "DISLIKE";
	
	private Integer id;
	private LikeAndDislike likeAndDislike;
	private User user;
	private String userVote;
	
	public Voted(){
	}
	
	public Voted(LikeAndDislike likeAndDislike, User user, String userVote){
		this.likeAndDislike = likeAndDislike;
		this.user = user;
		this.userVote = userVote;
	}
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "LIKE_DISLIKE_ID")
	public LikeAndDislike getLikeAndDislike() {
		return likeAndDislike;
	}
	public void setLikeAndDislike(LikeAndDislike likeAndDislike) {
		this.likeAndDislike = likeAndDislike;
	}
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	/*Guarda si el usuario user, ha votado a like o dislike*/
	@Column(name = "USER_VOTE")
	public String getUserVote() {
		return userVote;
	}

	public void setUserVote(String userVote) {
		this.userVote = userVote;
	}
}
