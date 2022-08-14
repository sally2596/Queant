from django.apps import AppConfig
from . import save_data_manually

class QueantAppConfig(AppConfig):
    name = 'queant_app'
    from . import updater
    print("Sever running!")
    
    save_data_manually.save_db_manually()
    updater.start()
    updater.start_bank()
    updater.start_post()
    #updater.save_manually()