import SwiftUI

struct MainView: View {
    
    @StateObject var viewModel = MainViewViewModel()
    
    var body: some View {
        if viewModel.isSignedIn, !viewModel.currentUserId.isEmpty {
            HomeView()
        } else {
            LoginView()
        }
    }
}

#Preview {
    MainView()
}
