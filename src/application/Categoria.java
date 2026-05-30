package application;

import java.io.Serializable;
import javax.swing.JTextField;

public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	private String tipo;
	private int percentual;
	private double valor;
	private transient JTextField campoValor;

	private boolean fixo;

	private double valorFixo;

	public Categoria() {
	}

	public Categoria(String nome, String tipo, int percentual, boolean fixo, double valorFixo) {
		this.nome = nome;
		this.tipo = tipo;
		this.percentual = percentual;
		this.fixo = fixo;
		this.valorFixo = valorFixo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getPercentual() {
		return percentual;
	}

	public void setPercentual(int percentual) {
		this.percentual = percentual;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public JTextField getCampoValor() {
		return campoValor;
	}

	public void setCampoValor(JTextField campoValor) {
		this.campoValor = campoValor;
	}

	public boolean isFixo() {
		return fixo;
	}

	public void setFixo(boolean fixo) {
		this.fixo = fixo;
	}

	public double getValorFixo() {
		return valorFixo;
	}

	public void setValorFixo(double valorFixo) {
		this.valorFixo = valorFixo;
	}
}