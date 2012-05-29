package mi.poker.common.model.struct;

public class Tree<T>  {

	private Node<T> root;
	
	public Tree(){
	}
	
	public Tree(Node<T> root){
		this.root = root;
	}

	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}
	 

}
