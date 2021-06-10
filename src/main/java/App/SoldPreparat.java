package App;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "soldpreparat")
public class SoldPreparat {
    @Id
    @Column(name = "idSoldPreparat")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "cntSold")
    private int cntSold=0;

    @Column(name = "date")
    //private String dateStr;
    private Date date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPreparat")
    private Preparat preparat;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPharmacy")
    private Pharmacy pharmacy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idIllness")
    private Illness illness;

    public SoldPreparat(){}

    public SoldPreparat(Preparat ParentPreparat, int cnt){
        this.name = ParentPreparat.getName();
        this.preparat = ParentPreparat;
        this.cntSold = cnt;
        this.price = ParentPreparat.getPrice();
        this.illness = preparat.getIllness();
        this.pharmacy = preparat.getPharmacy();
        this.date = new Date();
    }

    public int getId() {
        return id;
    }
    public String getName(){
        return name;
    }
    public int getPrice(){ return price; }
    public int getCntSold(){ return cntSold; }
    public Date getDate() {return date;}
    public Pharmacy getPharmacy() { return pharmacy; }
    public Preparat getPreparat() {return preparat;}
    public Illness getIllness() { return illness; }
}
