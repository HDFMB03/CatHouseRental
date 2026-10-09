package com.Cathouse.utils;

import java.util.*;
import java.util.logging.Logger;

public class AhoCorasickFilter {

    private static final Logger logger = Logger.getLogger(AhoCorasickFilter.class.getName());

    public final static List<String> sensitiveWords = Arrays.asList(
            "暴力", "色情", "赌博", "毒品", "孤单", "寂寞", "求陪", "连睡", "磕炮", "语音哄睡",
            "刺激", "过瘾", "爽", "老司机", "18禁", "艳遇", "大波"
    );

    private final AcNode root = new AcNode();
    private final Object lock = new Object();

    private static class AcNode {
        Map<Character, AcNode> children = new HashMap<>();
        AcNode fail;
        boolean isEnd;
        int length;
    }

    static {
        AhoCorasickFilter filter = new AhoCorasickFilter();
        for (String word : sensitiveWords) {
            filter.addWord(word);
        }
        filter.buildFailurePointer();
    }

    public void addWord(String word) {
        synchronized (lock) {
            AcNode node = root;
            for (char c : word.toCharArray()) {
                node.children.putIfAbsent(c, new AcNode());
                node = node.children.get(c);
            }
            node.isEnd = true;
            node.length = word.length();
        }
    }

    public void buildFailurePointer() {
        synchronized (lock) {
            Queue<AcNode> queue = new LinkedList<>();
            root.fail = null;
            queue.offer(root);
            while (!queue.isEmpty()) {
                AcNode p = queue.poll();
                for (Map.Entry<Character, AcNode> entry : p.children.entrySet()) {
                    char c = entry.getKey();
                    AcNode pc = entry.getValue();
                    if (p == root) {
                        pc.fail = root;
                    } else {
                        AcNode q = p.fail;
                        while (q != null) {
                            if (q.children.containsKey(c)) {
                                pc.fail = q.children.get(c);
                                break;
                            }
                            q = q.fail;
                        }
                        if (q == null) {
                            pc.fail = root;
                        }
                    }
                    queue.offer(pc);
                }
            }
        }
    }

    public void addSensitiveWord(String word) {
        addWord(word);
        buildFailurePointer();
    }

    public String filter(String text, char replacement) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        logger.info("开始过滤文本: " + text);
        char[] chars = text.toCharArray();
        AcNode p = root;

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            while (p != root && !p.children.containsKey(c)) {
                p = p.fail;
            }
            if (p.children.containsKey(c)) {
                p = p.children.get(c);
            }
            if (p.isEnd) {
                int start = i - p.length + 1;
                Arrays.fill(chars, start, i + 1, replacement);
                p = root;
            }
        }

        String result = new String(chars);
        logger.info("过滤完成，结果: " + result);
        return result;
    }

    public String filter(String text) {
        return filter(text, '*');
    }
}
