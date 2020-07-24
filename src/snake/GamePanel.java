package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * @Author 翁煌
 * @Date2020/7/23 002317:20
 * @Description:
 * @Version1.0
 */
//游戏的面板
public class GamePanel extends JPanel implements KeyListener, ActionListener {

    //定义蛇的数据结构
    int lenth;  //蛇的长度
    int[] snakeX=new int[600];//蛇的坐标x
    int[] snakeY=new int[600];//蛇的坐标y
    String fx;  //舌头的方向  R:右  L:左  U:上  D:下
    boolean isStart = false; //游戏是否开始
    Timer timer = new Timer(100, this); //定时器：第一个参数，就是定时执行时间

    //食物
    int foodx;
    int foody;
    Random random = new Random();

    boolean isFail=false;//游戏是否结束

    int score; //游戏分数！

    public GamePanel() {
        init();
        fx="R";
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();
    }

    //初始化方法
    public void init(){
        lenth=3; //初始小蛇有三节,包括小脑袋
        snakeX[0]=100;snakeY[0]=100;//小脑袋
        snakeX[1]=75;snakeY[1]=100; //第一个身体坐标
        snakeX[2]=50;snakeY[2]=100;//第二个身体坐标

        //初始化食物数据
        foodx=25+25*random.nextInt(34);
        foody=25+25*random.nextInt(24);
        score=0;//初始化游戏分数
    }

    //frame.setBounds(10,10,900,720);
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //清屏
        //绘制静态的面板
        this.setBackground(Color.white);
        //头部广告栏画上去
        Data.header.paintIcon(this,g,25,11);
        //默认的游戏界面
        g.fillRect(25,70,850,600);

        //画食物
        Data.food.paintIcon(this,g,foodx,foody);

        //把小蛇画上去
        if (fx.equals("R")){ //蛇的头通过方向变量来判断
            Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if (fx.equals("L")){
            Data.left.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if (fx.equals("U")){
            Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if (fx.equals("D")){
            Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }

        //画身体
        for (int i = 1; i < lenth; i++) {
            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]); //蛇的身体长度根据lenth来控制
        }

        g.setColor(Color.white);
        g.setFont(new Font("微软雅黑",Font.BOLD,18));
        g.drawString("长度 " + lenth,750,35);
        g.drawString("分数 " + score,750,50);

        //游戏状态
        if (isStart==false){
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("按下空格开始游戏!",300,300);
        }

        if(isFail==true){
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("游戏失败，按下空格重新开始",200,300);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //键盘监听事件
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode(); //获取按下的键盘

        if (keyCode==KeyEvent.VK_SPACE){ //如果是空格
            if (isFail){ //如果游戏失败,从头再来！
                isFail = false;
                init(); //重新初始化
            }else { //否则，暂停游戏
                isStart = !isStart;
            }
            repaint();
        }

        //键盘控制走向
        if (keyCode==KeyEvent.VK_LEFT){
            fx = "L";
        }else if (keyCode==KeyEvent.VK_RIGHT){
            fx = "R";
        }else if (keyCode==KeyEvent.VK_UP){
            fx = "U";
        }else if (keyCode==KeyEvent.VK_DOWN){
            fx = "D";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //如果游戏处于开始状态，并且没有结束，则小蛇可以移动
        if (isStart&& isFail==false) {
            //右移:即让后一个移到前一个的位置即可 !
            for (int i = lenth - 1; i > 0; i--) { //除了脑袋都往前移：身体移动
                snakeX[i] = snakeX[i - 1]; //即第i节(后一节)的位置变为(i-1：前一节)节的位置！
                snakeY[i] = snakeY[i - 1];
            }
            //通过方向控制，头部移动
            if (fx.equals("R")){
                snakeX[0] = snakeX[0]+25;
                if (snakeX[0]>850) snakeX[0] = 25;
            }else if (fx.equals("L")){
                snakeX[0] = snakeX[0]-25;
                if (snakeX[0]<25) snakeX[0] = 850;
            }else if (fx.equals("U")){
                snakeY[0] = snakeY[0]-25;
                if (snakeY[0]<75) snakeY[0] = 650;
            }else if (fx.equals("D")){
                snakeY[0] = snakeY[0]+25;
                if (snakeY[0]>650) snakeY[0] = 75;
            }

            //吃食物:当蛇的头和食物一样时,算吃到食物!
            if (snakeX[0]==foodx && snakeY[0]==foody){
                //1.长度加一
                lenth++;
                score+=10;
                //2.重新随机生成食物
                foodx = 25 + 25* random.nextInt(34);
                foody = 75 + 25* random.nextInt(24);
            }
            //结束判断，头和身体撞到了
            for (int i = 1; i < lenth; i++) {
                if(snakeX[0]==snakeX[i] && snakeY[0]==snakeY[i]){
                    isFail=true;
                }
            }

            repaint();
        }
        timer.start();//让时间动起来!
    }
}
