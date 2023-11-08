package hugohiihto;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Hugo Ski Game by T. Hyvönen, 2023 
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
 * Computations and methods not included in the user interface / game display Java source code file. 
 * Comments and names are sometimes in Finnish but tried to use English mostly. 
 * 
 * Tested with Microsoft Windows 11
 * Java developed by Oracle / Sun Microsystems. 
 * Created with Apache NetBeans 15 
 * Official release date: 24/2/2023 - v1.0 Finnish version available worldwide 
 * @author Tuomas Hyvönen
 * @version 1.0.0.4
 */
public class HugoHiihto {
    static TimerTask timerTask;
    String[] haz = null;
    String rem = null;
    static boolean gameOver = true; // has to be true at first 
    static boolean hasAchievedMaxScore = false; // even though it is not possible to get 999999 in real gameplay 
    static int currentStateAtTheLevel = -5; // -5   (so the game does not start with a surprise attack)
    static int theFurthestThePlayerHasGot = -4; // -4   (-5 +1)
    // There is no clear logic where the variables should belong, there are only 2 Java classes used anyway 
    
    /**
     * The constructor.
     */
    public HugoHiihto() {
        haz = giveStageHazards();
        rem = giveThingsToRemember();
    }
    
    /**
     * Getter for a game instance. 
     * 
     * @return 
     */
    public HugoHiihto getGame() {
        return this;
    }
    
    /**
     * Resetting the game or beginning a new game. 
     * The game speed integer is in milliseconds, for example 1800 = 1.8 seconds. 
     * Does not edit how fast the graphics will show on the screen though! 
     * 
     * @param gameSpeed 
     */
    public static void gameReset(int gameSpeed) {
                    
        if(gameSpeed > 1000 && gameSpeed < 3000 && gameOver) {
            System.out.println("Game reset called"); 
            gameSpeed = Game_Display.GAMESPEED;
            System.gc(); // run Java garbage collector 
                Game_Display.currentGrid = 0;
                Game_Display.pulled_rope_1 = false;
                Game_Display.pulled_rope_2 = false;
                Game_Display.pulled_rope_3 = false;
                gameOver = false;
                Game_Display.ones = 0;   // score digits (6)
                Game_Display.tens = 0;
                Game_Display.hundreds = 0;
                Game_Display.thousands = 0;
                Game_Display.tenThousands = 0;
                Game_Display.hundredThousands = 0;
                Game_Display.number_of_lives = 3; // amount of lives
                HugoHiihto.currentStateAtTheLevel = -5;
                HugoHiihto.hasAchievedMaxScore = false;
                HugoHiihto.theFurthestThePlayerHasGot = -4;
                Game_Display.currentlyAllCorrect = true; // even though 0 guesses 
                Game_Display.secondPhase = false;
                Game_Display.allCorrectInTheEnd = false;
                
                HugoHiihto hugoHiihto = new HugoHiihto();
                Game_Display.thingsToRemember = hugoHiihto.getREM();
                timerTask = hugoHiihto.new GameLoop();  // Gameloop handles the stage hazards with time tasks and 
                java.util.Timer ti = new java.util.Timer(true);   // processes them, different than the game display itself. 
                ti.scheduleAtFixedRate(timerTask, 0, gameSpeed);  // For example 1800 = 1.8 sec (affects the game speed, not graphics) 
        }
        else {
            //System.out.println("Weird game speed ------- should be: gameSpeed > 1000 ms && gameSpeed < 3000 ms"); 
            gameSpeed = 1620;
            gameOver = true;
            gameReset(gameSpeed);
        }
    }
    
    /**
     * Get the 2 things to remember. 
     * @return 
     */
    public String getREM() {
        return this.rem;
    }
    
    /**
     * Create a random integer, Java's own Math.random() has been used. 
     * @param min
     * @param max_that_does_not_count
     * @return 
     */
    public static int getRandom(int min, int max_that_does_not_count) {
        int random = (int) ((Math.random() * (max_that_does_not_count - min)) + min);
        return random; // max_that_does_not_count if zero counts 
    }
    
