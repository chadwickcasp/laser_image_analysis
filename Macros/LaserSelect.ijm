setTool("wand");

function select(){
	for (i = 0; i < 2; i++){
		waitForUser("Please Select...", "Select laser points to analyze. Press OK when finished.");
		roiManager("Add");
	}
	
	if (getBoolean("Are there any more laser points to analyze?")){
		return select();
	} else{
		return;
	}	
}

select();

//run("Analyze Particles...", "circularity=0.20-1.00 display clear slice");
