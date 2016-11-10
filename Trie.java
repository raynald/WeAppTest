import java.util.Map;

public class Trie {
    /**
     * Trie class
     */
    private TreeNode root;
    protected boolean caseSensitive;
    private TreeNode node;

    public Trie(boolean _caseSensitive) {
        root = new TreeNode((char) 0);
        caseSensitive = _caseSensitive;
    }

    // Add word in the tree
    public void addWord(String word, String fileName) {
        if (word != null) root.add(caseSensitive ? word: word.toLowerCase(), 0, fileName);
    }

    // Search word in the trie
    public Map<String, Integer> searchWord(String word) {
        if (word == null) return null;
        node = root.search(caseSensitive ? word : word.toLowerCase(), 0);
        if (node != null) {
            return node.occurence;
        } return null;
    }
}
