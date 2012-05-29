package mi.poker.common.model.struct;

import java.util.LinkedList;
import java.util.List;

public class Node<T>  {
	
	private T data;
	private Node<T> parent;
	private List<Node <T> > children = new LinkedList<Node<T>>();
	
	public Node(){
	}
	
	public Node(T data){
		this.data = data;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public Node<T> getParent() {
		return parent;
	}
	
	public void appendChild(Node<T> child){
		child.setParent(this);
		children.add(child);
	}
	
	public void setParent(Node<T> parent) {
		this.parent = parent;
	}
	
	public List<Node<T>> getChildren() {
		return children;
	}
	
	public Node<T> getLastChild(){
		return children.isEmpty() ? null : children.get(children.size()-1);
	}
	
	public Node<T> getFirstChild(){
		return children.isEmpty() ? null : children.get(0);
	}
	
	public void setChildren(List<Node<T>> children) {
		if (children != null){
			for (Node<T> child : children){
				child.setParent(this);
			}
		}
		this.children = children;
	}
	
	public boolean hasChildren(){
		return !children.isEmpty();
	}

	@Override
	public String toString(){
		return data.toString();
	}
}
