import time
import pytest
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException

from driver_setup import MobileAppTest
from login.test_login import perform_login


def is_logged_in(driver):
    time.sleep(2)
    try:
        driver.find_element(By.ACCESSIBILITY_ID, "greeting_text")
        print("User is already logged in.")
        return True
    except NoSuchElementException:
        print("User is not logged in.")
        return False


def check_tests(driver):
    time.sleep(2)

    try:
        see_all_btn = driver.find_element(By.ACCESSIBILITY_ID, "see_all_tests_btn")
        see_all_btn.click()
        print("Clicked on 'See All Tests' button.")
        time.sleep(3)

        like_buttons = driver.find_elements(By.ACCESSIBILITY_ID, "like_btn")
        num_tests = len(like_buttons)

        print(f"Number of tests found (based on like buttons): {num_tests}")

        if num_tests == 0:
            raise AssertionError("No tests found!")
    except NoSuchElementException:
        print("Error: Could not find tests or like buttons.")
        raise


@pytest.fixture
def app_test():
    test = MobileAppTest("emulator-5554", "com.example.kmm.android", ".MainActivity")
    test.start_driver()
    yield test
    test.quit_driver()


def test_course_list(app_test):
    if not is_logged_in(app_test.driver):
        perform_login(app_test.driver, "patriklisivka95@gmail.com", "Test1234")

    print("Proceeding to check tests list.")
    check_tests(app_test.driver)


if __name__ == "__main__":
    test_instance = MobileAppTest("emulator-5554", "com.example.kmm.android", ".MainActivity")
    test_instance.start_driver()
    if not is_logged_in(test_instance.driver):
        perform_login(test_instance.driver, "patriklisivka95@gmail.com", "Test1234")
    check_tests(test_instance.driver)
    test_instance.quit_driver()
