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

    def test_register(self, first_name, last_name, email, password):
        wait = WebDriverWait(self.driver, 10)
        time.sleep(2)
        try:
            self.driver.find_element(By.ACCESSIBILITY_ID, "create_acc_text")
        except NoSuchElementException:
            self.driver.find_element(By.ACCESSIBILITY_ID, "sign_up_redirect").click()
            time.sleep(2)

        self.driver.find_element(By.ACCESSIBILITY_ID, "choose_photo_btn").click()
        time.sleep(2)

        try:
            first_photo = self.driver.find_element(By.XPATH, '//android.widget.ImageView[@resource-id="com.google.android.providers.media.module:id/icon_thumbnail"]')
            first_photo.click()
            time.sleep(2)
        except NoSuchElementException:
            print("Photo selection failed – no image found.")
            raise

        first_name_input = self.driver.find_element(By.XPATH,
                                                    "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[1]")
        first_name_input.clear()
        first_name_input.send_keys(first_name)

        last_name_input = self.driver.find_element(By.XPATH,
                                                   "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[2]")
        last_name_input.clear()
        last_name_input.send_keys(last_name)

        email_input = self.driver.find_element(By.XPATH,
                                               "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[3]")
        email_input.clear()
        email_input.send_keys(email)

        password_input = self.driver.find_element(By.XPATH,
                                                  "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[4]")
        password_input.clear()
        password_input.send_keys(password)

        confirm_password_input = self.driver.find_element(By.XPATH,
                                                          "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[5]")
        confirm_password_input.send_keys(password)

        self.driver.find_element(By.ACCESSIBILITY_ID, "sign_up_btn").click()
        time.sleep(2)

        try:
            success_message = self.driver.find_element(By.ACCESSIBILITY_ID, "error_msg")
            if success_message.text == "Registration successful! You can now log in":
                print("Registration successful: Message displayed correctly.")
            else:
                print(f"Registration failed: Unexpected message '{success_message.text}'.")
                raise AssertionError(f"Expected success message, but got '{success_message.text}'.")
        except NoSuchElementException:
            print("Error message not found after registration.")
            raise

        try:
            self.driver.find_element(By.ACCESSIBILITY_ID, "login_btn")
            print("Successfully redirected to the login page.")
        except NoSuchElementException:
            print("Failed to redirect to the login page.")
            raise


@pytest.fixture
def app_test():
    test = MobileAppTest("emulator-5554", "com.google.android.apps.nexuslauncher",
                         "com.google.android.apps.nexuslauncher.NexusLauncherActivity")
    test.start_driver()
    yield test
    test.quit_driver()


def test_register(app_test):
    app_test.test_register("Test", "User", "testuser3@example.com", "Test1234")


if __name__ == "__main__":
    app_test_instance = MobileAppTest("emulator-5554", "com.google.android.apps.nexuslauncher",
                                      "com.google.android.apps.nexuslauncher.NexusLauncherActivity")
    app_test_instance.start_driver()
    app_test_instance.test_register("Test", "User", "testuser3@example.com", "Test1234")
    app_test_instance.quit_driver()
