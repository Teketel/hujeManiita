################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../ye_nasurdin_be_linux/recorder.c \
../ye_nasurdin_be_linux/stream_client.c \
../ye_nasurdin_be_linux/stream_server.c 

OBJS += \
./ye_nasurdin_be_linux/recorder.o \
./ye_nasurdin_be_linux/stream_client.o \
./ye_nasurdin_be_linux/stream_server.o 

C_DEPS += \
./ye_nasurdin_be_linux/recorder.d \
./ye_nasurdin_be_linux/stream_client.d \
./ye_nasurdin_be_linux/stream_server.d 


# Each subdirectory must supply rules for building sources it contributes
ye_nasurdin_be_linux/%.o: ../ye_nasurdin_be_linux/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -I/usr/local/include/opencv -I/usr/local/include -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


