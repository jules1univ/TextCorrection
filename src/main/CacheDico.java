package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CacheDico {
	private String[] words;
	private final File sortedFile = new File("lib/sorted.txt");
	private final File dicoFile = new File("lib/dico.txt");

	public CacheDico() {
		this.words = null;
	}

	public boolean load() {
		if (sortedFile.exists() && sortedFile.isFile()) {
			List<String> lwords = this.read(sortedFile);
			this.words = new String[lwords.size()];
			lwords.toArray(this.words);

			return true;
		}

		if (!dicoFile.exists() || !dicoFile.isFile()) {
			return false;
		}

		List<String> lwords = this.read(dicoFile);
		this.words = new String[lwords.size()];
		lwords.toArray(this.words);

		new Thread(() -> {
			SortDico sortDico = new SortDico();
			sortDico.sortFusion(this.words);

			this.write(this.sortedFile, this.words);
		}).start();

		return true;
	}

	public String[] get() {
		return this.words;
	}

	private List<String> read(File file) {
		List<String> words = new ArrayList<>();
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNext()) {
				String value = sc.nextLine();
				if (value.startsWith("%") || value.length() == 0) {
					continue;
				}

				words.add(value);
			}
			sc.close();
			return words;
		} catch (FileNotFoundException e) {
			return null;
		}

	}

	private boolean write(File file, String[] arr) {

		try (FileWriter fileWriter = new FileWriter(file)) {
			try {

				for (String word : arr) {
					fileWriter.write(String.format("%s\n", word));
				}
			} catch (Exception e) {
				return false;
			} finally {
				fileWriter.close();
			}

		} catch (IOException e) {
			return false;
		}

		return true;
	}

}
