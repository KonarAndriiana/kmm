import time
import pytest
from appium import webdriver
from appium.options.android import UiAutomator2Options
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException


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

    def test_login(self, email, password, expected_error_message):
        print(f"\nTesting with email: '{email}' and password: '{password}'")

        email_input = self.driver.find_element(By.XPATH,
                                               "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[1]")
        password_field = self.driver.find_element(By.XPATH,
                                                  "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[2]")

        if not self.clear_and_verify(email_input):
            raise AssertionError("Email field could not be cleared.")

        email_input.send_keys(email)

        if not self.clear_and_verify(password_field):
            raise AssertionError("Password field could not be cleared.")

        password_field.send_keys(password)

        login_button = self.driver.find_element(By.ACCESSIBILITY_ID, "login_btn")
        login_button.click()

        time.sleep(2)

        try:
            error_element = self.driver.find_element(By.ACCESSIBILITY_ID, "error_msg")
            actual_error_text = error_element.text

            if expected_error_message in actual_error_text:
                print(f"Test passed: Error message '{expected_error_message}' is displayed.")
            else:
                print(f"Test failed: Expected '{expected_error_message}' but got '{actual_error_text}'.")
                raise AssertionError(f"Expected '{expected_error_message}' but got '{actual_error_text}'.")
        except NoSuchElementException:
            print("Test failed: Error message element not found.")
            raise

        time.sleep(5)


@pytest.fixture(scope="class")
def app_test():
    from driver_setup import MobileAppTest
    test = MobileAppTest("emulator-5554", "com.example.kmm.android",
                         ".MainActivity")
    test.start_driver()
    yield test
    test.quit_driver()


@pytest.mark.parametrize("email, password, expected_error", [
    ("wrong1@gmail.com", "admiN1", "Email or password is incorrect"),
    ("wrong2@gmail.com", "admiN1", "Email or password is incorrect"),
    ("", "admiN1", "Email cannot be empty"),
])
def test_invalid_email(app_test, email, password, expected_error):
    app_test.test_login(email, password, expected_error)


@pytest.mark.parametrize("email, password, expected_error", [
    ("szilard.visoky@gmail.com", "wrong1", "Email or password is incorrect"),
    ("szilard.visoky@gmail.com", "wrong2", "Email or password is incorrect"),
    ("szilard.visoky@gmail.com", "", "Password cannot be empty"),
])
def test_invalid_password(app_test, email, password, expected_error):
    app_test.test_login(email, password, expected_error)


@pytest.mark.parametrize("email, password, expected_error", [
    ("szilard.vysokygmail.com", "admiN1", "Please enter a valid email address"),
    ("szilard@", "admiN1", "Please enter a valid email address"),
])
def test_invalid_email_format(app_test, email, password, expected_error):
    app_test.test_login(email, password, expected_error)


@pytest.mark.parametrize("email, password, expected_error", [
    ("", "", "Both fields must be completed"),
    ("", "admiN1", "Email cannot be empty"),
    ("szilard.visoky@gmail.com", "", "Password cannot be empty"),
])
def test_empty_fields(app_test, email, password, expected_error):
    app_test.test_login(email, password, expected_error)


if __name__ == "__main__":
    app_test_instance = MobileAppTest("emulator-5554", "com.google.android.apps.nexuslauncher",
                                      "com.google.android.apps.nexuslauncher.NexusLauncherActivity")
    app_test_instance.start_driver()

    test_cases = [
        ("wrong1@gmail.com", "admiN1", "Email or password is incorrect"),
        ("szilard.vysokygmail.com", "admiN1", "Please enter a valid email address"),
        ("", "", "Both fields must be completed"),
    ]

    for email, password, expected in test_cases:
        app_test_instance.test_login_pytest(email, password, expected)

    app_test_instance.quit_driver()
