laser_image_analysis
====================

Software to speed up image analysis in ImageJ

#### Installation:

- Download the latest version of ImageJ
- Copy all files in the 'Macros' folder and paste them into the 'plugins' folder within your ImageJ directory.
- In ImageJ, navigate to 'Plugins' > 'Install...' and choose the Analysis_Pipeline_v1.java and Coupling_Pipeline
  .java files.
- The dialog will prompt you to save them in the 'plugins' folder of your ImageJ directory. Navigate to 'Analyze'
  and save the files there.

#### Files and descriptions:

###### Analysis_Pipeline: 
  The pipeline used to analyze the laser images. When results put into spreadsheet, this calculates the high index 
  fluid fill or height of SU8 features.

###### Directions:
  Upon running, in the 'File name contains: ' box type 'Fill' or whatever keyword you use to distinguish your 
  laser images. After the prompts stop showing up, navigate to the ROI Manager Window, hit 'Ctrl+A' and click 
  on 'Measure'. Then bring up the 'Results' window and hit 'Ctrl+A' again and'Ctrl+C' to copy the results into 
  the spreadsheet.

###### Macros called in Analysis_Pipeline:
  - SetPoint.ijm - Marks the center of the image and uses this as a reference point.
  - CropStack.ijm - The laser points should be equidistant from the reference point. This crops out all image features
    that are outside of the laser point's range.
  - Colorize.ijm - This module correctly thresholds the image and converts it into 8-bit in order to perform particle
    analysis
  - LaserSelect.ijm - Allows the laser points to be manually selected. This is to be run when the automation is failing.

###### Coupling_Pipeline:
  The pipeline used to analyze coupling region images. When results put into the spreadsheet, this calculates the low
  index fill.

###### Directions:
  Upon running, in the 'File name contains: ' box type 'Coup', or the keyword you use to distinguish the coupling
  region images. Follow the prompts by fitting ellipses to the regions. After the prompts stop showing up, navigate
  to the ROI Manager Window hit 'Ctrl+A' and click on 'Measure'. Then bringup the 'Results' window and hit 'Ctrl+A'
  again and 'Ctrl+C' to copy the results into the spreadsheet.

###### Macros called in Coupling_Pipeline:
- SetPoint.ijm - Marks the center of the image and uses this as a reference point.
- RegionSelect.ijm - Allows the coupling regions to be manually selected. Automation was more difficult to implement
    for the coupling pipeline, but this speeds up selection of the coupling regions.

