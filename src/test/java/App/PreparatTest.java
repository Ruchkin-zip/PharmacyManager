package App;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

//В классе Preparat были созданы тесты для методов changeCount, buyPreparat, calcSold, executePr, information
//Также были добавлены JUnit тесты для геттеров. Для сеттеров было решено не добавлять тесты, тк тесты геттеров,
//также проверяют правильность работы сеттеров.
// (УБРАТЬ!) Для классов Illness и Pharmacy, тоже было решено не добавлять тесты сеттеров и геттеров,
//потому что они работают по тому же принципу, что в классе Preparat

public class PreparatTest {

    @Test
    public void buyPreparat() {
        Preparat preparat = new Preparat();
        int cnt = 100, buyCnt = 5;
        preparat.setCount(cnt);
        preparat.buyPreparat(buyCnt);
        assertEquals(cnt - buyCnt, preparat.getCount());
        assertEquals(buyCnt, preparat.getCntSold());
    }

    @Test
    public void calcSold() {
        Preparat preparat = new Preparat();
        int cnt = 100, price_ = 200, buyCnt = 5;
        preparat.setCount(cnt);
        preparat.setPrice(price_);
        preparat.buyPreparat(buyCnt);
        assertEquals(buyCnt*price_, preparat.calcSold());
    }

    @Test
    public void executePr() {
        Preparat preparat = new Preparat();
        Illness illness = new Illness();
        Pharmacy pharmacy = new Pharmacy();
        preparat.setPharmacy(pharmacy);
        preparat.setIllness(illness);

        preparat.executePr();

        //assertNotSame(illness, preparat.getIllness());
        //assertNotSame(pharmacy, preparat.getPharmacy());
        assertSame(null, preparat.getIllness());
        assertSame(null, preparat.getPharmacy());
    }


    @Test
    public void getName() {
        Preparat preparat = new Preparat();
        String s = "Drug";
        preparat.setName(s);
        assertEquals(s, preparat.getName());
    }

    @Test
    public void getIdIll() {
        Preparat preparat = new Preparat();
        Illness illness = new Illness();
        preparat.setIllness(illness);
        assertEquals(illness.getId(), preparat.getIllness().getId());
    }

    @Test
    public void getNameIll() {
        Preparat preparat = new Preparat();
        String s = "Суставная боль";
        Illness illness = new Illness(s);
        preparat.setIllness(illness);
        assertEquals(s, preparat.getIllness().getName());
    }

    @Test
    public void getIdPhar() {
        Preparat preparat = new Preparat();
        Pharmacy pharmacy = new Pharmacy();
        preparat.setPharmacy(pharmacy);
        assertEquals(pharmacy.getId(), preparat.getPharmacy().getId());
    }

    @Test
    public void getPrice() {
        Preparat preparat = new Preparat();
        int price_ = 500;
        preparat.setPrice(price_);
        assertEquals(price_, preparat.getPrice());
    }

    @Test
    public void getCount() {
        Preparat preparat = new Preparat();
        int cnt = 10;
        preparat.setCount(cnt);
        assertEquals(cnt, preparat.getCount());
    }

    @Test
    public void getCntSold() {
        Preparat preparat = new Preparat();
        int cntSold = 1;
        preparat.setCntSold(cntSold);
        assertEquals(cntSold, preparat.getCntSold());
    }

    @Test
    public void getPharmacy() {
        Preparat preparat = new Preparat();
        Pharmacy pharmacy = new Pharmacy();
        preparat.setPharmacy(pharmacy);
        assertSame(pharmacy, preparat.getPharmacy());
    }

    @Test
    public void getIllness() {
        Preparat preparat = new Preparat();
        Illness illness = new Illness();
        preparat.setIllness(illness);
        assertSame(illness, preparat.getIllness());
    }
}




   /* @Test
    public void information() {
        String consoleOutput = null;
        PrintStream originalOut = System.out;
        Preparat preparat = new Preparat();
        String nm = "Terafly";
        int price_ = 350, cnt = 75;
        preparat.setName(nm);
        preparat.setPrice(price_);
        preparat.setCount(cnt);
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(100);
            PrintStream capture = new PrintStream(outputStream);
            System.setOut(capture);
            preparat.information();
            capture.flush();
            consoleOutput = outputStream.toString();
            System.setOut(originalOut);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        assertEquals( ("Drug " + nm + ":\r\nPrice: " + price_ + " rubles\r\nQuantity in stock: "+ cnt +"\r\n") , consoleOutput);
    }
*/


    /*@Test
    public void changeCount() {
        Preparat preparat = new Preparat();
        int cnt = 100, changeCnt = 50;
        preparat.setCount(cnt);
        preparat.changeCount(changeCnt);
        assertEquals(cnt+changeCnt, preparat.getCount());
    }*/