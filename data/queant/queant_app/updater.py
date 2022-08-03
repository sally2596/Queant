from apscheduler.schedulers.background import BackgroundScheduler

from .save_to_db import save_db
from .save_to_db import save_bank_db

def start():
    scheduler = BackgroundScheduler()
    #scheduler.add_job(save_db, 'interval', seconds = 10)
    #scheduler.add_job(save_bank_db, 'interval', seconds = 10)
    #scheduler.add_job(save_db, trigger='cron', day_of_week='wed', hour=11, minute=15)
    scheduler.add_job(save_db, trigger='cron', hour=11, minute=18)
    scheduler.start()
    
    
def start_bank():
    scheduler = BackgroundScheduler()
    scheduler.add_job(save_bank_db, trigger='cron',hour=11, minute=18)
    scheduler.start()    