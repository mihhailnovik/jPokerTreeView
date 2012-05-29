package mi.poker.gui.tree;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import static mi.poker.gui.tree.DrawProperties.*;
import javax.imageio.ImageIO;

import mi.poker.common.model.struct.Node;
import mi.poker.common.model.struct.Tree;
import mi.poker.gui.tree.struct.NodeMetaInfo;
import mi.poker.gui.tree.struct.TreeXY;

/**
 * @author m1 
 * Disadvantages Much wider than necessary Parent is not centered
 * with respect to children
 */
public class SimpleDraw<T> implements TreePainter<T> {

	private static Image player1;
	private static Image player2;
	private static Image root;

	static {
		try {
			player1 = ImageIO.read(new File("img/p1.png"));
			player2 = ImageIO.read(new File("img/p2.png"));
			root = ImageIO.read(new File("img/root.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void drawTree(Graphics2D g, Tree<T> tree) {
		TreeXY<Node<T>> treeXY = preprocess(tree);
		NodeMetaInfo<T> root = (NodeMetaInfo<T>) treeXY.getRoot();
		drawNode(g, (NodeMetaInfo<T>) root);
	}

	protected void drawPlayer(NodeMetaInfo<T> nodeXY, Graphics2D g) {
		// image
		if (nodeXY.getMetaInfo().getPlayerNr() == 0) {
			g.drawImage(root, xCord(nodeXY.getX()) + (int) (NODE_WIDHT * 0.12),
					yCord(nodeXY.getY()) + (int) (NODE_HEIGHT * 0.12),
					(int) (NODE_WIDHT * 0.75), (int) (NODE_HEIGHT * 0.75), null);
		}

		if (nodeXY.getMetaInfo().getPlayerNr() == 1) {
			g.drawImage(player1, xCord(nodeXY.getX())
					+ (int) (NODE_WIDHT * 0.12), yCord(nodeXY.getY())
					+ (int) (NODE_HEIGHT * 0.12), (int) (NODE_WIDHT * 0.75),
					(int) (NODE_HEIGHT * 0.75), null);
		}

		if (nodeXY.getMetaInfo().getPlayerNr() == 2) {
			g.drawImage(player2, xCord(nodeXY.getX())
					+ (int) (NODE_WIDHT * 0.12), yCord(nodeXY.getY())
					+ (int) (NODE_HEIGHT * 0.12), (int) (NODE_WIDHT * 0.75),
					(int) (NODE_HEIGHT * 0.75), null);
		}
		// value
		Font font = new Font("Arial", Font.PLAIN, 5);
		g.setFont(font);
		
		if (nodeXY.getMetaInfo().getValue() > 0){
			g.setColor(Color.BLUE);
		} else {
			g.setColor(Color.RED);
		}
		g.drawString(String.valueOf(nodeXY.getMetaInfo().getValue()),
				xCord(nodeXY.getX()), yCord(nodeXY.getY()));
		
		g.setColor(Color.DARK_GRAY);
		g.drawString(nodeXY.getData().toString(), xCord(nodeXY.getX()),
				yCord(nodeXY.getY()) + (int) (NODE_HEIGHT + NODE_HEIGHT * 0.1));

	}

	protected void drawNode(Graphics2D g, NodeMetaInfo<T> nodeXY) {
		drawPlayer(nodeXY, g);
		for (Node<T> n : nodeXY.getChildren()) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine(xCord(nodeXY.getX())+ (int) (NODE_WIDHT * 0.2), yCord(nodeXY.getY())
					+ (int) (NODE_HEIGHT + NODE_HEIGHT * 0.1),
					xCord(((NodeMetaInfo<T>) n).getX())+ (int) (NODE_WIDHT * 0.2),
					yCord(((NodeMetaInfo<T>) n).getY())
							+ (int) (NODE_HEIGHT + NODE_HEIGHT * 0.1)); // edge
			drawNode(g, (NodeMetaInfo<T>) n);
		}
	}

	protected int xCord(int x) {
		return DEFAULT_X + x * SIB_SPACE;
	}
	
	protected int yCord(int y) {
		return DEFAULT_Y + y * LAYER_SPACE;
	}

	protected TreeXY<Node<T>> convert(Tree<T> tree) {
		TreeXY<Node<T>> treeXY = new TreeXY<Node<T>>();
		treeXY.setRoot(convert(tree.getRoot()));
		return treeXY;
	}

	@SuppressWarnings("unchecked")
	private NodeMetaInfo<Node<T>> convert(Node<T> node) {
		if (node instanceof NodeMetaInfo) {
			return (NodeMetaInfo<Node<T>>) node;
		}

		NodeMetaInfo<Node<T>> nodeXY = new NodeMetaInfo<Node<T>>();
		if (node.hasChildren()) {
			List<Node<Node<T>>> l = new LinkedList<Node<Node<T>>>();
			for (Node<T> n : node.getChildren()) {
				l.add(convert(n));
			}
			nodeXY.setChildren(l);
		}
		nodeXY.setData(node);
		return nodeXY;
	}

	protected TreeXY<Node<T>> preprocess(Tree<T> tree) {
		TreeXY<Node<T>> treeXY = convert(tree);
		// set all Y's
		setLayerNr(treeXY.getRootXY(), 1);
		// set all X's
		inOrderTraversal(treeXY.getRootXY(), new Counter());
		
		return treeXY;
	}

	// represent y
	protected void setLayerNr(NodeMetaInfo<Node<T>> node, int currentY) {
		node.setY(currentY);
		if (node.hasChildren()) {
			for (Node<Node<T>> n : node.getChildren()) {
				setLayerNr((NodeMetaInfo<Node<T>>) n, currentY + 1);
			}
		}
	}

	// represent x
	protected void inOrderTraversal(NodeMetaInfo<Node<T>> nodeXY, Counter x) {
		if (nodeXY == null) {
			return;
		}
		int nrOfChildren = nodeXY.getChildren().size();
		for (int i = 0; i < nrOfChildren / 2; i++) {
			inOrderTraversal((NodeMetaInfo<Node<T>>) nodeXY.getChildren()
					.get(i), x);
		}
		x.increase();
		nodeXY.setX(x.getCounter());
		for (int i = nrOfChildren / 2; i < nrOfChildren; i++) {
			inOrderTraversal((NodeMetaInfo<Node<T>>) nodeXY.getChildren()
					.get(i), x);
		}
	}

	protected static class Counter {
		int counter = 0;

		public void increase() {
			counter++;
		}

		public int getCounter() {
			return counter;
		}
	}
}
