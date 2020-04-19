import java.util.LinkedList;
import java.util.List;

/**
 * Defines an individual image of a digit and it label
 */
public class Image {
	/**
	 * The pixel values of the image
	 */
	int[][] data;

	/**
	 * The integer value of the digit shown in the image
	 */
	int label;

	/**
	 * Constructs image with 2D data
	 * @param imageData 2D array containing grayscale pixel values
	 * @param label integer value represented by the image
	 */
	public Image(int[][] imageData, int label) {
		data = imageData;
		this.label = label;
	}

	/**
	 * Constructs image given 1D data for 28x28 image and label
	 * @param imageData 1D array containing 784 pixel values
	 * @param label integer value represented by the image
	 */
	public Image(int[] imageData, int label) {
		data = new int[28][28];
		int i = 0;
		for(int y = 0; y < 28; y++) {
			for(int x = 0; x < 28; x++)
				data[y][x] = imageData[i++];
		}
		this.label = label;
	}

	/**
	 * Prints image using symbols for pixels
	 */
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

	/**
	 * Gets the one dimensional data for the image
	 * @return a double array containing the pixel values
	 */
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

	/**
	 * Returns the output layer based off of the label
	 * @return a double[10] where arr[n] = 1.0 where n is the value of the label
	 */
	public double[] getOutput() {
		double[] rv = new double[10];
		rv[label] = 1;
		return rv;
	}

	/**
	 * Loads the training data from file
	 * @return a linked list containing the training images
	 */
	public static LinkedList<Image> getTrainingImageList(){
		int[] labels = MnistReader.getLabels("assets/dataset/training labels");
		List<int[][]> imageData = MnistReader.getImages("training data");
		return getImageList(labels, imageData);
	}

	/**
	 * Loads the testing data from file
	 * @return a linked list containing the testing images
	 */
	public static LinkedList<Image> getTestImageList(){
		int[] labels = MnistReader.getLabels("assets/dataset/test labels");
		List<int[][]> imageData = MnistReader.getImages("assets/dataset/test data");
		return getImageList(labels, imageData);
	}

	/**
	 * Constructs a list of images based off the provided list of data and labels
	 * @param labels an integer array containing the labels of the images in order
	 * @param imageData a list containing the image data in order
	 * @return a linked list containing the images
	 */
	public static LinkedList<Image> getImageList(int[] labels, List<int[][]> imageData){
		assert labels.length == imageData.size();
		assert imageData.get(0).length == 28 && imageData.get(0)[0].length == 28;
		LinkedList<Image> rv = new LinkedList<>();
		for(int i = 0; i < labels.length; i++)
			rv.add(new Image(imageData.get(i), labels[i]));
		return rv;
	}
}
