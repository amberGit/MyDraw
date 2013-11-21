package john.bmp.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileListener implements ActionListener {
	private BitMap bmp = new BitMap();
	private DrawPanel drawPanel;

	public FileListener(DrawPanel drawPanel) {
		this.drawPanel = drawPanel;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("打开")) {
			open("f:\\temp.bmp");

		} else if (e.getActionCommand().equals("保存")) {
			save("f:\\test.bmp");
		}
	}

	private void open(String path) {
		try {
			// 创建输入流
			FileInputStream fis = new FileInputStream(path);
			DataInputStream dis = new DataInputStream(fis);
			// 读取文件
			// 读取文件头
			bmp.bfType = dis.readShort();
			bmp.bfSize = dis.readInt();
			bmp.bfReserved1 = dis.readShort();
			bmp.bfReserved2 = dis.readShort();
			bmp.bfOffBits = dis.readInt();
			// 读取信息头
			bmp.biSize = dis.readInt();
			bmp.biWidth = dis.readInt();
			bmp.biHeight = dis.readInt();
			bmp.biPlanes = dis.readShort();
			bmp.biBitCount = dis.readShort();
			bmp.biCompression = dis.readInt();
			bmp.biSizeImage = dis.readInt();
			bmp.biXPelsPerMeter = dis.readInt();
			bmp.biYPelsPerMeter = dis.readInt();
			bmp.biClrUsed = dis.readInt();
			bmp.biClrImportant = dis.readInt();
			// 读取数据域
			bmp.data = new int[300][400];
			for (int i = 0; i < 300; i++) {
				for (int j = 0; j < 400; j++) {
					bmp.data[i][j] = dis.readByte();
				}
			}
			// 打印所有文件信息
			System.out.println(bmp.bfType);
			System.out.println(bmp.bfSize);
			System.out.println(bmp.bfReserved1);
			System.out.println(bmp.bfReserved2);
			System.out.println(bmp.bfOffBits);
			System.out.println(bmp.biSize);
			System.out.println(bmp.biWidth);
			System.out.println(bmp.biHeight);
			System.out.println(bmp.biPlanes);
			System.out.println(bmp.biBitCount);
			System.out.println(bmp.biCompression);
			System.out.println(bmp.biSizeImage);
			System.out.println(bmp.biXPelsPerMeter);
			System.out.println(bmp.biYPelsPerMeter);
			System.out.println(bmp.biClrUsed);
			System.out.println(bmp.biClrImportant);
			// 获取画布
			Graphics g = drawPanel.getGraphics();
			for (int i = 0; i < 300; i++) {
				for (int j = 0; j < 400; j++) {
					if (bmp.data[i][j] != drawPanel.getBackground().getRGB()) {
						g.setColor(new Color(bmp.data[i][j]));
						g.drawLine(j, i, j, i);

					}
				}
			}
		} catch (Exception ef) {
			ef.printStackTrace();
		}
	}

	private void save(String path) {
		try {
			int size = 0;// 调色板数组长度
			File file = new File(path);
			file.createNewFile();
			int temp;// 保存图像宽度
			// 创建输出流
			FileOutputStream fos = new FileOutputStream(file);
			// 设置图片信息
			if (CatchScrn.a != null) {
				bmp.biHeight = CatchScrn.a.length;
				bmp.biWidth = CatchScrn.a[0].length;
				// 判断是否能被4整除
				if (CatchScrn.a[0].length * 3 % 4 != 0) {
					temp = CatchScrn.a[0].length
							+ (4 - CatchScrn.a[0].length * 3 % 4);
					bmp.data = new int[bmp.biHeight][temp];
					// 在填充域补0
					for (int i = 0; i < bmp.biHeight; i++) {
						for (int j = bmp.biWidth; j < temp; j++) {
							bmp.data[i][j] = 0;
						}
					}
				} else {
					temp = bmp.biWidth;
					bmp.data = new int[bmp.biHeight][bmp.biWidth];
				}

				if (bmp.biBitCount < 24) {
					// 创建调色板
					// 计算调色板大小（2的位深度次方）
					size = 1 << bmp.biBitCount;
					bmp.color = new int[size][4];
					bmp.bfOffBits = 54 + bmp.color.length * 4;
				} else if (bmp.biBitCount == 24) {
					bmp.bfOffBits = 54;
				}
				// bmp.bfSize = bmp.bfOffBits + bmp.biHeight * temp
				// * bmp.biBitCount / 8;
				// bmp.biSizeImage = bmp.biHeight * bmp.biWidth * bmp.biBitCount
				// / 8;
				// 图像在文件中的存储顺序是从左至右，从下到上的
				for (int i = CatchScrn.a.length - 1, j = 0; i >= 0; i--, j++) {
					for (int k = 0; k < CatchScrn.a[0].length; k++) {
						bmp.data[i][k] = CatchScrn.a[j][k];
					}
				}

				// 写文件操作
				// 写bmp文件头
				fos.write(BitMap.changeEndian(bmp.bfType));
				fos.write(BitMap.changeEndian(bmp.bfSize));
				fos.write(BitMap.changeEndian(bmp.bfReserved1));
				fos.write(BitMap.changeEndian(bmp.bfReserved2));
				fos.write(BitMap.changeEndian(bmp.bfOffBits));
				// 信息头
				fos.write(BitMap.changeEndian(bmp.biSize));
				fos.write(BitMap.changeEndian(bmp.biWidth));
				fos.write(BitMap.changeEndian(bmp.biHeight));
				fos.write(BitMap.changeEndian(bmp.biPlanes));
				fos.write(BitMap.changeEndian(bmp.biBitCount));
				fos.write(BitMap.changeEndian(bmp.biCompression));
				fos.write(BitMap.changeEndian(bmp.biSizeImage));
				fos.write(BitMap.changeEndian(bmp.biXPelsPerMeter));
				fos.write(BitMap.changeEndian(bmp.biYPelsPerMeter));
				fos.write(BitMap.changeEndian(bmp.biClrUsed));
				fos.write(BitMap.changeEndian(bmp.biClrImportant));
				// 调色板
				// 当位深度小于24时才会用到调色板
				if (bmp.biBitCount < 24) {

					// 如果位深度为1，就是黑白图片啦
					if (bmp.biBitCount == 1) {
						// 设置调色板
						// 调色板只有2种颜色
						for (int i = 0, t = 0; i < 2; i++) {
							// 保留字设为0
							bmp.color[i][3] = 0;
							for (int j = 0; j < 3; j++) {
								bmp.color[i][j] = t;
							}
							t = 0xff;
						}
						// 写入调色板
						for (int i = 0; i < 2; i++) {
							for (int j = 0; j < 4; j++) {
								fos.write((byte) bmp.color[i][j]);
							}
						}
						// 写入数据到数据域(非24位位图要用调色板索引值)
						byte t[] = new byte[8];
						int test = 0;
						for (int i = 0, count = 0; i < bmp.data.length; i++) {
							for (int j = 0; j < bmp.data[0].length; j++) {
								if (j < bmp.biWidth) {
									if (count != 0 && count % 8 == 0) {
										byte tp = 0;
										for (int k = 0; k < 8; k++) {
											tp = (byte) (tp | (byte) (t[k] << (7 - k)));
											System.out.print("!" + tp);
										}
										fos.write(tp);
										System.out.println();
									}
									// 1位位图用一个BIT存放一个像素
									// 1位位图不是黑色就是白色
									if (new Color(bmp.data[i][j])
											.equals(Color.WHITE)) {
										t[count % 8] = 1;
									} else {
										t[count % 8] = 0;
									}

									count++;
								} else {
									// 填充0的区域
									System.out.println("******");
									t[count % 8] = 0;
									if (count != 0 && count % 8 == 0) {
										byte tp = 0;
										for (int k = 0; k < 7; k++) {
											tp = (byte) (tp | (byte) (t[k] << (7 - k)));
											System.out.print("+" + tp);
										}
										fos.write(tp);
									}
									count++;
								}

							}
							fos.write(0);
						}
					} // 如果位深度为4
					else if (bmp.biBitCount == 4) {
						int i = 0;
						for (int j = 0; j < size; j++) {
							// 保留字都设为0
							bmp.color[j][3] = 0;
						}
						for (int t = 0; i < size / 2; i++) {

							t = 128 * i;
							bmp.color[i][2] = t % 256;
							bmp.color[i][1] = t / 256 * 128 % 256;
							bmp.color[i][0] = bmp.color[i][1] / 256 * 128;
						}
						for (int j = 0; j < 3; j++) {
							bmp.color[i][j] = 192;
						}
						i++;
						for (int t = 255; i < size; i++) {
							for (int j = 2; j >= 0; j--) {
								bmp.color[i][j] = (int) (byte) (t >> ((2 - i) * 8));

							}
							t = t << 8;
						}
						for (int j = 0; j < bmp.color.length; j++) {
							for (int k = 0; k < bmp.color[j].length; k++) {
								System.out.print(bmp.color[j][k] + " ");
							}
							System.out.println();
						}
						// 写入数据到数据域
						for (int j = 0; j < bmp.biHeight; j++) {
							for (int k = 0; k < bmp.data[0].length - 1; k += 2) {
								if (k + 1 < bmp.biWidth) {
									// 找调色版里最接近的颜色
									// 保存调色版索引值下标
									int flag1 = 0;
									int flag2 = 0;
									for (int m = 0, min1 = 65536, min2 = 65536; m < bmp.color.length; m++) {
										Color color = new Color(
												bmp.color[m][2],
												bmp.color[m][1],
												bmp.color[m][0]);
										if (Math.abs(bmp.data[j][k]
												- color.getRGB()) < min1) {
											min1 = Math.abs(bmp.data[j][k]
													- color.getRGB());
											flag1 = m;
										}
										if (Math.abs(bmp.data[j][k + 1]
												- color.getRGB()) < min2) {
											min2 = Math.abs(bmp.data[j][k]
													- color.getRGB());
											flag2 = m;
										}
									}
									// 把索引值写入图片数据域
									// 注意4位图一个像素占4个BIT,这意味着一次要写入两个像素
									byte t = (byte) ((byte) flag2 | ((byte) flag1) << 4);
									fos.write(t);
								} else if (k + 1 == bmp.biWidth) {
									int flag = 0;
									for (int m = 0, min = 65536; m < bmp.color.length; m++) {
										Color color = new Color(
												bmp.color[m][2],
												bmp.color[m][1],
												bmp.color[m][0]);
										if (Math.abs(bmp.data[j][k]
												- color.getRGB()) < min) {
											min = Math.abs(bmp.data[j][k]
													- color.getRGB());
											flag = m;
										}
									}
									fos.write((byte) flag << 4);
								} else if (k > bmp.biWidth) {
									fos.write(0);
								}
							}
						}
					} else if (bmp.biBitCount == 8) {
						// 如果位深度为8，也就是256色图片

					}
				} else if (bmp.biBitCount == 24) {
					// 如果位深度是24位的话，就不需要调色板，直接把颜色写到数据域
					// 数据域
					for (int i = 0; i < bmp.data.length; i++) {
						for (int j = 0; j < bmp.data[0].length; j++) {
							if (j < bmp.biWidth) {
								Color color = new Color(bmp.data[i][j]);
								// 写入蓝色分量
								fos.write((byte) color.getBlue());
								// 写入绿色分量
								fos.write((byte) color.getGreen());
								// 写入红色分量
								fos.write((byte) color.getRed());
							} else {
								// 填充域补0
								fos.write(0);
							}
						}
					}
				}
			}
			fos.flush();
			fos.close();

		} catch (Exception ef) {
			// TODO Auto-generated catch block
			ef.printStackTrace();
		}
	}
}
