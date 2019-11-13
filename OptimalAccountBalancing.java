/* *
 * 465. Optimal Account Balancing
 */
 
public class OptimalAccountBalancing {
  public int minTransfers(int[][] transactions) {
    Map<Integer, Long> map = new HashMap<>();
    for (int[] t : transactions) {
      long val1 = map.getOrDefault(t[0], 0L);
      long val2 = map.getOrDefault(t[1], 0L);
      map.put(t[0], val1 - t[2]);
      map.put(t[1], val2 - t[2]); 
    }
    
    List<Long> list = new ArrayList<>();
    for (long val : list) {
      if (val != 0) list.add(val);
    }
    
    Long[] debts = new Long[list.size()];
    debts = list.toArray(debts);
    return helper(debts, 0, 0);
  }
  
  private int helper(Long[] debts, int pos, int cnt) {
    while (pos < debts.length && debts[pos] == 0) pos++;
    if (pos >= debts.length) {
      return cnt;
    }
    int min = Integer.MAX_VALUE;
    for (int i = pos + 1; i < debts.length; i++) {
      if (debts[i] * debts[pos] < 0) {
        debts[i] += debts[pos];
        min = Math.min(helper(debts, pos + 1, cnt + 1), min);
        debts[i] = debts[i] - debts[pos];
      }
    }
    return min;
  }
}
