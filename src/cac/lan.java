package cac;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class lan extends JFrame{
    static final String team[] = {"红","蓝"};
    static final String trap[] = {"红陷阱","蓝陷阱"};
    static final String type[] = {"象","狮","虎","豹","狼","狗","猫","鼠"};
    static final String mode[] = {"人机对战","人人对战"};
    private int difficulty;
    private int switchListener;      //鼠标监听器的模式，不同switchlistener值对应不同的监听器功能
    private HashSet<chesspiece> deathChess;   //已死亡的棋子集合
    private HashSet<chesspiece> redliveChess;    //依然生还的红色方棋子集合
    private HashSet<chesspiece> blueliveChess;   //依然生还的蓝色方棋子集合
    private chesspiece curChess;              //当前选中的棋子
    private chesspiece AIchess;
    private Point AIpoint;
    public int cnt;                   //记录当前走棋的队伍
    public  String str;
    private String curmode;       //当前游戏模式
    JMenuBar menuBar;
    JMenu menu,pvc,help;
    JMenuItem pvp,undo,quit,explain,about;
    JLabel jb,jc,jd;
    Point point[][] = new Point[11][9];    //棋盘为9*7， 横纵各多加2为方便判定是否出界
    chesspiece chess[] = new chesspiece[16];
    JLabel redWin,blueWin;
    JLabel conside;
    JTextArea jta=null;
    JTextArea jta1=null;
    JPanel panelOutput;
    JPanel panelOutput1;
    JLabel playingnow;
    JLabel playingnow1;
    JButton button1,button2;
    JList list1 = null;
    DefaultListModel listModel;

    class undoUnit           //撤销一次移动操作的数据单元
    {
        public chesspiece movechess, deadchess;   //在一次移动操作中移动的棋子，死去的棋子以及目标移动点，用于撤销一次移动操作
        public Point frompoint,topoint;
        public undoUnit next;
    }
    undoUnit head;
    class menuListener implements ActionListener
    {
        JFrame tmp;
        menuListener(JFrame tmp)
        {
            this.tmp = tmp;
        }
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == pvp)
            {
                clearchess();
                switchListener = 0;
                curmode = mode[1];
                difficulty = 3;
                chessInit();
            }
            if (e.getSource() == undo)
            {
                if (curmode == mode[1])
                {
                    undo(head);
                    head.movechess.setLocation(head.frompoint.y1+50-(head.movechess.ima.getIconWidth())/2,
                            head.frompoint.x1+50-(head.movechess.ima.getIconHeight())/2);
                    jd.setVisible(false);
                    if (head.deadchess != null)
                    {
                        head.deadchess.setLocation(head.topoint.y1+50-(head.deadchess.ima.getIconWidth())/2,
                                head.topoint.x1+50-(head.deadchess.ima.getIconHeight())/2);
                        tmp.getContentPane().add(head.deadchess);
                        head.deadchess.setVisible(true);
                    }
                    jc.setLocation(head.frompoint.y1, head.frompoint.x1);
                    curChess = head.movechess;
                    head = head.next;
                    undo.setEnabled(false);
                }
                if (curmode == mode[0])
                {
                    undo(head);
                    head.movechess.setLocation(head.frompoint.y1+50-(head.movechess.ima.getIconWidth())/2,
                            head.frompoint.x1+50-(head.movechess.ima.getIconHeight())/2);
                    jd.setVisible(false);
                    if (head.deadchess != null)
                    {
                        head.deadchess.setLocation(head.topoint.y1+50-(head.deadchess.ima.getIconWidth())/2,
                                head.topoint.x1+50-(head.deadchess.ima.getIconHeight())/2);
                        tmp.getContentPane().add(head.deadchess);
                        head.deadchess.setVisible(true);
                    }
                    head = head.next;
                    undo(head);
                    head.movechess.setLocation(head.frompoint.y1+50-(head.movechess.ima.getIconWidth())/2,
                            head.frompoint.x1+50-(head.movechess.ima.getIconHeight())/2);
                    if (head.deadchess != null)
                    {
                        head.deadchess.setLocation(head.topoint.y1+50-(head.deadchess.ima.getIconWidth())/2,
                                head.topoint.x1+50-(head.deadchess.ima.getIconHeight())/2);
                        tmp.getContentPane().add(head.deadchess);
                        head.deadchess.setVisible(true);
                    }
                    jc.setLocation(head.frompoint.y1, head.frompoint.x1);
                    curChess = head.movechess;
                    head = head.next;
                    undo.setEnabled(false);
                }
            }
            if (e.getSource() == quit)
            {
                tmp.dispose();
                MainPage.frame.setVisible(true);

            }
            if (e.getSource() == explain)
            {
                rule r = new rule(750,580,"游戏规则.txt");
                r.setVisible(true);
            }
            if (e.getSource() == about)
            {
                rule r = new rule(450,200,"关于.txt");
                r.setVisible(true);
            }
        }
    }
    lan()
    {
        super.setTitle("斗兽棋");
        this.setLocation(580,200);

        this.setLayout(null);
        backgroundInit();

//		chessInit();
        switchListener = 1;
        this.setSize(996+550, 993);
        ((JPanel)this.getContentPane()).setOpaque(false);  //设置内容面板背景透明
        this.addMouseListener(new FrameMouseListener(this));
        this.setVisible(true);
        this.setResizable(false);         //设置窗口大小不可改变
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



    }

    void clearchess()
    {
        for (int i = 0;i < 16; i++)
            if (chess[i] != null)
                this.getContentPane().remove(chess[i]);
        jd.setVisible(false);
        jc.setVisible(false);
    }
    void connect(chesspiece chess,Point point)
    {
        chess.point = point;
        point.chess = chess;
    }

    void chessInit()         //棋子和棋盘坐标初始化的方法
    {
        cnt = 0;
        curChess = null;
        for (int i = 1; i < 10; i++)         //为棋盘上的点赋初值
        {
            for (int j = 1; j < 8; j++)

            {
                if ((i >= 4 && i<= 6) && ((j >= 2 && j <= 3)||(j >= 5 && j <=6)))
                    point[i][j] = new Point((i-1)*100,(j-1)*100,null,"河",0);
                else
                    point[i][j] = new Point((i-1)*100,(j-1)*100,null,"路",0);
                point[i][j].j = j;
                point[i][j].i = i;
            }
        }
        for (int j = 0; j < 9; j++)       //为棋盘边框上的点赋初值,从这开始
        {
            point[0][j] = new Point(0,0,null,"空",0);
            point[10][j] = new Point(0,0,null,"空",0);
        }
        for (int i = 1; i < 10; i++)
        {
            point[i][0] = new Point(0,0,null,"空",0);
            point[i][8] = new Point(0,0,null,"空",0);
        }                                 //              在这结束

        for (int i = 0; i < 16; i++)      //为棋子赋初值
        {
            chess[i] = new chesspiece("icon/"+type[i%8]+"-"+team[i/8]+".png",type[i%8],team[i/8],null,800-(i%8)*100);
        }
        //棋盘上的点的价值
        int poValue[][] =
                {
                        {0, 0,  0,   0,   0,     0,   0,   0,  0},
                        {0, 50, 75,  200, 10000, 200, 75,  50, 0},
                        {0, 50, 75,  100, 200,   100, 75,  50, 0},
                        {0, 50, 75,  75,  100,   75,  75,  50, 0},
                        {0, 50, 50,  50,  50,    50,  50,  50, 0},
                        {0, 10, 25,  25,  10,    25,  25,  10, 0},
                        {0, 10, 25,  25,  10,    25,  25,  10, 0},
                        {0, 5,  5,   5,   5,     5,   5,   5,  0},
                        {0, 4,  4,   4,   4,     4,   4,   4,  0},
                        {0, 3,  3,   3,   3,     3,   3,   3,  0},
                        {0, 0,  0,   0,   0,     0,   0,   0,  0},
                };
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 9; j++)
            {
                point[i][j].poValue[0] = poValue[i][j];    //对于红色方来说点的价值
                point[i][j].poValue[1] = poValue[10-i][j]; //对于蓝色方来说点的价值，与红色方上下对称
            }
        point[1][3].deAttack = -800;      //某些特殊棋子
        point[1][3].road = trap[1];
        point[1][5].deAttack = -800;
        point[1][5].road = trap[1];
        point[2][4].deAttack = -800;
        point[2][4].road = trap[1];
        point[8][4].deAttack = -800;
        point[8][4].road = trap[0];
        point[9][3].deAttack = -800;
        point[9][3].road = trap[0];
        point[9][5].deAttack = -800;
        point[9][5].road = trap[0];
