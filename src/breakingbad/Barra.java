/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package breakingbad;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 *
 * @author jorgelp94
 */
public class Barra extends Base{
    private static int conteo = 0;
    //private Animacion anim;

    //Metodo constructor que hereda los atributos de la clase ObjetoEspacial
    // posX es la posicion en x del planeta
    // posY es la posicion en y del planeta
    
    public Barra(int posX, int posY) {
        super(posX, posY);
//        Image jugador1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/jugador1.gif"));
	Image barra = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/obstaculo_600.png"));
        /*
	Image jugador3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/jugador3.gif"));
        Image jugador4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/jugador4.gif"));
	Image jugador5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/jugador5.gif"));
	Image jugador6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/jugador6.gif"));
        Image jugador7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/jugador7.gif"));
	Image jugador8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/jugador8.gif"));
	Image jugador9 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/jugador9.gif"));
        Image jugador10 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/jugador10.gif"));
	*/
        
        //Se inicializa la animación
        anim = new Animacion();
//	anim.sumaCuadro(jugador1, 100);
	anim.sumaCuadro(barra, 100);
        /*
	anim.sumaCuadro(jugador3, 100);
        anim.sumaCuadro(jugador4, 100);
        anim.sumaCuadro(jugador5, 100);
        anim.sumaCuadro(jugador6, 100);
        anim.sumaCuadro(jugador7, 100);
        anim.sumaCuadro(jugador8, 100);
        anim.sumaCuadro(jugador9, 100);
        anim.sumaCuadro(jugador10, 100);
        */
        conteo++;
    }
    
    public static int getConteo() {
        return conteo;
    }
    
    public static void setConteo(int cont) {
        conteo = cont;
    }
    
    //Método que actualiza la animacion
    public void actualiza(long tiempo) {
        anim.actualiza(tiempo);
    }
}
