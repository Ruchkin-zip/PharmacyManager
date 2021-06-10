package App;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "pharmacy")
public class Pharmacy{

    @Id
    @Column(name = "idpharmacy")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="address")
    private String address;

    @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
    private List<Preparat> ListOfAllPreparats = new ArrayList<>();

    static Scanner in = new Scanner(System.in);
    final static Logger logger = Logger.getLogger(Pharmacy.class);
    public Pharmacy() {
        logger.info("a pharmacy without an address was created");
    }
    public Pharmacy(String s) { //с указанием адреса аптеки
        this.address = s;
        logger.debug("the address " + this.getAddress() + " has been successfully assigned to the object");
        logger.info("pharmacy was created");
    }


    public void addPreparat(Preparat p) {
        ListOfAllPreparats.add(p);
        p.setPharmacy(this);
    }

    public void deletePreparat(Preparat p) {
        ListOfAllPreparats.remove(p);
    }

    public List<Preparat> findListDrugs(String nameIll) {//находит лекарства по болезни
        List<Preparat> preparatList = new ArrayList<>();
        for (int i=0; i<ListOfAllPreparats.size(); ++i){
            if (ListOfAllPreparats.get(i).getIllness().getName().equals(nameIll))
                preparatList.add(ListOfAllPreparats.get(i));
        }
        return preparatList;

    }

    public int findPreparat(String s){//находит лекарство по названию препарата
        for (int i=0; i<ListOfAllPreparats.size(); ++i)
            if ((ListOfAllPreparats.get(i).getName().equals(s)) && (ListOfAllPreparats.get(i).getCount() != 0))
                return i;//нашлось
        return -1;//ничего не нашлось
    }

    public int calcAllSold(){
        int res = 0;
        for (int i =0 ; i<ListOfAllPreparats.size(); ++i)
            res += ListOfAllPreparats.get(i).calcSold();
        return res;
    }

    public int getId() {
        return id;
    }

    public List<Preparat> getListOfAllPreparats()
    {
        if (ListOfAllPreparats.size() == 0)
            return null;
        else
            return ListOfAllPreparats;
    }

    public void setListOfAllPreparats(List<Preparat> preparats)
    { this.ListOfAllPreparats = preparats; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}



   /* public void drugInformation(String s){
        for (int i=0; i<ListOfAllPreparats.size(); ++i)
            if (ListOfAllPreparats.get(i).getName().equals(s))
            {
                ListOfAllPreparats.get(i).information();
                return;
            }
        System.out.println("The drug was not found in the database");
    }*/


    /*public void showSold(){
        System.out.println("list of items sold:");
        for (int i=0; i<ListOfAllPreparats.size(); ++i){
            if (ListOfAllPreparats.get(i).getCntSold() != 0)
                System.out.println(ListOfAllPreparats.get(i).getName() + ": " + ListOfAllPreparats.get(i).getCntSold());
        }
    }*/


   /* public void showListDrugs(){
        System.out.print("List of all drugs in pharmacy №" + this.getId() + ": " + ListOfAllPreparats.size());
        for (int i=0 ; i<ListOfAllPreparats.size(); ++i)
            System.out.print(ListOfAllPreparats.get(i).getName() + " " + ListOfAllPreparats.get(i).getId()+ ". " );
        System.out.println();
    }*/
