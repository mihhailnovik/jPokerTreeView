package mi.poker.gui.tree;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import mi.poker.common.model.struct.Tree;

public class TreeViewerPanel extends JPanel {
	private static final long serialVersionUID = 3481666712208091838L;
	private Tree<String> treeModel;
	private Image background;
	private TreePainter<String> painter;

	public TreeViewerPanel(Tree<String> treeModel) {
		this.treeModel = treeModel;
		initializePanel();
	}

	private void initializePanel() {
		try {
			background = ImageIO.read(new File("img/background.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setOpaque(true);
		repaint();
		setSize(800, 800);
	}

	public Tree<String> getTreeModel() {
		return treeModel;
	}

	public void setTreeModel(Tree<String> treeModel) {
		this.treeModel = treeModel;
	}

	@Override
	public void paint(final Graphics g) {
		g.drawImage(background, 0, 0, null);
		update(g);
	}

	@Override
	public void update(final Graphics g) {
		painter.drawTree((Graphics2D) g, treeModel);
	}
	
	public TreePainter<String> getPainter() {
		return painter;
	}

	public void setPainter(TreePainter<String> painter) {
		this.painter = painter;
	}

}