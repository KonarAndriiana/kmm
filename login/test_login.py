import time
import pytest
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait

from driver_setup import MobileAppTest


class LoginTest(MobileAppTest):
    def perform_login(self, email, password):
        wait = WebDriverWait(self.driver, 10)

        email_input = self.driver.find_element(By.XPATH,
                                               "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[1]")
        email_input.clear()
        email_input.send_keys(email)

        password_field = self.driver.find_element(By.XPATH,
                                                  "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[2]")
        password_field.click()
        password_field.clear()
        password_field.send_keys(password)

        login_button = self.driver.find_element(By.ACCESSIBILITY_ID, "login_btn")
        login_button.click()

        time.sleep(2)
        try:
            self.driver.find_element(By.XPATH, "//*[contains(@text, 'Course')]")
            print("Login successful: 'Course' is visible.")
        except NoSuchElementException:
            print("Login failed: 'Course' not found.")
            raise


@pytest.fixture
def app_test():
    test = LoginTest("emulator-5554", "com.example.kmm.android", ".MainActivity")
    test.start_driver()
    yield test
    test.quit_driver()


def test_login_flow(app_test):
    app_test.perform_login("szilard.vysoky@gmail.com", "admiN1")
    print("Login test prebehol úspešne!")


if __name__ == "__main__":
    test_instance = LoginTest("emulator-5554", "com.example.kmm.android", ".MainActivity")
    test_instance.start_driver()
    test_instance.perform_login("szilard.vysoky@gmail.com", "admiN1")
    test_instance.quit_driver()
