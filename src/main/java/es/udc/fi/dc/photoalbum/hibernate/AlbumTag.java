package es.udc.fi.dc.photoalbum.hibernate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ALBUM_TAG")
@SuppressWarnings("serial")
public class AlbumTag implements Serializable {

	private Integer id;
	private Album album;
	private String tag;

	public AlbumTag() {

	}

	public AlbumTag(Album album, String tag) {
		this.album = album;
		this.tag = tag;
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

	@ManyToOne
	@JoinColumn(name = "ALBUM_ID")
	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	@Column(name = "TAG")
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
