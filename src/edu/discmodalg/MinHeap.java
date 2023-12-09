package edu.discmodalg;

import java.util.*;

public class MinHeap<T extends Comparable<T>>  {

    private final List<T> heap;
    private final Map<T, Integer> positionMap;

    public MinHeap() {
        this.heap = new ArrayList<>();
        this.positionMap = new HashMap<>();
    }

    public void insert(T element) {
        heap.add(element);
        int index = heap.size() - 1;
        positionMap.put(element, index);
        heapifyUp(index);
    }

    public T extractMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        T min = heap.get(0);
        int lastIndex = heap.size() - 1;
        heap.set(0, heap.get(lastIndex));
        positionMap.put(heap.get(0), 0);
        heap.remove(lastIndex);
        positionMap.remove(min);

        if (!isEmpty()) {
            heapifyDown(0);
        }

        return min;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).compareTo(heap.get(parentIndex)) >= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void heapifyDown(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int smallest = index;

        if (leftChild < heap.size() && heap.get(leftChild).compareTo(heap.get(smallest)) < 0) {
            smallest = leftChild;
        }

        if (rightChild < heap.size() && heap.get(rightChild).compareTo(heap.get(smallest)) < 0) {
            smallest = rightChild;
        }

        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
        positionMap.put(heap.get(i), i);
        positionMap.put(heap.get(j), j);
    }

    public void decreaseKey(T element, T newKey) {
        if (!positionMap.containsKey(element)) {
            throw new IllegalStateException("Element not found in heap");
        }

        int index = positionMap.get(element);
        heap.set(index, newKey);
        positionMap.remove(element);
        positionMap.put(newKey, index);

        heapifyUp(index);
    }

}
