import web
import my_gcm

class HandleRegister:

    def GET(self):
        
        print "\n\t**********************************************"
        print "             In side HandleRegister"
        print "\t**********************************************"
        i = web.input()
        r_id = i.r_id
        print "I: Registration id = " + str(r_id)
        my_gcm.registerWithId(r_id, str(i.name), str(i.email))
        
        return 'Successfully registered'
        
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
        
        print "\n\t**********************************************"
        print "              In side HandleSend"
        print "\t**********************************************"
        i = web.input()
        room_name = i.room
        set_state = i.set
        print "I: Room " + str(room_name) + " light set to " + str(set_state) 
        return None
        
 
urls = ('/register','HandleRegister', '/unregister','HandleUnRegister', '/send','HandleSend', '/room','HandleRoomLight', '/stream','HandleStream', '/door','HandleDoor')
app = web.application(urls, globals())
if __name__ == "__main__":
    app.run()
