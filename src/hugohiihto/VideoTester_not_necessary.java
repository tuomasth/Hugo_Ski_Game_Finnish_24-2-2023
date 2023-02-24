package hugohiihto;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.sound.sampled.FloatControl;

/**
 * Hugo Ski Game by T. Hyvönen, 2023 
 * 
 * ----
 * 
 * 
 * Video testing possibilities are here. The Java codes here all never needed. Some variables are garbage/leftovers. 
 * The purpose was to try new computations and process files in new different ways + test things out. 
 * Also check out the .form file for a user interface. 
 * (This is the 3rd .java file but never used.) 
 * 
 * Tested with Microsoft Windows 11, Java 8 u361 64bit. JDK 18.0.2 64bit. 
 * Java developed by Oracle / Sun Microsystems. 
 * Created with Apache NetBeans 15 
 * Official release date: 24/2/2023 - v1.0 Finnish version available worldwide 
 * @author Tuomas Hyvönen
 * @version 1.0
 */
public class VideoTester_not_necessary extends javax.swing.JFrame{
    
    boolean titlescreen = true;
    boolean actual_game = false; 
    boolean memory_puzzle = false;
    boolean guessing_rope_game = false;
    
    int enterPressCount = 0;
    boolean titleAndMusic = false;
    boolean videoIsOn = false;
    //boolean waiting = false;
    Clip clip = null; // menu music
    static Clip clip2 = null; // popcorn music, in testing only though
    Clip clipHoplaa = null;
    Player mediaPlayer = null;
    //BufferedImage bufferedImage = null;
    
    
    Graphics g = new Graphics() {
        @Override
        public Graphics create() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void translate(int x, int y) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public Color getColor() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setColor(Color c) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setPaintMode() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setXORMode(Color c1) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public Font getFont() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setFont(Font font) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public FontMetrics getFontMetrics(Font f) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public Rectangle getClipBounds() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void clipRect(int x, int y, int width, int height) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setClip(int x, int y, int width, int height) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public Shape getClip() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setClip(Shape clip) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void copyArea(int x, int y, int width, int height, int dx, int dy) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawLine(int x1, int y1, int x2, int y2) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void fillRect(int x, int y, int width, int height) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void clearRect(int x, int y, int width, int height) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawOval(int x, int y, int width, int height) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void fillOval(int x, int y, int width, int height) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawString(String str, int x, int y) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawString(AttributedCharacterIterator iterator, int x, int y) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void dispose() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    };
    Graphics g2 = new Graphics() {
        @Override
        public Graphics create() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void translate(int x, int y) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public Color getColor() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setColor(Color c) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setPaintMode() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setXORMode(Color c1) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public Font getFont() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setFont(Font font) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public FontMetrics getFontMetrics(Font f) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public Rectangle getClipBounds() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void clipRect(int x, int y, int width, int height) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setClip(int x, int y, int width, int height) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public Shape getClip() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setClip(Shape clip) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void copyArea(int x, int y, int width, int height, int dx, int dy) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawLine(int x1, int y1, int x2, int y2) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void fillRect(int x, int y, int width, int height) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void clearRect(int x, int y, int width, int height) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawOval(int x, int y, int width, int height) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void fillOval(int x, int y, int width, int height) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawString(String str, int x, int y) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void drawString(AttributedCharacterIterator iterator, int x, int y) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void dispose() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    };
    
