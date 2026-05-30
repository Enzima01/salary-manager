package application;

import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import screens.Create;
import screens.Update;
import java.awt.Toolkit;

public class Program extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField txtSalario;
	private JTextField txtTotal;
	private JTextField txtRestante;

	private JLabel lblPercentualTotal;
	private JLabel lblPercentualRestante;

	private JLabel lblTotal;
	private JLabel lblRestante;

	private boolean temaEscuro = true;

	private JPanel painelCategorias;

	private int y = 10;

	private ArrayList<Categoria> categorias = new ArrayList<>();

	public static void main(String[] args) {

		try {

			FlatDarkLaf.setup();

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> {

			Program frame = new Program();

			frame.setVisible(true);
		});
	}

	public Program() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Program.class.getResource("/imgs/ico.png")));

		setResizable(false);

		setTitle("Gerenciador de Salário | Enzima01");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 610, 730);

		setLocationRelativeTo(null);

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		contentPane.setLayout(null);

		setContentPane(contentPane);

		// MENU

		JMenuBar menuBar = new JMenuBar();

		setJMenuBar(menuBar);

		JMenu menuNovo = new JMenu("Novo");

		menuNovo.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {

				Create c = new Create(Program.this);

				c.setVisible(true);
			}
		});

		menuBar.add(menuNovo);

		JMenu menuSalvar = new JMenu("Salvar");

		JMenuItem itemTemplate = new JMenuItem("Template");

		itemTemplate.addActionListener(e -> {
			salvarBanco();
		});

		menuSalvar.add(itemTemplate);

		JMenuItem itemRelatorio = new JMenuItem("Relatório TXT");

		itemRelatorio.addActionListener(e -> {
			gerarRelatorio();
		});

		menuSalvar.add(itemRelatorio);

		menuBar.add(menuSalvar);

		JMenu menuCarregar = new JMenu("Carregar");

		menuCarregar.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {

				carregarBanco();
			}
		});

		menuBar.add(menuCarregar);

		JMenu menuTema = new JMenu("Tema");

		menuTema.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {

				temaEscuro = !temaEscuro;

				trocarTema(temaEscuro);
			}
		});

		menuBar.add(menuTema);

		// SALÁRIO

		JLabel lblSalario = new JLabel("Digite seu salário:");

		lblSalario.setFont(new Font("SansSerif", Font.PLAIN, 24));

		lblSalario.setBounds(201, 20, 337, 40);

		contentPane.add(lblSalario);

		txtSalario = new JTextField();

		txtSalario.setHorizontalAlignment(SwingConstants.CENTER);

		txtSalario.setFont(new Font("SansSerif", Font.PLAIN, 20));

		txtSalario.setBounds(187, 70, 220, 40);

		contentPane.add(txtSalario);

		// BOTÃO

		JButton btnCalcular = new JButton("Calcular");

		btnCalcular.setFont(new Font("SansSerif", Font.PLAIN, 18));

		btnCalcular.setBounds(232, 130, 130, 40);

		contentPane.add(btnCalcular);

		btnCalcular.addActionListener(e -> calcular());

		// PAINEL

		painelCategorias = new JPanel();

		painelCategorias.setLayout(null);

		painelCategorias.setBounds(56, 190, 482, 320);

		contentPane.add(painelCategorias);

		// TOTAL

		lblTotal = new JLabel("Total");

		lblTotal.setFont(new Font("SansSerif", Font.PLAIN, 20));

		lblTotal.setBounds(257, 521, 185, 30);

		lblTotal.setVisible(false);

		contentPane.add(lblTotal);

		lblPercentualTotal = new JLabel("(0%)");
		lblPercentualTotal.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblPercentualTotal.setBounds(311, 521, 116, 30);

		contentPane.add(lblPercentualTotal);

		txtTotal = new JTextField();

		txtTotal.setEditable(false);

		txtTotal.setVisible(false); // text box invisivel

		txtTotal.setHorizontalAlignment(SwingConstants.CENTER);

		txtTotal.setFont(new Font("SansSerif", Font.PLAIN, 20));

		txtTotal.setBounds(207, 555, 180, 40);

		contentPane.add(txtTotal);

		// RESTANTE

		lblRestante = new JLabel("Restante");

		lblRestante.setFont(new Font("SansSerif", Font.PLAIN, 20));

		lblRestante.setBounds(221, 605, 232, 30);

		lblRestante.setVisible(false);

		contentPane.add(lblRestante);

		lblPercentualRestante = new JLabel("(0%)");
		lblPercentualRestante.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblPercentualRestante.setBounds(311, 605, 171, 30);

		contentPane.add(lblPercentualRestante);

		lblPercentualTotal.setVisible(false);
		lblPercentualRestante.setVisible(false);

		txtRestante = new JTextField();

		txtRestante.setEditable(false);

		txtRestante.setVisible(false);

		txtRestante.setHorizontalAlignment(SwingConstants.CENTER);

		txtRestante.setFont(new Font("SansSerif", Font.PLAIN, 20));

		txtRestante.setBounds(207, 640, 180, 40);

		contentPane.add(txtRestante);

		atualizarTela();
	}

	// ADICIONAR

	public void adicionarCategoria(Categoria categoria) {

		categorias.add(categoria);

		atualizarTudo();
	}

	// ATUALIZAR

	public void atualizarTudo() {

		atualizarTela();

		repaint();

		revalidate();
	}

	// TELA

	private void atualizarTela() {

		painelCategorias.removeAll();

		y = 10;

		for (Categoria categoria : categorias) {

			JLabel lbl = new JLabel();

			if (categoria.getTipo().equals("Porcentagem")) {

				lbl.setText(categoria.getNome() + " (" + categoria.getPercentual() + "%)");

			} else {

				lbl.setText(categoria.getNome());

			}

			lbl.setFont(new Font("SansSerif", Font.PLAIN, 20));
			lbl.setBounds(10, y, 220, 35);

			painelCategorias.add(lbl);

			JTextField txt = new JTextField();

			categoria.setCampoValor(txt);

			txt.setFont(new Font("SansSerif", Font.PLAIN, 18));
			txt.setBounds(240, y, 110, 35);

			if (categoria.getTipo().equals("Porcentagem")) {

				txt.setEditable(false);

			} else if (categoria.isFixo()) {

				txt.setText(String.valueOf(categoria.getValorFixo()));

				txt.setEditable(false);
			}

			painelCategorias.add(txt);

			JButton btnEditar = new JButton("✏");

			btnEditar.setBounds(360, y, 50, 35);

			painelCategorias.add(btnEditar);

			btnEditar.addActionListener(e -> {

				Update update = new Update(Program.this, categoria);

				update.setVisible(true);
			});

			JButton btnExcluir = new JButton("X");

			btnExcluir.setForeground(Color.RED);

			btnExcluir.setBounds(420, y, 50, 35);

			painelCategorias.add(btnExcluir);

			btnExcluir.addActionListener(e -> {

				int resposta = JOptionPane.showConfirmDialog(null, "Deseja deletar?", "Confirmação",
						JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {

					categorias.remove(categoria);

					atualizarTudo();
				}
			});

			y += 50;
		}

		painelCategorias.repaint();
		painelCategorias.revalidate();

		setSize(610, 740);
	}

	// CALCULAR

	private void calcular() {

		try {

			if (txtSalario.getText().trim().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Digite o salário.");

				return;
			}

			double salario = Double.parseDouble(txtSalario.getText().replace(",", "."));

			double total = 0;

			DecimalFormat df = new DecimalFormat("0.00");

			for (Categoria categoria : categorias) {

				JTextField txt = categoria.getCampoValor();

				double valor;

				if (categoria.getTipo().equals("Porcentagem")) {

					valor = salario * categoria.getPercentual() / 100.0;

					txt.setText(df.format(valor));

				} else {

					if (txt.getText().trim().isEmpty()) {

						valor = 0;

					} else {

						valor = Double.parseDouble(txt.getText().replace(",", "."));
					}
				}

				total += valor;
			}

			double restante = salario - total;

			txtTotal.setText(df.format(total));

			txtRestante.setText(df.format(restante));

			double percentualTotal = (total / salario) * 100.0;

			double percentualRestante = (restante / salario) * 100.0;

			lblPercentualTotal.setText("(" + String.format("%.0f", percentualTotal) + "%)");

			lblPercentualRestante.setText("(" + String.format("%.0f", percentualRestante) + "%)");

			//

			lblTotal.setVisible(true);
			lblRestante.setVisible(true);

			txtTotal.setVisible(true);
			txtRestante.setVisible(true);

			lblPercentualTotal.setVisible(true);
			lblPercentualRestante.setVisible(true);

			// enzo

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao calcular.");
		}
	}

	// SALVAR

	private void salvarBanco() {

		try {

			JFileChooser chooser = new JFileChooser();

			chooser.setSelectedFile(new File("salario.db"));

			int resultado = chooser.showSaveDialog(null);

			if (resultado == JFileChooser.APPROVE_OPTION) {

				File arquivo = chooser.getSelectedFile();

				String caminho = arquivo.getAbsolutePath();

				if (!caminho.endsWith(".db")) {

					caminho += ".db";
				}

				CategoriaDAO dao = new CategoriaDAO();

				dao.salvar(categorias, caminho);

				JOptionPane.showMessageDialog(null, "Banco salvo!");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// CARREGAR

	private void carregarBanco() {

		try {

			JFileChooser chooser = new JFileChooser();

			int resultado = chooser.showOpenDialog(null);

			if (resultado == JFileChooser.APPROVE_OPTION) {

				File arquivo = chooser.getSelectedFile();

				CategoriaDAO dao = new CategoriaDAO();

				categorias = new ArrayList<>(dao.carregar(arquivo.getAbsolutePath()));

				atualizarTudo();

				JOptionPane.showMessageDialog(null, "Banco carregado!");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void gerarRelatorio() {

		try {

			if (txtSalario.getText().trim().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Calcule os valores antes de gerar o relatório.");

				return;
			}

			JFileChooser chooser = new JFileChooser();

			chooser.setDialogTitle("Salvar Relatório");

			chooser.setSelectedFile(new File("relatorio_salario.txt"));

			int resultado = chooser.showSaveDialog(null);

			if (resultado != JFileChooser.APPROVE_OPTION) {

				return;
			}

			File arquivo = chooser.getSelectedFile();

			String caminho = arquivo.getAbsolutePath();

			if (!caminho.endsWith(".txt")) {

				caminho += ".txt";
			}

			PrintWriter writer = new PrintWriter(new FileWriter(caminho));

			writer.println("===================================");
			writer.println("Relatório do Gerenciador de Salário");
			writer.println("===================================");
			writer.println();

			writer.println("Salário: " + txtSalario.getText());

			writer.println();

			writer.println("===================================");

			for (Categoria categoria : categorias) {

				String descricao;

				if (categoria.getTipo().equals("Porcentagem")) {

					descricao = categoria.getNome() + " (" + categoria.getPercentual() + "%): ";

				} else {

					descricao = categoria.getNome() + ": ";
				}

				String valor = categoria.getCampoValor().getText();

				writer.println(descricao + valor);

				writer.println("===================================");
			}

			writer.println();

			writer.println("Total " + lblPercentualTotal.getText() + ": " + txtTotal.getText());

			writer.println();

			writer.println("Restante " + lblPercentualRestante.getText() + ": " + txtRestante.getText());

			writer.println();

			writer.println("===================================");

			LocalDateTime agora = LocalDateTime.now();

			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

			writer.println(agora.format(formato));
			writer.print("===================================");

			writer.close();

			JOptionPane.showMessageDialog(null, "Relatório salvo com sucesso!");

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro ao gerar relatório.");

			e.printStackTrace();
		}
	}

	// TEMA

	private void trocarTema(boolean escuro) {

		try {

			if (escuro) {

				UIManager.setLookAndFeel(new FlatDarkLaf());

			} else {

				UIManager.setLookAndFeel(new FlatLightLaf());
			}

			for (Window w : Window.getWindows()) {

				SwingUtilities.updateComponentTreeUI(w);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}