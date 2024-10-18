import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Case extends JPanel implements MouseListener { 
  
    public Boolean clicked = false;
    private Boolean hasMine = false;
    private int nbAround;
    private Color hiddenColor = Color.LIGHT_GRAY;
    public final static int DIM = 50;
    private Color revealedColor = Color.WHITE;
    private Gui gui;
    private int x;
    private boolean hasFlag = false;
    private Image bombIcon;
    private Image flagIcon;
    private int y;

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
        bombIcon = toolkit.getImage("lib/BombImage.png");
        flagIcon = toolkit.getImage("lib/FlagImage.png");
    }
    
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
                gc.drawImage(bombIcon, 5, 5, getWidth() - 10, getHeight() - 10, this);
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
            gc.drawImage(flagIcon, 5, 5, getWidth() - 10, getHeight() - 10, this);
        }
    }
  
    public void reveal() {
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
            gui.revealAllMine();
        }
        repaint();
        gui.checkVictory(); // Vérifier si le joueur a gagné après chaque révélation
    }

    public void setIconFlag() {
        if (!clicked) {  
            hasFlag = !hasFlag;  
            repaint(); 
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (gui.gameOver) {
            return;
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            reveal();
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