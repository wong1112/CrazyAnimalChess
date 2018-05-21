package eg_draw;

import java.io.Serializable;

class Point implements Serializable {
        public static final int X_BORDER = 150;
        public static final int Y_BORDER = 5;
        public int x1,x2,y1,y2;
        public int deAttack;         //对棋子攻击力的限制值
        public int poValue[];          //当前点的价值,poValue0为对红色方的价值poValue1为对蓝色方的价值
        public int i;
        public int j;
        public chesspiece chess;
        public String road;
        Point()
        {
            x1 = x2 = y1 = y2 = -1;
            chess = null;
            road = null;
            deAttack = 0;
        }
        Point(int i,int j,chesspiece chess,String road,int deAttack)
        {
            x1 = i + Y_BORDER;
            x2 = x1 + 100;
            y1 = j + X_BORDER;
            y2 = y1 + 100;
            this.chess = chess;
            this.road = road;
            this.deAttack = deAttack;
            poValue = new int[2];
        }
    }

