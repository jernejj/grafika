package construction;

public class Generator {
	public static void main(String[]  args) {
		int n = 4;
//		for(int i = 0; i < Math.pow((double)2,(double)n); i++) {
//			for(int j = n;  j > 0; j--) {
//				System.out.print(((int)((i / (int) Math.pow(2.0, (double)(j))) % (int) Math.pow(2.0, (double)(j)))+" ")); 
//			}
//			System.out.print("\n");
//		}
//		System.out.print("\n\n"+((int)((3 / 8) % Math.pow(2.0, 3.0))+" ")); 
	
		short[][] tabela = new short[(int)Math.pow((double)2,(double)n)][n];
		String bin="";
		
		int indeks = 0;
		for(int i=0; i < tabela.length; i++) {
			bin=Integer.toBinaryString(i);
			indeks = bin.length();
			for(int j = tabela[0].length - 1; j >= tabela[0].length - bin.length(); j--)
				tabela[i][j] = Short.parseShort(String.valueOf((bin.charAt(--indeks))));
		}
		print(tabela);

	}
	

	private static void print(short[][] t) {
		for(int i = 0; i < t.length; i++) {
			for(int j = 0; j < t[0].length; j++ ) {
				System.out.print(t[i][j]+ " ");
			}
			System.out.println("");
		}
	}
}
