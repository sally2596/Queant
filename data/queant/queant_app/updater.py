from apscheduler.schedulers.background import BackgroundScheduler
from pytz import timezone

from .save_to_db import save_db
from .save_to_db import save_bank_db
from .save_post_to_db import save_postdata_db
from .save_to_db import save_db_by_myself

def start():
    scheduler = BackgroundScheduler(timezone='Asia/Seoul')
    scheduler.add_job(save_db, trigger='cron', hour=17, minute=7)
    scheduler.start()
    
    
def start_bank():
    scheduler = BackgroundScheduler(timezone='Asia/Seoul')
    scheduler.add_job(save_bank_db, trigger='cron',hour=6, minute=1)
    scheduler.start()
    
    
def start_post():
    scheduler = BackgroundScheduler(timezone='Asia/Seoul')
    scheduler.add_job(save_postdata_db, trigger='cron',hour=7, minute=1)
    scheduler.start()