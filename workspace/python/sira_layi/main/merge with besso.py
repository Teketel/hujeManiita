import web
import my_gcm
import constant_utilities
import serial, threading
import os


def startStream():
    os.system("vlc-wrapper -vvv v4l2:///dev/video0 --sout '#transcode{vcodec=MJPG,vb=800,scale=1,width=480,height=320,acodec=none}:duplicate{dst=standard{access=http,mux=mpjpeg,dst=:8081/play}}'")
    
def stopStream():
    os.system("killall vlc")
    

class HandleRegister:

    def GET(self):
        
        print "\n\t**********************************************"
        print "             In side HandleRegister"
        print "\t**********************************************"
        i = web.input()
        r_id = i.r_id
        passWd = str(i.email)
        if passWd != constant_utilities.PASSCODE:
            return "Login error, Password incorrect"
        print "I: Registration id = " + str(r_id)
        print "Name = " + str(i.name)
        print "Password = " + str(i.email)
        my_gcm.registerWithId(r_id, str(i.name), str(i.email))
        constant_utilities.REG_ID = str(r_id)
        return 'Successfully registered' + r_id
                
class HandleUnRegister:

    def GET(self):
        
        print "\n\t**********************************************"
        print "              In side HandleUnRegister"
        print "\t**********************************************"
        i = web.input()
        r_id = i.r_id
        passWd = str(i.email)
        if passWd != constant_utilities.PASSCODE:
            return "Login error, Password incorrect"
        print "I: Registration id = " + str(r_id)
        #my_gcm.unregisterWithId(r_id)
        return 
        
class HandleStatus:
    def GET(self):
        
        print "\n\t**********************************************"
        print "              In side HandleStatus"
        print "\t**********************************************"
        #status = s.read()
        i = web.input()
        passWd = str(i.email)
        if passWd != constant_utilities.PASSCODE:
            return "Login error, Password incorrect"
        status = "1,0,1,1,1,1,false"
        return status
        
class HandleStream:

    def GET(self):
        
        print "\n\t**********************************************"
        print "              In side HandleStream"
        print "\t**********************************************"
        i = web.input()
        passWd = str(i.email)
        print passWd
        if passWd != constant_utilities.PASSCODE:
            return "Login error, Password incorrect"
        code = i.code
        if code == "1":
            threading.Thread(target = startStream).start()
        else:
            threading.Thread(target = stopStream).start()            
        
        return None
      
      
class HandleRoomLight:

    def GET(self):
        #s=serial.Serial('/dev/ttyACM0',9600)
        print "\n\t**********************************************"
        print "              In side HandleRoomLight"
        print "\t**********************************************"
        i = web.input()
        passWd = str(i.email)
        print passWd
        if passWd != constant_utilities.PASSCODE:
            return "Login error, Password incorrect"
        room_name = i.room
        set_state = i.set
        if str(i.set) == "on":
            set_state = "1"
        elif str(i.set) == "off":
            set_state = "0"
        print "I: " + str(room_name) + " Room light set to " + str(set_state)
        if (str(room_name)=="living_room" and str(set_state)=="1"):
                print "liv on"
                #s.write('1')
        elif (str(room_name)=="living_room" and str(set_state)=="0"):
                print "liv off"
                #s.write('0')
        elif (str(room_name)=="bed_room" and str(set_state)=="1"):
                print "bed_room on"
                #s.write('3')
        elif (str(room_name)=="bed_room" and str(set_state)=="0"):
                print "bed_room off"
                #s.write('2')
        elif (str(room_name)=="outdoor" and str(set_state)=="1"):
                print "outdoor on"
                #s.write('5')
        elif (str(room_name)=="outdoor" and str(set_state)=="0"):
                print "outdoor off"
                #s.write('4')
        return "I: Room " + str(room_name) + " light set to " + str(set_state)
        
        
class HandleDoorLock:

    def GET(self):
        #s=serial.Serial('/dev/ttyACM0',9600)
        print "\n\t**********************************************"
        print "              In side HandleDoorLock"
        print "\t**********************************************"
        i = web.input()
        passWd = str(i.email)
        print passWd
        if passWd != constant_utilities.PASSCODE:
            return "Login error, Password incorrect"
        door_name = i.name
        set_state = i.set
        if str(i.set) == "on":
            set_state = "1"
        elif str(i.set) == "off":
            set_state = "0"
        print "I: " + str(door_name) + " Door Lock set to " + str(set_state)
        if (str(door_name)=="main_door" and str(set_state)=="1"):
                print "Unlocking main_door"
                #s.write('1')
        elif (str(door_name)=="main_door" and str(set_state)=="0"):
                print "locking main_door"
                #s.write('0')
        elif (str(door_name)=="bedroom_door" and str(set_state)=="1"):
                print "Unlocking bedroom_door"
                #s.write('3')
        elif (str(door_name)=="bedroom_door" and str(set_state)=="0"):
                print "Locking bed_room"
                #s.write('2')
        elif (str(door_name)=="kidsroom_door" and str(set_state)=="1"):
                print "Unlocking kidsroom_door"
                #s.write('5')
        elif (str(door_name)=="kidsroom_door" and str(set_state)=="0"):
                print "Locking kidsroom_door"
                #s.write('4')
        return "I: Door " + str(door_name) + " Door State " + str(set_state)
 
urls = ('/register','HandleRegister', '/unregister','HandleUnRegister', '/send','HandleSend', '/room','HandleRoomLight', '/stream','HandleStream', '/door','HandleDoorLock', '/status','HandleStatus')
app = web.application(urls, globals())
if __name__ == "__main__":
    app.run()

