/**
 * App.java
 * @author: Gaël-Mehdi
 * @version: 1.0
 */


import java.net.*;
import java.io.*;
import javax.swing.JFrame;


/**
 * Classe App
 */
public class App extends JFrame {
    private Champ champ;
    private Gui gui;

    App() {
        champ = new Champ(6, 6); // champ créé en arrière-plan mais inutile

        // creation d'un JPanel : conteneur
        gui = new Gui(champ, this);
        // gui.majPanelMines();

        // affectation du Jpanel dans le Jframe
        setContentPane(gui);
        pack();
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Appel de la méthode ecranAccueil()
        gui.ecranAccueil();
    }


    /**
     * Méthode main
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Lancement du démineur");
        // connexionReseau();
        new App();
        new Serveur();
    }


    /**
     * Méthode pour quitter l'application
     */
    public void quit() {
        System.exit(0);
    }


    /**
     * Méthode nouvellePartie
     * @param level
     */
    public void nouvellePartie(int level) {
        gui.gameOver = false;
        champ.nouvellePartie(level);
        gui.nouvellePartie(level);
        gui.textLabel.setText("Démineur");
    }


    /**
     * Méthode connexionReseau
     */
    public void connexionReseau(){
        try {
            Socket socket = new Socket("localhost", 10000);

            // ouverture des streams
            DataInputStream entree = new DataInputStream(socket.getInputStream());
            DataOutputStream sortie = new DataOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            // envoi d’une donnée
            sortie.writeUTF("Joueur1");

            // lecture d’une donnée
            int response = entree.readInt();
            System.out.println("Réponse du serveur: " + response);

            // réception de l'objet Champ
            Champ champReseau = (Champ) objectInputStream.readObject();
            System.out.println("Champ reçu: " + champReseau);

            // mise à jour du champ et de l'interface graphique
            this.champ = champReseau;
            gui.setChamp(champReseau);
            this.champ.afficherDansTerminal();
            gui.majPanelMines();

            // un peu de ménage
            objectInputStream.close();
            sortie.close();
            entree.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}