/**
 * Gui.java
 * @author: Gaël-Mehdi
 * @version: 1.0
 */


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.time.Duration;
import java.time.Instant;


/**
 * Classe Gui
 */
public class Gui extends JPanel implements ActionListener {

    private App app;
    private Champ champ;
    private Case[][] tabCase;

    private JMenuBar menuBar; // Barre de menus

    private JMenu menuFichier; // Menu Fichier
    private JMenuItem menuItemNewGame;
    private JMenu menuLevel;
    private JMenuItem menuItemLevelEasy;
    private JMenuItem menuItemLevelMedium;
    private JMenuItem menuItemLevelHard;
    private JMenuItem menuItemQuit;

    private JMenu menuReseau; // Menu Réseau
    private JMenuItem menuLocal;
    private JMenuItem menuItemReseau;

    public JTextField champNomJoueur;

    public JLabel textLabel;
    private JLabel[][] tLabel;
    
    private JButton butQuit;
    private JButton newGame;

    private JLabel level;
    private JComboBox<Level> boxLevel;
    private JLabel score;
    private JPanel panelMines;

    // Pour le score de la partie
    private Instant startTime;
    private Instant endTime;
    private Instant instTime;

    public Boolean gameOver = false;
    public double scoreDixieme = 0;

    SoundPlayer soundPlayer = new SoundPlayer();


    /**
     * Constructeur de la classe Gui
     * @param champ
     * @param app
     */
    Gui(Champ champ, App app) {
        menuBar = new JMenuBar();
        menuFichier = new JMenu("Fichier");
        menuItemQuit = new JMenuItem("Quitter", KeyEvent.VK_Q);
        menuItemNewGame = new JMenuItem("Nouvelle Partie", KeyEvent.VK_N);
        menuLevel = new JMenu("Level");
        menuItemLevelEasy = new JMenuItem("EASY", KeyEvent.VK_E);
        menuItemLevelMedium = new JMenuItem("MEDIUM", KeyEvent.VK_M);
        menuItemLevelHard = new JMenuItem("HARD", KeyEvent.VK_H);

        menuReseau = new JMenu("Réseau");
        menuLocal = new JMenuItem("Jouer en local", KeyEvent.VK_L);
        menuItemReseau = new JMenuItem("Jouer en réseau", KeyEvent.VK_R);

        this.app = app;
        this.champ = champ;
        this.tLabel = new JLabel[champ.getWidth()][champ.getHeight()];
        this.setLayout(new BorderLayout());

        // création de contenu dans le JPanel
        menuBar.add(menuFichier);

        menuFichier.add(menuItemNewGame);
        menuFichier.add(menuLevel);
        menuFichier.add(menuItemQuit);

        menuItemNewGame.addActionListener(this);
        menuItemQuit.addActionListener(this);

        menuLevel.add(menuItemLevelEasy);
        menuLevel.add(menuItemLevelMedium);
        menuLevel.add(menuItemLevelHard);

        menuBar.add(menuReseau);
        menuReseau.add(menuLocal);
        menuReseau.add(menuItemReseau);

        menuLocal.addActionListener(this);
        menuItemReseau.addActionListener(this);

        // Ajouter le champ de texte à la barre des menus
        champNomJoueur = new JTextField(20);
        menuBar.add(Box.createHorizontalGlue()); // Pour pousser le champ de texte à droite
        menuBar.add(new JLabel("Nom :"));
        menuBar.add(champNomJoueur);

        menuItemLevelEasy.addActionListener(this);
        menuItemLevelMedium.addActionListener(this);
        menuItemLevelHard.addActionListener(this);
        app.setJMenuBar(menuBar);
        textLabel = new JLabel("Démineur");
        boxLevel = new JComboBox<>(Level.values());
        boxLevel.addActionListener(this);
        score = new JLabel("Score:" + scoreDixieme);
        level = new JLabel("EASY");
        butQuit = new JButton("Quit");
        butQuit.addActionListener(this);
        newGame = new JButton("Nouvelle Partie");
        newGame.addActionListener(this);

        // ajout du contenu dans le Panel
        JPanel Nord = new JPanel();
        Nord.add(textLabel);
        Nord.add(level);
        Nord.add(boxLevel);
        Nord.add(score);
        add(Nord, BorderLayout.NORTH);
        // Panel Sud
        JPanel Sud = new JPanel();
        Sud.add(newGame);
        Sud.add(butQuit);
        add(Sud, BorderLayout.SOUTH);
        // JPanel des mines
        panelMines = new JPanel();
        panelMines.setLayout(new GridLayout(champ.getWidth(), champ.getHeight()));
        add(panelMines);
    }


