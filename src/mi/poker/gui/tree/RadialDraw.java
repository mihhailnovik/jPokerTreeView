package mi.poker.gui.tree;

import java.awt.Graphics2D;

import mi.poker.common.model.struct.Node;
import mi.poker.common.model.struct.Tree;
import mi.poker.gui.tree.struct.NodeMetaInfo;
import mi.poker.gui.tree.struct.Region;
import mi.poker.gui.tree.struct.TreeXY;

/**
 * Root at the origin Layers are concentric circles centered at the origin
 * Vertices of depth i placed on circle Ci
 */

public class RadialDraw<T> extends SimpleDraw<T> {

	private static int CENTER_X = 300;
	private static int CENTER_Y = 300;
	private static int RING_RADIUS = 65;

	@SuppressWarnings("unchecked")
	@Override
	public void drawTree(Graphics2D g, Tree<T> tree) {
		TreeXY<Node<T>> treeXY = preprocess(tree);
		NodeMetaInfo<T> root = (NodeMetaInfo<T>) treeXY.getRoot();
		drawNode(g, (NodeMetaInfo<T>) root);
	}

	@Override
	protected TreeXY<Node<T>> preprocess(Tree<T> tree) {
		TreeXY<Node<T>> treeXY = convert(tree);

		treeXY.getRootXY().setX(CENTER_X);
		treeXY.getRootXY().setY(CENTER_Y);
		NodeMetaInfo<Node<T>> root = treeXY.getRootXY();
		Region r = new Region();
		r.setFrom(0);
		r.setTo(360);
		root.getMetaInfo().setRegion(r);

		setCircleNr(treeXY.getRootXY(), 1);
		setRegionNr(root);
		return treeXY;
	}

	private void setRegionNr(NodeMetaInfo<Node<T>> parent) {
		if (parent.getChildren() == null || parent.getChildren().isEmpty()) {
			return;
		}
		int childrensNumber = parent.getChildren().size();
		int counter = 0;
		int regionSize = (parent.getMetaInfo().getRegion().getTo()
				- parent.getMetaInfo().getRegion().getFrom()) / childrensNumber;
		if (((NodeMetaInfo<Node<T>>) parent).getMetaInfo().getCirclelvl() == 1) {
			for (Node<Node<T>> child : parent.getChildren()) {
				NodeMetaInfo<Node<T>> metaInfoChild = (NodeMetaInfo<Node<T>>) child;
				Region region = new Region();
				region.setFrom(parent.getMetaInfo().getRegion().getFrom() + counter
						* regionSize);
				counter++;
				region.setTo(parent.getMetaInfo().getRegion().getFrom() + counter
						* regionSize);
				metaInfoChild.getMetaInfo().setRegion(region);
				setXY(metaInfoChild,parent);
				setRegionNr(metaInfoChild);
			}
		} else {
			for (Node<Node<T>> child : parent.getChildren()) {
				NodeMetaInfo<Node<T>> metaInfoChild = (NodeMetaInfo<Node<T>>) child;
				Region region = new Region();
				
				region.setFrom((int) (parent.getMetaInfo().getRegion().getFrom() + counter
						* regionSize * 1.5));
				counter++;
				region.setTo((int) (parent.getMetaInfo().getRegion().getFrom() + counter
						* regionSize * 1.5));
				metaInfoChild.getMetaInfo().setRegion(region);
				setXY(metaInfoChild,parent);
				setRegionNr(metaInfoChild);
			}
		}
	}

	private void setXY(NodeMetaInfo<Node<T>> metaInfoChild, NodeMetaInfo<Node<T>> from) {
		int xi = (int) (Math.cos(2 * Math.PI
				* metaInfoChild.getMetaInfo().getRegion().getTo() / 360)
				* RING_RADIUS + from.getMetaInfo().getX());
		int yi = (int) (Math.sin(2 * Math.PI
				* metaInfoChild.getMetaInfo().getRegion().getTo() / 360)
				* RING_RADIUS + from.getMetaInfo().getY());
		metaInfoChild.setX(xi);
		metaInfoChild.setY(yi);
	}

	private void setCircleNr(NodeMetaInfo<Node<T>> node, int currentLvl) {
		node.getMetaInfo().setCirclelvl(currentLvl);
		if (node.hasChildren()) {
			for (Node<Node<T>> n : node.getChildren()) {
				setCircleNr((NodeMetaInfo<Node<T>>) n, currentLvl + 1);
			}
		}
	}

	@Override
	protected int xCord(int x) {
		return x;
	}

	@Override
	protected int yCord(int y) {
		return y;
	}
}
