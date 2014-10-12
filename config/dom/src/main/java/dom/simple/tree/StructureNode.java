/**
 * 
 */
package dom.simple.tree;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author kevin
 *
 */
@XmlRootElement(name="structure")
@XmlAccessorType(XmlAccessType.FIELD)
public class StructureNode extends RecursiveNode {
	private static final long serialVersionUID = 1L;

	//{{ XML properties
    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "alias")
    private String alias;
    
	@Override
	public String getName() {
		return name;
	}
}
