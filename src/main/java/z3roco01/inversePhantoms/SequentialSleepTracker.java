package z3roco01.inversePhantoms;

public interface SequentialSleepTracker {
    void setSequentialSleeps(long sequentialSleeps);
    long getSequentialSleeps();
    void incSequentialSleeps();
    void incSequentialSleeps(long amount);
}
