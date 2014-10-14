package com.gmail.quotenter.Algo;

import com.gmail.quotenter.Algo.Dijkstra.Puzzle;

/**
 * Hello world!
 *
 *	命名則	: パズル自体	Puzzle	Puzzle
 *			: 各局面		int[][]	face
 *			: ノード		Node	node
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        int[][] face1 = {{1, 3, 6, 7},
            {13, 14, 9, 15},
            {4, 0, 8, 11},
            {12, 5, 2, 10}
        };
        //Puzzle 2
        int[][] face2 = {{1, 14, 6, 7},
            {13, 15, 0, 11},
            {4, 5, 3, 9},
            {12, 2, 8, 10}
        };
        
        // Puzzle 3
        int[][] face3 = {
			{1, 9, 3, 7},
	        {4, 0, 6, 15},
	        {5, 13, 2, 11},
	        {8, 12, 14, 10}
		};
        
        Puzzle puzzle = new Puzzle(4, 4);
        puzzle.debug(face1);
    }
}
