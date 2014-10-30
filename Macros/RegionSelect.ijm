setTool("oval");
waitForUser("Please Select...", "Approximate a coupling region to analyze. Press OK when finished.")
roiManager("Add");

waitForUser("Now Move...", "Move the current ROI over to the other coupling region to fit it as best as possible. Press OK when finished.")

run("Add to Manager");

//roiManager("Select", newArray(1,2));
//roiManager("Combine");
//run("Clear Outside");
//roiManager("Fill");
//close("ROI*");
//runMacro("BackgroundAnalyzeCoup.ijm");
