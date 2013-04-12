import web
import my_gcm
import constant_utilities
import serial

class HandleRegister:

    def GET(self):
        
        print "\n\t**********************************************"
        print "             In side HandleRegister"
        print "\t**********************************************"
        i = web.input()
        r_id = i.r_id
        print "I: Registration id = " + str(r_id)
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
        print "I: Registration id = " + str(r_id)
        #my_gcm.unregisterWithId(r_id)
        return 
        
class HandleStatus:
    def GET(self):
        
        print "\n\t**********************************************"
        print "              In side HandleStatus"
        print "\t**********************************************"
        #status = s.read()
        status = "and,then,there"
        return status
        
class HandleSend:

    def GET(self):
        
        print "\n\t**********************************************"
        print "              In side HandleSend"
        print "\t**********************************************"
        i = web.input()
        message = i.msg
        r_id = 'APA91bFTUMtf6juVPopAQoNyXtRJnZ8ruIzggPlfwpXruGH-07aEMSXT2xlJAEZKbBf0JQ3Np4ZA2FooRWZbFWRFrOVXKGkAQPed5CcuD13boElwET0Y_vAIzXo9H-3jz_YZFkZQ_J_lIDU8i6gQJKfcxYjlirk0X3Iel_m9IVAE90uJQuN24ag' 
        #r_id = i.r_id
        print "I: Message " + str(message) + " Sent to " + str(r_id) 
        my_gcm.sendMessage(message, id)
        return None

class HandleStream:

    def GET(self):
        
        print "\n\t**********************************************"
        print "              In side HandleStream"
        print "\t**********************************************"
        i = web.input()
        message = i.msg
        r_id = 'APA91bFTUMtf6juVPopAQoNyXtRJnZ8ruIzggPlfwpXruGH-07aEMSXT2xlJAEZKbBf0JQ3Np4ZA2FooRWZbFWRFrOVXKGkAQPed5CcuD13boElwET0Y_vAIzXo9H-3jz_YZFkZQ_J_lIDU8i6gQJKfcxYjlirk0X3Iel_m9IVAE90uJQuN24ag' 
        #r_id = i.r_id
        print "I: Message " + str(message) + " Sent to " + str(r_id) 
        my_gcm.sendMessage(message, id)
        return None
      
      
class HandleRoomLight:

    def GET(self):
        #s=serial.Serial('/dev/ttyACM0',9600)
        print "\n\t**********************************************"
        print "              In side HandleRoomLight"
        print "\t**********************************************"
        i = web.input()
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
 
urls = ('/register','HandleRegister', '/unregister','HandleUnRegister', '/send','HandleSend', '/room','HandleRoomLight', '/stream','HandleStream', '/door','HandleDoor', '/status','HandleStatus')
app = web.application(urls, globals())
if __name__ == "__main__":
    app.run()
