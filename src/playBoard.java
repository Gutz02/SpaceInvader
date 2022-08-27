import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class playBoard extends JFrame implements KeyListener {

    JPanel panel;
    JLabel shipPic;
    JLabel bulletPic;

    JLabel tempBullet;


    int x = 0;
    int y= 0;

    public Map<String, ArrayList<ArrayList<Integer>> > xyLocation = new HashMap<>();
    public ArrayList<Object> bulletList = new ArrayList<>();

    enemies inverShips = new enemies(this);



    public playBoard() throws IOException, InterruptedException {
        BufferedImage myShip = ImageIO.read(new File("SpaceShip.png"));

        panel = new JPanel();

        this.setLayout(null);

        shipPic = new JLabel(new ImageIcon(myShip));
        shipPic.setBounds(x,600,60,60);

        setTitle("SpaceInvader");

        ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer> >();
        arr.add(new ArrayList<Integer>());
        arr.get(0).add(0,0);
        arr.get(0).add(1,600);
        xyLocation.put("User",arr);

        addKeyListener(this);
        this.add(shipPic);

        setSize(700,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        movingBullet();
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        playBoard icon = new playBoard();
    }

    synchronized public void addStats(JLabel image, int x, int y, int width, int height){
        image.setBounds(x,y,width,height);
        add(image);
    }


    public void movingBullet() throws InterruptedException {
        boolean element = true;

        while (element){
            if (xyLocation.keySet().contains("Bullet")){
                for (int i = 0; i < bulletList.size() ; i++){
                    tempBullet = (JLabel) bulletList.get(i);
                    int x = xyLocation.get("Bullet").get(i).get(0);
                    int y = xyLocation.get("Bullet").get(i).get(1);

                    if (y > 0 ){
                        System.out.println(y);
                        addStats(tempBullet,x,y-50,60,60);
                        xyLocation.get("Bullet").get(i).set(1,y-50);
                    }

                    else {
                        remove(tempBullet);
                    }

                }
                Thread.sleep(1000);
            }
            invalidate();
            validate();
            repaint();
        }


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
                    ArrayList<ArrayList<Integer>> arr = xyLocation.get("User");
                    arr.get(0).set(0,x);
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
                    ArrayList<ArrayList<Integer>> arr = xyLocation.get("User");
                    arr.get(0).set(0,x);
                    xyLocation.replace("User",arr);
                    shipPic.setBounds(x,600,60,60);
                    revalidate();
                }
                break;
            case ' ':
                try {
                    addBullet(xyLocation.get("User").get(0));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            default:
                break;
        }
        System.out.println(x);
    }

    public void addBullet(ArrayList<Integer> userPosition) throws IOException {
        ArrayList<ArrayList> arrArr = new ArrayList<ArrayList>();
        ArrayList<Integer> arr = new ArrayList();

        if (xyLocation.get("Bullet") == null){
            arr.add(0,userPosition.get(0));
            arr.add(1, 550);
            arrArr.add(new ArrayList());
            arrArr.get(0).add(0,arr);

            xyLocation.put("Bullet",arrArr.get(0));
        }
        else {
            arr.add(0,userPosition.get(0));
            arr.add(1,550);
            xyLocation.get("Bullet").add(xyLocation.get("Bullet").size(),arr);
        }

        BufferedImage bullet = ImageIO.read(new File("ammo.png"));
        bulletPic = new JLabel(new ImageIcon(bullet));
        bulletPic.setBounds(userPosition.get(0),550,60,60);
        bulletList.add(bulletPic);

        add(bulletPic);

        validate();
        repaint();
    }

}



class enemies extends Thread {

    playBoard currentInstance;
    JLabel ufoShip;
    boolean element = false;
    List position = (List) Arrays.asList(new int[]{50,100,150,200,250,300,350,400,450,500,550,600});

    public enemies(playBoard playBoard) {
        this.currentInstance = playBoard;
    }

    @Override
    public void run() throws RuntimeException  {
        Random rand = new Random();
        int timeToStart = rand.nextInt(11)*1000;
        try {
            Thread.sleep(timeToStart);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            BufferedImage ufo = ImageIO.read(new File("UFO.png"));

            ufoShip = new JLabel(new ImageIcon(ufo));

            while(element){
                int index = rand.nextInt(12);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }





    }

}
