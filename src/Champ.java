import java.util.Random;
import java.io.Serializable;


/**
 * Classe Champ
 */
public class Champ implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final int DEF_HEIGHT = 6;
    private static final int DEF_WIDTH = 6;
    // private static final int DEF_NBMINES = 2;
    // private int nbMines;
    private int height;
    private int width;
    private boolean[][] tabMines = new boolean[DEF_HEIGHT][DEF_WIDTH];
    private int [] tabSize = {5,10,15};
    // private int [] tabNbMines = {5,10,25}; 
    private double mineProba = 0.25 ;
    Random random = new Random();


    /**
     * Constructeur de la classe Champ
     * @param height
     * @param width
     */
    public Champ(int height,int width){
        this.height = height;
        this.width = width;
        
        this.tabMines = new boolean [height][width];
    }


    /**
     * Affiche le champ dans le terminal
     */
    public void afficherDansTerminal() {
        for (int i = 0; i < tabMines.length; i++) {
            for (int j = 0; j < tabMines[i].length; j++) { 
                if(isMines(i,j)){
                    System.out.print('x');
                }
                else{
                    System.out.print(nombreMinesAutour(i, j));
                } 
            }
            System.out.println(); 
        }
    }


    /**
     * Retourne si la case est une mine ou non
     * @param x
     * @param y
     * @return
     */
    public boolean isMines(int x,int y){
        return tabMines[x][y];
    }


    /**
     * Initialise le champ
     */
    public void init() {
        for (int i = 0; i < tabMines.length; i++) {
            for (int j = 0; j < tabMines[i].length; j++) {
                this.tabMines[i][j] = Math.random() > 1 - mineProba;
            }
        }
    }


    /**
     * Retourne le nombre de mines autour de la case
     * @param x
     * @param y
     * @return
     */
    public int nombreMinesAutour(int x, int y) {
        int count = 0;
        for (int i = Math.max(0, x - 1); i <= Math.min(tabMines.length - 1, x + 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(tabMines[0].length - 1, y + 1); j++) {
                if (tabMines[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * Retourne la largeur et la hauteur du champ
     * @return
     */
    public int getWidth(){
        return tabMines.length;
    }
    

    /**
     * Retourne la hauteur du champ
     * @return
     */
    public int getHeight(){
        return tabMines.length;
    }


    /**
     * Crée une nouvelle partie
     * @param level
     */
    public void nouvellePartie(int level) {
            tabMines = new boolean[tabSize[level]][tabSize[level]];
            init();
            afficherDansTerminal();
    }

    

}
