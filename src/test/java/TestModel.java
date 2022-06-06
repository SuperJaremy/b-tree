import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestModel {
    private Person male;
    private Person female;
    private Bar bar;

    @BeforeEach
    public void init() {
        male = new Person("Kirk");
        female = new Person("Jenny");
        bar = new Bar(15);
    }

    @Test
    public void testBarInvitation(){
        male.inviteToBar(female, bar);
        Assertions.assertEquals(2, bar.getVisitors().size());
        Assertions.assertTrue(male.getRelations().containsKey(female));
        Assertions.assertTrue(female.getRelations().containsKey(male));
        Assertions.assertEquals(Relation.LOVE, female.getRelations().get(male));
    }

    @Test
    public void testLoudLaughWithOnePerson(){
        male.laughLoudly(female);
        Assertions.assertTrue(female.getRelations().containsKey(male));
        Assertions.assertEquals(Relation.HATE, female.getRelations().get(male));
    }

    @Test
    public void testLoudLaughInBar(){
        Person visitor = new Person("Matt");
        bar.getVisitors().add(visitor);
        male.laughLoudly(bar);
        for (Person i:
                bar.getVisitors()) {
            Assertions.assertTrue(i.getRelations().containsKey(male));
            Assertions.assertEquals(Relation.HATE, i.getRelations().get(male));
        }
    }

    @Test
    public void testEvaporation(){
        SpaceBeam.evaporate(male);
        Assertions.assertTrue(male.isBusy());
        Assertions.assertEquals(State.GAS, male.getState());
    }

    @Test
    public void testRelationsAfterEvaporation(){
        male.laughLoudly(female);
        SpaceBeam.evaporate(female);
        Assertions.assertEquals(Relation.NEUTRAL, female.getRelations().get(male));
        male.laughLoudly(female);
        Assertions.assertEquals(Relation.NEUTRAL, female.getRelations().get(male));
    }
}
