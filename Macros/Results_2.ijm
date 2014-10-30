selectWindow("Results");

//Arrays representing columns in the "Results" table of the particle analysis done prior.
partNum = newArray(nResults);
xVals = newArray(nResults);
yVals = newArray(nResults);
Maj = newArray(nResults);
Min = newArray(nResults);
Angle = newArray(nResults);
sliceArray = newArray(nResults);

//Arrays representing columns of the final "Resultas" table
newpartNum = newArray(nResults);
newxVals = newArray(nResults);
newyVals = newArray(nResults);
newMaj = newArray(nResults);
newMin = newArray(nResults);
newAngle = newArray(nResults);
newsliceArray = newArray(nResults);

//Fill the initial arrays
for (i = 0; i < nResults(); i++){
	partNum[i] = getResult("Particle Number", i);
	xVals[i] = getResult("X", i);
	yVals[i] = getResult("Y", i);
	Maj[i] = getResult("Major", i);
	Min[i] = getResult("Minor", i);
	Angle[i] = getResult("Angle", i);
	sliceArray[i] = getResult("Slice", i);
}

//Function to check for pairs of laser points within a certain distance threshold
//function isPair(dist1, dist2){
//	if (abs(dist1 - dist2) <= 13.0){
//		return true;
//	}
//	return false;
//}


count = 0;

//Loop through all laser points from particle analysis
for (i = 0; i < nResults()-1; i++){

		//Initialize slice number and distance properties of original laser point
		rtSlice = sliceArray[i];
		sliceNum = rtSlice;
		x = xVals[i] - 159.0; //Distance away from central x-coordinate
		y = yVals[i] - 17.0; //Distance away from middle y-coordinate
		dist = sqrt(x*x + y*y); //Distance away from center for an original laser point
		j = i + 1;
		rtSlice = sliceArray[j];

		//Initialize 
		xcand = xVals[j] - 159.0;
		ycand = yVals[j] - 17.0;
		distcand = sqrt(xcand*xcand + ycand*ycand);
		minDiff = abs(dist - distcand);
		minJ = j;
		minSlice = rtSlice;
		
		//Loop through all laser points in a given slice, and compare their distances to the original 
		while (rtSlice == sliceNum && j < nResults()){
			xcand = xVals[j] - 159.0;
			ycand = yVals[j] - 17.0;
			distcand = sqrt(xcand*xcand + ycand*ycand);
			distDiff = abs(distcand-dist);

			//Minimize distance of laser points to original laser point, and save properties of this laser point to refer to it later
			if (distDiff < minDiff){
				minDiff = distDiff;
				minJ = j;
				minSlice = rtSlice;
			}
			j++;
			if (j < nResults()){
				rtSlice = sliceArray[j];
			}
		}

		//Check that the closest laser point to the original isn't so far away, and that the laser appears on same slice as original
		if (minDiff < 15 && sliceNum == minSlice){

			//Add the laser points to the new results table, cause we found a pair!
			newpartNum[count] = parseInt(count+1);
			newxVals[count] = xVals[i];
			newyVals[count] = yVals[i];
			newMaj[count] = Maj[i];
			newMin[count] = Min[i];
			newAngle[count] = Angle[i];
			newsliceArray[count] = sliceArray[i];
			count+=1;
			newpartNum[count] = count+1;
			newxVals[count] = xVals[minJ];
			newyVals[count] = yVals[minJ];
			newMaj[count] = Maj[minJ];
			newMin[count] = Min[minJ];
			newAngle[count] = Angle[minJ];
			newsliceArray[count] = sliceArray[minJ];
			count+=1;
		}
	}

//Show results
Array.show("Resultas", newpartNum, newxVals, newyVals, newMaj, newMin, newAngle);

selectWindow("ROI Manager");
close();
selectWindow("Results");
close();
close("Crop*");

