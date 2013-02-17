################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
/media/tekes/248A0E588A0E2740/AllOsInOne/VB/final\ project/Home-security/c/streamer.c 

OBJS += \
./streamer/streamer.o 

C_DEPS += \
./streamer/streamer.d 


# Each subdirectory must supply rules for building sources it contributes
streamer/streamer.o: /media/tekes/248A0E588A0E2740/AllOsInOne/VB/final\ project/Home-security/c/streamer.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross GCC Compiler'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


