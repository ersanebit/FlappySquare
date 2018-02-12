package FlappySquarePackage;

import oracle.jrockit.jfr.JFR;
import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

import static jdk.nashorn.internal.runtime.Debug.id;


/**
 * Created by Ersan on 7/25/2017.
 */


public class FlappySquare implements ActionListener, MouseListener, KeyListener {

    public static FlappySquare flappySquare;

    //width and height of the window
    public final int WIDTH=1200, HEIGHT=800;

    public Renderer renderer;

    public Rectangle bird;

    public int ticks, yMotion, score;

    public ArrayList<Rectangle> columns;

    public Random rand;

    public boolean gameOver, started;

    public FlappySquare(){

        JFrame jframe = new JFrame();
        //Game speed
        Timer timer = new Timer(20, this);


        rand = new Random();
        renderer = new Renderer();

        jframe.add(renderer);
        jframe.setTitle("My first game in Java!");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.addMouseListener(this);
        jframe.addKeyListener(this);
        jframe.setResizable(false);
        jframe.setVisible(true);


        bird = new Rectangle(WIDTH / 2, HEIGHT / 2 - 10, 20, 20);
        columns = new ArrayList<Rectangle>();

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        timer.start();

    }

    public void jump(){
        if(gameOver){

            bird = new Rectangle(WIDTH / 2, HEIGHT / 2 - 10, 20, 20);
            columns.clear();
            yMotion = 0;
            score = 0;

            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);
            gameOver = false;
        }

        if(!started){
            started = true;
        }else if (!gameOver){

            if(yMotion >0){
                yMotion = 0;
            }
            yMotion -= 10;

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {


        ticks++;

        if(started) {


            int speed = 10;

            //for(create something; check somehting, do something)
            // get the elements form the arrayList
            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);
                column.x -= speed;

            }

            if (ticks % 2 == 0 && yMotion < 15) {
                // going down continuosly by 2 pixels
                yMotion += 2;
            }

            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);
                if (column.x + column.width < 0) {
                    columns.remove(column);

                    if (column.y == 0) {
                        addColumn(false);
                    }
                }

            }

            bird.y += yMotion;

            for (Rectangle column : columns) {
                if(column.y == 0 && bird.x + bird.width /2 > column.x + column.width / 2 - 10 && bird.x + bird.width/2 < column.x + column.width /2 + 10)
                {
                    score++;

                }
                if (column.intersects(bird)) {
                    gameOver = true;

                    //if the bird falls, it will hit the column and stay there
                    bird.x = column.x - bird.width;
                }
            }

            if (bird.y > HEIGHT - 120 || bird.y < 0) {

                gameOver = true;



            }
            if(bird.y + yMotion >= HEIGHT - 120  ) {
                bird.y = HEIGHT - 120 - bird.height;
            }
        }

        renderer.repaint();
    }


    public void addColumn(boolean start){
        int space = 300;
        int width = 100;
        int height = 50 +rand.nextInt(300);

        if(start){


            columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height -120, width, height));
            columns.add(new Rectangle(WIDTH + width + (columns.size() -1) * 300,0,width,HEIGHT-height- space));
        }else{
            columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height -120, width, height));
            columns.add(new Rectangle(columns.get(columns.size() - 1).x,0,width,HEIGHT-height- space));
        }
    }


    public void paintColumn(Graphics g, Rectangle column){
        g.setColor(Color.green.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }

    public void repaint(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH,HEIGHT);

        g.setColor(Color.orange);
        g.fillRect(0, HEIGHT - 120, WIDTH, 120);

        g.setColor(Color.green);
        g.fillRect(0, HEIGHT-120, WIDTH, 20);



        //g with bird`s width, height etc from line 30, where bird = rectangle
        g.setColor(Color.red);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        for(Rectangle column: columns){
            paintColumn(g,column);
        }

        //"game over" written in white
        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 100));


        if(!started){
            g.drawString("Click to start!", 75, HEIGHT /2 - 50);
            g.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g.drawString("Press spacebar or click to jump!", 100, HEIGHT /2 + 100);
        }

        if(gameOver){
            g.drawString("Game over", 300, HEIGHT /2 - 50);
        }

        if(!gameOver && started){
            g.drawString(String.valueOf(score), WIDTH/ 2 -25, 100);
        }
    }



    public static void main(String[] args) {
        flappySquare = new FlappySquare();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        jump();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_SPACE){
            jump();
        }
    }
}