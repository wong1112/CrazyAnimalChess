package eg_draw;
import javax.swing.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.HashSet;
import java.awt.*;
public class chesspiece extends JButton implements Serializable {
	public String type;
	public String team;
	public String name;
	public Point point;
	public HashSet<Point> availPoint;
	public int attack;
	ImageIcon ima;
	chesspiece(String path,String type,String team,Point point,int attack)
	{
		ima = new ImageIcon(path);
		this.type = type;
		this.team = team;
		this.point = point;
		this.attack = attack;
		name = team + type;
		availPoint = new HashSet<Point>();        //将当前棋子可走的点都保存在该集合中
		super.setIcon(ima);
		super.setSize(ima.getIconWidth(), ima.getIconHeight());
//		super.setFocusPainted(true);           //设置JButton绘制焦点边框
		super.setBorder(null);                 //设置JButton无边框
		super.setContentAreaFilled(false);     //设置JButton背景透明
	}
}

