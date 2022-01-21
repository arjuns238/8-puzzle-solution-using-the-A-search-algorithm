/* *****************************************************************************
 *  Name: Arjun Sriram
 *  Date: 1/21/2022
 *  Description: The Solver class computes the solution to the board using the A * search algorithm
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private int movements;
    private searchNode min;
    private searchNode minTwin;
    private Board minimum_board;
    private searchNode minimum;
    private int movess;


    private static class searchNode {
        int manPriority;
        Board boardd;
        searchNode previous;
        int moves;

        // class for search nodes
        private searchNode(Board curr, int moves, searchNode prev) {
            this.moves = moves;
            manPriority = curr.manhattan() + this.moves;
            this.boardd = curr;
            this.previous = prev;

        }

        private int get_moves() {
            return moves;
        }

    }

    // class for comparator
    private class byPriority implements Comparator<searchNode> {
        public int compare(searchNode node1, searchNode node2) {
            // return node1.manPriority - node2.manPriority;
            if (node1.manPriority == node2.manPriority)
                return Integer.compare(node1.boardd.manhattan(), node2.boardd.manhattan());
            else return Integer.compare(node1.manPriority, node2.manPriority);


        }
    }

    // comparator object creation
    private Comparator<searchNode> comp = new byPriority();


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("argument null");
        movements = 0;

        // Creating a new priority queue
        MinPQ<searchNode> PQ = new MinPQ<searchNode>(comp);
        MinPQ<searchNode> PQtwin = new MinPQ<searchNode>(comp);

        // Creating the first and first twin search nodes
        PQ.insert(new searchNode(initial, 0, null));
        PQtwin.insert(new searchNode(initial.twin(), 0, null));

        do {
            minTwin = PQtwin.delMin();
            min = PQ.delMin();

            // check for actual PQ
            for (Board board1 : min.boardd.neighbors()) {
                if (min.moves != 0 && AlreadyIsInSolution(min, board1)) continue;
                searchNode search = new searchNode(board1, min.moves + 1, min);
                PQ.insert(search);
            }

            // check for PQ twin
            for (Board board2 : minTwin.boardd.neighbors()) {
                if (minTwin.moves != 0 && AlreadyIsInSolution(minTwin, board2)) continue;
                searchNode search1 = new searchNode(board2, minTwin.moves + 1, minTwin);
                PQtwin.insert(search1);
            }


        } while (!min.boardd.isGoal() && !minTwin.boardd.isGoal());

        minimum_board = min.boardd;
        movess = min.moves;
        minimum = min;

    }

    private boolean AlreadyIsInSolution(searchNode end, Board boardie) {
        if (end.previous.boardd.equals(boardie))
            return true;
        return false;
    }


    // is the initial board solvable?
    public boolean isSolvable() {
        return minimum_board.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return movess;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> stack = new Stack<Board>();
        do {
            stack.push(minimum.boardd);
            minimum = minimum.previous;
        }
        while (minimum != null);
        return stack;

    }

    // test client
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        System.out.println("Moves = " + solver.moves());

    }
}

