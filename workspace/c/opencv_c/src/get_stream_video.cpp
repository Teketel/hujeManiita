/*
 * stream.cpp
 *
 *  Created on: Feb 17, 2013
 *      Author: tekes
 *      Copyright (c) 2009 Adii Corporation \n All Right Reserved.
 */
#include "opencv/cv.h"
#include "opencv/highgui.h"
#include <iostream>

int main(int, char**) {
    cv::VideoCapture vcap;
    cv::Mat image;

    const std::string videoStreamAddress = "http://hg55.no-ip.org/mjpg/video.mjpg";
    //Yes, this stream does work! Try to paste it into your browser...

    //open the video stream and make sure it's opened
    if(!vcap.open(videoStreamAddress)) {
        std::cout << "Error opening video stream or file" << std::endl;
        return -1;
    }

    for(;;) {
        if(!vcap.read(image)) {
            std::cout << "No frame" << std::endl;
            cv::waitKey();
        }
        cv::imshow("Output Window", image);
        if(cv::waitKey(1) >= 0) break;
    }
}



