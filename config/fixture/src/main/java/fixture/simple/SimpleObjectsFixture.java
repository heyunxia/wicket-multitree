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

package fixture.simple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.isisaddons.wicket.blueprint.applib.Blueprint;
import org.isisaddons.wicket.blueprint.applib.BlueprintLocation;

import dom.simple.SimpleObject;
import dom.simple.SimpleObjects;

public class SimpleObjectsFixture extends FixtureScript {

	public SimpleObjectsFixture() {
		withDiscoverability(Discoverability.DISCOVERABLE);
	}

	@Override
	protected void execute(ExecutionContext executionContext) {

		// prereqs
		execute(new SimpleObjectsTearDownFixture(), executionContext);

		// create
		SimpleObject o1 = create("Foo", new BlueprintLocation(632, 280), executionContext);
		SimpleObject o2 = create("Bar", new BlueprintLocation(512, 302), executionContext);
		SimpleObject o3 = create("Baz", new BlueprintLocation(118, 130), executionContext);

		/*
		String chars = getXml();
		Clob xml = new Clob("XML", "application/xml", chars);
		o1.setXml(xml);
		*/
		
		Blueprint plan = createPlan("floorplan", executionContext);
		
		plan.addToContents(o1);
		
		plan.addToContents(o2);
		
		plan.addToContents(o3);
		
		o1.getFriends().add(o2);
		o1.getFriends().add(o3);
	}

	// //////////////////////////////////////

	private SimpleObject create(final String name,
			BlueprintLocation blueprintLocation, ExecutionContext executionContext) {
		return executionContext.add(this, simpleObjects.create(name, blueprintLocation));
	}
	
	private Blueprint createPlan(final String name,
			ExecutionContext executionContext) {
		return executionContext.add(this, simpleObjects.createPlan(name));
	}
	

	// //////////////////////////////////////

	@javax.inject.Inject
	private SimpleObjects simpleObjects;

	private String getXml() {
		File file = new File("../dom/src/main/resources/config-Tag[structured].xml");
		String everything = "";
		try {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				everything = sb.toString();
			} finally {
				if (br != null){
					br.close();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return everything;
	}

}
