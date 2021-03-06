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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.activation.MimeTypeParseException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.isis.applib.value.Clob;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.models.ScalarModel;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.model.IModel;
import org.isis.wicket.multitree.applib.model.ConfigModel;
import org.isis.wicket.multitree.applib.model.RecordNodeModel;
import org.isis.wicket.multitree.applib.model.StructureModel;

import dom.simple.tree.Configuration;
import dom.simple.tree.Node;
import dom.simple.tree.RecordNode;
import dom.simple.tree.StructureNode;

/**
 * Part of the {@link AjaxFallbackDefaultDataTable} API.
 */
public class ClobPlantSystemConfigurationDataProvider implements ITreeProvider<Node> {
	private static final long serialVersionUID = 1L;
	private final ScalarModel model;
	private Configuration config;

	public ClobPlantSystemConfigurationDataProvider(final ScalarModel model) {
		this.model = model;
		ObjectAdapter ob1 = model.getObject();
		if (ob1 == null){
			config = new Configuration();
			return;
		}
		Object o = ob1.getObject();
		if (o instanceof Clob) {
			Clob clob = (Clob) o;
			try {
				if (clob.getMimeType().match("application/xml")) {
					config = getConfigFrom(clob.getChars());
				}
			} catch (MimeTypeParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private Configuration getConfigFrom(CharSequence chars) {
		System.out.println("Reading XML");
		try {
			JAXBContext jc = JAXBContext.newInstance(Configuration.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			InputStream source = getStream(chars);
			return (Configuration) unmarshaller.unmarshal(source);
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	private InputStream getStream(final CharSequence charSequence) {
		return new InputStream() {
			int index = 0;
			int length = charSequence.length();

			@Override
			public int read() throws IOException {
				return index >= length ? -1 : charSequence.charAt(index++);
			}
		};
	}

	public Configuration getConfig() {
		return config;
	}

	
	@Override
	public void detach() {
		model.detach();
	}

	@Override
	public Iterator<? extends Node> getRoots() {
		/*
		if (config==null){
			return null;
		}
		List<Node> list = new ArrayList<Node>();
		list.add(config);
		//TODO: Add Constraints, later
		return list.iterator();
		*/
		return config.getChildren();
	}


	@Override
	public Iterator<? extends Node> getChildren(Node node) {
		if (node instanceof Configuration){
			Configuration conf = (Configuration)node;
			return conf.getChildren();
		}
		if (node instanceof StructureNode){
			StructureNode cNode = (StructureNode)node;
			return cNode.getChildren();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Node node) {
		if (node instanceof Configuration){
			Configuration conf = (Configuration)node;
			return conf.hasChildren();
		}
		if (node instanceof StructureNode){
			StructureNode cNode = (StructureNode)node;
			return cNode.hasChildren();
		}
		return false;
	}

	@Override
	public IModel<Node> model(Node object) {
		if (object instanceof Configuration){
			Configuration conf = (Configuration)object;
			return new ConfigModel(conf, conf.getName());
		}
		if (object instanceof StructureNode){
			StructureNode cNode = (StructureNode)object;
			return new StructureModel(cNode, cNode.getName());
		}
		if (object instanceof RecordNode){
			RecordNode cNode = (RecordNode)object;
			return new RecordNodeModel(cNode, cNode.getName());
		}
		return null;
	}
}