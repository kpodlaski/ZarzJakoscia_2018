package apptotest;

/**
 * Created by Krzysztof Podlaski on 05.03.2018.
 */
public class MainClass {
    private MyDatabase db;
    public MainClass(MyDatabase db) {
        this.db=db;
    }

    public boolean query(String q){
        return db.query(q);
    }
}
