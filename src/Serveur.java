/**
 * Serveur.java
 * @author: Gaël-Mehdi
 * @version: 1.0
 */


import java.net.*; // Sockets
import java.io.*; // Streams

public class Serveur {
    Serveur() {
        System.out.println("Démarrage du serveur");
        try {
            // gestionnaire de socket, port 10000
            ServerSocket gestSock = new ServerSocket(10000);

            // Accepter la connexion du premier joueur
            Socket socketJoueur1 = gestSock.accept();
            DataInputStream entreeJoueur1 = new DataInputStream(socketJoueur1.getInputStream());
            DataOutputStream sortieJoueur1 = new DataOutputStream(socketJoueur1.getOutputStream());
            ObjectOutputStream objectOutputStreamJoueur1 = new ObjectOutputStream(socketJoueur1.getOutputStream());

            // Lecture du nom du premier joueur
            String nomJoueur1 = entreeJoueur1.readUTF();
            System.out.println(nomJoueur1 + " connected");

            // Envoi d'une donnée au premier joueur
            sortieJoueur1.writeInt(0);

            // Accepter la connexion du deuxième joueur
            Socket socketJoueur2 = gestSock.accept();
            DataInputStream entreeJoueur2 = new DataInputStream(socketJoueur2.getInputStream());
            DataOutputStream sortieJoueur2 = new DataOutputStream(socketJoueur2.getOutputStream());
            ObjectOutputStream objectOutputStreamJoueur2 = new ObjectOutputStream(socketJoueur2.getOutputStream());

            // Lecture du nom du deuxième joueur
            String nomJoueur2 = entreeJoueur2.readUTF();
            System.out.println(nomJoueur2 + " connected");

            // Envoi d'une donnée au deuxième joueur
            sortieJoueur2.writeInt(0);

            // Création d'une partie
            Champ champReseau = new Champ(5, 5);
            champReseau.init();
            champReseau.afficherDansTerminal();

            // Envoi de l'objet Champ aux deux joueurs
            objectOutputStreamJoueur1.writeObject(champReseau);
            objectOutputStreamJoueur2.writeObject(champReseau);

            // Un peu de ménage
            objectOutputStreamJoueur1.close();
            sortieJoueur1.close();
            entreeJoueur1.close();
            socketJoueur1.close();

            objectOutputStreamJoueur2.close();
            sortieJoueur2.close();
            entreeJoueur2.close();
            socketJoueur2.close();

            gestSock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Serveur();
    }
}