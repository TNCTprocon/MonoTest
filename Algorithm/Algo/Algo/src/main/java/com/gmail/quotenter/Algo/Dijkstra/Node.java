package com.gmail.quotenter.Algo.Dijkstra;

import java.awt.Point;

public class Node {

	int manhattanDistanceSum;
	
	int[][] face;
	Puzzle puzzle = null;
	public static int WID = 0;
	public static int HEI = 0;
	
	public Node(int[][] puzzle, int width, int height) {
		this.face = puzzle;
		this.WID = width;
		this.HEI = height;
		this.manhattanDistanceSum = -1;
	}
	
	int getManhattanDistanceSum() {
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
		manhattanDistanceSum = sum;
		return manhattanDistanceSum;
	}
	
	public int[][] getFace() {
		return face;
	}
	
	public Point getHole() {
		for(int i = 0; i < HEI; i++) {
			for(int j = 0; j < WID; j++) {
				if(face[i][j] == 0) return new Point(i, j);
			}
		}
		return null;
	}
}