//		point[1][4].poValue = 10000;
        point[1][4].road = team[1];
//		point[9][4].poValue = 10000;
        point[9][4].road = team[0];
        connect(chess[0],point[7][1]);             //将棋子摆放在初始位置上
        connect(chess[1],point[9][7]);
        connect(chess[2],point[9][1]);
        connect(chess[3],point[7][5]);
        connect(chess[4],point[7][3]);
        connect(chess[5],point[8][6]);
        connect(chess[6],point[8][2]);
        connect(chess[7],point[7][7]);
        connect(chess[8],point[3][7]);
        connect(chess[9],point[1][1]);
        connect(chess[10],point[1][7]);
        connect(chess[11],point[3][3]);
        connect(chess[12],point[3][5]);
        connect(chess[13],point[2][2]);
        connect(chess[14],point[2][6]);
        connect(chess[15],point[3][1]);
        deathChess = new HashSet<chesspiece>();        //生还的棋子以及死亡的棋子集合初始化
        redliveChess = new HashSet<chesspiece>();
        blueliveChess = new HashSet<chesspiece>();
        for (int i = 0; i < 8; i++)
            redliveChess.add(chess[i]);
        for (int i = 8; i < 16; i++)
            blueliveChess.add(chess[i]);
        for (int i = 0; i < 16; i++)
        {
            this.getContentPane().add(chess[i]);
            chess[i].setLocation(chess[i].point.y1+50-(chess[i].ima.getIconWidth())/2, chess[i].point.x1+50-(chess[i].ima.getIconHeight())/2);
            chess[i].addMouseListener(new FrameMouseListener(this));
        }
        redWin.setVisible(false);
        blueWin.setVisible(false);
    }


    void backgroundInit()
    {
        Image title = new ImageIcon("icon/皮卡丘.jpg").getImage();
        this.setIconImage(title);
        ImageIcon back = new ImageIcon("icon/背景.jpg");
        jb = new JLabel(back);
        JLayeredPane pane = this.getLayeredPane();      //获得窗体的层面板
        pane.add(jb,new Integer(Integer.MIN_VALUE));
        jb.setBounds(0, 0, back.getIconWidth(), back.getIconHeight());
        ImageIcon choose = new ImageIcon("icon/选中框.png");
        jc = new JLabel(choose);
        this.getContentPane().add(jc);
        jc.setSize(choose.getIconWidth(), choose.getIconHeight());
        jc.setVisible(false);
        ImageIcon step = new ImageIcon("icon/脚印.png");
        jd = new JLabel(step);
        this.getContentPane().add(jd);
        jd.setSize(step.getIconWidth(), step.getIconHeight());
        jd.setVisible(false);
        ImageIcon redwin = new ImageIcon("icon/红色方胜利.png");
        redWin = new JLabel(redwin);
        this.getContentPane().add(redWin);
        redWin.setBounds(100,300,redwin.getIconWidth(),200);
        redWin.setVisible(false);
        ImageIcon bluewin = new ImageIcon("icon/蓝色方胜利.png");
        blueWin = new JLabel(bluewin);
        this.getContentPane().add(blueWin);
        blueWin.setBounds(100,300,redwin.getIconWidth(),200);
        blueWin.setVisible(false);
        ImageIcon consideIcon = new ImageIcon("icon/思考中-动.gif");
        conside = new JLabel(consideIcon);
        this.getContentPane().add(conside);
        conside.setBounds(100,300,consideIcon.getIconWidth(),200);
        conside.setVisible(false);
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        menu = new JMenu("开始");
        menuBar.add(menu);
        pvp = new JMenuItem("新的人人对战");
        pvp.addActionListener(new menuListener(this));
        menu.add(pvp);
        undo = new JMenuItem("悔棋");
        undo.addActionListener(new menuListener(this));
        undo.setEnabled(false);
        menu.add(undo);
        quit = new JMenuItem("返回主菜单");
        quit.addActionListener(new menuListener(this));
        menu.add(quit);
        help = new JMenu("帮助");
        menuBar.add(help);
        explain = new JMenuItem("游戏规则");
        explain.addActionListener(new menuListener(this));
        help.add(explain);
        about = new JMenuItem("关于");
        about.addActionListener(new menuListener(this));
        help.add(about);

        playingnow =new JLabel("玩家列表",JLabel.CENTER);
        playingnow.setFont(new Font("PingFang SC", 1, 20));
        playingnow.setBounds(1046,50,444,50);
        this.getContentPane().add(playingnow);

        jta =new JTextArea( str,20,30);
        JScrollPane scroll = new JScrollPane(jta);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jta.setEnabled(false);
        panelOutput = new JPanel();
        this.getContentPane().add(panelOutput);
        panelOutput.setBounds(1046,100,444,300);
        panelOutput.add(scroll);

        playingnow1 =new JLabel("正在进行局域网对战",JLabel.CENTER);
        playingnow1.setFont(new Font("PingFang SC", 1, 20));
        playingnow1.setBounds(1046,500,444,50);
        this.getContentPane().add(playingnow1);

        jta1 =new JTextArea( str,20,30);
        JScrollPane scroll1 = new JScrollPane(jta1);
        scroll1.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll1.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jta1.setEnabled(false);
        panelOutput1 = new JPanel();
        this.getContentPane().add(panelOutput1);
        panelOutput1.setBounds(1046,550,444,400);
        panelOutput1.add(scroll1);

        button1 =new JButton("挑战");
        button1.setBounds(1076,420,100,50);
        this.getContentPane().add(button1);

        button2 =new JButton("不玩了");
        button2.setBounds(1226,420,100,50);
        this.getContentPane().add(button2);










        String[] items = { "A", "B", "C", "D" };
        JList list = new JList(items);

        // Get the current selection model mode
        int mode = list.getSelectionMode(); // MULTIPLE_INTERVAL_SELECTION

        // Only one item can be selected
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);




    }
    public static void main(String args[])
    {
        new lan();
    }
    private static int toJ(int x)            //鼠标坐标点 与 point二维数组 的下标转换函数
    {
        return (x- Point.X_BORDER)/100 + 1;
    }
    private static int toI(int y)
    {
        return (y - 70)/100 + 1;       //鼠标坐标中Y值与Y_BORDER有偏差。。应该是鼠标坐标的Y值把窗体顶栏的宽度也算上了
    }
    private void clickAction(int x,int y,String mod)
    {
        int i = toI(y);
        int j = toJ(x);
//		System.out.println(point[i][j].chess.team + point[i][j].chess.type);
//		System.out.println(x + "  " + y);
//		System.out.println("当前点为point"+i+j);
        if (x < Point.X_BORDER || x > 7*100+Point.X_BORDER || y < 70 || y > 9*100+70)
        {
//			System.out.println("什么都没做");
        }
        else
        {
            if (curChess == null)            //当前没有选中棋子时，点击操作为选中棋子
            {
                //点击的不是空地并且是当前回合方时  选中棋子
                if (point[i][j].chess != null && point[i][j].chess.team == team[cnt%2])
                {
                    curChess = point[i][j].chess;
                    jc.setLocation(curChess.point.y1, curChess.point.x1);
                    jc.setVisible(true);
//						System.out.println("当前选中棋子为" + curChess.type);
                }
                else
                {
//						System.out.println("没有选中棋子!");
                }
            }
            else        //当前已经有选中的棋子时，点击操作为移动棋子或选中其它己方棋子
            {
                if (point[i][j].chess != null && point[i][j].chess.team == team[cnt%2])
                {
                    curChess = point[i][j].chess;
                    jc.setLocation(curChess.point.y1, curChess.point.x1);
                    jc.setVisible(true);
//						System.out.println("变更选中棋子为" + curChess.type);
                }
                else
                {
                    addAvailPoint_(curChess);
                    if (moveable(curChess,point[i][j]))      //如果可移动则移动，不可移动时什么都不做
                    {
                        chessPictureUpdate(curChess,point[i][j]);
                        undoUnit tmp = new undoUnit();
                        move(curChess,point[i][j],tmp);
                        addundoUnit(tmp);
                        curChess = null;
                        String result1 = winnable();  //移动完以后胜利检测
                        if (result1 == team[0])
                        {
                            conside.setVisible(false);
                            redWin.setVisible(true);
                            switchListener = 1;
                            return;
                        }else if (result1 == team[1])
                        {
                            conside.setVisible(false);
                            blueWin.setVisible(true);
                            switchListener = 1;
                            return;
                        }
                    }
                    else
                        System.out.println("不可移动！");
                }
            }
        }
    }
    private boolean moveable(chesspiece c,Point p)       //判断是否可移动,p为目的坐标
    {
        addAvailPoint_(c);
        if (c == null)
            return false;
        else
        {
            if (c.availPoint.contains(p)) //若目的坐标在可走的点的范围内
            {
                if (p.chess == null)    //若目的坐标上没有棋子，则可移动
                    return true;
                else                    //若目的坐标上有棋子
                {
                    if (p.chess.team == c.team)  //且目的坐标上的棋子为己方，则不可移动
                        return false;
                    else                      //该棋子为敌方时
                    {
                        if (c.point.road == trap[(cnt+1)%2]) //如果当前棋子在敌方的陷阱中，无法吃子
                            return false;
                        if (c.type == "象" && p.chess.type == "鼠")
                        {
                            if(p.road == trap[cnt%2])
                                return true;
                            else
                                return false;
                        }
                        if (c.point.road == "路" && p.road == "河")
                            return false;
                        if (c.point.road == "河" && p.road == "路")
                            return false;
                        if (c.type == "鼠" && p.chess.type == "象" && c.point.road == p.road)
                            return true;
                        if (c.attack >= p.chess.attack + (p.chess.team+"陷阱"==p.road?0:1)*p.deAttack)//如果棋子攻击力大于等于敌方，可以吃子
                            return true;
                        else
                            return false;
                    }
                }
            }
            else
                return false;
        }
    }
    private void addAvailPoint_(chesspiece c)
    {
        c.availPoint.clear();         //清空棋子c之前的可到达位置
        addAvailPoint(c.availPoint,c.type,c.point.i,c.point.j,0,1);
        addAvailPoint(c.availPoint,c.type,c.point.i,c.point.j,0,-1);
        addAvailPoint(c.availPoint,c.type,c.point.i,c.point.j,1,0);
        addAvailPoint(c.availPoint,c.type,c.point.i,c.point.j,-1,0);
    }
    //availPoint表示当前棋子可到达的坐标点集合
    //i,j表示当前棋子坐标，i_,j_表示相对于当前棋子坐标的偏移量，该方法调用四次，偏移量分别为（-1,0）（ +1,0） （0，-1）（ 0，+1）
    //即分别对当前点的上下左右点进行判断，若合适 就把它加入到availPoint集合中
    private void addAvailPoint(HashSet<Point> availPoint,String type,int i,int j,int i_,int j_)
    {
        if (point[i+i_][j+j_].road == "空")
            return;
        if (point[i+i_][j+j_].road == team[cnt%2])
            return;
        if (point[i+i_][j+j_].road == "河")
        {
            if (type == "鼠")
            {
//				System.out.print("("+(i+i_)+","+(j+j_)+")");
                availPoint.add(point[i+i_][j+j_]);
                return;
            }
            if (type == "狮" || type == "虎")
            {
                int cnt = 1;
                boolean flag = true;  //判断河中无鼠的标志
                while (point[i+i_*cnt][j+j_*cnt].road == "河")
                {
                    if (point[i+i_*cnt][j+j_*cnt].chess != null)
                        flag = false;  //此时河中有鼠
                    cnt++;
                }
                if (flag)
                {
//					System.out.print("("+(i+i_)+","+(j+j_)+")");
                    availPoint.add(point[i+i_*cnt][j+j_*cnt]);
                }
                return;
            }
        }
        if (point[i+i_][j+j_].road == (team[(cnt+1)%2]))
        {
            availPoint.add(point[i+i_][j+j_]);
            return;
        }
        if (point[i+i_][j+j_].road == "路" || point[i+i_][j+j_].road == trap[0]|| point[i+i_][j+j_].road == trap[1])
        {
//			System.out.print("("+(i+i_)+","+(j+j_)+")");
            availPoint.add(point[i+i_][j+j_]);
        }
    }
    private void chessPictureUpdate(chesspiece c, Point p)
    {

        if (p.chess == null)
        {
            jd.setLocation(c.point.y1,c.point.x1);   //脚步图标显示
            jd.setVisible(false);
            jd.setVisible(true);
            c.setLocation(p.y1+50-(c.ima.getIconWidth())/2, p.x1+50-(c.ima.getIconHeight())/2);

        }
        else
        {
            jd.setLocation(c.point.y1,c.point.x1);    //脚步图标显示
            jd.setVisible(false);
            jd.setVisible(true);
            p.chess.setVisible(false);
            this.getContentPane().remove(p.chess);    //棋子走动的图标移动  以及棋子中的point属性和点中的chess属性同时更新
            c.setLocation(p.y1+50-(c.ima.getIconWidth())/2, p.x1+50-(c.ima.getIconHeight())/2);
        }
//		jc.setVisible(false);    //选中棋子的图标框更新
        jc.setLocation(p.y1, p.x1);
    }
    private void move(chesspiece c,Point p,undoUnit u)     //移动操作一次
    {
        if (p.chess == null)
        {
            u.movechess = c;
            u.frompoint = c.point;
            u.topoint = p;
            u.deadchess = null;
            c.point.chess = null;            //棋子中的point属性和点中的chess属性同时更新
            c.point = p;
            p.chess = c;
        }
        else
        {
            u.movechess = c;
            u.frompoint = c.point;
            u.topoint = p;
            u.deadchess = p.chess;
            c.point.chess = null;
            deathChess.add(p.chess);                  //死亡棋子与生存棋子更新
            redliveChess.remove(p.chess);
            blueliveChess.remove(p.chess);
            c.point = p;
            p.chess = c;
        }
        cnt++;                   //回合转换
    }
    private String winnable()   //返回胜利方 若没有结束游戏则返回"无"
    {
        if (this.redliveChess.isEmpty() || point[9][4].chess != null)
            return team[1];
        if (this.blueliveChess.isEmpty() || point[1][4].chess != null)
            return team[0];
        return "无";
    }
    private int eveluation(int turn)    //棋面估值函数
    {
        int redSum = 0, blueSum = 0;
        int basicValue = 0;       //棋子基础价值
        int flexValue = 0;        //棋子灵活性价值
        for (Iterator<chesspiece> it1 = blueliveChess.iterator();it1.hasNext();)
        {
            chesspiece c = (chesspiece)it1.next();
            if (c == chess[15] && redliveChess.contains(chess[0]))
                basicValue += 400;
            basicValue += c.attack;
            flexValue += c.point.poValue[1];
            addAvailPoint_(c);
            HashSet<Point> availPoint = new HashSet<Point>(c.availPoint);
            for (Iterator<Point> it2 = availPoint.iterator(); it2.hasNext();)
            {
                Point p = it2.next();
                if (p.chess == null)
                {
                    flexValue += c.attack/6;
                }
                else if (p.chess.team == team[0])
                {
                    if (moveable(c,p))
                    {
                        flexValue += (team[1] == team[turn] ? p.chess.attack/2:c.attack/2);
                    }
                }
            }
        }
        blueSum = basicValue + flexValue;
        basicValue = 0;
        flexValue = 0;
        for (Iterator<chesspiece> it1 = redliveChess.iterator();it1.hasNext();)
        {
            chesspiece c = (chesspiece)it1.next();
            if (c == chess[7] && redliveChess.contains(chess[8]))
                basicValue += 400;
            basicValue += c.attack;
            flexValue += c.point.poValue[0];
            addAvailPoint_(c);
            HashSet<Point> availPoint = new HashSet<Point>(c.availPoint);
            for (Iterator<Point> it2 = availPoint.iterator(); it2.hasNext();)
            {
                Point p = it2.next();
                if (p.chess == null)
                {
                    flexValue += c.attack/6;
                }else if (p.chess.team == team[1])
                {
                    if (moveable(c,p))
                    {
                        flexValue += (team[0] == team[turn] ? p.chess.attack/2:c.attack/2);
                    }
                }
            }
        }
        redSum = basicValue + flexValue;
        return blueSum - redSum;
    }
    private void undo(undoUnit u)        //撤销一步移动动作
    {
        if (u.deadchess == null)
        {
            u.movechess.point = u.frompoint;
            u.topoint.chess = null;
            u.frompoint.chess = u.movechess;
        }
        else
        {
            u.movechess.point = u.frompoint;
            u.deadchess.point = u.topoint;
            u.frompoint.chess = u.movechess;
            u.topoint.chess = u.deadchess;
            deathChess.remove(u.deadchess);
            if (u.deadchess.team == team[0])
                redliveChess.add(u.deadchess);
            else
                blueliveChess.add(u.deadchess);
        }
        cnt--;
    }
    private void addundoUnit(undoUnit u)     //向移步记录链表中加入新的节点
    {
        u.next = head;
        head = u;
        undo.setEnabled(true);
    }
    final boolean fa = false;
    public  int MaxMinSearch(int deapth,int turn,boolean flag,int alpha,int beta)
    {
        undoUnit u = new undoUnit();
        int score = 0;
        if (turn%2 == 0)
            score = 10000;
        else
            score = -10000;
        if (winnable() == team[0] || winnable() == team[1] )
            return eveluation(turn%2);
        if (deapth == 0)
        {
            int a = eveluation(turn%2);
            //		 System.out.print(a + "  ");
            return a;
        }
        HashSet<chesspiece> redlive = new HashSet<chesspiece>(redliveChess);
        HashSet<chesspiece> bluelive = new HashSet<chesspiece>(blueliveChess);
        for (Iterator<chesspiece> it1 = (team[0] == team[turn])?redlive.iterator():bluelive.iterator();it1.hasNext(); )
        {
            chesspiece c = (chesspiece)it1.next();
            addAvailPoint_(c);
            HashSet<Point> availPoint = new HashSet<Point>(c.availPoint);
            boolean fl = true;
            for (Iterator<Point> it2 = availPoint.iterator();it2.hasNext();)
            {
                Point p = (Point)it2.next();
//				System.out.println("当前点  "+c.name+"当前目标位置  （"+p.i+" "+p.j+")");
                if (fl)
                    if (moveable(c,p))
                    {
                        move(c,p,u);
//						System.out.println("移动"+c.name+"到（"+p.i+" "+p.j+")");
                        if (turn == 0)         //取极小值
                        {
                            int min = MaxMinSearch(deapth-1,(turn+1)%2,fa,alpha,beta);
                            score = score <= min ? score : min;
                            if (score <= beta)
                            {
                                beta = score;
                                if (alpha > beta)
                                    fl = false;
                            }
                        }
                        else                   //取极大值
                        {
                            int max = MaxMinSearch(deapth-1,(turn+1)%2,fa,alpha,beta);
                            score = score >= max ? score : max;
                            //						System.out.print("score:"+score + "  ");
                            if (score >= alpha)
                            {
                                alpha = score;
                                if (alpha > beta)
                                    fl = false;
                            }
                            if (flag == true)
                            {
                                if (score == max)
                                {
                                    AIchess = c;
                                    AIpoint = p;
                                }
                            }
                        }
                        undo(u);
//						System.out.println("撤销移动"+c.name+"到（"+p.i+" "+p.j+")");
                    }
            }
        }
        return score;
    }
    //窗体的鼠标事件监听器
    public class FrameMouseListener implements MouseListener{

        private JFrame tmp;
        FrameMouseListener(JFrame tmp)
        {
            this.tmp = tmp;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if (switchListener == 0)
            {
                // TODO Auto-generated method stub
                if(e.getSource() != tmp)
                    clickAction(e.getX() + ((chesspiece)e.getSource()).getX(),e.getY() + ((chesspiece)e.getSource()).getY() + 40,curmode);
                else
                    clickAction(e.getX(),e.getY(),curmode);
            }
        }
        @Override
        public void mouseEntered(MouseEvent arg0) {
            // TODO Auto-generated method stub
        }
        @Override
        public void mouseExited(MouseEvent arg0) {
            // TODO Auto-generated method stub
        }
        @Override
        public void mousePressed(MouseEvent arg0) {
            // TODO Auto-generated method stub
        }
        @Override
        public void mouseReleased(MouseEvent arg0) {
            // TODO Auto-generated method stub
        }
    }
}
