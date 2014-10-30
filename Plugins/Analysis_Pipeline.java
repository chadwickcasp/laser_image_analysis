import ij.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import ij.plugin.filter.*;
import ij.measure.*;
import ij.plugin.filter.ParticleAnalyzer.*;
import ij.io.*;
import java.awt.*;
import ij.gui.*;
import ij.macro.*;
import ij.process.*;
import java.lang.Math.*;
import ij.measure.Measurements.*;
import ij.io.LogStream.*;

public class Analysis_Pipeline implements PlugIn {

	public boolean isPair(double dist1, double dist2){
		if (Math.abs(dist1 - dist2) <= 10.0){
			return true;
		}
		return false;
	}

	public void run(String args){
		
		LogStream ls = new LogStream();
		ls.redirectSystem();

		ImagePlus folder = new FolderOpener().open(null);
		if (folder == null){
			System.out.println("Error: Didn't give a valid image");
			return;
		}
		ImageStack stack = folder.getImageStack();
		if (stack == null){
			System.out.println("Error: Didn't give valid stack");
			return;
		}


		int size = stack.getSize();
		Macro_Runner m = new Macro_Runner();
		StackWindow w = new StackWindow(folder);
		
		//Center and Crop
		m.run("SetPoint.ijm");
		float[] xPoints = IJ.getImage().getRoi().getFloatPolygon().xpoints;
		float[] yPoints = IJ.getImage().getRoi().getFloatPolygon().ypoints;
		StackProcessor sp = new StackProcessor(stack);
		ImageStack s = sp.crop(((int) xPoints[0]) - 308, ((int) yPoints[0]) - 33, 616, 66);
		ImagePlus ip = new ImagePlus("Cropped Stack", s);
		w = new StackWindow(ip);

		//Run some macros
		m.run("RemoveOutliers.ijm");
		m.run("Colorize.ijm");
		m.run("RemoveMid.ijm");

	
		//Object Construction
		/*
		ResultsTable rest = new ResultsTable();
		ResultsTable newrt = new ResultsTable();

		RoiManager RM = new RoiManager();
		int ATM = ParticleAnalyzer.ADD_TO_MANAGER;
		int SO = ParticleAnalyzer.SHOW_OUTLINES;
		int SR = ParticleAnalyzer.SHOW_RESULTS;
		int SROI = ParticleAnalyzer.SHOW_ROI_MASKS;
		int CEN = Measurements.CENTROID;
		int ELL = Measurements.ELLIPSE;
		int SLICE = Measurements.SLICE;
		CustomParticleAnalyzer PA = new CustomParticleAnalyzer(ATM+SR, CEN+ELL+SLICE, rest, 3.0, Double.POSITIVE_INFINITY, 0.2, 1.0);


		//Analyze Particles
		for (int i = 1; i < size+1; i++){
			w.showSlice(i);
			PA.analyze(ip);
		}
		//rt.show("Results");
		//ResultsTable rest = PA.getResultsTable();
		//rest.updateResults();
		//rest.show("Results");
		*/
		
		m.run("Results_2.ijm");

		/*for (int t = 0; t < size+1; t++){
			Analyzer A = new Analyzer(ip);
			rt = A.getResultsTable();
		*/

		//Load the Results Table columns as arrays
		/*
		double[] partNum = rest.getColumnAsDoubles(0);
		double[] xVals = rest.getColumnAsDoubles(1);
		double[] yVals = rest.getColumnAsDoubles(2);
		double[] major = rest.getColumnAsDoubles(3);
		double[] minor = rest.getColumnAsDoubles(4);
		double[] angle = rest.getColumnAsDoubles(5);
		double[] rtSliceArray = rest.getColumnAsDoubles(6);
		
		if (xVals == null){
			System.out.println("xVals is null!");
		}

		//Match pairs of lasers points in the Results Table, and send them to a new one.
		for (int i = 0; i < partNum.length-1; i++){
			int rtSlice = ((int) rtSliceArray[i]);
			int sliceNum = rtSlice;
			double x = xVals[i] - 159.0; //Distance away from middle x
			double y = yVals[i] - 17.0; //Distance away from middle y
			if (x < 0){x += 159.0;}
			if (y < 0){y += 17.0;}
			double dist = Math.sqrt(x*x + y*y); //Distance away from middle
			int j = i + 1;
			rtSlice = ((int) rtSliceArray[j]);
			if (rtSlice != sliceNum){
				System.out.println("Checkpoint 2");	
			}
			while (rtSlice == sliceNum){
				System.out.println("Checkpoint 3");
				double xcand = xVals[j] - 159.0;
				double ycand = yVals[j] - 17.0;
				if (xcand < 0){xcand += 159.0;}
				if (ycand < 0){ycand += 17.0;}
				double distcand = Math.sqrt(xcand*xcand + ycand*ycand);
				if (isPair(distcand, dist)){
					for (int k = 0; k < 6; k++){
						newrt.addValue(k, partNum[i]);
						newrt.addValue(k, partNum[j]);	
					}
					rest.deleteRow(j);
					rest.deleteRow(i);
					partNum = rest.getColumnAsDoubles(0);
					xVals = rest.getColumnAsDoubles(1);
					yVals = rest.getColumnAsDoubles(2);
					major = rest.getColumnAsDoubles(3);
					minor = rest.getColumnAsDoubles(4);
					angle = rest.getColumnAsDoubles(5);
					rtSliceArray = rest.getColumnAsDoubles(6);
				}
				j++;
				rtSlice = ((int) rtSliceArray[j]);
			}
			sliceNum = rtSlice;
			if (j == i + 1){
				rest.deleteRow(i);
				partNum = rest.getColumnAsDoubles(0);
				xVals = rest.getColumnAsDoubles(1);
				yVals = rest.getColumnAsDoubles(2);
				major = rest.getColumnAsDoubles(3);
				minor = rest.getColumnAsDoubles(4);
				angle = rest.getColumnAsDoubles(5);
				rtSliceArray = rest.getColumnAsDoubles(6);
			}
		}
		*/
	}
}

class CustomParticleAnalyzer extends ParticleAnalyzer {

	public CustomParticleAnalyzer(int options, int measurements, ResultsTable rt, double minSize, double maxSize, double minCirc, double maxCirc){
		super(options, measurements, rt, minSize, maxSize, minCirc, maxCirc);
	}

	public ResultsTable getResultsTable() {
		ResultsTable rt = this.rt;
		return rt;
	}

	public double[] getColumnAsDoubles(int i){
		return this.rt.getColumnAsDoubles(i);
	}

}










