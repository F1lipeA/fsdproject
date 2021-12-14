import java.util.*;
import java.net.*;
import java.io.*;

public class presencesClient {

	static final int SI_PORTO = 2000; 
	static final String SI_HOST = "127.0.0.1"; //IP e PORTO do Serviço de Identificação

	public static void main(String[] args) throws IOException {
		String servidor = SI_HOST;
		int porto = SI_PORTO;



		if (args.length != 2) {
			System.out.println("Erro: use java presencesClient <ip> <Identificador Unico>");
			System.exit(-1);
		}


		// Create a representation of the IP address of the Server: API
		// java.net.InetAddress
		InetAddress serverAddress = InetAddress.getByName(servidor);

		Socket ligacao = null;

		// Create a client sockets (also called just "sockets"). A socket is an endpoint
		// for communication between two machines: API java.net.Socket

		ligacao = new Socket(serverAddress, porto);

		try {


			// Create a java.io.BufferedReader for the Socket; Use
			// java.io.Socket.getInputStream() to obtain the Socket input stream

			BufferedReader in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));

			// Create a java.io. PrintWriter for the Socket; Use
			// java.io.Socket.etOutputStream() to obtain the Socket output stream

			PrintWriter out = new PrintWriter(ligacao.getOutputStream(), true);

			String request = "get" + " " + args[0] + " " + args[1];

			// write the request into the Socket

			out.println(request);

			String msg;


			// Read the server response - read the data until null
			
/*
			while((msg= in.readLine())!=null){
				System.out.println(msg);
			}

			*/
			int length=0;
			while((msg = in.readLine())!= null){
			if(length == 0) {System.out.println("\nChave de Acesso: " +msg +"\n");}
			if(length == 1) {System.out.println("Ip do Servico de Tickting: " +msg +"\n");}
			else {if (length ==2) {System.out.println("Porto do Servico de Ticketing: " + msg +"\n");}
			}
			length+=1;
  			  }
		
			// Close the Socket
			ligacao.close();

			System.out.println("Terminou a ligacao!");
		} catch (IOException e) {
			System.out.println("Erro ao comunicar com o servidor: " + e);
			System.exit(1);
		}

	}
}
