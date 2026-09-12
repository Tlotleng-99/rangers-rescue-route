import java.util.*;

/**
 * Unit tests for Level 2 (The Great Savannah).
 * No external test framework required.
 *
 * Run with:
 *   javac Main.java TestLevel2.java
 *   java TestLevel2
 */
public class TestLevel2 {

    static int passed = 0;
    static int failed = 0;

    static void assertEquals(double expected, double actual, String testName) {
        if (Math.abs(expected - actual) < 1e-9) {
            System.out.println("PASS: " + testName);
            passed++;
        } else {
            System.out.println("FAIL: " + testName + " -> expected " + expected + " but got " + actual);
            failed++;
        }
    }

    static void assertTrue(boolean condition, String testName) {
        if (condition) {
            System.out.println("PASS: " + testName);
            passed++;
        } else {
            System.out.println("FAIL: " + testName);
            failed++;
        }
    }

    // Helper: run the full Level 2 search (same logic as Main.solveLevel2, but returning values for assertions)
    static double bestCost;
    static List<String> bestRoute;

    static void runLevel2Search() {
        Main.buildLevel2Graph();
        List<String> stations = Arrays.asList("S1", "S2", "S3", "S4");
        List<List<String>> allOrderings = Main.permutations(new ArrayList<>(stations));

        bestCost = Double.POSITIVE_INFINITY;
        bestRoute = null;

        for (List<String> ordering : allOrderings) {
            List<String> stops = new ArrayList<>();
            stops.add("A");
            stops.addAll(ordering);
            stops.add("B");

            double totalCost = 0;
            List<String> fullRoute = new ArrayList<>();
            for (int i = 0; i < stops.size() - 1; i++) {
                Main.DijkstraResult leg = Main.dijkstra(stops.get(i), stops.get(i + 1));
                totalCost += leg.cost;
                if (i == 0) fullRoute.addAll(leg.path);
                else fullRoute.addAll(leg.path.subList(1, leg.path.size()));
            }
            if (totalCost < bestCost) {
                bestCost = totalCost;
                bestRoute = fullRoute;
            }
        }
    }

    static void testOptimalCost() {
        runLevel2Search();
        assertEquals(60.0, bestCost, "Optimal total cost across all station orderings is 60");
    }

    static void testPermutationCount() {
        List<String> stations = Arrays.asList("S1", "S2", "S3", "S4");
        List<List<String>> allOrderings = Main.permutations(new ArrayList<>(stations));
        assertEquals(24.0, allOrderings.size(), "4 stations produce 4! = 24 orderings");
    }

    static void testRiskAddedToTime() {
        // A -> P6 has time=5, risk=2, so effective weight must be 7
        Main.buildLevel2Graph();
        boolean found = false;
        for (Main.Edge e : Main.graph.get("A")) {
            if (e.to.equals("P6")) {
                assertEquals(7.0, e.weight, "Effective weight A->P6 is time+risk = 5+2 = 7");
                found = true;
            }
        }
        assertTrue(found, "Edge A->P6 exists in the graph");
    }

    static void testAllStationsVisitedInBestRoute() {
        runLevel2Search();
        List<String> stations = Arrays.asList("S1", "S2", "S3", "S4");
        boolean allVisited = bestRoute.containsAll(stations);
        assertTrue(allVisited, "Best route visits all four required stations");
    }

    static void testRouteStartsAndEndsCorrectly() {
        runLevel2Search();
        assertTrue(bestRoute.get(0).equals("A"), "Best route starts at A");
        assertTrue(bestRoute.get(bestRoute.size() - 1).equals("B"), "Best route ends at B");
    }

    public static void main(String[] args) {
        System.out.println("Running Level 2 tests...\n");
        testOptimalCost();
        testPermutationCount();
        testRiskAddedToTime();
        testAllStationsVisitedInBestRoute();
        testRouteStartsAndEndsCorrectly();

        System.out.println("\n" + passed + " passed, " + failed + " failed.");
        if (failed > 0) {
            System.exit(1);
        }
    }
}
