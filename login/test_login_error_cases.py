import time
import pytest
from appium import webdriver
from appium.options.android import UiAutomator2Options
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException

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

    def restart_app(self):
        if self.driver:
            self.driver.reset()

    def test_login(self, email, password, expected_error_message):
        print(f"\nTesting with email: {email} and password: {password}")

        email_input = self.driver.find_element(By.XPATH, "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[1]")
        email_input.clear()
        email_input.send_keys(email)

        password_field = self.driver.find_element(By.XPATH, "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[2]")
        password_field.clear()
        password_field.send_keys(password)

        login_button = self.driver.find_element(By.ACCESSIBILITY_ID, "login_btn")
        login_button.click()

        time.sleep(2)

        try:
            error_element = self.driver.find_element(By.XPATH, f"//*[contains(@text, '{expected_error_message}')]")
            print(f"Test passed: Error message '{expected_error_message}' found.")
        except NoSuchElementException:
            print(f"Test failed: Error message '{expected_error_message}' not found.")
            raise

    def quit_driver(self):
        if self.driver:
            self.driver.quit()


# Test cases for pytest
@pytest.fixture(scope="class")
def app_test():
    app_test_instance = MobileAppTest("emulator-5554", "com.google.android.apps.nexuslauncher", "com.google.android.apps.nexuslauncher.NexusLauncherActivity")
    app_test_instance.start_driver()
    yield app_test_instance
    app_test_instance.quit_driver()


def test_invalid_email(app_test):
    print("\nRunning test for invalid email...")
    app_test.test_login("invalidemail@domain.com", "admiN1", "Invalid email")


def test_invalid_password(app_test):
    print("\nRunning test for invalid password...")
    app_test.test_login("szilard.vysoky@gmail.com", "wrongpassword", "Invalid password")


def test_empty_fields(app_test):
    print("\nRunning test for empty fields...")
    app_test.test_login("", "", "Fields cannot be empty")


if __name__ == "__main__":
    app_test_instance = MobileAppTest("emulator-5554", "com.google.android.apps.nexuslauncher", "com.google.android.apps.nexuslauncher.NexusLauncherActivity")
    app_test_instance.start_driver()

    app_test_instance.test_login("szilard.vysoky@gmail.com", "admiN1", "Invalid email")
    app_test_instance.restart_app()

    # app_test_instance.test_login("invalidemail@domain.com", "wrongpassword", "Invalid password")
    app_test_instance.quit_driver()
