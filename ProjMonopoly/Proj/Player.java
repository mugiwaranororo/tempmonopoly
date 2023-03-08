package Proj;

import java.net.*;
import java.io.*;

public class Player {

    static Socket socket = null;
    static PrintWriter out = null;
    static BufferedReader in = null;

    public static void PlayButton() throws IOException {
        out.println("Play");
    }
    public static void main(String[] args) throws IOException {

        try {
            socket = new Socket("localhost", 4444); // connexion au serveur
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Serveur inconnu : " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Erreur lors de la connexion au serveur : " + e.getMessage());
            System.exit(1);
        }

        Affichage serv = new Affichage();
        serv.setVisible(true);

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        while ((userInput = stdIn.readLine()) != null) {
            System.out.println();
            out.println(userInput); // send the value to the server
            System.out.println("Server response: " + in.readLine());
            // read the response from the server (assuming it's a string)
            // and print it to the console
            // you can store the response in a variable if needed
        }

        out.close(); // fermeture des flux
        in.close();
        stdIn.close();
        socket.close(); // fermeture du socket client
    }
}
