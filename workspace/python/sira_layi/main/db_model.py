import constant_utilities
import MySQLdb
import MySQLdb.cursors
from datetime import datetime
import os

def saveVideo(path, date_time):
    conn = MySQLdb.connect (constant_utilities.DB_HOST, constant_utilities.DB_USER, constant_utilities.DB_PW, constant_utilities.DB_NAME)
    dbCursor = conn.cursor()
    print "I: Saving video > " + path
    dbCursor.execute("INSERT INTO videos (file_path, record_date) VALUES(%s, %s)", (path, date_time))
    conn.commit()
    print 'I: Closing table connection'
    conn.close()
   
