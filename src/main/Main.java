package main;

import java.util.ArrayList;
import java.util.List;

import util.ACX;

public class Main {

	public static CacheDico cacheDico = new CacheDico();

	public static boolean[] corriger(String[] correct, String[] words) {
		boolean[] valids = new boolean[correct.length];

		for (int i = 0; i < correct.length; i++) {
			int j = 0;
			for (; j < words.length; j++) {
				if (correct[i].toLowerCase().equals(words[j])) {
					break;
				}
			}

			valids[i] = j != words.length;
		}

		return valids;
	}

	public static boolean[] corrigerDico(String[] correct) {
		return Main.corriger(correct, cacheDico.get());
	}

	private static int rechercheBinaire(String[] arr, String search) {
		int left = 0;
		int right = arr.length - 1;

		while (left <= right) {
			int mid = left + (right - left) / 2;
			int cmp = search.compareTo(arr[mid]);

			if (cmp == 0) {
				return mid;
			} else if (cmp < 0) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}

		return -1;
	}

	public static boolean[] corrigerRapide(String[] correct, String[] words) {
		boolean[] valids = new boolean[correct.length];

		for (int i = 0; i < correct.length; i++) {
			int index = Main.rechercheBinaire(words, correct[i].toLowerCase());
			valids[i] = index >= 0;
		}

		return valids;
	}

	public static boolean[] corrigerDicoRapide(String[] correct) {
		return Main.corrigerRapide(correct, cacheDico.get());
	}

	private static int levenshteinDistance(String word1, String word2) {
		int m = word1.length();
		int n = word2.length();
		int[][] dp = new int[m + 1][n + 1];

		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0) {
					dp[i][j] = j;
				} else if (j == 0) {
					dp[i][j] = i;
				} else {
					int cost = (word1.charAt(i - 1) == word2.charAt(j - 1)) ? 0 : 1;
					dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
				}
			}
		}

		return dp[m][n];
	}

	public static String[] proposerCorrection(String correct) {
		List<String> fixs = new ArrayList<String>();

		for (String word : cacheDico.get()) {

			if (levenshteinDistance(correct, word) < 2) {
				fixs.add(word);
				if (fixs.size() > 10) {
					break;
				}
			}
		}

		return fixs.toArray(new String[0]);
	}

	public static void main(String[] args) {
		cacheDico.load();
		ACX.interfaceCorrection("corrigerDicoRapide", "proposerCorrection");
	}
}
