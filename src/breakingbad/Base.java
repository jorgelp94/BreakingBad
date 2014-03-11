/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package breakingbad;

/**
 *
 * @author jorgelp94
 */
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Base {

    private int posX;    //posicion en x.       
    private int posY;	//posicion en y.
    protected Animacion anim;    //anim.
    private int alto;
    /**
     * Metodo constructor usado para crear el objeto
     *
     * @param posX es la <code>posicion en x</code> del objeto.
     * @param posY es la <code>posicion en y</code> del objeto.
     * @param image es la <code>imagen</code> del objeto.
     */
    public Base(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Metodo modificador usado para cambiar la posicion en x del objeto
     *
     * @param posX es la <code>posicion en x</code> del objeto.
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Metodo de acceso que regresa la posicion en x del objeto
     *
     * @return posX es la <code>posicion en x</code> del objeto.
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Metodo modificador usado para cambiar la posicion en y del objeto
     *
     * @param posY es la <code>posicion en y</code> del objeto.
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Metodo de acceso que regresa la posicion en y del objeto
     *
     * @return posY es la <code>posicion en y</code> del objeto.
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Metodo modificador usado para cambiar el anim del objeto
     *
     * @param anim es el <code>anim</code> del objeto.
     */
    public void setAnim(Animacion anim) {
        this.anim = anim;
    }

    /**
     * Metodo de acceso que regresa el anim del objeto
     *
     * @return anim es el <code>anim</code> del objeto.
     */
    public Animacion getAnim() {
        return anim;
    }

    /**
     * Metodo de acceso que regresa el ancho del anim
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es el ancho del
     * anim.
     */
    public int getAncho() {
        return new ImageIcon(anim.getImagen()).getIconWidth();
    }

    /**
     * Metodo de acceso que regresa el alto del anim
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es el alto del
     * anim.
     */
    public int getAlto() {
        return new ImageIcon(anim.getImagen()).getIconHeight();
    }

    /**
     * Metodo de acceso que regresa la imagen del anim
     *
     * @return un objeto de la clase <code>Image</code> que es la imagen del
     * anim.
     */
    public Image getImagenI() {
        return anim.getImagen();
    }

    /**
     * Metodo de acceso que regresa un nuevo rectangulo
     *
     * @return un objeto de la clase <code>Rectangle</code> que es el perimetro
     * del rectangulo
     */
    public Rectangle getPerimetro() {
        if(alto!=0)return new Rectangle(getPosX(), getPosY(), getAncho(), this.alto);
        return new Rectangle(getPosX(), getPosY(), getAncho(), getAlto());
    }

    /**
     * Checa si el objeto
     * <code>Base</code> intersecta a otro
     * <code>Base</code>
     *
     * @return un valor boleano <code>true</code> si lo      * intersecta <code>false</code> en caso contrario
     */
    public boolean intersecta(Base obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }

    /**
     * @param alto the alto to set
     */
    public void setAlto(int alto) {
        this.alto = alto;
    }
}