package client.manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

public class IOManager {
	/**
	 * 获取输入输出流
	 */
	private static IOManager instance = null;

	private PrintStream ps;
	private BufferedReader br;

	private IOManager(){

	}
	public static IOManager getInstance(){

		if(instance == null){

			instance = new IOManager();
		}
		return instance;
	}

	public PrintStream getPs(){
		return ps;
	}

	public BufferedReader getBr(){
		return br;
	}

	public void setPs(OutputStream os){
		ps = new PrintStream(os);
	}

	public void setBr(InputStreamReader isr){
		br  = new BufferedReader(isr);
	}
}
