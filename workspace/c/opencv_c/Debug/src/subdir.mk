################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../src/checker.c \
../src/detect.c \
../src/detector.c \
../src/opencv_c.c \
../src/record.c \
../src/recorder.c \
../src/s_main.c \
../src/s_stream.c \
../src/stream_client.c \
../src/stream_server.c 

OBJS += \
./src/checker.o \
./src/detect.o \
./src/detector.o \
./src/opencv_c.o \
./src/record.o \
./src/recorder.o \
./src/s_main.o \
./src/s_stream.o \
./src/stream_client.o \
./src/stream_server.o 

C_DEPS += \
./src/checker.d \
./src/detect.d \
./src/detector.d \
./src/opencv_c.d \
./src/record.d \
./src/recorder.d \
./src/s_main.d \
./src/s_stream.d \
./src/stream_client.d \
./src/stream_server.d 


# Each subdirectory must supply rules for building sources it contributes
src/%.o: ../src/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -I/usr/local/include/opencv -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


