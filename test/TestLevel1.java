import java.util.*;

/**
 * Unit tests for Level 1 (The Small Reserve).
 * No external test framework required.
 *
 * Run with:
 *   javac Main.java TestLevel1.java
 *   java TestLevel1
 */
public class TestLevel1 {

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

    static void assertEquals(String expected, String actual, String testName) {
        if (expected.equals(actual)) {
            System.out.println("PASS: " + testName);
            passed++;
        } else {
            System.out.println("FAIL: " + testName + " -> expected \"" + expected + "\" but got \"" + actual + "\"");
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

    static void testOptimalCost() {
        Main.buildLevel1Graph();
        Main.DijkstraResult result = Main.dijkstra("A", "B");
        assertEquals(9.0, result.cost, "Optimal cost from A to B is 9");
    }

    static void testPathStartsAndEndsCorrectly() {
        Main.buildLevel1Graph();
        Main.DijkstraResult result = Main.dijkstra("A", "B");
        assertEquals("A", result.path.get(0), "Path starts at A");
        assertEquals("B", result.path.get(result.path.size() - 1), "Path ends at B");
    }

    static void testPathIsConnected() {
        Main.buildLevel1Graph();
        Main.DijkstraResult result = Main.dijkstra("A", "B");
        boolean connected = true;
        for (int i = 0; i < result.path.size() - 1; i++) {
            String from = result.path.get(i);
            String to = result.path.get(i + 1);
            boolean edgeExists = Main.graph.get(from).stream().anyMatch(e -> e.to.equals(to));
            if (!edgeExists) connected = false;
        }
        assertTrue(connected, "Every consecutive pair in the path has a real edge");
    }

    static void testSameNode() {
        Main.buildLevel1Graph();
        Main.DijkstraResult result = Main.dijkstra("A", "A");
        assertEquals(0.0, result.cost, "Cost from a node to itself is 0");
    }

    static void testUnknownNodeIsUnreachable() {
        Main.buildLevel1Graph();
        Main.DijkstraResult result = Main.dijkstra("A", "Z"); // Z does not exist in this graph
        assertTrue(result.cost == Double.POSITIVE_INFINITY, "Unreachable/unknown node returns infinite cost");
    }

    public static void main(String[] args) {
        System.out.println("Running Level 1 tests...\n");
        testOptimalCost();
        testPathStartsAndEndsCorrectly();
        testPathIsConnected();
        testSameNode();
        testUnknownNodeIsUnreachable();

        System.out.println("\n" + passed + " passed, " + failed + " failed.");
        if (failed > 0) {
            System.exit(1);
        }
    }
}
