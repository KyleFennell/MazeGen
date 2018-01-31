import java.util.LinkedList;

public class Solver{

	private MazeGenerator maze;

	public Solver(MazeGenerator maze){
		this.maze = maze;
	}

	public Graph<Point> aStar(Node<Point> start, Node<Point> goal){

		LinkedList<SNode> open = new LinkedList<SNode>();		// nodes that have been visited but not expanded upon
		LinkedList<SNode> closed = new LinkedList<SNode>();		// nodes that have been visited and expanded upon

		SNode first = new SNode(start, null);
		first.g(0);
		first.f(start.data().distanceFrom(goal.data()));
		open.add(first);

		while (!open.isEmpty()){
			SNode current = open.peek();
			for (SNode n : open){
				if (n.compareTo(current) > 0){
					current = n;
				}
			}
			System.out.println(maze.printMazeWithSolution(reconstructPath(current)));
			if (current.data().equals(goal)){
				return reconstructPath(new SNode(goal, current));
			}
			open.remove(current);
			closed.add(current);
			for (Edge<Point> e : current.data().edges()){
				SNode temp = new SNode(e.to(), current);
				if (closed.contains(temp)){
					continue;
				}
				double tempg = current.g() + e.weight();
				if (!open.contains(temp)){
					open.add(temp);
				}
				else {
					SNode t = open.remove(open.indexOf(temp));
					if (tempg >= t.g()){
						open.add(t);
						continue;
					}
				} 
				temp.g(tempg);
				temp.f(temp.g() + temp.data().data().distanceFrom(goal.data()));
			}
		}
		return null;
	}

	private Graph<Point> reconstructPath(SNode goal){
		Graph<Point> out = new Graph<Point>();
		SNode current = goal;
		while (current.cameFrom() != null){
			out.add(current.data());
			current = current.cameFrom();
		}
		out.add(current.data());
		return out;
	}

	private class SNode implements Comparable<SNode>{

		double g, f;		//g == cost to get here, h == euclid dist to goal
		Node<Point> node;
		SNode cameFrom;

		SNode(Node<Point> node, SNode cameFrom){
			this.node = node;
			this.cameFrom = cameFrom;
		}

		public Node<Point> data(){
			return node;
		}

		public void f(double f){
			this.f = f;
		}

		public double f(){
			return f;
		}

		public void g(double g){
			this.g = g;
		}

		public double g(){
			return g;
		}

		public SNode cameFrom(){
			return cameFrom;
		}

		public boolean equals(Object o){
			return (node.equals(((SNode)o).data()));
		}

		public int compareTo(SNode n){
			if (this.f() < n.f()){
				return 1;
			}
			else if (this.f() > n.f()){
				return -1;
			}
			else {
				return 0;
			}
		}

		public String toString(){
			String out = node.data()+" "+g+" "+f;
			return out;
		}

	}
}