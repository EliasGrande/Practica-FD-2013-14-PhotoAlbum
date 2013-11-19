package es.udc.fi.dc.photoalbum.wicket.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.Comment;
import es.udc.fi.dc.photoalbum.hibernate.File;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.hibernate.Voted;
import es.udc.fi.dc.photoalbum.spring.CommentService;
import es.udc.fi.dc.photoalbum.spring.VotedService;

/**
 * The model for a {@link Comment}.
 */
@SuppressWarnings("serial")
public class CommentsModel extends
        LoadableDetachableModel<List<Comment>> {

    /**
     * @see CommentService
     */
    @SpringBean
    private CommentService commentService;

    /**
     * @see VotedService
     */
    @SpringBean
    private VotedService votedService;

    /**
     * The id of the {@link User} who comments.
     */
    private int userId;
    /**
     * The commented {@link Album}.
     */
    private Album album;
    /**
     * The commented {@link File}.
     */
    private File file;
    /**
     * The index of the first comment.
     */
    private int index;
    /**
     * The number of comments.
     */
    private int count;
    /**
     * List of the {@link Album} or {@link File} comments. This field
     * allows you to not have to access the database.
     */
    private List<Comment> commentCache;
    /**
     * True if exists more comments.
     */
    private boolean hasMoreComments;
    /**
     * Contains the votes of comments. This field allows you to not
     * have to access the database.
     */
    private Map<Integer, Voted> voteCache;

    /**
     * Constructor for CommentsModel.
     * 
     * @param album
     *            The {@link Album} that have the comment.
     * @param file
     *            The {@link File} that have comment.
     * @param count
     *            The number of comments.
     * @param userId
     *            The id of the {@link User} that comments.
     */
    public CommentsModel(Album album, File file, int count, int userId) {
        this.userId = userId;
        this.album = album;
        this.file = file;
        this.index = 0;
        this.count = count;
        this.commentCache = new ArrayList<Comment>();
        this.hasMoreComments = true;
        this.voteCache = new HashMap<Integer, Voted>();
        Injector.get().inject(this);
    }

    /**
     * Obtain the {@link Voted} for a comment.
     * 
     * @param comment
     *            The comment that want obtaion the {@link Voted}.
    
     * @return Voted Return the {@link Voted} for the {@link Comment}. */
    public Voted getVoted(Comment comment) {
        return voteCache.get(comment.getLikeAndDislike().getId());
    }

    /**
     * Method hasMoreComments.
     * 
    
     * @return boolean True if has more comment otherwise false. */
    public boolean hasMore() {
        return hasMoreComments;
    }

    /**
     * Load the list of {@link Comment}.
     * 
    
     * @return List<Comment> Return the list of the {@link Comment}s
     *         to shown. */
    @Override
    protected List<Comment> load() {
        if (hasMore())
            getMore();
        return commentCache;
    }

    /**
     * Copy in {@link #commentCache} more {@link Comment} if has more.
     */
    private void getMore() {
        // update commentCache
        List<Comment> moreComments = (file == null) ? commentService
                .getCommentsPaging(album, index, count + 1)
                : commentService.getCommentsPaging(file, index,
                        count + 1);
        hasMoreComments = (moreComments.size() > count);
        if (hasMoreComments)
            moreComments.remove(count);
        index += moreComments.size();
        commentCache.addAll(moreComments);
        // update voteCache
        ArrayList<Integer> likeAndDislikeIdList = new ArrayList<Integer>();
        Iterator<Comment> iterComments = moreComments.iterator();
        while (iterComments.hasNext())
            likeAndDislikeIdList.add(iterComments.next()
                    .getLikeAndDislike().getId());
        Iterator<Voted> iterVoted = votedService.getVoted(
                likeAndDislikeIdList, userId).iterator();
        Voted voted;
        while (iterVoted.hasNext()) {
            voted = iterVoted.next();
            voteCache.put(voted.getLikeAndDislike().getId(), voted);
        }
    }
}