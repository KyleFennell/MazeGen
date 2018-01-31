import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Maze{

	long startTime = System.currentTimeMillis();
	
	public static void main(String[] args){

		Maze m = new Maze();
		
		int width = Integer.parseInt(args[0]);
		int height = Integer.parseInt(args[1]);
		int alg = Integer.parseInt(args[2]);
		int deadends = Integer.parseInt(args[3]);

		m.run(width, height, alg, deadends);		
		// gen.printdebugmaze();
	}

	public void run(int width, int height, int alg, int deadends){

		MazeGenerator gen = new MazeGenerator(width, height);
		gen.generate(alg, deadends);
		// System.out.println(gen.printMaze());
		System.out.println("Maze created");
		System.out.println(System.currentTimeMillis() - startTime);
		Graph maze = gen.fullToGraph();
		System.out.println("Graph constructed");
		System.out.println(System.currentTimeMillis() - startTime);
		Solver solver = new Solver(gen);
		Graph solution = solver.aStar(maze.get(new Point(0,0)), maze.get(new Point(width-1, height-1)));
		System.out.println("Solution found");
		// System.out.println(System.currentTimeMillis() - startTime);
		System.out.println(gen.printMazeWithSolution(solution));
		// toFile(gen, new Graph(), "maze");
		// toFile(gen, solution, "solution");
		// System.out.println("file created");
		System.out.println(System.currentTimeMillis() - startTime);
	}

	public void toFile(MazeGenerator gen, Graph solution, String fileName){
		try{
			File f = new File(fileName+".png");
			ImageIO.write(gen.toImage(solution), "png", f);
		}
		catch(IOException e){
			System.out.println("Error: "+e);
		}
	}
}