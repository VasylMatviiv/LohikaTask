import java.util.Objects;

class StringBlockSeeker implements BlockSeeker {
    final private String start;
    final private String end;

    public StringBlockSeeker(String start, String end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean isStart(String el) {
        return start.equals(el);
    }

    @Override
    public boolean isEnd(String el) {
        return end.equals(el);
    }
}
