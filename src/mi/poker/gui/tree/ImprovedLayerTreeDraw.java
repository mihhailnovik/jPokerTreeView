package mi.poker.gui.tree;

import java.util.List;

import mi.poker.common.model.struct.Node;
import mi.poker.common.model.struct.Tree;
import mi.poker.gui.tree.SimpleDraw;
import mi.poker.gui.tree.struct.NodeMetaInfo;
import mi.poker.gui.tree.struct.TreeXY;

/**
 *  Compute the left and right contour of vertex v: 
	scan the right contour of the left subtree (T’) and the left contour of the right subtree (T’’ )
	accumulate the displacements of the vertices on the left & right contour
	keep the max cumulative displacement at any depth
 *  @author m1
 */
public class ImprovedLayerTreeDraw<T> extends SimpleDraw<T> {

	@Override
	protected TreeXY<Node<T>> preprocess(Tree<T> tree) {
		TreeXY<Node<T>> treeXY = convert(tree);
		// set all Y's
		setLayerNr(treeXY.getRootXY(), 1);
		// set all X's
		inOrderTraversal(treeXY.getRootXY(), new Counter());
		move(treeXY.getRootXY(), treeXY.getRootXY());
		move(treeXY.getRootXY(), (NodeMetaInfo<Node<T>>) treeXY.getRootXY().getFirstChild());
		return treeXY;
	}
	
	private void move(NodeMetaInfo<Node<T>> root, NodeMetaInfo<Node<T>> node) {
		moveParent(node);
		int distance = getDistance(node.getFirstChild(), node.getLastChild());
		moveRight((NodeMetaInfo<Node<T>>) node.getFirstChild(),distance);
		moveParent(node);
		moveLeft(root, distance);
	}

	private void moveParent(NodeMetaInfo<Node<T>> parent) {
		List<Node<Node<T>>> children = parent.getChildren();
		int sum = 0;
		if (children != null && !children.isEmpty()) {
			for (Node<Node<T>> child : children) {
				moveParent((NodeMetaInfo<Node<T>>) child);
				sum += ((NodeMetaInfo<Node<T>>) child).getX();
			}
			parent.setX(sum / children.size());
		}
	}
	
	private void moveRight(NodeMetaInfo<Node<T>> node, int distance) {
		node.setX(node.getX() + distance);
		for (Node<Node<T>> child : node.getChildren()) {
			moveRight((NodeMetaInfo<Node<T>>) child, distance);
		}
	}
	
	private void moveLeft(NodeMetaInfo<Node<T>> node, int distance) {
		node.setX(node.getX() - distance);
		for (Node<Node<T>> child : node.getChildren()) {
			moveLeft((NodeMetaInfo<Node<T>>) child, distance);
		}
	}

	private int getDistance(Node<Node<T>> node, Node<Node<T>> node2) {
		int minDistance = 0;
		while (node.getLastChild() != null
				&& node2.getFirstChild() != null) {
			node = node.getLastChild();
			node2 = node2.getFirstChild();

			NodeMetaInfo<Node<T>> leftNodeXY = (NodeMetaInfo<Node<T>>) node;
			NodeMetaInfo<Node<T>> rightNodeXY = (NodeMetaInfo<Node<T>>) node2;

			if (minDistance < rightNodeXY.getX() - leftNodeXY.getX()) {
				minDistance = rightNodeXY.getX() - leftNodeXY.getX();
			}
		}
		return minDistance - 2;
	}

}