    /**
     * Creating the stage hazards to the 1d array called hazards. 
     * Length is 81, negative values = the stage is about to begin soon and Hugo is just skiing. 
     * @return 
     */
    public static int[] createStageHazards() {
        int[] hazards = new int[81];
        /*
         * E- empty
         * M- money
         * 8- snowman
         * o- snowball
         * Q- bomb
         * B- Masi the beaver
         * 
         * 1- thing to remember #1 (6 possible)
         * 2- thing to remember #2 (6 possible (actually 5 because no same again))
         * S- Scylla button press with short horror music
         * F- goal, just end the skiing session
         * 
         * 
         * 0 = empty, empty, SNOWBALL, empty
         * 1 = MONEY, empty, SNOWMAN, empty
         * 2 = empty, MONEY, SNOWMAN, empty
         * 3 = empty, empty, MONEY, empty
         * 4 = empty, SNOWMAN, empty, MONEY
         * 5 = SNOWMAN, empty, empty, empty
         * 6 = empty, empty, empty, SNOWMAN
         * 7 = empty, SNOWBALL, empty, empty
         * 8 = empty, empty, empty, empty 
         * 9 = BOMB, BOMB, empty, BOMB 
         * 10 = BOMB, empty, BOMB, BOMB 
         * 11 = empty, BOMB, BOMB, empty
         * 12 = BOMB, empty, MONEY, BOMB
         * 13 = BEAVER, MONEY, empty, empty
         * 14 = SNOWBALL, BEAVER, empty, empty
         * 15 = empty, empty, BEAVER, SNOWBALL
         * 16 = BOMB, empty, empty, BEAVER
         * 17 = empty, SNOWBALL, BOMB, BOMB
         * 18 = BOMB, BOMB, SNOWBALL, empty
         * 
         * // some will be overwritten with 2 must-remember-images etc. 
         */
        int randomPrevious = 8;
        for(int i = 0; i < hazards.length/2; i++) {
            int random = getRandom(0,9);
            while(randomPrevious == random) { // avoids getting the same number again 
                random = getRandom(0,9);
            }
            hazards[i] = random;
            randomPrevious = hazards[i];
        }
        for(int i = hazards.length/2; i < hazards.length; i++) {
            int random = getRandom(5,19);
            while(randomPrevious == random) {
                random = getRandom(5,19);
            }
            hazards[i] = random;
            randomPrevious = hazards[i];
        }
        System.out.println(Arrays.toString(hazards));
        return hazards;
    }
    
    /**
     * Getting the stage hazards.
     * 
     * @return 
     */
    public static String[] giveStageHazards() {
        String[] s = new String[81];
        int haz[] = createStageHazards();
        
        for(int i = 0; i < 80; i++) {
            if(haz[i] <= 0) {
                s[i] = "EEoE";
            }
            if(haz[i] == 1) {
                s[i] = "ME8E";
            }
            if(haz[i] == 2) {
                s[i] = "EM8E";
            }
            if(haz[i] == 3) {
                s[i] = "EEME";
            }
            if(haz[i] == 4) {
                s[i] = "E8EM";
            }
            if(haz[i] == 5) {
                s[i] = "8EEE";
            }
            if(haz[i] == 6) {
                s[i] = "EEE8";
            }
            if(haz[i] == 7) {
                s[i] = "EoEE";
            }
            if(haz[i] == 8) {
                s[i] = "EEEE";
            }
            if(haz[i] == 9) {
                s[i] = "QQEQ";
            }
            if(haz[i] == 10) {
                s[i] = "QEQQ";
            }
            if(haz[i] == 11) {
                s[i] = "EQQE";
            }
            if(haz[i] == 12) {
                s[i] = "QEMQ";
            }
            if(haz[i] == 13) {
                s[i] = "BMEE";
            }
            if(haz[i] == 14) {
                s[i] = "oBEE";
            }
            if(haz[i] == 15) {
                s[i] = "EEBo";
            }
            if(haz[i] == 16) {
                s[i] = "QEEB";
            }
            if(haz[i] == 17) {
                s[i] = "EoQQ";
            }
            if(haz[i] >= 18) {
                s[i] = "QQoE";
            }
        }
        
        // zero counts as the 1st number, 
        
        // from 0 to 14:    casual easy 
        // the 15th:          the 1st to remember
        // from 16 to 25:   casual easy 
        // the 26th:          the 2nd to remember
        // from 27 to 38:   casual easy 
        // the 39th:          Scylla's button press, popcorn starts 
        // from 40 to 79:   hardcore bombing 
        // the 80th:          the finish line
        // "memory game" 
        // 3 ropes
        s[15] = "1111";
        s[26] = "2222";
        s[39] = "SSSS";
        s[80] = "FFFF";
        System.out.println(Arrays.toString(s));
        
        return s;
    }
    
