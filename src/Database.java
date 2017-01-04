import java.sql.*;

public class Database {

    private static Statement stmt = null;

    public Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:brunelbankdb.db");
            stmt = connection.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS `Users` (\n" +
                    "\t`id`\tINTEGER NOT NULL,\n" +
                    "\t`name`\tTEXT NOT NULL,\n" +
                    "\t`balance`\tNUMERIC NOT NULL,\n" +
                    "\tPRIMARY KEY(id)\n" +
                    ")";
            stmt.executeUpdate(sql);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createAccount(String accountName, Double balance) {
        update("INSERT INTO Users (name, balance) VALUES (" + "'" + accountName + "',"
                + balance + ")");
        }

    private static void update(String sql){

        try {
            stmt.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static Account getAccount(String accountName) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE name='" + accountName + "'");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double balance = rs.getDouble("balance");
                return new Account(id, name, balance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double getAccountBalance(String accountName){

        try {
            ResultSet rs = stmt.executeQuery("SELECT balance FROM users WHERE name='" + accountName +'"');
            return rs.getDouble("balance");
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public static void setAccountBalance(String name, double balance){

        update("UPDATE users SET 'balance'=" + balance + " WHERE name='" + name + "'");

    }
}