package com.gmail.quotenter.Algo.Dijkstra;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Puzzle1 {
	
	/*
	 * 0 : 上
	 * 1 : 下
	 * 2 : 左
	 * 3 : 右
	 */

	public static int diff = 10000000;
	public static int WID = 0;
	public static int HEI = 0;
	public static int swapCount = 0;
	public static ArrayList<Node1> nodeArray;
	public static ArrayList<Node1> priorityArray;
	public Node1 startNode = null;
	public Node1 goalNode = null;
	public int[][] goalFace = null;
	public static int[][] firstFace;
//	public static Node maxNode = null;
	public static Node1 minNode = null;
	
	public Puzzle1(int[][] face, int width, int height) {
		this.WID = width;
		this.HEI = height;
		priorityArray = new ArrayList<Node1>();
		nodeArray = new ArrayList<Node1>();
		firstFace = face;
		startNode = new Node1(face, WID, HEI);	// 問題のfaceからスタートノードを生成
//		maxNode = new Node(face, WID, HEI);
		minNode = new Node1(face, WID, HEI);
		minNode.cost = diff;
//		maxNode.manhattanDistanceSum = 100000;	// つねにMD総和が最大値となるノード
		goalFace = new int[HEI][WID];
		int count = 0;
		for(int i = 0; i < HEI; i++) {
			for(int j = 0; j < WID; j++) {
				goalFace[i][j] = count;
				count++;
			}
		}
		goalNode = new Node1(goalFace, WID, HEI);
//		printIntFace(goalFace);
//		printIntNode(goalNode);
	}
	
	// Now loading...
	public void solve() {
		
		Node1 currentNode = startNode;	// カレントノードをスタートのノードに指定
		Node1 minCostNode = null;
		Node1 tmpNode = null;
		int calc = 0;
		
		
		// コスト(ノード間はマンハッタン距離の差)
		// スタートノードの値を0にセット
		currentNode.cost = this.diff;		// 最初のノードのコストを0
		currentNode.done = true;	// 最初のノードを確定
		nodeArray.add(currentNode);	// リストに追加
//		if(currentNode.holePoint.x == HEI - 1 && currentNode.holePoint.y == WID - 1 	// 右下
//				| currentNode.holePoint.x == 0 && currentNode.holePoint.y == 0 			// 左上
//				| currentNode.holePoint.x == 0 && currentNode.holePoint.y == WID - 1	// 右上
//				| currentNode.holePoint.x == HEI - 1 && currentNode.holePoint.y == 0) {	// 左下
//			
//		}
		
		/*
		 *  現在のノードに接続されているノードを検索
		 */
		
		// カレントが復元になるまでループ
		
		do {
			// 現在のノードから上下左右に動いたノードを検索
			System.out.println("===current===");
			printIntNode(currentNode);
			System.out.println("[COST] current " + currentNode.cost);
			System.out.println("===========");
			
			int tmpCost = -1;
			int tmpDir = -1;
			priorityArray.clear();
			for(int dir = 0; dir <= 3; dir++) {
				System.out.println("DIR: " + dir);
				tmpNode = currentNode.getNextNode(dir);
				
				// dir方向のノードを取得
				
				// 最初にminCostNodeを初期化
				if(dir == 0) minCostNode = minNode;
				// dir方向のノードがあるなら
				if(tmpNode != null) {
	//				printIntFace(tmpNode.getFace());	// 局面を表示
					// TODO: 多分コスト計算に問題がある
					// コスト(マンハッタン距離の総和の差)
					// 負 遠ざかる
					// 0 変わらない
					// 正 近づく
					// ↓に変更(tmpCostが小さい方が近くなる)
					// 負 なし(diffから引いて負に成らないようにした)
					// 0 変わらない
					// 正 近づく
					tmpCost = diff - (currentNode.manhattanDistanceSum - tmpNode.manhattanDistanceSum);
					System.out.println("[MDSUM] current " + currentNode.manhattanDistanceSum);
					System.out.println("[MDSUM] tmp " + tmpNode.manhattanDistanceSum);
					System.out.println("[COST] current " + currentNode.cost);
					tmpNode.cost = tmpCost;
					System.out.println("[COST] tmp " + tmpNode.cost);
					// コストが0以上の時
					// コスト0は解答に近づいておらず、コストが負のとき解答から遠ざかるため
					// (やめ)が、コスト0しかない場合MinCostがMaxNodeCostとなるので0も含む
					if(tmpCost > 0) {
						// 現在のtmpNodeがminCostNodeよりコストが大きければ最短ノードとして入れる
						if(tmpNode.cost < minCostNode.cost) {
							minCostNode = tmpNode;
							// 方向を保存(表示用)
							tmpDir = dir;
						}
					}
					// 優先度リストにノードを追加
					priorityArray.add(tmpNode);
				}
				System.out.printf("Now MinCostNode cost:%d \n", minCostNode.cost);
			}
			// DIRが-1の時(最小が確定できなかった時)
			// 優先度リストから最後の物を取り出し(スタック・深さ優先)minCostNodeに指定
			if(tmpDir == -1) {
				System.out.println("[優先度リスト発動]");
				printIntFace(priorityArray.get(priorityArray.size() - 1).face);
				currentNode = priorityArray.get(priorityArray.size() - 1);
			} else {
				System.out.printf("MinCost: %3d DIR: %d\n", minCostNode.manhattanDistanceSum, tmpDir);
				// 最小コストのノードを確定
				minCostNode.done = true;
				currentNode = minCostNode;
			}
			
			System.out.println("=============================");
			
			
			// デバッグ用低速化
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while(!currentNode.equals(goalNode));
	}
	
	// デバッグ
	public void debug(int[][] face) {
		System.out.println("===debug=======");
		if(startNode == null) {
			System.out.println("Puzzleのインスタンスが未生成です");
			return;
		}
		
		printIntNode(startNode);
//		System.out.println("MD SUM : " + startNode.getManhattanDistanceSum());
		System.out.printf("HOLE   : [%3d][%3d]\n", startNode.getHole().x, startNode.getHole().y);
		
		System.out.println("===debugend=====");
	}
	
	// ランダム問題(偶数交換)
	public int[][] randomFace() {
		int tmp = 0;
		int count = 0;
		int[][] tmpFace = new int[HEI][WID];
		

		for(int i = 0; i < HEI; i++) {
			for(int j = 0; j < WID; j++) {
				tmpFace[i][j] = count;
				count++;
			}
		}

		// 配列から問題を生成(偶数置換)
		int p = 0;
		int q = 0;
		int r = 0;
		int s = 0;
		Random random = new Random();
		int swapnum = random.nextInt(WID * HEI * 10) * 2;
		System.out.printf("Swapping %d\n", swapnum);

		for(int i = 0; i < swapnum; i++) {
			p = random.nextInt(HEI);
			q = random.nextInt(WID);
			r = random.nextInt(HEI);
			s = random.nextInt(WID);

//			System.out.printf("[%d][%d] <=> [%d][%d]\n", p, q, r, s);

			// 置換
			tmp = tmpFace[p][q];
			tmpFace[p][q] = tmpFace[r][s];
			tmpFace[r][s] = tmp;
		}
		
		return tmpFace;
	}
	public int[][] getRandomFace() {
		return randomFace();
	}
	
	// 16進変換機
	public static String[][] hexConverter(int[][] face) {
		String[][] stringFace = new String[HEI][WID];
		
		for(int i = 0; i < HEI; i++) {
			for(int j = 0; j < WID; j++) {
				stringFace[i][j] = Integer.toHexString(0x100 | face[i][j]).substring(1);			
			}
		}
		return stringFace;
	}
	
	// 16進表示
	public static void printStringFace(String[][] stringFace) {
		for(int i = 0; i < HEI; i++) {
			for(int j = 0; j < WID; j++) {
				System.out.printf("%3s", stringFace[i][j]);
				if(j == WID - 1) System.out.println();
			}
		}
	}
	public static void printStringFace(int[][] intFace) {
		printStringFace(hexConverter(intFace));
	}
	public static void printStringFace(Node1 node) {
		printStringFace(hexConverter(node.getFace()));
	}
	
	// 10進表示
	public static void printIntFace(int[][] face) {
		if(face == null) {
			System.out.println("null faceは表示できません");
			return;
		}
		System.out.println();
		for(int i = 0; i < HEI; i++) {
			for(int j = 0; j < WID; j++) {
				System.out.printf("%4d", face[i][j]);
				if(j == WID - 1) System.out.println();
			}
		}
		System.out.println();
	}
	public static void printIntNode(Node1 node) {
		printIntFace(node.getFace());
		System.out.println("MDSUM: " + node.manhattanDistanceSum);
	}
	// 問題セット用
	public void setFirstFace(int[][] face) {
		this.startNode = new Node1(face, WID, HEI);
	}
}
