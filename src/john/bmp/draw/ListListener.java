package john.bmp.draw;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;

public class ListListener extends MouseAdapter{
	private JList list;
	private int style;
	public ListListener(JList list){
		this.list=list;
	}
	public void mousePressed(MouseEvent e){
		style=list.getSelectedIndex();
	}
	public int getStyle(){
		return style;
	}
}