    /**
     * Creates new form UserInterface
     */
    public VideoTester_not_necessary() {
        setUndecorated(true);
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        /**
        HugoHiihto hugoHiihto = new HugoHiihto();
        TimerTask timerTask = hugoHiihto.new GameLoop();
        java.util.Timer g = new java.util.Timer(true);
        g.scheduleAtFixedRate(timerTask, 0, 2500); // 2500 = 2.5 sec 
        **/
        
        
        File fileMenuMusic = new File("./src/hugohiihto/music-ps1hugo2menu.wav");
        try {
            clip = AudioSystem.getClip();
        } 
        catch (LineUnavailableException ex) {
            Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            clip.open(AudioSystem.getAudioInputStream(fileMenuMusic));
        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        File fileGameMusic = new File("./src/hugohiihto/music-djhugopopcorn.wav");
        try {
            clip2 = AudioSystem.getClip();
        } 
        catch (LineUnavailableException ex) {
            Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            clip2.open(AudioSystem.getAudioInputStream(fileGameMusic));
        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        File fileHoplaa = new File("./src/hugohiihto/hoplaa_nyt_hommiin.wav");
        try {
            clipHoplaa = AudioSystem.getClip();
        } 
        catch (LineUnavailableException ex) {
            Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            clipHoplaa.open(AudioSystem.getAudioInputStream(fileHoplaa));
        } 
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelGAME = new javax.swing.JPanel();
        jLabelGAME = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanelGAME.setBackground(new java.awt.Color(0, 51, 51));
        jPanelGAME.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanelGAMEKeyPressed(evt);
            }
        });

        jLabelGAME.setBackground(new java.awt.Color(0, 51, 51));
        jLabelGAME.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelGAME.setForeground(new java.awt.Color(255, 255, 204));
        jLabelGAME.setText("<html>Press ENTER / Paina ENTER-näppäintä    ------    esc = exit / esc = poistu<br><br>This is a fan-made Java game software, not for commercial purposes. If you paid money for this, you have been fooled.<br> Hugo the Troll is created by Ivan Sølvason and Niels Krogh Mortensen, Denmark.<br> Please support the official Hugo releases! Hugo © Interactive Television Entertainment (ITE Media)<br> <br>Tämä Java-pelisovellus on fanitekoinen, ei kaupallisiin tarkoituksiin. Mikäli maksoitte rahaa tästä, teitä on huijattu.<br> Hugo-peikon luojat ovat Ivan Sølvason ja Niels Krogh Mortensen, Tanska.<br> Olkaa hyvä ja tukekaa virallisia Hugo-julkaisuja! Hugo © Interactive Television Entertainment (ITE Media)<br></html>");
        jLabelGAME.setHorizontalAlignment(jLabelGAME.CENTER);

