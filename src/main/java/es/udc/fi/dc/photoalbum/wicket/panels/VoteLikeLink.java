package es.udc.fi.dc.photoalbum.wicket.panels;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

@SuppressWarnings("serial")
public class VoteLikeLink extends AjaxLink<String> {

    public static final String VOTE_TITLE_RESOURCE_KEY = "vote.button.like";
    public static final String UNVOTE_TITLE_RESOURCE_KEY = "vote.button.unvote";
    public static final String IS_VOTED_CLASS = "unvote";
    public static final String IS_NOT_VOTED_CLASS = "vote";

    private VotePanel votePanel;

    protected VoteLikeLink(String id, VotePanel votePanel) {
        super(id);
        this.votePanel = votePanel;
    }
    
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
                return isVoted() ? getUnvoteTitle()
                        : getVoteTitle();
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

    protected boolean isVoted() {
        return votePanel.isVotedLike();
    }

    protected String getUnvoteTitle() {
        return new StringResourceModel(UNVOTE_TITLE_RESOURCE_KEY, this,
                null).getString();
    }

    protected String getVoteTitle() {
        return new StringResourceModel(VOTE_TITLE_RESOURCE_KEY, this,
                null).getString();
    }

    protected void vote() {
        votePanel.voteLike();
    }

    protected void unvote() {
        votePanel.unvote();
    }
}