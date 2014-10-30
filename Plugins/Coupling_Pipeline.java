import ij.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import ij.io.*;
import java.awt.*;
import ij.gui.*;
import ij.macro.*;
import ij.process.*;

public class Coupling_Pipeline implements PlugIn {

	public void run(String args){

		//Opening image directory and setting up objects for use.
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
		w.showSlice(1);

		//Cropping and pre-processing
		/*
		m.run("SetPoint.ijm");
		float[] xpoints = IJ.getImage().getRoi().getFloatPolygon().xpoints;
		float[] ypoints = IJ.getImage().getRoi().getFloatPolygon().ypoints;
		StackProcessor sp = new StackProcessor(stack);
		ImageStack s = sp.crop(((int) xpoints[0]) - 700, (int) ypoints[0] - 300, 1400, 600);
		ImagePlus ip = new ImagePlus("Cropped Stack", s);
		w = new StackWindow(ip);
		*/


		//Elliptical ROI Selection
		for (int i = 1; i < size+1; i++){
			w.showSlice(i);
			m.run("RegionSelect.ijm");
		}
	}
}
