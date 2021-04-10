package server;

import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AuthService {
    static final SimpleDateFormat date = new SimpleDateFormat("dd.MM.yy");
    static final SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
    private static Connection connection;
    private static Statement statement;


    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static int addUser(String login, String pass, String nickname) {
        try {
            String query = "INSERT INTO users (login, password, nickname) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setInt(2, pass.hashCode());
            ps.setString(3, nickname);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getNicknameByLoginAndPass(String login, String pass) {
        String query = String.format("select nickname, password from users where login='%s'", login);
        try {
            ResultSet rs = statement.executeQuery(query);
            int myHash = pass.hashCode();


            if (rs.next()) {
                String nick = rs.getString(1);
                int dbHash = rs.getInt(2);
                if (myHash == dbHash) {
                    return nick;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static void addToHistory(final String sourceIp, final String sourceNick, final String distanceNick, final String message) {
        try {
            statement.executeUpdate(String.format( "INSERT INTO history (source_ip, date, time, source_nick, distance_nick, message) VALUES ( '%s', '%s', " + "'%s', '%s', '%s', " + "'%s');", sourceIp, date.format(new Date()), time.format(new Date()), sourceNick, distanceNick, message));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
