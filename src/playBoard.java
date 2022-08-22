import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class playBoard extends JFrame implements KeyListener {

    JPanel panel;
    JLabel shipPic;


    public int x;
    public int y;

    public void setX(int num){
        x = num;
    }

    public playBoard() throws IOException {

        BufferedImage myShip = ImageIO.read(new File("SpaceShip.png"));


        panel = new JPanel();

        shipPic = new JLabel(new ImageIcon(myShip));
        shipPic.setBorder(new EmptyBorder(580, x,0, y));

        setTitle("SpaceInvader");

        panel.add(shipPic);
        add(panel);

        addKeyListener(this);
        setSize(700,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }


    public static void main(String[] args) throws IOException {
        //playBoard icon = new playBoard();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e){
        char ch = e.getKeyChar();

        System.out.println(e.getKeyCode());

        switch (ch){
            case 'd':
                if (x == 600) {
                    break;
                }
                if (y == 0){
                    x += 100;
                    shipPic.setBorder(new EmptyBorder(580, x,0,y));
                }
                else {
                    y = y - 100;
                    shipPic.setBorder(new EmptyBorder(580, x,0,y));
                }
                revalidate();
                break;
            case 'a':
                if (y == 600){
                    break;
                }
                if (x == 0){
                    y = y + 100;
                    shipPic.setBorder(new EmptyBorder(580, x,0,y));
                }
                else {
                    x = x - 100;
                    shipPic.setBorder(new EmptyBorder(580, x,0,y));
                }
                revalidate();
                break;
            default:
                break;
        }
        System.out.println(x);

    }



}
