import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class StringBlockSeekerTest {
    static private BlockSeeker blockSeeker;

    @BeforeAll
    static void setUp() {
        blockSeeker = new StringBlockSeeker("Start", "End");
    }

    @Test
    void isStartTest() {
        assertTrue(blockSeeker.isStart("Start"));
        assertFalse(blockSeeker.isStart("End"));
        assertFalse(blockSeeker.isStart(null));
        assertFalse(blockSeeker.isStart(""));
    }

    @Test
    void isEndTest() {
        assertTrue(blockSeeker.isEnd("End"));
        assertFalse(blockSeeker.isEnd("Start"));
        assertFalse(blockSeeker.isEnd(null));
        assertFalse(blockSeeker.isEnd(""));
    }

    @Test
    void findBlockTest() {
        var expected = List.of("expected", "value");
        var iterator = List.of("missed", "Start", "expected", "value", "End").iterator();

        assertEquals(expected, blockSeeker.findBlock(iterator));
        assertNull(blockSeeker.findBlock(iterator));
    }

}