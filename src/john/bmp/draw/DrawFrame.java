package john.bmp.draw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;

public class DrawFrame extends JFrame {
	// ������
	public static void main(String args[]) {
		DrawFrame drawFrame = new DrawFrame();
		drawFrame.drawUI();
	}

	private ButtonGroup shapeGroup;
	private DrawPanel drawPanel;
	private ColorListener colorLis;
	private JLabel leftClick;
	private JLabel rightClick;
	private ListListener listLis;
	private FileListener flis;

	// ������
	public void drawUI() {
		// ���ô����С
		this.setSize(600, 500);
		// ���ô������
		this.setTitle("��XP����");
		// ���ô�������Ļ�Ͼ���
		this.setLocationRelativeTo(null);
		// ���ô���Ĭ���˳�����Ϊ�ر�
		this.setDefaultCloseOperation(3);
		// ���ô��岼��ģʽ
		BorderLayout bdlot = new BorderLayout();
		this.setLayout(bdlot);
		// ������
		JPanel center = centerPanel();
		JPanel top = topPanel();
		JPanel left = leftPanel();
		JPanel foot = footPanel();
		this.add(top, BorderLayout.NORTH);
		this.add(left, BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);
		this.add(foot, BorderLayout.SOUTH);

		// ���ô���ɼ�
		this.setVisible(true);

		// ����������������������

		DrawListener drawLis = new DrawListener(shapeGroup, leftClick,
				rightClick, listLis, drawPanel);
		drawPanel.addMouseListener(drawLis);
		drawPanel.addMouseMotionListener(drawLis);
	}

	/**
	 * ��������棨�˵�����
	 * 
	 * @return �˵�������
	 */

	private JPanel topPanel() {
		// ��������
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// �����˵���
		JMenuBar menuBar = new JMenuBar();
		String str[] = { "�ļ�", "�༭", "�鿴", "ͼ��", "��ɫ", "����" };
		for (int i = 0; i < str.length; i++) {
			JMenu menu = new JMenu(str[i]);
			menu.setActionCommand(str[i]);
			if (menu.getActionCommand().equals("�ļ�")) {

				JMenuItem open = new JMenuItem("��");
				open.setActionCommand("��");
				// ��Ӽ�����
				flis=new FileListener(drawPanel);
				open.addActionListener(flis);
				JMenuItem save = new JMenuItem("����");
				save.setActionCommand("����");
				// ��Ӽ�����
				save.addActionListener(flis);
				menu.add(open);
				menu.add(save);
			}
			menuBar.add(menu);
		}

		// ��Ӳ˵���
		top.add(menuBar);

		return top;
	}

