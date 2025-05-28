import time
import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


@pytest.fixture
def app_test():
    from driver_setup import MobileAppTest
    test = MobileAppTest("emulator-5554", "com.example.kmm.android",
                         ".MainActivity")
    test.start_driver()
    yield test
    test.quit_driver()


def test_pass_visibility(app_test):
    wait = WebDriverWait(app_test.driver, 10)

    password_input = wait.until(EC.presence_of_element_located(
        (By.XPATH, "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[2]"))
    )
    toggle_button = app_test.driver.find_element(By.ACCESSIBILITY_ID, "toggle_pass")

    password_value = "MySecret123"
    password_input.send_keys(password_value)
    time.sleep(2)

    password_text = password_input.text
    print(f"Initial password field value: '{password_text}'")

    if password_text == password_value:
        print("Test failed: Password is not hidden initially.")
        raise AssertionError("Password should be hidden initially.")
    else:
        print("Test passed: Password is hidden initially.")

    toggle_button.click()
    time.sleep(2)
    password_text = password_input.text
    print(f"After first toggle, password field value: '{password_text}'")

    if password_text == password_value:
        print("Test passed: Password is visible after clicking toggle button.")
    else:
        print("Test failed: Password is not visible after clicking toggle button.")
        raise AssertionError("Password should be visible after toggle.")

    toggle_button.click()
    time.sleep(2)
    password_text = password_input.text
    print(f"After second toggle, password field value: '{password_text}'")

    if password_text != password_value:
        print("Test passed: Password is hidden again after second toggle.")
    else:
        print("Test failed: Password is not hidden after second toggle.")
        raise AssertionError("Password should be hidden after toggle.")

    print("Final check: Password is hidden as expected.")
