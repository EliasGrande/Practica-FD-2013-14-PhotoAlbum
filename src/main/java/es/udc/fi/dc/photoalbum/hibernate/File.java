package es.udc.fi.dc.photoalbum.hibernate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.udc.fi.dc.photoalbum.utils.PrivacyLevel;

@Entity
@Table(name = "ARCHIVO")
@SuppressWarnings("serial")
public class File implements Serializable {

	private Integer id;
	private String name;
	private String privacyLevel;
	private byte[] file;
	private byte[] fileSmall;
	private Album album;
	private Set<FileShareInformation> shareInformation = new HashSet<FileShareInformation>();
	private LikeAndDislike likeAndDislike;
	
	public File() { 
		privacyLevel = PrivacyLevel.INHERIT_FROM_ALBUM;
	}

	public File(Integer id, String name, byte[] file, byte[] fileSmall,
			Album album) {
		this.id = id;
		this.name = name;
		this.privacyLevel = PrivacyLevel.INHERIT_FROM_ALBUM;
		this.file = Arrays.copyOf(file, file.length);
		this.fileSmall = Arrays.copyOf(fileSmall, fileSmall.length);
		this.album = album;
	}

	@OneToMany(mappedBy = "file", fetch = FetchType.LAZY)
	public Set<FileShareInformation> getShareInformation() {
		return shareInformation;
	}

	public void setShareInformation(Set<FileShareInformation> shareInformation) {
		this.shareInformation = shareInformation;
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PRIVACY_LEVEL")
	public String getPrivacyLevel() {
		return privacyLevel;
	}

	public void setPrivacyLevel(String privacyLevel) {
		this.privacyLevel = privacyLevel;
	}

	@Column(name = "FILE")
	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = Arrays.copyOf(file, file.length);
	}

	@Column(name = "FILE_SMALL")
	public byte[] getFileSmall() {
		return fileSmall;
	}

	public void setFileSmall(byte[] fileSmall) {
		this.fileSmall = Arrays.copyOf(fileSmall, fileSmall.length);
	}

	@ManyToOne
	@JoinColumn(name = "ALBUM_ID")
	public Album getAlbum() {
		return this.album;
	}

	public void setAlbum(Album album) {
		this.album = album;
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
