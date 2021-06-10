package App;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class IllnessTest {

    @Test
    public void getRightDrugs() {
        Illness illness = new Illness();
        Preparat preparat1 = new Preparat();
        Preparat preparat2 = new Preparat();
        Preparat preparat3 = new Preparat();

        illness.addNewPreparat(preparat1);
        illness.addNewPreparat(preparat2);
        illness.addNewPreparat(preparat3);

        List<Preparat> list = new ArrayList<Preparat>();
        list.add(preparat1);
        list.add(preparat2);
        list.add(preparat3);

        for (int i=0; i<list.size() && i<illness.getRightDrugs().size(); ++i)
            assertSame(list.get(i), illness.getRightDrugs().get(i));
    }

    @Test
    public void addNewPreparat() {
        Illness illness = new Illness();
        Preparat preparat = new Preparat();
        illness.addNewPreparat(preparat);
        assertSame(preparat, illness.getRightDrugs().get(illness.getRightDrugs().size()-1));
    }

    @Test
    public void deletePreparat() {
        Illness illness = new Illness();
        Preparat preparat = new Preparat();
        illness.addNewPreparat(preparat);
        assertSame(preparat, illness.getRightDrugs().get(illness.getRightDrugs().size()-1));//проверка добавилось ли
        illness.deletePreparat(preparat);
        assertNull(illness.getRightDrugs());//проверка удаления
    }

    @Test
    public void getName() {
        Illness illness = new Illness();
        String s = "Ill";
        illness.setName(s);
        assertEquals(s, illness.getName());
    }
}