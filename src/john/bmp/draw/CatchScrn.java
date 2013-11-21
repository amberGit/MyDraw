package john.bmp.draw;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class CatchScrn {
	private Robot rb;
	public static int a[][];

	public CatchScrn() {
		// ���������˶���
		try {
			rb = new Robot();
		} catch (Exception ef) {
			ef.printStackTrace();
		}
	}

	public void saveImage(DrawPanel drawPanel) {
		// �õ���������
		Rectangle rect = new Rectangle(drawPanel.getLocationOnScreen(),
				drawPanel.getPreferredSize());
		// �õ���ȡͼ��
		BufferedImage image = rb.createScreenCapture(rect);
		// ���������С
		a = new int[image.getHeight()][image.getWidth()];
		// ��ͼ��������ر��浽������Ӧλ��
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				a[i][j] = image.getRGB(j, i);
			}
		}
	}

}
