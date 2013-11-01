
function AddCommentForm(form) {
	this.label = form.find("label:first");
	this.counter = form.find(".maxLength");
	this.maxLength = parseInt(this.counter.children("span").html());
	this.counter.children().remove();
	this.counter.html(this.maxLength);
	this.textarea = form.find("textarea:first").val("");
	this.submit = form.find("input:first");
	this.defaultClass = this.counter.attr("class");
	this.counter.attr("class", this.defaultClass);
	this.container = form.addClass("js_on");
	var textarea = this.textarea;
	this.label.click(function(){textarea.focus();});
}

AddCommentForm.prototype = {
	disable: function() {
		var d = "disabled";
		this.submit.prop(d, true).attr(d,d).addClass(d);
		return this;
	},
	enable: function() {
		var d = "disabled";
		this.submit.prop(d, false).removeAttr(d).removeClass(d);
		return this;
	},
	focus: function() {
		this.label.hide();
		return this.keydown();
	},
	blur: function() {
		if (this.textarea.val() == "")
			this.label.show();
		return this.keydown();
	},
	keydown: function() {
		var leftLength = this.maxLength - this.textarea.val().length;
		this.counter.html(leftLength);
		var counterClass = this.defaultClass;
		this.enable();
		if (leftLength <= 20) {
			if (leftLength <= 10)
				counterClass = this.disable().defaultClass+" superwarn";
			else
				counterClass = this.defaultClass+" warn";
		}
		if (leftLength==this.maxLength)
			this.disable();
		this.counter.attr("class", counterClass);
		return this;
	}
};

function addCommentForm(formObj) {
	var form = new AddCommentForm(formObj);
	form.textarea.focus(function(){form.focus();});
	form.textarea.blur(function(){form.blur();});
	form.textarea.keydown(function(){form.keydown();});
	form.textarea.keyup(function(){form.keydown();});
	form.blur().disable();
}

$(document).ready(function(){
	var commentForms = $('.addCommentForm').get();
	for (var i in commentForms)
		addCommentForm($(commentForms[i]));
});


