package john.bmp.draw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JList;

public class StyleListener implements ActionListener {
	private JList<ImageIcon> list;

	public StyleListener(JList<ImageIcon> list) {
		this.list = list;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("rect")
				|| e.getActionCommand().equals("oval")
				|| e.getActionCommand().equals("roundrect")) {
			list.removeAll();
			list.setFixedCellHeight(20);
			ImageIcon styleIcon[] = new ImageIcon[3];
			for (int i = 0; i < styleIcon.length; i++) {
				URL style = DrawFrame.class.getResource("images/shape_"
						+ (i + 1) + ".png");
				styleIcon[i] = new ImageIcon(style);
			}
			list.setListData(styleIcon);
		} else if (e.getActionCommand().equals("line")) {
			list.removeAll();
			list.setFixedCellHeight(10);
			ImageIcon styleIcon[] = new ImageIcon[5];
			for (int i = 0; i < styleIcon.length; i++) {
				URL style = DrawFrame.class.getResource("images/lineSize_"
						+ (i + 1) + ".gif");
				styleIcon[i] = new ImageIcon(style);
			}
			list.setListData(styleIcon);
		} else if (e.getActionCommand().equals("penqiang")) {
			list.removeAll();
			list.setFixedCellHeight(20);
			ImageIcon styleIcon[] = new ImageIcon[3];
			for (int i = 0; i < styleIcon.length; i++) {
				URL style = DrawFrame.class.getResource("images/penhui_"
						+ (i + 1) + ".gif");
				styleIcon[i] = new ImageIcon(style);
			}
			list.setListData(styleIcon);
		} else if (e.getActionCommand().equals("penwrite")) {
			list.removeAll();// 不起作用，求解释

		}

	}
}
