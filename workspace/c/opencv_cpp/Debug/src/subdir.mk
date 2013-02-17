################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/det.cpp \
../src/imag_viewer.cpp \
../src/opencv_cpp.cpp 

OBJS += \
./src/det.o \
./src/imag_viewer.o \
./src/opencv_cpp.o 

CPP_DEPS += \
./src/det.d \
./src/imag_viewer.d \
./src/opencv_cpp.d 


# Each subdirectory must supply rules for building sources it contributes
src/%.o: ../src/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -I/usr/local/include/opencv -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


