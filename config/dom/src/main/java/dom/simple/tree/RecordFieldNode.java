/**
 * 
 */
package dom.simple.tree;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author kevin
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RecordFieldNode implements Node {
	private static final long serialVersionUID = 1L;
	
	public static final String VAL = "VAL";

    @XmlAttribute(name = "name", required=true)
    private String name;
    
    @XmlValue
    private String value;
	
	@Override
	public String getName() {
		return name;
	}

}
