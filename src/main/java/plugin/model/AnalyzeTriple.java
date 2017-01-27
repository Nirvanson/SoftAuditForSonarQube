package plugin.model;

public class AnalyzeTriple<T, U, V> {

    private final T first;
    private final U second;
    private final V third;

    public AnalyzeTriple(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFile() {
        return first;
    }

    public U getWordList() {
        return second;
    }

    public V getModel() {
        return third;
    }
}
