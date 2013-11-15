package es.udc.fi.dc.photoalbum.wicket.panels;

import org.apache.wicket.model.StringResourceModel;

/**
 * Ajax button to vote dislike used by {@link VotePanel}.
 */
@SuppressWarnings("serial")
public class VoteDislikeLink extends VoteLikeLink {

    /**
     * Vote title {@link StringResourceModel} {@code resourceKey}.
     */
    public static final String VOTE_TITLE_RESOURCE_KEY = "vote.button.dislike";

    /**
     * Defines a {@link VoteDislikeLink} object for a
     * {@link VotePanel} panel.
     * 
     * @param id
     *            VoteLikeLink {@code wicket:id}
     * @param votePanel
     *            VotePanel object
     */
    protected VoteDislikeLink(String id, VotePanel votePanel) {
        super(id, votePanel);
    }

    /**
     * @see VotePanel#isVotedDislike()
     */
    @Override
    public boolean isVoted() {
        return getVotePanel().isVotedDislike();
    }

    @Override
    protected String getVoteTitle() {
        return new StringResourceModel(VOTE_TITLE_RESOURCE_KEY, this,
                null).getString();
    }

    /**
     * @see VotePanel#voteDislike()
     */
    @Override
    public void vote() {
        getVotePanel().voteDislike();
    }
}
