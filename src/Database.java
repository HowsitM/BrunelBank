import java.sql.*;

public class Database {

    private static Statement stmt = null;

    // - Database() , creates a SQLite3 Database which connects to an external brunelbankdb.db file
    // - The table Users is created with a primary key id, along with columns for name and balance.

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

    // - createAccount() accepts the accountName and balance from the Account class
    // - The database is then updated through the method update.

    public static void createAccount(String accountName, Double balance) {
        update("INSERT INTO Users (name, balance) VALUES (" + "'" + accountName + "'," + balance + ")");
        }

        //- update() accepts a string of sql commands and executes the sql statement to update the database.
    private static void update(String sql){

        try {
            stmt.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // -getAccount() returns an account object for when an account is attempting a transfer to another.
    // -The account gets details from the database and returns it as an account object

    public static Account getAccount(String accountName) {
        try {
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM users WHERE name='" + accountName + "'");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double balance = resultSet.getDouble("balance");
                return new Account(id, name, balance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // -getAccountBalance() queries the database with an accountName to return the corresponding balance

    public static double getAccountBalance(String accountName){

        try {
            ResultSet rs = stmt.executeQuery("SELECT balance FROM users WHERE name='" + accountName +"'");
            return rs.getDouble("balance");
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    // - setAccountBalance() updates the database accepting the accountName and balance as parameters

    public static void setAccountBalance(String name, double balance){
        update("UPDATE users SET 'balance'=" + balance + " WHERE name='" + name + "'");
    }
}