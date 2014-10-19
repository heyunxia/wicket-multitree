/**
 * 
 */
package org.isis.wicket.multitree.applib.content;

import org.apache.isis.viewer.wicket.ui.ComponentFactory;
import org.apache.isis.viewer.wicket.ui.ComponentType;
import org.apache.isis.viewer.wicket.ui.app.registry.ComponentFactoryRegistry;
import org.apache.isis.viewer.wicket.ui.app.registry.ComponentFactoryRegistryAccessor;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.iter.codac.pss.configuration.RecursiveStructure;
/**
 * @author kevin
 *
 */
public class RecursiveStructureContent implements IDetachable {

    private static final long serialVersionUID = 1L;

    public Component newContentComponent(String id, final AbstractTree<RecursiveStructure> tree, IModel<RecursiveStructure> model)
    {
    	System.out.println("RecursiveStructureContent:newContentComponent");
    	Component component = new Folder<RecursiveStructure>(id, tree, model){
            private static final long serialVersionUID = 1L;
            
            @Override
            protected Component newLabelComponent(String id, IModel<RecursiveStructure> model) {
                final ComponentFactory componentFactory = findComponentFactory(ComponentType.ENTITY_LINK, model);
                return componentFactory.createComponent(id, model);    	
            }
        };
        component.setOutputMarkupId(true);
        return component;
    }

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