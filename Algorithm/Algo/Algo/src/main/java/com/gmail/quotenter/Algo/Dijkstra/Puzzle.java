package com.gmail.quotenter.Algo.Dijkstra;

import java.util.Random;

public class Puzzle {

	public static int WID = 0;
	public static int HEI = 0;
	
	public Puzzle(int width, int height) {
		this.WID = width;
		this.HEI = height;
	}
	
	// Now loading...
	public void solve() {
		
	}
	
	public void debug(int[][] face) {
		Node node = new Node(randomFace(), WID, HEI);
		node.printFace();
		node.getManhattanDistance();
	}
	
	public int[][] randomFace() {
		int tmp = 0;
		int count = 0;
		int[][] face = new int[HEI][WID];
		

		for(int i = 0; i < HEI; i++) {
			for(int j = 0; j < WID; j++) {
				face[i][j] = count;
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

			System.out.printf("[%d][%d] <=> [%d][%d]\n", p, q, r, s);

			// 置換
			tmp = face[p][q];
			face[p][q] = face[r][s];
			face[r][s] = tmp;
		}
		
		return face;
	}
	
}
