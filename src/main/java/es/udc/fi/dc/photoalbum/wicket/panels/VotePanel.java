package es.udc.fi.dc.photoalbum.wicket.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.spring.LikeAndDislikeService;

@SuppressWarnings("serial")
public class VotePanel extends Panel {

	@SpringBean
	private LikeAndDislikeService likeAndDislikeService;
	
	private LikeAndDislike likeAndDislike;

	public VotePanel(String id, LikeAndDislike _likeAndDislike) {
		super(id);
		likeAndDislike = _likeAndDislike;

		int likeCount = likeAndDislike.getLike();
		int dislikeCount = likeAndDislike.getDislike();
		int voteCount = likeCount + dislikeCount;
		int likePercent = 0;
		int dislikePercent = 0;
		if (voteCount > 0) {
			likePercent = Math.round(likeCount * 100 / voteCount);
			dislikePercent = 100 - likePercent;
		}

		add(new Label("likeCount",String.valueOf(likeCount)));
		add(new Label("likePercent",String.valueOf(likePercent)));
		add(new Label("dislikeCount",String.valueOf(dislikeCount)));
		add(new Label("dislikePercent",String.valueOf(dislikePercent)));

		AjaxLink<String> likeLink = new AjaxLink<String>("likeLink") {
			public void onClick(AjaxRequestTarget target) {
				//TODO: vote like on click
			}
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
		    }
			@Override
			public boolean isEnabled() {
				return true;
			}
		};
		add(likeLink);

		AjaxLink<String> dislikeLink = new AjaxLink<String>("dislikeLink") {
			public void onClick(AjaxRequestTarget target) {
				//TODO: vote dislike on click
			}
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
		    }
			@Override
			public boolean isEnabled() {
				return true;
			}
		};
		add(dislikeLink);
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderCSSReference("css/CommentAndVote.css");
	}

}
