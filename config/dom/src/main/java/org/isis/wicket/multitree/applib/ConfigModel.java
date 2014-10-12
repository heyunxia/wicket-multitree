/**
 * 
 */
package org.isis.wicket.multitree.applib;

import java.io.Serializable;

import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.iter.codac.pss.configuration.Structure;

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
public class ConfigModel extends LoadableDetachableModel<Structure>
{
    private static final long serialVersionUID = 1L;

    private final String name;
    private Structure structure;

    public ConfigModel(Structure structure, String name) {
        super(structure);
        this.structure = structure;
        
    	this.name = name;
    }

    @Override
    protected Structure load(){
    	for (Structure structure : this.structure.getStructure()) {
			if (structure.getName().equals(name)){
				return structure;
			}
		}
        return structure;//config.getStructure().get(0);//Foo(id);
    }

    /**
     * Important! Models must be identifiable by their contained object.
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof ConfigModel){
            return ((ConfigModel)obj).toString().equals(name);
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