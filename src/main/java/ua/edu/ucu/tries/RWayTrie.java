package ua.edu.ucu.tries;


import ua.edu.ucu.immutable.Queue;

import java.util.ArrayList;

public class RWayTrie implements Trie {
    private static final int R = 26;
    private Node root = new Node();
    private int size = 0;

    private static class Node {
        private int value;
        private final Node[] next;

        private Node() {
            this.value = 0;
            this.next = new Node[R];
        }
    }

    @Override
    public void add(Tuple t) {
        root = addHelper(root, t.term.toLowerCase(), t.weight, 0);
        this.size++;
    }

    private Node addHelper(Node node, String string, int weight, int i) {
        if (node == null) {
            node = new Node();
        }
        if (string.length() == i) {
            node.value = weight;
            return node;
        }
        char cur = string.charAt(i);
        node.next[transformIndex(cur)] = addHelper(node.next[transformIndex(cur)],
                string, weight, i + 1);
        return node;
    }

    private int transformIndex(int i) {
        return i - 97;
    }

    @Override
    public boolean contains(String word) {
        return containsHelper(root, word, 0);
    }

    private boolean containsHelper(Node node, String word, int i) {
        if (node == null) {
            return false;
        }
        if (word.length() == i) {
            return true;
        }
        char cur = word.charAt(i);
        return containsHelper(node.next[transformIndex(cur)], word, i + 1);
    }

    @Override
    public boolean delete(String word) {
        if (!contains(word)) {
            return false;
        }
        root = deleteHelper(root, word, 0);
        this.size--;
        return true;
    }

    private Node deleteHelper(Node node, String word, int i) {
        if (node == null) {
            return null;
        }
        if (i == word.length()) {
            node.value = 0;
        } else {
            char cur = word.charAt(i);
            node.next[transformIndex(cur)] = deleteHelper(node.next[transformIndex(cur)],
                    word, i + 1);
        }
        if (node.value != 0) {
            return node;
        }
        for (int j = 0; j < R; j++) {
            if (node.next[j] != null) {
                return node;
            }
        }
        return node;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String pref) {
        Queue queue = new Queue();
        collect(get(root, pref), pref, queue);
        ArrayList<String> res = new ArrayList<>();
        while (queue.getSize() > 0) {
            Object obj = queue.dequeue();
            res.add(obj.toString());
        }
        return res;
    }

    private void collect(Node node, String prefx,
                         Queue q) {
        if (node == null) {
            return;
        }
        if (node.value != 0) {
            q.enqueue(prefx);
        }
        for (int c = 0; c < R; c++) {
            collect(node.next[c], prefx + (char) (c + 97), q);
        }
    }

    private Node get(Node node, String word) {
        Node cur = node;
        for (int i = 0; i < word.length(); i++) {
            char curr = word.charAt(i);
            cur = cur.next[transformIndex(curr)];
            if (cur == null) {
                return null;
            }
        }
        return cur;
    }


    @Override
    public int size() {
        return this.size;
    }


}
