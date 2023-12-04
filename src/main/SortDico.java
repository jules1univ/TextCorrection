package main;

public class SortDico {

	public SortDico() {

	}

	public void sortFusion(String arr[]) {
		int longueur = arr.length;
		if (longueur > 0) {
			sortFusion(arr, 0, longueur - 1);
		}
	}

	private void sortFusion(String arr[], int start, int end) {
		if (start == end) {
			return;
		}

		int mid = (end + start) / 2;
		sortFusion(arr, start, mid);
		sortFusion(arr, mid + 1, end);

		fusion(arr, start, mid, end);
	}

	private void fusion(String arr[], int start1, int end1, int end2) {

		String[] cpy_arr = new String[end1 - start1 + 1];
		for (int i = start1; i <= end1; i++) {
			cpy_arr[i - start1] = arr[i];
		}

		int start2 = end1 + 1;
		int cmp2 = start2;

		int cmp1 = start1;

		for (int i = start1; i <= end2; i++) {
			if (cmp1 == start2) {
				break;
			} else if (cmp2 == (end2 + 1)) {
				arr[i] = cpy_arr[cmp1 - start1];
				cmp1++;
			} else if (arr[cmp2].compareTo(cpy_arr[cmp1 - start1]) > 0) {
				arr[i] = cpy_arr[cmp1 - start1];
				cmp1++;
			} else {
				arr[i] = arr[cmp2];
				cmp2++;
			}
		}
	}

}
