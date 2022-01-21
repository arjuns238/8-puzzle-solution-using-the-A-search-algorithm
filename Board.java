/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;
import java.util.Iterator;

public class Board {
    private int n;
    private int[][] arr;
    private int index_i, index_j;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        n = tiles.length;
        arr = new int[n][n];


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = tiles[i][j];
            }
        }

    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", arr[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (arr[i][j] == 0) continue;
                // if (i == n - 1 && j == n - 1) break;
                if (arr[i][j] != ((i * n) + (j + 1)))
                    count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (arr[i][j] == 0) continue;
                manhattanCount += manhattanOne(i, j, arr[i][j]);

            }

        }
        return manhattanCount;
    }

    private int manhattanOne(int i, int j, int val) {
        int req_row, req_col;
        int step_i = 0;
        int step_j = 0;
        if (val % n == 0) {
            req_row = (val / n) - 1;
            req_col = n - 1;
        }
        else {
            req_row = (val / n);
            req_col = (val % n) - 1;
        }
        if (i == req_row && j == req_col)
            return 0;
        else {
            if (i > req_row)
                step_i = i - req_row;
            else if (req_row > i)
                step_i = req_row - i;
            if (j > req_col)
                step_j = j - req_col;
            else if (req_col > j) {
                step_j = req_col - j;
            }
            return step_i + step_j;
        }
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        int N1 = this.n;
        int N2 = that.n;
        if (N1 != N2) return false;
        return (Arrays.deepEquals(this.arr, that.arr));
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (arr[i][j] == 0) {
                    index_i = i;
                    index_j = j;
                    break;
                }
            }
        }

        // testing the different cases
        if (index_i == 0 && index_j == 0) {

            Board neighbor1 = new Board(arr);
            Board neighbor2 = new Board(arr);

            // swap with 1 cell down
            swapDown(neighbor1.arr, index_i, index_j);
            // swap with 1 cell to the right
            swapRight(neighbor2.arr, index_i, index_j);
            stack.push(neighbor1);
            stack.push(neighbor2);
        }

        else if (index_i == 0 && index_j == n - 1) {
            Board neighbor1 = new Board(arr);
            Board neighbor2 = new Board(arr);

            // swap with 1 cell left
            swapLeft(neighbor1.arr, index_i, index_j);
            // swap with 1 cell below
            swapDown(neighbor2.arr, index_i, index_j);
            stack.push(neighbor1);
            stack.push(neighbor2);
        }

        else if (index_i == 0) {

            Board neighbor1 = new Board(arr);
            Board neighbor2 = new Board(arr);
            Board neighbor3 = new Board(arr);

            // swap with 1 cell left
            swapLeft(neighbor1.arr, index_i, index_j);
            // swap with 1 cell below
            swapDown(neighbor2.arr, index_i, index_j);
            // swap with 1 cell to the right
            swapRight(neighbor3.arr, index_i, index_j);
            stack.push(neighbor1);
            stack.push(neighbor2);
            stack.push(neighbor3);

        }
        else if (index_i == n - 1 && index_j == 0) {
            Board neighbor1 = new Board(arr);
            Board neighbor2 = new Board(arr);

            // swap with 1 cell right
            swapRight(neighbor1.arr, index_i, index_j);
            // swap with 1 cell up
            swapUp(neighbor2.arr, index_i, index_j);
            stack.push(neighbor1);
            stack.push(neighbor2);
        }
        else if (index_j == 0) {
            Board neighbor1 = new Board(arr);
            Board neighbor2 = new Board(arr);
            Board neighbor3 = new Board(arr);

            // swap with 1 cell right
            swapRight(neighbor1.arr, index_i, index_j);
            // swap with 1 cell below
            swapDown(neighbor2.arr, index_i, index_j);
            // swap with 1 cell up
            swapUp(neighbor3.arr, index_i, index_j);
            stack.push(neighbor1);
            stack.push(neighbor2);
            stack.push(neighbor3);
        }
        else if (index_i == n - 1 && index_j == n - 1) {
            Board neighbor1 = new Board(arr);
            Board neighbor2 = new Board(arr);

            // swap with 1 cell left
            swapLeft(neighbor1.arr, index_i, index_j);
            // swap with 1 cell up
            swapUp(neighbor2.arr, index_i, index_j);
            stack.push(neighbor1);
            stack.push(neighbor2);
        }
        else if (index_i == n - 1) {
            Board neighbor1 = new Board(arr);
            Board neighbor2 = new Board(arr);
            Board neighbor3 = new Board(arr);

            // swap with 1 cell right
            swapRight(neighbor1.arr, index_i, index_j);
            // swap with 1 cell left
            swapLeft(neighbor2.arr, index_i, index_j);
            // swap with 1 cell up
            swapUp(neighbor3.arr, index_i, index_j);
            stack.push(neighbor1);
            stack.push(neighbor2);
            stack.push(neighbor3);
        }
        else if (index_j == n - 1) {
            Board neighbor1 = new Board(arr);
            Board neighbor2 = new Board(arr);
            Board neighbor3 = new Board(arr);

            // swap with 1 cell down
            swapDown(neighbor1.arr, index_i, index_j);
            // swap with 1 cell left
            swapLeft(neighbor2.arr, index_i, index_j);
            // swap with 1 cell up
            swapUp(neighbor3.arr, index_i, index_j);
            stack.push(neighbor1);
            stack.push(neighbor2);
            stack.push(neighbor3);
        }
        else {
            Board neighbor1 = new Board(arr);
            Board neighbor2 = new Board(arr);
            Board neighbor3 = new Board(arr);
            Board neighbor4 = new Board(arr);

            // swap with 1 cell down
            swapDown(neighbor1.arr, index_i, index_j);
            // swap with 1 cell left
            swapLeft(neighbor2.arr, index_i, index_j);
            // swap with 1 cell up
            swapUp(neighbor3.arr, index_i, index_j);
            // swap with 1 cell right
            swapRight(neighbor4.arr, index_i, index_j);
            stack.push(neighbor1);
            stack.push(neighbor2);
            stack.push(neighbor3);
            stack.push(neighbor4);
        }
        return stack;
    }

    private void swapLeft(int[][] array, int i1, int j1) {
        int temp = array[i1][j1];
        array[i1][j1] = array[i1][j1 - 1];
        array[i1][j1 - 1] = temp;
    }

    private void swapRight(int[][] array, int i1, int j1) {
        int temp = array[i1][j1];
        array[i1][j1] = array[i1][j1 + 1];
        array[i1][j1 + 1] = temp;
    }

    private void swapDown(int[][] array, int i1, int j1) {
        int temp = array[i1][j1];
        array[i1][j1] = array[i1 + 1][j1];
        array[i1 + 1][j1] = temp;
    }

    private void swapUp(int[][] array, int i1, int j1) {
        int temp = array[i1][j1];
        array[i1][j1] = array[i1 - 1][j1];
        array[i1 - 1][j1] = temp;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin = new Board(arr);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (twin.arr[i][j] != 0) {
                    twin.arr = swap(i, j, twin.arr);
                    return twin;
                }
            }
        }
        return twin;

    }

    private int[][] swap(int i, int j, int[][] arrayy) {
        for (int k = 0; k < arr.length; k++) {
            for (int l = 0; l < arr.length; l++) {
                if (arrayy[k][l] != 0 && arrayy[k][l] != arrayy[i][j]) {
                    int temp = arrayy[k][l];
                    arrayy[k][l] = arrayy[i][j];
                    arrayy[i][j] = temp;
                    return arrayy;

                }
            }
        }
        return arrayy;

    }


    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        System.out.println(initial.toString());
        Iterable<Board> stackie = initial.neighbors();
        Iterator<Board> itr = stackie.iterator();
        while (itr.hasNext()) {
            Board b = itr.next();
            System.out.println(b);
            System.out.println("Hamming distance is :" + b.hamming());
            System.out.println("Manhattan distance is :" + b.manhattan());
            System.out.println("This board is goal: " + b.isGoal());

        }

        System.out.println("Twin is " + initial.twin().toString());
        System.out.println("Hamming distance is :" + initial.twin().hamming());
        System.out.println("Manhattan distance is :" + initial.twin().manhattan());


    }
}

