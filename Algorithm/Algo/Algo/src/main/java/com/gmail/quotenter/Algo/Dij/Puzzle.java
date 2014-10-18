package com.gmail.quotenter.Algo.Dij;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.lang.SerializationUtils;

import com.gmail.quotenter.Algo.Dij.Node;
import com.gmail.quotenter.Algo.Dij.Puzzle;

public class Puzzle {
	public static int WID;
	public static int HEI;
	public static int[][] startFace;
	public static Node startNode;
	public static Node goalNode;
	public static Node currentNode;
	public static ArrayList<Node> priorityArray;
	public static ArrayList<Node> nodeArray;
	public static ArrayList<Node> closeNodes;
	
	public Puzzle(int[][] face, int width, int height) {
		Puzzle.WID = width;
		Puzzle.HEI = height;
		Puzzle.startNode = new Node(face);
		Puzzle.nodeArray = new ArrayList<Node>();
		Puzzle.goalNode = new Node(face);
		Puzzle.closeNodes = new ArrayList<Node>();	// 確定済
		
		System.out.println("===START===");
		Puzzle.printIntNode(startNode);
		System.out.println("===GOAL===");
		int[][] tmpFace = new int[HEI][WID];
		for(int i = 0; i < HEI; i++) {
			for(int j = 0; j < WID; j++) {
				tmpFace[i][j] = i * HEI + j;
			}
		}
		Puzzle.printIntFace(tmpFace);
		Puzzle.priorityArray = new ArrayList<Node>();
		
		System.out.println("==============================");
	}
	
	// ソルバー
	public void solve() {
//		
//		int x, y, min;
//		
//		
//		for(Node node : nodeArray) {
//			
//		}
//		
//		for(x = 0; x < n; x++){
//			cost[x] = 1000000000;
//			used[x] = 0;
//		}
//		cost[g] = 0;
//		while(true){
//			min = INF;
//			for(x = 0; x < n; x++){
//				if(!used[x] && min > cost[x])
//					min = cost[x];
//				}
//			if(min == INF)
//				break;
//			for(y = 0; y < n; y++){
//				if(cost[y] == min){
//					for(x = 0; x < n; x++){
//						if(cost[x] > dist[x][y] + cost[y])
//							cost[x] = dist[x][y] + cost[y];
//					}
//				}
//			}
//		}
		
		
		int beforeCost = 10000010;
		int minCost = 10000000;
		int tmpCost = 0;
		Node minNode = null;
		
		// スタートノードをnodeArrayに追加
		nodeArray.add(startNode);
		
		// スタートノードのコストは10000000
		startNode.cost = 10000000;
		
		// スタートノードをカレントに指定
		currentNode = startNode;
		
		// スタートは最小確定なのでnodeArrayから取り除く
		nodeArray.remove(startNode);
		
		// スタートを確定ノードに追加
		closeNodes.add(startNode);
		
		while(true) {
			beforeCost = 10000010;
			
			// カレントに隣接するノード計算
			calcNextNodes(currentNode);
			
			// ノードリストから最小コストの物を探す
			System.out.println("最小探し");
			for(Node node : nodeArray) {
				// ノード表示
				printIntNode(node);
				System.out.println("befocost: " + beforeCost);
				
				// コスト計算(今のコストよりもtmpCostが小さい時のみ更新)
				tmpCost = 10000000 - (currentNode.MDSUM - node.MDSUM) - (10000000 - currentNode.cost);
				if(tmpCost < node.cost | node.cost < 0) {
					node.cost = tmpCost;
				}
				System.out.println("nodecost: " + node.cost);
				System.out.println("tmp cost: " + tmpCost);
				
				// 最小探し
				if(tmpCost <= minCost) {
					minCost = node.cost;
					minNode = node;
				}
				beforeCost = node.cost;
				System.out.println("min cost: " + minCost);
			}
			System.out.println("====================");
//			System.out.println("最小値は: " + minCost + "MDSUM: " + minNode.MDSUM);
			
			// minNodeをカレントにする
			currentNode = minNode;
			
			// 最小コストのノードを確定し、nodeArrayから取り除く
			closeNodes.add(minNode);
			nodeArray.remove(minNode);
			
			// nodeArrayが空のとき、全てのノードが探索終了
			if(nodeArray.isEmpty()) {
				System.out.println("探索終了");
				return;
			}
			
			// 低速化
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
		
		// デバッグ用低速化
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	// 次の面リスト
	public void calcNextNodes(Node currentNode) {
		ArrayList<Node> nextNodes = new ArrayList<Node>();
		boolean flag_nowhere = false;
		boolean flag_noclose = false;
		
		// 現在の穴の位置から移動面を推定する
		// 0123 上下左右
		for(int dir = 0; dir <= 3; dir++) {
			flag_nowhere = false;
			flag_noclose = false;
			System.out.println("DIR: " + dir);
			int[][] tmpFace = (int[][]) SerializationUtils.clone(currentNode.getSwappedFace(dir));
			// 次の局面があるなら
			if(tmpFace != null) {
				if(nodeArray.size() != 0) {
					System.out.println("nodea: " + nodeArray.size());
					for(Node tmpNode1 : nodeArray) {
						if(tmpNode1.getFace().equals(tmpFace)) {
							System.out.println("発見済です");
						} else {
							flag_nowhere = true;
						}
					}
				} else {
					flag_nowhere = true;
				}
				
				// closeNodes(確定リスト)に無いならnodeArrayにノードを作成して追加
				System.out.println("close: " + closeNodes.size());
				for(Node tmpNode : closeNodes) {
					if(tmpNode.getFace().equals(tmpFace)) {
						System.out.println("確定済です");
					} else {
						flag_noclose = true;
					}
				}
				
				// 未発見かつ未確定ならノード作成
				if(flag_noclose && flag_nowhere) {
					System.out.println("ノード作成");
					Node node = new Node(tmpFace);
					Puzzle.nodeArray.add(node);
//					nodeArray.add(node);
				} else {
					System.out.println("すべてのフラグを抜けました");
				}
				
				
				
			} else {
				System.out.println("[Node]次の局面はnullです(局面なし)");
			}
		}
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
	public static void printStringFace(Node node) {
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
	public static void printIntNode(Node node) {
		printIntFace(node.getFace());
		System.out.println("MDSUM: " + node.MDSUM);
	}
	
	// 問題セット用
	public void setFirstFace(int[][] face) {
		Puzzle.startNode = new Node(face);
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
		System.out.println(WID + " " + HEI);
		int swapnum = random.nextInt(WID * HEI * 10) * 2;
		System.out.printf("Swapping %d\n", swapnum);

		for(int i = 0; i < swapnum; i++) {
			p = random.nextInt(HEI);
			q = random.nextInt(WID);
			r = random.nextInt(HEI);
			s = random.nextInt(WID);

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
}
