package firstTest;



import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.polito.elite.teaching.cv.Utils;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ImageSelect {
	
	public void Load(ImageView imageView, Button btn_analyze) {
		try {
	
				Image img;
				FileChooser chooser = new FileChooser();
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg)", "*.png","*.jpg");
				chooser.getExtensionFilters().add(extFilter);
				File selectFile = chooser.showOpenDialog(null);
			
				if(selectFile != null) {
					
						ImageSelect extraction = new ImageSelect();
						extraction.FeatureExtraction(selectFile);
						img = new Image(selectFile.toURI().toString());
						imageView.setImage(img);
				
					
				}
				else {
				System.out.println("File is not valid");
				}
					
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	protected void updateImageView(ImageView imageView, Image imageToShow) {
		Utils.onFXThread(imageView.imageProperty(), imageToShow);
	}

	private void FeatureExtraction(File input) throws Exception {
		
		Mat originalImage = Imgcodecs.imread(input.toString());
		
		Size sz = new Size(800,500);
		
		Imgproc.resize( originalImage, originalImage, sz );
		
		Mat treatedHImage = new Mat(originalImage.rows(),originalImage.cols(),originalImage.type());
		
		originalImage.convertTo(treatedHImage, -1, 2, 0);
		
		Mat treatedLImage = new Mat(originalImage.rows(),originalImage.cols(),originalImage.type());
		
		originalImage.convertTo(treatedLImage, -1, 0.1, 0);
		
		Mat grayImage = new Mat(originalImage.rows(),originalImage.cols(),originalImage.type());
		
		Mat binaryImage = new Mat(originalImage.rows(),originalImage.cols(),originalImage.type());
		
		Mat edgeImage = new Mat(originalImage.rows(),originalImage.cols(),originalImage.type());
		
		String originalFile = "C:/Users/Bruno Vilarouca/Desktop/teste/originalImage.jpg";
		
		String treatedHFile = "C:/Users/Bruno Vilarouca/Desktop/teste/treatedHImage.jpg";
		
		String treatedLFile = "C:/Users/Bruno Vilarouca/Desktop/teste/treatedLImage.jpg";
		
		Imgcodecs.imwrite(originalFile,originalImage);
		
		Imgcodecs.imwrite(treatedHFile,treatedHImage);
		
		Imgcodecs.imwrite(treatedLFile,treatedLImage);
						
		Imgproc.cvtColor(originalImage, grayImage, Imgproc.COLOR_RGB2GRAY);
		
		Imgproc.Canny(grayImage,edgeImage,50,150,3,false);
		
		 int x = grayImage.cols()/4;
		 int y = grayImage.rows()/4;
		    
		 int k = 0;

		 for(int i=0; i<4; i++){
		        for(int j=0; j<4; j++){
		        	k++;
		        	Rect roi = new Rect(new Point(i*x ,j*y), new Point((i+1)*x, (j+1)*y));
		            Mat submat = grayImage.submat(roi);
		            Mat histPieceGray = showHistogram(submat,true);
		            String piece = "C:/Users/Bruno Vilarouca/Desktop/teste/Pieces/piece" + Integer.toString(k) + ".jpg";
		            Imgcodecs.imwrite(piece,submat);
		            String histPiece = "C:/Users/Bruno Vilarouca/Desktop/teste/Pieces/histPiece" + Integer.toString(k) + ".jpg";
		            Imgcodecs.imwrite(histPiece,histPieceGray);
		            
		        }
		  }
		
		Imgproc.threshold(grayImage, binaryImage,100,255, Imgproc.THRESH_BINARY);
		
		Mat histOriginal = showHistogram(originalImage,false);
		
		Mat histGray = showHistogram(grayImage,true);
		
		Mat histBinary = showHistogram(binaryImage,true);
		
		String edgeFile = "C:/Users/Bruno Vilarouca/Desktop/teste/edgeImage.jpg";
		
		String grayFile = "C:/Users/Bruno Vilarouca/Desktop/teste/grayImage.jpg";
		
		String binaryFile = "C:/Users/Bruno Vilarouca/Desktop/teste/binaryImage.jpg";
		
		String originalHistFile = "C:/Users/Bruno Vilarouca/Desktop/teste/histOriginal.jpg";
		
		String grayHistFile = "C:/Users/Bruno Vilarouca/Desktop/teste/histGray.jpg";
		
		String binHistFile = "C:/Users/Bruno Vilarouca/Desktop/teste/histBinary.jpg";
		
		Imgcodecs.imwrite(edgeFile,edgeImage);
		
		Imgcodecs.imwrite(originalHistFile,histOriginal);
		
		Imgcodecs.imwrite(grayHistFile,histGray);
		
		Imgcodecs.imwrite(binHistFile,histBinary);
		
		Imgcodecs.imwrite(grayFile,grayImage);
		
		Imgcodecs.imwrite(binaryFile,binaryImage);
			
	}

	private Mat showHistogram(Mat frame, boolean gray) {
		// split the frames in multiple images
		List<Mat> images = new ArrayList<Mat>();
		Core.split(frame, images);

		// set the number of bins at 256
		MatOfInt histSize = new MatOfInt(256);
		// only one channel
		MatOfInt channels = new MatOfInt(0);
		// set the ranges
		MatOfFloat histRange = new MatOfFloat(0, 256);

		// compute the histograms for the B, G and R components
		Mat hist_b = new Mat();
		Mat hist_g = new Mat();
		Mat hist_r = new Mat();

		// B component or gray image
		Imgproc.calcHist(images.subList(0, 1), channels, new Mat(), hist_b, histSize, histRange, false);

		// G and R components (if the image is not in gray scale)
		if (!gray) {
			Imgproc.calcHist(images.subList(1, 2), channels, new Mat(), hist_g, histSize, histRange, false);
			Imgproc.calcHist(images.subList(2, 3), channels, new Mat(), hist_r, histSize, histRange, false);
		}

		// draw the histogram
		int hist_w = 500; // width of the histogram image
		int hist_h = 500; // height of the histogram image
		int bin_w = (int) Math.round(hist_w / histSize.get(0, 0)[0]);
		Mat histImage = new Mat(hist_h, hist_w, CvType.CV_8UC3, new Scalar(0, 0, 0));

		// normalize the result to [0, histImage.rows()]
		Core.normalize(hist_b, hist_b, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());

		// for G and R components
		if (!gray) {
			Core.normalize(hist_g, hist_g, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());
			Core.normalize(hist_r, hist_r, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());
		}

		// effectively draw the histogram(s)
		for (int i = 1; i < histSize.get(0, 0)[0]; i++) {
			// B component or gray image
			Imgproc.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_b.get(i - 1, 0)[0])),
					new Point(bin_w * (i), hist_h - Math.round(hist_b.get(i, 0)[0])), new Scalar(255, 0, 0), 2, 8, 0);
			// G and R components (if the image is not in gray scale)
			if (!gray) {
				Imgproc.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_g.get(i - 1, 0)[0])),
						new Point(bin_w * (i), hist_h - Math.round(hist_g.get(i, 0)[0])), new Scalar(0, 255, 0), 2, 8,
						0);
				Imgproc.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_r.get(i - 1, 0)[0])),
						new Point(bin_w * (i), hist_h - Math.round(hist_r.get(i, 0)[0])), new Scalar(0, 0, 255), 2, 8,
						0);
			}
		}
		// display the histogram...
		
		
		return histImage;
	}
}
