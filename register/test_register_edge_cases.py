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

    def test_register_edge_case(self, first_name, last_name, email, password, confirm_password, expected_error):
        print(f"\nTesting registration with First Name: '{first_name}', Last Name: '{last_name}', "
              f"Email: '{email}', Password: '{password}', Confirm Password: '{confirm_password}'")

        wait = WebDriverWait(self.driver, 10)
        time.sleep(2)

        try:
            self.driver.find_element(By.ACCESSIBILITY_ID, "create_acc_text")
        except NoSuchElementException:
            print("Navigating to Sign Up page...")
            self.driver.find_element(By.ACCESSIBILITY_ID, "sign_up_redirect").click()
            time.sleep(2)

        first_name_input = self.driver.find_element(By.XPATH,
                                                    "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[1]")
        if not self.clear_and_verify(first_name_input):
            raise AssertionError("Email field could not be cleared.")
        first_name_input.send_keys(first_name)

        last_name_input = self.driver.find_element(By.XPATH,
                                                   "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[2]")
        if not self.clear_and_verify(last_name_input):
            raise AssertionError("Email field could not be cleared.")
        last_name_input.send_keys(last_name)

        email_input = self.driver.find_element(By.XPATH,
                                               "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[3]")
        if not self.clear_and_verify(email_input):
            raise AssertionError("Email field could not be cleared.")
        email_input.send_keys(email)

        password_input = self.driver.find_element(By.XPATH,
                                                  "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[4]")
        if not self.clear_and_verify(password_input):
            raise AssertionError("Email field could not be cleared.")
        password_input.send_keys(password)

        confirm_password_input = self.driver.find_element(By.XPATH,
                                                          "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[5]")
        if not self.clear_and_verify(confirm_password_input):
            raise AssertionError("Email field could not be cleared.")
        confirm_password_input.send_keys(confirm_password)

        print("Clicking the 'Sign Up' button...")
        self.driver.find_element(By.ACCESSIBILITY_ID, "sign_up_btn").click()
        time.sleep(2)

        try:
            error_message = self.driver.find_element(By.ACCESSIBILITY_ID, "error_msg")
            actual_error_text = error_message.text

            if expected_error in actual_error_text:
                print(f"Test passed: Error message '{expected_error}' is displayed.")
            else:
                print(f"Test failed: Expected '{expected_error}' but got '{actual_error_text}'.")
                raise AssertionError(f"Expected '{expected_error}' but got '{actual_error_text}'.")
        except NoSuchElementException:
            print("Test failed: Error message element not found.")
            raise

        time.sleep(5)

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


@pytest.mark.parametrize("first_name, last_name, email, password, confirm_password, expected_error", [
    ("Test", "User", "invalid-email", "Test1234", "Test1234", "Please enter a valid email address"),
    ("Test", "User", "testuser8@.com", "Test1234", "Test1234", "Please enter a valid email address"),
    ("Test", "User", "testuser9.com", "Test1234", "Test1234", "Please enter a valid email address"),
])
def test_invalid_email_format(app_test, first_name, last_name, email, password, confirm_password, expected_error):
    app_test.test_register_edge_case(first_name, last_name, email, password, confirm_password, expected_error)


@pytest.mark.parametrize("first_name, last_name, email, password, confirm_password, expected_error", [
    ("Test", "User", "testuser2@example.com", "Password", "Password", "Password must contain at least one number"),
    ("Test", "User", "testuser3@example.com", "NoNumbersHere", "NoNumbersHere",
     "Password must contain at least one number"),
    ("Test", "User", "testuser4@example.com", "test1234", "test1234",
     "Password must contain at least one uppercase letter"),
    ("Test", "User", "testuser5@example.com", "onlylowercase1", "onlylowercase1",
     "Password must contain at least one uppercase letter"),
    ("Test", "User", "testuser6@example.com", "T1", "T1", "Password must be at least 6 characters long"),
    ("Test", "User", "testuser7@example.com", "Pass1", "Pass1", "Password must be at least 6 characters long"),
])
def test_password_validation(app_test, first_name, last_name, email, password, confirm_password, expected_error):
    app_test.test_register_edge_case(first_name, last_name, email, password, confirm_password, expected_error)


@pytest.mark.parametrize("first_name, last_name, email, password, confirm_password, expected_error", [
    ("", "", "", "", "", "All fields must be completed")
])
def test_all_fields_empty(app_test, first_name, last_name, email, password, confirm_password, expected_error):
    app_test.test_register_edge_case(first_name, last_name, email, password, confirm_password, expected_error)


@pytest.mark.parametrize("first_name, last_name, email, password, confirm_password, expected_error", [
    ("Test", "User", "testuser10@example.com", "", "", "Password cannot be empty"),
])
def test_empty_fields(app_test, first_name, last_name, email, password, confirm_password, expected_error):
    app_test.test_register_edge_case(first_name, last_name, email, password, confirm_password, expected_error)


@pytest.mark.parametrize("first_name, last_name, email, password, confirm_password, expected_error", [
    ("Test", "User", "testuser11@example.com", "Test1234", "DifferentPassword", "Passwords do not match"),
    ("Test", "User", "testuser12@example.com", "AnotherPass1", "AnotherPass2", "Passwords do not match"),
])
def test_password_mismatch(app_test, first_name, last_name, email, password, confirm_password, expected_error):
    app_test.test_register_edge_case(first_name, last_name, email, password, confirm_password, expected_error)


@pytest.mark.parametrize("first_name, last_name, email, password, confirm_password, expected_error", [
    ("Test", "User", "testuser1@example.com", "Test1234", "Test1234", "This email is already registered"),
])
def test_existing_user(app_test, first_name, last_name, email, password, confirm_password, expected_error):
    app_test.test_register_edge_case(first_name, last_name, email, password, confirm_password, expected_error)


if __name__ == "__main__":
    app_test_instance = MobileAppTest("emulator-5554", "com.google.android.apps.nexuslauncher",
                                      "com.google.android.apps.nexuslauncher.NexusLauncherActivity")
    app_test_instance.start_driver()

    edge_cases = [
        ("Test", "User", "testuser1@example.com", "Test1234", "Test1234", "This email is already registered"),
        ("Test", "User", "testuser2@example.com", "Password", "Password", "Password must contain at least one number"),
        ("Test", "User", "testuser3@example.com", "NoNumbersHere", "NoNumbersHere",
         "Password must contain at least one number"),
    ]

    for case in edge_cases:
        app_test_instance.test_register_edge_case(*case)

    app_test_instance.quit_driver()
