package es.udc.fi.dc.photoalbum.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Hibernate entity for storing vote info (count of likes and
 * dislikes).
 */
@Entity
@Table(name = "LIKE_DISLIKE")
@SuppressWarnings("serial")
public class LikeAndDislike implements Serializable {

    /**
     * @see #getId()
     */
    private Integer id;

    /**
     * @see #getId()
     */
    private Integer like;

    /**
     * @see #getId()
     */
    private Integer dislike;

    /**
     * Defines a {@link LikeAndDislike} object setting its counters to
     * zero.
     */
    public LikeAndDislike() {
        this.like = 0;
        this.dislike = 0;
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
     * Number of {@link Voted#LIKE LIKE} votes.
     * 
     * @return Like count
     */
    @Column(name = "LIKES")
    public Integer getLike() {
        return like;
    }

    /**
     * Setter for {@link #getLike() like}.
     * 
     * @param like
     *            New like count
     */
    public void setLike(Integer like) {
        this.like = like;
    }

    /**
     * Number of {@link Voted#DISLIKE DISLIKE} votes.
     * 
     * @return Dislike count
     */
    @Column(name = "DISLIKES")
    public Integer getDislike() {
        return dislike;
    }

    /**
     * Setter for {@link #getDislike() dislike}.
     * 
     * @param dislike
     *            New dislike count
     */
    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }
}
