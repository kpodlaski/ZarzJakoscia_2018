package apptotest;

/**
 * Created by Krzysztof Podlaski on 05.03.2018.
 */
public class MyDatabaseMock  implements MyDatabase{
    public String getPersonNameFromPesel(long pesel) {
        return null;
    }

    public String getPersonAdresFromPesel(long pesel) {
        return null;
    }

    public boolean query(String s) {
        return false;
    }

    public int getUniqueId() {
        return 0;
    }
}
