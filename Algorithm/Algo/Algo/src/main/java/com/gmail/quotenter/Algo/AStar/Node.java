package com.gmail.quotenter.Algo.AStar;

import org.apache.commons.lang.SerializationUtils;

public class Node {
	
	public int mdsum = 0;										// マンハッタン距離総和
	public static int[][] face;									// ノードの面
	public int g = 0;											// g*(スタートノードからの最短推定距離)
	
	public Node(int[][] face) {
		Node.face = (int[][]) SerializationUtils.clone(face);	// 面の初期化(DeepCopy)
		mdsum = -1;
	}
	
	// MDSUM計算
	public void calcManhattanDistanceSum() {
		int sum = 0;
		int orgX = 0;
		int orgY = 0;
		int md = 0;
		for(int i = 0; i < Puzzle.HEI; i++) {
			for(int j = 0; j < Puzzle.WID; j++) {
				orgX = face[i][j] % Puzzle.WID;
				orgY = face[i][j] / Puzzle.WID;
				md = Math.abs(orgX - j) + Math.abs(orgY - i);
				sum = sum + md;
			}
		}
		mdsum = sum;
	}
	
	// MDSUM
	
}
