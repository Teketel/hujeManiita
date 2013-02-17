#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include "cv.h"
#include "highgui.h"

int main(int argc, char** argv) {
	CvCapture* capture = cvCaptureFromCAM(0); // capture from video device #0
	//CvCapture* capture = cvCaptureFromAVI("infile.avi");
	//IplImage* img = 0;

	if (!cvGrabFrame(capture)) {              // capture a frame
		printf("Could not grab a frame\n\7");
		exit(0);
	}
	//img = cvRetrieveFrame(capture);
	CvVideoWriter *writer = 0;
	int isColor = 1;
	int fps = 30;  // or 30
	int frameW = 640; // 744 for firewire cameras
	int frameH = 480; // 480 for firewire cameras
	writer = cvCreateVideoWriter("out2.avi", CV_FOURCC('P', 'I', 'M', '1'), fps,
			cvSize(frameW, frameH), isColor);
	IplImage* img = 0;
	int nFrames = 950;
	int i = 0;
	for (i = 0; i < nFrames; i++) {
		cvGrabFrame(capture);          // capture a frame
		img = cvQueryFrame(capture);  // retrieve the captured frame
		cvWriteFrame(writer, img);      // add the frame to the file
	}
}
