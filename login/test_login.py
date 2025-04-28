from appium import webdriver
from appium.options.android import UiAutomator2Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait


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

    def login(self, email, password):
        wait = WebDriverWait(self.driver, 10)

        email_input = self.driver.find_element(By.XPATH, "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[1]")
        email_input.clear()
        email_input.send_keys(email)

        password_field = self.driver.find_element(By.XPATH, "")
        password_field.click()
        password_field.clear()
        password_field.send_keys(password)

        toggle_pass_btn = self.driver.find_element(By.ACCESSIBILITY_ID, "toggle_pass")
        toggle_pass_btn.click()

        login_button = self.driver.find_element(By.ACCESSIBILITY_ID, "login_btn")
        login_button.click()

    def quit_driver(self):
        if self.driver:
            self.driver.quit()


test = MobileAppTest("emulator-5554", "com.google.android.apps.nexuslauncher",
                     "com.google.android.apps.nexuslauncher.NexusLauncherActivity")
test.start_driver()
test.login("szilard.vysoky@gmail.com", "admiN1")
print("Login test prebehol úspešne!")
test.quit_driver()
