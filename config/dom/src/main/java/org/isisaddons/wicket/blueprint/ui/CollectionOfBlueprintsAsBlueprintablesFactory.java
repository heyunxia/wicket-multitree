/**
 * 
 */
package org.isisaddons.wicket.blueprint.ui;

import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.SpecificationLoader;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.isisaddons.wicket.blueprint.applib.Blueprintable;

/**
 * @author kevin
 *
 */
public class CollectionOfBlueprintsAsBlueprintablesFactory extends ComponentFactoryAbstract {
    private static final long serialVersionUID = 1L;
    private static final String ID_MAP = "blueprint";

    public CollectionOfBlueprintsAsBlueprintablesFactory() {
        super(ComponentType.COLLECTION_CONTENTS, ID_MAP);
    }
    
    @Override
    public ApplicationAdvice appliesTo(IModel<?> model) {
    	
        if (model instanceof EntityCollectionModel){
            EntityCollectionModel entityCollectionModel = (EntityCollectionModel) model;
            ObjectSpecification locatableSpec = getSpecificationLoader().loadSpecification(Blueprintable.class);
            ObjectSpecification typeOfSpec = entityCollectionModel.getTypeOfSpecification();
            return appliesIf(typeOfSpec.isOfType(locatableSpec));
        };

        return ApplicationAdvice.DOES_NOT_APPLY;
    }
    
    public Component createComponent(String id, IModel<?> model) {
        EntityCollectionModel collectionModel = (EntityCollectionModel) model;
        return new CollectionOfEntitiesAsBlueprintables(id, collectionModel);
    }

    protected SpecificationLoader getSpecificationLoader() {
        return IsisContext.getSpecificationLoader();
    }
}