    /**
     * Generate what 2 things must be remembered when Hugo is at the goal.
     * 
     * @return 
     */
    public static int generateIntToRemember() {
        /*
         * 1 = asterisk & bell
         * 2 = asterisk & clock
         * 3 = asterisk & diamond
         * 4 = asterisk & hashtag
         * 5 = asterisk & star  // "tähti, tähti"
         * 6 = bell & clock     // "kello, kello"
         * 7 = bell & diamond
         * 8 = bell & hashtag
         * 9 = bell & star
         * 10 = clock & diamond
         * 11 = clock & hashtag
         * 12 = clock & star
         * 13 = diamond & hashtag // "ruutu, ruutu"
         * 14 = diamond & star
         * 15 = hashtag & star
         * 
         * Wrong answers when 3+3 are shown to the player: 
         * 1 clock, diamond; hashtag, star 
         * 2 bell, diamond; hashtag, star 
         * 3 bell, clock; hashtag, star 
         * 4 bell, clock; diamond, star 
         * 5 bell, clock; diamond, hashtag 
         * 6 asterisk, diamond; hashtag, star 
         * 7 asterisk, clock; hashtag, star 
         * 8 asterisk, clock; diamond, star 
         * 9 asterisk, clock; diamond, hashtag 
         * 10 asterisk, bell; hashtag, star 
         * 11 asterisk, bell; diamond, star 
         * 12 asterisk, bell; diamond, hashtag 
         * 13 asterisk, bell; clock, star 
         * 14 asterisk, bell; clock, hashtag 
         * 15 asterisk, bell; clock, diamond 
         * 
         */
        int items = getRandom(0,16); // some may have more probability to become chosen
        return items;
    }
    
    /**
     * Give the 2 items to remember in order to get the skull cave key at the end.
     * 
     * @return 
     */
    public static String giveThingsToRemember() {
        int problem = generateIntToRemember();
        boolean[][] problem2array = new boolean[3][3];
        // asterisk a, bell b, clock c, diamond d, hashtag h, star s 
        // caps means the right answer 
        String s = "";
        if(problem <= 1) {
            problem2array[0][0] = true; // Acd Bhs, most probably 
            s = "AcdBhs100100";
        }
        if(problem == 2) {
            problem2array[0][1] = true; // Ads bCh 
            s = "AdsbCh100010";
        }
        if(problem == 3) {
            problem2array[0][2] = true; // Acb shD 
            s = "AcbshD100001";
        }
        if(problem == 4) {
            problem2array[1][0] = true; // bAc Hds 
            s = "bAcHds010100";
        }
        if(problem == 5) {
            problem2array[1][1] = true; // cAb hSd 
            s = "cAbhSd010010";
        }
        if(problem == 6) {
            problem2array[1][2] = true; // aBd hsC 
            s = "aBdhsC010001";
        }
        if(problem == 7) {
            problem2array[2][0] = true; // chB Das 
            s = "chBDas001100";
        }
        if(problem == 8) {
            problem2array[2][1] = true; // acB sHd 
            s = "acBsHd001010";
        }
        if(problem == 9) {
            problem2array[2][2] = true; // haB cdS 
            s = "haBcdS001001";
        }
        if(problem == 10) {
            problem2array[0][0] = true; // Cab Dsh 
            s = "CabDsh100100";
        }
        if(problem == 11) {
            problem2array[0][1] = true; // Cbd aHs 
            s = "CbdaHs100010";
        }
        if(problem == 12) {
            problem2array[2][2] = true; // haC dbS 
            s = "haCdbS001001";
        }
        if(problem == 13) {
            problem2array[0][0] = true; // Das Hbc 
            s = "DasHbc100100";
        }
        if(problem == 14) {
            problem2array[1][1] = true; // aDb cSh 
            s = "aDbcSh010010";
        }
        if(problem >= 15) {
            problem2array[0][0] = true; // Hba Sdc 
            s = "HbaSdc100100";
        }
        
        System.out.println(s);
        return s;
    }
    
