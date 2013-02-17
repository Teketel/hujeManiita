################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
/media/tekes/248A0E588A0E2740/AllOsInOne/VB/final\ project/Home-security/c/ye_nashurdin/stream_client.c \
/media/tekes/248A0E588A0E2740/AllOsInOne/VB/final\ project/Home-security/c/ye_nashurdin/stream_server.c 

OBJS += \
./ye_nashurdin/stream_client.o \
./ye_nashurdin/stream_server.o 

C_DEPS += \
./ye_nashurdin/stream_client.d \
./ye_nashurdin/stream_server.d 


# Each subdirectory must supply rules for building sources it contributes
ye_nashurdin/stream_client.o: /media/tekes/248A0E588A0E2740/AllOsInOne/VB/final\ project/Home-security/c/ye_nashurdin/stream_client.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -I/usr/local/include/opencv -I/usr/local/include -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

ye_nashurdin/stream_server.o: /media/tekes/248A0E588A0E2740/AllOsInOne/VB/final\ project/Home-security/c/ye_nashurdin/stream_server.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -I/usr/local/include/opencv -I/usr/local/include -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


