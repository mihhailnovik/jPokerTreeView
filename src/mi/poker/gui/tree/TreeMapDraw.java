package mi.poker.gui.tree;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import mi.poker.common.model.struct.Node;
import mi.poker.common.model.struct.Tree;
import mi.poker.gui.tree.struct.NodeMetaInfo;
import mi.poker.gui.tree.struct.TreeSize;
import mi.poker.gui.tree.struct.TreeXY;
import static mi.poker.gui.tree.DrawProperties.*;

public class TreeMapDraw<T> extends SimpleDraw<T> {

	@SuppressWarnings("unchecked")
	@Override
	public void drawTree(Graphics2D g, Tree<T> tree) {
		TreeXY<Node<T>> treeXY = preprocess(tree);
		NodeMetaInfo<T> root = (NodeMetaInfo<T>) treeXY.getRoot();
		drawNode(g, (NodeMetaInfo<T>) root);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected TreeXY<Node<T>> preprocess(Tree<T> tree) {
		TreeXY<Node<T>> treeXY = convert(tree);
		NodeMetaInfo<T> root = (NodeMetaInfo<T>) treeXY.getRoot();
		root.setX(0);
		root.setY(0);
		TreeSize treeSize = new TreeSize();
		treeSize.setHeight(IMAGE_HEIGHT);
		treeSize.setWidth(IMAGE_WIDTH);
		root.getMetaInfo().setTreeSize(treeSize);
		initSizes((NodeMetaInfo<Node<T>>) root, true);
		return treeXY;
	}

	@Override
	protected void drawNode(Graphics2D g, NodeMetaInfo<T> nodeXY) {
		if (nodeXY.getMetaInfo().getPlayerNr() == 0){
			g.setColor(Color.WHITE);
		}
		if (nodeXY.getMetaInfo().getPlayerNr() == 1){
			g.setColor(Color.LIGHT_GRAY);
		}
		if (nodeXY.getMetaInfo().getPlayerNr() == 2){
			g.setColor(Color.RED);
		}
		
		g.fillRect(nodeXY.getX(), nodeXY.getY(), nodeXY.getMetaInfo().getTreeSize().getWidth(), 
				nodeXY.getMetaInfo().getTreeSize().getHeight());
		g.setColor(Color.BLACK);
		g.drawRect(nodeXY.getX(), nodeXY.getY(), nodeXY.getMetaInfo().getTreeSize().getWidth(), 
				nodeXY.getMetaInfo().getTreeSize().getHeight());
		
		Font font = new Font("Arial", Font.PLAIN, 7);
		g.setFont(font);
		
		if (nodeXY.getMetaInfo().getTreeSize().getWidth() > 70) {
			g.drawString(nodeXY.getData().toString(), nodeXY.getX()+7, nodeXY.getY()+13);
		}
		
		if (nodeXY.getMetaInfo().getTreeSize().getWidth() < 70 && nodeXY.getMetaInfo().getTreeSize().getWidth() > 50) {
			g.drawString(nodeXY.getData().toString().substring(0, 6), nodeXY.getX()+7, nodeXY.getY()+13);
		}
		for (Node<T> n : nodeXY.getChildren()) {
			drawNode(g, (NodeMetaInfo<T>) n);
		}
	}
	
	private void initSizes(NodeMetaInfo<Node<T>> node, boolean vertical){
		if (node.hasChildren()) {
			int counter = 0;
			int zoneWidth = node.getMetaInfo().getTreeSize().getWidth() / node.getChildren().size();
			int zoneHeight = node.getMetaInfo().getTreeSize().getHeight() / node.getChildren().size();
			for (Node<Node<T>> n : node.getChildren()) {
				NodeMetaInfo<Node<T>> child = (NodeMetaInfo<Node<T>>) n;
				TreeSize treeSize = new TreeSize();
				if (vertical){
					child.setX(node.getX() + counter * zoneWidth);
					child.setY(node.getY());
					treeSize.setHeight(node.getMetaInfo().getTreeSize().getHeight());
					treeSize.setWidth(zoneWidth);
				} else {
					child.setY(node.getY() + counter * zoneHeight);
					child.setX(node.getX());
					treeSize.setHeight(zoneHeight);
					treeSize.setWidth(node.getMetaInfo().getTreeSize().getWidth());
				}
				if (counter == 0 || vertical){
					child.setY(child.getY() + 15);
					treeSize.setHeight(treeSize.getHeight() - 15);
				}
				child.getMetaInfo().setTreeSize(treeSize);
				initSizes(child, !vertical);
				counter++;
			}
		}
	}
}
