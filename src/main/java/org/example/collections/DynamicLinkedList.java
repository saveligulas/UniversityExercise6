package org.example.collections;

import java.util.*;

public class DynamicLinkedList<E> implements List<E> {
    private static class Node<T> {
        private T _value;
        private Node<T> _parent;
        private Node<T> _child;

        public Node() {
            this(null);
        }

        public Node(T value) {
            this(value, null);
        }

        public Node(T value, Node<T> parent) {
            this(value, parent, null);
        }

        public Node(T value, Node<T> parent, Node<T> child) {
            _value = value;
            _parent = parent;
            _child = child;
        }

        public T setValue(T value) {
            T oldValue = _value;
            _value = value;
            return oldValue;
        }

        public void addChild(T value) {
            this.linkChild(new Node<>(value));
        }

        public void linkParent(Node<T> parent) {
            parent.linkChild(this);
        }

        public void linkChild(Node<T> child) {
            _child = child;
            _child._parent = this;
        }

        public void linkGrandChild() {
            if (!hasGrandChild()) {
                throw new NullPointerException();
            }
            linkChild(_child.getChild());
        }

        public void unlinkParent() {
            _parent.unlinkChild();
        }

        public void unlinkChild() {
            _child._parent = null;
            _child = null;
        }

        public boolean hasChild() {
            return _child != null;
        }

        public boolean hasGrandChild() {
            return _child.hasChild();
        }

        public T getValue() {
            return _value;
        }

        public Node<T> getParent() {
            return _parent;
        }

        public Node<T> getChild() {
            return _child;
        }
    }

    private final Node<E> _source;
    private int _size;

    public DynamicLinkedList() {
        _source = new Node<E>();
        _size = 0;
    }

    private Node<E> getNode(int index) {
        if (index < 0 || index >= _size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> currentNode = _source;
        for (int i = 0; i <= index; i++) {
            currentNode = currentNode.getChild();
        }
        return currentNode;
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public boolean isEmpty() {
        return _size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return null;
    }

    @Override
    public boolean add(E e) {
        Node<E> lastNode = _source;
        for (int i = 0; i < _size; i++) {
            lastNode = lastNode.getChild();
        }
        _size++;
        lastNode.addChild(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node<E> currentNode = _source;
        boolean removed = false;
        for (int i = 0; i < _size; i++) {
            currentNode = currentNode.getChild();
            if (Objects.equals(currentNode.getValue(), o)) {
                removed = true;
                _size--;
                if (i == _size - 1) {
                    currentNode.unlinkParent();
                } else {
                    currentNode.getParent().linkGrandChild();
                }
            }
        }
        return removed;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return false;
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> collection) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int i) {
        if (i < 0 || i >= _size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> currentNode = _source;
        for (int j = 0; j <= i; j++) {
            currentNode = currentNode.getChild();
        }
        return currentNode.getValue();
    }

    @Override
    public E set(int i, E e) {
        Node<E> node = getNode(i);
        E oldValue = node.getValue();
        node.setValue(e);
        return oldValue;
    }

    @Override
    public void add(int i, E e) {
        Node<E> child = getNode(i);
        Node<E> node = new Node<>(e);
        Node<E> parent = child.getParent();
        child.unlinkParent();
        node.linkParent(parent);
        child.linkParent(node);
    }

    @Override
    public E remove(int i) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        return null;
    }

    @Override
    public List<E> subList(int i, int i1) {
        return null;
    }
}
