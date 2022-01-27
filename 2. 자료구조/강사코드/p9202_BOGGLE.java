import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
  static int[] mx = {-1, 1, 0, 0, -1, 1, -1, 1};
  static int[] my = {0, 0, -1, 1, -1, -1, 1, 1};
  static int[] score = {0, 0, 0, 1, 1, 2, 3, 5, 11};

  static int W, N;
  static char[][] map;
  static boolean[][] visited;
  static String answer;
  static int sum;
  static int count;
  static StringBuilder sb = new StringBuilder();
  static TrieNode root = new TrieNode();

  public static void main(String[] args) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    W = Integer.parseInt(br.readLine());

    for (int i = 0; i < W; i++) {
      insert(br.readLine());
    }

    br.readLine();
    N = Integer.parseInt(br.readLine());
    StringBuilder resultSb = new StringBuilder();
    for (int n = 0; n < N; n++) {
      map = new char[4][4];
      visited = new boolean[4][4];
      answer = "";
      sum = 0;
      count = 0;
      sb = new StringBuilder();

      for (int i = 0; i < 4; i++) {
        String in = br.readLine();
        for (int k = 0; k < 4; k++) {
          map[i][k] = in.charAt(k);
        }
      }

      for (int y = 0; y < 4; y++) {
        for (int x = 0; x < 4; x++) {
          if (root.hasChild(map[y][x])) {
            search(y, x, 1, root.getChild(map[y][x]));
          }
        }
      }
      resultSb.append(sum);
      resultSb.append(" ");
      resultSb.append(answer);
      resultSb.append(" ");
      resultSb.append(count);
      resultSb.append("\n");
      root.clearHit();
      br.readLine();
    }
    System.out.println(resultSb.toString());

  }

  static void search(int y, int x, int length, TrieNode node) {
    // 1. 체크인
    visited[y][x] = true;
    sb.append(map[y][x]);

    // 2. 목적지에 도달하였는가?
    if (node.isEnd && node.isHit == false) {
      node.isHit = true;
      sum += score[length];
      count++;
      String foundWord = sb.toString();
      if (compare(answer, foundWord) > 0) {
        answer = foundWord;
      }
    }
    // 3. 연결된 곳을 순회
    for (int i = 0; i < 8; i++) {
      int ty = y + my[i];
      int tx = x + mx[i];
      // 4. 가능한가?
      if (0 <= ty && ty < 4 && 0 <= tx && tx < 4) {
        if (visited[ty][tx] == false && node.hasChild(map[ty][tx])) {
          // 5. 간다
          search(ty, tx, length + 1, node.getChild(map[ty][tx]));
        }
      }
    }
    // 6. 체크아웃
    visited[y][x] = false;
    sb.deleteCharAt(length - 1);
  }

  static int compare(String arg0, String arg1) {
    int result = Integer.compare(arg1.length(), arg0.length());
    if (result == 0) {
      return arg0.compareTo(arg1);
    } else {
      return result;
    }
  }

  static void insert(String word) {
    TrieNode current = root;
    for (int i = 0; i < word.length(); i++) {
      int wordIndex = word.charAt(i) - 'A';
      if (current.children[wordIndex] == null) {
        current.children[wordIndex] = new TrieNode();
      }
      current = current.children[wordIndex];
    }
    current.isEnd = true;
  }

  static boolean containsNode(String word) {
    TrieNode current = root;
    for (int i = 0; i < word.length(); i++) {
      int wordIndex = word.charAt(i) - 'A';
      if (current.children[wordIndex] == null) {
        return false;
      }
      current = current.children[wordIndex];
    }
    return true;
  }
}

class TrieNode {
  TrieNode[] children = new TrieNode[26];
  boolean isEnd;
  boolean isHit;

  void clearHit() {
    isHit = false;
    for (int i = 0; i < children.length; i++) {
      if (children[i] != null) {
        children[i].clearHit();
      }
    }
  }

  boolean hasChild(char c) {
    return children[c - 'A'] != null;
  }

  TrieNode getChild(char c) {
    return children[c - 'A'];
  }

  public String toString() {
    String str = "";
    for (int i = 0; i < children.length; i++) {
      if (children[i] != null) {
        str += (char) ('A' + i) + (children[i].isEnd ? "end" : "") + "{" + children[i].toString()
            + "}";
      }
    }
    return str;
  }
}
