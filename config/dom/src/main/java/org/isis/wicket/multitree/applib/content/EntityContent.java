/**
 * 
 */
package org.isis.wicket.multitree.applib.content;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.ui.ComponentFactory;
import org.apache.isis.viewer.wicket.ui.ComponentType;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.model.IModel;

/**
 * @author Kevin
 */
public class EntityContent extends Content
{

    private static final long serialVersionUID = 1L;

	@Override
	public Component newContentComponent(String id, final AbstractTree<ObjectAdapter> tree, IModel<ObjectAdapter> model) {
		System.out.println("SelectableFolderContent: newContentComponent");
		final ComponentFactory componentFactory = findComponentFactory(	ComponentType.ENTITY_LINK, model);
		return componentFactory.createComponent(id, model);
	}
}