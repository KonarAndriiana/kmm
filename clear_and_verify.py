import time


class MobileAppTest:


    def clear_and_verify(self, element):
        max_attempts = 5
        for attempt in range(max_attempts):
            element.clear()
            time.sleep(1)
            if element.text == "":
                print(f"Field is successfully cleared after {attempt + 1} attempts.")
                return True
            print(f"Field not cleared, retrying... ({attempt + 1}/{max_attempts})")
        print("Failed to clear the field.")
        return False
