package es.udc.fi.dc.photoalbum.hibernate;

import java.util.Calendar;

//TODO
public class Comment {
	private Integer id;
	private LikeAndDislike likeAndDislike;
	private User user;
	private Calendar date;
	private String textComment;
	private Album album;
	private File file;
	private long version;
	
	public Comment(){
		
	}
	
	public Comment(LikeAndDislike likeAndDislike, User user,
			String textComment, Album album, File file) {
		this.likeAndDislike = likeAndDislike;
		this.user = user;
		this.textComment = textComment;
		this.album = album;
		this.file = file;
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

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getTextComment() {
		return textComment;
	}

	public void setTextComment(String textComment) {
		this.textComment = textComment;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	
	
	
}
