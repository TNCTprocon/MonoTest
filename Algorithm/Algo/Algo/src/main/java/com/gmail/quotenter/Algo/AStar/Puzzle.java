package com.gmail.quotenter.Algo.AStar;

import java.util.ArrayList;

public class Puzzle {
	
	public static int WID = 0;							// 面の横幅
	public static int HEI = 0;							// 面の高さ
	
	public static ArrayList<Node> openArray = null;		// Openリスト
	public static ArrayList<Node> closeArray = null;	// Closeリスト
	public static Node startNode = null;				// 開始ノ＝ド
	public static Node goalNode = null;					// 終了ノード
	
	public static int[][] goalFace;						// ゴールの面
	
	/*
	 * face : 問題
	 * width : 横幅
	 * height : 高さ
	 */
	public Puzzle(int[][] face, int width, int height) {
		
		// 面のサイズ初期化
		WID = width;
		HEI = height;
		
		// スタートノードからの最小コスト
		int g = 0;
		
		// ゴールの面
		int count = 0;
		for(int i = 0; i < HEI; i++) {
			for(int j = 0; j < WID; j++) {
				face[i][j] = count;
				count++;
			}
		}
		
		// OpenリストとCloseリストの初期化
		openArray = new ArrayList<Node>();
		closeArray = new ArrayList<Node>();
		
		// スタートノードとゴールノードの初期化
		startNode = new Node(face);
		goalNode = new Node(face);
	}
	
	// 解答検索
	public void solve() {
		startNode.g = 0;
	}
}