    /**
     * Méthode actionPerformed
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGame || e.getSource() == menuItemNewGame) {
            startTime = Instant.now(); // Initialise le timer
            System.out.println("newGame\n");
            app.nouvellePartie(boxLevel.getSelectedIndex());
        }
        if (e.getSource() == menuItemLevelEasy || e.getSource() == menuItemLevelMedium || e.getSource() == menuItemLevelHard) {
            LevelPanelMenu(e);
        } else if (e.getSource() == boxLevel) {
            LevelPanelComboBox();
        } else if (e.getSource() == butQuit || e.getSource() == menuItemQuit) {
            app.quit();
        } else if (e.getSource() == menuLocal) {
            System.out.println("Jouer en local\n");
        } else if (e.getSource() == menuItemReseau) {
            app.connexionReseau();
        }
    }


    /**
     * Méthode nouvellePartie
     * @param level
     */
    public void nouvellePartie(int level) {
        score.setText("0");
        panelMines.removeAll();
        majPanelMines();
        app.pack();
    }


    /**
     * Méthode LevelPanelMenu
     * @param e
     */
    public void LevelPanelMenu(ActionEvent e) {
        if (e.getSource() == menuItemLevelEasy) {
            level.setText("EASY");
            boxLevel.setSelectedItem(Level.EASY);
            app.nouvellePartie(Level.EASY.ordinal());
        } else if (e.getSource() == menuItemLevelMedium) {
            level.setText("MEDIUM");
            boxLevel.setSelectedItem(Level.MEDIUM);
            app.nouvellePartie(Level.MEDIUM.ordinal());
        } else if (e.getSource() == menuItemLevelHard) {
            level.setText("HARD");
            boxLevel.setSelectedItem(Level.HARD);
            app.nouvellePartie(Level.HARD.ordinal());
        }

        app.pack();
    }


    /**
     * Méthode LevelPanelComboBox
     */
    public void LevelPanelComboBox() {
        if (boxLevel.getSelectedIndex() == 0) {
            level.setText("EASY");
        } else if (boxLevel.getSelectedIndex() == 1) {
            level.setText("MEDIUM");
        } else if (boxLevel.getSelectedIndex() == 2) {
            level.setText("HARD");
        }
        app.pack();
    }


    /**
     * Méthode getCase
     * @param x
     * @param y
     * @return
     */
    public Case getCase(int x, int y) {
        return tabCase[x][y];
    }


    /**
     * Méthode setChamp
     * @param champ
     */
    public void setChamp(Champ champ) {
        this.champ = champ;
    }


    /**
     * Méthode updateScoreLabel
     */
    public void updateScoreLabel() {
        instTime = Instant.now();
        long timeElapsedMillis = Duration.between(startTime, instTime).toMillis();
        double timeElapsedSeconds = timeElapsedMillis / 1000.0;
        double roundedTimeElapsedSeconds = Math.round(timeElapsedSeconds * 10) / 10.0;
        scoreDixieme = roundedTimeElapsedSeconds;
        score.setText("Score: " + scoreDixieme);
    }


    /**
     * Méthode majPanelMines
     */
    void majPanelMines() {
        this.tLabel = new JLabel[champ.getWidth()][champ.getHeight()];
        this.tabCase = new Case[champ.getWidth()][champ.getHeight()];
        panelMines.removeAll();
        panelMines.setLayout(new GridLayout(champ.getWidth(), champ.getHeight()));
        System.out.println(String.valueOf(champ.getWidth()));
        System.out.println(String.valueOf(champ.getHeight()));
        for (int i = 0; i < champ.getWidth(); i++) {
            for (int j = 0; j < champ.getHeight(); j++) {
                tabCase[i][j] = new Case(champ.isMines(i, j), champ.nombreMinesAutour(i, j), i, j, this);
                panelMines.add(tabCase[i][j]);
            }
        }
        // Ajuster la taille du panel pour qu'il soit suffisamment grand
        panelMines.setPreferredSize(new Dimension(champ.getWidth() * Case.DIM, champ.getHeight() * Case.DIM));
        app.pack();
    }

