import java.net.*;
import java.io.*;

public class presencesServer {
	static int DEFAULT_PORT=2000;
	
	public static void main(String[] args) {
		int port=DEFAULT_PORT;
		Presences presences = new Presences();
			
		ServerSocket servidor = null; 
	
// Create a server socket, bound to the specified port: API java.net.ServerSocket	
		try{ //
			servidor = new ServerSocket(port); //FUI EU
	
			System.out.println("Servidor a' espera de ligacoes no porto " + port);
		} catch (IOException e){
			System.out.println("Exception: " + e);
			System.exit(1);
		} //FUI EU

		while(true) {
			try {

// Listen for a connection to be made to the socket and accepts it: API java.net.ServerSocket				
				
				Socket ligacao = servidor.accept();
				
// Start a GetPresencesRequestHandler thread			
				
				GetPresencesRequestHandler requests = new GetPresencesRequestHandler(ligacao, presences); //FUI EU
				requests.start(); //FUI EU
				
			} catch (IOException e) {
				System.out.println("Erro na execucao do servidor: "+e);
				System.exit(1);
			}
		}
	}
}
