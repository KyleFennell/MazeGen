import java.util.Random;
import java.awt.image.BufferedImage;

public class MazeGenerator{

	private int width, height;
	private boolean[][][] maze;	//x,y,path direction
	private boolean[][] visited;
	private Random random = new Random();

	public MazeGenerator(int width, int height){
		this.width = width;
		this.height = height;
		maze = new boolean[height][width][4];
		visited = new boolean[height][width];
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				for (int k = 0; k < 4; k++){
					maze[i][j][k] = false;
				}
				visited[i][j] = false;
			}
		}
	}

	public void generate(int choice, int deadEndNumber){
		switch (choice){
			case 0: recursive(0, 0, -1);
		}
		if (deadEndNumber == 1){
			removeDeadEnds();
		}
	}

	private void recursive(int y, int x, int cameFrom){
		visited[y][x] = true;
		// try{
		// 	Thread.sleep(10);
		// }
		// catch(Exception e){}
		// System.out.println(printMaze());
		if (cameFrom != -1){									// -1 means first call
			maze[y][x][cameFrom] = true;	
		}
		while ((y != 0 && !visited[y-1][x]) || (x != 0 && !visited[y][x-1]) || (y != height-1 && !visited[y+1][x]) || (x != width-1 && !visited[y][x+1])){		// a neighbour exists that hasnt been visited
			switch (random.nextInt()%4){						//pick a random neighbor disregarding if it has been visited or not (innefficient but less code)
				case 0: if (y != 0 && !visited[y-1][x]){		// west
					maze[y][x][0] = true;						// make a path
					recursive(y-1, x, 2);						// recurse and say where you came from
				}
				break;
				case 3: if (x != 0 && !visited[y][x-1]){		// north
					maze[y][x][3] = true;
					recursive(y, x-1, 1);
				}
				break;
				case 2: if (y != height-1 && !visited[y+1][x]){	// east
					maze[y][x][2] = true;
					recursive(y+1, x, 0);
				}
				break;
				case 1: if (x != width-1 && !visited[y][x+1]){	// south
					maze[y][x][1] = true;
					recursive(y, x+1, 3);
				}
			}
		}
	}

	private void removeDeadEnds(){
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				while (openingCount(y, x) < 2){
					switch (random.nextInt()%4){						//pick a random neighbor disregarding if it has been visited or not (innefficient but less code)
						case 0: if (y != 0){		// west
							maze[y][x][0] = true;						// make a path
							maze[y-1][x][2] = true;
						}
						break;
						case 1: if (x != width-1){	// south
							maze[y][x][1] = true;
							maze[y][x+1][3] = true;
						}
						break;
						case 2: if (y != height-1){	// east
							maze[y][x][2] = true;
							maze[y+1][x][0] = true;
						}
						break;
						case 3: if (x != 0){		// north
							maze[y][x][3] = true;
							maze[y][x-1][1] = true;
						}
					}
				}
			}
		}
	}

	private int openingCount(int y, int x){
		int openings = 0;
		for (int i = 0; i < 4; i++){
			if (maze[y][x][i])
				openings++;
		}
		return openings;
	}

	public void printdebugmaze(){
		String out = "";
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				for (int k = 0; k < 4; k++){
					out += (maze[i][j][k])?0:1;
				}
				out += " ";
			}
			out += "\n";
		}
		System.out.println(out);
	}

	public String printMaze(){
		String out = "";
		for (int i = -1; i < height*2; i++){
			for (int j = -1; j < width*2; j++){
				if (i == -1 || j == -1 || i == height*2-1 || j == width*2-1){
					out += "#";
				}
				else if (i%2 == 0 && j%2 == 0){
					out += " ";
				}
				else if (i%2 == 1 && j%2 == 1){
					out += "#";
				}
				else if (j%2 == 1){
					out += (maze[i/2][(j-1)/2][1])?" ":"#";
				}
				else if (i%2 == 1){
					out += (maze[(i-1)/2][j/2][2])?" ":"#";
				}
				else {
					out += ".";
				}
				out += " ";
			}
			out += "\n";
		}
		return out;
	}

	public String printMazeWithSolution(Graph<Point> solution){
		String out = "";
		for (int i = -1; i < height*2; i++){
			for (int j = -1; j < width*2; j++){
				if (i == -1 || j == -1 || i == height*2-1 || j == width*2-1){
					out += "#";
				}
				else if (i%2 == 0 && j%2 == 0){
					out += (solution.get(new Point(j/2, i/2)) != null)?".":" ";
				}
				else if (i%2 == 1 && j%2 == 1){
					out += "#";
				}
				else if (j%2 == 1){
					if (solution.get(new Point((j-1)/2, i/2)) != null && solution.get(new Point((j-1)/2+1, (i/2))) != null && maze[i/2][(j-1)/2][1]){
						out += ".";
					}
					else{
						out += (maze[i/2][(j-1)/2][1])?" ":"#";
					}
				}
				else if (i%2 == 1){
					if (solution.get(new Point(j/2, (i-1)/2)) != null && solution.get(new Point((j/2), (i-1)/2+1)) != null && maze[(i-1)/2][j/2][2]){
						out += ".";
					}
					else{
						out += (maze[(i-1)/2][j/2][2])?" ":"#";
					}
				}
				else {
					out += ".";
				}
				out += " ";
			}
			out += "\n";
		}
		return out;
	}

	public BufferedImage toImage(Graph<Point> solution){
		BufferedImage img = new BufferedImage(width*2+1, height*2+1, BufferedImage.TYPE_INT_RGB);
		int w = (255<<16)|(255<<8)|255;
		int b = (0<<16)|(0<<8)|0;
		int c = (255<<16)|(0<<8)|0;
		for (int i = -1; i < height*2; i++){
			for (int j = -1; j < width*2; j++){
				if (i == -1 || j == -1 || i == height*2-1 || j == width*2-1){
					img.setRGB(j+1, i+1, b);
				}
				else if (i%2 == 0 && j%2 == 0){
					img.setRGB(j+1, i+1, (solution.get(new Point(j/2, i/2)) != null)?c:w);
				}
				else if (i%2 == 1 && j%2 == 1){
					img.setRGB(j+1, i+1, b);
				}
				else if (j%2 == 1){
					if (solution.get(new Point((j-1)/2, i/2)) != null && solution.get(new Point((j-1)/2+1, (i/2))) != null && maze[i/2][(j-1)/2][1]){
						img.setRGB(j+1, i+1, c);
					}
					else{
						img.setRGB(j+1, i+1, (maze[i/2][(j-1)/2][1])?w:b);
					}
				}
				else if (i%2 == 1){
					if (solution.get(new Point(j/2, (i-1)/2)) != null && solution.get(new Point((j/2), (i-1)/2+1)) != null && maze[(i-1)/2][j/2][2]){
						img.setRGB(j+1, i+1, c);
					}
					else{
						img.setRGB(j+1, i+1, (maze[(i-1)/2][j/2][2])?w:b);
					}
				}
			}
		}
		return img;
	}

	public Graph fullToGraph(){
		Graph<Point> mazeGraph = new Graph<Point>();
		Point[][] pointMaze = new Point[height][width];
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				pointMaze[y][x] = new Point(x, y);
				mazeGraph.add(pointMaze[y][x]);
			}
		}
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				if (maze[y][x][0] && y != 0){
					mazeGraph.connect(pointMaze[y][x], pointMaze[y-1][x], 1);
				}
				if (maze[y][x][1] && x != width-1){
					mazeGraph.connect(pointMaze[y][x], pointMaze[y][x+1], 1);
				}
				if (maze[y][x][2] && y != height-1){
					mazeGraph.connect(pointMaze[y][x], pointMaze[y+1][x], 1);
				}
				if (maze[y][x][3] && x != 0){
					mazeGraph.connect(pointMaze[y][x], pointMaze[y][x-1], 1);
				}
			}
		}
		return mazeGraph;
	}
}