/*
 *  Copyright 2013~2014 Kevin Meyer
 *
 *  Licensed under the Apache License, Version 2.0 (the
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
package org.isisaddons.wicket.blueprint.applib;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.Optional;

/**
 * Value representing a Blueprint on a blueprint (image)
 * 
 * @author kevin
 *
 */
@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")
@ObjectType("BLUEPRINT")
public class Blueprint implements Serializable, Blueprintable {
	private static final long serialVersionUID = 1L;
	
	// Hierarchy
	@Persistent(mappedBy = "blueprint", dependentElement = "true")
	private SortedSet<Blueprint> children = new TreeSet<Blueprint>();
	
	// Position
	private BlueprintLocation location;
	
	// Plotting
	/*
	private int scale;
	*/
	private int xSize;
	private int ySize;
	
	private String imageFilename;
	
	private Blueprint blueprint;
	@Persistent(mappedBy = "blueprint", dependentElement = "true")
	private SortedSet<Blueprintable> contents = new TreeSet<Blueprintable>();

	//{{ Hierarchy
	//{{Parent
	@javax.jdo.annotations.Column(allowsNull="true")
	@Override
	public Blueprint getBlueprint() {
		return blueprint;
	}

	@Override
	/**
	 * Note: Don't set parent directly, rather use {@link #getChildren().add(child).
	 * @param parent
	 */
	public void setBlueprint(Blueprint blueprint) {
		this.blueprint = blueprint;
	}
	//}}Parent
	
	//}} Hierarchy

	
	//{{ Position
	//}} Position

	//{{ Plotting
	public int getXSize() {
		return xSize;
	}
	public void setXSize(int xSize) {
		this.xSize = xSize;
	}
	public int getYSize() {
		return ySize;
	}
	public void setYSize(int ySize) {
		this.ySize = ySize;
	}

	@Optional
	public String getImageFilename() {
		return imageFilename;
	}
	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}
	//}} Plotting
	
	//{{Contents
	@MemberOrder(sequence = "1")
	@Persistent(mappedBy="blueprint")	
	public SortedSet<Blueprintable> getContents() {
		return contents;
	}
	public void setContents(final SortedSet<Blueprintable> contents) {
		this.contents = contents;
	}
	
	public void addToContents(Blueprintable content){
		getContents().add(content);
	}
	public void removeFromContents(Blueprintable content){
		getContents().remove(content);
	}
	//}}Contents

	@Override
	public BlueprintLocation getLocation() {
		return location;
	}

}
