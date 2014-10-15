package com.gmail.quotenter.Algo.Dijkstra;

import java.awt.Point;

import junit.runner.Sorter.Swapper;

public class Node {

	int manhattanDistanceSum;
	
	public int[][] face;
	public int[][] nextFace;
	public Point holePoint;
	
	Puzzle puzzle = null;
	
	public static int WID = 0;
	public static int HEI = 0;
	
	public Node(int[][] puzzle, int width, int height) {
		this.face = puzzle;
		this.WID = width;
		this.HEI = height;
		this.nextFace = null;
		this.manhattanDistanceSum = -1;
		this.holePoint = getHole();
	}
	
	// 次局面取得
	public int[][] getNextFace(int dir) {
		return getSwappedFace(dir);
	}
	
	// マンハッタン距離総和
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
	
	// 面取得
	public int[][] getFace() {
		return face;
	}
	
	// 穴探索
	public Point getHole() {
		for(int i = 0; i < HEI; i++) {
			for(int j = 0; j < WID; j++) {
				if(face[i][j] == 0) return new Point(i, j);
			}
		}
		return null;
	}
	
	// パネル入れ替え
	/*
	 * 0 : 上
	 * 1 : 下
	 * 2 : 左
	 * 3 : 右
	 */
	public int[][] getSwappedFace(int dir) {
		Point tmpPoint = holePoint;
		
		switch (dir) {
		case 0:
			tmpPoint.x--;
			return getSwappedFace(holePoint, tmpPoint);
		case 1:
			tmpPoint.x++;
			return getSwappedFace(holePoint, tmpPoint);
		case 2:
			tmpPoint.y--;
			return getSwappedFace(holePoint, tmpPoint);
		case 3:
			tmpPoint.y++;
			return getSwappedFace(holePoint, tmpPoint);
		default:
			return null;
		}
	}
	public int[][] getSwappedFace(Point a, Point b) {
		int[][] tmpFace = face;
		int tmp = tmpFace[a.x][a.y];
		
		try {
			tmpFace[a.x][a.y] = tmpFace[b.x][b.y];
			tmpFace[b.x][b.y] = tmp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmpFace;
	}
	
}
