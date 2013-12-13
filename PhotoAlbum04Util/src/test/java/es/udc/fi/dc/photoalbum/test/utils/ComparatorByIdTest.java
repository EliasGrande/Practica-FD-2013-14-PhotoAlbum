package es.udc.fi.dc.photoalbum.test.utils;

import org.junit.Before;
import org.junit.Test;

import es.udc.fi.dc.photoalbum.util.utils.ComparableById;

/**
 * The class <code>ComparatorByIdTest</code> contains tests for the class
 * {@link <code>ComparatorById</code>}.
 */
public class ComparatorByIdTest {
    
    private class MinimalComparableBy implements ComparableById {
        public Integer id;
        public MinimalComparableBy(Integer id) {
            this.id = id;
        }
        public Integer getId() {
            return id;
        }
    }

    @Before
    public void setUp() throws Exception {
        // TODO
    }
    
    @Test
    public void testSortSorted() {
        // TODO
    }
    
    @Test
    public void testSortReverseSorted() {
        // TODO
    }
    
    @Test
    public void testSortNotSorted() {
        // TODO
    }
}