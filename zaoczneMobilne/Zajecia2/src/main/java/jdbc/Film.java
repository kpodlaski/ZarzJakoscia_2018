package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Krzysztof Podlaski on 04.03.2018.
 */
public class Film {
    private int id;
    private String title;
    private int year;

    public Film(){}

    public static Film getFilmFromResulSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int year = rs.getInt("year");
        String title  = rs.getString("title");
        Film f = new Film();
        f.setId(id);
        f.title=title;
        f.year=year;

        return f;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
