import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


/**
 * Classe Case
 */
public class Case extends JPanel implements MouseListener {

    private Gui gui;

    private int x;
    private int y;

    private Boolean hasMine = false;
    private boolean hasFlag = false;
    private int nbAround;
    public Boolean clicked = false;

    public final static int DIM = 50;
    private Color hiddenColor = Color.LIGHT_GRAY;
    private Color revealedColor = Color.WHITE;

    private Image bombImage;
    private Image flagImage;


    /**
     * Constructeur de la classe Case
     * @param hasMine
     * @param nbAround
     * @param x
     * @param y
     * @param gui
     */
    public Case(Boolean hasMine, int nbAround, int x, int y, Gui gui) {
        this.x = x;
        this.y = y;
        this.hasMine = hasMine;
        this.nbAround = nbAround;
        this.gui = gui;
        setPreferredSize(new Dimension(DIM, DIM)); // taille de la case 
        addMouseListener(this); 
        setBackground(hiddenColor);
        setBorder(new LineBorder(Color.BLACK));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        bombImage = toolkit.getImage("ressources/BombImage.png");
        flagImage = toolkit.getImage("ressources/FlagImage.png");
    }
    
    
    /**
     * Peindre le composant
     * @param gc
     */
    @Override
    public void paintComponent(Graphics gc) {
        super.paintComponent(gc);
        gc.setColor(new Color(128, 128, 128));
        gc.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
        if (clicked) {
            if (this.hasMine) {
                gc.setColor(revealedColor);
                gc.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
                gc.setColor(new Color(128, 0, 0));
                gc.drawImage(bombImage, 5, 5, getWidth() - 10, getHeight() - 10, this);
            } else if (this.nbAround > 0) {
                gc.setColor(revealedColor);
                gc.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
                gc.setColor(new Color(0, 128, 0));
                gc.drawString(String.valueOf(nbAround), getWidth() / 2 - 5, getHeight() / 2 + 5);
            } else {
                gc.setColor(revealedColor);
                gc.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
            }
        }
        if (hasFlag) {
            gc.drawImage(flagImage, 5, 5, getWidth() - 10, getHeight() - 10, this);
        }
    }
  

    /**
     * Révéler la mine
     */
    public void revelerMine() {
        if (clicked) {
            return;
        }
        clicked = true;
        hasFlag = false; // Enlever le drapeau lorsqu'une case est révélée
        if (nbAround == 0) {
            gui.propagation(x, y);
        }
        if (!hasMine) {
            gui.updateScoreLabel();
        }
        if (hasMine) {
            gui.revelerToutesLesMines();
        }
        repaint();
        gui.checkVictory(); // Vérifier si le joueur a gagné après chaque révélation
    }


    /**
     * Mettre un drapeau
     */
    public void setIconFlag() {
        if (!clicked) {  
            hasFlag = !hasFlag;  
            repaint(); 
        }
    }


    /**
     * Méthode mouseClicked
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (gui.gameOver) {
            return;
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            revelerMine();
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            setIconFlag();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}