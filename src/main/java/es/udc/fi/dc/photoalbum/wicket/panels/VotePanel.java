package es.udc.fi.dc.photoalbum.wicket.panels;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import es.udc.fi.dc.photoalbum.hibernate.LikeAndDislike;
import es.udc.fi.dc.photoalbum.hibernate.User;
import es.udc.fi.dc.photoalbum.spring.LikeAndDislikeService;
import es.udc.fi.dc.photoalbum.spring.UserService;
import es.udc.fi.dc.photoalbum.wicket.MySession;

@SuppressWarnings("serial")
public class VotePanel extends Panel {

	@SpringBean
	private LikeAndDislikeService likeAndDislikeService;
	@SpringBean
	private UserService userService;
	
	private LikeAndDislike likeAndDislike;
	private User user;
	private boolean votedLike;
	private boolean votedDislike;

	public VotePanel(String id, LikeAndDislike _likeAndDislike) {
		super(id);
		likeAndDislike = _likeAndDislike;
		user = userService.getById(((MySession) Session.get()).getuId());
		votedLike = false;
		votedDislike = false;

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
		AjaxLink<String> likeLink = new AjaxLink<String>("likeLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				try {
					// TODO: Quitar este try-catch cuando arreglen el servicio.
					// Peta al votar algo que ya votaste, me parece bien lo de
					// dejarle cambiar de opinión al usuario, vi algo por el
					// estilo en el servicio, pero que funcione, la interfaz ya
					// tiene en cuenta esa posibilidad.
					// No hay feedback de texto, solo el hecho de desabilitar el
					// botón de lo que votas (ej: like) y habilitar el otro (ej: dislike).
					likeAndDislikeService.voteLike(likeAndDislike, user);
					votedLike = true;
					votedDislike = false;
					target.add(voteContainer);
					target.focusComponent(null);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			@Override
			public boolean isEnabled() {
				return !votedLike;
			}
		};
		voteContainer.add(likeLink);

		// dislike link
		AjaxLink<String> dislikeLink = new AjaxLink<String>("dislikeLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				try {
					// TODO: Quitar este try-catch cuando arreglen el servicio.
					likeAndDislikeService.voteDislike(likeAndDislike, user);
					votedDislike = true;
					votedLike = false;
					target.add(voteContainer);
					target.focusComponent(null);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			@Override
			public boolean isEnabled() {
				return !votedDislike;
			}
		};
		voteContainer.add(dislikeLink);
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderCSSReference("css/CommentAndVote.css");
	}

}
