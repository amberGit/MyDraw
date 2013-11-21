package john.bmp.draw;

public class BitMap {
	// 位图文件头
	public short bfType = 0x4d42;// 如果你的系统是微软的就必须设为0x424D (ASCII码为“BM”)
	public int bfSize;// 表示文件大小,可以直接写个0进去，系统会自动补上正确的文件大小
	public short bfReserved1 = 0;// 系统保留字 必须为0
	public short bfReserved2 = 0;// 系统保留字 必须为0
	public int bfOffBits = 54;// 表示真实的数据域是从文件的那个字节之后开始的
	// 位图信息头
	public int biSize = 40;
	public int biWidth;// 一定要写正确
	public int biHeight;// 一定要写正确
	public short biPlanes = 1;
	// 表示位深度，也就是每个像素在文件中占用多少空间24代表一个像素在文件中占用24/8个字节其它以此类推
	public short biBitCount = 1;// 一定要写正确

	public int biCompression = 0;// 压缩类型，bmp格式一般定为0
	public int biSizeImage = 0;// 可以无视它直接写个0
	public int biXPelsPerMeter = 0;// 垂直分辨率 直接写0
	public int biYPelsPerMeter = 0;// 水平分辨率 直接写0
	public int biClrUsed = 0;// 大胆的设为0
	public int biClrImportant = 0;// 设为0
	// 调色板
	// 如果位深度低于24才需要调色板
	public int color[][];

	// 位图数据
	public int data[][];

	// 存储顺序(little endian)
	public static byte[] changeEndian(int var) {
		byte temp[] = new byte[4];
		for (int i = 0; i < 4; i++) {
			temp[i] = (byte) (var >> (i * 8));
		}
		return temp;
	}

	public static byte[] changeEndian(short var) {
		byte temp[] = new byte[2];
		for (int i = 0; i < 2; i++) {
			temp[i] = (byte) (var >> (i * 8));
		}
		return temp;
	}
}
