from apscheduler.schedulers.background import BackgroundScheduler
from pytz import timezone

from .save_to_db import save_db
from .save_to_db import save_bank_db
from .save_post_to_db import save_postdata_db
from .save_to_db import save_db_by_myself

def start():
    scheduler = BackgroundScheduler(timezone='Asia/Seoul')
    #scheduler.add_job(save_db, 'interval', seconds = 10)
    #scheduler.add_job(save_bank_db, 'interval', seconds = 10)
    #scheduler.add_job(save_db, trigger='cron', day_of_week='wed', hour=11, minute=15)
    scheduler.add_job(save_db, trigger='cron', hour=12, minute=55)
    scheduler.start()
    
    
def start_bank():
    scheduler = BackgroundScheduler(timezone='Asia/Seoul')
    scheduler.add_job(save_bank_db, trigger='cron',hour=11, minute=29)
    scheduler.start()
    
    
def start_post():
    scheduler = BackgroundScheduler(timezone='Asia/Seoul')
    scheduler.add_job(save_postdata_db, trigger='cron',hour=12, minute=57)
    scheduler.start()
    
#def save_manually():
#    scheduler = BackgroundScheduler(timezone='Asia/Seoul')
#    scheduler.add_job(save_db_by_myself, trigger='cron',hour=16, minute=43)
#    scheduler.start()