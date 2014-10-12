/**
 * 
 */
package org.isis.wicket.multitree.applib;

import org.apache.isis.core.commons.authentication.MessageBroker;
import org.apache.isis.viewer.wicket.model.isis.WicketViewerSettings;
import org.apache.isis.viewer.wicket.model.models.ScalarModel;
import org.apache.isis.viewer.wicket.ui.components.actionprompt.ActionPromptModalWindow;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.theme.WindowsTheme;
import org.apache.wicket.model.IModel;
import org.isis.wicket.multitree.applib.content.StructureContent;
import org.iter.codac.pss.configuration.Structure;

import com.google.inject.Inject;


/**
 * @author kevin
 *
 */
public class ClobAsMultitreePanel extends PanelAbstract<ScalarModel> {

	private static final long serialVersionUID = 1L;
	
    private static final String ID_TABLE = "tree";
    //private static final String ID_ENTITY_ACTIONS = "entityActions";
    private static final String ID_ACTION_PROMPT_MODAL_WINDOW = "actionPromptModalWindow";
    
    // Wicket stuff
    private Behavior theme;    
    private StructureContent content;
    private ClobPlantSystemConfigurationDataProvider provider;

    // The collection being presented 
    private AbstractTree<Structure> tree;
    
    public ClobAsMultitreePanel(final String id, final ScalarModel model) {
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
        final ScalarModel model = getModel();
        provider = new ClobPlantSystemConfigurationDataProvider(model);
        
        content = new StructureContent(provider.getConfig());
        
        theme = new WindowsTheme();
        
        tree = new 	DefaultNestedTree<Structure>(ID_TABLE, provider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected Component newContentComponent(String id, IModel<Structure> model) {
				return ClobAsMultitreePanel.this.newContentComponent(id, model);			
			}
		};
		
		tree.add(theme);
		
		//tree.add(new Drag)
		
        addActionPromptModalWindow();
        
        addOrReplace(tree);
    }
    
    protected Component newContentComponent(String id, IModel<Structure> model)
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
