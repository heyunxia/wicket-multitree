/**
 * 
 */
package org.isis.wicket.multitree.applib;

import org.apache.isis.core.commons.authentication.MessageBroker;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.isis.WicketViewerSettings;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.components.actionprompt.ActionPromptModalWindow;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.model.IModel;
import org.isis.wicket.multitree.applib.content.Content;
import org.isis.wicket.multitree.applib.content.SelectableFolderContent;

import com.google.inject.Inject;


/**
 * @author kevin
 *
 */
public class CollectionContentsAsMultitreePanel extends PanelAbstract<EntityCollectionModel> {

	private static final long serialVersionUID = 1L;
	
    private static final String ID_TABLE = "tree";
    //private static final String ID_ENTITY_ACTIONS = "entityActions";
    private static final String ID_ACTION_PROMPT_MODAL_WINDOW = "actionPromptModalWindow";
    
    private AbstractTree<ObjectAdapter> tree;
    CollectionContentsDataProvider provider;
    private Content content;
    
    
    public CollectionContentsAsMultitreePanel(final String id, final EntityCollectionModel model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        buildGui();
    }
    
    @Override
    protected void onModelChanged() {
        buildGui();
    }

    private void buildGui() {
        final EntityCollectionModel model = getModel();
        
        provider = new CollectionContentsDataProvider(model);        
        content = new SelectableFolderContent(provider);
        
        tree = new 	NestedTree<ObjectAdapter>(ID_TABLE, provider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected Component newContentComponent(String id,
					IModel<ObjectAdapter> model) {
	               return CollectionContentsAsMultitreePanel.this.newContentComponent(id, model);			}
		};
		
        addActionPromptModalWindow();
        
        addOrReplace(tree);
    }
    
    protected Component newContentComponent(String id, IModel<ObjectAdapter> model)
    {
        return content.newContentComponent(id, tree, model);
    }   
    
    @Override
    public void detachModels() {
    	content.detach();
    	super.detachModels();
    }
    
    
    // ///////////////////////////////////////////////////////////////////
    // ActionPromptModalWindowProvider
    // ///////////////////////////////////////////////////////////////////
    
    private ActionPromptModalWindow actionPromptModalWindow;
    public ActionPromptModalWindow getActionPrompt() {
        return ActionPromptModalWindow.getActionPromptModalWindowIfEnabled(actionPromptModalWindow);
    }
    
    private void addActionPromptModalWindow() {
        this.actionPromptModalWindow = ActionPromptModalWindow.newModalWindow(ID_ACTION_PROMPT_MODAL_WINDOW); 
        addOrReplace(actionPromptModalWindow);
    }

    // //////////////////////////////////////

    @Inject
    private WicketViewerSettings settings;
    protected WicketViewerSettings getSettings() {
        return settings;
    }

    protected MessageBroker getMessageBroker() {
        return getAuthenticationSession().getMessageBroker();
    }
    
}
