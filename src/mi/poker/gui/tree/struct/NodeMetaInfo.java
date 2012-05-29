package mi.poker.gui.tree.struct;

import mi.poker.common.model.struct.Node;

public class NodeMetaInfo<T> extends Node<T> {

	private MetaInfo metaInfo = new MetaInfo();

	public int getX() {
		return metaInfo.getX();
	}

	public int getY() {
		return metaInfo.getY();
	}

	public void setX(int x) {
		metaInfo.setX(x);
	}

	public void setY(int y) {
		metaInfo.setY(y);
	}

	public MetaInfo getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(MetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}
	
}
