public class Edge<E>{

	private Node<E> from, to;
	private double weight;

	public Edge(Node<E> from, Node<E> to, double weight){
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public Node<E> to(){
		return to;
	}

	public double weight(){
		return weight;
	}
}