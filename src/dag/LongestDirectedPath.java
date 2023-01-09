
package dag;

import java.util.*;

public class LongestDirectedPath {
  static class Vertex {
    int id;
  }

  static class Edge {
    Vertex from = new Vertex();
    Vertex to = new Vertex();
    int distance;

    public Edge(int from, int to, int distance) {
      this.from.id = from;
      this.to.id = to;
      this.distance = distance;
    }
  }

  private static int deptFirstSearch(Map<Integer, List<Edge>> graph, int index, int position, int[] ordering, boolean[] hasVisited) {

    hasVisited[position] = true;

    List<Edge> edges = graph.get(position);
    if (edges != null) {
      for (Edge edge : edges) {
        if (!hasVisited[edge.to.id]) {
          index = deptFirstSearch(graph, index, edge.to.id, ordering, hasVisited);
        }
      }
    }

    ordering[index] = position;
    return index - 1;
  }

  public static int[] orderTopologically(int nodesCount, Map<Integer, List<Edge>> graph) {

    boolean[] hasVisited = new boolean[nodesCount];
    int[] ordering = new int[nodesCount];

    int index = nodesCount - 1;
    for (int position = 0; position < nodesCount; position++) {
      if (!hasVisited[position]) {
        index = deptFirstSearch(graph, index, position, ordering, hasVisited);
      }
    }

    return ordering;
  }

  public static Integer[] longestPath(int nodesCount, Map<Integer, List<Edge>> graph, int start) {

    Integer[] distances = new Integer[nodesCount];
    int[] orderedArr = orderTopologically(nodesCount, graph);
    distances[start] = 0;

    for (int i = 0; i < nodesCount; i++) {

      int indexOfNode = orderedArr[i];
      if (distances[indexOfNode] != null) {
        List<Edge> edges = graph.get(indexOfNode);
        if (edges != null) {
          for (Edge edge : edges) {

            int distance = distances[indexOfNode] + edge.distance;
            if (distances[edge.to.id] == null) {
              distances[edge.to.id] = distance;
            }
            else {
              distances[edge.to.id] = Math.max(distances[edge.to.id], distance);
            }
          }
        }
      }
    }

    return distances;
  }

  static Map<Integer, List<Edge>> createGraph(int numberOfNodes) {
    Map<Integer, List<Edge>> graph = new HashMap<>();
    for (int i = 0; i < numberOfNodes; i++) {
      graph.put(i, new ArrayList<>());
    }
    graph.get(0).add(new Edge(0, 1, 4));
    graph.get(0).add(new Edge(0, 3, 2));
    graph.get(0).add(new Edge(0, 4, 9));
    graph.get(1).add(new Edge(1, 2, 3));
    graph.get(1).add(new Edge(1, 3, 3));
    graph.get(2).add(new Edge(2, 5, 1));
    graph.get(3).add(new Edge(3, 2, 2));
    graph.get(3).add(new Edge(3, 4, 1));
    graph.get(3).add(new Edge(3, 5, 4));
    graph.get(4).add(new Edge(4, 5, 6));

    return graph;
  }

  public static void main(String[] args) {

    int numberOfNodes = 6;
    Map<Integer, List<Edge>> graph = createGraph(numberOfNodes);

    int start = 0;
    // Longest paths starting from node start
    Integer[] distances = longestPath(numberOfNodes, graph, start);

    System.out.println("Distances");
    System.out.println(Arrays.toString(distances));

    // Longest path from start to 5
    System.out.println(distances[5]);
  }
}
