package es.udc.fi.dc.photoalbum.wicket.panels;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

/**
 * Ajax button to vote like used by {@link VotePanel}.
 */
@SuppressWarnings("serial")
public class VoteLikeLink extends AjaxLink<String> {

    /**
     * Vote title {@link StringResourceModel} {@code resourceKey}.
     */
    public static final String VOTE_TITLE_RESOURCE_KEY = "vote.button.like";

    /**
     * Unvote title {@link StringResourceModel} {@code resourceKey}.
     */
    public static final String UNVOTE_TITLE_RESOURCE_KEY = "vote.button.unvote";

    /**
     * Html class of the link if the user has already voted.
     */
    public static final String IS_VOTED_CLASS = "unvote";

    /**
     * Html class of the link if the user has not voted yet.
     */
    public static final String IS_NOT_VOTED_CLASS = "vote";

    /**
     * @see #getVotePanel()
     */
    private VotePanel votePanel;

    /**
     * Defines a {@link VoteLikeLink} object for a {@link VotePanel}
     * panel.
     * 
     * @param id
     *            VoteLikeLink {@code wicket:id}
     * @param votePanel
     *            VotePanel object
     */
    protected VoteLikeLink(String id, VotePanel votePanel) {
        super(id);
        this.votePanel = votePanel;
    }

    /**
     * The {@link VotePanel} which owns this link.
     * 
     * @return {@link #votePanel} object
     */
    protected VotePanel getVotePanel() {
        return votePanel;
    }

    @Override
    public void onInitialize() {
        super.onInitialize();
        add(new AttributeModifier("class", new Model<String>() {
            @Override
            public String getObject() {
                return isVoted() ? IS_VOTED_CLASS
                        : IS_NOT_VOTED_CLASS;
            }
        }));
        add(new AttributeModifier("title", new Model<String>() {
            @Override
            public String getObject() {
                return isVoted() ? getUnvoteTitle() : getVoteTitle();
            }
        }));
    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        if (isVoted()) {
            unvote();
        } else {
            vote();
        }
        target.add(votePanel.getAjaxContainer());
        target.focusComponent(null);
    }

    /**
     * Indicates whether the user has voted
     * {@link VotePanel#isVotedLike() like} or not.
     * 
     * @return {@code true} if the current user has voted
     *         {@link VotePanel#isVotedLike() like} or {@code false}
     *         otherwise.
     */
    protected boolean isVoted() {
        return votePanel.isVotedLike();
    }

    /**
     * Unvote title localized text.
     * 
     * @return Unvote title
     */
    protected String getUnvoteTitle() {
        return new StringResourceModel(UNVOTE_TITLE_RESOURCE_KEY,
                this, null).getString();
    }

    /**
     * Vote title localized text.
     * 
     * @return Vote title
     */
    protected String getVoteTitle() {
        return new StringResourceModel(VOTE_TITLE_RESOURCE_KEY, this,
                null).getString();
    }

    /**
     * @see VotePanel#voteLike()
     */
    protected void vote() {
        votePanel.voteLike();
    }

    /**
     * @see VotePanel#unvote()
     */
    protected void unvote() {
        votePanel.unvote();
    }
}