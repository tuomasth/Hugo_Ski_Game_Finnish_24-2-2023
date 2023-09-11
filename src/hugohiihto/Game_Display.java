package hugohiihto;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player; // library JMF 2.1.1e used, https://en.wikipedia.org/wiki/Java_Media_Framework 
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

/** 
 * Hugo Ski Game by T. Hyvönen, 2023 // 1.0.0.2 quick edit on Sep 12, 2023 
 * 
 * The game window cannot be resized currently, press the Windows key then +Plus/-Minus to zoom in/out. 
 * Windows Magnifier/zooming instructions: 
 * https://www.androidpolice.com/how-to-zoom-in-out-on-windows-10-11/
 * Something like this should be possible on Linux, MAC etc. too but without Windows button... 
 * 
 * Thanks for trying out this game! It took months to complete. I do not own the Hugo franchise. 
 * Sharing Java codes and random files on the Internet should not be a crime - as far as I do not cause harm to people or their money. 
 * The purpose is to share the fun with people and keep the Hugo franchise alive. 
 * 
 * Java must be installed before playing the game: 
 * https://www.java.com/en/download/manual.jsp
 * 
 * 
 * "Work is not man's punishment. It is his reward and his strength and his pleasure." 
 * - George Sand, French novelist 
 * 
 * "Love is a serious mental disease."      (especially for Hugo franchise) 
 * - Plato, Greek philosopher
 * 
 * "We can easily forgive a child who is afraid of the dark; the real tragedy of life is when men are afraid of the light."
 * - Plato
 * 
 * https://www.brainyquote.com 
 * 
 * ----
 * 
 * Please support the original and official Hugo releases! They have been the inspiration for this game. 
 * Consider this game to be treated the same way as "Mega Man Unlimited". 
 * https://megamanfanon.fandom.com/wiki/Mega_Man_Unlimited 
 * 
 * Or if you're mean, treat this the same way as Metroid AM2R. 
 * https://en.wikipedia.org/wiki/AM2R 
 * 
 * Or Commodore64 Super Mario Bros. that Nintendo also did not treat well. 
 * https://archive.org/details/Super_Mario_Bros_C64_Zeropaige 
 * 
 * 
 * Thanks to this discussion for huge progress in game development when getting started:
 * https://stackoverflow.com/questions/12082660/background-image-for-simple-game
 * 
 * "The beginning is the most important part of the work."
 * - also from Plato
 * 
 * ----
 * 
 * Some relevant Hugo creators not involved in this project:
 * 
 * https://screentroll.fandom.com/wiki/Hugo_(PlayStation) 
 *  Producers: Ivan Sølvason, Lars Rikart Jensen, Troels Gram, Jens C. Ringdal
 *  Design: Niels Krogh Mortensen, Troels Gram, Lars Rikart Jensen
 *  Programmers: Peter Marino, Poul-Jesper Olsen, Ole Thomas Jensen, Mario Gomes, Claes Hougaard, 
 *    Michael Barklund, Erik Schack Andersen, John Dideriksen, Troels Gram, Jørgen Lortentsen
 *  Graphics: Chadi Freigeh, Claus Friese, Thomas Skellund, Mark Gregory, Jakob Steffensen, Peter Eide Paulsen, 
 *    Jørgen Trolle Ørberg, John Madsen, Niels Krogh Mortensen, Lars Krogh Mortensen, René Bidstrup, 
 *    Anders Morgenthaler, Torben Bakager Larsen, Jesper Laugesen 
 *  Music and sound: David A. Filskov, Christian Steen Jensen
 * 
 * https://screentroll.fandom.com/wiki/Hugo_2_(PlayStation) 
 *  Producer: Ivan Sølvason, Lars Rikart Jensen. Piet N. Kargaard
 *  Design: Mario Gomes, Peter Eide Paulsen, Poul Engelbrech Madsen, John Dideriksen
 *  Programming: Mario Gomes, Jesper Olsen, Anders Emil Hansen, Ole T. Jensen
 *  Graphics: Claus Friese, Chadi Freigeh, Peter Eide Paulsen, Piet N. Kargaard, John Madsen, Jørgen Trolle Ørberg
 *  Music and sound: David A. Filskov, Christian Steen Jensen, Klaus Mulvad Nielsen, Asbjørn Andersen
 *  Other: Niels Krogh Mortensen, Lars Krogh Mortensen, René Bidstrup, Anders Morgenthaler, Torben Bakager Larsen, 
 *    Jesper Laugesen, Tom Westerman, Thomas Skellund, Espen Toft Jacobsen, Laust Palbo Nielsen
 * ----
 * 
 * 
 * The game display class. The other class is HugoHiihto.java. 
 * The main method is here, check the end of this file. 
 * 
 * Tested with Microsoft Windows 11
 * Java developed by Oracle / Sun Microsystems. 
 * Created with Apache NetBeans 15 
 * Official release date: 24/2/2023 - v1.0 Finnish version available worldwide 
 * @author Tuomas Hyvönen 
 * @version 1.0 
 */
public final class Game_Display extends JPanel{
    final static String VERSION = "1.0.0.2"; 
    final static int GAMESPEED = 1315;      // in milliseconds, a good speed is 1315 ms 
                                            // does not update graphics! 
    int game_state = 0;      // 0 = Pre title no music,  1 = title screen and credits screen after beating the game,
                              // 2 = showing a video,     3 = actual ski game,
                                // 4 = remember two items,  5 = game over or beat the game + show score.
                                  // use state 6 or higher when moving from a video to another video 
    static int nextState = 0; 
    boolean useMP4 = false; // if false, use gifs that might flicker if made incorrectly 
    // should be false though because mp4s will open a new window currently, Windows Media Player for instance. 
                // Original Hugo graphics and sounds have been edited, mostly to create new content. 
    static int video = 0;       
    Image videoimg = null; // .gif expected + .aiff for sound 
                                // 0 = Scylla intro,        1 = Hugo's first words hoplaa nyt hommiin,
                                // 2 = Scylla button,       3 = three ropes intro,
                                // 4 = Hugo asks for two,   5 = two chosen correctly,
                                // 6 = made a wrong choice, 7 = (knock) Hugo finished the skiing,
                                // 8 = knock wake up pahvi, 9 = (knock) now the last troll going,
                                // 10 = (knock) game over,  11 = rope #1,
                                // 12 = rope #2,            13 = rope #3,
                                // 14 = snowman,            15 = snowball,
                                // 16 = bomb,               17 = Masi the beaver. 
    static Player mediaPlayer = null;
    static Clip clip0 = null; // music or sound
    static Clip clip1 = null; // music or sound
    static Clip clip2 = null; // music or sound
    static Clip clip3 = null; // music or sound
    static Clip clip4 = null; // music or sound
    static Clip clipH = null; // music or sound, skiing
        // twistedwave.com sound edit tool is a helpful example tool when editing .wav or .aiff files 
    static Clip clipMoney = null;
    static File fileMoney = new File("./src/hugohiihto/money.wav");
    static Clip clipScore = null;
    static File fileScore = new File("./src/hugohiihto/points_score.wav");
    
    static File fileGameMusic0 = new File("./src/hugohiihto/music-ps1hugo2menu.wav");
    static File fileGameMusic1 = new File("./src/hugohiihto/music-djhugopopcorn.wav");
    static File fileGameMusic2 = new File("./src/hugohiihto/music_from_classic_skateboard.wav"); // or straight to clip use 
    static File fileGameMusic3 = new File("./src/hugohiihto/music_credits.wav");
    // some variables like "int music" might not be used but there are still these musics: 
    static int music = 0;   //  Hugo 2 PlayStation 1 title/menu 
                            //  Popcorn Slotmachine featuring Gemini 7 (1993 DJ Hugo charity CD tr 8) 
                            //  Hugo classic skateboard music Hugo 1 PlayStation 1 
                            //  DJ Hugo: Kata & Hugo – Hugo singlepeikko mix edit (1993 DJ Hugo charity CD tr 1) 
    // As the programmer I own nothing. The original creators listed above do not lose money when these tracks are included in this game. 
    // Do not upload valuable video game and TV show musics to YouTube if they have strict copyrights! 
    
    /*   * E- empty (even though E might not be needed to be read, it is meaningful to show positions) 
         * M- money
         * 8- snowman
         * o- snowball (small o)
         * Q- bomb
         * B- the beaver
         * 1- thing to remember #1 (6 possible)
         * 2- thing to remember #2 (6 possible (actually 5 because never same again allowed))
         * S- Scylla button press with short horror music
         * F- goal, just end the skiing session
         * 
         * A-  black asterisk* as +correct
         * a-  black asterisk* as incorrect-
         * B-  yellow bell as +correct
         * b-  yellow bell as incorrect-
         * C-  red clock as +correct
         * c-  red clock as incorrect-
         * D-  red diamond as +correct
         * d-  red diamond as incorrect-
         * H-  black hash(tag#) as +correct
         * h-  black hash(tag#) as incorrect-
         * S-  yellow star as +correct
         * s-  yellow star as incorrect-
    */
    
    // ezgif.com  &  redketchup.io/gif-resizer  were useful services for video gif polishing 
    // ezgif.com's might flicker with black frames sometimes 
    static Robot robot; // automatic game control might be planned later for showing a demo video of the gameplay 
                        // not implemented yet and might never be necessary though 
    
    //TimerTask timerTask;
    static boolean pulled_rope_1 = false; // good ending        1   
    static boolean pulled_rope_2 = false; // bad ending         2   
    static boolean pulled_rope_3 = false; // the best ending    3   
    Image r1;
    Image r2;
    Image r3;
    
    static boolean lastPressWasLeft = false; // if not, then Right 
    static boolean gamePaused = false;
    static boolean pausedWithEnter = false; // 2 types of pausing: interruption before a video and pause on purpose by the player 
    static String thingsToRemember = "dsHAcb";   // will be random later , end 0s and 1s are just extra if present 
    static boolean currentlyAllCorrect = true;
    static boolean secondPhase = false; // these are in guessing 123 123 for the skull cave key 
    static boolean allCorrectInTheEnd = false;      // (Scylla has weird locks and why does she even give the 2 clues to Hugo?) 
    
    static JFrame f = new JFrame();
    static Dimension d = new Dimension(630, 500);
    int maxW = d.width - 220;
    
    int w_width = (int) d.getWidth()/7; // hugo skiing anim 
    int w_height = (int) d.getHeight()/3;
    int e_width = (int) d.getWidth()/7;
    int e_height = (int) d.getHeight()/3;
    
    int x, y;
    static int currentGrid = 0; // or line, should be 0, 1, 2 or 3, nothing else 
    // Hugo will always go forward and <- & -> change the line on the current phase 
    // bugs might occur if the variable is set wrong 
    
    Image theVeryFirst; // 30th anniversary in Finland 
    Image titleScreen;  // instructions for how to play 
    Image scoreBGR;
    Image creditsScreen;
    
    Image sprite_R; // Hugo showing up on 4 lines/grids/tracks 
    Image sprite_L;
    Image sprite_R2;
    Image sprite_L2;
    Image bg;
    
    int cave_x, cave_y;
    Image bgCave;
    
    Image cloud;
    int cloud_x_position;
    int cloud_y_position;
    
    Image possibleTree1; // may be changed to something else than trees - if wanted to edit so 
    int possibleTree1_x_position;
    int possibleTree1_y_position;
    Image possibleTree2;
    int possibleTree2_x_position;
    int possibleTree2_y_position;
    Image possibleTree3;
    int possibleTree3_x_position;
    int possibleTree3_y_position;
    Image possibleTree4;
    int possibleTree4_x_position;
    int possibleTree4_y_position;
    Image possibleTree5;
    int possibleTree5_x_position;
    int possibleTree5_y_position;
    Image possibleTree6;
    int possibleTree6_x_position;
    int possibleTree6_y_position;
    int possibleTree1iconw;
    int possibleTree1iconh;
    int possibleTree2iconw;
    int possibleTree2iconh;
    int possibleTree3iconw;
    int possibleTree3iconh;
    int possibleTree4iconw;
    int possibleTree4iconh;
    int possibleTree5iconw;
    int possibleTree5iconh;
    int possibleTree6iconw;
    int possibleTree6iconh;
    ImageIcon possibleTree1icon;
    ImageIcon possibleTree2icon;
    ImageIcon possibleTree3icon;
    ImageIcon possibleTree4icon;
    ImageIcon possibleTree5icon;
    ImageIcon possibleTree6icon;
    
    Image scorebar; // blue score bar 
    int scorebar_x_position;
    int scorebar_y_position;
    
    Image hugolife1;
    int hugolife1_x_position;
    int hugolife1_y_position;
    Image hugolife2;
    int hugolife2_x_position;
    int hugolife2_y_position;
    Image hugolife3;
    int hugolife3_x_position;
    int hugolife3_y_position;
    Image digitFromLeft1image;
    int digitFromLeft1_x_position;
    int digitFromLeft1_y_position;
    Image digitFromLeft2image;
    int digitFromLeft2_x_position;
    int digitFromLeft2_y_position;
    Image digitFromLeft3image;
    int digitFromLeft3_x_position;
    int digitFromLeft3_y_position;
    Image digitFromLeft4image;
    int digitFromLeft4_x_position;
    int digitFromLeft4_y_position;
    Image digitFromLeft5image;
    int digitFromLeft5_x_position;
    int digitFromLeft5_y_position;
    Image digitFromLeft6image;
    int digitFromLeft6_x_position;
    int digitFromLeft6_y_position;
    Image pause;
    int pause_x_position;
    int pause_y_position;
    
    Image currentHazardOrMoney1_image;
    static int currentHazardOrMoney1_x_position;
    static int currentHazardOrMoney1_y_position;
    Image currentHazardOrMoney2_image;
    static int currentHazardOrMoney2_x_position;
    static int currentHazardOrMoney2_y_position;
    Image currentHazardOrMoney3_image;
    static int currentHazardOrMoney3_x_position;
    static int currentHazardOrMoney3_y_position;
    Image currentHazardOrMoney4_image;
    static int currentHazardOrMoney4_x_position;
    static int currentHazardOrMoney4_y_position;
    static String currentHazardOrMoney1 = "E";
    static String currentHazardOrMoney2 = "E";
    static String currentHazardOrMoney3 = "E";
    static String currentHazardOrMoney4 = "E";
    static int currentHazardOrMoney1w;
    static int currentHazardOrMoney1h;
    static int currentHazardOrMoney2w;
    static int currentHazardOrMoney2h;
    static int currentHazardOrMoney3w;
    static int currentHazardOrMoney3h;
    static int currentHazardOrMoney4w;
    static int currentHazardOrMoney4h;
    
