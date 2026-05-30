package screens;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import application.Categoria;
import application.Program;

public class Update extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField txtNome;
	private JTextField txtPercentual;
	private JTextField txtValor;

	private JComboBox<String> cbTipo;

	private JCheckBox chkFixo;

	private Categoria categoria;
	private Program program;

	private JLabel lblPercentual;
	private JLabel lblPorcentagem;
	private JLabel lblValor;

	public Update(Program program, Categoria categoria) {

		this.program = program;
		this.categoria = categoria;

		setTitle("Editar Categoria");

		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setBounds(100, 100, 380, 430);

		setLocationRelativeTo(null);

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		contentPane.setLayout(null);

		setContentPane(contentPane);

		JLabel lblNome = new JLabel("Nome");

		lblNome.setHorizontalAlignment(SwingConstants.CENTER);

		lblNome.setFont(new Font("SansSerif", Font.PLAIN, 22));

		lblNome.setBounds(115, 10, 133, 30);

		contentPane.add(lblNome);

		txtNome = new JTextField();

		txtNome.setFont(new Font("SansSerif", Font.PLAIN, 20));

		txtNome.setBounds(77, 50, 210, 40);

		txtNome.setText(categoria.getNome());

		contentPane.add(txtNome);

		JLabel lblTipo = new JLabel("Tipo");

		lblTipo.setHorizontalAlignment(SwingConstants.CENTER);

		lblTipo.setFont(new Font("SansSerif", Font.PLAIN, 22));

		lblTipo.setBounds(127, 105, 110, 30);

		contentPane.add(lblTipo);

		cbTipo = new JComboBox<>();

		cbTipo.setFont(new Font("SansSerif", Font.PLAIN, 20));

		cbTipo.setModel(new DefaultComboBoxModel<>(new String[] { "Porcentagem", "Gasto" }));

		cbTipo.setSelectedItem(categoria.getTipo());

		cbTipo.setBounds(77, 145, 210, 40);

		contentPane.add(cbTipo);

		lblPercentual = new JLabel("Percentual");

		lblPercentual.setHorizontalAlignment(SwingConstants.CENTER);

		lblPercentual.setFont(new Font("SansSerif", Font.PLAIN, 22));

		lblPercentual.setBounds(102, 205, 160, 30);

		contentPane.add(lblPercentual);

		lblPorcentagem = new JLabel("%");

		lblPorcentagem.setFont(new Font("SansSerif", Font.BOLD, 24));

		lblPorcentagem.setBounds(229, 246, 30, 40);

		contentPane.add(lblPorcentagem);

		txtPercentual = new JTextField();

		txtPercentual.setHorizontalAlignment(SwingConstants.CENTER);

		txtPercentual.setFont(new Font("SansSerif", Font.PLAIN, 20));

		txtPercentual.setBounds(144, 246, 75, 39);

		txtPercentual.setText(String.valueOf(categoria.getPercentual()));

		contentPane.add(txtPercentual);

		lblValor = new JLabel("Valor");

		lblValor.setHorizontalAlignment(SwingConstants.CENTER);

		lblValor.setFont(new Font("SansSerif", Font.PLAIN, 22));

		lblValor.setBounds(115, 205, 130, 30);

		contentPane.add(lblValor);

		txtValor = new JTextField();

		txtValor.setHorizontalAlignment(SwingConstants.CENTER);

		txtValor.setFont(new Font("SansSerif", Font.PLAIN, 20));

		txtValor.setBounds(77, 245, 210, 40);

		txtValor.setText(String.valueOf(categoria.getValorFixo()));

		contentPane.add(txtValor);

		chkFixo = new JCheckBox("Gasto Fixo");
		chkFixo.setHorizontalAlignment(SwingConstants.CENTER);

		chkFixo.setFont(new Font("SansSerif", Font.PLAIN, 18));

		chkFixo.setBounds(107, 293, 150, 22);

		chkFixo.setSelected(categoria.isFixo());

		contentPane.add(chkFixo);

		cbTipo.addActionListener(e -> atualizarCampos());

		atualizarCampos();

		JButton btnAtualizar = new JButton("Atualizar");

		btnAtualizar.setFont(new Font("SansSerif", Font.PLAIN, 20));

		btnAtualizar.setBounds(112, 320, 140, 45);

		contentPane.add(btnAtualizar);

		btnAtualizar.addActionListener(e -> atualizar());
	}

	private void atualizarCampos() {

		boolean porcentagem = cbTipo.getSelectedItem().equals("Porcentagem");

		lblPercentual.setVisible(porcentagem);
		txtPercentual.setVisible(porcentagem);
		lblPorcentagem.setVisible(porcentagem);

		lblValor.setVisible(!porcentagem);
		txtValor.setVisible(!porcentagem);
		chkFixo.setVisible(!porcentagem);
	}

	private void atualizar() {

		String nome = txtNome.getText().trim();

		String tipo = cbTipo.getSelectedItem().toString();

		int percentual = 0;

		boolean fixo = false;

		double valorFixo = 0;

		if (nome.isEmpty()) {

			JOptionPane.showMessageDialog(null, "Digite um nome.");

			return;
		}

		if (tipo.equals("Porcentagem")) {

			try {

				percentual = Integer.parseInt(txtPercentual.getText());

				if (percentual < 1 || percentual > 100) {

					JOptionPane.showMessageDialog(null, "O percentual deve estar entre 1 e 100.");

					return;
				}

			} catch (Exception e) {

				JOptionPane.showMessageDialog(null, "Percentual inválido.");

				return;
			}

		} else {

			try {

				valorFixo = Double.parseDouble(txtValor.getText().replace(",", "."));

			} catch (Exception e) {

				JOptionPane.showMessageDialog(null, "Valor inválido.");

				return;
			}

			fixo = chkFixo.isSelected();
		}

		categoria.setNome(nome);

		categoria.setTipo(tipo);

		categoria.setPercentual(percentual);

		categoria.setFixo(fixo);

		categoria.setValorFixo(valorFixo);

		program.atualizarTudo();

		JOptionPane.showMessageDialog(null, "Categoria atualizada!");

		dispose();
	}
}