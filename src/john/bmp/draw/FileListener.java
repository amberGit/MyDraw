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

		if (e.getActionCommand().equals("��")) {
			open("f:\\temp.bmp");

		} else if (e.getActionCommand().equals("����")) {
			save("f:\\test.bmp");
		}
	}

	private void open(String path) {
		try {
			// ����������
			FileInputStream fis = new FileInputStream(path);
			DataInputStream dis = new DataInputStream(fis);
			// ��ȡ�ļ�
			// ��ȡ�ļ�ͷ
			bmp.bfType = dis.readShort();
			bmp.bfSize = dis.readInt();
			bmp.bfReserved1 = dis.readShort();
			bmp.bfReserved2 = dis.readShort();
			bmp.bfOffBits = dis.readInt();
			// ��ȡ��Ϣͷ
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
			// ��ȡ������
			bmp.data = new int[300][400];
			for (int i = 0; i < 300; i++) {
				for (int j = 0; j < 400; j++) {
					bmp.data[i][j] = dis.readByte();
				}
			}
			// ��ӡ�����ļ���Ϣ
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
			// ��ȡ����
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
			int size = 0;// ��ɫ�����鳤��
			File file = new File(path);
			file.createNewFile();
			int temp;// ����ͼ����
			// ���������
			FileOutputStream fos = new FileOutputStream(file);
			// ����ͼƬ��Ϣ
			if (CatchScrn.a != null) {
				bmp.biHeight = CatchScrn.a.length;
				bmp.biWidth = CatchScrn.a[0].length;
				// �ж��Ƿ��ܱ�4����
				if (CatchScrn.a[0].length * 3 % 4 != 0) {
					temp = CatchScrn.a[0].length
							+ (4 - CatchScrn.a[0].length * 3 % 4);
					bmp.data = new int[bmp.biHeight][temp];
					// �������0
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
					// ������ɫ��
					// �����ɫ���С��2��λ��ȴη���
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
				// ͼ�����ļ��еĴ洢˳���Ǵ������ң����µ��ϵ�
				for (int i = CatchScrn.a.length - 1, j = 0; i >= 0; i--, j++) {
					for (int k = 0; k < CatchScrn.a[0].length; k++) {
						bmp.data[i][k] = CatchScrn.a[j][k];
					}
				}

				// д�ļ�����
				// дbmp�ļ�ͷ
				fos.write(BitMap.changeEndian(bmp.bfType));
				fos.write(BitMap.changeEndian(bmp.bfSize));
				fos.write(BitMap.changeEndian(bmp.bfReserved1));
				fos.write(BitMap.changeEndian(bmp.bfReserved2));
				fos.write(BitMap.changeEndian(bmp.bfOffBits));
				// ��Ϣͷ
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
				// ��ɫ��
				// ��λ���С��24ʱ�Ż��õ���ɫ��
				if (bmp.biBitCount < 24) {

					// ���λ���Ϊ1�����Ǻڰ�ͼƬ��
					if (bmp.biBitCount == 1) {
						// ���õ�ɫ��
						// ��ɫ��ֻ��2����ɫ
						for (int i = 0, t = 0; i < 2; i++) {
							// ��������Ϊ0
							bmp.color[i][3] = 0;
							for (int j = 0; j < 3; j++) {
								bmp.color[i][j] = t;
							}
							t = 0xff;
						}
						// д���ɫ��
						for (int i = 0; i < 2; i++) {
							for (int j = 0; j < 4; j++) {
								fos.write((byte) bmp.color[i][j]);
							}
						}
						// д�����ݵ�������(��24λλͼҪ�õ�ɫ������ֵ)
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
									// 1λλͼ��һ��BIT���һ������
									// 1λλͼ���Ǻ�ɫ���ǰ�ɫ
									if (new Color(bmp.data[i][j])
											.equals(Color.WHITE)) {
										t[count % 8] = 1;
									} else {
										t[count % 8] = 0;
									}

									count++;
								} else {
									// ���0������
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
					} // ���λ���Ϊ4
					else if (bmp.biBitCount == 4) {
						int i = 0;
						for (int j = 0; j < size; j++) {
							// �����ֶ���Ϊ0
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
						// д�����ݵ�������
						for (int j = 0; j < bmp.biHeight; j++) {
							for (int k = 0; k < bmp.data[0].length - 1; k += 2) {
								if (k + 1 < bmp.biWidth) {
									// �ҵ�ɫ������ӽ�����ɫ
									// �����ɫ������ֵ�±�
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
									// ������ֵд��ͼƬ������
									// ע��4λͼһ������ռ4��BIT,����ζ��һ��Ҫд����������
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
						// ���λ���Ϊ8��Ҳ����256ɫͼƬ

					}
				} else if (bmp.biBitCount == 24) {
					// ���λ�����24λ�Ļ����Ͳ���Ҫ��ɫ�壬ֱ�Ӱ���ɫд��������
					// ������
					for (int i = 0; i < bmp.data.length; i++) {
						for (int j = 0; j < bmp.data[0].length; j++) {
							if (j < bmp.biWidth) {
								Color color = new Color(bmp.data[i][j]);
								// д����ɫ����
								fos.write((byte) color.getBlue());
								// д����ɫ����
								fos.write((byte) color.getGreen());
								// д���ɫ����
								fos.write((byte) color.getRed());
							} else {
								// �����0
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
