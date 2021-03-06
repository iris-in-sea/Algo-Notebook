public class WoodCut {
    /**
     * @param L: Given n pieces of wood with length L[i]
     * @param k: An integer
     * @return: The maximum length of the small pieces
     *
     * Time: O(n logL) where n is number of woods in the arr, L is the max length among all given woods
     */
    public int woodCut(int[] L, int k) {
        int res = 0;
        int l = 1, r = 0;  // value not index, binary search on the length
        
        for (int len : L) {
            r = Math.max(len, r); // r = max(all L)
        }
        
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (cntWoods(L, mid) >= k) {
                // mid is a possible answer, but might be larger
                res = mid;
                l = mid + 1;
            } else {
                // mid is not an answer, must be smaller
                r = mid - 1;
            }
        }
        
        return res;
    }
    
    private int cntWoods(int[] L, int curLen) {
        int cnt = 0; 
        for (int len : L) {
            cnt += len / curLen;
        }
        return cnt;
    }
    
    // version 2
    public int woodCut(int[] L, int k) {
        int l = 1;
        int r = 0;
        for (int item : L) {
            r = Math.max(r, item);
        }
        
        while (l + 1 < r) {
            int mid = l + (r - l) / 2;
            if (count(L, mid) >= k) {
                l = mid;
            } else {
                r = mid;
            }
        }
        // post-processing
        if (count(L, r) >= k) {
            return r;
        }
        
        if (count(L, l) >= k) {
            return l;
        }
        return 0;
    }
    
    private int count(int[] L, int len) {
        int sum = 0;
        for (int item : L) {
            sum += item / len;
        }
        return sum;
    }
}
