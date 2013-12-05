package es.udc.fi.dc.photoalbum.webapp.wicket.panels;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.model.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.model.hibernate.Voted;
import es.udc.fi.dc.photoalbum.model.spring.LikeAndDislikeService;
import es.udc.fi.dc.photoalbum.model.spring.UserService;
import es.udc.fi.dc.photoalbum.model.spring.VotedService;
import es.udc.fi.dc.photoalbum.webapp.wicket.MySession;

/**
 * Reusable panel for showing {@link LikeAndDislike} info and allowing
 * the user to vote.
 */
@SuppressWarnings("serial")
public class VotePanel extends Panel {

    /**
     * Ajax container {@code wicket:id}.
     * 
     * @see #getAjaxContainer()
     */
    public static final String CONTAINER_ID = "voteContainer";

    /**
     * Like count label {@code wicket:id}.
     */
    public static final String LIKE_COUNT_LABEL_ID = "likeCount";

    /**
     * Like percent label {@code wicket:id}.
     */
    public static final String LIKE_PERCENT_LABEL_ID = "likePercent";

    /**
     * Dislike count label {@code wicket:id}.
     */
    public static final String DISLIKE_COUNT_LABEL_ID = "dislikeCount";

    /**
     * Dislike percent label {@code wicket:id}.
     */
    public static final String DISLIKE_PERCENT_LABEL_ID = "dislikePercent";

    /**
     * Vote like ajax link {@code wicket:id}.
     */
    public static final String VOTE_LIKE_LINK_ID = "likeLink";

    /**
     * Vote dislike ajax link {@code wicket:id}.
     */
    public static final String VOTE_DISLIKE_LINK_ID = "dislikeLink";

    /**
     * @see LikeAndDislikeService
     */
    @SpringBean
    private LikeAndDislikeService likeAndDislikeService;

    /**
     * @see VotedService
     */
    @SpringBean
    private VotedService votedService;

    /**
     * @see UserService
     */
    @SpringBean
    private UserService userService;

    /**
     * The {@link LikeAndDislike} object which this panel can show and
     * modify.
     */
    private LikeAndDislike likeAndDislike;

    /**
     * The user id of the current session.
     */
    private int userId;

    /**
     * @see #isVotedLike()
     */
    private boolean votedLike;

    /**
     * @see #isVotedDislike()
     */
    private boolean votedDislike;

    /**
     * @see #getAjaxContainer()
     */
    private WebMarkupContainer voteContainer;

    /**
     * Defines a {@link VotePanel} panel for a {@link LikeAndDislike}
     * object retrieving the current user vote info from the
     * {@link VotedService}.
     * 
     * @param id
     *            VotePanel {@code wicket:id}
     * @param likeAndDislike
     *            LikeAndDislike object
     */
    public VotePanel(String id, LikeAndDislike likeAndDislike) {
        this(id, likeAndDislike, null, true);
    }

    /**
     * Defines a {@link VotePanel} panel for a {@link LikeAndDislike}
     * object retrieving the current user vote info from the given
     * {@code voted} object.
     * 
     * @param id
     *            VotePanel {@code wicket:id}
     * @param likeAndDislike
     *            LikeAndDislike object
     * @param voted
     *            Voted object which links the given
     *            {@code likeAndDislike} to the current user, or
     *            {@code null} if the user didn't vote this
     *            {@code likeAndDislike}
     */
    public VotePanel(String id, LikeAndDislike likeAndDislike,
            Voted voted) {
        this(id, likeAndDislike, voted, false);
    }

    /**
     * Defines a {@link VotePanel} panel for a {@link LikeAndDislike}
     * object.
     * <p>
     * If {@code requestVoted} is {@code true} this constructor works
     * as described on {@link #VotePanel(String, LikeAndDislike)}.
     * <p>
     * If {@code requestVoted} is {@code false} this constructor works
     * as described on
     * {@link #VotePanel(String, LikeAndDislike, Voted)}.
     * <p>
     * The method signature it's a little tricky to keep the public
     * constructors as simple as possible.
     * 
     * @param id
     *            VotePanel {@code wicket:id}
     * @param likeAndDislike
     *            LikeAndDislike object
     * @param voted
     *            Voted object which links the given
     *            {@code likeAndDislike} to the current user, or
     *            {@code null} if the user didn't vote this
     *            {@code likeAndDislike}, this parameter will be
     *            ignored if the flag {@code requestVoted} is
     *            {@code true}
     * @param requestVoted
     *            {@code true} to retrieve the user vote info from the
     *            {@link VotedService} or {@code false} to use the
     *            given {@code voted} object
     */
    private VotePanel(String id, LikeAndDislike likeAndDislike,
            Voted voted, boolean requestVoted) {

        super(id);

        // vote count info
        this.likeAndDislike = likeAndDislike;

        // user vote info
        userId = ((MySession) Session.get()).getuId();
        Voted effectiveVoted = voted;
        effectiveVoted = (requestVoted) ? votedService.getVoted(
                likeAndDislike.getId(), userId) : voted;

        if (effectiveVoted == null) {
            votedLike = false;
            votedDislike = false;
        } else {
            votedLike = effectiveVoted.getUserVote().equals(
                    Voted.LIKE);
            votedDislike = !votedLike;
        }

        // build components
        makeAjaxContainer();
        makeLikeLabels();
        makeDislikeLabels();
        makeVoteLinks();
    }

