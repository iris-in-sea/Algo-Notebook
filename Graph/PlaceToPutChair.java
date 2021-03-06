/* *
 * Given a gym with k pieces of equipment and some obstacles. We bought a chair and wanted to put this chair into the gym 
 * such that  the sum of the shortest path cost from the chair to the k pieces of equipment is minimal. 
 * The gym is represented by a char matrix, ‘E’ denotes a cell with equipment, 
 *                                          ‘O’ denotes a cell with an obstacle, 
 *                                          'C' denotes a cell without any equipment or obstacle. 
 * 
 * Only allow to move to neighboring cells (left, right, up, down) 
 * if the neighboring cell is not an obstacle. 
 * The cost of moving from one cell to its neighbor is 1. 
 *
 * Do not allow to put the chair on a cell with equipment or obstacle.
 * 
 * Assumptions
 * There is at least one equipment in the gym
 * The given gym is represented by a char matrix of size M * N, where M >= 1 and N >= 1, it is guaranteed to be not null
 * It is guaranteed that each 'C' cell is reachable from all 'E' cells.
 * If there does not exist such place to put the chair, just return {-1, -1}
 * 
 * Examples
 * { { 'E', 'O', 'C' },
 *   { 'C', 'E', 'C' },
 *   { 'C', 'C', 'C' } }
 * 
 * we should put the chair at (1, 0), so that the sum of cost from the chair to the two equipment is 1 + 1 = 2, which is minimal.
 */

public class Pair {
  int i;
  int j;
  
  Pair(int i, int j) {
    this.i = i;
    this.j = j;
  }
}

public class PlaceToPutChairI {
  private static final char EQUIP = 'E';
  private static final char OB = 'O';

  public List<Integer> putChair(char[][] gym) {
    int m = gym.length;
    int n = gym[0].length;

    // record the sum of shortest path cost 
    // from each cell to all the 'E' cells
    int[][] cost = new int[m][n];

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        
        // 只计算以E cell为起点，到其他所有cells的距离之和，
        // 然后把这些距离，累加到empty cell（比如坐标为 i, j）上，即 cost[i][j] 上
        // 那么到了最后，cost[i][j] 就是从所有E cells到当前的 empy cell [i, j] 的距离之和 
        if (gym[i][j] == EQUIP) {
          // use BFS to calculate the shortest path cost from each of the equipments
          // to all the other reachable cells,
          // and add the cost to each corresponding cell.
          // The addCost method will return null if there is any other 'E' cell
          // that is not reachable from the current 'E' cell,
          // if so, there won't be any valid place to put a chair
          if (!addCost(cost, gym, i, j)) {
            return null;
          }
        }
      }
    }
    // find the cell with the smallest sum of shorted path costs
    // to all the 'E' cells
    List<Integer> result = null;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        
        if (gym[i][j] != EQUIP && gym[i][j] != OB) { // this means an empty 'C' cell
          if (result == null) {
            result = Arrays.asList(i, j);
          } else if (cost[i][j] < cost[result.get(0)][result.get(1)]) {
            result.set(0, i);
            result.set(1, j);
          }
        }
      }
    }
    return result == null ? Arrays.asList(-1, -1) : result;  // if not found, return [-1, -1] !!
  }
  
  private boolean addCost(int[][] cost, char[][] gym, int i, int j) {
    // use a boolean matrix to make sure each cell will be visited no more than once
    boolean[][] visited = new boolean[gym.length][gym[0].length];
    // Breadth-first search, record the current path cost
    int pathCost = 1;
    
    Queue<Pair> queue = new LinkedList<>();
    visited[i][j] = true;
    queue.offer(new Pair(i, j));
    
    while (!queue.isEmpty()) {
      int size = queue.size();
      
      for (int l = 0; l < size; l++) {
        Pair cur = queue.poll();
        List<Pair> neis = getNeis(cur, gym);
        
        for (Pair nei : neis) {
          if (!visited[nei.i][nei.j]) {
            visited[nei.i][nei.j] = true;
            cost[nei.i][nei.j] += pathCost; // key update !!!
            queue.offer(nei);
          }
        }
      }
      // add the path cost by 1 for each level
      pathCost++; // inside the while
    }

    // if there exist another 'E' cell that is not reachable from the starting
    // 'E' cell, we return false
    for (int l = 0; l < gym.length; l++) {
      for (int m = 0; m < gym[0].length; m++) {
        if (!visited[l][m] && gym[l][m] == EQUIP) {
          return false;
        }
      }
    }
    return true;
  }

  private List<Pair> getNeis(Pair cur, char[][] gym) {
    int x = cur.i;
    int y = cur.j;
    int m = gym.length;
    int n = gym[0].length;

    List<Pair> neis = new ArrayList<>();

    if (x + 1 < m && gym[x + 1][y] != OB) {
      neis.add(new Pair(x + 1, y));
    }
    if (y + 1 < n && gym[x][y + 1] != OB) {
      neis.add(new Pair(x, y + 1));
    }
    if (x - 1 >= 0 && gym[x - 1][y] != OB) {
      neis.add(new Pair(x - 1, y));
    }
    if (y - 1 >= 0 && gym[x][y - 1] != OB) {
      neis.add(new Pair(x, y - 1));
    }
    return neis;
  }
}

/**
 * Ref: https://github.com/HungryOrc/Algorithm/blob/master/PlaceChairClosestToEquipments.java
 * * *  
 * 方法1：数学结论（据说可以用数学归纳法来证明）：
 * 一维直线上，有一些点，每个点有自己的x坐标，离所有点最近的点，是它们的x坐标的median处。
 * 二维矩阵上，有一些点，每个点有自己的x和y坐标，离所有点最近的点，它的x坐标是所有E的x坐标的median，它的y坐标是所有E的y坐标的median，
 * 那么这样得到的点，有可能自己也是一个E，还可能是一个障碍物。如果是障碍物，那就只能在这个障碍物的附近继续找了。  
 *
 * * *
 * 方法2：Dijkstra 算法，计算从一个固定起点到n个点分别的最短路径，耗时 O(n logn)
 * 那么，我们有2种做法：
 * 
 * 方法 2.1，在矩阵里二维loop所有的点，算他们到矩阵内所有点的距离（自然也就包含那k个equipment点），
 * 矩阵里一共有 n^2 个点，对于每一个点，以它为起始点，对整个矩阵做 Dijkstra 算法，耗时 n^2 log(n^2)，
 * 所以最后总时间是 O(n^2 * n^2 * log(n^2)) = O(n^4 logn)
 * 
 * 方法 2.2：从k个equipment点出发，计算它们到矩阵内所有点的距离，然后取最小的那个。这样耗时就小多了 ！！ 因为一般来说k的量级小于n^2。
 * 时间：O(k * n^2 * log(n^2)) = O(k * n^2 logn)     
 */
