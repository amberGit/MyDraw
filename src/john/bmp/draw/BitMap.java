package john.bmp.draw;

public class BitMap {
	// λͼ�ļ�ͷ
	public short bfType = 0x4d42;// ������ϵͳ��΢��ľͱ�����Ϊ0x424D (ASCII��Ϊ��BM��)
	public int bfSize;// ��ʾ�ļ���С,����ֱ��д��0��ȥ��ϵͳ���Զ�������ȷ���ļ���С
	public short bfReserved1 = 0;// ϵͳ������ ����Ϊ0
	public short bfReserved2 = 0;// ϵͳ������ ����Ϊ0
	public int bfOffBits = 54;// ��ʾ��ʵ���������Ǵ��ļ����Ǹ��ֽ�֮��ʼ��
	// λͼ��Ϣͷ
	public int biSize = 40;
	public int biWidth;// һ��Ҫд��ȷ
	public int biHeight;// һ��Ҫд��ȷ
	public short biPlanes = 1;
	// ��ʾλ��ȣ�Ҳ����ÿ���������ļ���ռ�ö��ٿռ�24����һ���������ļ���ռ��24/8���ֽ������Դ�����
	public short biBitCount = 1;// һ��Ҫд��ȷ

	public int biCompression = 0;// ѹ�����ͣ�bmp��ʽһ�㶨Ϊ0
	public int biSizeImage = 0;// ����������ֱ��д��0
	public int biXPelsPerMeter = 0;// ��ֱ�ֱ��� ֱ��д0
	public int biYPelsPerMeter = 0;// ˮƽ�ֱ��� ֱ��д0
	public int biClrUsed = 0;// �󵨵���Ϊ0
	public int biClrImportant = 0;// ��Ϊ0
	// ��ɫ��
	// ���λ��ȵ���24����Ҫ��ɫ��
	public int color[][];

	// λͼ����
	public int data[][];

	// �洢˳��(little endian)
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
