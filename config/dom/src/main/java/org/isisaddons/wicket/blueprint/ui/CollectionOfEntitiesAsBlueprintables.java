/**
 * 
 */
package org.isisaddons.wicket.blueprint.ui;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.model.models.EntityModel;
import org.apache.isis.viewer.wicket.model.models.ImageResourceCache;
import org.apache.isis.viewer.wicket.model.models.PageType;
import org.apache.isis.viewer.wicket.ui.pages.PageClassRegistry;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.ClientSideImageMap;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.isisaddons.wicket.blueprint.applib.Blueprint;
import org.isisaddons.wicket.blueprint.applib.BlueprintLocation;
import org.isisaddons.wicket.blueprint.applib.Blueprintable;


/**
 * @author kevin
 *
 */
public class CollectionOfEntitiesAsBlueprintables extends PanelAbstract<EntityCollectionModel> {
	private static final long serialVersionUID = 1L;
	
    private static final String INVISIBLE_CLASS = "collection-contents-as-blueprint-locatables-invisible";
    private static final String ID_MAP = "blueprint";
    private static final String ID_IMAGEMAP = "blueprintmap";
    private static final String ID_LINK = "enlink";
	

    @Inject
    private ImageResourceCache imageResourceCache;
    
    @Inject
    private PageClassRegistry pageClassRegistry;
	
	public CollectionOfEntitiesAsBlueprintables(final String id, final EntityCollectionModel model) {
        super(id, model);
        buildGui();
    }

    private void buildGui() {
        final EntityCollectionModel model = getModel();
        final List<ObjectAdapter> adapterList = model.getObject();
        BlueprintResource floorPlan = getBlueprintResourceFor(adapterList, model);
        
        final Image image = new Image(ID_MAP, floorPlan);
        final ClientSideImageMap imageMap = new ClientSideImageMap(ID_IMAGEMAP, image);
        addOrReplace(image);
        addOrReplace(imageMap);


        if (!adapterList.isEmpty()) {
            int i = 0;
            for (ObjectAdapter adapter : adapterList) {
                Blueprintable blueprintable = ((Blueprintable) adapter.getObject());
                //Blueprint blueprint = blueprintable.getBlueprint();
                AbstractLink link = makeClickableLinkFor(i++, adapter);
                BlueprintLocation location = blueprintable.getLocation();
                if (location != null){
                	imageMap.addCircleArea(link, location.getX(), location.getY(), 10);
                }
            }

            applyCssVisibility(image, !adapterList.isEmpty());
        }
    }

    private BlueprintResource getBlueprintResourceFor(List<ObjectAdapter> adapterList, final EntityCollectionModel model) {
    	
        if (adapterList.isEmpty()) {
            return new BlueprintResource(10, 10, model);
        }
        
        Blueprint parent=null;
        for (ObjectAdapter objectAdapter : adapterList) {
            Blueprintable blueprintable = ((Blueprintable) objectAdapter.getObject());
            Blueprint blueprint = blueprintable.getBlueprint();
            if (parent == null){
            	parent = blueprint.getBlueprint();
            }
            if (parent == null){
            	parent = blueprint;
            } else {
            	if (blueprint != null && !parent.equals(blueprint)){
            		parent = blueprint;
            	}
            }
		}
        
        
        return new BlueprintResource(parent, model);
	}

	private AbstractLink makeClickableLinkFor(int i, ObjectAdapter adapter) {
        final Class<? extends Page> pageClass = getPageClassRegistry().getPageClass(PageType.ENTITY);
        final PageParameters pageParameters = EntityModel.createPageParameters(adapter);

        String urlFor = (String) RequestCycle.get().urlFor(pageClass, pageParameters);

        final String titleString = adapter.titleString(adapter);
        return new ExternalLink(ID_LINK + String.valueOf(i), urlFor, titleString);
    }

    private static void applyCssVisibility(final Component component, final boolean visible) {
        if (visible == false) {
            return;
        }
        final Object compName = component.getMarkupAttributes().get("class");
        final String newName = String.valueOf(compName).replaceFirst(INVISIBLE_CLASS, "");
        final AttributeModifier modifier = visible ? new AttributeModifier("class", newName) : new AttributeAppender("class", " " + INVISIBLE_CLASS);
        component.add(modifier);
    }

    
    @Override
    protected void onModelChanged() {
        buildGui();
    }
    
    //////////////////////////////////////////////
    // Dependency Injection
    //////////////////////////////////////////////

    protected ImageResourceCache getImageCache() {
        return imageResourceCache;
    }

    protected PageClassRegistry getPageClassRegistry() {
        return pageClassRegistry;
    }

}
