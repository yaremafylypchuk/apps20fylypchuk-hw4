package ua.edu.ucu.immutable;

import java.util.Arrays;

public class ImmutableLinkedList implements ImmutableList {
    private int size;
    private Node head;
    private Node tail;

    public ImmutableLinkedList(Node next) {
        head = next;
    }

    static class Node {
        private Object value;
        private Node next;

        private Node(Object value) {
            this.value = value;
            next = null;
        }
    }

    public ImmutableLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    public ImmutableLinkedList(Object[] objects) {
        this();
        if (objects.length > 0) {
            head = new Node(objects[0]);
            Node curNode = head;
            for (int i = 1; i < objects.length; i++) {
                curNode.next = new Node(objects[i]);
                curNode = curNode.next;
            }
            tail = curNode;
            size = objects.length;
        }
    }

    private ImmutableLinkedList copy() {
        if (this.size == 0) {
            return new ImmutableLinkedList();
        }
        ImmutableLinkedList result = new ImmutableLinkedList();
        result.size = this.size;
        result.head = new Node(this.head.value);
        Node original = this.head.next;
        Node neww = result.head;
        while (original != null) {
            neww.next = new Node(original.value);
            neww = neww.next;
            original = original.next;
        }
        return result;
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return add(this.size, e);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        CheckBounds(index);
        int resIndex = 0;
        ImmutableLinkedList res = copy();
        Node reshead = res.head;
        if (reshead == null) {
            res.head = new Node(e);
        }
        if (index == 0) {
            Node node = new Node(e);
            node.next = reshead;
            ImmutableLinkedList result = new ImmutableLinkedList(node);
            result.size = res.size + 1;
            return result;
        }
        while (reshead != null) {
            if (index - 1 == resIndex) {
                Node cur = reshead.next;
                reshead.next = new Node(e);
                reshead = reshead.next;
                reshead.next = cur;
            }
            reshead = reshead.next;
            resIndex++;
        }
        res.size++;
        return res;
    }


    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(this.size, c);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        CheckBounds(index);
        ImmutableLinkedList result = copy();
        for (Object o : c) {
            result = result.add(index, o);
            index++;
        }
        return result;
    }

    @Override
    public Object get(int index) {
        CheckBounds(index);
        int ind = 0;
        Node cur = head;
        while (ind != index) {
            ind++;
            cur = cur.next;
        }
        return cur.value;
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        CheckBounds(index);
        ImmutableLinkedList res = copy();
        Node reshead = res.head;
        int resIndex = 0;
        if (index == 0) {
            ImmutableLinkedList result = new ImmutableLinkedList(reshead.next);
            result.size = res.size - 1;
            return result;
        }
        while (resIndex != index - 1) {
            reshead = reshead.next;
            resIndex++;
        }
        Node previous = reshead;
        previous.next = reshead.next.next;
        res.size--;
        return res;
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        CheckBounds(index);
        ImmutableLinkedList res = copy();
        Node reshead = res.head;
        int curindex = 0;
        while (curindex != index - 1) {
            reshead = reshead.next;
            curindex++;
        }
        Node cur = reshead.next;
        reshead.next = new Node(e);
        reshead.next.next = cur.next;
        return res;
    }

    @Override
    public int indexOf(Object e) {
        int index = 0;
        Node curhead = head;
        while (curhead != null) {
            if (curhead.value == e) {
                return index;
            }
            curhead = curhead.next;
            index++;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public String toString() {
        return Arrays.toString(this.toArray());
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node curNode = head;
        int i = 0;
        while (curNode != null) {
            array[i] = curNode.value;
            curNode = curNode.next;
            i++;
        }
        return array;
    }

    public ImmutableLinkedList addFirst(Object e) {
        return add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        return add(e);
    }

    public Object getFirst() {
        return head.value;
    }

    public Object getLast() {
        return tail.value;
    }

    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return remove(size - 1);
    }

    private void CheckBounds(int index) {
        if (size < index || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }
}
