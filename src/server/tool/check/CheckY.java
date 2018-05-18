package server.tool.check;

public class CheckY {

	/**
	 * 检查Y轴
	 *
	 * */

	public static int checkY(int line ,int row ,int id,int[][] chessBoard){

		int check = 0;
		int checkLeft = 0;
		int checkRight = 0;

		for(int i = 0; i < 5; i++){

			if(row - i >= 0){

				if( chessBoard[line][row - i] == id){

					checkLeft++;
				}
				else{

					break;
				}
			}

		}

		for(int i = 1; i < 5; i++){

			if(row + i < 15 ){

				if(chessBoard[line][row + i] == id){

					checkRight++;
				}
				else{
					break;
				}
			}

		}
		check = checkLeft + checkRight;
		return (check);
	}
}
