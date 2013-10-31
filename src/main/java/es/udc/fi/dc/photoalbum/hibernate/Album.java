package es.udc.fi.dc.photoalbum.hibernate;

import javax.persistence.*;

import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ALBUM")
@SuppressWarnings("serial")
public class Album implements Serializable {

	private Integer id;
	private String name;
	private User user;
	private String privacyLevel;
	private Set<File> files = new HashSet<File>();
	private Set<AlbumShareInformation> shareInformation = new HashSet<AlbumShareInformation>();
	private LikeAndDislike likeAndDislike;

	public Album() {
		privacyLevel = PrivacyLevel.PRIVATE;
	}

	public Album(Integer id, String name, User user, Set<File> files,
			Set<AlbumShareInformation> shareInformation, String privacyLevel) {
		this.id = id;
		this.name = name;
		this.user = user;
		this.privacyLevel = privacyLevel;
		this.files = files;
		this.shareInformation = shareInformation;
	}

	@OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	@OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
	public Set<AlbumShareInformation> getShareInformation() {
		return shareInformation;
	}

	public void setShareInformation(Set<AlbumShareInformation> shareInformation) {
		this.shareInformation = shareInformation;
	}

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "PRIVACY_LEVEL")
	public String getPrivacyLevel() {
		return privacyLevel;
	}

	public void setPrivacyLevel(String privacyLevel) {
		this.privacyLevel = privacyLevel;
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

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@OneToOne
	@JoinColumn(name = "LIKE_DISLIKE_ID")
	public LikeAndDislike getLikeAndDislike() {
		return likeAndDislike;
	}

	public void setLikeAndDislike(LikeAndDislike likeAndDislike) {
		this.likeAndDislike = likeAndDislike;
	}
}
