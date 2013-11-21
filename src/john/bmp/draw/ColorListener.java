package john.bmp.draw;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class ColorListener extends MouseAdapter {
	private JLabel leftClick;
	private JLabel rightClick;
	private Color color;

	public ColorListener(JLabel leftClick, JLabel rightClick) {
		this.leftClick = leftClick;
		this.rightClick = rightClick;
	}

	public void mousePressed(MouseEvent e) {
		if (e.getSource() instanceof JLabel) {
			JLabel temp=(JLabel)e.getSource();
			if (e.getButton() == 1) {
				leftClick.setBackground(temp.getBackground());
				color=temp.getBackground();
			}else if(e.getButton()==3){
				rightClick.setBackground(temp.getBackground());
				color=temp.getBackground();
			}
		}

	}

}
