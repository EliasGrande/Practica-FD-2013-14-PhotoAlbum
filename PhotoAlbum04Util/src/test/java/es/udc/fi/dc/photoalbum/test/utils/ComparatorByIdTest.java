package es.udc.fi.dc.photoalbum.test.utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.util.utils.ComparableById;
import es.udc.fi.dc.photoalbum.util.utils.ComparatorById;

/**
 * The class <code>ComparatorByIdTest</code> contains tests for the
 * class {@link <code>ComparatorById</code>}.
 */
public class ComparatorByIdTest {

    private MinimalComparableById element0 = new MinimalComparableById(0);
    private MinimalComparableById element1 = new MinimalComparableById(1);
    private MinimalComparableById element2 = new MinimalComparableById(2);
    private ComparatorById comparator = new ComparatorById();
    private List<ComparableById> list;

    private class MinimalComparableById implements ComparableById {

        private Integer id;

        public MinimalComparableById(Integer id) {
            this.id = id;
        }

        @Override
        public Integer getId() {
            return id;
        }
    }

    private void auxTestSort(ComparableById pos0,
            ComparableById pos1, ComparableById pos2) {
        list = Arrays
                .asList(new ComparableById[] { pos0, pos1, pos2 });
        Collections.sort(list, comparator);
        assertSame(element0, list.get(0));
        assertSame(element1, list.get(1));
        assertSame(element2, list.get(2));
    }

    @Test
    public void testSort() {
        auxTestSort(element0, element1, element2);
        auxTestSort(element0, element2, element1);
        auxTestSort(element1, element0, element2);
        auxTestSort(element1, element2, element0);
        auxTestSort(element2, element0, element1);
        auxTestSort(element2, element1, element0);
    }
}