/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package breakingbad;
/*
 * @LuisReyna A01139953
 * @JorgePerales A00813101
 * @version 1.0
 */

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.math.BigDecimal;
import java.net.URL;
import java.util.LinkedList;
import java.util.Random;
import java.io.IOException;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;

public class JFrameBB extends JFrame implements Runnable, KeyListener, MouseListener {
    private static final long serialVersionUID = 1L;
    private int vidas;    // vidas del elefante.
//    private final int MIN = -5;    //Rango minimo al generar un numero al azar.
//    private final int MAX = 6;    //Rango maximo al generar un numero al azar.
    private Image dbImage;    // Imagen a proyectar
    private Image gameover;    //Imagen a desplegar al acabar el juego.	 
    private Graphics dbg;	// Objeto grafico
    private SoundClip musicaInicio;    // Objeto SoundClip
    private SoundClip bomb;    //Objeto SoundClip 
    private SoundClip point;  //Objeto SoundClip
    private Bola bola;    // Objeto de la clase Balon
    private Barra barra; //Objeto de la clase Anotacion
    private Barra barra2; 
    private Barra barra3; //Objeto de la clase Anotacion
    private Barra barra4; 
    //Variables de control de tiempo de la animaciÃ³n
    private long tiempoActual;
    private long tiempoInicial;
    private boolean pause;
    private boolean choca;
    private boolean presionaI;
    private boolean bolaMove;
    private boolean activaSonido;
    private boolean presionaG;
    private boolean presionaC;
    private boolean gameoverB;
    private boolean presionaEnter; // Al presionar enter empieza el juego
    private boolean presionaR;
    private int y;
    private int y2;
    private int velocI;
    private double t;
    private double gravedad;
    private double angulo;
    private double anguloRadianes;
    private double cos;
    private double sin;
    private double tP;
    private int punto;
    private int caidas; //cuenta las veces que cae el balon
    private int score; // puntaje del jugador
    private String nombreArchivo;    //Nombre del archivo.
    private Vector vec;    // Objeto vector para agregar el puntaje.
    private String[] arr;  //array para obtener lo guardado
    private Image fondo;
    private Image inicial;
    private Image won;
    private Image title;
    private Image restart;
    private Image enter;
    private int fuerza;
    private int veloc; //velocidad a la que se mueve la barra
    private int dist; //distancia entre barra y barra;
    private int pass;
    private String name;
    private boolean kreal;

