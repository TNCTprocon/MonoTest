package com.gmail.quotenter.Algo.Dijkstra;

public class Puzzle {

	public static int WID = 0;
	public static int HEI = 0;
	
	public Puzzle(int width, int height) {
		this.WID = width;
		this.HEI = height;
	}
	
	public void debug(int[][] face) {
		Node node = new Node(face, WID, HEI);
		node.printFace();
		node.getManhattanDistance();
	}
}
