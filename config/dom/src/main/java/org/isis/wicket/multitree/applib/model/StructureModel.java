/**
 * 
 */
package org.isis.wicket.multitree.applib.model;

import java.io.Serializable;

import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import dom.simple.tree.Node;
import dom.simple.tree.StructureNode;

/**
 * @author kevin
 *
 */

/**
 * A {@link Model} which uses an id to load its {@link Foo}.
 * 
 * If {@link Foo}s were {@link Serializable} you could just use a standard {@link Model}.
 * 
 * @see #equals(Object)
 * @see #hashCode()
 */
public class StructureModel extends LoadableDetachableModel<Node>
{
    private static final long serialVersionUID = 1L;

    private final String name;
    private StructureNode structure;

    public StructureModel(StructureNode category, String name) {
        super(category);
        this.structure = category;
    	this.name = name;
    }

    @Override
    protected StructureNode load(){
        return structure;
    }

    /**
     * Important! Models must be identifiable by their contained object.
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof StructureModel){
            return ((StructureModel)obj).toString().equals(name);
        }
        return false;
    }

    /**
     * Important! Models must be identifiable by their contained object.
     */
    @Override
    public int hashCode(){
        return name.hashCode();
    }
    
    @Override
    public String toString() {
    	return structure.toString();
    }
}