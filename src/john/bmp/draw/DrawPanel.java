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
		// �ػ����
		if (CatchScrn.a != null) {
			for (int i = 0; i < CatchScrn.a.length; i++) {
				for (int j = 0; j < CatchScrn.a[0].length; j++) {
					// ȡ��
					int point = CatchScrn.a[i][j];
					if (point != this.getBackground().getRGB()) {
						// ������ɫ
						g.setColor(new Color(point));
						// ����
						g.drawLine(j, i, j, i);
					}
				}
			}
		}
	}

}
