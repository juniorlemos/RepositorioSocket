

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
import java.util.Scanner;

public class Cliente {

  private static PrintStream atvServidor;	
  private static Socket socket;
  private static String nomeArquivo;
 private static BufferedReader entrada;

  public static void main(String[] args) throws IOException {
try {
    socket = new Socket("localhost", 55555);
    entrada = new BufferedReader(new InputStreamReader(System.in));
} catch (Exception e) {
   System.err.println("Erro de conexão");
}

atvServidor = new PrintStream(socket.getOutputStream());
System.out.println("digite uma entrada");
System.out.println("1 enviar");
System.out.println("2 receber");
System.out.println("3 listar");
System.out.println("4 deletar ");
System.out.println("5 mover ");

Scanner s = new Scanner(System.in);
int t =s.nextInt();


try {

	switch (t) {
    case 1:
    	atvServidor.println("1");
    	atvServidor.println("Public");
        enviar();
        break;
    case 2:
    	atvServidor.println("2");
        System.err.print("Digite o nome do Arquivo: ");
        nomeArquivo = entrada.readLine();
        atvServidor.println(nomeArquivo);
        atvServidor.println("Public");
        receber(nomeArquivo);
        break;
   
    case 3:
    	atvServidor.println("3");
    	atvServidor.println("Public");
        listar();
        
        break;
    case 4:
    	atvServidor.println("4");
    	atvServidor.println("Public");
    	System.err.print("Digite o nome do Arquivo: ");
        nomeArquivo = entrada.readLine();
        atvServidor.println(nomeArquivo);
        deletar();
        break; 
        
        
    case 5:
    	atvServidor.println("5");
    	atvServidor.println("Public");
    	System.err.print("Digite o nome do Arquivo: ");
        nomeArquivo = entrada.readLine();
        atvServidor.println(nomeArquivo);
        mover();
        break; 
        
    default:
        	System.out.println("Opção errada");
        	break;
   }
 } catch (Exception e) {

	   System.err.println("Erro na leitura do parâmetro escolha");

 
}
socket.close();
    
  }
    
  
     public static void enviar() {
 try {
    System.out.print("Digite o nome do Arquivo: ");
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
    in.close();

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
      
      
      public static void mover() {
    	  
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
      
      
    