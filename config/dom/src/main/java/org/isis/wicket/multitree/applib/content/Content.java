/**
 * 
 */
package org.isis.wicket.multitree.applib.content;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.ui.ComponentFactory;
import org.apache.isis.viewer.wicket.ui.ComponentType;
import org.apache.isis.viewer.wicket.ui.app.registry.ComponentFactoryRegistry;
import org.apache.isis.viewer.wicket.ui.app.registry.ComponentFactoryRegistryAccessor;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

/**
 * @author kevin
 *
 */
public abstract class Content implements IDetachable {
	private static final long serialVersionUID = 1L;

	public abstract Component newContentComponent(String id, AbstractTree<ObjectAdapter> tree,
            IModel<ObjectAdapter> model);
	
	@Override
	public void detach() {
	}

    protected ComponentFactory findComponentFactory(final ComponentType componentType, final IModel<?> model) {
        return getComponentRegistry().findComponentFactory(componentType, model);
    }
    
    protected ComponentFactoryRegistry getComponentRegistry() {
        final ComponentFactoryRegistryAccessor cfra = (ComponentFactoryRegistryAccessor) Application.get();
        return cfra.getComponentFactoryRegistry();
    }
	
}
