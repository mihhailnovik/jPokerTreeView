package mi.poker.gui.tree;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static mi.poker.gui.tree.DrawProperties.*;
import javax.swing.JFrame;

import mi.poker.common.model.struct.Tree;

import org.jdom2.JDOMException;

/**
 * @author Mihhail 'm1' Novik
 */

public class Main {
	// java - jar TreeVisualizer.jar --method=simple_layer --file=data.xml --node_width=25 --node_height=25
	public static void main(String[] args) throws IOException, JDOMException {
		
		Map<String, String> propertiesMap =  readProperties(args);
		if (!propertiesMap.containsKey("method") || !propertiesMap.containsKey("file")){
			System.out.println("Miss properties (file | method)");
			System.out.println("java -jar TreeVisualizer.jar --method=.. --file=..");
			System.out.println("Availible methods: simple_layer, improved_layer, radial, hv_draw");
			System.exit(0);
		}
		System.out.println("Drawing properties "+propertiesMap);
		Tree<String> data = TreeBuilder.buildPokerTree(propertiesMap.get("file"));
		TreeViewerPanel panel = new TreeViewerPanel(data);
		TreePainter<String> painter = null;
		if (propertiesMap.get("method").equals("simple_layer")) {
			painter = new SimpleDraw<String>();
		}
		if (propertiesMap.get("method").equals("improved_layer")) {
			painter = new ImprovedLayerTreeDraw<String>();
		}
		if (propertiesMap.get("method").equals("radial")) {
			painter = new RadialDraw<String>();
		}
		if (propertiesMap.get("method").equals("hv_draw")) {
			painter = new HVDraw<String>();
		}
		if (propertiesMap.get("method").equals("treemap")) {
			painter = new TreeMapDraw<String>();
		}
		if (propertiesMap.containsKey("node_width")){
			NODE_WIDHT = Integer.valueOf(propertiesMap.get("node_width"));
		}
		if (propertiesMap.containsKey("node_height")){
			NODE_HEIGHT = Integer.valueOf(propertiesMap.get("node_height"));
		}
		System.out.println("Processing tree...");
		panel.setPainter(painter);
		MainFrame m = new MainFrame();
		System.out.println("Drawing...");
		m.setContentPane(panel);
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.setSize(1024, 768);
		m.setLocation(100, 100);
		m.setVisible(true);
		System.out.println("Finished!");
	}
	
	private static Map<String, String> readProperties(String[] args){
		Map<String, String> map = new HashMap<String, String>();
		
		for (String s : args){
			s = s.replaceAll("--", "");
			map.put(s.split("=")[0],s.split("=")[1]);
		}
		return map;
	}
	
}
