package trabalho;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JEditorPane;
import javax.swing.JList;

public class Index extends JFrame {

	 private static PrintStream atvServidor;	
	 private static Socket socket;
	 private static String nomeArquivo;
	 private static BufferedReader entrada;
	 private  static JTextField nome;
	 private static List<String> lista = new ArrayList<String>();

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	 
	public static void main(String[] args) throws IOException {
		
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Index frame = new Index();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		
		try {
		    socket = new Socket("localhost", 55555);
		    entrada = new BufferedReader(new InputStreamReader(System.in));
		    
		} catch (Exception e) {
			
		   System.err.println("Erro de conexão");
		}
		atvServidor = new PrintStream(socket.getOutputStream());
		while(true) {
			
		}
			
		
		
		//socket.close();
		
	}

	/**
	 * Create the frame.
	 */
	public Index() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		getContentPane().setLayout(null);
		
		JButton subir = new JButton("Subir");
		subir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		System.out.println(nome.getText());
		 
		contato("1","Public");
	          	enviar();
		
			}
		});
		
		JList list = new JList();
		list.setBounds(431, 34, 178, 250);
		getContentPane().add(list);
		subir.setBounds(27, 27, 117, 25);
		getContentPane().add(subir);
		
		JButton deletar = new JButton("Deletar");
		deletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atvServidor.println("4");
		    	atvServidor.println("Public");
		    	System.err.print("Digite o nome do Arquivo: ");
		        nomeArquivo = nome.getText();
		        atvServidor.println(nomeArquivo);
		        deletar();
			}
		});
		
		JButton baixar = new JButton("Baixar");
		baixar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atvServidor.println("2");
		        nomeArquivo = nome.getText();
		        atvServidor.println(nomeArquivo);
		        atvServidor.println("Public");
		        receber(nomeArquivo);
		        
	        
			
	          	
	          	
			}
		});
		baixar.setBounds(27, 77, 117, 25);
		getContentPane().add(baixar);
		deletar.setBounds(27, 215, 117, 25);
		getContentPane().add(deletar);
		
		JButton mover = new JButton("Mover");
		mover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atvServidor.println("5");
		    	atvServidor.println("Public");
		        nomeArquivo = nome.getText();
		        atvServidor.println(nomeArquivo);
		        mover();
			}
		});
		mover.setBounds(27, 136, 117, 25);
		getContentPane().add(mover);
		
		JButton listar = new JButton("Listar");
		listar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atvServidor.println("3");
		    	atvServidor.println(pasta);
		        listar();
		        DefaultListModel dlm = new DefaultListModel();
				
				dlm.addAll(lista);
				
				list.setModel(dlm);
				
			}
		});
		listar.setBounds(27, 283, 117, 25);
		getContentPane().add(listar);
		
		nome = new JTextField();
		nome.setText("exemplo");
		nome.setBounds(267, 33, 114, 19);
		getContentPane().add(nome);
		nome.setColumns(10);
		
		
	}
	
	public static void contato(String numero,String usuario ) {
		atvServidor.println(numero);
    	atvServidor.println(usuario);
    	
	}
	
	public static void enviar() {
		
		try {
		    //System.out.print("Digite o nome do Arquivo: ");
		    //nomeArquivo = entrada.readLine();
           
		
			 nomeArquivo=nome.getText();

System.out.println("esse e o lugar par eu mydar"+nome.getText());
           
           System.out.println(nomeArquivo);
		    File arquivo = new File(nomeArquivo);
		    byte[] mybytearray = new byte[(int) arquivo.length()];

		    FileInputStream fis = new FileInputStream(arquivo);
		    BufferedInputStream bis = new BufferedInputStream(fis);

		    DataInputStream dis = new DataInputStream(bis);
		    dis.readFully(mybytearray, 0, mybytearray.length);

		    OutputStream os = socket.getOutputStream();

		    
		    DataOutputStream dos = new DataOutputStream(os);
		    dos.writeUTF(arquivo.getName());
		    dos.writeLong(mybytearray.length);
		    dos.write(mybytearray, 0, mybytearray.length);
		    dos.flush();
		    System.out.println("Arquivo "+nomeArquivo+" enviado para o Servidor.");
		     } catch (Exception e) {
		        System.err.println("Arquivo não existe");
		}
		 }


    public static void receber(String nomeArquivo) {
 try {
  InputStream in = socket.getInputStream();

  DataInputStream clienteData = new DataInputStream(in);

  nomeArquivo = clienteData.readUTF();
  OutputStream output = new FileOutputStream((nomeArquivo));
  int bytesRead;
  long size = clienteData.readLong();
  byte[] buffer = new byte[1024];
  while (size > 0 && (bytesRead = clienteData.read(buffer, 0, (int) 
                  Math.min(buffer.length, size))) != -1) {
      output.write(buffer, 0, bytesRead);
      size -= bytesRead;
  }

  output.close();
 // in.close();

  System.out.println("Arquivo "+nomeArquivo+" recebido pelo Servidor.");
} catch (IOException ex) {
}
}	

    public static void mover() {
  	  
  	  DataInputStream clienteData;
			try {
				clienteData = new DataInputStream(socket.getInputStream());
				String mensagem = clienteData.readUTF();
				System.out.println(mensagem);
			  	 //clienteData.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  }
    public static void deletar() {
	    DataInputStream clienteData;
		try {
			clienteData = new DataInputStream(socket.getInputStream());
			String mensagem = clienteData.readUTF();
			System.out.println(mensagem);
		  	 //clienteData.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
    
 public static void listar() {
    	
  	    
		try {
		  	    DataInputStream clienteData = new DataInputStream(socket.getInputStream());
		  	 String nome = clienteData.readUTF();
		  	
		  	 String [] separacao = nome.split("  ");
		     for (int i=0 ; i<separacao.length;i++) {
		    	 System.out.println(separacao[i]);
		        	  lista.add(separacao[i]);
		     }
		  	for (String s : lista) {
				System.out.println(s);
			}
		     
		     
		  	 // clienteData.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	    
    	  
      }
}

