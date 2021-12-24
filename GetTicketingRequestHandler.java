import java.net.*;
import java.io.*;
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class GetTicketingRequestHandler extends Thread {
    Socket ligacao;
    Presences presences;
    BufferedReader in;
    PrintWriter out;

    static final int ST_PORTO = 3123;
    static final String ST_HOST = "127.0.0.2"; // IP e PORTO do Serviço de Ticketing

    public GetTicketingRequestHandler(Socket ligacao, Presences presences) {
        this.ligacao = ligacao;
        this.presences = presences;
        try {
            this.in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));

            this.out = new PrintWriter(ligacao.getOutputStream());
        } catch (IOException e) {
            System.out.println("Erro na execucao do servidor: " + e);
            System.exit(1);
        }
    }

    public void run() {
        try {
            System.out.println("Aceitou ligacao de cliente no endereco " + ligacao.getInetAddress() + " na porta "
                    + ligacao.getPort());

            String response;
            String msg = in.readLine();

            System.out.println("Request=" + msg);

            StringTokenizer tokens = new StringTokenizer(msg);
            String metodo = tokens.nextToken();

            if (metodo.equals("get")) {
                String ip = tokens.nextToken();
                String identificador = tokens.nextToken();

                response = "101\n";

                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");

                    byte[] messageDigest = md.digest(identificador.getBytes());

                    BigInteger no = new BigInteger(1, messageDigest);

                    String hashtext = no.toString(16);

                    while (hashtext.length() < 32) {
                        hashtext = "0" + hashtext;
                    }

                    if (hashtext.equals(identificador)) {
                        // CRIAR UMA FUNÇÃO PARA IMPRIMIR LISTA DE SERVIÇOS
                        System.out.println("Selecione o serviço ao qual pretende aceder");
                        System.out.println("\n 1 - Serviço de Humidade \n");
                        System.out.println("\n 2 - Serviço de Temperatura \n");

                    }

                } catch (NoSuchAlgorithmException e) {
                    System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
                }

                // response2 = hashtext +"\n";
                // response2 += ST_HOST + "\n";
                // response2 += ST_PORTO;
                Vector<String> ipList = presences.getPresences(ip);
                response += ipList.size() + "\n";
                for (Iterator<String> it = ipList.iterator(); it.hasNext();) {
                    String next = it.next();
                    response += next + ";";
                }
                System.out.println(response);
                // out.println(response2);
            } else
                out.println("201;method not found");

            out.flush();
            in.close();
            out.close();
            ligacao.close();
        } catch (IOException e) {
            System.out.println("Erro na execucao do servidor: " + e);
            System.exit(1);
        }
    }
}
