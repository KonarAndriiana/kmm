import time
import pytest
from appium import webdriver
from appium.options.android import UiAutomator2Options
from selenium.common import NoSuchElementException
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

    def test_login(self, email, password):
        wait = WebDriverWait(self.driver, 10)

        email_input = self.driver.find_element(By.XPATH,
                                               "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[1]")
        email_input.clear()
        email_input.send_keys(email)

        password_field = self.driver.find_element(By.XPATH,
                                                  "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[2]")
        password_field.click()
        password_field.clear()
        password_field.send_keys(password)

        login_button = self.driver.find_element(By.ACCESSIBILITY_ID, "login_btn")
        login_button.click()

        time.sleep(2)

        try:
            course_element = self.driver.find_element(By.XPATH, "//*[contains(@text, 'Courses')]")
            print("Login successful: 'Course' is visible.")
        except NoSuchElementException:
            print("Login failed: 'Course' not found.")
            raise

    def quit_driver(self):
        if self.driver:
            self.driver.quit()


@pytest.fixture
def app_test():
    test = MobileAppTest("emulator-5554", "com.google.android.apps.nexuslauncher",
                         "com.google.android.apps.nexuslauncher.NexusLauncherActivity")
    test.start_driver()
    yield test
    test.quit_driver()


def test_login(app_test):
    app_test.test_login("szilard.vysoky@gmail.com", "admiN1")
    print("Login test prebehol úspešne!")


if __name__ == "__main__":
    app_test_instance = MobileAppTest("emulator-5554", "com.google.android.apps.nexuslauncher",
                                      "com.google.android.apps.nexuslauncher.NexusLauncherActivity")
    app_test_instance.start_driver()

    app_test_instance.test_login("szilard.vysoky@gmail.com", "admiN1")

    app_test_instance.quit_driver()
