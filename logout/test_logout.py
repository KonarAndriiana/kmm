import time
import pytest
from selenium.common import NoSuchElementException
from selenium.webdriver.common.by import By

from login.test_login import test_login_pytest


@pytest.fixture
def app_test():
    from driver_setup import MobileAppTest
    test = MobileAppTest("emulator-5554", "com.example.kmm.android",
                         ".MainActivity")
    test.start_driver()
    yield test
    test.quit_driver()


def test_logout(app_test):
    test_login_pytest("szilard.vysoky@gmail.com", "admiN1")

    logout_button = app_test.driver.find_element(By.ACCESSIBILITY_ID, "logout_btn")
    logout_button.click()
    time.sleep(2)

    try:
        app_test.driver.find_element(By.ACCESSIBILITY_ID, "login_btn")
        print("Test passed: Successfully logged out and returned to login page.")
    except NoSuchElementException:
        print("Test failed: Did not return to login page after logout.")
        raise