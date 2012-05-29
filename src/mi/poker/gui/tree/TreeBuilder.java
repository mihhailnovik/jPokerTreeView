package mi.poker.gui.tree;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import mi.poker.common.model.struct.Node;
import mi.poker.common.model.struct.Tree;
import mi.poker.gui.tree.struct.NodeMetaInfo;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class TreeBuilder {

	private static final String NAME = "name";
	private static final String VALUE = "value";
	private static final String PLAYER = "player";
	
	public static Tree<String> buildDemoPokerTree() throws JDOMException, IOException {
		NodeMetaInfo<String> root = null;
		SAXBuilder parser = new SAXBuilder();
		Document doc = parser.build("/home/m1/programming/java/jPokerTreeView/data/large_tree.xml");
		Element data = doc.getRootElement().getChildren().get(0);
		root = convert(data);
		root.setChildren(convert(data.getChildren()));
		
		Tree<String> tree = new Tree<String>(root);
		return tree;
	}
	
	public static Tree<String> buildPokerTree(String filePath) throws JDOMException, IOException{
		NodeMetaInfo<String> root = null;
		SAXBuilder parser = new SAXBuilder();
		Document doc = parser.build(filePath);
		Element data = doc.getRootElement().getChildren().get(0);
		root = convert(data);
		root.setChildren(convert(data.getChildren()));
		
		Tree<String> tree = new Tree<String>(root);
		return tree;
	}
	
	public static Tree<String> buildDemoPokerSmallTree() throws JDOMException, IOException {
		NodeMetaInfo<String> root = null;
		SAXBuilder parser = new SAXBuilder();
		Document doc = parser.build("/home/m1/programming/java/jPokerTreeView/data/simple_tree.xml");
		Element data = doc.getRootElement().getChildren().get(0);
		root = convert(data);
		root.setChildren(convert(data.getChildren()));
		Tree<String> tree = new Tree<String>(root);
		return tree;
	}
	
	private static List<Node<String>> convert(List<Element> data){
		if (data == null || data.isEmpty()){
			return null;
		}
		List<Node<String>> result = new LinkedList<Node<String>>();
		
		for (Element el : data){
			result.add(convert(el));
		}
		return result;
	}
	
	private static NodeMetaInfo<String> convert(Element data){
		NodeMetaInfo<String> node = new NodeMetaInfo<String>();
		node.setData(data.getAttributeValue(NAME));
		node.getMetaInfo().setValue(toDouble(data.getAttributeValue(VALUE)));
		node.getMetaInfo().setPlayerNr(toInt(data.getAttributeValue(PLAYER)));
		
		if (!data.getChildren().isEmpty()){
			node.setChildren(convert(data.getChildren()));
		}
		
		return node;
	}
	
	private static Double toDouble(String data){
		return Double.valueOf(data);
	}
	
	private static Integer toInt(String data){
		return Integer.valueOf(data);
	}
}
