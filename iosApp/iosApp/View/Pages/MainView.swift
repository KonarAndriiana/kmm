import SwiftUI
import shared

struct MainView: View {
	let greet = Greeting().greet()

	var body: some View {
		Text(greet)
	}
}

struct MainView_Previews: PreviewProvider {
	static var previews: some View {
		MainView()
	}
}
