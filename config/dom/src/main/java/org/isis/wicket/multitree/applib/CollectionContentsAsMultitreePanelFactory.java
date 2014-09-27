/**
 * 
 */
package org.isis.wicket.multitree.applib;

import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * @author kevin
 * 
 */
public class CollectionContentsAsMultitreePanelFactory extends ComponentFactoryAbstract {
	private static final long serialVersionUID = 1L;
	private static final String NAME = "tree";

	public CollectionContentsAsMultitreePanelFactory() {
		super(ComponentType.COLLECTION_CONTENTS, NAME, CollectionContentsAsMultitreePanel.class);
	}

	@Override
	protected ApplicationAdvice appliesTo(IModel<?> model) {
		return appliesIf(model instanceof EntityCollectionModel);
	}

	@Override
	public Component createComponent(String id, IModel<?> model) {
		final EntityCollectionModel collectionModel = (EntityCollectionModel) model;
		return new CollectionContentsAsMultitreePanel(id, collectionModel);
	}

}
