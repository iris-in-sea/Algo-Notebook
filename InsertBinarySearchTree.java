/**
 * Insert a key in a binary search tree if the binary search tree does not already contain the key. 
 * Return the root of the binary search tree.
 *
 * Assumptions
 * 1) There are no duplicate keys in the binary search tree
 * 2) If the key is already existed in the binary search tree, you do not need to do anything
 */

/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int key;
 *     public TreeNode left, right;
 *     public TreeNode(int key) {
 *         this.key = key;
 *         this.left = this.right = null;
 *     }
 * }
 */

public class InsertBinarySearchTree {
  /* *
   * Mathod 1: Recurison
   * Time: O(H) where O(log N) for average case, O(N) worst case
   * Space: O(H) for recursion stack calls
   */
  public TreeNode insert(TreeNode root, int key) {
    if (root == null) {
      return new TreeNode(key);
    }
    if (key < root.key) {
      root.left = insert(root.left, key);
    } else if (key > root.key) {
      root.right = insert(root.right, key);
    }
    return root;
  }
  
  /**
   * Mathod 2: Iterative 
   * Time: O(H) where O(log N) for average case, O(N) worst case
   * Space: O(1) since it's a constant space solution
   */
  public TreeNode insert(TreeNode root, int key) {
    // corner case: empty tree
    if (root == null) {
      return new TreeNode(key);
    }
    TreeNode cur = root;
    while (key != cur.key) { // If the key is already existed
      if (key < cur.key) {
        if (cur.left == null) {
          cur.left = new TreeNode(key);
        }
        cur = cur.left;
      } else {
        if (cur.right == null) {
          cur.right = new TreeNode(key);
        }
        cur = cur.right;
      }
    }
    return root;
  }
}
