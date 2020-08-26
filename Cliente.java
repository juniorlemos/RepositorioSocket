package trabalho;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import java.sql.*;

public class Cliente {
	static Connection conect = null;
	static PreparedStatement pst = null;
	static ResultSet rs = null;

	
	private static PrintStream atvServidor;	
	 private static Socket socket;
	 private static String nomeArquivo;
	 private static BufferedReader entrada;
	 private static List<String> lista = new ArrayList<String>();

	
	public static void main(String[] args) {

		String login;
		String senha;
		String nome;
		String ip;
		
		Scanner s = new Scanner(System.in);
		Scanner l = new Scanner(System.in);
		Scanner sn = new Scanner(System.in);
		Scanner n = new Scanner(System.in);

		conect = Conexao.conector();

		int e = 0;
		while (e != 3) {

			System.out.println("Faça sua Escolha");
			System.out.println("1 login");
			System.out.println("2 cadastrar");
			e = s.nextInt();
			
			
			if (e == 1) {

				System.out.println("Digite o seu Login");
				login = l.nextLine();
				System.out.println("Digite a sua Senha");
				senha = sn.nextLine();

				if (logar(login, senha)) {
					System.out.println("Sistema logado com sucesso"+login);
					System.out.println("Ben Vindo: "+login);
					
					while(true) {
					
					System.out.println("Entre com o numero do seu Ip");
					ip = s.nextLine();
					
					if (ip.equals("127.0.0.1")) {
						break;
					}
					
					}
					
					try {
						socket = new Socket(ip, 55555);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					

					 entrada = new BufferedReader(new InputStreamReader(System.in));
					    try {
							atvServidor = new PrintStream(socket.getOutputStream());
						} catch (IOException a) {
							// TODO Auto-generated catch block
							a.printStackTrace();
						}
					
					   // Scanner s = new Scanner(System.in);
					    
					    //int e;
					    e =0;
					    
					    while(e!=6) {
					    
					    	System.out.println("digite uma entrada");
					    	System.out.println("1 enviar");
					    	System.out.println("2 receber");
					    	System.out.println("3 listar");
					    	System.out.println("4 deletar ");
					    	System.out.println("5 mover ");
					    	System.out.println("6 Sair ");
					    	e =s.nextInt();
					    	
					    	switch (e) {
							case 1:
								System.out.println("subir ");					
								atvServidor.println("1");
						    	atvServidor.println(login);
						        enviar();
								break;					
							case 2:
								System.out.println("baixar");
								
								atvServidor.println("2");
						        System.err.print("Digite o nome do Arquivo: ");
						        try {
									nomeArquivo = entrada.readLine();
								} catch (IOException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
						        atvServidor.println(nomeArquivo);
						        atvServidor.println(login);
						        receber(nomeArquivo);
						        break;
								
								
							case 3:
								System.out.println("listar");
								atvServidor.println("3");
						    	atvServidor.println(login);
						        listar();
								
								break;
							case 4:
								System.out.println("deleta");
								
								atvServidor.println("4");
						    	atvServidor.println(login);
						    	System.err.print("Digite o nome do Arquivo: ");
						        try {
									nomeArquivo = entrada.readLine();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						        atvServidor.println(nomeArquivo);
						        deletar();
								
								break;
							case 5:
								System.out.println("move");
								String usu;
								atvServidor.println("5");
								
								atvServidor.println(login);
								
								System.err.print("Digite o nome do usuario para quem você quer mandar o arquivo");
								
								try {
									usu = entrada.readLine();
									atvServidor.println(usu);

									
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

						    	System.err.print("Digite o nome do Arquivo: ");
						        try {
									nomeArquivo = entrada.readLine();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						        atvServidor.println(nomeArquivo);
						        mover();
								break;
							default:
								break;
							}
					    
					    	
						}
						s.close();
						}
					
				}
				
				else {
					System.out.println("erro no login ");
				}
				
			}
			
			
			if (e == 2) {
				
				System.out.println("Digite um Nome");
				nome = n.nextLine();
				System.out.println("Crie um Login");
				login = l.nextLine();
				System.out.println("Crie uma Senha");
				senha = sn.nextLine();
				
				if(cadastrar(nome,login,senha)) {
					
			   System.out.println("usuario cadastrado com sucesso");
					
				}
				
				else
				{
					System.out.println("Erro de cadastro login ja existe");
					
				}
				
			}
			

		}

	//}




public static void enviar() {
		
		try {
		    
			System.out.print("Digite o nome do Arquivo: ");
		    nomeArquivo = entrada.readLine();
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

	public static boolean cadastrar (String nome ,String login ,String senha) {
		
String sql = "insert into pessoa(nome,login,senha) values(?,?,?)"; 	
		try {

			pst=conect.prepareStatement(sql);
			pst.setString(1,nome);
			pst.setString(2,login);
			pst.setString(3,senha);
				
			int param =pst.executeUpdate();			
			
			if (param>0){
		     	
			// criando as pastas de acesso dos usuarios
			new File(login).mkdir();
			return true;
			}
			else {
			return false;				
						}  
			
		}
			  catch (Exception e) {
		return false;
			
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
		
	

	public static boolean logar(String login, String senha) {
		 
		 String sql ="select * from pessoa where login=? and senha=?";
			
			try {
				pst=conect.prepareStatement(sql);
				pst.setString(1, login);
				pst.setString(2, senha);
				
				rs= pst.executeQuery();

				if (rs.next()) {
					
				
					conect.close();
				return true;
				}
				
				else  {
					
				return false ;
				
				}
				}catch (Exception e) {
					
				}
			return false;
					
	 
	 }

}