    /**
     * Increase score, ones.
     * 
     * @param whatWillBeIncreased
     */
    public static void increaseScoreOnes(int whatWillBeIncreased) {
        if((whatWillBeIncreased < 9) && (whatWillBeIncreased > -1) && (!hasAchievedMaxScore)) {
                whatWillBeIncreased++;
                Game_Display.setOnes(whatWillBeIncreased);
        }
        else {
                increaseScoreTens(Game_Display.tens);
                Game_Display.setOnes(0);
        }
        if(hasAchievedMaxScore) {
            Game_Display.setOnes(9);
            Game_Display.setTens(9);
            Game_Display.setHundreds(9);
            Game_Display.setThousands(9);
            Game_Display.setTenThousands(9);
            Game_Display.setHundredThousands(9);
        }
    }
    
    /**
     * Increase score, tens.
     * 
     * @param whatWillBeIncreased
     */
    public static void increaseScoreTens(int whatWillBeIncreased) {
        if((whatWillBeIncreased < 9) && (whatWillBeIncreased > -1) && (!hasAchievedMaxScore)) {
                whatWillBeIncreased++;
                Game_Display.setTens(whatWillBeIncreased);
        }
        else {
                increaseScoreHundreds(Game_Display.hundreds);
                Game_Display.setTens(0);
        }
        if(hasAchievedMaxScore) {
            Game_Display.setOnes(9);
            Game_Display.setTens(9);
            Game_Display.setHundreds(9);
            Game_Display.setThousands(9);
            Game_Display.setTenThousands(9);
            Game_Display.setHundredThousands(9);
        }
    }
    
    /**
     * Increase score, 100s.
     * 
     * @param whatWillBeIncreased
     */
    public static void increaseScoreHundreds(int whatWillBeIncreased) {
        if((whatWillBeIncreased < 9) && (whatWillBeIncreased > -1) && (!hasAchievedMaxScore)) {
                whatWillBeIncreased++;
                Game_Display.setHundreds(whatWillBeIncreased);
        }
        else {
                increaseScoreThousands(Game_Display.thousands);
                Game_Display.setHundreds(0);
        }
        if(hasAchievedMaxScore) {
            Game_Display.setOnes(9);
            Game_Display.setTens(9);
            Game_Display.setHundreds(9);
            Game_Display.setThousands(9);
            Game_Display.setTenThousands(9);
            Game_Display.setHundredThousands(9);
        }
    }
    
    /**
     * Increase score, 1000s.
     * 
     * @param whatWillBeIncreased
     */
    public static void increaseScoreThousands(int whatWillBeIncreased) {
        if((whatWillBeIncreased < 9) && (whatWillBeIncreased > -1) && (!hasAchievedMaxScore)) {
                whatWillBeIncreased++;
                Game_Display.setThousands(whatWillBeIncreased);
        }
        else {
                increaseScoreTenThousands(Game_Display.tenThousands);
                Game_Display.setThousands(0);
        }
        if(hasAchievedMaxScore) {
            Game_Display.setOnes(9);
            Game_Display.setTens(9);
            Game_Display.setHundreds(9);
            Game_Display.setThousands(9);
            Game_Display.setTenThousands(9);
            Game_Display.setHundredThousands(9);
        }
    }
    
