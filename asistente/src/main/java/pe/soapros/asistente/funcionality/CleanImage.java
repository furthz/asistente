package pe.soapros.asistente.funcionality;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;

public class CleanImage {

	@Autowired
	public static void main(String[] args) {

		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			Mat mat = Imgcodecs.imread(
					"D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\scripts\\resizeimg.png");

			Mat matGris = new Mat(); // (image.getHeight(),image.getWidth(),CvType.CV_8UC1);
			Imgproc.cvtColor(mat, matGris, Imgproc.COLOR_RGB2GRAY);

			Mat matCanny = new Mat();// (image.getHeight(),image.getWidth(),CvType.CV_8UC1);
			Imgproc.Canny(matGris, matCanny, 50, 200);

			Mat lineas = new Mat();
			Imgproc.HoughLines(matCanny, lineas, 1, Math.PI / 720, 100);

			HashMap<Double, Double> angulos = new HashMap<Double, Double>();

			for (int i = 0; i < lineas.cols(); i++) {

				double datos[] = lineas.get(0, i);
				double rho = datos[0];
				Double theta = new Double(datos[1]);

				System.out.println(Math.toDegrees(theta));
				
				if (rho < 0)
					theta = theta * -1;

				if (angulos.containsKey(theta)) {
					Double value = angulos.get(theta);
					angulos.put(theta, value + 1);
				} else {
					angulos.put(theta, 1.0);
				}

				

			}

			// calcular el máximo dentro de los angulos

			Double anguloMax = 0.0;

			for (Double value : angulos.values()) {
				if (value > anguloMax) {
					anguloMax = value;
				}
			}

			System.out.println("angulo max: " + Math.toDegrees(anguloMax));
			System.out.println("angulo sin conv max: " + (anguloMax - Math.PI/2));
			
			anguloMax = Math.toDegrees(anguloMax - Math.PI/2);
			
			System.out.println(anguloMax);
			
			
			//Buscar los bordes y adicionar los puntos
			List<Point> p = new ArrayList<Point>();
			for(int i = 0; i < mat.rows(); i++) {
				for(int j = 0; j < mat.cols(); j++) {
					double pixel = mat.get(i, j)[0];
					if(pixel != 0.0) {
						p.add(new Point(i,j));
					}
				}
			}
			
			
			//convertir del arraylist a un array de puntos
			Point[] array_point = new Point[p.size()]; 
					
			array_point = p.toArray(array_point);
			
			MatOfPoint2f points = new MatOfPoint2f(array_point);
			
			//rotar texto
			RotatedRect box = Imgproc.minAreaRect(points);
			Mat rot_mat = Imgproc.getRotationMatrix2D(box.center, anguloMax, 1);
			Mat rotated = new Mat();
	        Imgproc.warpAffine(mat, rotated, rot_mat, mat.size(), Imgproc.INTER_CUBIC);
	        
	        
	        Mat cropped = new Mat();
	        Size box_size = box.size;
	        
	        if (box.angle < -45.) {
	            //std::swap(box_size.width, box_size.height);
	            double aux = box_size.width;
	            box_size.width = box_size.height;
	            box_size.height = aux;
	        }
	        
	        Imgproc.getRectSubPix(rotated, box_size, box.center, cropped);
			
	        Imgcodecs.imwrite("D:\\Documents\\Proyectos\\Bancolombia\\Asistente Financiero\\EEFF\\scripts\\prueba.png", rotated);

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
