import java.util.Map;
import java.util.HashMap;

public class TreeNode {
    /**
     * TreeNode class
     */

    char val;
    Map<String, Integer> occurence;
    Map<Character, TreeNode> children;

    TreeNode(char ch) {
        val = ch;
        occurence = null;
        children = null;
    }

    // Add word and filename
    void add(String str, int position, String filename) {
        if (str == null || position >= str.length()) return;

        if (children == null) {
            children = new HashMap<Character, TreeNode>();
        }
        char ch = str.charAt(position);
        TreeNode node = children.get(ch);
        if (node == null) {
            node = new TreeNode(ch);
            children.put(ch, node);
        }
        if (position == str.length() - 1) {
            if (node.occurence == null) node.occurence = new HashMap<String, Integer>();
            if (node.occurence.get(filename) == null) {
                node.occurence.put(filename, 1);
            } else {
                node.occurence.put(filename, node.occurence.get(filename) + 1);
            }
        } else node.add(str, position + 1, filename);
    }

    // Search method
    TreeNode search(String str, int position) {
        if (str == null) return null;

        if (position >= str.length() || children == null) return null;
        else if (position == str.length() - 1) return children.get(str.charAt(position));
        else {
            TreeNode node = children.get(str.charAt(position));
            return node == null ? null : node.search(str, position + 1);
        }
    }

}

