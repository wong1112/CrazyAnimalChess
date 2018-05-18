package server.tool.check;
import java.util.*;

public class Check {
	/**
	 * 检查胜负
	 *
	 * */
	public boolean check(int x ,int y ,int id,int[][] chessBoard){

		int winPoint[] = new int [4];

		winPoint[0] = CheckX.checkX(x ,y ,id, chessBoard);
		winPoint[1] = CheckY.checkY(x ,y ,id, chessBoard);
		winPoint[2] = CheckM.checkM(x ,y ,id, chessBoard);
		winPoint[3] = CheckN.checkN(x ,y ,id, chessBoard);

		Arrays.sort(winPoint);

		if(winPoint[3] > 4){
			return true;
		}
		else{
			return false;
		}
	}
}
