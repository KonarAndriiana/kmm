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


def open_it_security_course(driver):
    time.sleep(2)
    try:
        it_security_course = driver.find_element(By.XPATH, "//*[contains(@text, 'IT security')]")
        it_security_course.click()
        print("Clicked on 'IT security' course.")
        time.sleep(3)
    except NoSuchElementException:
        print("Could not find 'IT security' course.")
        raise


def count_lectures(driver):
    time.sleep(2)
    all_elements = driver.find_elements(By.XPATH, "//*")
    lecture_count = 0

    for element in all_elements:
        try:
            text = element.text.strip()
            if "lecture" in text.lower():
                lecture_count += 1
        except Exception:
            continue

    print(f"Number of lecture elements found: {lecture_count}")
    if lecture_count == 0:
        raise AssertionError("No lectures found!")


@pytest.fixture
def app_test():
    test = MobileAppTest("emulator-5554", "com.example.kmm.android", ".MainActivity")
    test.start_driver()
    yield test
    test.quit_driver()


def test_lecture_list(app_test):
    if not is_logged_in(app_test.driver):
        perform_login(app_test.driver, "patriklisivka95@gmail.com", "Test1234")

    open_it_security_course(app_test.driver)
    count_lectures(app_test.driver)


if __name__ == "__main__":
    test_instance = MobileAppTest("emulator-5554", "com.example.kmm.android", ".MainActivity")
    test_instance.start_driver()
    if not is_logged_in(test_instance.driver):
        perform_login(test_instance.driver, "patriklisivka95@gmail.com", "Test1234")
    open_it_security_course(test_instance.driver)
    count_lectures(test_instance.driver)
    test_instance.quit_driver()
