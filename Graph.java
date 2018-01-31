import java.util.LinkedList;

public class Graph<E>{

	private LinkedList<Node<E>> nodes;

	public Graph(){
		nodes = new LinkedList<Node<E>>();
	}

	public void add(E data){
		nodes.add(new Node<E>(data));
	}

	public void add(Node<E> node){
		nodes.add(node);
	}

	public Node<E> get(E data){
		if (nodes.indexOf(new Node<E>(data)) != -1){
			return nodes.get(nodes.indexOf(new Node(data)));
		}
		return null;
	}

	public void connect(E a, E b, double weight){
		get(a).connectTo(get(b), weight);
		get(b).connectTo(get(a), weight);
	}

	public String toString(){
		String out = "";
		for (Node<E> n : nodes){
			out += n.data() +" ";
		}
		return out;
	}
}