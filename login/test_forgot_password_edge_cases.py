import time
import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common import NoSuchElementException

from clear_and_verify import ClearInput


@pytest.fixture
def app_test():
    from driver_setup import MobileAppTest
    test = MobileAppTest("emulator-5554", "com.example.kmm.android",
                         ".MainActivity")
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
    if not ClearInput.clear_and_verify(email_input):
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
