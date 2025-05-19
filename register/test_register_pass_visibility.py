import time
import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common import NoSuchElementException


@pytest.fixture
def app_test():
    from login.test_login import MobileAppTest
    test = MobileAppTest("emulator-5554", "com.google.android.apps.nexuslauncher",
                         "com.google.android.apps.nexuslauncher.NexusLauncherActivity")
    test.start_driver()
    yield test
    test.quit_driver()


def test_pass_visibility_register(app_test):
    wait = WebDriverWait(app_test.driver, 10)
    time.sleep(2)
    try:
        app_test.driver.find_element(By.ACCESSIBILITY_ID, "create_acc_text")
    except NoSuchElementException:
        app_test.driver.find_element(By.ACCESSIBILITY_ID, "sign_up_redirect").click()
        time.sleep(2)

    password_input = app_test.driver.find_element(
        By.XPATH, "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[4]"
    )
    confirm_password_input = app_test.driver.find_element(
        By.XPATH, "//androidx.compose.ui.platform.ComposeView/android.view.View/android.widget.EditText[5]"
    )
    toggle_password_button = app_test.driver.find_element(By.ACCESSIBILITY_ID, "toggle_pass")
    toggle_confirm_password_button = app_test.driver.find_element(By.ACCESSIBILITY_ID, "toggle_confirm_pass")

    password_value = "MySecret123"
    confirm_password_value = "MySecret123"

    password_input.send_keys(password_value)
    confirm_password_input.send_keys(confirm_password_value)
    time.sleep(2)

    password_text = password_input.text
    confirm_password_text = confirm_password_input.text

    print(f"Initial password field value: '{password_text}'")
    print(f"Initial confirm password field value: '{confirm_password_text}'")

    if password_text == password_value or confirm_password_text == confirm_password_value:
        print("Test failed: Passwords are not hidden initially.")
        raise AssertionError("Passwords should be hidden initially.")
    else:
        print("Test passed: Passwords are hidden initially.")

    toggle_password_button.click()
    toggle_confirm_password_button.click()
    time.sleep(2)

    password_text = password_input.text
    confirm_password_text = confirm_password_input.text

    print(f"After first toggle, password field value: '{password_text}'")
    print(f"After first toggle, confirm password field value: '{confirm_password_text}'")

    if password_text == password_value and confirm_password_text == confirm_password_value:
        print("Test passed: Passwords are visible after clicking toggle buttons.")
    else:
        print("Test failed: Passwords are not visible after clicking toggle buttons.")
        raise AssertionError("Passwords should be visible after toggle.")

    toggle_password_button.click()
    toggle_confirm_password_button.click()
    time.sleep(2)

    password_text = password_input.text
    confirm_password_text = confirm_password_input.text

    print(f"After second toggle, password field value: '{password_text}'")
    print(f"After second toggle, confirm password field value: '{confirm_password_text}'")

    if password_text != password_value and confirm_password_text != confirm_password_value:
        print("Test passed: Passwords are hidden again after second toggle.")
    else:
        print("Test failed: Passwords are not hidden after second toggle.")
        raise AssertionError("Passwords should be hidden after toggle.")

    print("Final check: Passwords are hidden as expected.")