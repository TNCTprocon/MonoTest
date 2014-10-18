package com.gmail.quotenter.Algo.Dij;


import java.awt.Point;
import java.io.Serializable;

import org.apache.commons.lang.SerializationUtils;

@SuppressWarnings("serial")
public class Node implements Serializable {
	public int[][] face;
	public int WID;
	public int HEI;
	public int MDSUM;
	public int cost;
	public boolean done;
	public Point holePoint;
	public Node fromNode;
	
	public Node(int[][] face) {
		this.done = false;
		this.face = (int[][]) SerializationUtils.clone(face); // 与えられた面でノードを初期化
		this.WID = Puzzle.WID;
		this.HEI = Puzzle.HEI;
		this.MDSUM = getManhattanDistanceSum();
		this.cost = 1000000000;
		this.holePoint = getHole();
	}
	
	// MDSUM取得
	int getManhattanDistanceSum() {
		calcManhattanDistanceSum();
		return MDSUM;
	}
	void calcManhattanDistanceSum() {
		int sum = 0;
		int orgX = 0;
		int orgY = 0;
		int md = 0;
		for(int i = 0; i < HEI; i++) {
			for(int j = 0; j < WID; j++) {
				orgX = face[i][j] % WID;
				orgY = face[i][j] / WID;
				md = Math.abs(orgX - j) + Math.abs(orgY - i);
				sum = sum + md;
			}
		}
		MDSUM = sum;
	}
	
	// 面取得
	public int[][] getFace() {
		return (int[][]) SerializationUtils.clone(this.face); // 面の複製を返す
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
//			Point tmpPoint = new Point();
		Point tmpPoint = (Point) holePoint.clone();
		
		// 入れ替え面が無いときnullを返す
		switch (dir) {
		case 0:
			tmpPoint.x--;
			if(tmpPoint.x < 0) return null;
			return getSwappedFace(holePoint, tmpPoint);
		case 1:
			tmpPoint.x++;
			if(tmpPoint.x >= HEI) return null;
			return getSwappedFace(holePoint, tmpPoint);
		case 2:
			tmpPoint.y--;
			if(tmpPoint.y < 0) return null;
			return getSwappedFace(holePoint, tmpPoint);
		case 3:
			tmpPoint.y++;
			if(tmpPoint.y >= WID) return null;
			return getSwappedFace(holePoint, tmpPoint);
		default:
			return null;
		}
	}
	public int[][] getSwappedFace(Point a, Point b) {
		int[][] tmpFace = new int[HEI][WID];
		
		tmpFace = (int[][]) SerializationUtils.clone(this.face);	// DeepCopy
		
		int tmp = tmpFace[a.x][a.y];
		System.out.printf("[Nodeswap]入れ替え対象 穴[%2d][%2d] <=> [%2d][%2d]\n", a.x, a.y, b.x, b.y);
		try {
			tmpFace[a.x][a.y] = tmpFace[b.x][b.y];
			tmpFace[b.x][b.y] = tmp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Puzzle.printIntFace(tmpFace);
		return tmpFace;
	}
}
