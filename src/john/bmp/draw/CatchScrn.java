package john.bmp.draw;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class CatchScrn {
	private Robot rb;
	public static int a[][];

	public CatchScrn() {
		// 创建机器人对象
		try {
			rb = new Robot();
		} catch (Exception ef) {
			ef.printStackTrace();
		}
	}

	public void saveImage(DrawPanel drawPanel) {
		// 得到画布区域
		Rectangle rect = new Rectangle(drawPanel.getLocationOnScreen(),
				drawPanel.getPreferredSize());
		// 得到截取图像
		BufferedImage image = rb.createScreenCapture(rect);
		// 设置数组大小
		a = new int[image.getHeight()][image.getWidth()];
		// 将图像逐个像素保存到数组相应位置
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				a[i][j] = image.getRGB(j, i);
			}
		}
	}

}
