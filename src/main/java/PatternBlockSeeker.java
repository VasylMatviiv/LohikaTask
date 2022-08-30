import java.util.Objects;
import java.util.regex.Pattern;

class PatternBlockSeeker implements BlockSeeker {
    final private Pattern start;
    final private Pattern end;

    public PatternBlockSeeker(Pattern start, Pattern end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean isStart(String el) {
        return el != null && start.matcher(el).matches();

    }

    @Override
    public boolean isEnd(String el) {
        return el != null && end.matcher(el).matches();
    }
}
