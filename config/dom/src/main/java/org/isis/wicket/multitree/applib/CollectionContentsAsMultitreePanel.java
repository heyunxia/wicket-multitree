/**
 * 
 */
package org.isis.wicket.multitree.applib;

import java.util.List;

import org.apache.isis.core.commons.authentication.MessageBroker;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.isis.WicketViewerSettings;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.components.actionprompt.ActionPromptModalWindow;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.model.IModel;

import com.google.inject.Inject;


/**
 * @author kevin
 *
 */
public class CollectionContentsAsMultitreePanel extends
		PanelAbstract<EntityCollectionModel> {

	private static final long serialVersionUID = 1L;
	
    private static final String ID_TABLE = "tree";
    //private static final String ID_ENTITY_ACTIONS = "entityActions";
    private static final String ID_ACTION_PROMPT_MODAL_WINDOW = "actionPromptModalWindow";
    
    
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
        
        final CollectionContentsDataProvider provider = new CollectionContentsDataProvider(model);
		final DefaultNestedTree<ObjectAdapter> tree = new DefaultNestedTree<ObjectAdapter>(ID_TABLE, provider){
			private static final long serialVersionUID = 1L;
			
			

			@Override
			protected Component newContentComponent(String id,
					IModel<ObjectAdapter> node) {
				// TODO Auto-generated method stub
				return super.newContentComponent(id, node);
			}
		};
        addActionPromptModalWindow();
        
        addOrReplace(tree);
        //applyCssVisibility(tree, !adapterList.isEmpty());
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
