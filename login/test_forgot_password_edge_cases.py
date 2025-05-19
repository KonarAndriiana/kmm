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

    def clear_and_verify(self, element):
        max_attempts = 5
        for attempt in range(max_attempts):
            element.clear()
            time.sleep(1)
            if element.text == "":
                print(f"Field is successfully cleared after {attempt + 1} attempts.")
                return True
            print(f"Field not cleared, retrying... ({attempt + 1}/{max_attempts})")
        print("Failed to clear the field.")
        return False


@pytest.fixture
def app_test():
    test = MobileAppTest("emulator-5554", "com.google.android.apps.nexuslauncher",
                         "com.google.android.apps.nexuslauncher.NexusLauncherActivity")
    test.start_driver()
    yield test
    test.quit_driver()


@pytest.mark.parametrize("email, expected_message", [
    # ("", "Enter your email to continue"),
    ("invalid-email", "Please enter a valid email address"),
    ("invalid-email@", "Please enter a valid email address"),
    ("@domain.com", "Please enter a valid email address"),
    ("invalid@domain", "Please enter a valid email address"),
    ("wrong.email@notregistered.com", "Success! Please check your email to reset your password.")
])
def test_forgot_password_edge_cases(app_test, email, expected_message):
    wait = WebDriverWait(app_test.driver, 10)

    forgot_button = wait.until(EC.presence_of_element_located((By.ACCESSIBILITY_ID, "forgot_btn")))
    forgot_button.click()
    time.sleep(3)

    try:
        info_text = app_test.driver.find_element(By.ACCESSIBILITY_ID, "info_text")
        assert info_text.is_displayed()
        print("The information text is displayed correctly.")
    except NoSuchElementException:
        print("Test failed: Information text not displayed.")
        raise

    email_input = app_test.driver.find_element(By.XPATH, "//android.widget.EditText")
    if not app_test.clear_and_verify(email_input):
        raise AssertionError("Email field could not be cleared.")
    email_input.send_keys(email)
    time.sleep(2)

    try:
        message_element = app_test.driver.find_element(By.ACCESSIBILITY_ID, "error_msg")
        actual_message = message_element.text
        assert actual_message == expected_message, f"Expected '{expected_message}' but got '{actual_message}'"
        print(f"Test passed: '{actual_message}' is displayed as expected.")
    except NoSuchElementException:
        print("Test failed: Expected message element not found.")
        raise

    print("Waiting 5 seconds for the message to disappear...")
    time.sleep(5)

    forgot_button.click()
    time.sleep(3)

    try:
        message_element = app_test.driver.find_element(By.ACCESSIBILITY_ID, "error_msg")
        assert message_element.text == expected_message, f"Expected '{expected_message}' but got '{message_element.text}'"
        print(f"Test passed: '{expected_message}' is displayed correctly after returning to Forgot Password.")
    except NoSuchElementException:
        print("Test failed: The error message did not appear on the Forgot Password page.")
        raise
