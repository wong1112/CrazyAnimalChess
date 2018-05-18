package client.data;

public class Data {

	/**
	 *静态常量
	 */
	public static int last = -1;	//最后棋子位置

	public static int turn = 0;		//回合

	public static int myId = 0;			//我的id
	public static int oppoId = 0;	//对手id

	public static int BLACK = 1;
	public static int WHITE = -1;

	public static int myChess = 0;
	public static int oppoChess = 0;

	public static String myName = null;

	public static boolean wait = false;

	public static boolean ready = false;

	public static boolean started = false;

	public static boolean connected = false;

	public static int [][] chessBoard = new int[15][15];

}