    int position1 = 10;
    int position2 = 130;
    int position3 = 250;
    int heightLevel1 = 5;
    int heightLevel2 = 150;
    Image asterisk;
    int asterisk_x_position;
    int asterisk_y_position;
    Image bell;
    int bell_x_position;
    int bell_y_position;
    Image clock;
    int clock_x_position;
    int clock_y_position;
    Image diamond;
    int diamond_x_position;
    int diamond_y_position;
    Image hashtag;
    int hashtag_x_position;
    int hashtag_y_position;
    Image star;
    int star_x_position;
    int star_y_position;
    
    Image u1b;
    int u1b_x_position;
    int u1b_y_position;
    Image u1w;
    Image u2b;
    int u2b_x_position;
    int u2b_y_position;
    Image u2w;
    Image u3b;
    int u3b_x_position;
    int u3b_y_position;
    Image u3w;
    Image d1b;
    int d1b_x_position;
    int d1b_y_position;
    Image d1w;
    Image d2b;
    int d2b_x_position;
    int d2b_y_position;
    Image d2w;
    Image d3b;
    int d3b_x_position;
    int d3b_y_position;
    Image d3w;
    
    // for score digit values: 
    static int ones = 0;
    static int tens = 0;
    static int hundreds = 0;
    static int thousands = 0;
    static int tenThousands = 0;
    static int hundredThousands = 0;
    
    static int number_of_lives = 3; // we expect that 3 is the max amount and no 1ups are given 
        // 0th life does not count (Super Mario Bros. 1 for NES is a good example of the same "no 0th style") 
    Clip clipChangeGrid = null;
    File fileChangeGrid = new File("./src/hugohiihto/ski_track_change.wav");
    
    Clip clipCorrect = null;
    File fileCorrect = new File("./src/hugohiihto/correct_selection.wav");
    
    /**
     * Set lives, max is 3 because decided so and no "1-ups". 
     * @param new_amount 
     */
    public static void setLives(int new_amount) { // this is not how setters should be done but this works 
        if(new_amount < 4 && new_amount > -1) {
            number_of_lives = new_amount;
        }
    }
    /**
     * Set ones.
     * @param new_amount 
     */
    public static void setOnes(int new_amount) {
        ones = new_amount;
    }
    /**
     * Set tens.
     * @param new_amount 
     */
    public static void setTens(int new_amount) {
        tens = new_amount;
    }
    /**
     * Set hundreds.
     * @param new_amount 
     */
    public static void setHundreds(int new_amount) {
        hundreds = new_amount;
    }
    /**
     * Set thousands.
     * @param new_amount 
     */
    public static void setThousands(int new_amount) {
        thousands = new_amount;
    }
    /**
     * Set 10 000s.
     * @param new_amount 
     */
    public static void setTenThousands(int new_amount) {
        tenThousands = new_amount;
    }
    /**
     * Set 100 000s.
     * @param new_amount 
     */
    public static void setHundredThousands(int new_amount) {
        hundredThousands = new_amount;
    }
    /**
     * Set hazard 1.
     * @param value 
     */
    public static void setcurrentHazardOrMoney1(String value) {
        currentHazardOrMoney1 = value;
    }
    /**
     * Set hazard 2.
     * @param value 
     */
    public static void setcurrentHazardOrMoney2(String value) {
        currentHazardOrMoney2 = value;
    }
    /**
     * Set hazard 3.
     * @param value 
     */
    public static void setcurrentHazardOrMoney3(String value) {
        currentHazardOrMoney3 = value;
    }
    /**
     * Set hazard 4.
     * @param value 
     */
    public static void setcurrentHazardOrMoney4(String value) {
        currentHazardOrMoney4 = value;
    }
    
    /**
     * Key listeners when pressing buttons. Please call only once! 
     * Else, input bugs will occur with multiple presses. 
     */
    public class AL extends KeyAdapter {
        
        /**
         * Key pressed event when player gives input. 
         * @param e 
         */
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if(keyCode == KeyEvent.VK_ESCAPE) {
                
                System.gc();    // run garbage collector 
                System.exit(0); // and exit if ESC pressed 
            }
            
