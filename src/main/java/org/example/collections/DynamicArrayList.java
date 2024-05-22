package org.example.collections;

import java.util.*;

public class DynamicArrayList<E> implements List<E> {
    private static final int INITIAL_SIZE = 10;

    protected Object[] _data;
    protected int _pointer;

    public DynamicArrayList() {
        _data = new Object[INITIAL_SIZE];
        _pointer = 0;
    }

    public DynamicArrayList(Collection<?> collection) {
        this(collection.toArray());
    }

    public DynamicArrayList(Object[] data) {
        _data = new Object[data.length];
        System.arraycopy(data, 0, _data, 0, data.length);
        _pointer = data.length;
    }

    private void increaseSize() {
        int size;
        if (_data.length < 200) {
            size = _data.length * 2;
        } else {
            size = _data.length + (int) Math.sqrt(_data.length) + 1;
        }
        Object[] newData = new Object[size];
        System.arraycopy(_data, 0, newData, 0, _data.length);
        _data = newData;
    }

    @Override
    public int size() {
        return _pointer;
    }

    @Override
    public boolean isEmpty() {
        return _pointer == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < _pointer; i++) {
            if (Objects.equals(_data[i], o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < _pointer;
            }

            @Override
            public E next() {
                return get(currentIndex++);
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[_pointer];
        System.arraycopy(_data, 0, result, 0, _pointer);
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < _pointer) {
            return ((T[]) Arrays.copyOf(_data, _pointer));
        }
        System.arraycopy(_data, 0, a, 0, _pointer);
        return a;
    }

    @Override
    public boolean add(E e) {
        if (_pointer == _data.length) {
            increaseSize();
        }
        _data[_pointer++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        boolean wasPresent = false;
        for (int i = 0; i < _pointer; i++) {
            if (Objects.equals(_data[i], o)) {
                wasPresent = true;
                if (i != _pointer - 1) {
                    for (int j = i; j < _pointer - 1; j++) {
                        _data[j] = _data[j + 1];
                    }
                }
                _pointer--;
            }
        }
        return wasPresent;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        int found = 0;
        for (Object o : c) {
            if (contains(o)) {
                found++;
            }
        }
        return found == c.size();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(int index, Collection<? extends E> c) {
        E[] array = (E[]) c.toArray();
        for (int i = 0; i < c.size(); i++) {
            add(index + i, array[i]);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean wasPresent = false;
        for (Object o : c) {
            if (remove(o)) {
                wasPresent = true;
            }
        }
        return wasPresent;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        _data = new Object[INITIAL_SIZE];
        _pointer = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) _data[index];
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= _pointer) {
            return null;
        }
        E result = get(index);
        _data[index] = element;
        return result;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > _pointer) {
            throw new IndexOutOfBoundsException();
        }
        if (index == _pointer) {
            add(element);
            return;
        }
        if (_pointer == _data.length) {
            increaseSize();
        }
        for (int i = _pointer; i > index; i--) {
            _data[i] = _data[i - 1];
        }
        _data[index] = element;
        _pointer++;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= _pointer) {
            throw new IndexOutOfBoundsException();
        }
        E result = get(index);
        for (int i = index; i < _pointer - 1; i++) {
            _data[i] = _data[i + 1];
        }
        _pointer--;
        return result;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < _pointer; i++) {
            if (Objects.equals(_data[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int indexFound = -1;
        for (int i = 0; i < _pointer; i++) {
            if (Objects.equals(_data[i], o)) {
                indexFound = i;
            }
        }
        return indexFound;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListIterator<E>() {
            private int _currentIndex = 0;
            private int _lastReturnedIndex = -1;
            private boolean _canRemoveOrSet = false;

            @Override
            public boolean hasNext() {
                return _currentIndex < _pointer;
            }

            @Override
            @SuppressWarnings("unchecked")
            public E next() {
                _canRemoveOrSet = true;
                _lastReturnedIndex = _currentIndex;
                return get(_currentIndex++);
            }

            @Override
            public boolean hasPrevious() {
                return _currentIndex > 0;
            }

            @Override
            public E previous() {
                _canRemoveOrSet = true;
                _currentIndex--;
                _lastReturnedIndex = _currentIndex;
                return get(_currentIndex);
            }

            @Override
            public int nextIndex() {
                return _currentIndex;
            }

            @Override
            public int previousIndex() {
                return _currentIndex - 1;
            }

            @Override
            public void remove() {
                if (!_canRemoveOrSet) {
                    throw new IllegalStateException();
                }
                _canRemoveOrSet = false;
                DynamicArrayList.this.remove(_lastReturnedIndex);
            }

            @Override
            public void set(E e) {
                if (!_canRemoveOrSet) {
                    throw new IllegalStateException();
                }
                DynamicArrayList.this.set(_lastReturnedIndex, e);
            }

            @Override
            public void add(E e) {
                _canRemoveOrSet = false;
                DynamicArrayList.this.add(_currentIndex, e);
                _currentIndex++;
            }
        };
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex >= _pointer || toIndex < 0 || toIndex >= _pointer || fromIndex >= toIndex) {
            throw new IndexOutOfBoundsException();
        }
        return new SubList<>(this, fromIndex, toIndex);
    }

    private static class SubList<E> extends DynamicArrayList<E> {
        //TODO: finish implementation
        private int _start;
        private int _end;

        public SubList(DynamicArrayList<E> list, int start, int end) {
            super();
            _pointer = _start;
            _data = list._data;
            _start = start;
            _end = end;
        }

        private void checkRange(int index) {
            if (_start > index || _end < index) {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public int size() {
            return _pointer - _start;
        }

        @Override
        public boolean isEmpty() {
            return _pointer - _start == 0;
        }

        @Override
        public boolean contains(Object o) {
            return super.contains(o);
        }

        @Override
        public Iterator<E> iterator() {
            return super.iterator();
        }

        @Override
        public Object[] toArray() {
            return super.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return super.toArray(a);
        }

        @Override
        public boolean add(E e) {
            return super.add(e);
        }

        @Override
        public boolean remove(Object o) {
            return super.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return super.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            return super.addAll(c);
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            return super.addAll(index, c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return super.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return super.retainAll(c);
        }

        @Override
        public void clear() {
            super.clear();
        }

        @Override
        public E get(int index) {
            checkRange(index);
            return super.get(index);
        }

        @Override
        public E set(int index, E element) {
            checkRange(index);
            return super.set(index, element);
        }

        @Override
        public void add(int index, E element) {
            checkRange(index);
            super.add(index, element);
        }

        @Override
        public E remove(int index) {
            checkRange(index);
            return super.remove(index);
        }

        @Override
        public int indexOf(Object o) {
            return super.indexOf(o);
        }

        @Override
        public int lastIndexOf(Object o) {
            return super.lastIndexOf(o);
        }

        @Override
        public ListIterator<E> listIterator() {
            return super.listIterator();
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            return super.listIterator(index);
        }
    }
}
