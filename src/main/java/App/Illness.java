package App;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "illness")
public class Illness{

    @Id
    @Column(name = "idillness")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name")
    private String name;

   @OneToMany(mappedBy = "illness", cascade = CascadeType.ALL)
    private List<Preparat> RightDrugs = new ArrayList<Preparat>();

    public List<Preparat> getRightDrugs(){
        if (RightDrugs.size() == 0)
            return null;
        else
            return RightDrugs;
    }

    static Scanner in = new Scanner(System.in);

    public Illness() {    }

    public Illness(String s){
        this.setName(s);
    }

    public void addNewPreparat(Preparat p) { RightDrugs.add(p); }
    public void deletePreparat(Preparat p){ RightDrugs.remove(p);}


    public int getId() {
        return id;
    }
    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}




    /*public void showRightDrugs(){
        boolean check = false;
        for (int i=0; i<RightDrugs.size(); ++i)
            if (RightDrugs.get(i).getCount() != 0)
            {
                check = true;
                System.out.print(RightDrugs.get(i).getName() + " " + RightDrugs.get(i).getId() + ". ");
            }
        if (check)
            System.out.println(" - Suitable medicines for the disease " + getName());
    }*/
