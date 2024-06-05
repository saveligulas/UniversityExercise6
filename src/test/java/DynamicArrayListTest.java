import org.example.collections.DynamicArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class DynamicArrayListTest {
    private DynamicArrayList<Integer> list;

    @BeforeEach
    public void setUp() {
        list = new DynamicArrayList<>();
    }

    @Test
    public void testSize() {
        assertEquals(0, list.size());
        list.add(1);
        assertEquals(1, list.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.add(1);
        assertFalse(list.isEmpty());
    }

    @Test
    public void testContains() {
        list.add(1);
        assertTrue(list.contains(1));
        assertFalse(list.contains(2));
    }

    @Test
    public void testIterator() {
        list.add(1);
        list.add(2);
        Iterator<Integer> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testToArray() {
        list.add(1);
        list.add(2);
        Object[] array = list.toArray();
        assertArrayEquals(new Object[]{1, 2}, array);
    }

    @Test
    public void testToArrayWithType() {
        list.add(1);
        list.add(2);
        Integer[] array = new Integer[2];
        Integer[] result = list.toArray(array);
        assertArrayEquals(new Integer[]{1, 2}, result);
    }

    @Test
    public void testAdd() {
        assertTrue(list.add(1));
        assertEquals(1, list.size());
        assertEquals(1, list.get(0));
    }

    @Test
    public void testRemoveObject() {
        list.add(1);
        list.add(2);
        assertTrue(list.remove(Integer.valueOf(1)));
        assertEquals(1, list.size());
        assertFalse(list.contains(1));
        assertTrue(list.contains(2));
    }

    @Test
    public void testContainsAll() {
        list.add(1);
        list.add(2);
        List<Integer> sublist = Arrays.asList(1, 2);
        assertTrue(list.containsAll(sublist));
        sublist = Arrays.asList(1, 3);
        assertFalse(list.containsAll(sublist));
    }

    @Test
    public void testAddAll() {
        List<Integer> sublist = Arrays.asList(1, 2, 3);
        assertTrue(list.addAll(sublist));
        assertEquals(3, list.size());
        assertTrue(list.containsAll(sublist));
    }

    @Test
    public void testAddAllAtIndex() {
        list.add(1);
        List<Integer> sublist = Arrays.asList(2, 3);
        assertTrue(list.addAll(1, sublist));
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    public void testRemoveAll() {
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> sublist = Arrays.asList(1, 3);
        assertTrue(list.removeAll(sublist));
        assertEquals(1, list.size());
        assertTrue(list.contains(2));
        assertFalse(list.contains(1));
        assertFalse(list.contains(3));
    }

    @Test
    public void testRetainAll() {
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> sublist = Arrays.asList(1, 3);
        assertTrue(list.retainAll(sublist));
        assertEquals(2, list.size());
        assertTrue(list.contains(1));
        assertFalse(list.contains(2));
        assertTrue(list.contains(3));
    }

    @Test
    public void testClear() {
        list.add(1);
        list.add(2);
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    public void testGet() {
        list.add(1);
        assertEquals(1, list.get(0));
    }

    @Test
    public void testSet() {
        list.add(1);
        assertEquals(1, list.set(0, 2));
        assertEquals(2, list.get(0));
    }

    @Test
    public void testAddAtIndex() {
        list.add(1);
        list.add(1, 2);
        assertEquals(2, list.get(1));
        assertEquals(2, list.size());

        list.add(0, 0);
        assertEquals(0, list.get(0));
        assertEquals(3, list.size());
        assertEquals(1, list.get(1));
        assertEquals(2, list.get(2));
    }

    @Test
    public void testRemoveAtIndex() {
        list.add(1);
        list.add(2);
        assertEquals(1, list.remove(0));
        assertEquals(1, list.size());
        assertEquals(2, list.get(0));
    }

    @Test
    public void testIndexOf() {
        list.add(1);
        list.add(2);
        assertEquals(0, list.indexOf(1));
        assertEquals(1, list.indexOf(2));
        assertEquals(-1, list.indexOf(3));
    }

    @Test
    public void testLastIndexOf() {
        list.add(1);
        list.add(2);
        list.add(1);
        assertEquals(2, list.lastIndexOf(1));
        assertEquals(1, list.lastIndexOf(2));
        assertEquals(-1, list.lastIndexOf(3));
    }

    @Test
    public void testListIterator() {
        list.add(1);
        list.add(2);
        list.add(3);

        ListIterator<Integer> listIterator = list.listIterator();

        // Test hasNext and next
        assertTrue(listIterator.hasNext());
        assertEquals(0, listIterator.nextIndex());
        assertEquals(1, listIterator.next());
        assertTrue(listIterator.hasNext());
        assertEquals(1, listIterator.nextIndex());
        assertEquals(2, listIterator.next());
        assertTrue(listIterator.hasNext());
        assertEquals(2, listIterator.nextIndex());
        assertEquals(3, listIterator.next());
        assertFalse(listIterator.hasNext());

        // Test hasPrevious and previous
        assertTrue(listIterator.hasPrevious());
        assertEquals(2, listIterator.previousIndex());
        assertEquals(3, listIterator.previous());
        assertTrue(listIterator.hasPrevious());
        assertEquals(1, listIterator.previousIndex());
        assertEquals(2, listIterator.previous());
        assertTrue(listIterator.hasPrevious());
        assertEquals(0, listIterator.previousIndex());
        assertEquals(1, listIterator.previous());
        assertFalse(listIterator.hasPrevious());

        // Reset iterator and test set
        listIterator = list.listIterator();
        listIterator.next();
        listIterator.set(4);
        assertEquals(4, list.get(0));

        // Test add
        listIterator.add(5);
        assertEquals(5, list.get(1));
        assertEquals(2, list.get(2));
        assertEquals(4, list.size()); // Expecting 3 elements: 4, 5, 2

        // Move iterator to the end
        assertEquals(2, listIterator.next());
        assertEquals(3, listIterator.next());
        assertFalse(listIterator.hasNext());

        // Move iterator back and test remove
        listIterator.previous(); // Points to 2
        listIterator.remove(); // Remove 2
        assertEquals(3, list.size());
        assertEquals(4, list.get(0));
        assertEquals(5, list.get(1));
    }
}
