package model;

import java.util.HashMap;
import java.util.Map;

public class Person implements Executor, Victim {
    private final String name;
    private final Map<Person, Relation> relations;
    private State state;
    private boolean busy;

    public Person(String name) {
        this.name = name;
        relations = new HashMap<>();
        state = State.SOLID;
        busy = false;
    }

    @Override
    public void inviteToBar(Victim victim, Bar bar) {
        if (!(victim instanceof Person p))
            throw new RuntimeException("...");
        if (!victim.isBusy() && !bar.isBusy()) {
            victim.getInvitedToBar(this);
        }
        if (!relations.containsKey(p))
            relations.put(p, Relation.NEUTRAL);
        bar.getVisitors().add(this);
        bar.getVisitors().add(p);
    }

    @Override
    public void laughLoudly(Victim victim) {
        if (!victim.isBusy()) {
            victim.hearLoudLaugh(this);
        }
    }

    @Override
    public void getInvitedToBar(Executor by) {
        if (busy)
            return;
        if (!(by instanceof Person p))
            throw new RuntimeException("where is my mind");
        addRelationIfDontExist(p);
        relations.replace(p, Relation.LOVE);
    }

    @Override
    public void hearLoudLaugh(Executor by) {
        if (busy)
            return;
        if (!(by instanceof Person p))
            throw new RuntimeException("troubles de la tête");
        if(this.equals(p))
            return;
        addRelationIfDontExist(p);
        relations.replace(p, Relation.HATE);
    }

    @Override
    public void beEvaporated() {
        if (busy)
            return;
        relations.replaceAll((k, v) -> v = Relation.NEUTRAL);
        state = State.GAS;
        busy = true;
    }

    public boolean isBusy() {
        return busy;
    }

    public Map<Person, Relation> getRelations() {
        return relations;
    }

    public State getState() {
        return state;
    }

    private void addRelationIfDontExist(Person p){
        if (!relations.containsKey(p))
            relations.put(p, Relation.NEUTRAL);
    }
}
