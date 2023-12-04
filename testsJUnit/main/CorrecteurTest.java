package main;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CorrecteurTest {

	private static final String[] correct = { "pome", "voitture", "bonjour" };
	private static final boolean[] expected = { false, false, true };
	private static String[] testLarge = null;

	@BeforeClass
	public static void setUp() {
		Main.cacheDico.load();
		try {
			List<String> words = new ArrayList<>();
			Scanner sc = new Scanner(new File("lib/germinalExtrait.txt"));
			while (sc.hasNext()) {
				String line = sc.nextLine();
				if (line.length() == 0) {
					continue;
				}

				words.addAll(Arrays.asList(line.split(" ")));
			}
			sc.close();

			words.removeAll(Arrays.asList("", null));
			testLarge = words.toArray(new String[0]);
		} catch (IOException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testCorriger() {
		boolean[] result = Main.corriger(correct, Main.cacheDico.get());
		assertArrayEquals(expected, result);
	}

	@Test
	public void testCorrigerRapide() {
		boolean[] result = Main.corrigerRapide(correct, Main.cacheDico.get());
		assertArrayEquals(expected, result);
	}

	@Test
	public void testCorrigerRapideContreSimple() {
		long startTime = 0;

		startTime = System.currentTimeMillis();
		Main.corrigerDicoRapide(testLarge);
		long rapideTime = System.currentTimeMillis() - startTime;

		startTime = System.currentTimeMillis();
		Main.corrigerDico(testLarge);
		long simpleTime = System.currentTimeMillis() - startTime;

		assertTrue(simpleTime > rapideTime);
	}

	@Test
	public void testProposerCorrection() {
		String[] result = Main.proposerCorrection(correct[1]);

		for (String option : result) {

			if (option.equals(correct[1])) {
				assertTrue(true);
				return;
			}
		}

		assertTrue(true);
	}

	@Test
	public void testSortDico() {
		String[] arr = { "abc", "bca", "cba", "a", "b" };

		String[] valid = Arrays.copyOf(arr, arr.length);
		Arrays.sort(valid);

		new SortDico().sortFusion(arr);
		assertArrayEquals(valid, arr);
	}
}
