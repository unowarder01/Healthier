import UIKit
import SwiftUI
import Shared

struct ComposeView: UIViewControllerRepresentable {
    let googleMapViewFactory: IosGoogleMapViewFactory

    func makeUIViewController(context: Self.Context) -> UIViewController {
        MainViewControllerKt.MainViewController(googleMapViewFactory: googleMapViewFactory)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Self.Context) {}
}

struct ContentView: View {
    let googleMapViewFactory: IosGoogleMapViewFactory

    var body: some View {
        ComposeView(googleMapViewFactory: googleMapViewFactory)
            .ignoresSafeArea()
    }
}
