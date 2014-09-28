/**
 * 
 */
package org.isis.wicket.multitree.applib.content;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

/**
 * @author Sven Meier
 */
public class SelectableFolderContent extends Content {

	private static final long serialVersionUID = 1L;

	private ITreeProvider<ObjectAdapter> provider;

	private IModel<ObjectAdapter> selected;

	public SelectableFolderContent(ITreeProvider<ObjectAdapter> provider) {
		this.provider = provider;
	}

	@Override
	public void detach() {
		if (selected != null) {
			selected.detach();
		}
	}

	protected boolean isSelected(ObjectAdapter ObjectAdapter) {
		IModel<ObjectAdapter> model = provider.model(ObjectAdapter);

		try {
			return selected != null && selected.equals(model);
		} finally {
			model.detach();
		}
	}

	protected void select(ObjectAdapter ObjectAdapter,
			AbstractTree<ObjectAdapter> tree, final AjaxRequestTarget target) {
		if (selected != null) {
			tree.updateNode(selected.getObject(), target);

			selected.detach();
			selected = null;
		}

		selected = provider.model(ObjectAdapter);

		tree.updateNode(ObjectAdapter, target);
	}

	@Override
	public Component newContentComponent(String id,
			final AbstractTree<ObjectAdapter> tree, IModel<ObjectAdapter> model) {
		System.out.println("SelectableFolderContent: newContentComponent");

		return new Folder<ObjectAdapter>(id, tree, model) {
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
				SelectableFolderContent.this.select(getModelObject(), tree,
						target);
			}

			@Override
			protected boolean isSelected() {
				return SelectableFolderContent.this
						.isSelected(getModelObject());
			}

			@Override
			protected Component newLabelComponent(String id,
					IModel<ObjectAdapter> rowModel) {
				return new Label(id);
			}
		};
	}
}
