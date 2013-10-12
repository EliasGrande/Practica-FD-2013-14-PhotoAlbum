package es.udc.fi.dc.photoalbum.hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USUARIO")
@SuppressWarnings("serial")
public class User implements Serializable {

	private Integer id;
	private String email;
	private String password;
	private Set<Album> albums = new HashSet<Album>();
	private Set<AlbumShareInformation> shareInformation = new HashSet<AlbumShareInformation>();

	public User() {
	}

	public User(Integer id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	public Set<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(Set<Album> albums) {
		this.albums = albums;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	public Set<AlbumShareInformation> getShareInformation() {
		return shareInformation;
	}

	public void setShareInformation(Set<AlbumShareInformation> shareInformation) {
		this.shareInformation = shareInformation;
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

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
