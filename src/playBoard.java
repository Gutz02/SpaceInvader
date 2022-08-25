import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;


public class playBoard extends JFrame implements KeyListener {

    JPanel panel;
    JLabel shipPic;

    int [][] arr;

    int x = 0;
    int y= 0;

    public Map<String, int[][] > xyLocation = new HashMap<>();

    enemies inverShips = new enemies(this);


    public playBoard() throws IOException {
        BufferedImage myShip = ImageIO.read(new File("SpaceShip.png"));

        this.setLayout(null);

        shipPic = new JLabel(new ImageIcon(myShip));
        shipPic.setBounds(x,600,60,60);

        setTitle("SpaceInvader");

        int [][]  arr = { {0,0} };
        xyLocation.put("User",arr);

        addKeyListener(this);
        add(shipPic);

        setSize(700,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }


    public static void main(String[] args) throws IOException {
        playBoard icon = new playBoard();
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

        switch (ch){
            case 'd':
                if (x + 50 > 625) {
                    break;
                }
                else {
                    x = x + 50;
                    int [][] arr = {{x,0}};
                    xyLocation.replace("User",arr);
                    shipPic.setBounds(x,600,60,60);
                    revalidate();
                }
                break;
            case 'a':
                if (x == 0){
                    break;
                }
                else{
                    x = x - 50;
                    int [][] arr = {{x,0}};
                    xyLocation.replace("User",arr);
                    shipPic.setBounds(x,600,60,60);
                    revalidate();
                }
                break;
            case ' ':
                int [] userPosition = xyLocation.get("User")[0];

                try {
                    movingBullet(userPosition);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            default:
                break;
        }
        System.out.println(x);
    }

    public void movingBullet(int[] element) throws IOException {
        JLabel bulletPic;

        BufferedImage bullet = ImageIO.read(new File("ammo.png"));
        bulletPic = new JLabel(new ImageIcon(bullet));
        bulletPic.setBounds(element[0],550,bullet.getWidth(),bullet.getWidth());
        add(bulletPic);

        revalidate();
    }

}


class enemies extends Thread {

    playBoard currentInstance;
    Data data;

    public enemies(playBoard playBoard) {
        currentInstance = playBoard;
    }

    @Override
    public void run() {
        System.out.println("Thread enemies Started");
    }

}
