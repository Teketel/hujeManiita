################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/face_detector.cpp \
../src/motion_detector.cpp \
../src/test_cpp.cpp 

OBJS += \
./src/face_detector.o \
./src/motion_detector.o \
./src/test_cpp.o 

CPP_DEPS += \
./src/face_detector.d \
./src/motion_detector.d \
./src/test_cpp.d 


# Each subdirectory must supply rules for building sources it contributes
src/%.o: ../src/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -I/usr/local/include/opencv -I/usr/local/include -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

