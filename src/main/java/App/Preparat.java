package App;

import org.apache.log4j.Logger;
import javax.persistence.*;
import java.util.Scanner;

@Entity
@Table(name = "preparat")
public class Preparat{

    static Scanner in = new Scanner(System.in);

    @Id
    @Column(name = "idpreparat")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "cntSold")
    private int cntSold=0;

    @Column(name = "count")
    private int count=0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pharmacy")
    private Pharmacy pharmacy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idIll")
    private Illness illness;

    final static Logger logger = Logger.getLogger(Preparat.class);

    public Preparat() { logger.info("a drug was created without specifying fields");};

    public Preparat(Illness i, String s, int pr, int c){
        this.setName(s);
        logger.debug("the name " + this.getName() + " has been successfully assigned to the object");
        this.price = pr;
        logger.debug("the price " + this.getPrice() + " has been successfully assigned to the object");
        this.count = c;
        logger.debug("the count " + this.getCount() + " has been successfully assigned to the object");
        i.addNewPreparat(this);
        this.setIllness(i);
        logger.debug("the illness " + this.getIllness().getName() + " has been successfully assigned to the object");
        logger.info("the drug was created");
    }

    public SoldPreparat buyPreparat(int k){ // возвращает купленный товар
            this.count -= k;
            this.cntSold += k;
            SoldPreparat sldPr = new SoldPreparat(this, k);
            return sldPr;
    }
    public int calcSold(){ return cntSold*price; }

    public void executePr(){//метод разрывает связь препарата с болезнью и аптекой
        if (pharmacy != null)
            pharmacy = null;
        if (illness != null)
            illness = null;
    }
    public int getId() {
        return id;
    }
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(int p) { this.price = p;}
    public void setCntSold(int a) { this.cntSold = a;}
    public void setCount(int a) {this.count = a;}

    public int getPrice(){ return price; }
    public int getCount(){ return count; }
    public int getCntSold(){ return cntSold; }
    public Pharmacy getPharmacy() { return pharmacy; }
    public Illness getIllness() { return illness; }

    public void setPharmacy(Pharmacy p) {
        this.pharmacy = p;
    }
    public void setIllness(Illness i) {
        this.illness = i;
    }
}


    /*public void information(){
        System.out.println("Drug " + getName() + ":");
        System.out.println("Price: " + getPrice() + " rubles");
        System.out.println("Quantity in stock: " + getCount());//кол-во в наличии
    }*/
