setForegroundColor(255, 255, 255);
setBackgroundColor(255, 255, 255);
for (i = 1; i < nSlices()+1; i++){
	setSlice(i);
	makeOval(293, 19, 31, 31);
	run("Clear", "slice");
	makePoint(311, 50);
}

run("Set Measurements...", "  centroid fit stack redirect=None decimal=4");
run("Analyze Particles...", "size=5-Infinity circularity=0.20-1.00 display clear add stack");
