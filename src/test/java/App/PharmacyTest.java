package App;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PharmacyTest {

    @Test
    public void addPreparat() {
        Pharmacy pharmacy = new Pharmacy();
        Preparat preparat = new Preparat();
        Preparat preparat2 = new Preparat();
        pharmacy.addPreparat(preparat);
        assertSame(preparat, pharmacy.getListOfAllPreparats().get(pharmacy.getListOfAllPreparats().size()-1));
        assertNotSame(preparat2, pharmacy.getListOfAllPreparats().get(pharmacy.getListOfAllPreparats().size()-1));//проверяем что не добавился другой
    }

    @Test
    public void deletePreparat() {
        Pharmacy pharmacy = new Pharmacy();
        Preparat preparat = new Preparat();
        pharmacy.addPreparat(preparat);
        assertSame(preparat, pharmacy.getListOfAllPreparats().get(pharmacy.getListOfAllPreparats().size()-1));//проверка добавилось ли
        pharmacy.deletePreparat(preparat);
        assertNull(pharmacy.getListOfAllPreparats());//проверка удаления
    }

    @Test
    public void findPreparat() {
        Pharmacy pharmacy = new Pharmacy();
        Preparat preparat1 = new Preparat();
        preparat1.setName("Лекарство№1");
        preparat1.setCount(1);
        pharmacy.addPreparat(preparat1);

        assertSame(preparat1, pharmacy.getListOfAllPreparats().get(pharmacy.findPreparat("Лекарство№1")));//нашёлся препарат
        assertEquals(-1, pharmacy.findPreparat("Лекарство№2"));//не нашелся другой препарат
    }

    @Test
    public void calcAllSold() {
        Pharmacy pharmacy = new Pharmacy();
        Preparat preparat1 = new Preparat();
        int cntS1 = 15, cntS2 = 100, price1_ = 500, price2_ = 20;
        preparat1.setCntSold(cntS1);
        preparat1.setPrice(price1_);
        pharmacy.addPreparat(preparat1);

        Preparat preparat2 = new Preparat();
        preparat2.setCntSold(cntS2);
        preparat2.setPrice(price2_);
        pharmacy.addPreparat(preparat2);

        assertEquals(price1_*cntS1 + price2_*cntS2, pharmacy.calcAllSold());//9500
    }

    @Test
    public void getListOfAllPreparats() {
        Pharmacy pharmacy = new Pharmacy();
        Pharmacy pharmacyEmpty = new Pharmacy();
        Preparat preparat1 = new Preparat();
        Preparat preparat2 = new Preparat();

        List<Preparat> list = new ArrayList<Preparat>();
        list.add(preparat1);
        list.add(preparat2);

        pharmacy.setListOfAllPreparats(list);
        assertSame(list, pharmacy.getListOfAllPreparats());
        assertNull(pharmacyEmpty.getListOfAllPreparats());//возвращает null, если нет лекарств в аптеке
    }



    @Test
    public void getAddress() {
        Pharmacy pharmacy = new Pharmacy();
        String s = "Popova 5";
        pharmacy.setAddress(s);
        assertEquals(s, pharmacy.getAddress());
    }
}





/*    @Test
    public void findListDrugs() {
        Pharmacy pharmacy = new Pharmacy();
        Illness illness = new Illness();
        illness.setName("Болезнь1");
        Illness illness1 = new Illness();
        illness1.setName("Болезнь2");
        Preparat preparat1 = new Preparat();
        preparat1.setName("Лекарство№1");
        preparat1.setCount(1);
        illness.addNewPreparat(preparat1);
        for (int i=0; i<illness.getRightDrugs().size(); ++i)
            System.out.println(illness.getRightDrugs().get(i).getName());
        System.out.println(preparat1.getNameIll());
        pharmacy.addPreparat(preparat1);

        Preparat preparat2 = new Preparat();
        preparat2.setName("Лекарство№2");
        preparat2.setCount(1);
        illness.addNewPreparat(preparat2);
        pharmacy.addPreparat(preparat2);

        List<Preparat> list = new ArrayList<Preparat>();
        list.add(preparat1);
        list.add(preparat2);

        List<Preparat> list2 = pharmacy.findListDrugs("Болезнь1>");
        for (int i=0; i<list.size() && i<list2.size(); ++i)
        {
            assertSame(list.get(i), list2.get(i));//нашлись подходящие препараты
            System.out.println(list.get(i).getName());
        }

        List<Preparat> list3 = pharmacy.findListDrugs("Болезнь1>");
        for (int i=0; i<list.size() && i<list3.size(); ++i)
        {
            assertSame(list.get(i), list3.get(i));//не нашлось лишнего
            System.out.println(list3.get(i).getName());
        }
    }не получилось*/