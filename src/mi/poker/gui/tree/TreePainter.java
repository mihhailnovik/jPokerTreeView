package mi.poker.gui.tree;

import java.awt.Graphics2D;


import mi.poker.common.model.struct.Tree;

public interface TreePainter<T> {
	public void drawTree(final Graphics2D g, Tree<T> tree);
	
	
}