            if(keyCode == KeyEvent.VK_ENTER) {
                
                if(video != 3) {
                    if(videoimg != null) {  // so videos will always start at the beginning 
                        videoimg.flush();
                        videoimg = null;
                    }
                }
                
                // Mutings 
                if(clip0 != null) {
                    if(clip0.isRunning()) {
                        clip0.stop();
                    }
                }
                /*
                if(clip1 != null) {
                    if(clip1.isRunning()) {
                        clip1.stop();
                    }
                }
                */ 
                if(clip2 != null) {
                    if(clip2.isRunning()) {
                        clip2.stop();
                    }
                }
                if(clip3 != null) {
                    if(clip3.isRunning()) {
                        clip3.stop();
                    }
                }
                if(mediaPlayer != null && video != 2 && video != 3) {
                    mediaPlayer.stop();
                }
            }
            
            
            if(keyCode == KeyEvent.VK_F13) { // idea: possible state change with F13 key, by a "robot user" 
                if(HugoHiihto.currentStateAtTheLevel >= 81 && Game_Display.gamePaused) {
                    //System.out.println("BOT -- Entering the 'remember 2' part --");
                    //Game_Display.nextState = 4;
                }
            }
            
            
            if((double)game_state < 0.1) {
                if (keyCode == KeyEvent.VK_ENTER) {
                    
                    construct(game_state);
                    
                    if(clip1 != null) {
                        if(clip1.isRunning()) {
                            clip1.stop();//popcorn 
                        }
                    }
                    if(clip4 != null) {
                        if(clip4.isRunning()) {
                            clip4.stop();//skateboard 
                        }
                    }
                    
                    video = 0;
                    nextState = 6;
                }
            }
            else if((double)game_state > 0.9 && (double)game_state < 1.1) {
                if (keyCode == KeyEvent.VK_ENTER) {
                    
                    videoimg = new ImageIcon("./src/hugohiihto/scylla_intro_s.gif").getImage();
                    videoimg = new ImageIcon("./src/hugohiihto/start_hoplaa_s.gif").getImage();
                    videoimg.setAccelerationPriority((float)1.0); // from 0-> lowest to 1-> highest 
                    
                    
                    if(HugoHiihto.currentStateAtTheLevel >= 81 && (pulled_rope_3 || pulled_rope_1)) {
                        nextState = 0;
                        HugoHiihto.currentStateAtTheLevel = -5;
                    }
                    else {
                        if(HugoHiihto.gameOver) {
                            if(HugoHiihto.timerTask != null) {
                                HugoHiihto.timerTask.cancel();
                            }
                        }
                        HugoHiihto.gameReset(GAMESPEED); // creates a new game 
                        video = 1;
                        nextState = 6;
                        construct(game_state);
                    }
                }
            }
            else if((double)game_state > 1.9 && (double)game_state < 2.1) {
                        // 0 = Scylla intro,        1 = Hugo's first words hoplaa nyt hommiin, 
                        // 2 = Scylla button,       3 = three ropes intro, 
                        // 4 = Hugo asks for two,   5 = two chosen correctly, 
                        // 6 = made a wrong choice, 7 = (knock) Hugo finished the skiing, 
                        // 8 = knock wake up pahvi, 9 = (knock) now the last troll going, 
                        // 10 = (knock) game over,  11 = rope #1, 
                        // 12 = rope #2,            13 = rope #3, 
                        // 14 = snowman,            15 = snowball, 
                        // 16 = bomb,               17 = Masi the beaver. 
                        
                if(video == 0) {
                    if(keyCode == KeyEvent.VK_ENTER) {
                        
                        if(HugoHiihto.currentStateAtTheLevel >= 81 && HugoHiihto.gameOver == false) {
                            try {
                                clip3 = AudioSystem.getClip();
                            } 
                            catch (LineUnavailableException ex) {
                                Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                clip3.open(AudioSystem.getAudioInputStream(fileGameMusic3));
                            } 
                            catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                                Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            FloatControl gainControl = 
                            (FloatControl) clip3.getControl(FloatControl.Type.MASTER_GAIN);
                            gainControl.setValue(-0.0f); // volume setting for music, decreasing the volume if wanted 
                            clip3.start();
                        }
                        else {
                            try {
                                clip0 = AudioSystem.getClip();
                            } 
                            catch (LineUnavailableException ex) {
                                Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                clip0.open(AudioSystem.getAudioInputStream(fileGameMusic0));
                            } 
                            catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                                Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            FloatControl gainControl = 
                            (FloatControl) clip0.getControl(FloatControl.Type.MASTER_GAIN);
                            gainControl.setValue(-0.0f); // volume setting for music, decreasing the volume if wanted 
                            clip0.start();
                        }
                        
                        gamePaused = true;
                        nextState = 1;
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 1) {
                    if(keyCode == KeyEvent.VK_ENTER) {
                        nextState = 3; // to the actual game 
                        gamePaused = false;
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 2) {
                    if(keyCode == KeyEvent.VK_ENTER) {
                        nextState = 3;
                        gamePaused = false;
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 3) {
                    if(clip1 != null) {
                        if(clip1.isRunning()) {
                            clip1.stop();
                        }
                    }
                    
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                    if(keyCode == KeyEvent.VK_1 || keyCode == KeyEvent.VK_NUMPAD1) {
                        if(mediaPlayer != null) {
                            mediaPlayer.stop();
                        }
                        pulled_rope_1 = true;
                        pulled_rope_2 = false;
                        pulled_rope_3 = false;
                        System.out.println("1 chosen!");
                        
                        // 1004 points like in the good old times (at least in some classic games) 
                        HugoHiihto.increaseScoreThousands(thousands);
                        HugoHiihto.increaseScoreOnes(ones);
                        HugoHiihto.increaseScoreOnes(ones);
                        HugoHiihto.increaseScoreOnes(ones);
                        HugoHiihto.increaseScoreOnes(ones);
                        
                        video = 11;
                        nextState = 6; // use state 6 or higher when moving from a video to another video 
                    }
                    if(keyCode == KeyEvent.VK_2 || keyCode == KeyEvent.VK_NUMPAD2) {
                        if(mediaPlayer != null) {
                            mediaPlayer.stop();
                        }
                        pulled_rope_2 = true;
                        pulled_rope_1 = false;
                        pulled_rope_3 = false;
                        System.out.println("2 chosen!");
                        HugoHiihto.currentStateAtTheLevel = -5; 
                        video = 12;
                        nextState = 6;
                    }
                    if(keyCode == KeyEvent.VK_3 || keyCode == KeyEvent.VK_NUMPAD3) {
                        if(mediaPlayer != null) {
                            mediaPlayer.stop();
                        }
                        pulled_rope_3 = true;
                        pulled_rope_1 = false;
                        pulled_rope_2 = false;
                        System.out.println("3 chosen!");
                        
                        // 2026 points = the best ending score, more than just a money bag 
                        HugoHiihto.increaseScoreThousands(thousands);
                        HugoHiihto.increaseScoreThousands(thousands);
                        HugoHiihto.increaseScoreTens(tens);
                        HugoHiihto.increaseScoreTens(tens);
                        HugoHiihto.increaseScoreOnes(ones);
                        HugoHiihto.increaseScoreOnes(ones);
                        HugoHiihto.increaseScoreOnes(ones);
                        HugoHiihto.increaseScoreOnes(ones);
                        HugoHiihto.increaseScoreOnes(ones);
                        HugoHiihto.increaseScoreOnes(ones);
                        
                        video = 13;
                        nextState = 6;
                    }
                }
                if(video == 4) {    // hugo asks 2 
                    if(keyCode == KeyEvent.VK_ENTER) {
                        nextState = 4;
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 5) {    // 2 right 
                    if(keyCode == KeyEvent.VK_ENTER) {
                        if(clip1 != null) {
                            if(clip1.isRunning()) {
                                clip1.stop();//popcorn stop 
                            }
                        }
                        if(clip4 != null) {
                            if(clip4.isRunning()) {
                                clip4.stop();//skateboard stop 
                            }
                        }
                        
                        video = 3;
                        nextState = 6;
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 6) {    // wrong 
                    if(keyCode == KeyEvent.VK_ENTER) {
                        HugoHiihto.gameOver = true;
                        nextState = 5;
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 7) {
                    if(keyCode == KeyEvent.VK_ENTER) {
                        Game_Display.video = 4;
                        Game_Display.nextState = 6;
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 8) {
                    if(keyCode == KeyEvent.VK_ENTER) {
                        nextState = 3; // to the actual game 
                        gamePaused = false;
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 9) {
                    if(keyCode == KeyEvent.VK_ENTER) {
                        nextState = 3; // to the actual game 
                        gamePaused = false;
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 10) {
                    if(keyCode == KeyEvent.VK_ENTER) {
                        // cancel all timertasks!
                        HugoHiihto.gameOver = true;
                        //HugoHiihto.currentStateAtTheLevel = 81;
                        nextState = 5;
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 11) {
                    if(keyCode == KeyEvent.VK_ENTER) {
                        nextState = 5;
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 12) {
                    if(keyCode == KeyEvent.VK_ENTER) {
                        nextState = 5;
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 13) {
                    if(keyCode == KeyEvent.VK_ENTER) {
                        nextState = 5;
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 14) {
                    currentGrid = 0;
                    currentHazardOrMoney1_y_position += 800;
                    currentHazardOrMoney2_y_position += 800;
                    currentHazardOrMoney3_y_position += 800;
                    currentHazardOrMoney4_y_position += 800;
                    if(keyCode == KeyEvent.VK_ENTER) {
                        if(number_of_lives >= 2) {
                            video = 8;
                            nextState = 6;
                        }
                        else {
                            if(number_of_lives >= 1) {
                                video = 9;
                                nextState = 6;
                            }
                            else {
                                video = 10;
                                nextState = 6;
                            }
                        }
                        
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 15) {
                    currentGrid = 0;
                    currentHazardOrMoney1_y_position += 800;
                    currentHazardOrMoney2_y_position += 800;
                    currentHazardOrMoney3_y_position += 800;
                    currentHazardOrMoney4_y_position += 800;
                    if(keyCode == KeyEvent.VK_ENTER) {
                        if(number_of_lives >= 2) {
                            video = 8;
                            nextState = 6;
                        }
                        else {
                            if(number_of_lives >= 1) {
                                video = 9;
                                nextState = 6;
                            }
                            else {
                                video = 10;
                                nextState = 6;
                            }
                        }
                        
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 16) {
                    currentGrid = 0;
                    currentHazardOrMoney1_y_position += 800;
                    currentHazardOrMoney2_y_position += 800;
                    currentHazardOrMoney3_y_position += 800;
                    currentHazardOrMoney4_y_position += 800;
                    if(keyCode == KeyEvent.VK_ENTER) {
                        if(number_of_lives >= 2) {
                            video = 8;
                            nextState = 6;
                        }
                        else {
                            if(number_of_lives >= 1) {
                                video = 9;
                                nextState = 6;
                            }
                            else {
                                video = 10;
                                nextState = 6;
                            }
                        }
                        
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
                if(video == 17) {
                    currentGrid = 0;
                    currentHazardOrMoney1_y_position += 800;
                    currentHazardOrMoney2_y_position += 800;
                    currentHazardOrMoney3_y_position += 800;
                    currentHazardOrMoney4_y_position += 800;
                    if(keyCode == KeyEvent.VK_ENTER) {
                        if(number_of_lives >= 2) {
                            video = 8;
                            nextState = 6;
                        }
                        else {
                            if(number_of_lives >= 1) {
                                video = 9;
                                nextState = 6;
                            }
                            else {
                                video = 10;
                                nextState = 6;
                            }
                        }
                        
                    }
                    if(keyCode == KeyEvent.VK_ESCAPE) {
                        System.gc();
                        System.exit(0);
                    }
                }
            }
            else if((double)game_state > 2.9 && (double)game_state < 3.1) {
                
                maxW = d.width - 220;
                
                if (keyCode == KeyEvent.VK_LEFT) {
                    if(!gamePaused) {
                        lastPressWasLeft = true;

                        if (x <= -25) {
                            //x = -25;
                        }
                        else {
                            if(currentGrid >= 1){
                                currentGrid--;
                                //x -= (maxW/3);
                            }
                        }

                    }
                }
                if (keyCode == KeyEvent.VK_RIGHT) {
                    if(!gamePaused) {
                        lastPressWasLeft = false;

                        
                        if (x >= maxW) {
                            //x = maxW;
                        }
                        else {
                            if(currentGrid <= 2){
                                currentGrid++;
                                //x += (maxW/3);
                            }
                        }

                    }
                }

                
                if(keyCode == KeyEvent.VK_ENTER) {
                    pausedWithEnter = true;
                    if(!gamePaused) {
                        gamePaused = true;
                    }
                    else {
                        pausedWithEnter = false;
                        gamePaused = false;
                    }
                }
            }
            else if((double)game_state > 3.9 && (double)game_state < 4.1) {
                //keyCode = e.getKeyCode();
                // currentlyAllCorrect = true;
                if(keyCode == KeyEvent.VK_NUMPAD1 || keyCode == KeyEvent.VK_1) { // 1 
                    if(!secondPhase) {
                            if( thingsToRemember.charAt(0) == 'A' || // if caps, then correct 
                                thingsToRemember.charAt(0) == 'B' || 
                                thingsToRemember.charAt(0) == 'C' || 
                                thingsToRemember.charAt(0) == 'D' || 
                                thingsToRemember.charAt(0) == 'H' || 
                                thingsToRemember.charAt(0) == 'S') {
                                    //currentlyAllCorrect = true;
                            }
                            else {
                                currentlyAllCorrect = false;
                            }
                        secondPhase = true;
                    }
                    else {
                            if( thingsToRemember.charAt(3) == 'A' || // if caps, then correct 
                                thingsToRemember.charAt(3) == 'B' || 
                                thingsToRemember.charAt(3) == 'C' || 
                                thingsToRemember.charAt(3) == 'D' || 
                                thingsToRemember.charAt(3) == 'H' || 
                                thingsToRemember.charAt(3) == 'S') {
                                    //currentlyAllCorrect = true;
                            }
                            else {
                                currentlyAllCorrect = false;
                            }
                        //secondPhase = false;
                        if(currentlyAllCorrect) {
                            allCorrectInTheEnd = true;
                            System.out.println("Both correct!");
                        }
                    }
                }
                if(keyCode == KeyEvent.VK_NUMPAD2 || keyCode == KeyEvent.VK_2) { // 2 
                    if(!secondPhase) {
                            if( thingsToRemember.charAt(1) == 'A' || // if caps, then correct 
                                thingsToRemember.charAt(1) == 'B' || 
                                thingsToRemember.charAt(1) == 'C' || 
                                thingsToRemember.charAt(1) == 'D' || 
                                thingsToRemember.charAt(1) == 'H' || 
                                thingsToRemember.charAt(1) == 'S') {
                                    //currentlyAllCorrect = true;
                            }
                            else {
                                currentlyAllCorrect = false;
                            }
                        secondPhase = true;
                    }
                    else {
                            if( thingsToRemember.charAt(4) == 'A' || // if caps, then correct 
                                thingsToRemember.charAt(4) == 'B' || 
                                thingsToRemember.charAt(4) == 'C' || 
                                thingsToRemember.charAt(4) == 'D' || 
                                thingsToRemember.charAt(4) == 'H' || 
                                thingsToRemember.charAt(4) == 'S') {
                                    //currentlyAllCorrect = true;
                            }
                            else {
                                currentlyAllCorrect = false;
                            }
                        //secondPhase = false;
                        if(currentlyAllCorrect) {
                            allCorrectInTheEnd = true;
                            System.out.println("Both correct!");
                        }
                    }
                }
                if(keyCode == KeyEvent.VK_NUMPAD3 || keyCode == KeyEvent.VK_3) { // 3 
                    if(!secondPhase) {
                            if( thingsToRemember.charAt(2) == 'A' || // if caps, then correct 
                                thingsToRemember.charAt(2) == 'B' || 
                                thingsToRemember.charAt(2) == 'C' || 
                                thingsToRemember.charAt(2) == 'D' || 
                                thingsToRemember.charAt(2) == 'H' || 
                                thingsToRemember.charAt(2) == 'S') {
                                    //currentlyAllCorrect = true;
                            }
                            else {
                                currentlyAllCorrect = false;
                            }
                        secondPhase = true;
                    }
                    else {
                            if( thingsToRemember.charAt(5) == 'A' || // if caps, then correct 
                                thingsToRemember.charAt(5) == 'B' || 
                                thingsToRemember.charAt(5) == 'C' || 
                                thingsToRemember.charAt(5) == 'D' || 
                                thingsToRemember.charAt(5) == 'H' || 
                                thingsToRemember.charAt(5) == 'S') {
                                    //currentlyAllCorrect = true;
                            }
                            else {
                                currentlyAllCorrect = false;
                            }
                        //secondPhase = false;
                        if(currentlyAllCorrect) {
                            allCorrectInTheEnd = true;
                            System.out.println("Both correct!");
                        }
                    }
                }
                
                    if ((currentlyAllCorrect && (
                            keyCode == KeyEvent.VK_1 || 
                            keyCode == KeyEvent.VK_2 || 
                            keyCode == KeyEvent.VK_3 || 
                            keyCode == KeyEvent.VK_NUMPAD1 || 
                            keyCode == KeyEvent.VK_NUMPAD2 || 
                            keyCode == KeyEvent.VK_NUMPAD3))) {
                        try {
                            clipCorrect = AudioSystem.getClip();
                        } 
                        catch (LineUnavailableException ex) {
                            Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            clipCorrect.open(AudioSystem.getAudioInputStream(fileCorrect));
                        } 
                        catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                            Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            FloatControl gainControl = 
                            (FloatControl) clipCorrect.getControl(FloatControl.Type.MASTER_GAIN);
                            gainControl.setValue(-0.0f); // volume setting if wanted 
                            clipCorrect.start();
                    }
                if(!currentlyAllCorrect) {
                    if(!allCorrectInTheEnd) {
                        System.out.println("Wrong guess, not proceeding to ropes!");
                        
                        nextState = 5;
                    }
                }
                if(allCorrectInTheEnd) {
                    System.out.println("Proceeding to ropes!");
                    video = 3;
                    nextState = 2;
                }
            }
            else if((double)game_state > 4.9 && (double)game_state < 5.1) {
                if(keyCode == KeyEvent.VK_ENTER) {
                    if(pulled_rope_1 || pulled_rope_3) {
                        HugoHiihto.currentStateAtTheLevel = 81;
                        nextState = 1;
                        
                        if(HugoHiihto.currentStateAtTheLevel >= 81) {
                            try {
                                clip3 = AudioSystem.getClip();
                            } 
                            catch (LineUnavailableException ex) {
                                Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                clip3.open(AudioSystem.getAudioInputStream(fileGameMusic3));
                            } 
                            catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                                Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            FloatControl gainControl = 
                            (FloatControl) clip3.getControl(FloatControl.Type.MASTER_GAIN);
                            gainControl.setValue(-0.0f); // volume setting for music, decreasing the volume if wanted 
                            clip3.start();
                        }
                        else {
                            try {
                                clip0 = AudioSystem.getClip();
                            } 
                            catch (LineUnavailableException ex) {
                                Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                clip0.open(AudioSystem.getAudioInputStream(fileGameMusic0));
                            } 
                            catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                                Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            FloatControl gainControl = 
                            (FloatControl) clip0.getControl(FloatControl.Type.MASTER_GAIN);
                            gainControl.setValue(-0.0f); // volume setting for music, decreasing the volume if wanted 
                            clip0.start();
                        }
                    }
                    else {
                        nextState = 0;
                    }
                }
            }
            else if((double)game_state >= 5.1) {
                nextState = 2;
                System.out.println(" --- keyPressed --- to state " + nextState);
            }
        }
        
        /**
         * When releasing the left/right button in state 3. Plays a sound effect. 
         * 
         * @param e 
         */
        @Override
        public void keyReleased(KeyEvent e) {
            if(!gamePaused && game_state == 3) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    try {
                        clipChangeGrid = AudioSystem.getClip();
                    } 
                    catch (LineUnavailableException ex) {
                        Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        clipChangeGrid.open(AudioSystem.getAudioInputStream(fileChangeGrid));
                    } 
                    catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        FloatControl gainControl = 
                        (FloatControl) clipChangeGrid.getControl(FloatControl.Type.MASTER_GAIN);
                        gainControl.setValue(-23.0f); // volume setting, decreasing sfx volume a bit 
                        clipChangeGrid.start();
                }
            }
        }
    }
    
    /**
     * The constructor.
     * 
     * @throws Exception 
     */
    public Game_Display() throws Exception {
        addKeyListener(new AL());
        construct(game_state);
        
        if(videoimg != null) {  // videos should always start at the beginning 
            videoimg.flush();
            videoimg = null;
        }
    }
    
    /**
     * Called by the constructor at first, then others can call when needed. 
     * Creates the base of the game display, makes a screen update. 
     * 
     * @param gameState 
     */
    public void construct(int gameState) {
        if(gameState != nextState) {
            System.out.println("gameState != nextState");
            return;
        }
        if((double)gameState < 0.1) {
            
            ImageIcon theVeryFirst_icon = new ImageIcon("./src/hugohiihto/_the_very_1st_texts.png");
            int wi = (int) d.getWidth();
            int he = (int) d.getHeight()-35;
            theVeryFirst_icon.setImage(theVeryFirst_icon.getImage().getScaledInstance(wi, he, Image.SCALE_DEFAULT));
            theVeryFirst = theVeryFirst_icon.getImage();
            
            //addKeyListener(new AL());
            setFocusable(true);
            
        }
        if((double)gameState > 0.9 && (double)gameState < 1.1) {
            if(gameState != 3 && nextState != 3) {
                gamePaused = true;
            }
            
            if(HugoHiihto.currentStateAtTheLevel >= 81 && HugoHiihto.gameOver == false) {
                ImageIcon credits_icon = new ImageIcon("./src/hugohiihto/credits_screen.png");
                int wi = (int) d.getWidth()-2;
                int he = (int) d.getHeight()-37;
                credits_icon.setImage(credits_icon.getImage().getScaledInstance(wi, he, Image.SCALE_DEFAULT));
                creditsScreen = credits_icon.getImage();
                
                //addKeyListener(new AL());
                setFocusable(true);
            }
            else {
                ImageIcon title_icon = new ImageIcon("./src/hugohiihto/title_screen.png");
                int wi = (int) d.getWidth()-15;
                int he = (int) d.getHeight()-20;
                title_icon.setImage(title_icon.getImage().getScaledInstance(wi, he, Image.SCALE_DEFAULT));
                titleScreen = title_icon.getImage();
                
                //addKeyListener(new AL());
                setFocusable(true);
            }
        }
        if((double)gameState > 1.9 && (double)gameState < 2.1) {
            //addKeyListener(new AL());
            setFocusable(true);
            if(!useMP4) {       // Remember the video integer codes:
                    // 0 = Scylla intro,        1 = Hugo's first words hoplaa,
                    // 2 = Scylla button,       3 = three ropes intro,
                    // 4 = Hugo asks for two,   5 = two chosen correctly,
                    // 6 = made a wrong choice, 7 = Hugo finished the skiing,
                    // 8 = knock wake up pahvi, 9 = knock now the last troll going,
                    // 10 = knock game over,    11 = rope #1,
                    // 12 = rope #2,            13 = rope #3,
                    // 14 = snowman,            15 = snowball,
                    // 16 = bomb,               17 = Masi the beaver.
                
                //String pathGif = "";
                String pathSound = "";
                switch(video) {
                    case 0 ->  { 
                        //pathGif = "./src/hugohiihto/scylla_intro.gif";
                        pathSound = "./src/hugohiihto/scylla_intro.aiff";
                    }
                    case 1 ->  {
                        //pathGif = "./src/hugohiihto/start_hoplaa.gif";
                        pathSound = "./src/hugohiihto/start_hoplaa.aiff";
                    }
                    case 2 ->  {
                        //pathGif = "./src/hugohiihto/scylla_button_press.gif";
                        pathSound = "./src/hugohiihto/scylla_button_press.aiff";
                    }
                    case 3 ->  {
                        //pathGif = "./src/hugohiihto/scylla0.gif";
                        pathSound = "./src/hugohiihto/scylla0.aiff";
                    }
                    case 4 ->  {
                        //pathGif = "./src/hugohiihto/remember2forKey_intro.gif";
                        pathSound = "./src/hugohiihto/remember2forKey_intro.aiff";
                    }
                    case 5 ->  {
                        //pathGif = "./src/hugohiihto/remember2forKey_win.gif";
                        pathSound = "./src/hugohiihto/remember2forKey_win.aiff";
                    }
                    case 6 ->  {
                        //pathGif = "./src/hugohiihto/remember2forKey_fail.gif";
                        pathSound = "./src/hugohiihto/remember2forKey_fail.aiff";
                    }
                    case 7 ->  {
                        //pathGif = "./src/hugohiihto/screentalk_finish_line.gif";
                        pathSound = "./src/hugohiihto/screentalk_finish_line.aiff";
                    }
                    case 8 ->  {
                        //pathGif = "./src/hugohiihto/screentalk_heraa_pahvi.gif";
                        pathSound = "./src/hugohiihto/screentalk_heraa_pahvi.aiff";
                    }
                    case 9 ->  {
                        //pathGif = "./src/hugohiihto/screentalk_viimeista_viedaan.gif";
                        pathSound = "./src/hugohiihto/screentalk_viimeista_viedaan.aiff";
                    }
                    case 10 ->  {
                        //pathGif = "./src/hugohiihto/screentalk_game_over.gif";
                        pathSound = "./src/hugohiihto/screentalk_game_over.aiff";
                    }
                    case 11 ->  {
                        //pathGif = "./src/hugohiihto/scylla1.gif";
                        pathSound = "./src/hugohiihto/scylla1.aiff";
                    }
                    case 12 ->  {
                        //pathGif = "./src/hugohiihto/scylla2.gif";
                        pathSound = "./src/hugohiihto/scylla2.aiff";
                    }
                    case 13 ->  {
                        //pathGif = "./src/hugohiihto/scylla3.gif";
                        pathSound = "./src/hugohiihto/scylla3.aiff";
                    }
                    case 14 ->  {
                        //pathGif = "./src/hugohiihto/loselife_snowman.gif";
                        pathSound = "./src/hugohiihto/loselife_snowman.aiff";
                    }
                    case 15 ->  {
                        //pathGif = "./src/hugohiihto/loselife_snowball.gif";
                        pathSound = "./src/hugohiihto/loselife_snowball.aiff";
                    }
                    case 16 ->  {
                        //pathGif = "./src/hugohiihto/loselife_bomb.gif";
                        pathSound = "./src/hugohiihto/loselife_bomb.aiff";
                    }
                    case 17 ->  {
                        //pathGif = "./src/hugohiihto/loselife_beaver.gif";
                        pathSound = "./src/hugohiihto/loselife_beaver.aiff";
                    }
                } // Important! Do not change the file names. Any renaming will cause problems. 
                
                
                File fi = new File(pathSound); // .aiff is a well-working sound format for the current video setup 
                
                URL mediaURL = null;
                try {
                    mediaURL = fi.toURL();
                } 
                catch (MalformedURLException ex) {
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
                
                System.out.println("Playing sound for a video");
            }
        }
        if((double)gameState > 2.9 && (double)gameState < 3.1) {
            
            ImageIcon west = new ImageIcon("./src/hugohiihto/hugo_ski_L.gif");
            ImageIcon east = new ImageIcon("./src/hugohiihto/hugo_ski_R.gif");

                w_width = 110;
                west.setImage(west.getImage().getScaledInstance(w_width, w_height, Image.SCALE_DEFAULT));
                sprite_L2 = west.getImage();

                w_width = 90;
                west.setImage(west.getImage().getScaledInstance(w_width, w_height, Image.SCALE_DEFAULT));
                sprite_L = west.getImage();
                
                e_width = 90;
                east.setImage(east.getImage().getScaledInstance(e_width, e_height, Image.SCALE_DEFAULT));
                sprite_R = east.getImage();

                e_width = 110;
                east.setImage(east.getImage().getScaledInstance(e_width, e_height, Image.SCALE_DEFAULT));
                sprite_R2 = east.getImage();
            

            ImageIcon j = new ImageIcon("./src/hugohiihto/background1.png");
            int wi = (int) d.getWidth()-10;
            int he = (int) d.getHeight()-35;
            j.setImage(j.getImage().getScaledInstance(wi, he, Image.SCALE_DEFAULT));
            bg = j.getImage();

            setFocusable(true);

            //addKeyListener(new AL());
            
            x = 55;
            y = (int) ((int) d.getHeight()/2.67);
            
            // decoration graphics: trees, cloud(s) etc. 
            cloud_x_position = (int) ((int) d.getWidth()/3);
            cloud_y_position = (int) ((int) d.getHeight()/25);
            ImageIcon cloudicon = new ImageIcon("./src/hugohiihto/cloud.png");
            int cloudiconw = cloudicon.getIconWidth();
            int cloudiconh = cloudicon.getIconHeight();
            cloudicon.setImage(cloudicon.getImage().getScaledInstance(cloudiconw, cloudiconh, Image.SCALE_DEFAULT));
            cloud = cloudicon.getImage();
            
            possibleTree1_x_position = (d.width/5)-40;
            possibleTree1_y_position = (d.height/17)+20;
            possibleTree1icon = new ImageIcon("./src/hugohiihto/trees2.PNG");
            possibleTree1iconw = possibleTree1icon.getIconWidth();
            possibleTree1iconh = possibleTree1icon.getIconHeight();
            possibleTree1icon.setImage(possibleTree1icon.getImage()
                    .getScaledInstance(possibleTree1iconw, possibleTree1iconh, Image.SCALE_DEFAULT));
            possibleTree1 = possibleTree1icon.getImage();
            
            possibleTree2_x_position = (d.width/5)-40;
            possibleTree2_y_position = (d.height/17)+20;
            possibleTree2icon = new ImageIcon("./src/hugohiihto/trees0.PNG");
            possibleTree2iconw = possibleTree2icon.getIconWidth();
            possibleTree2iconh = possibleTree2icon.getIconHeight();
            possibleTree2icon.setImage(possibleTree2icon.getImage()
                    .getScaledInstance(possibleTree2iconw, possibleTree2iconh, Image.SCALE_DEFAULT));
            possibleTree2 = possibleTree2icon.getImage();
            
            possibleTree3_x_position = (d.width/5)-40;
            possibleTree3_y_position = (d.height/17)+20;
            possibleTree3icon = new ImageIcon("./src/hugohiihto/trees1.PNG");
            possibleTree3iconw = possibleTree3icon.getIconWidth();
            possibleTree3iconh = possibleTree3icon.getIconHeight();
            possibleTree3icon.setImage(possibleTree3icon.getImage()
                    .getScaledInstance(possibleTree3iconw, possibleTree3iconh, Image.SCALE_DEFAULT));
            possibleTree3 = possibleTree3icon.getImage();
            
            possibleTree4_x_position = (int) ((int) d.getWidth()/2)+70;
            possibleTree4_y_position = (int) ((int) d.getHeight()/4)-90;
            possibleTree4icon = new ImageIcon("./src/hugohiihto/trees2.PNG");
            possibleTree4iconw = possibleTree4icon.getIconWidth();
            possibleTree4iconh = possibleTree4icon.getIconHeight();
            possibleTree4icon.setImage(possibleTree4icon.getImage()
                    .getScaledInstance(possibleTree4iconw, possibleTree4iconh, Image.SCALE_DEFAULT));
            possibleTree4 = possibleTree4icon.getImage();
            
            possibleTree5_x_position = (int) ((int) d.getWidth()/2)+70;
            possibleTree5_y_position = (int) ((int) d.getHeight()/4)-90;
            possibleTree5icon = new ImageIcon("./src/hugohiihto/trees0.PNG");
            possibleTree5iconw = possibleTree5icon.getIconWidth();
            possibleTree5iconh = possibleTree5icon.getIconHeight();
            possibleTree5icon.setImage(possibleTree5icon.getImage()
                    .getScaledInstance(possibleTree5iconw, possibleTree5iconh, Image.SCALE_DEFAULT));
            possibleTree5 = possibleTree5icon.getImage();
            
            possibleTree6_x_position = (int) ((int) d.getWidth()/2)+70;
            possibleTree6_y_position = (int) ((int) d.getHeight()/4)-90;
            possibleTree6icon = new ImageIcon("./src/hugohiihto/trees1.PNG");
            possibleTree6iconw = possibleTree6icon.getIconWidth();
            possibleTree6iconh = possibleTree6icon.getIconHeight();
            possibleTree6icon.setImage(possibleTree6icon.getImage()
                    .getScaledInstance(possibleTree6iconw, possibleTree6iconh, Image.SCALE_DEFAULT));
            possibleTree6 = possibleTree6icon.getImage();
            
            
            hugolife1_x_position = (int) ((int) d.getWidth()/1.6);
            hugolife1_y_position = (int) ((int) d.getHeight()/1.3);
            ImageIcon life1 = new ImageIcon("./src/hugohiihto/hugo_life.png");
            int life1w = life1.getIconWidth();
            int life1h = life1.getIconHeight();
            life1.setImage(life1.getImage().getScaledInstance(life1w, life1h, Image.SCALE_DEFAULT));
            hugolife1 = life1.getImage();

            hugolife2_x_position = (int) ((int) d.getWidth()/1.6) +80;
            hugolife2_y_position = (int) ((int) d.getHeight()/1.3);
            ImageIcon life2 = new ImageIcon("./src/hugohiihto/hugo_life.png");
            int life2w = life2.getIconWidth();
            int life2h = life2.getIconHeight();
            life2.setImage(life2.getImage().getScaledInstance(life2w, life2h, Image.SCALE_DEFAULT));
            hugolife2 = life2.getImage();

            hugolife3_x_position = (int) ((int) d.getWidth()/1.6) + 160;
            hugolife3_y_position = (int) ((int) d.getHeight()/1.3);
            ImageIcon life3 = new ImageIcon("./src/hugohiihto/hugo_life.png");
            int life3w = life3.getIconWidth();
            int life3h = life3.getIconHeight();
            life3.setImage(life3.getImage().getScaledInstance(life3w, life3h, Image.SCALE_DEFAULT));
            hugolife3 = life3.getImage();

            pause_x_position = (int) ((int) d.getWidth()/3);
            pause_y_position = (int) ((int) d.getHeight()/3);
            ImageIcon pauseicon = new ImageIcon("./src/hugohiihto/pause.png");
            int pausew = pauseicon.getIconWidth();
            int pauseh = pauseicon.getIconHeight();
            pauseicon.setImage(pauseicon.getImage().getScaledInstance(pausew, pauseh, Image.SCALE_DEFAULT));
            pause = pauseicon.getImage();

            scorebar_x_position = 0;
            scorebar_y_position = (int) ((int) d.getHeight()/1.35);
            ImageIcon scorebaricon = new ImageIcon("./src/hugohiihto/score-life-bar.png");
            int scorebarw = scorebaricon.getIconWidth();
            int scorebarh = scorebaricon.getIconHeight();
            scorebaricon.setImage(scorebaricon.getImage().getScaledInstance(scorebarw*5, scorebarh, Image.SCALE_DEFAULT));
            scorebar = scorebaricon.getImage();
            
            
        }
        if((double)gameState > 3.9 && (double)gameState < 4.1) {

            ImageIcon io = new ImageIcon("./src/hugohiihto/cave_entrance00.PNG");
            int wi = (int) d.getWidth()-10;
            int he = (int) d.getHeight()-35;
            io.setImage(io.getImage().getScaledInstance(wi, he, Image.SCALE_DEFAULT));
            bgCave = io.getImage();
            
            setFocusable(true);
            
            //addKeyListener(new Game_Display.AL());// keyboard listener 
            
            cave_x = 1;
            cave_y = 1;
            
            
            for(int i = 0; i < 6; i++) {
                int pos = 0;
                int hei = 0;
                if(i == 0) {
                    pos = position1;
                    hei = heightLevel1;
                    
                    u1b_x_position = (int) ((int) d.getWidth()/6)+(pos-2);
                    u1b_y_position = (int) ((int) d.getHeight()/19)+(hei+90);
                    ImageIcon u1bicon = new ImageIcon("./src/hugohiihto/num_select1.png");
                    ImageIcon u1wicon = new ImageIcon("./src/hugohiihto/num_selected1.png");
                    int u1we = u1bicon.getIconWidth();
                    int u1he = u1bicon.getIconHeight();
                    u1bicon.setImage(u1bicon.getImage().getScaledInstance(u1we, u1he, Image.SCALE_DEFAULT));
                    u1b = u1bicon.getImage();
                    u1wicon.setImage(u1wicon.getImage().getScaledInstance(u1we, u1he, Image.SCALE_DEFAULT));
                    u1w = u1wicon.getImage();
                }
                if(i == 1) {
                    pos = position2;
                    hei = heightLevel1;
                    
                    u2b_x_position = (int) ((int) d.getWidth()/6)+(pos-2);
                    u2b_y_position = (int) ((int) d.getHeight()/19)+(hei+90);
                    ImageIcon u2bicon = new ImageIcon("./src/hugohiihto/num_select2.png");
                    ImageIcon u2wicon = new ImageIcon("./src/hugohiihto/num_selected2.png");
                    int u2we = u2bicon.getIconWidth();
                    int u2he = u2bicon.getIconHeight();
                    u2bicon.setImage(u2bicon.getImage().getScaledInstance(u2we, u2he, Image.SCALE_DEFAULT));
                    u2b = u2bicon.getImage();
                    u2wicon.setImage(u2wicon.getImage().getScaledInstance(u2we, u2he, Image.SCALE_DEFAULT));
                    u2w = u2wicon.getImage();
                }
                if(i == 2) {
                    pos = position3;
                    hei = heightLevel1;
                    
                    u3b_x_position = (int) ((int) d.getWidth()/6)+(pos-2);
                    u3b_y_position = (int) ((int) d.getHeight()/19)+(hei+90);
                    ImageIcon u3bicon = new ImageIcon("./src/hugohiihto/num_select3.png");
                    ImageIcon u3wicon = new ImageIcon("./src/hugohiihto/num_selected3.png");
                    int u3we = u3bicon.getIconWidth();
                    int u3he = u3bicon.getIconHeight();
                    u3bicon.setImage(u3bicon.getImage().getScaledInstance(u3we, u3he, Image.SCALE_DEFAULT));
                    u3b = u3bicon.getImage();
                    u3wicon.setImage(u3wicon.getImage().getScaledInstance(u3we, u3he, Image.SCALE_DEFAULT));
                    u3w = u3wicon.getImage();
                }
                if(i == 3) {
                    pos = position1;
                    hei = heightLevel2;
                    
                    d1b_x_position = (int) ((int) d.getWidth()/6)+(pos-2);
                    d1b_y_position = (int) ((int) d.getHeight()/19)+(hei+90);
                    ImageIcon d1bicon = new ImageIcon("./src/hugohiihto/num_select1.png");
                    ImageIcon d1wicon = new ImageIcon("./src/hugohiihto/num_selected1.png");
                    int d1we = d1bicon.getIconWidth();
                    int d1he = d1bicon.getIconHeight();
                    d1bicon.setImage(d1bicon.getImage().getScaledInstance(d1we, d1he, Image.SCALE_DEFAULT));
                    d1b = d1bicon.getImage();
                    d1wicon.setImage(d1wicon.getImage().getScaledInstance(d1we, d1he, Image.SCALE_DEFAULT));
                    d1w = d1wicon.getImage();
                }
                if(i == 4) {
                    pos = position2;
                    hei = heightLevel2;
                    
                    d2b_x_position = (int) ((int) d.getWidth()/6)+(pos-2);
                    d2b_y_position = (int) ((int) d.getHeight()/19)+(hei+90);
                    ImageIcon d2bicon = new ImageIcon("./src/hugohiihto/num_select2.png");
                    ImageIcon d2wicon = new ImageIcon("./src/hugohiihto/num_selected2.png");
                    int d2we = d2bicon.getIconWidth();
                    int d2he = d2bicon.getIconHeight();
                    d2bicon.setImage(d2bicon.getImage().getScaledInstance(d2we, d2he, Image.SCALE_DEFAULT));
                    d2b = d2bicon.getImage();
                    d2wicon.setImage(d2wicon.getImage().getScaledInstance(d2we, d2he, Image.SCALE_DEFAULT));
                    d2w = d2wicon.getImage();
                }
                if(i == 5) {
                    pos = position3;
                    hei = heightLevel2;
                    
                    d3b_x_position = (int) ((int) d.getWidth()/6)+(pos-2);
                    d3b_y_position = (int) ((int) d.getHeight()/19)+(hei+90);
                    ImageIcon d3bicon = new ImageIcon("./src/hugohiihto/num_select3.png");
                    ImageIcon d3wicon = new ImageIcon("./src/hugohiihto/num_selected3.png");
                    int d3we = d3bicon.getIconWidth();
                    int d3he = d3bicon.getIconHeight();
                    d3bicon.setImage(d3bicon.getImage().getScaledInstance(d3we, d3he, Image.SCALE_DEFAULT));
                    d3b = d3bicon.getImage();
                    d3wicon.setImage(d3wicon.getImage().getScaledInstance(d3we, d3he, Image.SCALE_DEFAULT));
                    d3w = d3wicon.getImage();
                }
                
                if(thingsToRemember.charAt(i) == 'a' || thingsToRemember.charAt(i) == 'A') {
                    asterisk_x_position = (int) ((int) d.getWidth()/6)+(pos);
                    asterisk_y_position = (int) ((int) d.getHeight()/19)+(hei);
                    ImageIcon asteriskicon = new ImageIcon("./src/hugohiihto/remember_A_asterisk.png");
                    int asteriskw = asteriskicon.getIconWidth();
                    int asteriskh = asteriskicon.getIconHeight();
                    asteriskicon.setImage(asteriskicon.getImage().getScaledInstance(asteriskw, asteriskh, Image.SCALE_DEFAULT));
                    asterisk = asteriskicon.getImage();
                }
                if(thingsToRemember.charAt(i) == 'b' || thingsToRemember.charAt(i) == 'B') {
                    bell_x_position = (int) ((int) d.getWidth()/6)+(pos);
                    bell_y_position = (int) ((int) d.getHeight()/19)+(hei);
                    ImageIcon bellicon = new ImageIcon("./src/hugohiihto/remember_B_bell.png");
                    int bellw = bellicon.getIconWidth();
                    int bellh = bellicon.getIconHeight();
                    bellicon.setImage(bellicon.getImage().getScaledInstance(bellw, bellh, Image.SCALE_DEFAULT));
                    bell = bellicon.getImage();
                }
                if(thingsToRemember.charAt(i) == 'c' || thingsToRemember.charAt(i) == 'C') {
                    clock_x_position = (int) ((int) d.getWidth()/6)+(pos);
                    clock_y_position = (int) ((int) d.getHeight()/19)+(hei);
                    ImageIcon clockicon = new ImageIcon("./src/hugohiihto/remember_C_clock.png");
                    int clockw = clockicon.getIconWidth();
                    int clockh = clockicon.getIconHeight();
                    clockicon.setImage(clockicon.getImage().getScaledInstance(clockw, clockh, Image.SCALE_DEFAULT));
                    clock = clockicon.getImage();
                }
                if(thingsToRemember.charAt(i) == 'd' || thingsToRemember.charAt(i) == 'D') {
                    diamond_x_position = (int) ((int) d.getWidth()/6)+(pos);
                    diamond_y_position = (int) ((int) d.getHeight()/19)+(hei);
                    ImageIcon diamondicon = new ImageIcon("./src/hugohiihto/remember_D_diamond.png");
                    int diamondw = diamondicon.getIconWidth();
                    int diamondh = diamondicon.getIconHeight();
                    diamondicon.setImage(diamondicon.getImage().getScaledInstance(diamondw, diamondh, Image.SCALE_DEFAULT));
                    diamond = diamondicon.getImage();
                }
                if(thingsToRemember.charAt(i) == 'h' || thingsToRemember.charAt(i) == 'H') {
                    hashtag_x_position = (int) ((int) d.getWidth()/6)+(pos);
                    hashtag_y_position = (int) ((int) d.getHeight()/19)+(hei);
                    ImageIcon hashtagicon = new ImageIcon("./src/hugohiihto/remember_H_hash.png");
                    int hashtagw = hashtagicon.getIconWidth();
                    int hashtagh = hashtagicon.getIconHeight();
                    hashtagicon.setImage(hashtagicon.getImage().getScaledInstance(hashtagw, hashtagh, Image.SCALE_DEFAULT));
                    hashtag = hashtagicon.getImage();
                }
                if(thingsToRemember.charAt(i) == 's' || thingsToRemember.charAt(i) == 'S') {
                    star_x_position = (int) ((int) d.getWidth()/6)+(pos);
                    star_y_position = (int) ((int) d.getHeight()/19)+(hei);
                    ImageIcon staricon = new ImageIcon("./src/hugohiihto/remember_S_star.png");
                    int starw = staricon.getIconWidth();
                    int starh = staricon.getIconHeight();
                    staricon.setImage(staricon.getImage().getScaledInstance(starw, starh, Image.SCALE_DEFAULT));
                    star = staricon.getImage();
                }
                
            }
            
        }
        if((double)gameState > 4.9 && (double)gameState < 5.1) {
            
            //addKeyListener(new Game_Display.AL());// keyboard listener but do not uncomment these, should be called only once 
            setFocusable(true);
            
            ImageIcon scoreBG = new ImageIcon("./src/hugohiihto/title_screen_nothing.png");
            int wi = (int) d.getWidth();
            int he = (int) d.getHeight();
            scoreBG.setImage(scoreBG.getImage().getScaledInstance(wi, he, Image.SCALE_DEFAULT));
            scoreBGR = scoreBG.getImage();
            
            star_x_position = (int) ((int) d.getWidth()/6);
            star_y_position = (int) ((int) d.getHeight()/19);
            ImageIcon staricon = new ImageIcon("./src/hugohiihto/remember_S_star.png");
            int starw = staricon.getIconWidth();
            int starh = staricon.getIconHeight();
            staricon.setImage(staricon.getImage().getScaledInstance(starw, starh, Image.SCALE_DEFAULT));
            star = staricon.getImage();
            
            if(videoimg != null) {
                videoimg.flush();
                videoimg = null;
            }
        }
        
        if(videoimg != null) {  // videos should always start at the beginning 
            videoimg.flush();
            videoimg = null;
        }
    }
    
    
    /**
     * The paint component method for graphics object(s).
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
            if((double)game_state < 0.1) {
                super.paintComponent(g);
                g.drawImage(theVeryFirst, 0, 0, this);
                repaint();
                
                if(nextState != game_state) {
                    System.out.println("------ State change from " + game_state + " to " + nextState);
                    game_state = nextState;
                    //super.paintComponent(g);
                    construct(game_state);
                    /*
                    try {
                        f.setContentPane(new Game_Display());
                    } 
                    catch (Exception ex) {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    */
                    repaint();
                    
                }
            }
            
            if((double)game_state > 0.9 && (double)game_state < 1.1) {
                super.paintComponent(g);
                if(HugoHiihto.currentStateAtTheLevel >= 81 && HugoHiihto.gameOver == false) {
                    //super.paintComponent(g);
                    g.drawImage(creditsScreen, 0, 0, this);
                    repaint();
                }
                else {
                    //super.paintComponent(g);
                    g.drawImage(titleScreen, 0, 0, this);
                    repaint();
                }
                
                if(nextState != game_state) {
                    System.out.println("------ State change from " + game_state + " to " + nextState);
                    game_state = nextState;
                    //super.paintComponent(g);
                    construct(game_state);
                    /*
                    try {
                        f.setContentPane(new Game_Display());
                    } 
                    catch (Exception ex) {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    */
                    repaint();
                    
                }
            }
            
            if((double)game_state > 1.9 && (double)game_state < 2.1) {
                super.paintComponent(g);
                
                if(useMP4) { // If you think that ".aiff + .gif" is not a good combination for some reason, 
                    String pathMP4 = "";    // please make your .mp4s and use them. 
                    switch(video) {
                        case 0 ->  { 
                            pathMP4 = "./src/hugohiihto/scylla_intro.mp4";
                            //pathSound = "./src/hugohiihto/scylla_intro.aiff";
                        }
                        case 1 ->  {
                            pathMP4 = "./src/hugohiihto/start_hoplaa.mp4";
                            //pathSound = "./src/hugohiihto/start_hoplaa.aiff";
                        }
                        case 2 ->  {
                            pathMP4 = "./src/hugohiihto/scylla_button_press.mp4";
                            //pathSound = "./src/hugohiihto/scylla_button_press.aiff";
                        }
                        case 3 ->  {
                            pathMP4 = "./src/hugohiihto/scylla0.mp4";
                            //pathSound = "./src/hugohiihto/scylla0.aiff";
                        }
                        case 4 ->  {
                            pathMP4 = "./src/hugohiihto/remember2forKey_intro.mp4";
                            //pathSound = "./src/hugohiihto/remember2forKey_intro.aiff";
                        }
                        case 5 ->  {
                            pathMP4 = "./src/hugohiihto/remember2forKey_win.mp4";
                            //pathSound = "./src/hugohiihto/remember2forKey_win.aiff";
                        }
                        case 6 ->  {
                            pathMP4 = "./src/hugohiihto/remember2forKey_fail.mp4";
                            //pathSound = "./src/hugohiihto/remember2forKey_fail.aiff";
                        }
                        case 7 ->  {
                            pathMP4 = "./src/hugohiihto/screentalk_finish_line.mp4";
                            //pathSound = "./src/hugohiihto/screentalk_finish_line.aiff";
                        }
                        case 8 ->  {
                            pathMP4 = "./src/hugohiihto/screentalk_heraa_pahvi.mp4";
                            //pathSound = "./src/hugohiihto/screentalk_heraa_pahvi.aiff";
                        }
                        case 9 ->  {
                            pathMP4 = "./src/hugohiihto/screentalk_viimeista_viedaan.mp4";
                            //pathSound = "./src/hugohiihto/screentalk_viimeista_viedaan.aiff";
                        }
                        case 10 ->  {
                            pathMP4 = "./src/hugohiihto/screentalk_game_over.mp4";
                            //pathSound = "./src/hugohiihto/screentalk_game_over.aiff";
                        }
                        case 11 ->  {
                            pathMP4 = "./src/hugohiihto/scylla1.mp4";
                            //pathSound = "./src/hugohiihto/scylla1.aiff";
                        }
                        case 12 ->  {
                            pathMP4 = "./src/hugohiihto/scylla2.mp4";
                            //pathSound = "./src/hugohiihto/scylla2.aiff";
                        }
                        case 13 ->  {
                            pathMP4 = "./src/hugohiihto/scylla3.mp4";
                            //pathSound = "./src/hugohiihto/scylla3.aiff";
                        }
                        case 14 ->  {
                            pathMP4 = "./src/hugohiihto/loselife_snowman.mp4";
                            //pathSound = "./src/hugohiihto/loselife_snowman.aiff";
                        }
                        case 15 ->  {
                            pathMP4 = "./src/hugohiihto/loselife_snowball.mp4";
                            //pathSound = "./src/hugohiihto/loselife_snowball.aiff";
                        }
                        case 16 ->  {
                            pathMP4 = "./src/hugohiihto/loselife_bomb.mp4";
                            //pathSound = "./src/hugohiihto/loselife_bomb.aiff";
                        }
                        case 17 ->  {
                            pathMP4 = "./src/hugohiihto/loselife_beaver.mp4";
                            //pathSound = "./src/hugohiihto/loselife_beaver.aiff";
                        }
                    }

                    File video_source = new File(pathMP4);
                    try {
                        Desktop.getDesktop().open(video_source); // opens Windows Media Player for instance 
                    } 
                    catch (IOException ex) {                   // not the best way to do things 
                        Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                else {
                    String pathGif = "";
                    //String pathSound = ""; 
                    switch(video) {
                        case 0 ->  { 
                            pathGif = "./src/hugohiihto/scylla_intro_s.gif";
                            //pathSound = "./src/hugohiihto/scylla_intro.aiff";
                        }
                        case 1 ->  {
                            pathGif = "./src/hugohiihto/start_hoplaa_s.gif";
                            //pathSound = "./src/hugohiihto/start_hoplaa.aiff";
                        }
                        case 2 ->  {
                            pathGif = "./src/hugohiihto/scylla_button_press_s.gif";
                            //pathSound = "./src/hugohiihto/scylla_button_press.aiff";
                        }
                        case 3 ->  {
                            pathGif = "./src/hugohiihto/scylla0_s.gif";
                            //pathSound = "./src/hugohiihto/scylla0.aiff";
                        }
                        case 4 ->  {
                            pathGif = "./src/hugohiihto/remember2forKey_intro_s.gif";
                            //pathSound = "./src/hugohiihto/remember2forKey_intro.aiff";
                        }
                        case 5 ->  {
                            pathGif = "./src/hugohiihto/remember2forKey_win_s.gif";
                            //pathSound = "./src/hugohiihto/remember2forKey_win.aiff";
                        }
                        case 6 ->  {
                            pathGif = "./src/hugohiihto/remember2forKey_fail_s.gif";
                            //pathSound = "./src/hugohiihto/remember2forKey_fail.aiff";
                        }
                        case 7 ->  {
                            pathGif = "./src/hugohiihto/screentalk_finish_line_s.gif";
                            //pathSound = "./src/hugohiihto/screentalk_finish_line.aiff";
                        }
                        case 8 ->  {
                            pathGif = "./src/hugohiihto/screentalk_heraa_pahvi_s.gif";
                            //pathSound = "./src/hugohiihto/screentalk_heraa_pahvi.aiff";
                        }
                        case 9 ->  {
                            pathGif = "./src/hugohiihto/screentalk_viimeista_viedaan_s.gif";
                            //pathSound = "./src/hugohiihto/screentalk_viimeista_viedaan.aiff";
                        }
                        case 10 ->  {
                            pathGif = "./src/hugohiihto/screentalk_game_over_s.gif";
                            //pathSound = "./src/hugohiihto/screentalk_game_over.aiff";
                        }
                        case 11 ->  {
                            pathGif = "./src/hugohiihto/scylla1_s.gif";
                            //pathSound = "./src/hugohiihto/scylla1.aiff";
                        }
                        case 12 ->  {
                            pathGif = "./src/hugohiihto/scylla2_s.gif";
                            //pathSound = "./src/hugohiihto/scylla2.aiff";
                        }
                        case 13 ->  {
                            pathGif = "./src/hugohiihto/scylla3_s.gif";
                            //pathSound = "./src/hugohiihto/scylla3.aiff";
                        }
                        case 14 ->  {
                            pathGif = "./src/hugohiihto/loselife_snowman_s.gif";
                            //pathSound = "./src/hugohiihto/loselife_snowman.aiff";
                        }
                        case 15 ->  {
                            pathGif = "./src/hugohiihto/loselife_snowball_s.gif";
                            //pathSound = "./src/hugohiihto/loselife_snowball.aiff";
                        }
                        case 16 ->  {
                            pathGif = "./src/hugohiihto/loselife_bomb_s.gif";
                            //pathSound = "./src/hugohiihto/loselife_bomb.aiff";
                        }
                        case 17 ->  {
                            pathGif = "./src/hugohiihto/loselife_beaver_s.gif";
                            //pathSound = "./src/hugohiihto/loselife_beaver.aiff";
                        }
                    }   // Important! Do not change the file names. 
                        // Mp4 videos not recommended currently and are not even possible to use wisely right now. 
                    
                    super.paintComponent(g);
                    videoimg = null; // .gif 
                    videoimg = new ImageIcon(pathGif).getImage();
                    int wi = (int) (d.getWidth());
                    int he = (int) (d.getHeight()-40);
                    videoimg.setAccelerationPriority((float)1.0); // from 0-> lowest to 1-> highest 
                    
                    g.drawImage(videoimg, 0, 0, wi, he, null);
                    for(int i = 0; i < 32000; i++) { 
                        repaint(); // Important repaint lines (yes, huge amount of repaint calls are necessary) 
                    }
                }
                
                if(nextState != game_state) {
                    System.out.println("------ State change from " + game_state + " to " + nextState);
                    game_state = nextState;
                    //super.paintComponent(g);
                    construct(game_state);
                    /*
                    try {
                        f.setContentPane(new Game_Display());
                    } 
                    catch (Exception ex) {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    */
                    repaint();
                    
                }
            }
            if((double)game_state > 2.9 && (double)game_state < 3.1) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, null);
                
                //dbImage = createImage(getWidth(), getHeight());
                //dbg = dbImage.getGraphics();
                //paintComponent(dbg);
                //g.drawImage(dbImage, x, y, this);
                //g.setColor(Color.WHITE);

                Thread Cloud = new Thread() {
                    @Override
                    /**
                     * Run (cloud) object 0 thread.
                     */
                      public void run() {
                        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                            if(System.currentTimeMillis()%19 == 0) {
                                cloud_x_position--;
                                //repaint();
                            }
                            if(cloud_x_position < -300) {
                                cloud_x_position = d.width;
                            }
                    }
                };
                if(!Cloud.isAlive()) {
                    Cloud.start();
                }

                Thread Tr1 = new Thread() {
                    @Override
                    /**
                     * Run object 1 thread.
                     */
                      public void run() {
                        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                        if(!gamePaused) {
                            if(System.currentTimeMillis()%2 == 0) {
                                possibleTree1_x_position-=2;
                                possibleTree1_y_position++;
                                //possibleTree1iconw++;
                                //possibleTree1iconh++;
                                //repaint();
                            }
                            if(possibleTree1_x_position < -340) {
                                possibleTree1_x_position = (d.width/8)-7;
                                possibleTree1_y_position = (d.height/12)+20;
                            }
                        }
                    }
                };
                if(!Tr1.isAlive()) {
                    Tr1.start();
                }
                Thread Tr2 = new Thread() {
                    @Override
                    /**
                     * Run object 2 thread.
                     */
                      public void run() {
                        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                        if(!gamePaused) {
                            if(System.currentTimeMillis()%2 == 0) {
                                possibleTree2_x_position-=2;
                                possibleTree2_y_position++;
                                //possibleTree2iconw++;
                                //possibleTree2iconh++;
                                //repaint();
                            }
                            if(possibleTree2_x_position < -400) {
                                possibleTree2_x_position = (d.width/4)-4;
                                possibleTree2_y_position = (d.height/8)+20;
                            }
                        }
                    }
                };
                if(!Tr2.isAlive()) {
                    Tr2.start();
                }
                Thread Tr3 = new Thread() {
                    @Override
                    /**
                     * Run object 3 thread.
                     */
                      public void run() {
                        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                        if(!gamePaused) {
                            if(System.currentTimeMillis()%2 == 0) {
                                possibleTree3_x_position-=2;
                                possibleTree3_y_position++;
                                //possibleTree3iconw++;
                                //possibleTree3iconh++;
                                //repaint();
                            }
                            if(possibleTree3_x_position < -500) {
                                possibleTree3_x_position = (d.width/5)-4;
                                possibleTree3_y_position = (d.height/17)+20;
                            }
                        }
                    }
                };
                if(!Tr3.isAlive()) {
                    Tr3.start();
                }
                Thread Tr4 = new Thread() {
                    @Override
                    /**
                     * Run object 4 thread.
                     */
                      public void run() {
                        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                        if(!gamePaused) {
                            if(System.currentTimeMillis()%2 == 0) {
                                possibleTree4_x_position+=2;
                                possibleTree4_y_position++;
                                //possibleTree4iconw++;
                                //possibleTree4iconh++;
                                //repaint();
                            }
                            if(possibleTree4_x_position > 640) {
                                possibleTree4_x_position = (int) ((int) d.getWidth()/2)+40;
                                possibleTree4_y_position = (int) ((int) d.getHeight()/3)-100;
                            }
                        }
                    }
                };
                if(!Tr4.isAlive()) {
                    Tr4.start();
                }
                Thread Tr5 = new Thread() {
                    @Override
                    /**
                     * Run object 5 thread.
                     */
                      public void run() {
                        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                        if(!gamePaused) {
                            if(System.currentTimeMillis()%2 == 0) {
                                possibleTree5_x_position+=2;
                                possibleTree5_y_position++;
                                //possibleTree5iconw++;
                                //possibleTree5iconh++;
                                //repaint();
                            }
                            if(possibleTree5_x_position > 700) {
                                possibleTree5_x_position = (int) ((int) d.getWidth()/2)+40;
                                possibleTree5_y_position = (int) ((int) d.getHeight()/3)-80;
                            }
                        }
                    }
                };
                if(!Tr5.isAlive()) {
                    Tr5.start();
                }
                Thread Tr6 = new Thread() {
                    @Override
                    /**
                     * Run object 6 thread.
                     */
                      public void run() {
                        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                        if(!gamePaused) {
                            if(System.currentTimeMillis()%2 == 0) {
                                possibleTree6_x_position+=2;
                                possibleTree6_y_position++;
                                //possibleTree6iconw++;
                                //possibleTree6iconh++;
                                //repaint();
                            }
                            if(possibleTree6_x_position > 800) {
                                possibleTree6_x_position = (int) ((int) d.getWidth()/2)+40;
                                possibleTree6_y_position = (int) ((int) d.getHeight()/3)-80;
                            }
                        }
                    }
                };
                if(!Tr6.isAlive()) {
                    Tr6.start();
                }

                // finally drawing the graphical decorations: 
                g.drawImage(cloud, cloud_x_position, cloud_y_position, this);
                g.drawImage(possibleTree1, possibleTree1_x_position, possibleTree1_y_position, this);
                g.drawImage(possibleTree2, possibleTree2_x_position, possibleTree2_y_position, this);
                g.drawImage(possibleTree3, possibleTree3_x_position, possibleTree3_y_position, this);
                g.drawImage(possibleTree4, possibleTree4_x_position, possibleTree4_y_position, this);
                g.drawImage(possibleTree5, possibleTree5_x_position, possibleTree5_y_position, this);
                g.drawImage(possibleTree6, possibleTree6_x_position, possibleTree6_y_position, this);
                
                String path_of_hazard_1 = "";
                String path_of_hazard_2 = "";
                String path_of_hazard_3 = "";
                String path_of_hazard_4 = "";
                if(currentHazardOrMoney1.equals("E") || currentHazardOrMoney1.equals("S") || currentHazardOrMoney1.equals("F")) {
                    currentHazardOrMoney1_image = null;
                }
                if(currentHazardOrMoney2.equals("E") || currentHazardOrMoney2.equals("S") || currentHazardOrMoney2.equals("F")) {
                    currentHazardOrMoney2_image = null;
                }
                if(currentHazardOrMoney3.equals("E") || currentHazardOrMoney3.equals("S") || currentHazardOrMoney3.equals("F")) {
                    currentHazardOrMoney3_image = null;
                }
                if(currentHazardOrMoney4.equals("E") || currentHazardOrMoney4.equals("S") || currentHazardOrMoney4.equals("F")) {
                    currentHazardOrMoney4_image = null;
                }

                if(currentHazardOrMoney1.equals("M")) {
                    path_of_hazard_1 = "./src/hugohiihto/money.png";
                }
                if(currentHazardOrMoney1.equals("8")) {
                    path_of_hazard_1 = "./src/hugohiihto/enemy_snowman.png";
                }
                if(currentHazardOrMoney1.equals("o")) {
                    path_of_hazard_1 = "./src/hugohiihto/enemy_snowball.png";
                }
                if(currentHazardOrMoney1.equals("Q")) {
                    path_of_hazard_1 = "./src/hugohiihto/enemy_bomb.png";
                }
                if(currentHazardOrMoney1.equals("B")) {
                    path_of_hazard_1 = "./src/hugohiihto/enemy_beaver_masi.png";
                }
                if(currentHazardOrMoney2.equals("M")) {
                    path_of_hazard_2 = "./src/hugohiihto/money.png";
                }
                if(currentHazardOrMoney2.equals("8")) {
                    path_of_hazard_2 = "./src/hugohiihto/enemy_snowman.png";
                }
                if(currentHazardOrMoney2.equals("o")) {
                    path_of_hazard_2 = "./src/hugohiihto/enemy_snowball.png";
                }
                if(currentHazardOrMoney2.equals("Q")) {
                    path_of_hazard_2 = "./src/hugohiihto/enemy_bomb.png";
                }
                if(currentHazardOrMoney2.equals("B")) {
                    path_of_hazard_2 = "./src/hugohiihto/enemy_beaver_masi.png";
                }
                if(currentHazardOrMoney3.equals("M")) {
                    path_of_hazard_3 = "./src/hugohiihto/money.png";
                }
                if(currentHazardOrMoney3.equals("8")) {
                    path_of_hazard_3 = "./src/hugohiihto/enemy_snowman.png";
                }
                if(currentHazardOrMoney3.equals("o")) {
                    path_of_hazard_3 = "./src/hugohiihto/enemy_snowball.png";
                }
                if(currentHazardOrMoney3.equals("Q")) {
                    path_of_hazard_3 = "./src/hugohiihto/enemy_bomb.png";
                }
                if(currentHazardOrMoney3.equals("B")) {
                    path_of_hazard_3 = "./src/hugohiihto/enemy_beaver_masi.png";
                }
                if(currentHazardOrMoney4.equals("M")) {
                    path_of_hazard_4 = "./src/hugohiihto/money.png";
                }
                if(currentHazardOrMoney4.equals("8")) {
                    path_of_hazard_4 = "./src/hugohiihto/enemy_snowman.png";
                }
                if(currentHazardOrMoney4.equals("o")) {
                    path_of_hazard_4 = "./src/hugohiihto/enemy_snowball.png";
                }
                if(currentHazardOrMoney4.equals("Q")) {
                    path_of_hazard_4 = "./src/hugohiihto/enemy_bomb.png";
                }
                if(currentHazardOrMoney4.equals("B")) {
                    path_of_hazard_4 = "./src/hugohiihto/enemy_beaver_masi.png";
                }
                if(currentHazardOrMoney1.equals("1")) {
                    path_of_hazard_2 = ""; currentHazardOrMoney2_image = null;
                    path_of_hazard_3 = ""; currentHazardOrMoney3_image = null;
                    path_of_hazard_4 = ""; currentHazardOrMoney4_image = null;
                    for(int i = 0; i < 3; i++) {
                        if(thingsToRemember.charAt(i) == 'A') {
                            path_of_hazard_1 = "./src/hugohiihto/remember_A_asterisk.png";
                        }
                        if(thingsToRemember.charAt(i) == 'B') {
                            path_of_hazard_1 = "./src/hugohiihto/remember_B_bell.png";
                        }
                        if(thingsToRemember.charAt(i) == 'C') {
                            path_of_hazard_1 = "./src/hugohiihto/remember_C_clock.png";
                        }
                        if(thingsToRemember.charAt(i) == 'D') {
                            path_of_hazard_1 = "./src/hugohiihto/remember_D_diamond.png";
                        }
                        if(thingsToRemember.charAt(i) == 'H') {
                            path_of_hazard_1 = "./src/hugohiihto/remember_H_hash.png";
                        }
                        if(thingsToRemember.charAt(i) == 'S') {
                            path_of_hazard_1 = "./src/hugohiihto/remember_S_star.png";
                        }
                    }
                }
                if(currentHazardOrMoney1.equals("2")) {
                    path_of_hazard_2 = ""; currentHazardOrMoney2_image = null;
                    path_of_hazard_3 = ""; currentHazardOrMoney3_image = null;
                    path_of_hazard_4 = ""; currentHazardOrMoney4_image = null;
                    for(int i = 3; i < 6; i++) {
                        if(thingsToRemember.charAt(i) == 'A') {
                            path_of_hazard_1 = "./src/hugohiihto/remember_A_asterisk.png";
                        }
                        if(thingsToRemember.charAt(i) == 'B') {
                            path_of_hazard_1 = "./src/hugohiihto/remember_B_bell.png";
                        }
                        if(thingsToRemember.charAt(i) == 'C') {
                            path_of_hazard_1 = "./src/hugohiihto/remember_C_clock.png";
                        }
                        if(thingsToRemember.charAt(i) == 'D') {
                            path_of_hazard_1 = "./src/hugohiihto/remember_D_diamond.png";
                        }
                        if(thingsToRemember.charAt(i) == 'H') {
                            path_of_hazard_1 = "./src/hugohiihto/remember_H_hash.png";
                        }
                        if(thingsToRemember.charAt(i) == 'S') {
                            path_of_hazard_1 = "./src/hugohiihto/remember_S_star.png";
                        }
                    }
                }
                // file names should remain exactly original 
                
                if(!"".equals(path_of_hazard_1)) {
                    //currentHazardOrMoney1_x_position = (int) ((int) d.getWidth()/15);
                    //currentHazardOrMoney1_y_position = (int) ((int) d.getHeight()/3);
                    ImageIcon currentHazardOrMoney_1 = new ImageIcon(path_of_hazard_1);
                    //currentHazardOrMoney1w = currentHazardOrMoney1.getIconWidth();
                    //currentHazardOrMoney1h = currentHazardOrMoney1.getIconHeight();
                    currentHazardOrMoney_1.setImage(currentHazardOrMoney_1.getImage()
                            .getScaledInstance(currentHazardOrMoney1w, currentHazardOrMoney1h, Image.SCALE_DEFAULT));
                    currentHazardOrMoney1_image = currentHazardOrMoney_1.getImage();
                }
                if(!"".equals(path_of_hazard_2)) {
                    //currentHazardOrMoney2_x_position = (int) ((int) d.getWidth()/15)+130;
                    //currentHazardOrMoney2_y_position = (int) ((int) d.getHeight()/3);
                    ImageIcon currentHazardOrMoney_2 = new ImageIcon(path_of_hazard_2);
                    //currentHazardOrMoney2w = currentHazardOrMoney2.getIconWidth();
                    //currentHazardOrMoney2h = currentHazardOrMoney2.getIconHeight();
                    currentHazardOrMoney_2.setImage(currentHazardOrMoney_2.getImage()
                            .getScaledInstance(currentHazardOrMoney2w, currentHazardOrMoney2h, Image.SCALE_DEFAULT));
                    currentHazardOrMoney2_image = currentHazardOrMoney_2.getImage();
                }
                if(!"".equals(path_of_hazard_3)) {
                    //currentHazardOrMoney3_x_position = (int) ((int) d.getWidth()/15)+250;
                    //currentHazardOrMoney3_y_position = (int) ((int) d.getHeight()/3);
                    ImageIcon currentHazardOrMoney_3 = new ImageIcon(path_of_hazard_3);
                    //currentHazardOrMoney3w = currentHazardOrMoney3.getIconWidth();
                    //currentHazardOrMoney3h = currentHazardOrMoney3.getIconHeight();
                    currentHazardOrMoney_3.setImage(currentHazardOrMoney_3.getImage()
                            .getScaledInstance(currentHazardOrMoney3w, currentHazardOrMoney3h, Image.SCALE_DEFAULT));
                    currentHazardOrMoney3_image = currentHazardOrMoney_3.getImage();
                }
                if(!"".equals(path_of_hazard_4)) {
                    //currentHazardOrMoney4_x_position = (int) ((int) d.getWidth()/15)+395;
                    //currentHazardOrMoney4_y_position = (int) ((int) d.getHeight()/3);
                    ImageIcon currentHazardOrMoney_4 = new ImageIcon(path_of_hazard_4);
                    //currentHazardOrMoney4w = currentHazardOrMoney4.getIconWidth();
                    //currentHazardOrMoney4h = currentHazardOrMoney4.getIconHeight();
                    currentHazardOrMoney_4.setImage(currentHazardOrMoney_4.getImage()
                            .getScaledInstance(currentHazardOrMoney4w, currentHazardOrMoney4h, Image.SCALE_DEFAULT));
                    currentHazardOrMoney4_image = currentHazardOrMoney_4.getImage();
                }

                String onesToDraw_path = "./src/hugohiihto/numbers" + String.valueOf(ones) + ".png";
                String tensToDraw_path = "./src/hugohiihto/numbers" + String.valueOf(tens) + ".png";
                String hundredsToDraw_path = "./src/hugohiihto/numbers" + String.valueOf(hundreds) + ".png";
                String thousandsToDraw_path = "./src/hugohiihto/numbers" + String.valueOf(thousands) + ".png";
                String tenThousandsToDraw_path = "./src/hugohiihto/numbers" + String.valueOf(tenThousands) + ".png";
                String hundredThousandsToDraw_path = "./src/hugohiihto/numbers" + String.valueOf(hundredThousands) + ".png";

                digitFromLeft1_x_position = (int) ((int) d.getWidth()/11);
                digitFromLeft1_y_position = (int) ((int) d.getHeight()/1.27);
                ImageIcon digitFromLeft1 = new ImageIcon(hundredThousandsToDraw_path);
                int digitFromLeft1w = digitFromLeft1.getIconWidth();
                int digitFromLeft1h = digitFromLeft1.getIconHeight();
                digitFromLeft1.setImage(digitFromLeft1.getImage().getScaledInstance(digitFromLeft1w, digitFromLeft1h, Image.SCALE_DEFAULT));
                digitFromLeft1image = digitFromLeft1.getImage();

                digitFromLeft2_x_position = (int) ((int) d.getWidth()/11) +50;
                digitFromLeft2_y_position = (int) ((int) d.getHeight()/1.27);
                ImageIcon digitFromLeft2 = new ImageIcon(tenThousandsToDraw_path);
                int digitFromLeft2w = digitFromLeft2.getIconWidth();
                int digitFromLeft2h = digitFromLeft2.getIconHeight();
                digitFromLeft2.setImage(digitFromLeft2.getImage().getScaledInstance(digitFromLeft2w, digitFromLeft2h, Image.SCALE_DEFAULT));
                digitFromLeft2image = digitFromLeft2.getImage();

                digitFromLeft3_x_position = (int) ((int) d.getWidth()/11) +100;
                digitFromLeft3_y_position = (int) ((int) d.getHeight()/1.27);
                ImageIcon digitFromLeft3 = new ImageIcon(thousandsToDraw_path);
                int digitFromLeft3w = digitFromLeft3.getIconWidth();
                int digitFromLeft3h = digitFromLeft3.getIconHeight();
                digitFromLeft3.setImage(digitFromLeft3.getImage().getScaledInstance(digitFromLeft3w, digitFromLeft3h, Image.SCALE_DEFAULT));
                digitFromLeft3image = digitFromLeft3.getImage();

                digitFromLeft4_x_position = (int) ((int) d.getWidth()/11) +150;
                digitFromLeft4_y_position = (int) ((int) d.getHeight()/1.27);
                ImageIcon digitFromLeft4 = new ImageIcon(hundredsToDraw_path);
                int digitFromLeft4w = digitFromLeft4.getIconWidth();
                int digitFromLeft4h = digitFromLeft4.getIconHeight();
                digitFromLeft4.setImage(digitFromLeft4.getImage().getScaledInstance(digitFromLeft4w, digitFromLeft4h, Image.SCALE_DEFAULT));
                digitFromLeft4image = digitFromLeft4.getImage();

                digitFromLeft5_x_position = (int) ((int) d.getWidth()/11) +200;
                digitFromLeft5_y_position = (int) ((int) d.getHeight()/1.27);
                ImageIcon digitFromLeft5 = new ImageIcon(tensToDraw_path);
                int digitFromLeft5w = digitFromLeft5.getIconWidth();
                int digitFromLeft5h = digitFromLeft5.getIconHeight();
                digitFromLeft5.setImage(digitFromLeft5.getImage().getScaledInstance(digitFromLeft5w, digitFromLeft5h, Image.SCALE_DEFAULT));
                digitFromLeft5image = digitFromLeft5.getImage();

                digitFromLeft6_x_position = (int) ((int) d.getWidth()/11) +250;
                digitFromLeft6_y_position = (int) ((int) d.getHeight()/1.27);
                ImageIcon digitFromLeft6 = new ImageIcon(onesToDraw_path);
                int digitFromLeft6w = digitFromLeft6.getIconWidth();
                int digitFromLeft6h = digitFromLeft6.getIconHeight();
                digitFromLeft6.setImage(digitFromLeft6.getImage().getScaledInstance(digitFromLeft6w, digitFromLeft6h, Image.SCALE_DEFAULT));
                digitFromLeft6image = digitFromLeft6.getImage();
                
                Thread HAZ1 = new Thread() {
                    @Override
                    /**
                     * Run ski track 1 thread.
                     */
                      public void run() {
                        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                        if(!gamePaused) {
                            if(System.currentTimeMillis()%3 == 0) {
                                currentHazardOrMoney1_x_position-=2;
                                currentHazardOrMoney1_y_position++;
                                currentHazardOrMoney1w++;
                                currentHazardOrMoney1h++;
                            }
                        }
                        if(currentHazardOrMoney1_y_position > d.getHeight()-250) {
                            currentHazardOrMoney1_x_position = (int) (d.getWidth() + 100);
                            currentHazardOrMoney1w = 1;
                            currentHazardOrMoney1h = 1;
                        }
                        if(currentHazardOrMoney1_y_position > d.getHeight()-250) {
                            currentHazardOrMoney1_y_position = 8000;
                            currentHazardOrMoney1_x_position = 8000;
                            //System.out.println("Off screen 1");
                        }
                    }
                };
                if(!HAZ1.isAlive()) {
                    HAZ1.start();
                }
                if(HugoHiihto.currentStateAtTheLevel > -2) {
                    g.drawImage(currentHazardOrMoney1_image, currentHazardOrMoney1_x_position, currentHazardOrMoney1_y_position, this);
                }
                
                Thread HAZ2 = new Thread() {
                    @Override
                    /**
                     * Run ski track 2 thread.
                     */
                      public void run() {
                        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                        if(!gamePaused) {
                            if(System.currentTimeMillis()%3 == 0) {
                                currentHazardOrMoney2_y_position++;
                                    currentHazardOrMoney2w++;
                                    currentHazardOrMoney2h++;
                            }
                            if(System.currentTimeMillis()%7 == 0 && currentHazardOrMoney2_x_position > 162) {
                                currentHazardOrMoney2_x_position--;
                            }
                            if(System.currentTimeMillis()%7 == 0 && currentHazardOrMoney2_x_position > 162) {
                                currentHazardOrMoney2_x_position--;
                            }
                        }
                        if(currentHazardOrMoney2_y_position > d.getHeight()-250) {
                            currentHazardOrMoney2_x_position = (int) (d.getWidth() + 100);
                            currentHazardOrMoney2w = 1;
                            currentHazardOrMoney2h = 1;
                        }
                        if(currentHazardOrMoney2_y_position > d.getHeight()-250) {
                            //System.out.println("Off screen 2");
                            currentHazardOrMoney2_y_position = 8000;
                            currentHazardOrMoney2_x_position = 8000;
                        }
                    }
                };
                if(!HAZ2.isAlive()) {
                    HAZ2.start();
                }
                if(HugoHiihto.currentStateAtTheLevel > -2) {
                    g.drawImage(currentHazardOrMoney2_image, currentHazardOrMoney2_x_position, currentHazardOrMoney2_y_position, this);
                }

                Thread HAZ3 = new Thread() {
                    @Override
                    /**
                     * Run ski track 3 thread.
                     */
                      public void run() {
                        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                        if(!gamePaused) {
                            if(System.currentTimeMillis()%3 == 0) {
                                currentHazardOrMoney3_y_position++;
                                    currentHazardOrMoney3w++;
                                    currentHazardOrMoney3h++;
                            }
                            if(System.currentTimeMillis()%15 == 0  && currentHazardOrMoney3_x_position < 340) {
                                currentHazardOrMoney3_x_position++;
                            }
                        }
                        if(currentHazardOrMoney3_y_position > d.getHeight()-250) {
                            currentHazardOrMoney3_x_position = (int) (d.getWidth() + 100);
                            currentHazardOrMoney3w = 1;
                            currentHazardOrMoney3h = 1;
                        }
                        if(currentHazardOrMoney3_y_position > d.getHeight()-250) {
                            //System.out.println("Off screen 3");
                            currentHazardOrMoney3_y_position = 8000;
                            currentHazardOrMoney3_x_position = 8000;
                        }
                    }
                };
                if(!HAZ3.isAlive()) {
                    HAZ3.start();
                }
                if(HugoHiihto.currentStateAtTheLevel > -2) {
                    g.drawImage(currentHazardOrMoney3_image, currentHazardOrMoney3_x_position, currentHazardOrMoney3_y_position, this);
                }

                Thread HAZ4 = new Thread() {
                    @Override
                    /**
                     * Run ski track 4 thread.
                     */
                      public void run() {
                        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                        if(!gamePaused) {
                            if(System.currentTimeMillis()%3 == 0) {
                                currentHazardOrMoney4_x_position++;
                                currentHazardOrMoney4_y_position++;
                                currentHazardOrMoney4w++;
                                currentHazardOrMoney4h++;
                            }
                        }
                        if(currentHazardOrMoney4_y_position > d.getHeight()-250) {
                            currentHazardOrMoney4_x_position = (int) (d.getWidth() + 100);
                            currentHazardOrMoney4w = 1;
                            currentHazardOrMoney4h = 1;
                        }
                        if(currentHazardOrMoney4_y_position > d.getHeight()-250) {
                            //System.out.println("Off screen 4");
                            currentHazardOrMoney4_y_position = 8000;
                            currentHazardOrMoney4_x_position = 8000;
                        }
                    }
                };
                if(!HAZ4.isAlive()) {
                    HAZ4.start();
                }
                if(HugoHiihto.currentStateAtTheLevel > -2) {
                    g.drawImage(currentHazardOrMoney4_image, currentHazardOrMoney4_x_position, currentHazardOrMoney4_y_position, this);
                }
                
                g.drawImage(scorebar, scorebar_x_position, scorebar_y_position, this);
                g.drawImage(digitFromLeft1image, digitFromLeft1_x_position, digitFromLeft1_y_position, this);
                g.drawImage(digitFromLeft2image, digitFromLeft2_x_position, digitFromLeft2_y_position, this);
                g.drawImage(digitFromLeft3image, digitFromLeft3_x_position, digitFromLeft3_y_position, this);
                g.drawImage(digitFromLeft4image, digitFromLeft4_x_position, digitFromLeft4_y_position, this);
                g.drawImage(digitFromLeft5image, digitFromLeft5_x_position, digitFromLeft5_y_position, this);
                g.drawImage(digitFromLeft6image, digitFromLeft6_x_position, digitFromLeft6_y_position, this);
                repaint();
                if((double)number_of_lives > 0.5) {
                    g.drawImage(hugolife1, hugolife1_x_position, hugolife1_y_position, this);
                }
                if((double)number_of_lives > 1.5) {
                    g.drawImage(hugolife2, hugolife2_x_position, hugolife2_y_position, this);
                }
                if((double)number_of_lives > 2.5) {
                    g.drawImage(hugolife3, hugolife3_x_position, hugolife3_y_position, this);
                }

                if(currentGrid < 2) { // Hugo ski animation 
                        Thread GRIDS01 = new Thread() {
                            @Override
                            /**
                            * Run Hugo L thread.
                            */
                              public void run() {
                                Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                                int target = 55;
                                if(currentGrid == 0) {
                                    target = 55;
                                }
                                if(currentGrid == 1) {
                                    target = 55 + (maxW/3);
                                }
                                    if(x > target && lastPressWasLeft) {
                                        x-=4;
                                        repaint();
                                    }
                                    if(x < target && !lastPressWasLeft) {
                                        x+=4;
                                        repaint();
                                    }
                            }
                        };
                        if(!GRIDS01.isAlive()) {
                            GRIDS01.start();
                        }
                    if(currentGrid == 0) {
                        g.drawImage(sprite_L2, x, y, this);
                    }
                    else {
                        g.drawImage(sprite_L, x, y, this);
                    }
                }
                else {
                        Thread GRIDS23 = new Thread() {
                            @Override
                            /**
                            * Run Hugo R thread.
                            */
                              public void run() {
                                Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                                int target = 55;
                                if(currentGrid == 2) {
                                    target = 55 + (maxW/3)*2;
                                }
                                if(currentGrid == 3) {
                                    target = 55 + (maxW/3)*3;
                                }
                                    if(x > target && lastPressWasLeft) {
                                        x-=4;
                                        repaint();
                                    }
                                    if(x < target && !lastPressWasLeft) {
                                        x+=4; // 4 is the speed of changing grid 
                                        repaint();
                                    }
                            }
                        };
                        if(!GRIDS23.isAlive()) {
                            GRIDS23.start();
                        }
                    if(currentGrid == 3) {
                        g.drawImage(sprite_R2, x, y, this);
                    }
                    else {
                        g.drawImage(sprite_R, x, y, this);
                    }
                }
                
                                 // Order matters, 
                if(gamePaused) { // pause should be written last because it should always be on top of everything. 
                    if(pausedWithEnter) {
                        g.drawImage(pause, pause_x_position, pause_y_position, this);
                    }
                }
                
                if(nextState != game_state) {
                    System.out.println("------ State change from " + game_state + " to " + nextState);
                    game_state = nextState;
                    //super.paintComponent(g);
                    construct(game_state);
                    /*
                    try {
                        f.setContentPane(new Game_Display());
                    } 
                    catch (Exception ex) {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    */
                    
                    repaint();
                    
                }
            }
            if((double)game_state > 3.9 && (double)game_state < 4.1) {
                super.paintComponent(g);
                
                g.drawImage(bgCave, cave_x, cave_y, this); // cave image is based on the sledge Hugo game, a classic winter game 
                
                g.drawImage(asterisk, asterisk_x_position, asterisk_y_position, this);
                g.drawImage(bell, bell_x_position, bell_y_position, this);
                g.drawImage(clock, clock_x_position, clock_y_position, this);
                g.drawImage(diamond, diamond_x_position, diamond_y_position, this);
                g.drawImage(hashtag, hashtag_x_position, hashtag_y_position, this);
                g.drawImage(star, star_x_position, star_y_position, this);
                
                if(currentlyAllCorrect) {
                    g.drawImage(u1b, u1b_x_position, u1b_y_position, this);
                    g.drawImage(u2b, u2b_x_position, u2b_y_position, this);
                    g.drawImage(u3b, u3b_x_position, u3b_y_position, this);
                }

                if(secondPhase && currentlyAllCorrect) {
                    if( thingsToRemember.charAt(0) == 'A' || // if caps, correct (design choice) 
                        thingsToRemember.charAt(0) == 'B' || 
                        thingsToRemember.charAt(0) == 'C' || 
                        thingsToRemember.charAt(0) == 'D' || 
                        thingsToRemember.charAt(0) == 'H' || 
                        thingsToRemember.charAt(0) == 'S') {
                                g.drawImage(u1w, u1b_x_position, u1b_y_position, this);
                    }
                    if( thingsToRemember.charAt(1) == 'A' || 
                        thingsToRemember.charAt(1) == 'B' || 
                        thingsToRemember.charAt(1) == 'C' || 
                        thingsToRemember.charAt(1) == 'D' || 
                        thingsToRemember.charAt(1) == 'H' || 
                        thingsToRemember.charAt(1) == 'S') {
                                g.drawImage(u2w, u2b_x_position, u2b_y_position, this);
                    }
                    if( thingsToRemember.charAt(2) == 'A' || 
                        thingsToRemember.charAt(2) == 'B' || 
                        thingsToRemember.charAt(2) == 'C' || 
                        thingsToRemember.charAt(2) == 'D' || 
                        thingsToRemember.charAt(2) == 'H' || 
                        thingsToRemember.charAt(2) == 'S') {
                                g.drawImage(u3w, u3b_x_position, u3b_y_position, this);
                    }

                    g.drawImage(d1b, d1b_x_position, d1b_y_position, this);
                    g.drawImage(d2b, d2b_x_position, d2b_y_position, this);
                    g.drawImage(d3b, d3b_x_position, d3b_y_position, this);

                    if(allCorrectInTheEnd) {
                        if( thingsToRemember.charAt(3) == 'A' || // if caps, correct (design choice) 
                            thingsToRemember.charAt(3) == 'B' || 
                            thingsToRemember.charAt(3) == 'C' || 
                            thingsToRemember.charAt(3) == 'D' || 
                            thingsToRemember.charAt(3) == 'H' || 
                            thingsToRemember.charAt(3) == 'S') {
                                    g.drawImage(d1w, d1b_x_position, d1b_y_position, this);
                        }
                        if( thingsToRemember.charAt(4) == 'A' || 
                            thingsToRemember.charAt(4) == 'B' || 
                            thingsToRemember.charAt(4) == 'C' || 
                            thingsToRemember.charAt(4) == 'D' || 
                            thingsToRemember.charAt(4) == 'H' || 
                            thingsToRemember.charAt(4) == 'S') {
                                    g.drawImage(d2w, d2b_x_position, d2b_y_position, this);
                        }
                        if( thingsToRemember.charAt(5) == 'A' || 
                            thingsToRemember.charAt(5) == 'B' || 
                            thingsToRemember.charAt(5) == 'C' || 
                            thingsToRemember.charAt(5) == 'D' || 
                            thingsToRemember.charAt(5) == 'H' || 
                            thingsToRemember.charAt(5) == 'S') {
                                    g.drawImage(d3w, d3b_x_position, d3b_y_position, this);
                        }
                        video = 5;
                        nextState = 6;
                    }
                }
                if(!currentlyAllCorrect) {
                    video = 6;
                    nextState = 6;
                }
                repaint();

                if(nextState != game_state) {
                    System.out.println("------ State change from " + game_state + " to " + nextState);
                    game_state = nextState;
                    //super.paintComponent(g);
                    construct(game_state);
                    /*
                    try {
                        f.setContentPane(new Game_Display());
                    } 
                    catch (Exception ex) {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    */
                    
                    repaint();
                    
                }
            }
            
            if((double)game_state > 4.9 && (double)game_state < 5.1) {
                super.paintComponent(g);
                
                String onesToDraw_path = "./src/hugohiihto/numbers" + String.valueOf(ones) + ".png";
                String tensToDraw_path = "./src/hugohiihto/numbers" + String.valueOf(tens) + ".png";
                String hundredsToDraw_path = "./src/hugohiihto/numbers" + String.valueOf(hundreds) + ".png";
                String thousandsToDraw_path = "./src/hugohiihto/numbers" + String.valueOf(thousands) + ".png";
                String tenThousandsToDraw_path = "./src/hugohiihto/numbers" + String.valueOf(tenThousands) + ".png";
                String hundredThousandsToDraw_path = "./src/hugohiihto/numbers" + String.valueOf(hundredThousands) + ".png";

                digitFromLeft1_x_position = (int) ((int) d.getWidth()/11);
                digitFromLeft1_y_position = (int) ((int) d.getHeight()/2.2);
                ImageIcon digitFromLeft1 = new ImageIcon(hundredThousandsToDraw_path);
                int digitFromLeft1w = digitFromLeft1.getIconWidth();
                int digitFromLeft1h = digitFromLeft1.getIconHeight();
                digitFromLeft1.setImage(digitFromLeft1.getImage().getScaledInstance(
                        digitFromLeft1w, digitFromLeft1h, Image.SCALE_DEFAULT));
                digitFromLeft1image = digitFromLeft1.getImage();

                digitFromLeft2_x_position = (int) ((int) d.getWidth()/11) +50;
                digitFromLeft2_y_position = (int) ((int) d.getHeight()/2.2);
                ImageIcon digitFromLeft2 = new ImageIcon(tenThousandsToDraw_path);
                int digitFromLeft2w = digitFromLeft2.getIconWidth();
                int digitFromLeft2h = digitFromLeft2.getIconHeight();
                digitFromLeft2.setImage(digitFromLeft2.getImage().getScaledInstance(
                        digitFromLeft2w, digitFromLeft2h, Image.SCALE_DEFAULT));
                digitFromLeft2image = digitFromLeft2.getImage();

                digitFromLeft3_x_position = (int) ((int) d.getWidth()/11) +100;
                digitFromLeft3_y_position = (int) ((int) d.getHeight()/2.2);
                ImageIcon digitFromLeft3 = new ImageIcon(thousandsToDraw_path);
                int digitFromLeft3w = digitFromLeft3.getIconWidth();
                int digitFromLeft3h = digitFromLeft3.getIconHeight();
                digitFromLeft3.setImage(digitFromLeft3.getImage().getScaledInstance(
                        digitFromLeft3w, digitFromLeft3h, Image.SCALE_DEFAULT));
                digitFromLeft3image = digitFromLeft3.getImage();

                digitFromLeft4_x_position = (int) ((int) d.getWidth()/11) +150;
                digitFromLeft4_y_position = (int) ((int) d.getHeight()/2.2);
                ImageIcon digitFromLeft4 = new ImageIcon(hundredsToDraw_path);
                int digitFromLeft4w = digitFromLeft4.getIconWidth();
                int digitFromLeft4h = digitFromLeft4.getIconHeight();
                digitFromLeft4.setImage(digitFromLeft4.getImage().getScaledInstance(
                        digitFromLeft4w, digitFromLeft4h, Image.SCALE_DEFAULT));
                digitFromLeft4image = digitFromLeft4.getImage();

                digitFromLeft5_x_position = (int) ((int) d.getWidth()/11) +200;
                digitFromLeft5_y_position = (int) ((int) d.getHeight()/2.2);
                ImageIcon digitFromLeft5 = new ImageIcon(tensToDraw_path);
                int digitFromLeft5w = digitFromLeft5.getIconWidth();
                int digitFromLeft5h = digitFromLeft5.getIconHeight();
                digitFromLeft5.setImage(digitFromLeft5.getImage().getScaledInstance(
                        digitFromLeft5w, digitFromLeft5h, Image.SCALE_DEFAULT));
                digitFromLeft5image = digitFromLeft5.getImage();

                digitFromLeft6_x_position = (int) ((int) d.getWidth()/11) +250;
                digitFromLeft6_y_position = (int) ((int) d.getHeight()/2.2);
                ImageIcon digitFromLeft6 = new ImageIcon(onesToDraw_path);
                int digitFromLeft6w = digitFromLeft6.getIconWidth();
                int digitFromLeft6h = digitFromLeft6.getIconHeight();
                digitFromLeft6.setImage(digitFromLeft6.getImage().getScaledInstance(
                        digitFromLeft6w, digitFromLeft6h, Image.SCALE_DEFAULT));
                digitFromLeft6image = digitFromLeft6.getImage();
                
                g.drawImage(scoreBGR, -10, -18, null);
                repaint();

                if(pulled_rope_1) {
                    ImageIcon r1_icon = new ImageIcon("./src/hugohiihto/rope1good.png");
                    int r1_iconw = r1_icon.getIconWidth();
                    int r1_iconh = r1_icon.getIconHeight();
                    r1_icon.setImage(r1_icon.getImage().getScaledInstance(r1_iconw-30, r1_iconh-30, Image.SCALE_DEFAULT));
                    r1 = r1_icon.getImage();
                    g.drawImage(r1, digitFromLeft1_x_position-40, digitFromLeft1_y_position+70, this);
                }
                if(pulled_rope_2 == true || (pulled_rope_1 == false && pulled_rope_2 == false && pulled_rope_3 == false)) {
                    ImageIcon r2_icon = new ImageIcon("./src/hugohiihto/rope2bad.png");
                    int r2_iconw = r2_icon.getIconWidth();
                    int r2_iconh = r2_icon.getIconHeight();
                    r2_icon.setImage(r2_icon.getImage().getScaledInstance(r2_iconw-30, r2_iconh-30, Image.SCALE_DEFAULT));
                    r2 = r2_icon.getImage();
                    g.drawImage(r2, digitFromLeft1_x_position-40, digitFromLeft1_y_position+70, this);
                }
                if(pulled_rope_3) {
                    ImageIcon r3_icon = new ImageIcon("./src/hugohiihto/rope3best.png");
                    int r3_iconw = r3_icon.getIconWidth();
                    int r3_iconh = r3_icon.getIconHeight();
                    r3_icon.setImage(r3_icon.getImage().getScaledInstance(r3_iconw-30, r3_iconh-30, Image.SCALE_DEFAULT));
                    r3 = r3_icon.getImage();
                    g.drawImage(r3, digitFromLeft1_x_position-40, digitFromLeft1_y_position+70, this);
                }
                
                g.drawImage(digitFromLeft1image, digitFromLeft1_x_position, digitFromLeft1_y_position, this);
                g.drawImage(digitFromLeft2image, digitFromLeft2_x_position, digitFromLeft2_y_position, this);
                g.drawImage(digitFromLeft3image, digitFromLeft3_x_position, digitFromLeft3_y_position, this);
                g.drawImage(digitFromLeft4image, digitFromLeft4_x_position, digitFromLeft4_y_position, this);
                g.drawImage(digitFromLeft5image, digitFromLeft5_x_position, digitFromLeft5_y_position, this);
                g.drawImage(digitFromLeft6image, digitFromLeft6_x_position, digitFromLeft6_y_position, this);
                repaint();
                
                
                if(nextState != game_state) {
                    System.out.println("------ State change from " + game_state + " to " + nextState);
                    game_state = nextState;
                    //super.paintComponent(g);
                    construct(game_state);
                    /*
                    try {
                        f.setContentPane(new Game_Display());
                    } 
                    catch (Exception ex) {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    */
                    
                    repaint();
                    
                }
            }
            if((double)game_state >= 5.1) {
                // when moving from state 2 straight back to state 2, this is a needed workaround, show 2 videos in a row both with sound 
                nextState = 2;
                System.out.println("------ State change from " + game_state + " to " + nextState);
                game_state = nextState;
                //super.paintComponent(g);
                construct(game_state);
            }
    }
    
    
    /**
     * The main method. The program execution starts here. 
     * Hugo Ski Game for Java, PC video game, designed for laptops and desktops. 
     * 
     * Tested with Microsoft Windows 11, Java 8 u361 64bit. JDK 18.0.2 64bit. 
     * Java developed by Oracle / Sun Microsystems. 
     * 
     * Tuomas Hyvönen, 2023, Finland 
     * Apache NetBeans 15 
     * 
     * @param args 
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { // lambda expression, https://www.w3schools.com/java/java_lambda.asp
            try {
                System.out.println("Hugo Skiing, JAR version 1.0.0.2, Sep-12-2023");
                f = new JFrame("HUGO - HIIHTO");
                f.setIconImage(new ImageIcon("./src/hugohiihto/favicon_corner.png").getImage());
                f.setSize(d);
                f.setMaximumSize(d); // please do not change the dimension, affects how the graphics will show up
                f.setResizable(false);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
                f.setContentPane(new Game_Display());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //robot = new Robot(); // if a demo gameplay is wanted later (perhaps on the title screen when no input)

            }
            catch (Exception e) {
                // At least the top left corner favicon did not show up correctly if we're here
                // or nothing works at all in the first place.
                f = new JFrame("SOME FILES ARE PROBABLY MISSING OR THEY HAVE BEEN RENAMED OR EDITED");
                f.setSize(d);
                //f.setMaximumSize(d);
                //f.setResizable(false);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                System.out.println(e);
                System.out.println( """
                                Problem in the main method: JFrame window was not created correctly.
                                Please check that the game files are available in the correct folder and
                                the files have not been renamed. Java source codes are the best place
                                to start looking for solutions to bugs. Even if JFrame is created,
                                that does not guarantee that all of the files are always readable though.
                                    """);
            }
        } /**
         * Running the JFrame.
         */ );
    }
} 