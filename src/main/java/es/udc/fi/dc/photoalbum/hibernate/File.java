package es.udc.fi.dc.photoalbum.hibernate;

import javax.persistence.*;

import java.io.Serializable;

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

	public File() {
	}

	public File(Integer id, String name, byte[] file, byte[] fileSmall,
			Album album) {
		this.id = id;
		this.name = name;
		this.privacyLevel = "PRIVATE";
		this.file = file;
		this.fileSmall = fileSmall;
		this.album = album;
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
		this.file = file;
	}

	@Column(name = "FILE_SMALL")
	public byte[] getFileSmall() {
		return fileSmall;
	}

	public void setFileSmall(byte[] fileSmall) {
		this.fileSmall = fileSmall;
	}

	@ManyToOne
	@JoinColumn(name = "ALBUM_ID")
	public Album getAlbum() {
		return this.album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}
}
