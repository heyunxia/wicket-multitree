package org.isis.wicket.multitree.applib;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.BeforeClass;
import org.junit.Test;

import dom.simple.tree.Configuration;
import dom.simple.tree.StructureNode;

public class ClobPlantSystemConfigurationDataProviderTest {

	static Configuration conf;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			JAXBContext jc = JAXBContext.newInstance(Configuration.class);
			
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			File source = getXml();
			conf = (Configuration) unmarshaller.unmarshal(source);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	private static File getXml() {
		File file = new File("src/main/resources/config-Tag[structured].xml");
		return file;
	}
	
	

	@Test
	public void testNotNull() {
		assertThat(conf, is(not(nullValue())));
	}

	
	@Test
	public void testTitleNotNull() {
		assertThat("conf.getTitle()", conf.getTitle(), is(not(nullValue())));
		
	}

	@Test
	public void testNameNotNull() {
		assertThat("conf.getName()", conf.getName(), is(not(nullValue())));
	}
	
	@Test
	public void testHasChildren() {
		assertThat(conf.hasChildren(), is(true));
		 
	}
	@Test
	public void testChildHasDetails() {
		List<StructureNode> structures = conf.getStructureCollection();
		assertThat(structures, is(not(nullValue())));
		
		StructureNode node = structures.get(0);
		assertThat(node, is(not(nullValue())));
		
		assertThat("node.getName()", node.getName(), is(not(nullValue())));
	}

}
