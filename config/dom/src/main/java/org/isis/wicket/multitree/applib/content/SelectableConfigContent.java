/**
 * 
 */
package org.isis.wicket.multitree.applib.content;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

import dom.simple.tree.Configuration;
import dom.simple.tree.Node;

/**
 * @author Sven Meier
 * @author kevin
 */
public class SelectableConfigContent implements IDetachable {

	private static final long serialVersionUID = 1L;

	private ITreeProvider<Node> provider;

	private IModel<Node> selected;
	
    private Configuration config;

	public SelectableConfigContent(ITreeProvider<Node> provider, Configuration config){
    	this.config = config;
		this.provider = provider;
	}

	@Override
	public void detach() {
		if (selected != null) {
			selected.detach();
		}
	}

	protected boolean isSelected(Node node) {
		IModel<Node> model = provider.model(node);

		try {
			return selected != null && selected.equals(model);
		} finally {
			model.detach();
		}
	}

	protected void select(Node node,
			AbstractTree<Node> tree, final AjaxRequestTarget target) {
		if (selected != null) {
			tree.updateNode(selected.getObject(), target);

			selected.detach();
			selected = null;
		}

		selected = provider.model(node);

		tree.updateNode(node, target);
	}

	public Component newContentComponent(String id, final AbstractTree<Node> tree, IModel<Node> model) {
		System.out.println("SelectableConfigContent: newContentComponent");

		return new Folder<Node>(id, tree, model) {
			private static final long serialVersionUID = 1L;

			/**
			 * Always clickable.
			 */
			@Override
			protected boolean isClickable() {
				return true;
			}

			@Override
			protected void onClick(AjaxRequestTarget target) {
				SelectableConfigContent.this.select(getModelObject(), tree,
						target);
			}

			@Override
			protected boolean isSelected() {
				return SelectableConfigContent.this
						.isSelected(getModelObject());
			}

            @Override
            protected Component newLabelComponent(String id, IModel<Node> model) {
            	return new Label(id, model.toString());
            }
            @Override
            public String toString() {
            	return config.getTitle();
            }
		};
	}
}
