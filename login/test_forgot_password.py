import time
import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common import NoSuchElementException


@pytest.fixture
def app_test():
    from driver_setup import MobileAppTest
    test = MobileAppTest("emulator-5554", "com.example.kmm.android",
                         ".MainActivity")
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
        print("Information text is displayed correctly.")
    except NoSuchElementException:
        print("Test failed: Information text is not displayed.")
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
