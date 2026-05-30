package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
	public static Connection conectar(String caminho) {
		try {
			String url = "jdbc:sqlite:" + caminho;
			Connection conn = DriverManager.getConnection(url);
			criarTabela(conn);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void criarTabela(Connection conn) {
		try {
			Statement st = conn.createStatement();
			st.execute("CREATE TABLE IF NOT EXISTS categorias (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "nome TEXT NOT NULL," + "tipo TEXT NOT NULL," + "percentual INTEGER," + "fixo INTEGER,"
					+ "valorFixo REAL" + ")");
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}