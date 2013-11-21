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
	// 主函数
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

	// 主窗体
	public void drawUI() {
		// 设置窗体大小
		this.setSize(600, 500);
		// 设置窗体标题
		this.setTitle("仿XP画板");
		// 设置窗体在屏幕上居中
		this.setLocationRelativeTo(null);
		// 设置窗体默认退出操作为关闭
		this.setDefaultCloseOperation(3);
		// 设置窗体布局模式
		BorderLayout bdlot = new BorderLayout();
		this.setLayout(bdlot);
		// 添加组件
		JPanel center = centerPanel();
		JPanel top = topPanel();
		JPanel left = leftPanel();
		JPanel foot = footPanel();
		this.add(top, BorderLayout.NORTH);
		this.add(left, BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);
		this.add(foot, BorderLayout.SOUTH);

		// 设置窗体可见
		this.setVisible(true);

		// 创建画布监听器监听画布

		DrawListener drawLis = new DrawListener(shapeGroup, leftClick,
				rightClick, listLis, drawPanel);
		drawPanel.addMouseListener(drawLis);
		drawPanel.addMouseMotionListener(drawLis);
	}

	/**
	 * 创建上面版（菜单栏）
	 * 
	 * @return 菜单栏容器
	 */

	private JPanel topPanel() {
		// 创建容器
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// 创建菜单栏
		JMenuBar menuBar = new JMenuBar();
		String str[] = { "文件", "编辑", "查看", "图像", "颜色", "帮助" };
		for (int i = 0; i < str.length; i++) {
			JMenu menu = new JMenu(str[i]);
			menu.setActionCommand(str[i]);
			if (menu.getActionCommand().equals("文件")) {

				JMenuItem open = new JMenuItem("打开");
				open.setActionCommand("打开");
				// 添加监听器
				flis=new FileListener(drawPanel);
				open.addActionListener(flis);
				JMenuItem save = new JMenuItem("保存");
				save.setActionCommand("保存");
				// 添加监听器
				save.addActionListener(flis);
				menu.add(open);
				menu.add(save);
			}
			menuBar.add(menu);
		}

		// 添加菜单栏
		top.add(menuBar);

		return top;
	}

	/**
	 * 创建左面板
	 * 
	 * @return 容器
	 */
	private JPanel leftPanel() {
		// 创建容器
		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(80, 500));
		// 设置为流式布局，水平垂直间距均为0
		left.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		// 创建风格选择框
		JList<ImageIcon> list = new JList<ImageIcon>();
		list.setPreferredSize(new Dimension(40, 60));
		// 设置边框
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE,
				Color.GRAY));
		// 创建JList监听器
		listLis = new ListListener(list);
		// 添加监听器
		list.addMouseListener(listLis);

		// 创建按钮组
		shapeGroup = new ButtonGroup();
		// 创建 动作指令字符串
		String str[] = { "cut", "select", "eraser", "fill", "xiguan", "view",
				"penwrite", "brush", "penqiang", "word", "line", "quxian",
				"rect", "duobianxing", "oval", "roundrect" };
		// 创建绘画风格监听器
		StyleListener stlLis = new StyleListener(list);
		for (int i = 0; i < str.length; i++) {
			// 添加图片网络路径
			URL butUrl = DrawFrame.class
					.getResource("images/draw" + i + ".jpg");
			URL butRolloverUrl = DrawFrame.class.getResource("images/draw" + i
					+ "-1.jpg");
			URL butPressedUrl = DrawFrame.class.getResource("images/draw" + i
					+ "-2.jpg");
			URL butSelectedUrl = DrawFrame.class.getResource("images/draw" + i
					+ "-3.jpg");
			// 创建图标对象
			ImageIcon but = new ImageIcon(butUrl);
			ImageIcon butRollover = new ImageIcon(butRolloverUrl);
			ImageIcon butPressed = new ImageIcon(butPressedUrl);
			ImageIcon butSelected = new ImageIcon(butSelectedUrl);
			// 创建单选按钮
			JRadioButton radioButton = new JRadioButton();
			// 设置按钮图标
			radioButton.setIcon(but);
			radioButton.setRolloverIcon(butRollover);
			radioButton.setPressedIcon(butPressed);
			radioButton.setSelectedIcon(butSelected);
			// 设置动作指令
			radioButton.setActionCommand(str[i]);
			// 设置默认选中按钮为铅笔
			if (radioButton.getActionCommand().equals("penwrite")) {
				radioButton.setSelected(true);
			}
			// 给按钮添加风格监听器
			radioButton.addActionListener(stlLis);
			// 添加按钮到按钮组
			shapeGroup.add(radioButton);
			// 添加按钮到左面板上
			left.add(radioButton);
		}

		// 添加按钮到左面板上

		left.add(list);

		return left;
	}

	/**
	 * 创建中面板（画布面板）
	 * 
	 * @return
	 */
	private JPanel centerPanel() {
		// 创建容器
		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		center.setPreferredSize(new Dimension(500, 400));
		center.setBackground(Color.GRAY);
		drawPanel = new DrawPanel();
		center.add(drawPanel);
		return center;
	}

	/**
	 * 创建下面板（颜色面板）
	 * 
	 * @return
	 */
	private JPanel footPanel() {
		JPanel foot = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		foot.setPreferredSize(new Dimension(300, 80));
		// 当前颜色
		JPanel presentColor = new JPanel(
				new FlowLayout(FlowLayout.CENTER, 2, 2));
		presentColor.setPreferredSize(new Dimension(35, 35));
		presentColor.setBackground(Color.GRAY);
		// 左击颜色
		leftClick = new JLabel();
		leftClick.setPreferredSize(new Dimension(15, 15));
		leftClick.setBackground(Color.BLACK);
		leftClick.setOpaque(true);
		// 右击颜色
		rightClick = new JLabel();
		rightClick.setBackground(Color.WHITE);
		rightClick.setOpaque(true);
		rightClick.setPreferredSize(new Dimension(15, 15));
		presentColor.add(leftClick);
		presentColor.add(rightClick);
		// 创建颜色监听器
		colorLis = new ColorListener(leftClick, rightClick);
		// 颜色盒子
		JPanel colorBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
		colorBar.setPreferredSize(new Dimension(240, 35));
		colorBar.setBackground(Color.WHITE);
		// 创建颜色数组
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
		// 创建颜色按钮
		for (int i = 0; i < color.length; i++) {
			JLabel colorLabel = new JLabel();
			colorLabel.setPreferredSize(new Dimension(15, 15));
			colorLabel.setBackground(color[i]);
			colorLabel.setOpaque(true);
			// 添加颜色监听器
			colorLabel.addMouseListener(colorLis);
			colorBar.add(colorLabel);

		}

		foot.add(presentColor);
		foot.add(colorBar);
		return foot;
	}
}
