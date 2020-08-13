import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
public class TelaCadastro extends JFrame {
	
	Connection conect = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	

	private void salvar() {
		
		String sql = "insert into pessoa(nome,login,senha) values(?,?,?)"; 
		
		try {
		
			pst=conect.prepareStatement(sql);
			pst.setString(1,camponome.getText());
			pst.setString(2,campologin.getText());
			pst.setString(3,camposenha.getText());
			
			 if(camponome.getText().isEmpty()||campologin.getText().isEmpty()||camposenha.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos"); 
			 }
			 else {
			int param =pst.executeUpdate();			
			if (param>0) {
			JOptionPane.showMessageDialog(null, "Usuario adicionado com sucesso");


				 camponome.setText(null);
				 campologin.setText(null);
				 camposenha.setText(null);
			}
		}
			 
				
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			System.out.println(e);
			
		}
		
		

		
		
	}
	
	private JPanel contentPane;
	private JTextField camponome;
	private JTextField campologin;
	private JTextField camposenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastro frame = new TelaCadastro();
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
	public TelaCadastro() {
		
		
		conect =Conexao.conector();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nome = new JLabel("nome");
		nome.setBounds(35, 61, 70, 15);
		contentPane.add(nome);
		
		JLabel login = new JLabel("login");
		login.setBounds(35, 122, 70, 15);
		contentPane.add(login);
		
		JLabel senha = new JLabel("senha");
		senha.setBounds(35, 180, 70, 15);
		contentPane.add(senha);
		
		camponome = new JTextField();
		camponome.setBounds(190, 59, 114, 19);
		contentPane.add(camponome);
		camponome.setColumns(10);
		
		campologin = new JTextField();
		campologin.setBounds(190, 120, 114, 19);
		contentPane.add(campologin);
		campologin.setColumns(10);
		
		camposenha = new JTextField();
		camposenha.setColumns(10);
		camposenha.setBounds(190, 178, 114, 19);
		contentPane.add(camposenha);
		
		JButton btnNewButton = new JButton("cadastrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				salvar();
			}
		});
		btnNewButton.setBounds(305, 219, 117, 25);
		contentPane.add(btnNewButton);
		
		JButton btnVoltar = new JButton("voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
			TelaLogin tela = new TelaLogin();
				tela.setVisible(true);
				TelaCadastro.this.dispose();
				
				try {
					conect.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
				
			}
		});
		btnVoltar.setBounds(35, 219, 117, 25);
		contentPane.add(btnVoltar);
	}
}
