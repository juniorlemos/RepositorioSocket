package nuvem;

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

public class FileClient {

  private static PrintStream atvServidor;	
  private static Socket socket;
  private static String nomeArquivo;
 private static BufferedReader entrada;

  public static void main(String[] args) throws IOException {
try {
    socket = new Socket("localhost", 55555);
    entrada = new BufferedReader(new InputStreamReader(System.in));
} catch (Exception e) {
   System.err.println("Erro de conex�o");
}

atvServidor = new PrintStream(socket.getOutputStream());

try {
      switch (Integer.parseInt(escolha())) {
    case 1:
    	atvServidor.println("1");
        enviar();
        break;
    case 2:
    	atvServidor.println("2");
        System.err.print("Digite o nome do Arquivo: ");
        nomeArquivo = entrada.readLine();
        atvServidor.println(nomeArquivo);
        receber(nomeArquivo);
        break;
   
    case 3:
    	atvServidor.println("3");
        listar();
        break;
    case 4:
    	atvServidor.println("4");
    	System.err.print("Digite o nome do Arquivo: ");
        nomeArquivo = entrada.readLine();
        atvServidor.println(nomeArquivo);
        deletar();
        break; 
    default:
        	System.out.println("Op��o errada");
        	break;
   }
 } catch (Exception e) {

	   System.err.println("Erro na leitura do par�metro escolha");

 
}
socket.close();
    
  }
     public static String escolha() throws IOException {
    	
  System.out.println("Escolha uma Op��o:");
  System.out.println("1. Upload  Arquivo.");
  System.out.println("2. Download Arquivo.");
  System.out.println("3. Listar Arquivos.");
  System.out.println("4. Deletar Arquivo.");


  return entrada.readLine();
 }
  
     public static void enviar() {
 try {
    System.err.print("Digite o nome do Arquivo: ");
    nomeArquivo = entrada.readLine();

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
        System.err.println("Arquivo n�o existe");
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
    in.close();

    System.out.println("Arquivo "+nomeArquivo+" recebido pelo Servidor.");
} catch (IOException ex) {
}
  }
      public static void listar() {
    	
  	    
		try {
		  	    DataInputStream clienteData = new DataInputStream(socket.getInputStream());
		  	 String nome = clienteData.readUTF();
		  	
		  	 String [] separacao = nome.split(" ");
		     for (int i=0 ; i<separacao.length;i++) {
		    	 System.out.println(separacao[i]);
		    	 
		    	 
		     }
		  	    
		  	  clienteData.close();
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
			  	 clienteData.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      }
    }