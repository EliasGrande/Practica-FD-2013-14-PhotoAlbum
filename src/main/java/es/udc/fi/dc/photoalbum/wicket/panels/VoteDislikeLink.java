package es.udc.fi.dc.photoalbum.wicket.panels;

import org.apache.wicket.model.StringResourceModel;

@SuppressWarnings("serial")
public class VoteDislikeLink extends VoteLikeLink {

    public static final String VOTE_TITLE_RESOURCE_KEY = "vote.button.dislike";

    protected VoteDislikeLink(String id, VotePanel votePanel) {
        super(id, votePanel);
    }

    @Override
    public boolean isVoted() {
        return getVotePanel().isVotedDislike();
    }

    @Override
    protected String getVoteTitle() {
        return new StringResourceModel(VOTE_TITLE_RESOURCE_KEY, this,
                null).getString();
    }

    @Override
    public void vote() {
        getVotePanel().voteDislike();
    }
}
