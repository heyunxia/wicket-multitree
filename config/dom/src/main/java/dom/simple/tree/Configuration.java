/**
 * 
 */
package dom.simple.tree;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author kevin
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "configurationEditorSampleConfiguration")
public class Configuration extends RecursiveNode {
	private static final long serialVersionUID = 1L;

    @XmlElement(name = "title")
    private String title;
    
    //TODO: Add constraints, later    
	/*
    @XmlElement(name = "configurableConstraint")
    @XmlElementWrapper(name = "configurableConstraintCollection")
    private List<ConfigurableConstraint> configurableConstraintCollection;
    */
    
	@Override
	public String getName() {
		return title;
	}

	public String getTitle() {
		return title;
	}
	
	@Override
	public String toString() {
		return title;
	}
}
