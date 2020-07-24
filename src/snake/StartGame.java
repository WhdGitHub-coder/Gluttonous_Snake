package snake;

import javax.swing.*;

/**
 * @Author 翁煌
 * @Date2020/7/23 002316:47
 * @Description:
 * @Version1.0
 */
public class StartGame {
    public static void main(String[] args) {
        JFrame frame=new JFrame("贪吃蛇");

        frame.setBounds(10,10,900,720);
        frame.add(new GamePanel());

        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
