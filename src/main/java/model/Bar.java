package model;

import java.util.ArrayList;
import java.util.List;

public class Bar implements Victim {
    private final List<Person> visitors;
    private final int capacity;

    public Bar(int capacity){
        this.capacity = capacity;
        visitors = new ArrayList<>(capacity);
    }

    public List<Person> getVisitors() {
        return visitors;
    }

    @Override
    public void getInvitedToBar(Executor by) {
        throw new RuntimeException("Inviting bar to bar is not OK");
    }

    @Override
    public void hearLoudLaugh(Executor by) {
        for (Victim i:
             visitors) {
            i.hearLoudLaugh(by);
        }
    }

    @Override
    public void beEvaporated() {
        for (Victim i:
             visitors) {
            i.beEvaporated();
        }
    }

    @Override
    public boolean isBusy() {
        return !(this.getVisitors().size() < capacity);
    }
}
