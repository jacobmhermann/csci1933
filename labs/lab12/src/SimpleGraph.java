/**
 This is a class derived and adapted from the textbook: DirectedGraph.java
 A class that implements the ADT directed graph.

 @author Frank M. Carrano
 @author Timothy M. Henry
 @version 4.0
 */
import java.util.*;

public class SimpleGraph<T> implements GraphInterface<T> {
    private HashMap<T, VertexInterface<T>> vertices;
    private int edgeCount;

    public SimpleGraph() {
        vertices = new HashMap<T, VertexInterface<T>>();
        edgeCount = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addVertex(T vertexLabel) {
        return null == vertices.put(vertexLabel, new Vertex<>(vertexLabel));
    }

    @Override
    public boolean addEdge(T begin, T end, double edgeWeight) {
        boolean result = false;

        VertexInterface<T> beginVertex = vertices.get(begin);
        VertexInterface<T> endVertex = vertices.get(end);

        if ((beginVertex != null) && (endVertex != null))
            result = beginVertex.connect(endVertex, edgeWeight);

        if (result)
            edgeCount++;

        return result;
    }

    @Override
    public boolean addEdge(T begin, T end) {
        return addEdge(begin, end, 0);
    }

    @Override
    public boolean hasEdge(T begin, T end) {
        boolean found = false;

        VertexInterface<T> beginVertex = vertices.get(begin);
        VertexInterface<T> endVertex = vertices.get(end);

        if ((beginVertex != null) && (endVertex != null)) {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    found = true;
            } // end while
        } // end if

        return found;
    }

    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    @Override
    public int getNumberOfVertices() {
        return vertices.size();
    }

    @Override
    public int getNumberOfEdges() {
        return edgeCount;
    }

    @Override
    public void clear() {
        vertices.clear();
        edgeCount = 0;
    }

    @Override
    public VertexInterface<T> getVertex(T label) {
        try {
            return vertices.get(label);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private class VertexIterator implements Iterator<VertexInterface<T>> {
        private Iterator<Map.Entry<T, VertexInterface<T>>> verticeIterator;

        private VertexIterator() {
            verticeIterator = vertices.entrySet().iterator();
        } // end default constructor

        public boolean hasNext() {
            return verticeIterator.hasNext();
        } // end hasNext

        public VertexInterface<T> next() {
            return verticeIterator.hasNext() ? vertices.get(verticeIterator.next().getValue().getLabel()) : null;
        } // end next
    }

    public VertexIterator getVertexIterator() {
        return new VertexIterator();
    }

    @Override
    public Queue<VertexInterface<T>> breadthFirstSearch(T origin) {
        Queue<VertexInterface<T>> neighbors = new LinkedList<>();
        VertexInterface<T> originVertex = vertices.get(origin);
        neighbors.add(originVertex);
        originVertex.visit();

        while (neighbors.size() < getNumberOfVertices()) {
            Queue<VertexInterface<T>> newNeighbors = new LinkedList<>();
            for (VertexInterface<T> vertex : neighbors) {
                Iterator<VertexInterface<T>> neighborsIterator = vertex.getNeighborIterator();
                while (neighborsIterator.hasNext()) {
                    VertexInterface<T> neighbor = neighborsIterator.next();
                    if (!neighbor.isVisited()) {
                        neighbor.visit();
                        newNeighbors.add(neighbor);
                    }
                }
            }
            while (!newNeighbors.isEmpty()) {
                neighbors.add(newNeighbors.remove());
            }
        }

        return neighbors;
    }

    @Override
    public Queue<VertexInterface<T>> depthFirstSearchRecursive(T origin) {
        Queue<VertexInterface<T>> queue = new LinkedList<>();
        queue.add(getVertex(origin));
        getVertex(origin).visit();
        depthFirstSearchHelper(origin,queue);
        return queue;
    }

    private void depthFirstSearchHelper(T origin, Queue<VertexInterface<T>> queue) {
        boolean hasNewNeighbor = true;
        VertexInterface<T> thisVertex = getVertex(origin);
        while (hasNewNeighbor) {
            hasNewNeighbor = false;
            Iterator<VertexInterface<T>> thisIterator = thisVertex.getNeighborIterator();
            while (! hasNewNeighbor && thisIterator.hasNext()) {
                VertexInterface<T> nextVertex = thisIterator.next();
                if (! nextVertex.isVisited()) {
                    hasNewNeighbor = true;
                    queue.add(nextVertex);
                    nextVertex.visit();
                    depthFirstSearchHelper(nextVertex.getLabel(),queue);
                }
            }
        }
    }

    @Override
    public Queue<VertexInterface<T>> depthFirstSearchIterative(T origin) {

        Stack<VertexInterface<T>> stack = new Stack<>();
        Queue<VertexInterface<T>> queue = new LinkedList<>();

        VertexInterface<T> originVertex = vertices.get(origin);
        stack.push(originVertex);

        while (! stack.isEmpty()) {
            VertexInterface<T> vertex = stack.pop();
            if (! vertex.isVisited()) {
                vertex.visit();
                queue.add(vertex);
                Iterator<VertexInterface<T>> neighbors = vertex.getNeighborIterator();
                while (neighbors.hasNext()) {
                    VertexInterface<T> neighbor = neighbors.next();
                    if (! neighbor.isVisited()) {
                        stack.push(neighbor);
                    }

                }
            }
        }
        return queue;
    }
}