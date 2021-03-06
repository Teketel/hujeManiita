import gcm as core_gcm
from time import gmtime, strftime
import constant_utilities
import MySQLdb
import MySQLdb.cursors

def registerWithId(reg_id, name, email):
    conn = MySQLdb.connect (constant_utilities.DB_HOST, constant_utilities.DB_USER, constant_utilities.DB_PW, constant_utilities.DB_NAME)
    dbCursor = conn.cursor()
    print "I: Inserting " + reg_id
    dbCursor.execute("INSERT INTO devices (regis_id, user_name, email) VALUES(%s, %s, %s)", (reg_id, name, email))
    conn.commit()
    print 'I: Closing table connection'
    conn.close()

def unregisterWithId( reg_id):  
    conn = MySQLdb.connect (constant_utilities.DB_HOST, constant_utilities.DB_USER, constant_utilities.DB_PW, constant_utilities.DB_NAME)
    dbCursor = conn.cursor() 
    print "I: Deleting " + reg_id 
    dbCursor.execute("DELETE FROM devices WHERE regis_id = %s", reg_id)
    conn.commit()
    print 'I: Closing table connection'
    conn.close()
def sendMessage(notification = "Motion is detected", reg_id = constant_utilities.REG_ID):
    gcm = core_gcm.GCM(constant_utilities.API_KEY) 
    data = {"message": str(notification)}
    res = gcm.plaintext_request(registration_id=reg_id, data=data) 
    try:
        canonical_id = gcm.plaintext_request(registration_id=reg_id, data=data)
        if canonical_id:
            # Repace reg_id with canonical_id in your database
            entry = entity.filter(registration_id=reg_id)
            entry.registration_id = canonical_id
            entry.save()
    except GCMNotRegisteredException:
        # Remove this reg_id from database
        print "E: NotRegisteredException raised, So registration id will be deleted."
        entity.filter(registration_id=reg_id).delete()
    except GCMUnavailableException:
        # Resent the message
        print "E: Error sending message, please Resent the message"

def get_all_reg_id():  
    conn = MySQLdb.connect (constant_utilities.DB_HOST, constant_utilities.DB_USER, constant_utilities.DB_PW, constant_utilities.DB_NAME)
    dbCursor = conn.cursor() 
    print "I: Getting "
    dbCursor.execute("SELECT regis_id FROM devices")
    return dbCursor.fetchall()
    conn.commit()
    print 'I: Closing table connection'
    conn.close()