        javax.swing.GroupLayout jPanelGAMELayout = new javax.swing.GroupLayout(jPanelGAME);
        jPanelGAME.setLayout(jPanelGAMELayout);
        jPanelGAMELayout.setHorizontalGroup(
            jPanelGAMELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGAMELayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelGAME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelGAMELayout.setVerticalGroup(
            jPanelGAMELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelGAME, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelGAME, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelGAME, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   /**
    * Key pressed. 
    */
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        
        if(KeyEvent.VK_ESCAPE == evt.getKeyCode()) {
            System.out.println("Bye bye!");
            System.exit(0);
        }
        
        if(KeyEvent.VK_ENTER == evt.getKeyCode()) {
            
            
            System.out.println("ENTER pressed " + enterPressCount);
            jPanelGAME.repaint();
            
            //jLabelGAME.removeAll();
            //jLabelGAME.setVisible(false);
            //jLabelGAME.setSize(1, 1);
            Dimension d = jPanelGAME.getPreferredSize();
            jLabelGAME.setSize(d.height, d.width);
            jLabelGAME.repaint();
            jPanelGAME.repaint();
            
            
            if(titlescreen) {
                
                enterPressCount++;
                //if (fileMenuMusic.exists()) {
                    if(!titleAndMusic) { // prevents a bug with multiple musics at the same time 
                        
                        if(enterPressCount <=1 ) {
                            
                            clip.start();
                            //clip.loop(Clip.LOOP_CONTINUOUSLY);
                        }
                        else {
                            clip.stop();
                            clip.loop(0);
                            clip.stop();
                            clip.close();
                            clip.flush();
                            clip.setFramePosition(0);
                            //clip = null;
                        }
                    }
                //}
                    if(enterPressCount <=1 ) {
                        //
                    }
                    else {
                        
                        clip.stop();
                        clip.close();
                        clip.flush();
                        //g = null;
                        
                        jPanelGAME.setVisible(false);
                        
                        
                        //jLabelGAME.setBackground(Color.red);
                        //jPanelGAME.repaint();
                        jLabelGAME.repaint();
                        
                        
                        if(!videoIsOn) {
                            videoIsOn = true;
                            /**
                            try {

                                bufferedImage = ImageIO.read(new File("./src/hugohiihto/scylla_intro_slower.gif"));
                            } 
                            catch (IOException ex) {
                                Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            **/
                            
                            Dimension dim = jLabelGAME.getPreferredSize();
                            /**
                            Image newimg = null;
                            try {
                                newimg = ImageIO.read(new File("./src/hugohiihto/scylla_intro.gif")).getScaledInstance(
                                        dim.width/2, dim.height,  java.awt.Image.SCALE_SMOOTH);
                            } catch (IOException ex) {
                                Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            **/
                            
                            
                            ///// 
                            
                            
                                // 0 = Scylla intro,        1 = Hugo's first words hoplaa,
                                // 2 = Scylla button,       3 = three ropes intro,
                                // 4 = Hugo asks for two,   5 = two chosen correctly,
                                // 6 = made a wrong choice, 7 = Hugo finished the skiing,
                                // 8 = knock wake up pahvi, 9 = knock now the last troll going,
                                // 10 = knock game over,    11 = rope #1,
                                // 12 = rope #2,            13 = rope #3,
                                // 14 = snowman,            15 = snowball,
                                // 16 = bomb,               17 = Masi the beaver.
                                
                            int video = 3;
                            String pathGif = "";
                            String pathSound = "";
                            switch(video) {
                                case 0 ->  { 
                                    pathGif = "./src/hugohiihto/scylla_intro.gif";  // or ends like _s.gif (smaller)
                                    pathSound = "./src/hugohiihto/scylla_intro.aiff";
                                }
                                case 1 ->  {
                                    pathGif = "./src/hugohiihto/start_hoplaa.gif";
                                    pathSound = "./src/hugohiihto/start_hoplaa.aiff";
                                }
                                case 2 ->  {
                                    pathGif = "./src/hugohiihto/scylla_button_press.gif";
                                    pathSound = "./src/hugohiihto/scylla_button_press.aiff";
                                }
                                case 3 ->  {
                                    pathGif = "./src/hugohiihto/scylla0.gif";
                                    pathSound = "./src/hugohiihto/scylla0.aiff";
                                }
                                case 4 ->  {
                                    pathGif = "./src/hugohiihto/remember2forKey_intro.gif";
                                    pathSound = "./src/hugohiihto/remember2forKey_intro.aiff";
                                }
                                case 5 ->  {
                                    pathGif = "./src/hugohiihto/remember2forKey_win.gif";
                                    pathSound = "./src/hugohiihto/remember2forKey_win.aiff";
                                }
                                case 6 ->  {
                                    pathGif = "./src/hugohiihto/remember2forKey_fail.gif";
                                    pathSound = "./src/hugohiihto/remember2forKey_fail.aiff";
                                }
                                case 7 ->  {
                                    pathGif = "./src/hugohiihto/screentalk_finish_line.gif";
                                    pathSound = "./src/hugohiihto/screentalk_finish_line.aiff";
                                }
                                case 8 ->  {
                                    pathGif = "./src/hugohiihto/screentalk_heraa_pahvi.gif";
                                    pathSound = "./src/hugohiihto/screentalk_heraa_pahvi.aiff";
                                }
                                case 9 ->  {
                                    pathGif = "./src/hugohiihto/screentalk_viimeista_viedaan.gif";
                                    pathSound = "./src/hugohiihto/screentalk_viimeista_viedaan.aiff";
                                }
                                case 10 ->  {
                                    pathGif = "./src/hugohiihto/screentalk_game_over.gif";
                                    pathSound = "./src/hugohiihto/screentalk_game_over.aiff";
                                }
                                case 11 ->  {
                                    pathGif = "./src/hugohiihto/scylla1.gif";
                                    pathSound = "./src/hugohiihto/scylla1.aiff";
                                }
                                case 12 ->  {
                                    pathGif = "./src/hugohiihto/scylla2.gif";
                                    pathSound = "./src/hugohiihto/scylla2.aiff";
                                }
                                case 13 ->  {
                                    pathGif = "./src/hugohiihto/scylla3.gif";
                                    pathSound = "./src/hugohiihto/scylla3.aiff";
                                }
                                case 14 ->  {
                                    pathGif = "./src/hugohiihto/loselife_snowman.gif";
                                    pathSound = "./src/hugohiihto/loselife_snowman.aiff";
                                }
                                case 15 ->  {
                                    pathGif = "./src/hugohiihto/loselife_snowball.gif";
                                    pathSound = "./src/hugohiihto/loselife_snowball.aiff";
                                }
                                case 16 ->  {
                                    pathGif = "./src/hugohiihto/loselife_bomb.gif";
                                    pathSound = "./src/hugohiihto/loselife_bomb.aiff";
                                }
                                case 17 ->  {
                                    pathGif = "./src/hugohiihto/loselife_beaver.gif";
                                    pathSound = "./src/hugohiihto/loselife_beaver.aiff";
                                }
                            }
                            
                            this.setContentPane(new JLabel(new ImageIcon(pathGif)));
                            File f = new File(pathSound); // .aiff is a well-working sound format for the current video setup 
                            
                            URL mediaURL = null;
                            try {
                                mediaURL = f.toURL();
                            } catch (MalformedURLException ex) {
                                Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            
                            try {
                                //mediaPlayer = Manager.createRealizedPlayer(mediaURL);
                                mediaPlayer = Manager.createPlayer(mediaURL);


                            } catch (IOException | NoPlayerException  ex) {
                                Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            // https://convertio.co/mp4-mov/
                            // https://www.oracle.com/java/technologies/javase/jmf-211-formats.html
                            // https://convertio.co/mp4-mov/
                            // https://www.oracle.com/java/technologies/javase/jmf-211-formats.html
                            
                            mediaPlayer.start();
                            
                            
                        }
                        
                        
                        // Desktop.getDesktop().open(new File("./src/hugohiihto/scylla_intro.mp4")); 
                        // ^ The simplest way to just show a video, only 1 line of code. 
                        
                    }
            if(videoIsOn && enterPressCount >=3 ) {
                titleAndMusic = true;
                // stop the video and allow the game to begin...
                mediaPlayer.stop();
                // videoIsOn = false; // later
                // enterPressCount = 0;
                
                
                this.repaint();
                
                jPanelGAME.setVisible(true);
                JLabel l = null;
                JLabel l2 = null;
                Dimension dim = jLabelGAME.getPreferredSize();
                
                Image background1 = null;
                
                try {
                    background1 = ImageIO.read(new File("./src/hugohiihto/background1.png")).getScaledInstance(
                            dim.width/2, dim.height,  java.awt.Image.SCALE_SMOOTH);
                } catch (IOException ex) {
                    Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                this.setContentPane(l = new JLabel(new ImageIcon(background1)));
                this.setVisible(true);
                this.repaint();
                
                
                Graphics hugoFront00 = new Graphics() {
                    @Override
                    public Graphics create() {
                        return this;
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void translate(int x, int y) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public Color getColor() {
                        return Color.black;
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void setColor(Color c) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void setPaintMode() {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void setXORMode(Color c1) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public Font getFont() {
                        throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void setFont(Font font) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public FontMetrics getFontMetrics(Font f) {
                        throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public Rectangle getClipBounds() {
                        throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void clipRect(int x, int y, int width, int height) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void setClip(int x, int y, int width, int height) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public Shape getClip() {
                        throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void setClip(Shape clip) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void drawLine(int x1, int y1, int x2, int y2) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void fillRect(int x, int y, int width, int height) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void clearRect(int x, int y, int width, int height) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void drawOval(int x, int y, int width, int height) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void fillOval(int x, int y, int width, int height) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void drawString(String str, int x, int y) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
                        
                        return true;
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
                        throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
                        throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
                        throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
                        throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
                        throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }

                    @Override
                    public void dispose() {
                        //throw new UnsupportedOperationException("Not supported yet."); 
                        // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                    }
                };
                
                //hugoFront00.drawImage(imageHugoFront, 0, 0, this);
                
                clipHoplaa.start();
                
                FloatControl gainControl = 
                    (FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-10.0f); // volume setting for music, decreasing volume 
                clip2.start();
                
                
                
                if(!actual_game) {
                    actual_game = true;
                    HugoHiihto hugoHiihto = new HugoHiihto();
                    TimerTask timerTask = hugoHiihto.new GameLoop();
                    java.util.Timer gg = new java.util.Timer(true);
                    gg.scheduleAtFixedRate(timerTask, 0, 1800); // 2000 = 2.0 sec for instance 
                }
                
            }
               
                
                try {
                    paintComponent(g, "./src/hugohiihto/title_screen.png");
                } 
                catch (IOException ex) {
                    Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                /**
                if(enterPressCount >1) {
                    System.out.println("Stopping music");
                    titleAndMusic = false;
                    titlescreen = false; 
                    actual_game = true;
                        clip.stop();
                        clip.close();
                        clip.flush();
                    enterPressCount = 0;
                }
                
                /**
                if(enterPressCount >1) {
                    System.out.println("Stopping music");
                    titleAndMusic = false;
                    titlescreen = false; 
                    actual_game = true;
                        clip.stop();
                        clip.close();
                        clip.flush();
                    enterPressCount = 0;
                }
                **/
            }
            
        }
        
        if(KeyEvent.VK_LEFT == evt.getKeyCode()) {
            System.out.println("<-- pressed");
            
        }
        
        if(KeyEvent.VK_RIGHT == evt.getKeyCode()) {
            System.out.println("--> pressed");
            
        }
        
        if(KeyEvent.VK_1 == evt.getKeyCode()) {
            System.out.println("1 pressed");
            if(memory_puzzle) {
                
            }
            if(guessing_rope_game) {
                
            }
        }
        
        if(KeyEvent.VK_2 == evt.getKeyCode()) {
            System.out.println("2 pressed");
            if(memory_puzzle) {
                
            }
            if(guessing_rope_game) {
                
            }
        }
        
        if(KeyEvent.VK_3 == evt.getKeyCode()) {
            System.out.println("3 pressed");
            if(memory_puzzle) {
                
            }
            if(guessing_rope_game) {
                
            }
        }
        
        
    }//GEN-LAST:event_formKeyPressed

   /**
    * Key pressed.
    */
    private void jPanelGAMEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanelGAMEKeyPressed
        //
    }//GEN-LAST:event_jPanelGAMEKeyPressed

    /**
     * When testing a video, name this the "main" method and the other class "previous_main" for example. 
     * 
     * @param args the command line arguments
     */
    public static void previous_main(String args[]) {
        
        
        
        java.awt.EventQueue.invokeLater(() -> {
            new VideoTester_not_necessary().setVisible(true);
            
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelGAME;
    private javax.swing.JPanel jPanelGAME;
    // End of variables declaration//GEN-END:variables

    /**
     * Painting a component, graphics object input. 
     * @param g
     * @param filepath
     * @throws IOException 
     */
    protected void paintComponent(Graphics g, String filepath) throws IOException{
        
        
        
        if(!titleAndMusic) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(filepath).getImage()
                    .getScaledInstance(jPanelGAME.getWidth(), jPanelGAME.getHeight(), Image.SCALE_DEFAULT));
            jLabelGAME.setIcon(imageIcon);
            jLabelGAME.repaint();
            titleAndMusic = true;
        }

        
        
        if(titleAndMusic) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(filepath).getImage()
                    .getScaledInstance(jPanelGAME.getWidth(), jPanelGAME.getHeight(), Image.SCALE_DEFAULT));
            jLabelGAME.setIcon(imageIcon);
            jLabelGAME.repaint();
            titleAndMusic = true;
        }
        
    }
    
    
    /**
     * Draw panel
     */
    public class DrawPanel extends JPanel {     // image(s) might not appear 
        private BufferedImage image;
        public DrawPanel(String path) {
            try {
                image = ImageIO.read(new File(path));
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    /**
     * Painting a component, graphics object input. 
     * @param g
     */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
            //this.repaint();
        }
    }
}