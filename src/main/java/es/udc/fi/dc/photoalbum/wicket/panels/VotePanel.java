package es.udc.fi.dc.photoalbum.wicket.panels;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.Voted;
import es.udc.fi.dc.photoalbum.spring.LikeAndDislikeService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.spring.VotedService;
import es.udc.fi.dc.photoalbum.wicket.MySession;

@SuppressWarnings("serial")
public class VotePanel extends Panel {

	@SpringBean
	private LikeAndDislikeService likeAndDislikeService;
	@SpringBean
	private VotedService votedService;
	@SpringBean
	private UserService userService;
	
	private LikeAndDislike likeAndDislike;
	private int userId;
	private boolean votedLike;
	private boolean votedDislike;
	
	public VotePanel(String id, LikeAndDislike likeAndDislike) {
		this(id, likeAndDislike, null, true);
	}

	public VotePanel(String id, LikeAndDislike likeAndDislike, Voted voted) {
		this(id, likeAndDislike, voted, false);
	}

	// El getVoted es para decirle que lo recupere de bd en vez de usar el que le pasan,
	// una lógica algo rebuscada pero es para mantener simples los constructores públicos.
	private VotePanel(String id, LikeAndDislike _likeAndDislike, Voted _voted, boolean getVoted) {
		super(id);
		likeAndDislike = _likeAndDislike;
		userId = ((MySession) Session.get()).getuId();
		Voted voted = _voted;
		voted = (getVoted) ? votedService.getVoted(likeAndDislike.getId(),
				userId) : _voted;
		if (voted == null) {
			votedLike = false;
			votedDislike = false;
		} else {
			votedLike = voted.getUserVote().equals(Voted.LIKE);
			votedDislike = !votedLike;
		}

		// ajax data container
		final WebMarkupContainer voteContainer = new WebMarkupContainer("voteContainer");
		voteContainer.setOutputMarkupId(true);
		add(voteContainer);
		
		// info de votos y porcentajes (algo cerdo poner tanto Model pero es para
		// que se actualicen los datos al "refrescar" el contenedor por ajax)

		// like count
		voteContainer.add(new Label("likeCount", new Model<String>() {
			@Override
			public String getObject() {
				return String.valueOf(likeAndDislike.getLike());
			}
		}));

		// like percent
		voteContainer.add(new Label("likePercent", new Model<String>() {
			@Override
			public String getObject() {
				int likes = likeAndDislike.getLike();
				int count = likeAndDislike.getDislike() + likes;
				int percent = (count>0) ? (int)(likes*100/count) : 0;
				return String.valueOf(percent);
			}
		}));

		// dislike count
		voteContainer.add(new Label("dislikeCount", new Model<String>() {
			@Override
			public String getObject() {
				return String.valueOf(likeAndDislike.getDislike());
			}
		}));

		// dislike percent
		voteContainer.add(new Label("dislikePercent", new Model<String>() {
			@Override
			public String getObject() {
				int dislikes = likeAndDislike.getDislike();
				int count = likeAndDislike.getLike() + dislikes;
				int percent = (count>0) ? (int)(dislikes*100/count) : 0;
				return String.valueOf(percent);
			}
		}));

		// like link
		AjaxLink<String> likeLink = new LikeLink("likeLink",voteContainer);
		voteContainer.add(likeLink);

		// dislike link
		AjaxLink<String> dislikeLink = new LikeLink("dislikeLink",voteContainer) {
			@Override
			public boolean isVoted() {
				return votedDislike;
			}
			@Override
			public String getVoteTitle() {
				return getDislikeTitle();
			}
			@Override
			public void vote() {
				likeAndDislike = likeAndDislikeService.voteDislike(likeAndDislike,
						userService.getById(userId));
				votedDislike = true;
				votedLike = false;
			}
		};
		voteContainer.add(dislikeLink);
	}
	
	// Parecen una estupidez esto 3 gets, pero hay que hacerlo así para que los cargue de forma retardada
	// si los recupero en el contructor wicket me dice que como no se ha añadido aún a la página usará la
	// la traducción que le de la gana.
	
	private String getLikeTitle() {
		return new StringResourceModel("vote.button.like", this, null)
				.getString();
	}
	
	private String getDislikeTitle() {
		return new StringResourceModel("vote.button.dislike", this,
				null).getString();
	}
	
	private String getUnvoteTitle() {
		return new StringResourceModel("vote.button.unvote", this, null)
				.getString();
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
        response.render(CssHeaderItem.forReference(new CssResourceReference(
                CommentAndVotePanel.class, "CommentAndVotePanel.css")));
	}
	
	private class LikeLink extends AjaxLink<String> {
		
		private WebMarkupContainer container;

		public LikeLink(String id, WebMarkupContainer container) {
			super(id);
			this.container = container;
		}

		@Override
		public void onInitialize() {
			super.onInitialize();
			add(new AttributeModifier("class", new Model<String>() {
				@Override
				public String getObject() {
					return isVoted() ? "unvote" : "vote";
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
			target.add(container);
			target.focusComponent(null);
		}

		public boolean isVoted() {
			return votedLike;
		}

		public String getVoteTitle() {
			return getLikeTitle();
		}

		public void vote() {
			likeAndDislike = likeAndDislikeService.voteLike(likeAndDislike,
					userService.getById(userId));
			votedDislike = false;
			votedLike = true;
		}

		private void unvote() {
			likeAndDislike = likeAndDislikeService.unvote(likeAndDislike,
					userService.getById(userId));
			votedLike = false;
			votedDislike = false;
		}
	}

}
