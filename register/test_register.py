import time
import pytest
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait

from clear_and_verify import ClearInput
from driver_setup import MobileAppTest
from login.test_login import perform_login


def perform_register(driver, first_name, last_name, email, password):
    WebDriverWait(driver, 10)
    time.sleep(2)
    try:
        driver.find_element(By.ACCESSIBILITY_ID, "create_acc_text")
    except NoSuchElementException:
        driver.find_element(By.ACCESSIBILITY_ID, "sign_up_redirect").click()
        time.sleep(2)

    driver.find_element(By.ACCESSIBILITY_ID, "choose_photo_btn").click()
    time.sleep(2)

    try:
        first_photo = driver.find_element(By.XPATH,
                                          '//android.widget.ImageView[@resource-id="com.google.android.providers.media.module:id/icon_thumbnail"]')
        first_photo.click()
        time.sleep(2)
    except NoSuchElementException:
        raise

    first_name_input = driver.find_element(By.XPATH,
                                           "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[1]")
    if not ClearInput.clear_and_verify(first_name_input):
        raise AssertionError("First name field could not be cleared.")
    first_name_input.send_keys(first_name)

    last_name_input = driver.find_element(By.XPATH,
                                          "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[2]")
    if not ClearInput.clear_and_verify(last_name_input):
        raise AssertionError("Last name field could not be cleared.")
    last_name_input.send_keys(last_name)

    email_input = driver.find_element(By.XPATH,
                                      "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[3]")
    if not ClearInput.clear_and_verify(email_input):
        raise AssertionError("Email field could not be cleared.")
    email_input.send_keys(email)

    password_input = driver.find_element(By.XPATH,
                                         "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[4]")
    if not ClearInput.clear_and_verify(password_input):
        raise AssertionError("Password field could not be cleared.")
    password_input.send_keys(password)

    confirm_password_input = driver.find_element(By.XPATH,
                                                 "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[5]")
    if not ClearInput.clear_and_verify(confirm_password_input):
        raise AssertionError("Confirm password field could not be cleared.")
    confirm_password_input.send_keys(password)

    driver.find_element(By.ACCESSIBILITY_ID, "sign_up_btn").click()
    time.sleep(2)

    try:
        success_message = driver.find_element(By.ACCESSIBILITY_ID, "error_msg")
        if success_message.text == "Registration successful! You can now log in":
            print("Registration successful: Message displayed correctly.")
        else:
            raise AssertionError(f"Expected success message, but got '{success_message.text}'.")
    except NoSuchElementException:
        raise

    try:
        driver.find_element(By.ACCESSIBILITY_ID, "login_btn")
        print("Successfully redirected to the login page.")
    except NoSuchElementException:
        raise

    perform_login(driver, email, password)


@pytest.fixture
def app_test():
    test = MobileAppTest("emulator-5554", "com.google.android.apps.nexuslauncher",
                         "com.google.android.apps.nexuslauncher.NexusLauncherActivity")
    test.start_driver()
    yield test
    test.quit_driver()


def test_register_pytest(app_test):
    perform_register(app_test.driver, "Patrik", "Lišivka", "patriklisivka@gmail.com", "Test1234")
    perform_login(app_test.driver, "patriklisivka@gmail.com", "Test1234")


if __name__ == "__main__":
    app_test_instance = MobileAppTest("emulator-5554", "com.google.android.apps.nexuslauncher",
                                      "com.google.android.apps.nexuslauncher.NexusLauncherActivity")
    app_test_instance.start_driver()
    perform_register(app_test_instance.driver, "Patrik", "Lišivka", "patriklisivka@gmail.com", "Test1234")
    app_test_instance.quit_driver()
