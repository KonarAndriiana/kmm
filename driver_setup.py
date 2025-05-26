from appium import webdriver
from appium.options.android import UiAutomator2Options


class MobileAppTest:
    def __init__(self, device_name, app_package, app_activity):
        self.device_name = device_name
        self.app_package = app_package
        self.app_activity = app_activity
        self.driver = None

    def start_driver(self):
        options = UiAutomator2Options()
        options.platform_name = "Android"
        options.device_name = self.device_name
        options.app_package = self.app_package
        options.app_activity = self.app_activity
        options.no_reset = True
        self.driver = webdriver.Remote("http://127.0.0.1:4723", options=options)

    def quit_driver(self):
        if self.driver:
            self.driver.quit()
