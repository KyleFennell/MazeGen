import java.lang.Math;

public class Point{
	
	private int x, y;

	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int x(){
		return x;
	}

	public int y(){
		return y;
	}

	public double distanceFrom(Point p){
		return Math.pow((Math.pow(x - p.x(), 2) + Math.pow(y - p.y(), 2)), 0.5);
	}

	public boolean equals(Object o){
		return (this.x == ((Point)o).x() && this.y == ((Point)o).y());
	}

	public String toString(){
		String out = x +" "+ y;
		return out;
	}
}