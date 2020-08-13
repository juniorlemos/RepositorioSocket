import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TelaLogin extends JFrame {
	
	Connection conect = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	
	public void logar() {
		String sql ="select * from pessoa where login=? and senha=?";
		
		try {
			pst=conect.prepareStatement(sql);
			pst.setString(1, login.getText());
			pst.setString(2, senha.getText());
			
			rs= pst.executeQuery();
			
			if (rs.next()) {
				
				
				SegundaTela tela = new SegundaTela();
				tela.setVisible(true);
				this.dispose();
				conect.close();
				
				
			}else {
				JOptionPane.showMessageDialog(null,"usuario ou senha invalido");
			}
				
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e);
		}
		
		
	}
	

	private JPanel contentPane;
	private JTextField login;
	private JTextField senha;
	private JTextField nome;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin frame = new TelaLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaLogin() {
		
		conect =Conexao.conector();
		
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("login");
		lblNewLabel.setBounds(12, 58, 70, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("senha");
		lblNewLabel_1.setBounds(12, 130, 70, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(210, 12, 70, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("nome");
		lblNewLabel_3.setBounds(12, 209, 70, 15);
		contentPane.add(lblNewLabel_3);
		
		login = new JTextField();
		login.setBounds(198, 56, 114, 19);
		contentPane.add(login);
		login.setColumns(10);
		
		senha = new JTextField();
		senha.setBounds(198, 128, 114, 19);
		contentPane.add(senha);
		senha.setColumns(10);
		
		nome = new JTextField();
		nome.setBounds(198, 207, 114, 19);
		contentPane.add(nome);
		nome.setColumns(10);
		
		JButton btnNewButton = new JButton("logar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				logar();
			}
		});
		btnNewButton.setBounds(333, 111, 117, 25);
		contentPane.add(btnNewButton);
		
		JButton btnNovo = new JButton("cadastrar");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			TelaCadastro tela = new TelaCadastro();
			tela.setVisible(true);
		TelaLogin.this.dispose();
			
			
			}
		});
		btnNovo.setBounds(333, 204, 117, 25);
		contentPane.add(btnNovo);
	}
}
