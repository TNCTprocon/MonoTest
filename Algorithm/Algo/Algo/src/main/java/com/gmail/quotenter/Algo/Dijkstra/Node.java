package com.gmail.quotenter.Algo.Dijkstra;

public class Node {

	int manhattanDistance;
	
	int[][] face;
	Puzzle puzzle = null;
	public static int WID = 0;
	public static int HEI = 0;
	
	public Node(int[][] puzzle, int width, int height) {
		this.face = puzzle;
		this.WID = width;
		this.HEI = height;
		this.manhattanDistance = -1;
	}
	
	int getManhattanDistance() {
		int sum = 0;
		int orgX = 0;
		int orgY = 0;
		int md = 0;
		
		// i と orgYは列
		// j と orgXは行
		
		for(int i = 0; i < HEI; i++) {
			for(int j = 0; j < WID; j++) {
				orgX = face[i][j] % WID;
				orgY = face[i][j] / WID;
				md = Math.abs(orgX - j) + Math.abs(orgY - i);
				sum = sum + md;
				
				System.out.printf("[MD] %2d org[%2d][%2d], now[%2d][%2d], D =%3d, [SUM:%3d]\n",
						face[i][j],
						orgY, orgX,
						i, j,
						md,
						sum
						);
			}
		}
		
		
		return -1;
	}
	
	void printFace() {
		for(int i = 0; i < HEI; i++) {
			for(int j = 0; j < WID; j++) {
				System.out.printf("%4d", face[i][j]);
				if(j == WID - 1) System.out.println();
			}
		}
	}
}
