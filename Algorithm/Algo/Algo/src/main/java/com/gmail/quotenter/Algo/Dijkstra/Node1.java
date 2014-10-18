package com.gmail.quotenter.Algo.Dijkstra;

import java.awt.Point;
import org.apache.commons.lang.SerializationUtils;
import java.lang.reflect.Array;

public class Node1 implements Cloneable {

	int manhattanDistanceSum;
	
	public final int[][] face;
	public final Point holePoint;
	public int cost = -1;
	public boolean done = false;
	public static int WID = 0;
	public static int HEI = 0;
	
	public Node1(int[][] face, int width, int height) {
		this.face = face;
		this.WID = width;
		this.HEI = height;
		this.manhattanDistanceSum = -1;
		calcManhattanDistanceSum();
		this.holePoint = getHole();
	}
	
	// 次局面取得
	public int[][] getNextFace(int dir) {
		return getSwappedFace(dir);
	}
	
	// 次ノード取得
	public Node1 getNextNode(int dir) {
		int[][] tmpFace = getSwappedFace(dir);
		if(tmpFace != null) {	// 次の局面があるなら
			// 次の局面を表示
			System.out.println("[Node]次の局面発見");
			Puzzle1.printIntFace(tmpFace);
			// リストから同じノードを探す
			for(Node1 tmpNode : Puzzle1.nodeArray) {
				// 同じ面を持ったノードが見つかったらそのノードを返す
				if(tmpFace == tmpNode.getFace()) {
					System.out.println("既存ノード発見");
					System.out.println("MDSUM: " + tmpNode.manhattanDistanceSum);
					return tmpNode;
				}
			}
			// みつからなかったら新しく作って返す
			// 同時にnodeArrayに追加
			System.out.println("ノード作成");
			Node1 node = new Node1(tmpFace, WID, HEI);
			Puzzle1.nodeArray.add(node);
			System.out.println("MDSUM: " + node.manhattanDistanceSum);
			return node;
		} else {
			System.out.println("[Node]次の局面はnullです(局面なし)");
		}
		return null;
	}
	
	// マンハッタン距離総和
	public int getManhattanDistanceSum() {
		return this.manhattanDistanceSum;
	}
	void calcManhattanDistanceSum() {
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
				
				/*System.out.printf("[MD] %2d org[%2d][%2d], now[%2d][%2d], D =%3d, [SUM:%3d]\n",
						face[i][j],
						orgY, orgX,
						i, j,
						md,
						sum
						);*/
			}
		}
		manhattanDistanceSum = sum;
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
//		Point tmpPoint = new Point();
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
		
		System.out.printf("入れ替え対象 穴[%2d][%2d] <=> [%2d][%2d]\n", a.x, a.y, b.x, b.y);
		
		try {
			tmpFace[a.x][a.y] = tmpFace[b.x][b.y];
			tmpFace[b.x][b.y] = tmp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tmpFace;
	}
	
	
//	public Node clone() {
//		Node node = new Node(face, WID, HEI);
//		return node;
//	}
	public Node1 clone() throws CloneNotSupportedException {
        return (Node1) super.clone();
	}
	
}
