from django.apps import AppConfig


class QueantAppConfig(AppConfig):
    name = 'queant_app'
    from . import updater
    updater.start()
