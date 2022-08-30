import java.util.regex.Pattern;

public class BlockSeekerFactory {

    static BlockSeeker getStringBlockSeeker(String start, String end) {
        return new StringBlockSeeker(start, end);
    }

    static BlockSeeker getPatternBlockSeeker(Pattern start, Pattern end) {
        return new PatternBlockSeeker(start, end);
    }
}
