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
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

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
public class BlueprintResourceMap extends RenderedDynamicImageResource implements Serializable {

    private static final long serialVersionUID = 1L;
    final EntityCollectionModel model;

    private BlueprintResourceMap(int width, int height, String format, EntityCollectionModel model) {
        super(width, height, format);
        this.model = model;
    }

    public BlueprintResourceMap(BufferedImage image, EntityCollectionModel model) {
        this(image.getWidth(), image.getHeight(), "png", model);
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

        final List<ObjectAdapter> adapterList = model.getObject();

        for (ObjectAdapter adapter : adapterList) {
            Blueprintable blueprintable = ((Blueprintable) adapter.getObject());
            BlueprintLocation location = blueprintable.getLocation();
            graphics.drawOval((int) location.getX(), (int) location.getY(), 10, 10);
        }
        return true;
    }
}
