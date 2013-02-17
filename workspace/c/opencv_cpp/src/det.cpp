#include <iostream>
#include "stdlib.h"

#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>

#include "cv.h"
#include "highgui.h"

using namespace std;
using namespace cv;

int main(int argc, char* argv[]) {
	cvNamedWindow("My Window", CV_WINDOW_AUTOSIZE);
	CvCapture *input;

	//char *fileName = "E:\\highway.avi";
	input = cvCaptureFromCAM(0);

	//input = cvCaptureFromFile(fileName);

	CvSize imgSize;
	IplImage* frame = cvQueryFrame(input);
	imgSize = cvGetSize(frame);

	IplImage* greyImage = cvCreateImage(imgSize, IPL_DEPTH_8U, 1);
	IplImage* colourImage;
	IplImage* movingAverage = cvCreateImage(imgSize, IPL_DEPTH_32F, 3);
	IplImage* difference;
	IplImage* temp;
	IplImage* motionHistory = cvCreateImage(imgSize, IPL_DEPTH_8U, 3);

	CvRect bndRect = cvRect(0, 0, 0, 0);

	CvPoint pt1, pt2;
	CvFont font;
	char* out_file = "outputMovie.avi";
	CvVideoWriter* outputMovie = cvCreateVideoWriter(out_file,	CV_FOURCC('F', 'L', 'V', 'I'), 29.97, cvSize(720, 576));
	int prevX = 0;
	int numPeople = 0;
	char wow[65];

	int avgX = 0;

	bool first = true;

	int closestToLeft = 0;
	int closestToRight = 320;

	for (;;) {
		colourImage = cvQueryFrame(input);

		if (!colourImage) {
			break;
		}
		if (first) {
			difference = cvCloneImage(colourImage);
			temp = cvCloneImage(colourImage);
			cvConvertScale(colourImage, movingAverage, 1.0, 0.0);
			first = false;
		} else

		{
			cvRunningAvg(colourImage, movingAverage, 0.020, NULL);
		}

		cvConvertScale(movingAverage, temp, 1.0, 0.0);

		cvAbsDiff(colourImage, temp, difference);

		cvCvtColor(difference, greyImage, CV_RGB2GRAY);

		cvThreshold(greyImage, greyImage, 70, 255, CV_THRESH_BINARY);

		cvDilate(greyImage, greyImage, 0, 18);
		cvErode(greyImage, greyImage, 0, 10);

		CvMemStorage* storage = cvCreateMemStorage(0);
		CvSeq* contour = 0;

		cvFindContours(greyImage, storage, &contour, sizeof(CvContour),
				CV_RETR_CCOMP, CV_CHAIN_APPROX_SIMPLE);

		for (; contour != 0; contour = contour->h_next) {
			bndRect = cvBoundingRect(contour, 0);
			pt1.x = bndRect.x;
			pt1.y = bndRect.y;
			pt2.x = bndRect.x + bndRect.width;
			pt2.y = bndRect.y + bndRect.height;

			avgX = (pt1.x + pt2.x) / 2;

			if (avgX > 90 && avgX < 250) {
				if (closestToLeft >= 88 && closestToLeft <= 90) {
					if (avgX > prevX) {
						numPeople++;
						closestToLeft = 0;
					}
				} else if (closestToRight >= 250 && closestToRight <= 252) {
					if (avgX < prevX) {
						numPeople++;
						closestToRight = 320;
					}
				}
				cvRectangle(colourImage, pt1, pt2, CV_RGB(255,0,0), 1);
			}

			if (avgX > closestToLeft && avgX <= 90) {
				closestToLeft = avgX;
			}

			if (avgX < closestToRight && avgX >= 250) {
				closestToRight = avgX;
			}

			prevX = avgX;
			prevX = avgX;

		}

		cvInitFont(&font, CV_FONT_HERSHEY_SIMPLEX, 0.8, 0.8, 0, 2);
		cvPutText(colourImage, "Check one", cvPoint(60, 200),	&font, cvScalar(0, 0, 300));
		cvShowImage("My Window", colourImage);

		cvWaitKey(10);
		cvWriteFrame(outputMovie, colourImage);

	}

	cvReleaseImage(&temp);
	cvReleaseImage(&difference);
	cvReleaseImage(&greyImage);
	cvReleaseImage(&movingAverage);
	cvDestroyWindow("My Window");

	cvReleaseCapture(&input);
	cvReleaseVideoWriter(&outputMovie);

	return 0;

}
