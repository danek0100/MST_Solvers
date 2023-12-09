package edu.discmodalg;

public class DisjointSet {
    private final int[] parent;
    private final int[] rank;

    public DisjointSet(int numOfElements) {
        parent = new int[numOfElements];
        rank = new int[numOfElements];
        for (int i = 0; i < numOfElements; i++) {
            parent[i] = i; // Каждый элемент является своим собственным родителем
            rank[i] = 0;   // Ранг каждого элемента изначально 0
        }
    }

    public int find(int toFind) {
        // Сжатие путей
        if (parent[toFind] != toFind) {
            parent[toFind] = find(parent[toFind]); // Находим корневого представителя и делаем его прямым родителем
        }
        return parent[toFind];
    }

    public void union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);

        // Объединяем деревья по рангу
        if (rootI != rootJ) {
            if (rank[rootI] < rank[rootJ]) {
                parent[rootI] = rootJ;
            } else if (rank[rootJ] < rank[rootI]) {
                parent[rootJ] = rootI;
            } else {
                parent[rootJ] = rootI;
                rank[rootI]++;
            }
        }
    }
}
