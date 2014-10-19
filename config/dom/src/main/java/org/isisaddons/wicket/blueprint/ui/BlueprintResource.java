/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.isisaddons.wicket.blueprint.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.models.EntityCollectionModel;
import org.apache.wicket.markup.html.image.resource.RenderedDynamicImageResource;
import org.isisaddons.wicket.blueprint.applib.Blueprint;
import org.isisaddons.wicket.blueprint.applib.BlueprintLocation;
import org.isisaddons.wicket.blueprint.applib.Blueprintable;


/**
 * 
 *
 */
public class BlueprintResource extends RenderedDynamicImageResource implements Serializable {

    private static final long serialVersionUID = 1L;
    final EntityCollectionModel model;
    final BufferedImage image;

    private BlueprintResource(int width, int height, String fileName, EntityCollectionModel model) {
        super(width, height, "png");
        this.model = model;
    	image = load(fileName);
    }

    public BlueprintResource(int width, int height, EntityCollectionModel model) {
        this(width, height, null, model);
    }

    public BlueprintResource(Blueprint parent, EntityCollectionModel model) {
    	this(parent.getXSize(), parent.getYSize(), parent.getImageFilename(), model);
	}

	/**
     * @param filename
     *            The name of the file to load
     * @return The image read form the file
     */
    private BufferedImage load(final String filename) {
    	if (filename == null){
    		return null;
    	}
        try {
            final String fullName = "../dom/src/main/resources/images/" + filename + ".png";
            InputStream resourceAsStream = new FileInputStream(fullName);
            MemoryCacheImageInputStream stream = new MemoryCacheImageInputStream(resourceAsStream);
            return ImageIO.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* (non-Javadoc)
     * @see org.apache.wicket.markup.html.image.resource.RenderedDynamicImageResource#render(java.awt.Graphics2D, org.apache.wicket.request.resource.IResource.Attributes)
     */
    @Override
    protected boolean render(Graphics2D graphics, Attributes attributes) {
        // Draw circle with thick stroke width
        final BasicStroke basicStroke = new BasicStroke(5);
        graphics.setStroke(basicStroke);
        graphics.drawOval(20, 30, 5, 5);
        graphics.setPaint(Color.BLUE);

        final List<ObjectAdapter> adapterList = model.getObject();

        int i = 0;
        for (ObjectAdapter adapter : adapterList) {
            Blueprintable blueprintable = ((Blueprintable) adapter.getObject());

            // First one gets to be the background
            if (i++ == 0) {
                graphics.drawImage(image, null, null);
            }
            BlueprintLocation location = blueprintable.getLocation();
            if (location != null){
            	graphics.drawOval((int) location.getX() - 5, (int) location.getY() - 5, 10, 10);
            }
        }
        return true;
    }
}