    /**
     * Increase score, 10 000s.
     * 
     * @param whatWillBeIncreased
     */
    public static void increaseScoreTenThousands(int whatWillBeIncreased) {
        if((whatWillBeIncreased < 9) && (whatWillBeIncreased > -1) && (!hasAchievedMaxScore)) {
                whatWillBeIncreased++;
                Game_Display.setTenThousands(whatWillBeIncreased);
        }
        else {
                increaseScoreHundredThousands(Game_Display.hundredThousands);
                Game_Display.setTenThousands(0);
        }
        if(hasAchievedMaxScore) {
            Game_Display.setOnes(9);
            Game_Display.setTens(9);
            Game_Display.setHundreds(9);
            Game_Display.setThousands(9);
            Game_Display.setTenThousands(9);
            Game_Display.setHundredThousands(9);
        }
    }
    
    /**
     * Increase score, 100 000s.
     * 
     * @param whatWillBeIncreased
     */
    public static void increaseScoreHundredThousands(int whatWillBeIncreased) {
        if((whatWillBeIncreased < 9) && (whatWillBeIncreased > -1) && (!hasAchievedMaxScore)) {
                whatWillBeIncreased++;
                Game_Display.setHundredThousands(whatWillBeIncreased);
        }
        else {
                hasAchievedMaxScore = true; // trying to increase when it's already 9 
                System.out.println("THE PLAYER GOT A MILLION POINTS");
        }
        if(hasAchievedMaxScore) {
            Game_Display.setOnes(9);
            Game_Display.setTens(9);
            Game_Display.setHundreds(9);
            Game_Display.setThousands(9);
            Game_Display.setTenThousands(9);
            Game_Display.setHundredThousands(9);
        }
    }
    
    
    /**
     * Decreases lives by 1, call when hitting an enemy (4 possible enemies).
     * 
     * @param lives
     */
    public static void decreaseLives(int lives) {
        lives--;
        Game_Display.setLives(lives);
    }


    /**
     * The game loop, reads the hazard array step by step when not paused, uses timer. 
     * 
     * This is the most important part of the game logic. 
     * 
     * Thanks to this for huge progress: 
     * https://www.digitalocean.com/community/tutorials/java-timer-timertask-example
     * 
     */
    public class GameLoop extends TimerTask {
        boolean tic = true;
        boolean hasDoneOnce = false;
        String compareString = "";
        
