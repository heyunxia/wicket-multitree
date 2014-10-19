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
package dom.simple;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.isisaddons.wicket.blueprint.applib.Blueprint;
import org.isisaddons.wicket.blueprint.applib.BlueprintLocation;

@DomainService(menuOrder = "10", repositoryFor = SimpleObject.class)
public class SimpleObjects {

    //region > identification in the UI
    // //////////////////////////////////////

    public String getId() {
        return "simple";
    }

    public String iconName() {
        return "SimpleObject";
    }

    //endregion

    //region > listAll (action)
    // //////////////////////////////////////

    @Bookmarkable
    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "1")
    public List<SimpleObject> listAll() {
        return container.allInstances(SimpleObject.class);
    }

    //endregion

    //region > create (action)
    // //////////////////////////////////////
    
    @MemberOrder(sequence = "2")
    public SimpleObject create(
            final @Named("Name") String name) {
        final SimpleObject obj = container.newTransientInstance(SimpleObject.class);
        obj.setName(name);
        container.persistIfNotAlready(obj);
        return obj;
    }

	public SimpleObject create(final @Named("Name") String name, final @Named("Location") BlueprintLocation location) {
        final SimpleObject obj = container.newTransientInstance(SimpleObject.class);
        obj.setName(name);
        obj.setLocation(location);
        container.persistIfNotAlready(obj);
        return obj;
	}

    
	public Blueprint createPlan(final @Named("Name") String name) {
	    final Blueprint obj = container.newTransientInstance(Blueprint.class);
	    obj.setImageFilename(name);
	    
	    try {
			Image image = ImageIO.read(new File("../dom/src/main/resources/images/"+name+".png"));
			obj.setXSize(image.getWidth(null));
			obj.setYSize(image.getHeight(null));
			image.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    container.persistIfNotAlready(obj);
	    return obj;
	}

    //endregion

    //region > injected services
    // //////////////////////////////////////

    @javax.inject.Inject 
    DomainObjectContainer container;

    //endregion

}
