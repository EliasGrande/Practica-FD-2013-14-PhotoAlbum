package es.udc.fi.dc.photoalbum.hibernate;

import javax.persistence.*;
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
	private Set<ShareInformation> shareInformation = new HashSet<ShareInformation>();

	public Album() {
		privacyLevel = "SHAREABLE";
	}

	public Album(Integer id, String name, User user, Set<File> files,
			Set<ShareInformation> shareInformation, String privacyLevel) {
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
	public Set<ShareInformation> getShareInformation() {
		return shareInformation;
	}

	public void setShareInformation(Set<ShareInformation> shareInformation) {
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
}
