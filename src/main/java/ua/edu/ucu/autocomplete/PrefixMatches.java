package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;


/**
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        for (String s : strings) {
            if (s.length() > 2) {
                this.trie.add(new Tuple(s, s.length()));
            }
        }
        return this.trie.size();
    }

    public boolean contains(String word) {
        return this.trie.contains(word);
    }

    public boolean delete(String word) {
        return this.trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return this.trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() < 2) {
            System.out.println("Prefix too short");
        }
        ArrayList<String> res = new ArrayList<>();
        Iterable<String> lst = this.trie.wordsWithPrefix(pref);
        for (String prefix : lst) {
            if (prefix.length() - pref.length() < k) {
                res.add(prefix);
            }
        }
        return res;
    }

    public int size() {
        return this.trie.size();
    }
}
