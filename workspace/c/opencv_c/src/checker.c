/*
 * checker.c
 *
 *  Created on: Jan 18, 2013
 *      Author: tekes
 */

#include <cv.h>
#include <highgui.h>
#include <stdio.h>
#include <stdlib.h>
IplImage* g_image = NULL;
IplImage* g_gray = NULL;
int main(int argc, char** argv) {
	//declare for the height and width of the image
	int height = 320;
	int width = 240;
	//specify the point to place the text
	CvPoint pt = cvPoint(height / 4, width / 2);
	//Create an 8 bit, 3 plane image
	IplImage* hw = cvCreateImage(cvSize(height, width), 8, 3);
	//initialize the font
	CvFont font;
	cvInitFont(&font, CV_FONT_HERSHEY_COMPLEX, 1.0, 1.0, 0, 1, CV_AA);
	//place the text on the image using the font
	cvPutText(hw, "Hello World", pt, &font, CV_RGB(150, 0, 0));
	//create the window container
	cvNamedWindow("Hello World", 0);
	//display the image in the container
	cvShowImage("Hello World", hw);
	//hold the output windows
	cvWaitKey(0);
	return 0;
}
