from apscheduler.schedulers.background import BackgroundScheduler

from .save_db import save_prdt
    

def start():
    scheduler = BackgroundScheduler()
    scheduler.add_job(save_prdt, 'interval', seconds=5)
    scheduler.start()