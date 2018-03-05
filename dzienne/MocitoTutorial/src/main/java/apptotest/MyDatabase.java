package apptotest;

/**
 * Created by Krzysztof Podlaski on 05.03.2018.
 */
public interface MyDatabase {
    public String getPersonNameFromPesel(long pesel);
    public String getPersonAdresFromPesel(long pesel);

    public boolean query(String s);

    int getUniqueId();
}
