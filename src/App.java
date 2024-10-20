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
        try {// ouverture de la socket et des streams
            Socket sock = new Socket("localhost",10000);
            DataOutputStream out =new DataOutputStream(sock.getOutputStream());
            DataInputStream in = new DataInputStream(sock.getInputStream());
            out.writeUTF(gui.champNomJoueur.getText());


            String champReseauJoueur = in.readUTF() ; // Réception du champ par le joueur
            System.out.println(champReseauJoueur);

            int numJoueur = in.readInt(); // reception d’un nombre
            System.out.println("Joueur n°:"+numJoueur);
            in.close(); // fermeture Stream
            out.close();
            sock.close() ; // fermeture Socket
        } catch (UnknownHostException e) {
            System.out.println("R2D2 est inconnue");
        } catch (IOException e) {e.printStackTrace();}
    }

}