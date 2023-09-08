import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class gamepanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE=25;
    static final int GAME_UNITS=(SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
    static final int DELAY=75;
    final int x[]= new int[GAME_UNITS];
    final int y[]= new int[GAME_UNITS];
    int bodyParts=6; 
    int applesEaten;
    int appleX;
    int appleY;
    char direction ='R';
    boolean running =false;
    Timer timer;
    Random random;

    gamepanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        StartGame();


    
    }

    public void StartGame(){
        newappel();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running){

            for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                g.drawLine(i*UNIT_SIZE,0, i*UNIT_SIZE, i*SCREEN_HEIGHT);
                g.drawLine(0,i*UNIT_SIZE,i*SCREEN_WIDTH , i*UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(appleX,appleY, UNIT_SIZE, UNIT_SIZE);
            for(int i= 0;i<bodyParts;i++){
                if(i==0){
                    g.setColor(Color.green);
                    g.fillRect
                    (x[i],y[i], UNIT_SIZE,UNIT_SIZE);
                }
                else{
                    g.setColor(new Color(45,180,0));
                    g.fillRect(x[i],y[i], UNIT_SIZE,UNIT_SIZE);
                }
            }
            //score
             g.setColor(Color.red);
             g.setFont(new Font("Ink free",Font.BOLD,20));
             FontMetrics metrics = getFontMetrics(g.getFont());
             g.drawString("Score: "+applesEaten,(SCREEN_WIDTH- metrics.stringWidth("Score: "+applesEaten))-20,15);

        }
        else{
            GameOver(g);
        }
    }
    public void newappel(){
        appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
         for (int i=bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
         }
         
         switch(direction){
            case 'U' :
            y[0] = y[0]- UNIT_SIZE; 
            break;
            case 'D' :
            y[0]=y[0]+UNIT_SIZE;
            break; 
            case 'L' :
            x[0]=x[0]-UNIT_SIZE;
            break; 
            case 'R' :
            x[0]=x[0]+UNIT_SIZE;
            break;
        }
    }
    public void CheckApple(){
        if((x[0]==appleX)&&(y[0]==appleY)){
            bodyParts++;
            applesEaten++;
            newappel();
        }

    }
    public void CheckColisions(){
        //with body
        for(int i =bodyParts;i>0;i--){
            if((x[0]==x[i])&&(y[0]==y[i])){
                running=false;
            }
        }
        //with left border
        if(x[0]<0){
            running=false;
        }
        //with right border
        if(x[0]>SCREEN_WIDTH){
            running=false;
        }
        //with top border
        if (y[0]<0){
            running=false;
        }
        //with botom
        if (y[0]>SCREEN_HEIGHT){
            running=false;
        }
        if(!running){
            timer.stop();
        }

    }
    public void GameOver(Graphics g){
        //gameover text
        g.setColor(Color.red);
        g.setFont(new Font("Ink free",Font.BOLD,70));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER",(SCREEN_WIDTH- metrics.stringWidth("GAME OVER"))/2,SCREEN_HEIGHT/2);
        //score
          g.setColor(Color.red);
          g.setFont(new Font("Ink free",Font.BOLD,40));
          FontMetrics metrics2 = getFontMetrics(g.getFont());
          g.drawString("Score: "+applesEaten,((SCREEN_WIDTH- metrics.stringWidth("Score: "+applesEaten))/2)+70,(SCREEN_HEIGHT/2)+60);

          
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            CheckApple();
            CheckColisions();
        }
        repaint();
        
    }
    
    public class MyKeyAdapter extends KeyAdapter {
       
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                if(direction!='R'){
                    direction = 'L';
                }
                break ;
                case KeyEvent.VK_RIGHT:
                if(direction!='L'){
                    direction = 'R';
                }
                break ;
                case KeyEvent.VK_UP:
                if(direction!='D'){
                    direction = 'U';
                }
                break ;
                case KeyEvent.VK_DOWN:
                if(direction!='U'){
                    direction = 'D';
                }
                break ;
            }
        }
    }
}