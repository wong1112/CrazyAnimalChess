package eg_draw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage {

    static JFrame frame = new JFrame("疯狂斗兽棋");


    public static void  main(String[] args){


        frame.setSize(900,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setBackground(Color.gray);
        placeComponents(panel);

        frame.setVisible(true);

    }

    private static void placeComponents(JPanel panel){

        panel.setLayout(null);
        JLabel Lable = new JLabel("<html>CrazyAnimal<br>&emsp;&emsp;Chess<html>",JLabel.CENTER);
        Lable.setFont(new Font("Phosphate", 1, 70));
        Lable.setBounds(200,100,500,200);
        panel.add(Lable);

        JButton jButton_1 =new JButton("本地对战");
        jButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();//点击按钮时frame1销毁,new一个frame2
                new DrawBasic();
            }
        });//加入事件监听
        jButton_1.setBounds(500,400,100,50);
        panel.add(jButton_1);


        JButton jButton_2 = new JButton("联网对战");
        jButton_2.setBounds(650,400,100,50);
        jButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);//点击按钮时frame1销毁,new一个frame2
                new DrawBasic();
            }
        });//加入事件监听
        panel.add(jButton_2);
    }
}
