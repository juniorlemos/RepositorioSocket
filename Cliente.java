

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
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


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

		String login = null;
		String lp = null;
		String senha;
		String nome;
		String ip;
		
		Scanner s = new Scanner(System.in);
		Scanner l = new Scanner(System.in);
		Scanner sn = new Scanner(System.in);
		Scanner n = new Scanner(System.in);



		int e = 0;
		//while (e != 3) {

			//System.out.println("\nFaça sua Escolha");
			//System.out.println("1 login");
			//System.out.println("2 cadastrar");
			//System.out.println("3 sair");
			//e = s.nextInt();
			
			
			
            //   if (e == 2) {
				
				//System.out.println("Digite um Nome");
				//nome = n.nextLine();
				//System.out.println("Crie um Login");
				//login = l.nextLine();
				//System.out.println("Crie uma Senha");
				//senha = sn.nextLine();
				
			//	if(cadastrar(nome,login,senha)) {
					
			  // System.out.println("Usuario "+ nome+ "cadastrado com sucesso");
					
				//}
				
				//else
				//{
					//System.out.println("Erro de cadastro login ja existe");
					
				//}
				
			//}
                                            
               
			
			//if (e == 1) {

				//System.out.println("Digite o seu Login");
				//login = l.nextLine();
				//System.out.println("Digite a sua Senha ");
				//senha = sn.nextLine();

			//	if (logar(login, senha)) {
				//	System.out.println("Sistema logado com sucesso"+login);
					//System.out.println("Ben Vindo: "+login);
					
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
					   int v =0;
					    
					   while (e != 3) {

							System.out.println("\nFaça sua Escolha");
							System.out.println("1 login");
							System.out.println("2 cadastrar");
							System.out.println("3 sair");
							e = s.nextInt();
					   
					   
							if (e == 1) {

								System.out.println("Digite o seu Login");
								login = l.nextLine();
								System.out.println("Digite a sua Senha ");
								senha = sn.nextLine();

								String pass="ok"; 
								
								atvServidor.println("1;"+login+";"+senha);
								
								ObjectInputStream entrada = null;
								try {
									entrada = new ObjectInputStream(socket.getInputStream());
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								String input = null;
								try {
									input = entrada.readObject().toString();
								} catch (ClassNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								if (input.equals(pass)){
									System.out.println("login Realizado com Sucesso");
									
									lp=login;
									break;
									}
								
								else {
									System.out.println("Login ou senha incorretos");
								}
								}
							
							
							
							if (e == 2) {
								
								
								System.out.println("Cadastre o seu Nome");
								nome = n.nextLine();
								System.out.println("Cadastre o seu Login");
								login = l.nextLine();
								System.out.println("Cadastre a sua Senha ");
								senha = sn.nextLine();
							
								atvServidor.println("2;"+nome+";"+login+";"+senha);
								
								ObjectInputStream entrada = null;
								try {
									entrada = new ObjectInputStream(socket.getInputStream());
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								String input = null;
								try {
									input = entrada.readObject().toString();
								} catch (ClassNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								if (input.equals("ok")){
									System.out.println("Cadastro Realizado com Sucesso");
									
									}
								
								else {
									System.out.println("Cadastro incorreto");
								}
								}
							
							}
							
							
					   
								
							//	if (logar(login, senha)) {
								//	System.out.println("Sistema logado com sucesso"+login);
									//System.out.println("Ben Vindo: "+login);
							
							
					   
					   
					   
					   System.out.println(lp);
					   
					   
					   atvServidor.println(lp+"/Public");

					    while(e!=6) {
					    
					    	System.out.println("Digite uma entrada");
					    	System.out.println("1 Upload");
					    	System.out.println("2 Dowloand");
					    	System.out.println("3 Listar Arquivos ");
					    	System.out.println("4 Deletar Arquivos ");
					    	System.out.println("5 Mover Arquivos ");
					    	System.out.println("6 Sair ");
					    	System.out.println("7 Listar Pastas");
					    	System.out.println("8 Deletar Pastas");
					    	
					    	e =s.nextInt();
	
					    	switch (e) {
							case 1:
								System.out.println("Upload \n ");					
								atvServidor.println("1");								
						    	atvServidor.println(lp);
						    	
						    	 System.err.print("Digite o nome da pasta: ");
							        try {
										nomeArquivo = entrada.readLine();
									} catch (IOException e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}
							        atvServidor.println(nomeArquivo);
						        enviar();
								break;					
							case 2:
								System.out.println("Dowloand \n");
								String pasta=""; 
								atvServidor.println("2");
																
							   System.out.print("Digite o nome da pasta: ");
								try {
									pasta=nomeArquivo = entrada.readLine();
								} catch (IOException e3) {
									// TODO Auto-generated catch block
									e3.printStackTrace();
								}
								
								
								
						        System.out.print("Digite o nome do Arquivo: ");
						        try {
									nomeArquivo = entrada.readLine();
								} catch (IOException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
						        
						        atvServidor.println(nomeArquivo);
						        atvServidor.println(lp+"/"+pasta);
						        receber(nomeArquivo);
						        break;
								
								
							case 3:
								
								String nomePasta;
								System.out.println("Listar Arquivos");
								
								atvServidor.println("3");
								 System.out.print("Digite o nome da Pasta: ");
								
								 try {
									 nomeArquivo = entrada.readLine();
									} catch (IOException e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}
								 atvServidor.println(nomeArquivo);
						    	atvServidor.println(lp);
						        listar();
								
								break;
							case 4:
								System.out.println("Deletar arquivo");
								String arq = "";
								atvServidor.println("4");
						    	

						    	System.err.print("Digite a pasta que contém arquivo o Arquivo: ");
						    	
						    	 try {
										nomeArquivo = entrada.readLine();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
						    	
						    	arq=nomeArquivo;
						    	
						    	atvServidor.println(lp+"/"+arq);
						    	
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
								
								
								
								
								
								System.out.print("Digite o nome do usuario para quem você quer mandar o arquivo");
								
								try {
									usu = entrada.readLine();
									atvServidor.println(usu+"/"+"Public");

									
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								System.out.println("Digite o nome da  pasta onde está o arquivo ");										
								
								try {
									usu = entrada.readLine();
									atvServidor.println(lp+"/"+usu);

									
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								
								
								
								
						    	System.out.print("Digite o nome do Arquivo: ");
						        try {
									nomeArquivo = entrada.readLine();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						        atvServidor.println(nomeArquivo);
						        mover();
								break;
								
							case 7:
								System.out.println("Listar Pastas");
								atvServidor.println("7");
						    	atvServidor.println(lp);
						        listar();
								break;
								
							case 8:
								System.out.println("Deletar pasta");
								String pas="";
								atvServidor.println("8");
								
								System.err.print("Digite a pasta que você deseja deletar ");
						    	
						    	 try {
										nomeArquivo = entrada.readLine();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
						    	
						    	pas=nomeArquivo;
						    	
								
								
						    	atvServidor.println(lp+"/"+pas);
						        deletarP();
								break;
								
							default:
								break;
							}
					    
					    	
						}
						s.close();
						
				//}
				
				
			//	else {
				//	System.out.println("Erro no login ");
					//}
			//}
				
		}
			
			
		
			

		//}

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


public static void deletarP() {
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
		//	new File(login).mkdir();
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

		  System.out.println("Arquivo "+nomeArquivo+" baixado do Servidor.");
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
		  //	for (String s : lista) {
			//	System.out.println(s);
			//}
		     
		     
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
