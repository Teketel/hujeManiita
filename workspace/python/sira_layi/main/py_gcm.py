import gcm as core_gcm
from time import gmtime, strftime

SENDER_ID = "987765716293"
API_KEY = "AIzaSyANE_O8e99EX50aJU3jhP1OdJc8aKL9qcs"
reg_id1 = "APA91bFlIeUymqf-xzpnKxUE5pCg2AwCFQNXqCGL29dGShXH3Da9Im0W1T21MEA1LEu8kspsE39gjQi4Mwgr-RILKoY85BjpBgC1l4VCSVtC6Tgld27nqnVbdmpQ16CcGeBNTrhYnGaMd1hhcMbn1AZaYtG8OPn0YQ"
reg_id_emu = "APA91bGv0sT7m970i_kYWQ-N2j5pwgusQlFQgs2xIPLvbUDW0bFuCh6rLyVKz49lQunfOs5SKodHVAI_-W_25yVZUMFniqCMz5vEADO2oB_sVAvrJfWrGKkpcArsFtcXDC-S5rPgCq-RxT1rZQCyf27bdIB--747WQ"
reg_id = "APA91bG9REplTQl_8daUMTDq--HauckcQH7g2O2KLrwRB5xIf39lqJpD0zXnFagcOBtx6LDUqwNnU6I2tB5mpCxePRVDAPy2CIGxDztOxS13WApcabNRzg9r2FvqdI5JxaHIQPe-ok_Ps8Fs3wKQGX7D4gwFvlgwgg"
gcm = core_gcm.GCM(API_KEY) 
data = {"message": 'Motiion around home is detected!'}
# Plaintext request reg_id = '12345' 
res = gcm.plaintext_request(registration_id=reg_id, data=data) 

#Error handling 

# Plaintext request

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
