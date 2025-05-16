import time
import pytest
from appium import webdriver
from appium.options.android import UiAutomator2Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common import NoSuchElementException


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


@pytest.fixture
def app_test():
    test = MobileAppTest("emulator-5554", "com.google.android.apps.nexuslauncher",
                         "com.google.android.apps.nexuslauncher.NexusLauncherActivity")
    test.start_driver()
    yield test
    test.quit_driver()


def test_forgot_password(app_test):
    wait = WebDriverWait(app_test.driver, 10)

    forgot_button = wait.until(EC.presence_of_element_located((By.ACCESSIBILITY_ID, "forgot_btn")))
    forgot_button.click()
    time.sleep(2)

    try:
        info_text = app_test.driver.find_element(By.ACCESSIBILITY_ID, "info_text")
        assert info_text.is_displayed()
        print("Instruction text is displayed correctly.")
    except NoSuchElementException:
        print("Test failed: Instruction text is not displayed.")
        raise

    time.sleep(2)

    email_input = app_test.driver.find_element(By.XPATH, "//android.widget.EditText")
    email_input.clear()
    email_input.send_keys("patriklisivka95@gmail.com")

    # reset_button = app_test.driver.find_element(By.ACCESSIBILITY_ID, "reset_btn")
    # reset_button.click()
    time.sleep(2)

    try:
        success_message = app_test.driver.find_element(By.ACCESSIBILITY_ID, "error_msg")
        assert success_message.text == "Success! Please check your email to reset your password"
        print("Test passed: Password reset was successful.")
    except NoSuchElementException:
        print("Test failed: Success message did not appear.")
        raise
