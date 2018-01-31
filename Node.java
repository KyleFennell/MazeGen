import java.util.ArrayList;

public class Node<E>{

	private ArrayList<Edge<E>> edges;
	private E data;

	public Node(E data){
		this.data = data;
		edges = new ArrayList<Edge<E>>();
	}

	public E data(){
		return data;
	}

	public void connectTo(Node<E> n, double weight){
		edges.add(new Edge<E>(this, n, weight));
	}

	public ArrayList<Edge<E>> edges(){
		return edges;
	}

	public boolean equals(Object o){
		return (this.data().equals(((Node)o).data()));
	}

	public String toString(){
		return data.toString();
	}
}