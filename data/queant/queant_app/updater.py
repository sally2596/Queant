from apscheduler.schedulers.background import BackgroundScheduler

from .save_to_db import save_db
    

def start():
    scheduler = BackgroundScheduler()
    scheduler.add_job(save_db, trigger='cron', day_of_week='mon', hour=16, minute=27)
    scheduler.start()