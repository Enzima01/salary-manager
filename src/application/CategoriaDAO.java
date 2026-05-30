package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
	public void salvar(List<Categoria> categorias, String caminho) {
		try {
			Connection conn = Database.conectar(caminho);
			PreparedStatement limpar = conn.prepareStatement("DELETE FROM categorias");
			limpar.execute();
			limpar.close();
			String sql = "INSERT INTO categorias(" + "nome," + "tipo," + "percentual," + "fixo," + "valorFixo"
					+ ") VALUES(?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			for (Categoria categoria : categorias) {
				ps.setString(1, categoria.getNome());
				ps.setString(2, categoria.getTipo());
				ps.setInt(3, categoria.getPercentual());
				ps.setBoolean(4, categoria.isFixo());
				ps.setDouble(5, categoria.getValorFixo());

				ps.execute();
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Categoria> carregar(String caminho) {
		List<Categoria> lista = new ArrayList<>();
		try {
			Connection conn = Database.conectar(caminho);
			String sql = "SELECT * FROM categorias";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Categoria c = new Categoria();
				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				c.setTipo(rs.getString("tipo"));
				c.setPercentual(rs.getInt("percentual"));
				c.setFixo(rs.getBoolean("fixo"));
				c.setValorFixo(rs.getDouble("valorFixo"));
				lista.add(c);
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}