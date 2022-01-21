# 8-puzzle-solution-using-the-A-search-algorithm
The 8-puzzle [https://en.wikipedia.org/wiki/15_puzzle](url) is a sliding puzzle that is played on a 3-by-3 grid with 8 square tiles labeled 1 through 8, plus a blank square. The goal is to rearrange the tiles so that they are in row-major order, using as few moves as possible. A solution to this problem is given using the general Artificial Intelligence algorithm - A * search algorithm [https://en.wikipedia.org/wiki/A*_search_algorithm](url). The priority function used is the Manhattan priority function. 

The program uses the Prioirity Queue data structure to find the solution with the shortest moves possible and is also able to detect whether the board is solvable or not. 

A search node of the game is defined to be a board, the number of moves made to reach the board, and the previous search node. To implement the A * Search algorithm, first we insert the initial search node (the initial board, 0 moves, null previous search node). Then, we delete from the priority queue the search node with the minimum priority, and insert onto the priority queue all neighboring search nodes (those that can be reached in one move from the dequeued search node). This procedure is repeated until the search node dequeued corresponds to the goal board.

To solve the puzzle from a given search node on the priority queue, the priority function used is the Manhattan Priority function.
