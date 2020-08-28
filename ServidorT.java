
 
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
 
public class ServidorT implements Runnable {
	static Connection conect = null;
	static PreparedStatement pst = null;
	static ResultSet rs = null;
	private static PrintStream atvServidor;	
	
    private Socket clienteSocket;
    private BufferedReader in = null;
 
    private String usuario;
    
 
    public ServidorT(Socket cliente) {
        this.clienteSocket = cliente;
    }
 
    @Override
    public void run() {
        try {
        	
        	conect = Conexao.conector();
        	
        	
        	
            in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            String escolhacliente;
   
            Boolean bd= true;
            
            while(bd) {
            	
            String autenticacao=in.readLine();
           
            String aut[] =autenticacao.split(";");
            String op=aut[0];
            System.out.println(op);
           
            
            
            
            
            if (op.equals("2")) {
      	      System.out.println("passou no 2");      

             String lo=aut[1];
             String se=aut[2];
             String no=aut[3];
            
             ObjectOutputStream write = new ObjectOutputStream(clienteSocket.getOutputStream());   
            if( cadastrar(no,lo, se)) {
          	  String buffer = "ok";
             	write.writeObject(buffer);
                 }
            
          else {
          	   String buffer = "false";
                	write.writeObject(buffer);
              }
          }   
            
            if (op.equals("1")) {
      System.out.println("passou no 1");      
            String login=aut[1];
            String senha=aut[2];
            ObjectOutputStream writer = new ObjectOutputStream(clienteSocket.getOutputStream());
           if (logar(login,senha)) {
        	
           	String buffer = "ok";
           	writer.writeObject(buffer);
            bd = false;
            System.out.println("Usuario logado");                       
            new File(login+"/Public").mkdirs();
           }else {
        	   String buffer = "false";
              	writer.writeObject(buffer);
            }
            
         
              
              
           
           
           
           
            }
            }
            String log=in.readLine();
            
            System.out.println("login"+log);
            
        	new File(log).mkdirs();
            
            while (true) {
                escolhacliente = in.readLine();
 
                System.out.println("escolha");
 
                System.out.println(escolhacliente);
 
                switch (escolhacliente) {
                case "1":
 
                    String usua = in.readLine();
                    String p = in.readLine();
                  
                    
                    receber(usua,p);
                    break;
                
    			case "2":
    				String nomeCliente;
    				String us;
    				nomeCliente = in.readLine();
    				us=in.readLine();
    				
    				System.out.println(us);
    				enviar(nomeCliente, us);
    				break;
    			case "3":
    				String pasta;
    				
    			
    				
    				pasta = in.readLine();
    				
    				System.out.println(pasta);
    				
    				usuario = in.readLine();
    				
    				listar(usuario ,pasta);
    				break;
    			case "4":

    				String usuario;
    				String nomeArquivo;
    				usuario = in.readLine();
    				nomeArquivo = in.readLine();

    				deletar(usuario, nomeArquivo);
    				break;

    			case "5":

    				String origem = in.readLine();
    				String usuar = in.readLine();
    				String nomeArq = in.readLine();
    				System.out.println(origem);
    				System.out.println(usuar);
    				
    				
    				mover(usuar,origem,nomeArq);
    				break;

    			case "7":

    				usuario = in.readLine();
    				listarP(usuario);
    				break;
                    
    			case "8":

    				usuario = in.readLine();
    				deletarP(usuario);
    				break;	
    		
    			case "null":


    				break;	
    				
                default:
 
                    break;
                }
 
               
            }
        } catch (IOException ex) {
        }
    }
 
