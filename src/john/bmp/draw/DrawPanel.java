package john.bmp.draw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	public DrawPanel() {
		this.setPreferredSize(new Dimension(400, 300));
		this.setBackground(Color.WHITE);
	}

	public void paint(Graphics g) {
		super.paint(g);
		// 重绘代码
		if (CatchScrn.a != null) {
			for (int i = 0; i < CatchScrn.a.length; i++) {
				for (int j = 0; j < CatchScrn.a[0].length; j++) {
					// 取点
					int point = CatchScrn.a[i][j];
					if (point != this.getBackground().getRGB()) {
						// 设置颜色
						g.setColor(new Color(point));
						// 画点
						g.drawLine(j, i, j, i);
					}
				}
			}
		}
	}

}
