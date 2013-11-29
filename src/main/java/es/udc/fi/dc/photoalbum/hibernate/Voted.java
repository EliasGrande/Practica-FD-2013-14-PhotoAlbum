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

/**
 * Hibernate entity for storing user vote data, to know if the user
 * has voted like, dislike or didn't vote at all.
 */
@Entity
@Table(name = "VOTED")
@SuppressWarnings("serial")
public class Voted implements Serializable {

    /**
     * LIKE vote for {@link #setUserVote(String)}.
     */
    public static final String LIKE = "LIKE";

    /**
     * DISLIKE vote for {@link #setUserVote(String)}.
     */
    public static final String DISLIKE = "DISLIKE";

    /**
     * @see #getId()
     */
    private Integer id;

    /**
     * @see #getLikeAndDislike()
     */
    private LikeAndDislike likeAndDislike;

    /**
     * @see #getUser()
     */
    private User user;

    /**
     * @see #getUserVote()
     */
    private String userVote;

    /**
     * Empty constructor.
     */
    public Voted() {
    }

    /**
     * Defines a {@link Voted} setting its main properties.
     * 
     * @param likeAndDislike
     *            {@link #getLikeAndDislike() Vote count info}
     * @param user
     *            {@link #getUser() User is voting}
     * @param userVote
     *            {@link #getUserVote() User vote}, it must be
     *            {@link #LIKE} or {@link #DISLIKE}
     */
    public Voted(LikeAndDislike likeAndDislike, User user,
            String userVote) {
        this.likeAndDislike = likeAndDislike;
        this.user = user;
        this.userVote = userVote;
    }

    /**
     * Unique auto-incremental numeric identifier.
     * 
     * @return Unique id
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    /**
     * Setter for {@link #getId() id}.
     * 
     * @param id
     *            New id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Vote count information.
     * 
     * @return Vote count information
     */
    @ManyToOne
    @JoinColumn(name = "LIKE_DISLIKE_ID")
    public LikeAndDislike getLikeAndDislike() {
        return likeAndDislike;
    }

    /**
     * Setter for {@link #getLikeAndDislike() likeAndDislike}.
     * 
     * @param likeAndDislike
     *            New vote count information
     */
    public void setLikeAndDislike(LikeAndDislike likeAndDislike) {
        this.likeAndDislike = likeAndDislike;
    }

    /**
     * User who has voted.
     * 
     * @return User
     */
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }

    /**
     * Setter for {@link #getUser() user}.
     * 
     * @param user
     *            New user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Indicates whether the current user has voted {@link #LIKE LIKE}
     * or {@link #DISLIKE DISLIKE}.
     * 
     * @return User vote
     */
    @Column(name = "USER_VOTE")
    public String getUserVote() {
        return userVote;
    }

    /**
     * Setter for {@link #getUserVote() userVote}.
     * 
     * @param userVote
     *            New user vote
     */
    public void setUserVote(String userVote) {
        this.userVote = userVote;
    }
}