	/**
	 * ���������
	 * 
	 * @return ����
	 */
	private JPanel leftPanel() {
		// ��������
		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(80, 500));
		// ����Ϊ��ʽ���֣�ˮƽ��ֱ����Ϊ0
		left.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		// �������ѡ���
		JList<ImageIcon> list = new JList<ImageIcon>();
		list.setPreferredSize(new Dimension(40, 60));
		// ���ñ߿�
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE,
				Color.GRAY));
		// ����JList������
		listLis = new ListListener(list);
		// ��Ӽ�����
		list.addMouseListener(listLis);

		// ������ť��
		shapeGroup = new ButtonGroup();
		// ���� ����ָ���ַ���
		String str[] = { "cut", "select", "eraser", "fill", "xiguan", "view",
				"penwrite", "brush", "penqiang", "word", "line", "quxian",
				"rect", "duobianxing", "oval", "roundrect" };
		// �����滭��������
		StyleListener stlLis = new StyleListener(list);
		for (int i = 0; i < str.length; i++) {
			// ���ͼƬ����·��
			URL butUrl = DrawFrame.class
					.getResource("images/draw" + i + ".jpg");
			URL butRolloverUrl = DrawFrame.class.getResource("images/draw" + i
					+ "-1.jpg");
			URL butPressedUrl = DrawFrame.class.getResource("images/draw" + i
					+ "-2.jpg");
			URL butSelectedUrl = DrawFrame.class.getResource("images/draw" + i
					+ "-3.jpg");
			// ����ͼ�����
			ImageIcon but = new ImageIcon(butUrl);
			ImageIcon butRollover = new ImageIcon(butRolloverUrl);
			ImageIcon butPressed = new ImageIcon(butPressedUrl);
			ImageIcon butSelected = new ImageIcon(butSelectedUrl);
			// ������ѡ��ť
			JRadioButton radioButton = new JRadioButton();
			// ���ð�ťͼ��
			radioButton.setIcon(but);
			radioButton.setRolloverIcon(butRollover);
			radioButton.setPressedIcon(butPressed);
			radioButton.setSelectedIcon(butSelected);
			// ���ö���ָ��
			radioButton.setActionCommand(str[i]);
			// ����Ĭ��ѡ�а�ťΪǦ��
			if (radioButton.getActionCommand().equals("penwrite")) {
				radioButton.setSelected(true);
			}
			// ����ť��ӷ�������
			radioButton.addActionListener(stlLis);
			// ��Ӱ�ť����ť��
			shapeGroup.add(radioButton);
			// ��Ӱ�ť���������
			left.add(radioButton);
		}

		// ��Ӱ�ť���������

		left.add(list);

		return left;
	}

	/**
	 * ��������壨������壩
	 * 
	 * @return
	 */
	private JPanel centerPanel() {
		// ��������
		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		center.setPreferredSize(new Dimension(500, 400));
		center.setBackground(Color.GRAY);
		drawPanel = new DrawPanel();
		center.add(drawPanel);
		return center;
	}

	/**
	 * ��������壨��ɫ��壩
	 * 
	 * @return
	 */
	private JPanel footPanel() {
		JPanel foot = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		foot.setPreferredSize(new Dimension(300, 80));
		// ��ǰ��ɫ
		JPanel presentColor = new JPanel(
				new FlowLayout(FlowLayout.CENTER, 2, 2));
		presentColor.setPreferredSize(new Dimension(35, 35));
		presentColor.setBackground(Color.GRAY);
		// �����ɫ
		leftClick = new JLabel();
		leftClick.setPreferredSize(new Dimension(15, 15));
		leftClick.setBackground(Color.BLACK);
		leftClick.setOpaque(true);
		// �һ���ɫ
		rightClick = new JLabel();
		rightClick.setBackground(Color.WHITE);
		rightClick.setOpaque(true);
		rightClick.setPreferredSize(new Dimension(15, 15));
		presentColor.add(leftClick);
		presentColor.add(rightClick);
		// ������ɫ������
		colorLis = new ColorListener(leftClick, rightClick);
		// ��ɫ����
		JPanel colorBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
		colorBar.setPreferredSize(new Dimension(240, 35));
		colorBar.setBackground(Color.WHITE);
		// ������ɫ����
		Color color[] = { new Color(0, 0, 0), new Color(128, 128, 128),
				new Color(128, 0, 0), new Color(128, 128, 0),
				new Color(0, 128, 0), new Color(0, 128, 128),
				new Color(0, 0, 128), new Color(128, 0, 128),
				new Color(128, 128, 64), new Color(0, 64, 64),
				new Color(0, 128, 255), new Color(0, 128, 192),
				new Color(128, 0, 128), new Color(128, 64, 0),
				new Color(255, 255, 255), new Color(192, 192, 192),
				new Color(255, 0, 0), new Color(255, 255, 0),
				new Color(0, 255, 0), new Color(0, 255, 255),
				new Color(0, 0, 255), new Color(255, 0, 255),
				new Color(255, 255, 128), new Color(0, 255, 255),
				new Color(128, 255, 255), new Color(128, 128, 255),
				new Color(255, 0, 128), new Color(255, 128, 64) };
		// ������ɫ��ť
		for (int i = 0; i < color.length; i++) {
			JLabel colorLabel = new JLabel();
			colorLabel.setPreferredSize(new Dimension(15, 15));
			colorLabel.setBackground(color[i]);
			colorLabel.setOpaque(true);
			// �����ɫ������
			colorLabel.addMouseListener(colorLis);
			colorBar.add(colorLabel);

		}

		foot.add(presentColor);
		foot.add(colorBar);
		return foot;
	}
}
