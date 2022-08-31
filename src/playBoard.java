import javax.imageio.ImageIO;
import javax.swing.*;
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
    public ArrayList<Object> enemiesList = new ArrayList<>();

    enemies inverShips = new enemies(this);
    convergeChecker checker = new convergeChecker(this);



    public playBoard() throws IOException, InterruptedException {
        inverShips.start();
        checker.start();

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

        invalidate();
        validate();
        repaint();
    }


    public void movingBullet() {
        boolean element = true;

        while (element){
            try{
                if (getXyLocation().keySet().contains("Bullet")) {
                    for (int i = 0; i < getBulletList().size(); i++) {
                        tempBullet = (JLabel) getBulletList().get(i);
                        System.out.println("Bullet list size is " + getBulletList().size());
                        System.out.println("Index is "+i);

                        int x;
                        int y;

                        try{
                            x = getXyLocation().get("Bullet").get(i).get(0);
                            y = getXyLocation().get("Bullet").get(i).get(1);
                        }catch (IndexOutOfBoundsException e){
                            if (i-1 <0){
                                break;
                            }else {
                                x = getXyLocation().get("Bullet").get(i-1).get(0);
                                y = getXyLocation().get("Bullet").get(i-1).get(1);
                            }
                        }


                        if (y > 0) {
                            System.out.println(y);
                            addStats(tempBullet, x, y - 50, 60, 60);
                            getXyLocation().get("Bullet").get(i).set(1, y - 50);
                        } else {
                            System.out.println("Bullet map size"+getXyLocation().get("Bullet").size());
                            remove(tempBullet);
                        }

                    }

                }   Thread.sleep(1000);
            }catch (InterruptedException e){
                System.out.println("Interruption is caught");
            }
        }

    }


    public void addBullet(ArrayList<Integer> userPosition) throws IOException, InterruptedException {
        ArrayList<ArrayList<ArrayList<Integer>>> arrArr = new ArrayList<>();
        ArrayList<Integer> arr = new ArrayList<>();

        if (getXyLocation().get("Bullet") == null){
            arr.add(0,userPosition.get(0));
            arr.add(1, 550);
            arrArr.add(new ArrayList<>());
            arrArr.get(0).add(0,arr);

            getXyLocation().put("Bullet",arrArr.get(0));
        }
        else {
            arr.add(0,userPosition.get(0));
            arr.add(1,550);
            getXyLocation().get("Bullet").add(getXyLocation().get("Bullet").size(),arr);
        }

        BufferedImage bullet = ImageIO.read(new File("ammo.png"));
        bulletPic = new JLabel(new ImageIcon(bullet));
        bulletPic.setBounds(userPosition.get(0),550,60,60);
        getBulletList().add(bulletPic);

        add(bulletPic);

        validate();
        repaint();
    }

    public void removeImage(ArrayList<Integer> listToRemove){
        Integer bulletIndex = getXyLocation().get("Bullet").indexOf(listToRemove);
        Integer enemiesIndex = getXyLocation().get("Enemies").indexOf(listToRemove);

        System.out.println(bulletIndex);
        System.out.println(enemiesIndex);

        if (bulletIndex != -1 ){
            JLabel label = (JLabel) getBulletList().get(bulletIndex);
            getBulletList().remove(bulletIndex);
            remove(label);
        }
        else {
            JLabel label = (JLabel) enemiesList.get(enemiesIndex);
            enemiesList.remove(enemiesIndex);
            remove(label);
        }

    }

    synchronized public Map<String, ArrayList<ArrayList<Integer>>> getXyLocation(){
        return xyLocation;
    }
    
    synchronized public ArrayList<Object> getBulletList(){
        return bulletList;
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
                    ArrayList<ArrayList<Integer>> arr = getXyLocation().get("User");
                    arr.get(0).set(0,x);
                    getXyLocation().replace("User",arr);
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
                    ArrayList<ArrayList<Integer>> arr = getXyLocation().get("User");
                    arr.get(0).set(0,x);
                    getXyLocation().replace("User",arr);
                    shipPic.setBounds(x,600,60,60);
                    revalidate();
                }
                break;
            case ' ':
                try {
                    addBullet(getXyLocation().get("User").get(0));
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            default:
                break;
        }
        System.out.println(x);
    }

}



class enemies extends Thread {

    playBoard currentInstance;
    JLabel ufoShip;
    boolean element = false;

    int[] position = new int[]{50,100,150,200,250,300,350,400,450,500,550,600};
    ArrayList<ArrayList<Integer>> enemiesLocation;
    ArrayList<Integer> xyShipLocation;

    public enemies(playBoard playBoard) {
        this.currentInstance = playBoard;
        enemiesLocation = new ArrayList<ArrayList<Integer>>();
    }

    @Override
    public void run() throws RuntimeException  {
        Random rand = new Random();
        int timeToStart = rand.nextInt(10)*1000;
        try {
            Thread.sleep(timeToStart);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Thread enemies is running");
            BufferedImage ufo = ImageIO.read(new File("UFO.png"));


            currentInstance.getXyLocation().put("Enemies",enemiesLocation);

            while (!element){
                xyShipLocation = new ArrayList<>();
                ufoShip = new JLabel(new ImageIcon(ufo));

                int index = rand.nextInt(12);
                System.out.println("UFO x-axis ="+index);

                xyShipLocation.add(position[index]);
                xyShipLocation.add(50);

                currentInstance.getXyLocation().get("Enemies").add(xyShipLocation);

                currentInstance.addStats(ufoShip,position[index],50,60,60 );
                currentInstance.enemiesList.add(ufoShip);
                Thread.sleep(2000);
            }



        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }


    }

}

class convergeChecker extends Thread{

    playBoard currentInstance;



    public convergeChecker(playBoard PlayBoard){
        this.currentInstance = PlayBoard;
    }

    @Override
    public void run(){
        System.out.println("Checker Thread Started");
        boolean element = false;

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while (!element){
            try{
                if (currentInstance.getXyLocation().containsKey("Bullet") && currentInstance.getXyLocation().get("Bullet").size() > 0 ){
                    for (ArrayList<Integer> enemiesPosition : currentInstance.getXyLocation().get("Enemies")){
                        for (ArrayList<Integer> bulletPosition : currentInstance.getXyLocation().get("Bullet")){
                            if (Objects.equals(enemiesPosition.get(1), bulletPosition.get(1))){


                                System.out.println("Posistion = "+enemiesPosition.get(1));
                                System.out.println("Posistion = "+bulletPosition.get(1));

                                currentInstance.removeImage(enemiesPosition);
                                currentInstance.removeImage(bulletPosition);

                                System.out.println("getBulletList() size "+currentInstance.getBulletList().size());

                                currentInstance.getXyLocation().get("Enemies").remove(enemiesPosition);
                                currentInstance.getXyLocation().get("Bullet").remove(bulletPosition);
                                System.out.println("Checker thread, bulletsize is "+currentInstance.getXyLocation().get("Bullet").size() );

                            }
                        }
                    }
                }
            }catch (ConcurrentModificationException | NullPointerException e){
                System.out.println(e.getMessage());
            }

        }


    }




}
