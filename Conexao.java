
import java.sql.*;

public class Conexao {

	
	public static Connection conector(){
		java.sql.Connection conexao =null;
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/pessoas";
		String user="junior";
		String password="123456";
		
		try {
			Class.forName(driver);
			conexao = DriverManager.getConnection(url,user,password);
			return conexao;
			
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
	}
}