    public void receber(String usuario, String pasta) {
        try {
        	
        	
        	
        	String path = "";
    		try {
    			path = new File(".").getCanonicalPath() + "/" + usuario+"/"+pasta;
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    		

    	 File f = new File(path) ;
    	
    	 f.mkdir();
        	
        	
        	
 
            String origem = "";
            String destino = "";
 
            DataInputStream clienteData = new DataInputStream(clienteSocket.getInputStream());
 
            String nomeArquivo = clienteData.readUTF();
            OutputStream output = new FileOutputStream((nomeArquivo));
            int bytesRead;
            long size = clienteData.readLong();
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = clienteData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
 
            origem = new File(nomeArquivo).getAbsolutePath();
            destino = new File(".").getCanonicalPath() + "/" + usuario+"/"+pasta + "/" + nomeArquivo;
 
            System.out.println(destino);
            
            
            Path source = Paths.get(origem);
            Path destination = Paths.get(destino);
            Files.copy(source, destination);
 
            File file = new File(origem);
 
            file.delete();
 
            output.close();
            //clienteData.close();
 
            System.out.println("Arquivo " + nomeArquivo + " enviado ");
        } catch (IOException ex) {
            System.err.println("Conexao encerrada.");
        }
    }
    public void enviar(String nomeArquivo , String usuario) {
		try {
			String origem = "";
			
			
			
			origem=new File(".").getCanonicalPath()+"/"+usuario+"/"+nomeArquivo;			
			
			
			//File arquivo = new File(nomeArquivo);
			File arquivo = new File(origem);
			
			byte[] mybytearray = new byte[(int) arquivo.length()];

			FileInputStream fis = new FileInputStream(arquivo);
			BufferedInputStream bis = new BufferedInputStream(fis);

			DataInputStream dis = new DataInputStream(bis);
			dis.readFully(mybytearray, 0, mybytearray.length);

			OutputStream os = clienteSocket.getOutputStream();

			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF(arquivo.getName());
			dos.writeLong(mybytearray.length);
			dos.write(mybytearray, 0, mybytearray.length);
			dos.flush();
		} catch (Exception e) {
			System.err.println("Arquivo não existe!");
		}
	}
    public void listar(String usuario,String pasta ) {
    	
    	
    	System.out.println(pasta);
    	
		String path = "";
		try {
			path = new File(".").getCanonicalPath() + "/" + usuario+"/"+pasta;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		File file = new File(path);
		File[] arquivos = file.listFiles();

		String nome = "";
		for (File fileTmp : arquivos) {
			System.out.println(fileTmp.getName());
			nome = nome + fileTmp.getName() + "  ";
		}
		try {
			DataOutputStream dos = new DataOutputStream(clienteSocket.getOutputStream());

			dos.writeUTF(nome);
			dos.flush();
			//dos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    public void listarP(String usuario) {
		String path = "";
		try {
			path = new File(".").getCanonicalPath() + "/" + usuario;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		File file = new File(path);
		File[] arquivos = file.listFiles();

		String nome = "";
		for (File fileTmp : arquivos) {
			if(fileTmp.isDirectory()) {
			System.out.println(fileTmp.getName());
			nome = nome + fileTmp.getName() + "  ";
			}
			}
		try {
			DataOutputStream dos = new DataOutputStream(clienteSocket.getOutputStream());

			dos.writeUTF(nome);
			dos.flush();
			//dos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
public void mover(String usuOrigem,String usuario,String nomeArquivo  ) {
		
		String origem = "";
		String destino = "";
		
		try {
			
			origem =new File(".").getCanonicalPath() + "/" + usuOrigem + "/" + nomeArquivo;
			
			System.out.println(origem);
			
			destino = new File(".").getCanonicalPath() + "/" + usuario + "/" + nomeArquivo;
			
			System.out.println(destino);
			
			Path source = Paths.get(origem);
			Path destination = Paths.get(destino);
			Files.copy(source, destination);
			
			DataOutputStream dos = new DataOutputStream(clienteSocket.getOutputStream());
			dos.writeUTF("o Arquivo " + nomeArquivo + " foi movido para o usuario "+usuario+" com sucesso");
			dos.flush();
			//dos.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	
		
		
		
		
	}
	
	
	
	public void deletar(String usuario, String arquivo) {


		System.out.println(usuario);

		String path = "";
		try {
			path = new File(".").getCanonicalPath() + "/" + usuario;

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		File file = new File(path);

		File[] arquivos = file.listFiles();

		String nome = "";
		for (File fileTmp : arquivos) {

			if (fileTmp.getName().equals(arquivo)) {
				fileTmp.delete();
				try {
					DataOutputStream dos = new DataOutputStream(clienteSocket.getOutputStream());
					dos.writeUTF("o Arquivo " + arquivo + " foi apagado");
					dos.flush();
					//dos.close();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}
	
	
	public void deletarP(String usuario) {


		System.out.println(usuario);

		String path = "";
		try {
			path = new File(".").getCanonicalPath() + "/" + usuario;

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		File file = new File(path);

		if (file.exists())
		{
		deleteDir(file);
		
		try {
			DataOutputStream dos = new DataOutputStream(clienteSocket.getOutputStream());
			dos.writeUTF(" Diretorio  apagado com sucesso" );
			dos.flush();
			//dos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
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
	
	public static 	 void deleteDir(File file) {
	    File[] contents = file.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	            deleteDir(f);
	        }
	    }
	    file.delete();
	}
}
