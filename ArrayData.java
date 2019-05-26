package findRepeatedValueCuncurrent;

public class ArrayData {
    private int[] arr;
    private int start;
    private int end;

    public ArrayData(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    public int[] getArr() {
        return arr;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
