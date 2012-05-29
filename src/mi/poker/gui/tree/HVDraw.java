package mi.poker.gui.tree;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import mi.poker.common.model.struct.Node;
import mi.poker.common.model.struct.Tree;
import mi.poker.gui.tree.struct.NodeMetaInfo;
import mi.poker.gui.tree.struct.TreeSize;
import mi.poker.gui.tree.struct.TreeXY;

/**
 * The Right-Heavy-HV-Tree-Draw can be used general
 * trees. In this approach, a divide-and-conquer strategy is used to recursively
 * construct an upward, ordthogonal, and straight-line drawing of a tree, by
 * placing the root of the tree in the top-left corner subtrees one next to the other
 */
public class HVDraw<T> extends SimpleDraw<T> {

	@Override
	protected TreeXY<Node<T>> preprocess(Tree<T> tree) {
		TreeXY<Node<T>> treeXY = convert(tree);

		NodeMetaInfo<Node<T>> root = treeXY.getRootXY();
		root.setX(0);
		root.setY(0);
		initSizes(root);
		setCords(root);
		return treeXY;
	}
	
	private void setCords(NodeMetaInfo<Node<T>> node){
		int currentX = node.getX();
		int currentY = node.getY();
		for (Node<Node<T>> child : node.getChildren()) {
			NodeMetaInfo<Node<T>> meta = (NodeMetaInfo<Node<T>>)child;
			meta.setY(currentY+1);
			meta.setX(currentX);
			currentX += meta.getMetaInfo().getTreeSize().getWidth();
			setCords(meta);
		}
	}
	
	private void initSizes(NodeMetaInfo<Node<T>> node){
		node.getMetaInfo().setTreeSize(getSize(node));
		for (Node<Node<T>> child : node.getChildren()) {
			initSizes((NodeMetaInfo<Node<T>>) child);
		}
	}

	private TreeSize getSize(NodeMetaInfo<Node<T>> info) {
		TreeSize t = new TreeSize();
		t.setWidth(getWidth(info));
		t.setHeight(getHeight(info));
		return t;
	}

	private int getWidth(Node<Node<T>> node) {
		int width = 0;
		if (node.getChildren().size() <= 1)
			return 1;
		for (Node<Node<T>> child : node.getChildren()) {
			width += getWidth(child);
		}
		return width;
	}

	private int getHeight(Node<Node<T>> node) {
		if (node == null || node.getChildren().isEmpty()) {
			return 0;
		}
		List<Integer> heights = new LinkedList<Integer>();

		for (Node<Node<T>> child : node.getChildren()) {
			heights.add(getHeight(child));
		}
		return Collections.max(heights) + 1;
	}

}
