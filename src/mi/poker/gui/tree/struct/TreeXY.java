package mi.poker.gui.tree.struct;

import mi.poker.common.model.struct.Tree;

public class TreeXY<T> extends Tree<T>{
	
	public NodeMetaInfo<T> getRootXY(){
		return (NodeMetaInfo<T>) getRoot();
	}
	
}
