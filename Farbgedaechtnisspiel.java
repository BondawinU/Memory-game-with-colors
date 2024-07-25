import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class Farbgedaechtnisspiel {

    private static int gamestate;
    private static int gamelvl;
    private static int count;
    private static Color [] userArray;
    private static Color [] gameArray;
    private static JTextArea event;
    private static JButton [] k;
    private static JLabel lvl;
    private static Timer timer;

    public void actionPerformed(ActionEvent e){}

    public static void main(String[]args){

        gamestate = 0;
        gamelvl = 1;

        JFrame frame = new JFrame("Farbspiel");
        initFrame(frame);
       
        k = new JButton[6];   
        for(int i = 0;i<k.length;i++){
            k[i] = new JButton();
            frame.add(k[i]);
        }
        initButtons();

        lvl = new JLabel(); 
        initLabel();
        frame.add(lvl);

        event = new JTextArea(); 
        initText(event);
        frame.add(event);

        k[0].addActionListener(new ActionListener() {       
            public void actionPerformed(ActionEvent e) {
                if(gamestate == 1){
                    userArrayAddColor(Color.BLUE);
                    checkArrays();
                }
            
        }});

        k[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(gamestate == 1){
                    userArrayAddColor(Color.GREEN);
                    checkArrays();
                }
        }});

        k[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(gamestate == 1){
                    userArrayAddColor(Color.RED);
                    checkArrays();
                }
        }});

        k[3].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(gamestate == 1){
                    userArrayAddColor(Color.YELLOW);
                    checkArrays();
                }
            
        }});

        k[4].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(gamestate == 0){
                    gameArray = getColorArray();    
                    userArray = new Color [1];      
                    userArray[0] = Color.WHITE;
                    lvl.setText("Level "+gamelvl);      
                    event.setText("Level "+gamelvl+" startet");
                    showGameArray();
                }
            
        }});

        k[5].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }});
            
        frame.setVisible(true);
    }

    public static Color [] getColorArray(){ 
        int tmp;
        Color [] farben = new Color [gamelvl + 2];
        for(int i = 0;i<farben.length;i++){
            tmp = (int) (Math.random()*100.0 ); 
            if(tmp > 75)                        
                farben[i] = Color.BLUE; 
            else if(tmp > 50){
                farben[i] = Color.GREEN; 
            }
            else if(tmp > 25){
                farben[i] = Color.RED; 
            }
            else {
                farben[i] = Color.YELLOW; 
            }
        }
        return farben;
    }

    public static void showGameArray(){  
        count = 0;

        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) { 
                if(count < gameArray.length){
                    k[4].setBackground(gameArray[count]);
                    k[4].setText(Integer.toString(count+1));
                    count++;   
                }  
                else{
                    timer.stop();
                    k[4].setBackground(Color.LIGHT_GRAY);
                    k[4].setText("Start");
                    gamestate = 1;
                } 
            }}; 

        timer = new Timer(1200,taskPerformer);
        timer.setInitialDelay(1200);     
        timer.start();   
    }

    public static void checkArrays(){
        boolean r = true;
        for(int i = 0;i<userArray.length;i++){
            if(userArray[i] != gameArray[i]){
                r = false;
                event.setText(farbeToString(userArray[i]) + " ist Falsch\n"+
                              farbeToString(gameArray[i]) + " wäre Richtig\n"+
                                            "Level verloren!" );
                if(gamelvl != 1)
                    gamelvl--;
                    lvl.setText("Level "+gamelvl);
                gamestate = 0;
            }    
        }
        if(r && (userArray.length == gameArray.length) ){
            event.setText(farbeToString(userArray[userArray.length-1]) + " ist Richtig\nLevel Gewonnen!");
            gamelvl++;
            lvl.setText("Level "+gamelvl);
            gamestate = 0; 
            return;
        }
        if(r){
            event.setText(farbeToString(userArray[userArray.length-1]) + " ist Richtig"); 
        }
    }

    public static String farbeToString(Color g){
        if(g == Color.BLUE)
            return "Blau";
        if(g == Color.GREEN)
            return "Grün";
        if(g == Color.RED)
            return "Rot";
        return "Gelb";    
    }

    public static void userArrayAddColor(Color g){
        if(userArray[0] == Color.WHITE){
            userArray[0] = g;
        }
        else{
            Color [] tmp = new Color[userArray.length +1];
            for(int i = 0;i<userArray.length;i++){
                tmp[i] = userArray[i];
            }
            tmp[tmp.length-1] = g;
            userArray = tmp;
        }
    }

    public static void initFrame(JFrame frame){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
    }

    public static void initButtons(){
        int x = 0;
        for(int i = 0;i<k.length-1;i++){
            k[i].setBounds(x,0,200,200);
            x += 200;
        }
        k[4].setBounds(400,200,400,200);
        k[4].setText("Start");
        k[4].setFont(new Font("TimesRoman",Font.PLAIN, 55));
        k[0].setBackground(Color.BLUE);
        k[1].setBackground(Color.GREEN);
        k[2].setBackground(Color.RED);
        k[3].setBackground(Color.YELLOW);
        k[4].setBackground(Color.LIGHT_GRAY);
        k[5].setBounds(400,400,400,60);
        k[5].setBackground(Color.LIGHT_GRAY);
        k[5].setText("X");
        k[5].setFont(new Font("TimesRoman",Font.PLAIN, 55));
    }

    public static void initLabel(){
        lvl.setBounds(50,200,200,150);
        lvl.setFont(new Font("TimesRoman",Font.PLAIN, 55));
    }

    public static void initText(JTextArea event){
        event.setBounds(50,320,200,100);
        event.setFont(new Font("TimesRoman",Font.PLAIN, 22));
    }
}
