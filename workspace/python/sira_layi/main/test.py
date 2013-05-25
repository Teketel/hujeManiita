import MySQLdb
import MySQLdb.cursors
import constant_utilities

def get_all_reg_id():  
    conn = MySQLdb.connect (constant_utilities.DB_HOST, constant_utilities.DB_USER, constant_utilities.DB_PW, constant_utilities.DB_NAME)
    dbCursor = conn.cursor() 
    print "I: Getting "
    dbCursor.execute("SELECT regis_id FROM devices")
    reg_ids = []
    for m_id in dbCursor.fetchall():
        if (m_id[0]).find('APA91') >= 0:
            reg_ids.append(m_id[0])
    return reg_ids
    
print get_all_reg_id()  