    /**
     * Metodo <I>init</I> sobrescrito de la clase
     * <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el
     * <code>Applet</code> y se definen funcionalidades.
     */
    public JFrameBB() {

        this.setSize(1025, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPause(false);
        vidas = 1;    // Le asignamos un valor inicial a las vidas
        bola = new Bola(getWidth()/5, getHeight()/2);
        URL tURL = this.getClass().getResource("images/back.jpeg");
        //URL tURL2 = this.getClass().getResource("images/wallpaper_inicio");
        URL tURL3 = this.getClass().getResource("images/score.png");
        URL tURL4 = this.getClass().getResource("images/game_over.png");
        URL tURL5 = this.getClass().getResource("images/title.png");
        URL tURL6 = this.getClass().getResource("images/reset.png");
        URL tURL7 = this.getClass().getResource("images/texto_inicio.png");
        fondo = Toolkit.getDefaultToolkit().getImage(tURL); //imagen de fondo al iniciar juego
        //inicial = Toolkit.getDefaultToolkit().getImage(tURL2); // imagen de fondo antes de inicial el juego
        won = Toolkit.getDefaultToolkit().getImage(tURL3); //imagen cuando ganas
        gameover = Toolkit.getDefaultToolkit().getImage(tURL4); //imagen cuando pierdes
        title = Toolkit.getDefaultToolkit().getImage(tURL5);
        restart = Toolkit.getDefaultToolkit().getImage(tURL6);
        enter = Toolkit.getDefaultToolkit().getImage(tURL7);
        
        addKeyListener(this);
        addMouseListener(this);
        nombreArchivo = "Puntaje.txt";
        vec = new Vector();
        caidas = 0;
        score = 0;
        gravedad = 9.8;
        presionaI = false;
        presionaG = false;
        presionaC = false;
        presionaEnter = false;
        gameoverB=false;
        kreal=true;
        presionaR = false;
        activaSonido = true; // El sonido esta activado al iniciar el jueg
        name="";
        //Se cargan los sonidos.

        bomb = new SoundClip("sounds/drop.wav");

        point = new SoundClip("sounds/Jump.wav");
        dist=150;
        veloc=2;
        y = (int) (Math.random() * (4*(getHeight()/5) - 100)) + 100; //85 a 112
        
        barra = new Barra(getWidth(), y-600);
        barra2= new Barra(getWidth(),y+dist);
        y2 = (int) (Math.random() * (4*(getHeight()/5) - 100)) + 100; //85 a 112
        
        barra3 = new Barra(getWidth()+561, y2-600);
        barra4= new Barra(getWidth()+561,y2+dist);
        velocI= 30;
        tP= .1;
        t= .15;
        pass=0;
        punto=500;
        start();
    }

    /**
     * Metodo <I>start</I> sobrescrito de la clase
     * <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo para la animacion este metodo
     * es llamado despues del init o cuando el usuario visita otra pagina y
     * luego regresa a la pagina en donde esta este
     * <code>Applet</code>
     *
     */
    public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase
     * <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el
     * <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {

        while (true) { 
            if(vidas!=0){
                actualiza();
                checaColision();
            }

            // Se actualiza el <code>Applet</code> repintando el contenido.
            repaint();

            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * Metodo usado para actualizar la posicion de objetos elefante y raton.
     *
     */
    public void actualiza() {
        if(vidas==0){
            
            gameoverB=true;
        //Dependiendo de la direccion del elefante es hacia donde se mueve.
        }else{
        if (!isPause() && !presionaI) {
            try {
                if (presionaC) {
                    leeArchivo();    //lee el contenido del archivo 
                }
                if (presionaG) {
                    System.out.println("Entrar1");
                    presionaG = false;
                    grabaArchivo();    //Graba el vector en el archivo.
                }

            } catch (IOException e) {
                System.out.println("Error en " + e.toString());
            }
            if (bolaMove) {
                //incrementar 
                if((score)%10==0 && score != pass){
                    System.out.println("entro : " + dist + " "+ veloc + " " + score);
                    veloc++;
                    dist -=10;
                    pass= score;
                }
                    
                barra.setPosX(barra.getPosX()-veloc);
                barra2.setPosX(barra2.getPosX()-veloc);
                barra3.setPosX(barra3.getPosX()-veloc);
                barra4.setPosX(barra4.getPosX()-veloc);
                //Guarda el tiempo actual
                long tiempoTranscurrido =
                        System.currentTimeMillis() - getTiempoActual();
                setTiempoActual(getTiempoActual() + tiempoTranscurrido);
                setAnguloRadianes(90);
                setCos(Math.cos(getAnguloRadianes()));
                setSin(Math.sin(getAnguloRadianes()));
//                int x = (int) (velocI * getCos() * t);
                if(kreal){
                int y = (int) ((velocI * sin * t) - (.5 * gravedad * t * t));
//                bola.setPosX(x);
//                System.out.println(barra.getPosY() + " " +barra.getAlto() + " "+ this.y);
                bola.setPosY(-y + getPunto());
                }
                bola.actualiza(tiempoTranscurrido);
//                barra.actualiza(tiempoTranscurrido);
                t = t + gettP();
            }
        }
        }
    }

    /**
     * Metodo usado para checar las colisiones del objeto elefante y raton con
     * las orillas del
     * <code>Applet</code>.
     */
    public void checaColision() {
        if (bola.getPosY() > getHeight()) {
            bolaMove = false;
            if (activaSonido) {
                bomb.play();
            }
            vidas--;// se resta una vida cuando el pajaro cae
                
        }
        if (bola.getPosY() <= 0) {
            bolaMove = false;
            if (activaSonido) {
                bomb.play();
            }
            vidas--;// se resta una vida cuando el pajaro toca la parte de arriba
        }
        

        //checa que la barra este dentro del applet
        if(barra.getPosX()<-97){
            y = (int) (Math.random() * (4*(getHeight()/5) - 20)) + 20; //85 a 112
            barra = new Barra(getWidth(),y-600);
            barra2= new Barra(getWidth(), y+dist);
        }
        if(barra3.getPosX()<-97){
            y2 = (int) (Math.random() * (4*(getHeight()/5) - 100)) + 100; //85 a 112
            barra3 = new Barra(getWidth(), y2-600);
            barra4= new Barra(getWidth(),y2+dist);
            
        }
        
        if (barra.intersecta(bola) || barra2.intersecta(bola) || barra3.intersecta(bola) || barra4.intersecta(bola)) {
            vidas--;
            if (activaSonido) {
                bomb.play();
            }
        }
        if ((barra.getPosX()<bola.getPosX()&& (!barra.isPasa()))){
            barra.setPasa(true);
            score++;
        }else if(barra3.getPosX()<bola.getPosX()&& (!barra3.isPasa()))
        {
            barra3.setPasa(true);
            score++;
        }
        if(vidas==0) gameoverB=true;

    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase
     * <code>Applet</code>, heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la
     * tecla.
     *
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    public void keyPressed(KeyEvent e) {
        System.out.println(name);
        if(gameoverB){
            System.out.println("entro");
            if(e.getKeyCode() == KeyEvent.VK_ENTER)
                gameoverB=false;
            else
                System.out.println(e.getKeyChar());
           name= name+e.getKeyChar() ;
        }else{  
        if (e.getKeyCode() == KeyEvent.VK_P) {
            if (isPause()) {
                setPause(false);
            } else {
                setPause(true);
            }
        }
        
        // Tecla para que la bola se mueva
        if(e.getKeyCode()== KeyEvent.VK_SPACE && presionaEnter){
            kreal=false;
            setBolaMove(true);
            t=.15;
            setPunto(bola.getPosY());
            if (vidas > 0 && activaSonido) {
                point.play();
            }
        }
        //Si se presiona la tecla I, presionaI cambia a verdadero. si se vuelve a presionar presionaI cambia a falso
        // Salen instrucciones del juego
        if (e.getKeyCode() == KeyEvent.VK_I) {
            if (presionaI) {
                presionaI = false;
            } else {
                presionaI = true;
            }
        }

        // Quita el sonido
        if (e.getKeyCode() == KeyEvent.VK_S && !presionaI) {
            if (activaSonido) {
                activaSonido = false;
            } else {
                activaSonido = true;
            }
        }

        // Tecla para guardar archivo
        if (e.getKeyCode() == KeyEvent.VK_G && !presionaI) {
            if (presionaG) {
                presionaG = false;
            } else {
                presionaG = true;
            }
        }

        // Tecla para cargar archivo
        if (e.getKeyCode() == KeyEvent.VK_C) {
            if (presionaC) {
                presionaC = false;
            } else {
                presionaC = true;
            }
        }
        
        // Tecla para iniciar el juego
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            presionaEnter = true;

        }

        // Tecla que reinicia el juego
        if (e.getKeyCode() == KeyEvent.VK_R && vidas==0) {
            vidas = 1;    // Le asignamos un valor inicial a las vidas
            bola = new Bola(getWidth()/5, getHeight()/2);
            score = 0;
            gravedad = 9.8;
            presionaEnter = true;
            bolaMove=false;
            presionaR = false;
            presionaI= false;
            gameoverB=false;
            
        kreal=true;
            name="";
            activaSonido = true; // El sonido esta activado al iniciar el jueg
            dist=150;
            veloc=2;
            y = (int) (Math.random() * (4*(getHeight()/5) - 100)) + 100; //85 a 112

            barra = new Barra(getWidth(), y-600);
            barra2= new Barra(getWidth(),y+dist);
            y2 = (int) (Math.random() * (4*(getHeight()/5) - 100)) + 100; //85 a 112

            barra3 = new Barra(getWidth()+561, y2-600);
            barra4= new Barra(getWidth()+561,y2+dist);
            velocI= 30;
            tP= .1;
            t= .15;
            pass=0;
            punto=500;
        }
        }
    }

    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    public void keyReleased(KeyEvent e) {
        kreal=true;;
        fuerza=0;
    }

    /**
     * Metodo <I>mousePressed</I>
     * En este metodo se valida en donde dio click el usario para determinar la
     * direccion
     *
     * @param e es el <code>Mouse Event</code> usado para determinar dodne dio
     * click.
     */
    public void mousePressed(MouseEvent e) {
            kreal=false;
            setBolaMove(true);
            t=.15;
            setPunto(bola.getPosY());
            if (vidas > 0) {
                point.play();
            }
    }

    /**
     * Metodo <I>mouseReleased</I>
     * En este metodo se valida en donde dio click el usario para determinar la
     * direccion
     *
     * @param e es el <code>Mouse Event</code> usado para determinar dodne dio
     * click.
     */
    public void mouseReleased(MouseEvent e) {
        kreal=true;
    }

    /**
     * Metodo <I>mouseReleased</I>
     * En este metodo se valida en donde dio click el usario para determinar la
     * direccion
     *
     * @param e es el <code>Mouse Event</code> usado para determinar dodne dio
     * click.
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Metodo <I>mouseReleased</I>
     * En este metodo se valida en donde dio click el usario para determinar la
     * direccion
     *
     * @param e es el <code>Mouse Event</code> usado para determinar dodne dio
     * click.
     */
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Metodo <I>mouseReleased</I>
     * En este metodo se valida en donde dio click el usario para determinar la
     * direccion
     *
     * @param e es el <code>Mouse Event</code> usado para determinar dodne dio
     * click.
     */
    public void mouseClicked(MouseEvent e) {
        setBolaMove(true);
            t=.15;
            setPunto(bola.getPosY());
            if (vidas > 0 && activaSonido) {
                point.play();
            }
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase
     * <code>Applet</code>, heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        
            //          g.drawImage(fondo.getImage(), 0, 0,1300,700, this);
        g.setFont(new Font("default", Font.BOLD, 16));
        g.setColor(Color.white);
        if (vidas > 0) {
            if (bola != null) {
                g.drawImage(fondo, 0, 0, 1024, 640, this);
                //Dibuja la imagen en la posicion actualizada
                g.drawImage(bola.getImagenI(), bola.getPosX(), bola.getPosY(), this);
                //Dibuja la imagen en la posicion actualizada
                g.drawImage(barra.getImagenI(), barra.getPosX(), barra.getPosY(),this);
                g.drawImage(barra2.getImagenI(), barra2.getPosX(), barra2.getPosY(),this);
                g.drawImage(barra3.getImagenI(), barra3.getPosX(), barra3.getPosY(),this);
                g.drawImage(barra4.getImagenI(), barra4.getPosX(), barra4.getPosY(),this);
//                g.drawString("Puntos : " + list.get(0).getNum(), 10, 10);
                //Muestra las vidas
                g.drawString("Vidas: " + vidas, getWidth() / 2 - 10, 80);
                //Muestra el puntaje
                g.drawString("Distancia: " + score, bola.getAncho(), 80);
                if (isPause()) {
                    g.drawString(bola.getPAUSE(), bola.getPosX() + 15, bola.getPosY() + 30);
                }
                if (isChoca()) {
                    g.drawString(bola.getDISP(), bola.getPosX() + 15, bola.getPosY() + 30);
                    choca = false;
                }
                if (!presionaEnter) {
                    g.drawImage(fondo, 0, 0, 1024, 640, this);
                    g.setColor(Color.white);
                    g.drawImage(title, 260, 120, this);
                    g.setFont(new Font("defalut", Font.BOLD, 16));
                    //g.drawString("Presiona ENTER para iniciar el juego",370 ,600 );
                    g.drawImage(enter, 325, 580, this);

                }
                if (presionaI) {

                    g.drawString("Instrucciones:", getWidth() / 4 + getWidth() / 8, 200);
                    g.drawString("Presiona la barra espaciadora para elevar el", getWidth() / 4 + getWidth() / 8, 220);
                    g.drawString("pajaro y esqiva los obstaculos que se mueven.", getWidth() / 4 + getWidth() / 8, 240);
                    
                    g.drawString("Teclas: ", getWidth() / 4 + getWidth() / 8, 300);
                    /*
                    g.drawString("Flecha izquierda - se mueve a la izquierda", getWidth() / 4 + getWidth() / 8, 320);
                    g.drawString("Flecha derecha - se mueve a la derecha", getWidth() / 4 + getWidth() / 8, 340);
                    */
                    g.drawString("I - muestra/oculta instrucciones", getWidth() / 4 + getWidth() / 8, 320);
                    /*
                    g.drawString("G - guarda el juego", getWidth() / 4 + getWidth() / 8, 380);
                    g.drawString("C - carga el juego", getWidth() / 4 + getWidth() / 8, 400);
                    */
                    g.drawString("P - pausa el juego", getWidth() / 4 + getWidth() / 8, 340);
                    g.drawString("S - activa/desactiva el sonido del juego", getWidth() / 4 + getWidth() / 8, 360);
                    g.drawString("SPACE - eleva el pajaro", getWidth() / 4 + getWidth() / 8, 380);
                   
                }
            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..", 20, 20);
            }
        } else {
            g.drawImage(fondo, 0,0, 1024, 640, this);
            g.setFont(new Font("Helvetica", Font.BOLD, 40));
            g.drawImage(gameover, 350, 120, this);
            g.drawImage(won,390, 370, this);
            g.setColor(Color.white);
            g.drawString("" + score, 620, 410);
            g.drawImage(restart, 120, 550, this);
        }
        
       

    }

    /**
     * @return the tiempoActual
     */
    public long getTiempoActual() {
        return tiempoActual;
    }

    /**
     * @param tiempoActual the tiempoActual to set
     */
    public void setTiempoActual(long tiempoActual) {
        this.tiempoActual = tiempoActual;
    }

    /**
     * @return the tiempoInicial
     */
    public long getTiempoInicial() {
        return tiempoInicial;
    }

    /**
     * @param tiempoInicial the tiempoInicial to set
     */
    public void setTiempoInicial(long tiempoInicial) {
        this.tiempoInicial = tiempoInicial;
    }

    /**
     * @return the pause
     */
    public boolean isPause() {
        return pause;
    }

    /**
     * @param pause the pause to set
     */
    public void setPause(boolean pause) {
        this.pause = pause;
    }

    /**
     * @return the choca
     */
    public boolean isChoca() {
        return choca;
    }

    /**
     * @param choca the choca to set
     */
    public void setChoca(boolean choca) {
        this.choca = choca;
    }

    /**
     * @return the bolaMove
     */
    public boolean isBolaMove() {
        return bolaMove;
    }

    /**
     * @param bolaMove the bolaMove to set
     */
    public void setBolaMove(boolean bolaMove) {
        this.bolaMove = bolaMove;
    }

    /**
     * @return the velocI
     */
    public int getVelocI() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setVelocI(int y) {
        this.y = y;
    }

    /**
     * @return the t
     */
    public double getT() {
        return t;
    }

    /**
     * @param t the t to set
     */
    public void setT(double t) {
        this.t = t;
    }

    /**
     * @return the gravedad
     */
    public double getGravedad() {
        return gravedad;
    }

    /**
     * @param gravedad the gravedad to set
     */
    public void setGravedad(double gravedad) {
        this.gravedad = gravedad;
    }

    /**
     * @return the angulo
     */
    public double getAngulo() {
        return angulo;
    }

    /**
     * @param angulo the angulo to set
     */
    public void setAngulo(double angulo) {
        this.angulo = angulo;
    }

    /**
     * @return the anguloRadianes
     */
    public double getAnguloRadianes() {
        return anguloRadianes;
    }

    /**
     * @param anguloRadianes the anguloRadianes to set
     */
    public void setAnguloRadianes(double anguloRadianes) {
        this.anguloRadianes = anguloRadianes;
    }

    /**
     * @return the cos
     */
    public double getCos() {
        return cos;
    }

    /**
     * @param cos the cos to set
     */
    public void setCos(double cos) {
        this.cos = cos;
    }

    /**
     * @return the sin
     */
    public double getSin() {
        return sin;
    }

    /**
     * @param sin the sin to set
     */
    public void setSin(double sin) {
        this.sin = sin;
    }

    /**
     * @return the barra
     */
    public Barra getBarra() {
        return barra;
    }

    /**
     * @param barra the barra to set
     */
    public void setBarra(Barra barra) {
        this.barra = barra;
    }

    /**
     * @LuisReyna
     * @JorgePerales
     * @version1
     * Metodo que lee a informacion de un archivo y lo agrega a un vector.
     *
     * @throws IOException
     */
    public void leeArchivo() throws IOException {
        BufferedReader fileIn;
        try {
            presionaC = false;
            fileIn = new BufferedReader(new FileReader(nombreArchivo));
            String dato = fileIn.readLine();
            setArr(dato.split(","));
            score = (Integer.parseInt(arr[0]));
            vidas = (Integer.parseInt(arr[1]));
            bola.setPosX(Integer.parseInt(arr[2]));
            bola.setPosX(Integer.parseInt(arr[3]));
            barra.setPosX(Integer.parseInt(arr[4]));
            barra.setPosY(Integer.parseInt(arr[5]));
            bolaMove = true;
            gravedad = (Double.parseDouble(arr[7]));
            angulo = (Double.parseDouble(arr[8]));
            y = (Integer.parseInt(arr[9]));
            t = (Double.parseDouble(arr[10]));
            activaSonido = (Boolean.parseBoolean(arr[12]));
            fileIn.close();
            actualiza();
        } catch (IOException ioe) {
            System.out.println("Se arrojo una excepcion " + ioe.toString());
        }
    }

    /**
     * @LuisReyna
     * @JorgePerales
     * @version1
     * Metodo que agrega la informacion del vector al archivo.
     *
     * @throws IOException
     */
    public void grabaArchivo() throws IOException {
        try {
            PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));

            fileOut.println("" + score + "," + vidas + "," + bola.getPosX() + "," + bola.getPosY() + "," + barra.getPosX() + "," + barra.getPosY() + "," + bolaMove + "," + gravedad + "," + angulo + "," + y + "," + t + "," + activaSonido);

            fileOut.close();
        } catch (IOException ioe) {
            System.out.println("Se arrojo una excepcion " + ioe.toString());
        }
    }

    /**
     * @return the arr
     */
    public String[] getArr() {
        return arr;
    }

    /**
     * @param arr the arr to set
     */
    public void setArr(String[] arr) {
        this.arr = arr;
    }

    /**
     * @return the fuerza
     */
    public int getFuerza() {
        return fuerza;
    }

    /**
     * @param fuerza the fuerza to set
     */
    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    /**
     * @return the tP
     */
    public double gettP() {
        return tP;
    }

    /**
     * @param tP the tP to set
     */
    public void settP(double tP) {
        this.tP = tP;
    }

    /**
     * @return the punto
     */
    public int getPunto() {
        return punto;
    }

    /**
     * @param punto the punto to set
     */
    public void setPunto(int punto) {
        this.punto = punto;
    }
    
    /**
     * @return the fondo
     */
    //public ImageIcon getFondo() {
    //    return fondo;
    //}

    /**
     * @param fondo the fondo to set
     */
    //public void setFondo(ImageIcon fondo) {
    //    this.fondo = fondo;
    //}

}
