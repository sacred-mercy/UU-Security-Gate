import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Database {
    public com.mysql.jdbc.Connection con;

    Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            final String dbURL = "jdbc:mysql://localhost:3306/uu_security_system";
            final String dbUsername = "root";
            final String dbPassword = "";
            con = (com.mysql.jdbc.Connection) DriverManager.getConnection(dbURL, dbUsername, dbPassword);
        } catch (Exception error){
            System.out.println(error.getMessage());
        }
    }

    void dataInsert(String query, int noOfValues, String[] values){
        try {
            PreparedStatement statement = con.prepareStatement(query);
            for (int i=0; i<noOfValues; i++){
                statement.setString(i+1,values[i]);
            }
            statement.executeUpdate();
        } catch (Exception error){
            System.out.println(error.getMessage());
        }

    }
}
