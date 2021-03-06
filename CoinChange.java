public class CoinChange {
    // Method 1: DFS
    public int coinChange(int[] coins, int amount) {
        // Sanity Check 
        if (amount < 1) return 0;
        // 自上而下的动态规划方法
        return coinChange(coins, amount, new int[amount]);
    }

    private int coinChange(int[] coins, int rem, int[] count) {
        // base case: this path is invalid
        if (rem < 0) return -1;
        // base case: rem as 0, successfully exit
        if (rem == 0) return 0;
        // deduplication: directly return, avoid duplicate case
        if (count[rem - 1] != 0) return count[rem - 1];
        int min = Integer.MAX_VALUE;
        // dfs every possible path
        for (int coin : coins) {
            // 用一下coin这个面值的硬币会怎样？res是这个方法的最优情况
            int res = coinChange(coins, rem - coin, count);
            // res < 0 will result res = -1, 
            // res > min not the optimal, exclude
            if (res >= 0 && res < min) {
                min = 1 + res;
            }
        }
        // count[rem - 1]存储着给定金额amount的解
        // 若为Integer.MAX_VALUE则该情况无解
        count[rem - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
        return count[rem - 1];
    }
    
    // Method 2: DP
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        
        for (int j = 0; j< coins.length; j++) {
            for (int i = 0; i <= amount; i++) {
                if (i - coins[j] >= 0) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }
}

// dp[i] represents the fewest number of coins that 
// you need to make up the amount i 

// initialize dp[i] as (amount+1) a impossible value 
// Input: coins = [1, 7, 13], amount = 21
// Output: 3
// Explanation: 13 + 7 + 1 = 21

// in the first round of iteration, coins[j] has value 1
// so dp[i] equals value i

// in the second round of iteration, it will replace seven "1" to one "7"

// in the third round, it will replace amount of 13 (no matter what comb of "1" and "7") to one "13"
// dp[amount] is the fewest number of coins consisting that amount 

/*
Test Case: coins = [1, 7, 13], amount = 21
Return 3 since 1 + 7 + 13 = 21 or 7 + 7 + 7 = 21
========================================
1st Iteration, i: 0 - 21, coin = 1
dp[0] = 0 
dp[1] = min(dp[1], dp[0] + 1) = min(22, 1) = 1
dp[2] = min(dp[2], dp[1] + 1) = min(22, 2) = 2
…
dp[21] = min(dp[21], dp[20] + 1) = min(22, 21) = 21
========================================
2nd Iteration, i: 0 - 21, coin = 7
…
dp[7] = min(dp[7], dp[0] + 1) = min(7, 1) = 1
dp[8] = 2
…
dp[13] = 7
dp[14] = min(dp[14], dp[7] + 1) = min(14, 1 + 1) = 2
dp[15] = 3
…
dp[21] = min(dp[14], dp[14] + 1) = min(14, 2 + 1) = 3
========================================
3rd Iteration, i: 0 - 21, coin = 7
…
dp[13] = min(dp[13], dp[0] + 1) = min(7, 0 + 1) = 13
…
dp[21] = min(dp[21], dp[14] + 1) = min(3, 2 + 1) = 3
*/