        @Override
        /**
         * Running the time-based tasks. 
         */
        public void run() {
            
            if(currentStateAtTheLevel >= 81 || gameOver) {
                this.cancel(); // we're at the finish line or it is game over, now the memory puzzle if alive 
                Game_Display.gamePaused = true;
                
                if(HugoHiihto.currentStateAtTheLevel >= 81 && Game_Display.gamePaused) {
                    //Game_Display.robot.keyPress(KeyEvent.VK_F13);
                    // F13 means a state change, not implemented, press by a (ro)bot 
                    Game_Display.video = 7;
                    Game_Display.nextState = 6;
                }
            }
            if(Game_Display.nextState == 2) {
                Game_Display.currentGrid = 0;
                Game_Display.gamePaused = true; // no gameplay during a video 
            }
            else {
                if(currentStateAtTheLevel < 37) {
                    File fileGameMusicH = new File("./src/hugohiihto/hiihtoaani.wav");
                    try {
                        Game_Display.clipH = AudioSystem.getClip();
                    } 
                    catch (LineUnavailableException ex) {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        Game_Display.clipH.open(AudioSystem.getAudioInputStream(fileGameMusicH));
                    } 
                    catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    FloatControl gainControl = 
                    (FloatControl) Game_Display.clipH.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(-11.0f); // volume setting for music or sound 
                    Game_Display.clipH.start();
                }
            }
            
            if(!Game_Display.gamePaused) {
                if(currentStateAtTheLevel < 80) {
                    hasDoneOnce = false;
                }
                
                if(tic) {
                    tic = false;
                    System.out.println("\nTIK, location is " + currentStateAtTheLevel + ", next will be " + theFurthestThePlayerHasGot);
                    
                    if(theFurthestThePlayerHasGot > -1) {
                            
                        if(compareString.charAt(Game_Display.currentGrid) == 'F') {
                            currentStateAtTheLevel = 81;
                        }
                        if(compareString.charAt(Game_Display.currentGrid) == '8') {
                            System.out.println("ENEMY HIT -1---- SNOWMAN" + ", line (from 0 to 3) is " + Game_Display.currentGrid);
                            Game_Display.gamePaused = true;
                            Game_Display.video = 14;
                            Game_Display.nextState = 2;
                            decreaseLives(Game_Display.number_of_lives);
                        }
                        if(compareString.charAt(Game_Display.currentGrid) == 'o') {
                            System.out.println("ENEMY HIT --2--- SNOWBALL" + ", line (from 0 to 3) is " + Game_Display.currentGrid);
                            Game_Display.gamePaused = true;
                            Game_Display.video = 15;
                            Game_Display.nextState = 2;
                            decreaseLives(Game_Display.number_of_lives);
                        }
                        if(compareString.charAt(Game_Display.currentGrid) == 'Q') {
                            System.out.println("ENEMY HIT ---3-- BOMB" + ", line (from 0 to 3) is " + Game_Display.currentGrid);
                            Game_Display.gamePaused = true;
                            Game_Display.video = 16;
                            Game_Display.nextState = 2;
                            decreaseLives(Game_Display.number_of_lives);
                        }
                        if(compareString.charAt(Game_Display.currentGrid) == 'B') {
                            System.out.println("ENEMY HIT ----4- BEAVER" + ", line (from 0 to 3) is " + Game_Display.currentGrid);
                            Game_Display.gamePaused = true;
                            Game_Display.video = 17;
                            Game_Display.nextState = 2;
                            decreaseLives(Game_Display.number_of_lives);
                        }
                        if(compareString.charAt(Game_Display.currentGrid) == 'S') {
                            System.out.println("SCYLLA BUTTON PRESS");
                            Game_Display.gamePaused = true;
                            Game_Display.video = 2;
                            Game_Display.nextState = 2;
                            
                            Game_Display.clip4 = null; // clip variables are music or sound 
                            
                            if(Game_Display.number_of_lives <= 1) {
                                File fileGameMusic2 = new File("./src/hugohiihto/music_from_classic_skateboard.wav");
                                try {
                                    Game_Display.clip4 = AudioSystem.getClip();
                                } 
                                catch (LineUnavailableException ex) {
                                    Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                try {
                                    Game_Display.clip4.open(AudioSystem.getAudioInputStream(fileGameMusic2));
                                } 
                                catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                                    Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                FloatControl gainControl = 
                                (FloatControl) Game_Display.clip4.getControl(FloatControl.Type.MASTER_GAIN);
                                gainControl.setValue(-4.0f); // volume setting for music 
                                Game_Display.clip4.start();
                            }
                            else { // popcorn music from the TV show's airplane Hugo 
                                try {
                                    Game_Display.clip1 = AudioSystem.getClip();
                                } 
                                catch (LineUnavailableException ex) {
                                    Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                try {
                                    Game_Display.clip1.open(AudioSystem.getAudioInputStream(Game_Display.fileGameMusic1));
                                } 
                                catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                                    Logger.getLogger(Game_Display.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                FloatControl gainControl = 
                                (FloatControl) Game_Display.clip1.getControl(FloatControl.Type.MASTER_GAIN);
                                gainControl.setValue(-14.0f); // volume setting for music, decreasing volume so Hugo's words can be heard 
                                Game_Display.clip1.start();
                            }
                        }
                        
                        if(compareString.charAt(Game_Display.currentGrid) == 'M') {
                            
                            try {
                                Game_Display.clipMoney = AudioSystem.getClip();
                            } 
                            catch (LineUnavailableException ex) {
                                Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                Game_Display.clipMoney.open(AudioSystem.getAudioInputStream(Game_Display.fileMoney));
                            } 
                            catch (UnsupportedAudioFileException | IOException  ex) {
                                Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (LineUnavailableException ex) {
                                Logger.getLogger(HugoHiihto.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            FloatControl gainControl = 
                            (FloatControl) Game_Display.clipMoney.getControl(FloatControl.Type.MASTER_GAIN);
                            gainControl.setValue(-0.0f); // volume setting 
                            Game_Display.clipMoney.start();
                            
                            increaseScoreOnes(Game_Display.ones);
                            increaseScoreTens(Game_Display.tens);
                            increaseScoreHundreds(Game_Display.hundreds);
                            increaseScoreThousands(Game_Display.thousands); // 1111 p -- then add 0 or 12 or 100 or 112 p (4 possible) 
                            if(Math.random() < 0.5) {
                                increaseScoreHundreds(Game_Display.hundreds); // 1211 p 
                            }
                            if(Math.random() < 0.8) {
                                increaseScoreTens(Game_Display.tens);   // 1123 p 
                                increaseScoreOnes(Game_Display.ones);
                                increaseScoreOnes(Game_Display.ones);   // max can be +1223 p at one time, min is +1111 p 
                            }
                        }
                        if(compareString.charAt(Game_Display.currentGrid) == 'F' && !hasDoneOnce) {
                            hasDoneOnce = true;
                            increaseScoreTenThousands(Game_Display.tenThousands);
                            increaseScoreTenThousands(Game_Display.tenThousands);
                            increaseScoreTenThousands(Game_Display.tenThousands);
                            increaseScoreTenThousands(Game_Display.tenThousands);
                            increaseScoreTenThousands(Game_Display.tenThousands);// 50 000 when at the goal before the "2 memory puzzle" 
                            
                            if(Game_Display.number_of_lives >= 3) {
                                increaseScoreTenThousands(Game_Display.tenThousands);
                                increaseScoreTenThousands(Game_Display.tenThousands);
                                increaseScoreTenThousands(Game_Display.tenThousands);// +30 000 if no lives lost 
                            }
                            else {
                                if(Game_Display.number_of_lives >= 2) {
                                    increaseScoreTenThousands(Game_Display.tenThousands);
                                    increaseScoreTenThousands(Game_Display.tenThousands);// +20 000 
                                }
                                else {
                                    increaseScoreTenThousands(Game_Display.tenThousands);// +10 000 means 1 life 
                                }
                            }
                            
                            try {
                                Game_Display.clipScore = AudioSystem.getClip();
                            } 
                            catch (LineUnavailableException ex) {
                                Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                Game_Display.clipScore.open(AudioSystem.getAudioInputStream(Game_Display.fileScore));
                            } 
                            catch (UnsupportedAudioFileException | IOException  ex) {
                                Logger.getLogger(VideoTester_not_necessary.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (LineUnavailableException ex) {
                                Logger.getLogger(HugoHiihto.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            FloatControl gainControl = 
                            (FloatControl) Game_Display.clipScore.getControl(FloatControl.Type.MASTER_GAIN);
                            gainControl.setValue(-0.0f); // volume 
                            Game_Display.clipScore.start();
                        }
                    }
                    
                }
                else {
                    tic = true;
                    if(currentStateAtTheLevel < 0) {
                        Game_Display.currentHazardOrMoney1_x_position = 1500; // not even started yet, 
                        Game_Display.currentHazardOrMoney2_x_position = 1500; // if showing enemies, don't 
                        Game_Display.currentHazardOrMoney3_x_position = 1500;
                        Game_Display.currentHazardOrMoney4_x_position = 1500;
                    }
                    if(currentStateAtTheLevel < 80 && theFurthestThePlayerHasGot < 80) {
                        currentStateAtTheLevel++;
                        if(currentStateAtTheLevel >= theFurthestThePlayerHasGot) {
                            theFurthestThePlayerHasGot++;
                        }
                    }
                    System.out.println("TAK, location is " + currentStateAtTheLevel + ", next will be " + theFurthestThePlayerHasGot);
                    Game_Display.currentHazardOrMoney1_x_position = (Game_Display.d.width/3)+40;
                    Game_Display.currentHazardOrMoney1_y_position = (int)(Game_Display.d.height/3.7);
                    Game_Display.currentHazardOrMoney2_x_position = (Game_Display.d.width/3)+60;
                    Game_Display.currentHazardOrMoney2_y_position = (int)(Game_Display.d.height/3.7);
                    Game_Display.currentHazardOrMoney3_x_position = (Game_Display.d.width/3)+82;
                    Game_Display.currentHazardOrMoney3_y_position = (int)(Game_Display.d.height/3.7);
                    Game_Display.currentHazardOrMoney4_x_position = (Game_Display.d.width/3)+130;
                    Game_Display.currentHazardOrMoney4_y_position = (int)(Game_Display.d.height/3.7);
                    Game_Display.currentHazardOrMoney1w = 10; 
                    Game_Display.currentHazardOrMoney1h = 30;
                    Game_Display.currentHazardOrMoney2w = 10;
                    Game_Display.currentHazardOrMoney2h = 30;
                    Game_Display.currentHazardOrMoney3w = 10;
                    Game_Display.currentHazardOrMoney3h = 30;
                    Game_Display.currentHazardOrMoney4w = 10;
                    Game_Display.currentHazardOrMoney4h = 30;
                    
                    for(int i = 0; i < haz.length; i++) {
                        if(theFurthestThePlayerHasGot == i) {
                            System.out.print(" ... ABOUT TO GIVE THE NEXT 4 with index " + i + " --- " + haz[i]);
                            compareString = haz[i];
                            String values = haz[i];
                            if(     values.charAt(0) == 'E' || 
                                    values.charAt(0) == 'M' || 
                                    values.charAt(0) == '8' || 
                                    values.charAt(0) == 'o' || 
                                    values.charAt(0) == 'Q' || 
                                    values.charAt(0) == 'B' || 
                                    values.charAt(0) == '1' || 
                                    values.charAt(0) == '2' || 
                                    values.charAt(0) == 'S' || 
                                    values.charAt(0) == 'F') {
                                Game_Display.setcurrentHazardOrMoney1(String.valueOf(values.charAt(0)));
                            }
                            if(     values.charAt(1) == 'E' || 
                                    values.charAt(1) == 'M' || 
                                    values.charAt(1) == '8' || 
                                    values.charAt(1) == 'o' || 
                                    values.charAt(1) == 'Q' || 
                                    values.charAt(1) == 'B' || 
                                    values.charAt(1) == '1' || 
                                    values.charAt(1) == '2' || 
                                    values.charAt(1) == 'S' || 
                                    values.charAt(1) == 'F') {
                                Game_Display.setcurrentHazardOrMoney2(String.valueOf(values.charAt(1)));
                            }
                            if(     values.charAt(2) == 'E' || 
                                    values.charAt(2) == 'M' || 
                                    values.charAt(2) == '8' || 
                                    values.charAt(2) == 'o' || 
                                    values.charAt(2) == 'Q' || 
                                    values.charAt(2) == 'B' || 
                                    values.charAt(2) == '1' || 
                                    values.charAt(2) == '2' || 
                                    values.charAt(2) == 'S' || 
                                    values.charAt(2) == 'F') {
                                Game_Display.setcurrentHazardOrMoney3(String.valueOf(values.charAt(2)));
                            }
                            if(     values.charAt(3) == 'E' || 
                                    values.charAt(3) == 'M' || 
                                    values.charAt(3) == '8' || 
                                    values.charAt(3) == 'o' || 
                                    values.charAt(3) == 'Q' || 
                                    values.charAt(3) == 'B' || 
                                    values.charAt(3) == '1' || 
                                    values.charAt(3) == '2' || 
                                    values.charAt(3) == 'S' || 
                                    values.charAt(3) == 'F') {
                                Game_Display.setcurrentHazardOrMoney4(String.valueOf(values.charAt(3)));
                            }
                            
                            
                            if(!"FFFF".equals(haz[i])) {
                                haz[i] = "EEEE";
                            }
                        }
                    }
                    
                }
                
            }
            else {
                if(!hasDoneOnce && currentStateAtTheLevel > -2 && currentStateAtTheLevel < 80) {
                    currentStateAtTheLevel--;
                }
                hasDoneOnce = true;
            }
        }
    };
} 