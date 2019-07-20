import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Tester {
	LinkedList<Image> images;

	public static void main(String[] args) {
		System.out.println("Press enter to move on");
		Tester t = new Tester();
		t.test();
	}
	public void test() {
		images = Image.getTestImageList();
		Scanner in = new Scanner(System.in);
//		while(in.hasNextLine()) {
//			browse();
//			in.nextLine();
//		}
//		while(in.hasNextLine()) {
//			getDrawing();
//			in.nextLine();
//		}
		while(in.hasNextLine()) {
			in.nextLine();
		}
	}

	public int getGrayScale(int r, int g, int b) {
		return 255-(int)(r * 0.299 + g * 0.587 + b * 0.114);
	}
	public void print(double[] a) {
		System.out.print("[");
		for (int i = 0; i < a.length - 1; i++)
			System.out.print(a[i] + ", ");
		System.out.println(a[a.length - 1] + "]");
	}
	public void print(int[] a) {
		System.out.print("[");
		for (int i = 0; i < a.length - 1; i++)
			System.out.print(a[i] + ", ");
		System.out.println(a[a.length - 1] + "]");
	}

	public void getDrawing() {
		BufferedImage img;
		NN1 nn = new NN1();
		try {
			img = ImageIO.read(new File("number.png"));
			int width = img.getWidth();
			int height = img.getHeight();
			int[] startPixels = new int[width*height*4];
			img.getRaster().getPixels(width/2-14, height/2-14, 28, 28, startPixels);
			int[] imageData = new int[784];
			int j = 0;
			int i = 0;
			while(i < 3136) {
				imageData[j] = getGrayScale(startPixels[i], startPixels[i+1], startPixels[i+2]);
				//				System.out.printf("\n%3d",imageData[j]);
				j++;
				i+=4;
				while(i%(112) != 0) {
					imageData[j] = getGrayScale(startPixels[i], startPixels[i+1], startPixels[i+2]);
					//					System.out.printf("%3d",imageData[j]);
					i+=4;
					j++;
				}
			}
			System.out.println();
			Image image = new Image(imageData, 5);
			image.roughPrint();
			System.out.println(nn.classify(image));
			double[] out = nn.getOutput();
			double min = 1;
			double max = 0;
			for(double d: out) {
				min = Math.min(min, d);
				max = Math.max(max, d);
			}
			for(int d = 0; d < 10; d++) {
				double val = (out[d]-min)/(max-min)*10;
				System.out.printf("%d (%.2f):",d,out[d]);
				for(int ij = 0; ij < val; ij++) {
					System.out.print("*");
				}
				System.out.println();
			}
		} catch (IOException e) {
			System.err.println("unable to load image");
		}

	}

	public void browse() {
		NN1 nn = new NN1();
		Image image = images.get((int)(Math.random()*images.size()));
		image.roughPrint();
		System.out.printf("Real: %d Guess: %d\n",image.label,nn.classify(image));
		double[] out = nn.getOutput();
		double min = 1;
		double max = 0;
		for(double d: out) {
			min = Math.min(min, d);
			max = Math.max(max, d);
		}
		for(int d = 0; d < 10; d++) {
			double val = (out[d]-min)/(max-min)*10;
			System.out.printf("%d (%.2f):",d,out[d]);
			for(int ij = 0; ij < val; ij++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}
	
	

}
