# Ranger's Rescue Route — Solution

A two-level shortest path optimisation solution using Dijkstra's algorithm, written in Java.

## Overview

- **Level 1**: Finds the shortest path from A to B on a small weighted graph using standard Dijkstra.
- **Level 2**: Finds the optimal route from A to B that also visits four required stations (S1, S2, S3, S4) in any order, using risk-adjusted edge weights (`effective weight = time + risk`). All 24 possible orderings of the stations are brute-forced, running Dijkstra between each consecutive stop, and the cheapest overall ordering is selected.

## Requirements

- Java JDK 11 or higher (tested on JDK 21)
- No external libraries required — uses only the Java standard library

## How to Run

1. Clone or download this repository.
2. Open a terminal in the project folder.
3. Compile the program:
   ```bash
   javac Main.java
   ```
4. Run the program:
   ```bash
   java Main
   ```

## Output

The program prints results for both levels to the console, including:
- The computed route (list of nodes from start to end)
- The total route cost
- A ready-to-submit JSON object in the format required by the hackathon platform, e.g.:
  ```json
  { "route": ["A", "D", "E", "B"] }
  ```

Copy the printed JSON for each level into your `answer.txt` / `submission.json` file before uploading to the hackathon platform.

## Verifying Correctness

The known optimal costs for this practice problem are:
- Level 1: **9**
- Level 2: **60**

If the program's printed cost for a level doesn't match, double-check the graph data in `buildLevel1Graph()` / `buildLevel2Graph()` against the problem statement before submitting.

## Running the Unit Tests

`Test.java` contains lightweight, dependency-free unit tests (no JUnit needed) covering both levels:

- **Level 1**: optimal cost equals 9, path starts/ends at the right nodes, every step in the path follows a real edge, and self-distance is 0.
- **Level 2**: optimal cost equals 60, there are exactly 24 station orderings (4!), risk is correctly added to time on an edge, and the best route actually visits all four required stations.

To compile and run the tests:

```bash
javac Main.java Test.java
java Test
```

Expected output ends with:

```
10 passed, 0 failed.
```

If a test fails, it prints the expected vs. actual value so you can pinpoint the issue before submitting.

## Project Structure

```
rangers-rescue-route/
├── Main.java     # Dijkstra implementation + Level 1 and Level 2 solvers
├── Test.java     # Unit tests for Level 1 and Level 2
└── README.md     # This file
```

## Approach Summary

1. **Graph representation**: adjacency list built from the problem's edge data, stored as `Map<String, List<Edge>>`.
2. **Dijkstra**: standard priority-queue-based implementation, returning both the total cost and the reconstructed path.
3. **Level 2 station ordering**: since there are only 4 required stops (4! = 24 orderings), all permutations are generated and evaluated exhaustively rather than using a heuristic — this guarantees the optimal ordering is found.
4. **Route stitching**: for Level 2, individual leg paths are concatenated into one continuous route, removing duplicate junction nodes where legs connect.