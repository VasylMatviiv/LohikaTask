import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.regex.Pattern;

public class BlockIterator implements Iterator<List<String>> {

    private final Iterator<String> iterator;

    private final BlockSeeker blockSeeker;

    private List<String> next;

    public BlockIterator(Iterator<String> input, String start, String end) {
        Objects.requireNonNull(input);
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);

        iterator = input;
        blockSeeker = BlockSeekerFactory.getStringBlockSeeker(start, end);
        next = findNext();
    }

    public BlockIterator(Iterator<String> input, Pattern start, Pattern end) {
        Objects.requireNonNull(input);
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);

        iterator = input;
        blockSeeker = BlockSeekerFactory.getPatternBlockSeeker(start, end);
        next = findNext();
    }

    private List<String> findNext() {
        return blockSeeker.findBlock(iterator);
    }


    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public List<String> next() {
        if (next == null) throw new NoSuchElementException();
        var current = next;
        next = findNext();
        return current;
    }

    public static void main(String[] args) {
        // String
        testCase1(TestData.TEST_01.iterator());
        testCase2(TestData.TEST_01.iterator());
        testCase3(TestData.TEST_01.iterator());
        testCase4(TestData.TEST_01.iterator());
        testCase1(TestData.TEST_02.iterator());
        testCase2(TestData.TEST_02.iterator());
        testCase3(TestData.TEST_02.iterator());
        testCase4(TestData.TEST_02.iterator());
        testCase1(TestData.TEST_03.iterator());
        testCase2(TestData.TEST_03.iterator());
        testCase3(TestData.TEST_03.iterator());
        testCase4(TestData.TEST_03.iterator());

        // Pattern
        System.out.println("/////////PATTERN/////////");
        testCase5(TestData.TEST_01.iterator());
        testCase6(TestData.TEST_01.iterator());
        testCase7(TestData.TEST_01.iterator());
        testCase8(TestData.TEST_01.iterator());
        testCase5(TestData.TEST_02.iterator());
        testCase6(TestData.TEST_02.iterator());
        testCase7(TestData.TEST_02.iterator());
        testCase8(TestData.TEST_02.iterator());
        testCase5(TestData.TEST_03.iterator());
        testCase6(TestData.TEST_03.iterator());
        testCase7(TestData.TEST_03.iterator());
        testCase8(TestData.TEST_03.iterator());
    }

    private static void testCase1(Iterator<String> input) {
        BlockIterator blockIterator = new BlockIterator(input,
                TestData.MARKER_START, TestData.MARKER_END);
        while (blockIterator.hasNext()) {
            blockIterator.hasNext();
            blockIterator.hasNext();
            System.out.println(blockIterator.next());
        }
    }

    private static void testCase2(Iterator<String> input) {
        BlockIterator blockIterator = new BlockIterator(input,
                TestData.MARKER_START, TestData.MARKER_END);
        Iterable<List<String>> iterable = () -> blockIterator;
        for (List<String> block : iterable) {
            System.out.println(block);
        }
    }

    private static void testCase3(Iterator<String> input) {
        BlockIterator blockIterator = new BlockIterator(input,
                TestData.MARKER_START, TestData.MARKER_END);
        blockIterator.forEachRemaining(System.out::println);
    }

    private static void testCase4(Iterator<String> input) {
        BlockIterator blockIterator = new BlockIterator(input,
                TestData.MARKER_START, TestData.MARKER_END);
        while (true) {
            try {
                System.out.println(blockIterator.next());
            } catch (NoSuchElementException e) {
                System.out.println("Reached iterator end");
                break;
            }
        }
    }
    private static void testCase5(Iterator<String> input) {
        BlockIterator blockIterator = new BlockIterator(input,
                TestData.MARKER_START_PATTERN, TestData.MARKER_END_PATTERN);
        while (blockIterator.hasNext()) {
            blockIterator.hasNext();
            blockIterator.hasNext();
            System.out.println(blockIterator.next());
        }
    }

    private static void testCase6(Iterator<String> input) {
        BlockIterator blockIterator = new BlockIterator(input,
                TestData.MARKER_START, TestData.MARKER_END);
        Iterable<List<String>> iterable = () -> blockIterator;
        for (List<String> block : iterable) {
            System.out.println(block);
        }
    }

    private static void testCase7(Iterator<String> input) {
        BlockIterator blockIterator = new BlockIterator(input,
                TestData.MARKER_START_PATTERN, TestData.MARKER_END_PATTERN);
        blockIterator.forEachRemaining(System.out::println);
    }

    private static void testCase8(Iterator<String> input) {
        BlockIterator blockIterator = new BlockIterator(input,
                TestData.MARKER_START_PATTERN, TestData.MARKER_END_PATTERN);
        while (true) {
            try {
                System.out.println(blockIterator.next());
            } catch (NoSuchElementException e) {
                System.out.println("Reached iterator end");
                break;
            }
        }
    }
}

class TestData {
    static String MARKER_START = "MARKER_START";
    static String MARKER_END = "MARKER_END";

    static Pattern MARKER_START_PATTERN = Pattern.compile("MARKER_START{1}");

    static Pattern MARKER_END_PATTERN = Pattern.compile("MARKER_END{1}");
    static List<String> TEST_01 = List.of(
            "this is string which is not relevant to us",
            "MARKER_START",
            "This text should",
            "appear",
            "in the",
            "result",
            "MARKER_END",
            "MARKER_START",
            "Another part",
            "of valuable",
            "data",
            "MARKER_END",
            "to be ignored",
            "MARKER_START",
            "Something that we",
            "need",
            "to keep",
            "MARKER_END"
    );
    // TODO create one more test data set with edge cases
    static List<String> TEST_02 = List.of("this is string which is not relevant to us",
            "MARKER_END",
            "MARKER_START",
            "This text should",
            "appear",
            "in the",
            "result",
            "MARKER_END",
            "to be ignored",
            "MARKER_START",
            "Something that we",
            "miss",
            "because we ",
            "didn't close the block");
    // TODO create one more test data set with edge cases
    static List<String> TEST_03 = List.of("this is string which is not relevant to us",
            "MARKER_END",
            "MARKER_START",// empty value is also value
            "MARKER_END",
            "MARKER_START",
            "This text should",
            "appear",
            "in the",
            "result",
            "MARKER_END",
            "to be ignored",
            "MARKER_START");
}
