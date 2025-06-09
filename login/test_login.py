import time
import pytest
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait

from clear_and_verify import ClearInput
from driver_setup import MobileAppTest


def perform_login(driver, email, password):
    WebDriverWait(driver, 10)

    email_input = driver.find_element(By.XPATH,
                                      "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[1]")
    if not ClearInput.clear_and_verify(email_input):
        raise AssertionError("Email field could not be cleared.")
    email_input.send_keys(email)

    password_field = driver.find_element(By.XPATH,
                                         "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[2]")
    if not ClearInput.clear_and_verify(password_field):
        raise AssertionError("Password field could not be cleared.")
    password_field.clear()
    password_field.send_keys(password)

    login_button = driver.find_element(By.ACCESSIBILITY_ID, "login_btn")
    login_button.click()

    time.sleep(2)
    try:
        driver.find_element(By.ACCESSIBILITY_ID, "test_text")
        driver.find_element(By.ACCESSIBILITY_ID, "course_text")
        driver.find_element(By.ACCESSIBILITY_ID, "about_courses")
        driver.find_element(By.ACCESSIBILITY_ID, "about_tests")
        greeting_element = driver.find_element(By.ACCESSIBILITY_ID, "greeting_text")
        greeting_text = greeting_element.text.strip()

        if greeting_text.startswith("Hi,") and len(greeting_text) > 4:
            name = greeting_text[4:].split(" ")[0]
            print(f"Login successful: My name '{name}' is visible")
        else:
            print("Login failed or name not visible")

    except NoSuchElementException:
        print("Login failed: 'Course' not found.")
        raise


@pytest.fixture
def app_test():
    test = MobileAppTest("emulator-5554", "com.example.kmm.android", ".MainActivity")
    test.start_driver()
    yield test
    test.quit_driver()


def test_login_flow(app_test):
    perform_login(app_test.driver, "patriklisivka95@gmail.com", "Test1234")
    print("Login test prebehol úspešne!")


if __name__ == "__main__":
    test_instance = MobileAppTest("emulator-5554", "com.example.kmm.android", ".MainActivity")
    test_instance.start_driver()
    perform_login(test_instance.driver, "patriklisivka95@gmail.com", "Test1234")
    test_instance.quit_driver()
