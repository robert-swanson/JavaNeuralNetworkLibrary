import java.util.LinkedList;
import java.util.List;

public class Image {
	int[][] data;
	int label;
	
	public Image(int[][] imageData, int label) {
		data = imageData;
		this.label = label;
	}
	public Image(int[] imageData, int label) {
		data = new int[28][28];
		int i = 0;
		for(int y = 0; y < 28; y++) {
			for(int x = 0; x < 28; x++)
				data[y][x] = imageData[i++];
		}
		this.label = label;
	}
	
	
	public void roughPrint() {
		for(int y = 0; y < data.length; y++) {
			for(int x = 0; x < data.length; x++) {
				int n = data[y][x];
				char c = ' ';
				if(n > 250) c = '•';
				else if(n > 200) c = '#';
				else if(n > 150) c = '*';
				else if(n > 100) c = '-';
				else if(n > 50) c = '.';
				System.out.print(c);
			}
			System.out.println();
		}
	}
	
	public double[] getInput() {
		double[] rv = new double[784];
		int i = 0;
		for(int y = 0; y < data.length; y++) {
			for(int x = 0; x < data[y].length; x++) {
				rv[i++] = data[y][x];
			}
		}
		return rv;
	}

	public double[] getOutput() {
		double[] rv = new double[10];
		rv[label] = 1;
		return rv;
	}
	
	public static LinkedList<Image> getTrainingImageList(){
		int[] labels = MnistReader.getLabels("assets/dataset/training labels");
		List<int[][]> imageData = MnistReader.getImages("training data");
		return getImageList(labels, imageData);
	}
	public static LinkedList<Image> getTestImageList(){
		int[] labels = MnistReader.getLabels("assets/dataset/test labels");
		List<int[][]> imageData = MnistReader.getImages("assets/dataset/test data");
		return getImageList(labels, imageData);
	}
	private static LinkedList<Image> getImageList(int[] labels, List<int[][]> imageData){
		assert labels.length == imageData.size();
		assert imageData.get(0).length == 28 && imageData.get(0)[0].length == 28;
		LinkedList<Image> rv = new LinkedList<>();
		for(int i = 0; i < labels.length; i++)
			rv.add(new Image(imageData.get(i), labels[i]));
		return rv;
	}
}
