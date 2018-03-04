package jdbc;

import java.io.File;
import java.sql.*;

/**
 * Created by Krzysztof Podlaski on 04.03.2018.
 */
public class KlasaStartowa {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String dbcon = "jdbc:sqlite:D:/Users/Krzysztof Podlaski/git/ZarzJakoscia_2018/zaoczneMobilne/baza.db";
        /*
        File f = new File ("D:/Users/Krzysztof Podlaski/git/ZarzJakoscia_2018/zaoczneMobilne/baza.db");
        f.delete();
        */
        Connection con = DriverManager.getConnection(dbcon);
        System.out.println("OK");
        String sql = null;
        //createDBStructure(con);
        //insertDataToDB(con);
        PreparedStatement pst =
                con.prepareStatement("SELECT * FROM Filmy");
        PreparedStatement pst2 =
                con.prepareStatement("SELECT * FROM Filmy WHERE id>? ORDER BY title");
        //pst.execute();
        //ResultSet rs = pst.getResultSet();
        pst2.setInt(1,0);
        pst2.execute();
        ResultSet rs = pst2.getResultSet();
        while(rs.next()){
            Film film = Film.getFilmFromResulSet(rs);
            //String title = rs.getString("title");
            //int year = rs.getInt("year");
            System.out.println(film.getTitle()+" rok produkcji: "+film.getYear());
        }
        con.close();

    }

    private static void insertDataToDB(Connection con) throws SQLException{
        String sql = "INSERT INTO Filmy (title, year) VALUES (?,?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1,"Ronin");
        pst.setInt(2,1998);
        pst.execute();
        pst.setString(1,"Kiler");
        pst.setInt(2,2002);
        pst.execute();
    }

    private static void createDBStructure(Connection con) throws SQLException {
        String sql = " CREATE TABLE Filmy " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " title CHAR(50) NOT NULL, " +
                " year INTEGER NOT NULL);";

        Statement st = con.createStatement();
        st.execute(sql);
    }
}
