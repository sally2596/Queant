from django.apps import AppConfig
from . import save_data_manually

class QueantAppConfig(AppConfig):
    name = 'queant_app'
    from . import updater
    print("Sever running!")
    
    updater.start()
    updater.start_bank()
    updater.start_post()