/**
 * 
 */
package org.isis.wicket.multitree.applib.content;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.ui.ComponentFactory;
import org.apache.isis.viewer.wicket.ui.ComponentType;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.model.IModel;
/**
 * @author kevin
 *
 */
public class FolderContent extends Content {

    private static final long serialVersionUID = 1L;

    @Override
    public Component newContentComponent(String id, final AbstractTree<ObjectAdapter> tree, IModel<ObjectAdapter> model)
    {
    	System.out.println("FolderContent:newContentComponent");
    	Component component = new Folder<ObjectAdapter>(id, tree, model){
            private static final long serialVersionUID = 1L;
            
            @Override
            protected Component newLabelComponent(String id, IModel<ObjectAdapter> model) {
                final ComponentFactory componentFactory = findComponentFactory(ComponentType.ENTITY_LINK, model);
                return componentFactory.createComponent(id, model);    	
            }
        };
        component.setOutputMarkupId(true);
        return component;
    }
}