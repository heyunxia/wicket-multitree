/**
 * 
 */
package dom.simple.tree;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.iter.codac.pss.configuration.RecordField;

/**
 * @author kevin
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	    "recordName",
	    "recordDescription",
	    "recordFieldCollection"
	})
public class RecordNode implements Node {
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "recordName", required = true)
    private String recordName;
    
    @XmlAttribute(name = "alias")
    private String alias;
    
    @XmlElement(name = "recordDescription")
    private String recordDescription;
    
	@XmlElementWrapper(name="recordFieldCollection" )
    @XmlElement(name = "recordField", required = true)
    private List<RecordField> recordFieldCollection;
	
	@Override
	public String toString() {
		return getName();
	}
	
	
	@Override
	public String getName() {
		return recordName;
	}

}
