
package snake;

import java.sql.*;


public class DBConnect {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    public DBConnect() {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/scores", "root", "root");
            st = con.createStatement();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }
    
    public void insertData(/*int Rank, String Name, int score*/) {
        try {
            String sql = "insert into ROOT.TOPPLAYERS " + " (RANK, NAME, SCORE)" + " values (3, 'third', 12)";
            st.executeUpdate(sql);
            System.out.println("db updated");
        } catch(Exception ex) {
            System.out.println("Error in getData method: "+ ex);
        }
    }
    
    public void getData() {
        try {
            String query = "select * from root.TopPlayers.Rank";
            rs = st.executeQuery(query);
            System.out.println("Records from Database:");
            while(rs.next()) {
                int rank = rs.getInt("Rank");
//                String name = rs.getString("Name");
//                int score = rs.getInt("Score");
                System.out.println("Rank: " + rank);
            }
        } catch(Exception ex) {
            System.out.println("Error in getData method: "+ ex);
        }
    }
}
