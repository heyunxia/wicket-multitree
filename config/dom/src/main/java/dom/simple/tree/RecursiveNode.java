/**
 * 
 */
package dom.simple.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * @author kevin
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class RecursiveNode implements Node {
	private static final long serialVersionUID = 1L;
	@XmlElementWrapper(name="configurableRecordCollection" )
    @XmlElement(name = "configurableRecord", required = true)
    private List<RecordNode> configurableRecordCollection = new ArrayList<RecordNode>();
    @XmlElement(name = "structure")
    private List<StructureNode> structures = new ArrayList<StructureNode>();
    
	
	@Override
	public String toString() {
		return getName();
	}
	
    
    public List<StructureNode> getStructureCollection(){
    	return structures;
    };
    
    public List<RecordNode> getRecordCollection(){
    	return configurableRecordCollection;
    };
    
	public Iterator<? extends Node> getChildren() {
		List<Node> allChildren = getChildrenAsList();
		return allChildren.iterator();
	}

	protected List<Node> getChildrenAsList() {
		List<Node> allChildren = new ArrayList<Node>();
		allChildren.addAll(getStructureCollection());
		allChildren.addAll(getRecordCollection());
		return allChildren;
	}
    
	public boolean hasChildren(){
		return (structures.size()>0) || (configurableRecordCollection.size() > 0);
	}
    
}
