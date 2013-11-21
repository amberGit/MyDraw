package john.bmp.draw;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;

public class DrawListener extends MouseAdapter {
	private ButtonGroup shapeGroup;
	private Graphics2D g;
	private int x1, y1, x2, y2;
	private String type;
	private JLabel leftClick;
	private JLabel rightClick;
	private ListListener listLis;
	private CatchScrn image = new CatchScrn();
	private DrawPanel drawPanel;
	// ��ʾ��ǰLISTѡ�еİ���
	private int style;
	// ��ǰ��ɫ
	private Color presentColor;
	// ��ǰ��ɫ�ķ�ɫ��������һ���ɫ�෴��
	private Color color;
	// ���滭����εĳ�ʼ��
	private int firstx, firsty;
	// flag�ж϶���δ�������״̬��true�����Ƿ��״̬��false��
	private boolean flag = false;

	// ��д�����������Ĺ�����
	public DrawListener(ButtonGroup shapeGroup, JLabel leftClick,
			JLabel rightClick, ListListener listLis, DrawPanel drawPanel) {
		this.shapeGroup = shapeGroup;
		this.leftClick = leftClick;
		this.rightClick = rightClick;
		this.listLis = listLis;
		this.drawPanel = drawPanel;
	}

	public void mousePressed(MouseEvent e) {
		// ��ȡ����
		g = (Graphics2D) drawPanel.getGraphics();
		// ������ɫ
		if (e.getButton() == 1) {
			g.setColor(leftClick.getBackground());
			presentColor = leftClick.getBackground();
			color = rightClick.getBackground();
		} else if (e.getButton() == 3) {
			presentColor = rightClick.getBackground();
			color = leftClick.getBackground();
			g.setColor(rightClick.getBackground());
		}

		// �õ�����ָ��
		type = shapeGroup.getSelection().getActionCommand();
		style = listLis.getStyle();
		x1 = e.getX();
		y1 = e.getY();
		if (type.equals("duobianxing")) {
			if (flag == false) {
				firstx = x1;
				firsty = y1;
				flag = true;
			} else {
				x1 = x2;
				y1 = y2;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY();
		if (type.equals("cut")) {

		} else if (type.equals("select")) {

		} else if (type.equals("fill")) {
			// ���µ�ǰ��Ļ��ͼ��
			// ���������˶���
			Robot rb;
			try {
				rb = new Robot();
				// �õ���������
				Rectangle rect = new Rectangle(drawPanel.getLocationOnScreen(),
						drawPanel.getPreferredSize());
				// �õ���ȡͼ��
				BufferedImage image = rb.createScreenCapture(rect);
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// �������⻭��

		} else if (type.equals("line")) {
			// ���ñʻ�����
			BasicStroke stroke = new BasicStroke(style + 1);
			g.setStroke(stroke);
			g.drawLine(x1, y1, x2, y2);
			// ���ñʻ�����
			stroke = new BasicStroke(0);
			g.setStroke(stroke);

		} else if (type.equals("quxian")) {

		} else if (type.equals("rect")) {
			if (style == 0) {
				g.drawRect(Math.min(x1, x2), Math.min(y1, y2),
						Math.abs(x1 - x2), Math.abs(y1 - y2));
			} else if (style == 1) {
				g.fillRect(Math.min(x1, x2), Math.min(y1, y2),
						Math.abs(x1 - x2), Math.abs(y1 - y2));
			} else if (style == 2) {
				// ����ɫ���ɷ�ɫ
				g.setColor(color);
				g.fillRect(Math.min(x1, x2), Math.min(y1, y2),
						Math.abs(x1 - x2), Math.abs(y1 - y2));
				// ����ɫ����ȥ
				g.setColor(presentColor);
			}

		} else if (type.equals("oval")) {
			if (style == 0) {
				g.drawOval(Math.min(x1, x2), Math.min(y1, y2),
						Math.abs(x1 - x2), Math.abs(y1 - y2));
			} else if (style == 1) {
				g.fillOval(Math.min(x1, x2), Math.min(y1, y2),
						Math.abs(x1 - x2), Math.abs(y1 - y2));
			} else if (style == 2) {
				g.setColor(color);
				g.fillOval(Math.min(x1, x2), Math.min(y1, y2),
						Math.abs(x1 - x2), Math.abs(y1 - y2));
				g.setColor(presentColor);
			}

		} else if (type.equals("duobianxing")) {
			g.drawLine(x1, y1, x2, y2);
			if (e.getClickCount() >= 2) {
				g.drawLine(firstx, firsty, x1, y1);
				flag = false;
			}

		} else if (type.equals("roundrect")) {
			if (style == 0) {
				g.drawRoundRect(Math.min(x1, x2), Math.min(y1, y2),
						Math.abs(x1 - x2), Math.abs(y1 - y2), 10, 10);
			} else if (style == 1) {
				g.fillRoundRect(Math.min(x1, x2), Math.min(y1, y2),
						Math.abs(x1 - x2), Math.abs(y1 - y2), 10, 10);
			} else if (style == 2) {
				g.setColor(color);
				g.fillRoundRect(Math.min(x1, x2), Math.min(y1, y2),
						Math.abs(x1 - x2), Math.abs(y1 - y2), 10, 10);
				g.setColor(presentColor);
			}

		}
		// ÿ���ͷź����
		image.saveImage(drawPanel);
	}

	public void mouseDragged(MouseEvent e) {
		if (type.equals("penwrite")) {
			int x2 = e.getX();
			int y2 = e.getY();

			g.drawLine(x1, y1, x2, y2);
			x1 = x2;
			y1 = y2;
		} else if (type.equals("penqiang")) {
			int x2 = e.getX();
			int y2 = e.getY();
			// ���ô�㷶Χ
			int size = (style + 1) * 5;
			// �����������������
			Random rd = new Random();
			// ��size��Χ��������
			for (int i = 0; i < 4; i++) {
				int rdx = rd.nextInt(size);
				int rdy = rd.nextInt(size);
				g.drawLine((rdx - size / 2) + x2, (rdy - size / 2) + y2,
						(rdx - size / 2) + x2, (rdy - size / 2) + y2);
			}

		} else if (type.equals("eraser")) {
			g.setColor(color);
			BasicStroke stroke = new BasicStroke(8, BasicStroke.CAP_SQUARE,
					BasicStroke.JOIN_BEVEL);
			g.setStroke(stroke);
			int x2 = e.getX();
			int y2 = e.getY();
			g.drawLine(x1, y1, x2, y2);
			x1 = x2;
			y1 = y2;
			// ������ɫ
			g.setColor(presentColor);
		} else if (type.equals("brush")) {
			BasicStroke stroke = new BasicStroke(8, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_ROUND);
			g.setStroke(stroke);
			int x2 = e.getX();
			int y2 = e.getY();
			g.drawLine(x1, y1, x2, y2);
			x1 = x2;
			y1 = y2;
		}
	}

	/**
	 * ��䷽��
	 * 
	 * @param loc
	 *            �������ͷ�ʱ��ľ���
	 */

	private void fill(BufferedImage image, int loc) {
		
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}
}
