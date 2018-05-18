package client.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import client.data.Data;
import client.listener.MapListener;

public class ChessBoardCanvas extends Canvas {
	/**
	 * 棋盘画板
	 */
	private static final long serialVersionUID = 1L;

	private int MAP_WIDTH = 531;
	private int MAP_HEIGHT = 531;

	String sourcePath = null;

	// 缓存图片
	BufferedImage chessBoardImage = new BufferedImage(MAP_WIDTH, MAP_HEIGHT, 1);
	Graphics2D g2 = chessBoardImage.createGraphics();

	// 构造函数
	public ChessBoardCanvas() {

		this.paintMapImage();
		this.setSize(MAP_WIDTH, MAP_HEIGHT);
		this.addMouseListener(new MapListener());
	}

	// 重写paint
	@Override
	public void paint(Graphics g) {
		g.drawImage(chessBoardImage, 0, 0, null);
	}

	public void paintMapImage() {

		this.paintBackground();
		this.paintChess();

	}

	public void paintBackground() {

		// 设置背景图片
		try {
			Image background = null;
			background = ImageIO.read(new File(this.getSourcePath()
					+ "/src/client/images/map.png"));
			g2.drawImage(background, 0, 0, null);

		} catch (IOException e1) {

			// 手动画网格
			this.setBackground(new Color(210, 180, 140));

			g2.setColor(Color.black);

			for (int i = 0; i < 15; i++) {
				g2.drawLine((35 * i + 20), 20, (35 * i + 20), 510);
			}
			for (int i = 0; i < 15; i++) {
				g2.drawLine(20, (35 * i + 20), 510, (35 * i + 20));
			}
			// 手动画标记点
			g2.fillRect(122, 122, 7, 7);
			g2.fillRect(402, 122, 7, 7);
			g2.fillRect(122, 402, 7, 7);
			g2.fillRect(402, 402, 7, 7);
			g2.fillRect(262, 262, 7, 7);

			e1.printStackTrace();
		}
	}

	public void paintChess() {

		Image black = null;
		BufferedImage white = null;

		// 棋子
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (Data.chessBoard[i][j] == Data.BLACK) {
					try {

						// 是否为最后一步棋
						if (15 * j + i == Data.last) {
							black = ImageIO.read(new File(this.getSourcePath()
									+ "/src/client/images/black2.png"));
						} else {
							black = ImageIO.read(new File(this.getSourcePath()
									+ "/src/client/images/black.png"));

						}

						g2.drawImage(black, i * 35 + 4, j * 35 + 4, null);

					} catch (IOException e) {

						// 手动画
						g2.fillOval(i * 35 + 4, j * 35 + 4, 33, 33);

						e.printStackTrace();
					}
				} else {
					if (Data.chessBoard[i][j] == Data.WHITE) {

						try {

							// 是否为最后一步棋
							if (15 * j + i == Data.last) {
								white = ImageIO.read(new File(this
										.getSourcePath()
										+ "/src/client/images/white2.png"));
							} else {
								white = ImageIO.read(new File(this
										.getSourcePath()
										+ "/src/client/images/white.png"));
							}

							g2.drawImage(white, i * 35 + 4, j * 35 + 4, null);

						} catch (IOException e) {

							// 手动画
							g2.setColor(Color.white);
							g2.fillOval(i * 35 + 4, j * 35 + 4, 33, 33);
							g2.setColor(Color.black);

							e.printStackTrace();
						}
					}
				}

			}
		}
	}

	public String getSourcePath() {
		if (sourcePath == null) {
			sourcePath = new File("").getAbsolutePath();
		}

		return sourcePath;
	}

	public int getMapWidth() {
		return MAP_WIDTH;
	}

	public int getMapHeitht() {
		return MAP_HEIGHT;
	}

}
