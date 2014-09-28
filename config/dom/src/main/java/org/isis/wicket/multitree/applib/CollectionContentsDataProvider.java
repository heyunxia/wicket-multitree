/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.isis.wicket.multitree.applib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.Contributed;
import org.apache.isis.core.metamodel.spec.feature.ObjectAssociation;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.isis.viewer.wicket.model.models.EntityModel;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.model.IModel;

/**
 * Part of the {@link AjaxFallbackDefaultDataTable} API.
 */
public class CollectionContentsDataProvider implements ITreeProvider<ObjectAdapter> {
    private static final long serialVersionUID = 1L;
    private final EntityCollectionModel model;

    public CollectionContentsDataProvider(final EntityCollectionModel model) {
        this.model = model;
    }

    @Override
    public void detach() {
        model.detach();
    }
    
    @Override
    public Iterator<ObjectAdapter> getRoots()
    {
        List<ObjectAdapter> adapters = model.getObject();
        return adapters.iterator();
    }
    

	@Override
	public boolean hasChildren(ObjectAdapter node) {
        //return foo.getParent() == null || !foo.getFoos().isEmpty();
		ObjectSpecification objectSpecification = node.getSpecification();
        final List<ObjectAssociation> associations = objectSpecification.getAssociations(Contributed.EXCLUDED);
        for (final ObjectAssociation association : associations) {
        	if (association.isOneToManyAssociation()){
        		//return true;
        	}
            	
        }
		return false;
	}
	
	@Override
	public Iterator<ObjectAdapter> getChildren(ObjectAdapter node) {
		ObjectSpecification objectSpecification = node.getSpecification();
        final List<ObjectAssociation> associations = objectSpecification.getAssociations(Contributed.EXCLUDED);
        for (final ObjectAssociation association : associations) {
        	if (association.isOneToManyAssociation()){
                List<ObjectAdapter> adapters = new ArrayList<ObjectAdapter>();
                ObjectAdapter e = association.get(node);
                adapters.add(e);
                return adapters.iterator();
        	}
        }
        List<ObjectAdapter> adapters = model.getObject();
        return adapters.iterator();
	}


    @Override
    public IModel<ObjectAdapter> model(final ObjectAdapter adapter) {
        return new EntityModel(adapter);
    }
}