    /**
     * Initializes {@link #voteContainer} and adds it to the page.
     */
    private void makeAjaxContainer() {
        voteContainer = new WebMarkupContainer(CONTAINER_ID);
        voteContainer.setOutputMarkupId(true);
        add(voteContainer);
    }

    /**
     * Initializes the like labels and adds them to the
     * {@link #voteContainer}.
     */
    private void makeLikeLabels() {

        // like count
        voteContainer.add(new Label(LIKE_COUNT_LABEL_ID,
                new Model<String>() {
                    @Override
                    public String getObject() {
                        return String.valueOf(likeAndDislike
                                .getLike());
                    }
                }));

        // like percent
        voteContainer.add(new Label(LIKE_PERCENT_LABEL_ID,
                new Model<String>() {
                    @Override
                    public String getObject() {
                        int likes = likeAndDislike.getLike();
                        int count = likeAndDislike.getDislike()
                                + likes;
                        int percent = (count > 0) ? (int) (likes * 100 / count)
                                : 0;
                        return String.valueOf(percent);
                    }
                }));
    }

    /**
     * Initializes the dislike labels and adds them to the
     * {@link #voteContainer}.
     */
    private void makeDislikeLabels() {

        // dislike count
        voteContainer.add(new Label(DISLIKE_COUNT_LABEL_ID,
                new Model<String>() {
                    @Override
                    public String getObject() {
                        return String.valueOf(likeAndDislike
                                .getDislike());
                    }
                }));

        // dislike percent
        voteContainer.add(new Label(DISLIKE_PERCENT_LABEL_ID,
                new Model<String>() {
                    @Override
                    public String getObject() {
                        int dislikes = likeAndDislike.getDislike();
                        int count = likeAndDislike.getLike()
                                + dislikes;
                        int percent = (count > 0) ? (int) (dislikes * 100 / count)
                                : 0;
                        return String.valueOf(percent);
                    }
                }));
    }

    /**
     * Initializes the like and dislike links and adds them to the
     * {@link #voteContainer}.
     */
    private void makeVoteLinks() {
        AjaxLink<String> likeLink = new VoteLikeLink(
                VOTE_LIKE_LINK_ID, this);
        voteContainer.add(likeLink);

        AjaxLink<String> dislikeLink = new VoteDislikeLink(
                VOTE_DISLIKE_LINK_ID, this);
        voteContainer.add(dislikeLink);
    }

    /**
     * Votes like.
     */
    public void voteLike() {
        likeAndDislike = likeAndDislikeService.voteLike(
                likeAndDislike, userService.getById(userId));
        votedDislike = false;
        votedLike = true;
    }

    /**
     * Votes dislike.
     */
    public void voteDislike() {
        likeAndDislike = likeAndDislikeService.voteDislike(
                likeAndDislike, userService.getById(userId));
        votedDislike = true;
        votedLike = false;
    }

    /**
     * Annuls the vote.
     */
    public void unvote() {
        likeAndDislike = likeAndDislikeService.unvote(likeAndDislike,
                userService.getById(userId));
        votedLike = false;
        votedDislike = false;
    }

    /**
     * Indicates whether the current user has voted {@link Voted#LIKE
     * like}.
     * 
     * @return {@link #votedLike} value
     */
    public boolean isVotedLike() {
        return votedLike;
    }

    /**
     * Indicates whether the current user has voted
     * {@link Voted#DISLIKE dislike}.
     * 
     * @return {@link #votedDislike} value
     */
    public boolean isVotedDislike() {
        return votedDislike;
    }

    /**
     * Container for the vote info and vote buttons refreshable by
     * ajax using the vote buttons.
     * 
     * @return {@link #voteContainer} object
     */
    public WebMarkupContainer getAjaxContainer() {
        return voteContainer;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem
                .forReference(new CssResourceReference(
                        CommentAndVotePanel.class,
                        "CommentAndVotePanel.css")));
    }
}
