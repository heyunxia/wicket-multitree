/**
 * 
 */
package org.isis.wicket.multitree.applib;

import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.SpecificationLoader;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.wicket.model.models.ScalarModel;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * @author kevin
 * 
 */
public class ClobAsMultitreePanelFactory extends ComponentFactoryAbstract {
	private static final long serialVersionUID = 1L;
	private static final String NAME = "tree";

	public ClobAsMultitreePanelFactory() {
		super(ComponentType.SCALAR_NAME_AND_VALUE, NAME, ClobAsMultitreePanel.class);
	}

	@Override
	protected ApplicationAdvice appliesTo(IModel<?> model) {
		
        if (!(model instanceof ScalarModel)) {
            return ApplicationAdvice.DOES_NOT_APPLY;
        }
        final ScalarModel scalarModel = (ScalarModel) model;
        ObjectSpecification configSpec = getSpecificationLoader().loadSpecification(org.apache.isis.applib.value.Clob.class);
        ObjectSpecification typeOfSpec = scalarModel.getTypeOfSpecification();
        if (model.getObject() == null){
        	System.out.println("Model is null");
        	return appliesIf(false);
        }
        return appliesIf(typeOfSpec.isOfType(configSpec));
	}

	@Override
	public Component createComponent(String id, IModel<?> model) {
		final ScalarModel scalarModel = (ScalarModel) model;
		return new ClobAsMultitreePanel(id, scalarModel);
	}
	
    protected SpecificationLoader getSpecificationLoader() {
        return IsisContext.getSpecificationLoader();
    }

}
