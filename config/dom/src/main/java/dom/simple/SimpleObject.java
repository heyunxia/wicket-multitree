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

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.value.Clob;
import org.isisaddons.wicket.blueprint.applib.Blueprint;
import org.isisaddons.wicket.blueprint.applib.BlueprintLocation;
import org.isisaddons.wicket.blueprint.applib.Blueprintable;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@ObjectType("SIMPLE")
@Bookmarkable
public class SimpleObject implements Comparable<SimpleObject>, Blueprintable {

    //region > name (property)
    // //////////////////////////////////////
    
    private String name;

    @javax.jdo.annotations.Column(allowsNull="false")
    @Title(sequence="1")
    @MemberOrder(sequence="1")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    //endregion
    
    private List<SimpleObject> friends = new ArrayList<SimpleObject>();
    
    public List<SimpleObject> getFriends() {
		return friends;
	}
    public void setFriends(final List<SimpleObject> friends) {
		this.friends = friends;
	}
    


    //region > compareTo
    // //////////////////////////////////////

    @Override
    public int compareTo(SimpleObject other) {
        return ObjectContracts.compare(this, other, "name");
    }

    //endregion

    //region > injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;

    //endregion
    
    // {{ Xml (property)
	private Clob xml;

	@MemberOrder(sequence = "1")
	@javax.jdo.annotations.Column(allowsNull="true")
	@javax.jdo.annotations.Persistent(defaultFetchGroup="false", columns = {
		      @javax.jdo.annotations.Column(name = "attachment_name"),
		      @javax.jdo.annotations.Column(name = "attachment_mimetype"),
		      @javax.jdo.annotations.Column(name = "attachment_chars", sqlType = "CLOB")
		  })
	public Clob getXml() {
		return xml;
	}

	public void setXml(final Clob xml) {
		this.xml = xml;
	}
	// }}

	
	private Blueprint plan;
	@javax.jdo.annotations.Column(allowsNull="true")	
	@Override
	public Blueprint getBlueprint() {
		return plan;
	}

	@Override
	public void setBlueprint(Blueprint blueprint) {
		plan = blueprint;
	}

	
	private BlueprintLocation blueprintLocation;
	@javax.jdo.annotations.Column(allowsNull="true")
	@javax.jdo.annotations.Persistent
	public BlueprintLocation getLocation() {
		return blueprintLocation;
	}
	public void setLocation(BlueprintLocation location) {
		this.blueprintLocation = location;
	}
	
}