    /**
     * Méthode propagation
     * @param x
     * @param y
     */
    void propagation(int x, int y) {
        for (int i = Math.max(0, x - 1); i <= Math.min(champ.getWidth() - 1, x + 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(champ.getHeight() - 1, y + 1); j++) {
                if (tabCase[i][j].clicked == false) {
                    tabCase[i][j].revelerMine();
                }
            }
        }
    }


    /**
     * Méthode qui révèle toutes les mines
     */
    void revelerToutesLesMines() {
        soundPlayer.playSound("ressources/GameOverEffect.wav");
        if (gameOver) {
            return;
        }
        for (int i = 0; i < champ.getWidth(); i++) {
            for (int j = 0; j < champ.getHeight(); j++) {
                if (champ.isMines(i, j)) {
                    this.getCase(i, j).revelerMine();
                }
            }
            gameOver = true;
            textLabel.setText("Game Over!");
        }
        endTime = Instant.now();
        long timeElapsedMillis = Duration.between(startTime, endTime).toMillis();
        double timeElapsedSeconds = timeElapsedMillis / 1000.0;
        System.out.printf("Temps écoulé: %.1f secondes%n", timeElapsedSeconds);
    }


    /**
     * Méthode checkVictory
     */
    public void checkVictory() {
        for (int i = 0; i < champ.getWidth(); i++) {
            for (int j = 0; j < champ.getHeight(); j++) {
                Case currentCase = this.getCase(i, j);
                if (!currentCase.clicked && !champ.isMines(i, j)) {
                    return; // Il reste des cases non découvertes qui ne sont pas des mines
                }
            }
        }
        // Si toutes les cases non découvertes sont des mines, le joueur a gagné
        ecranBravo();
        endTime = Instant.now();
        long timeElapsedMillis = Duration.between(startTime, endTime).toMillis();
        double timeElapsedSeconds = timeElapsedMillis / 1000.0;
        System.out.printf("Temps écoulé: %.1f secondes%n", timeElapsedSeconds);
    }


    /**
     * Méthode ecranAccueil
     */
    public void ecranAccueil() {
        // Retirer la grille de mines
        panelMines.removeAll();

        // Créer un label avec un message de bienvenue
        JLabel welcomeLabel = new JLabel("Bienvenue dans le démineur !");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Centrer le texte
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Changer la taille et le style de la police

        // Ajouter le label dans le panneau central
        panelMines.setLayout(new BorderLayout());
        panelMines.add(welcomeLabel, BorderLayout.CENTER);

        // Ajuster la taille du panel pour qu'il soit suffisamment grand
        panelMines.setPreferredSize(new Dimension(400, 300));  // Ajuster les dimensions selon vos besoins

        // Mettre à jour l'interface pour s'assurer que les changements sont pris en compte
        panelMines.revalidate();
        panelMines.repaint();

        // Redimensionner la fenêtre pour s'adapter au contenu
        app.pack();
    }


    /**
     * Méthode ecranBravo
     */
    public void ecranBravo(){
        // Retirer la grille de mines
        panelMines.removeAll();

        // Créer un label avec un message de félicitations
        JLabel congratsLabel = new JLabel("Bravo, vous avez gagné !");
        congratsLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Centrer le texte
        congratsLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Changer la taille et le style de la police

        // Ajouter le label dans le panneau central
        panelMines.setLayout(new BorderLayout());
        panelMines.add(congratsLabel, BorderLayout.CENTER);

        // Ajuster la taille du panel pour qu'il soit suffisamment grand
        panelMines.setPreferredSize(new Dimension(400, 300));  // Ajuster les dimensions selon vos besoins

        // Mettre à jour l'interface pour s'assurer que les changements sont pris en compte
        panelMines.revalidate();
        panelMines.repaint();

        // Redimensionner la fenêtre pour s'adapter au contenu
        app.pack();
    }


}