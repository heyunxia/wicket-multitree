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


/**
 * Indicates that an entity can be printed on a blueprint.
 * The {{@link #getBlueprint()} indicates on which blueprint it can be printed.
 * @author kevin
 *
 */
public interface Blueprintable {
	// Where this item can be located
	Blueprint getBlueprint();
	void setBlueprint(Blueprint blueprint);
	
	BlueprintLocation getLocation();	
}
