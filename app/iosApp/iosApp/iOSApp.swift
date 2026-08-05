import GoogleMaps
import SwiftUI

@main
struct iOSApp: App {
    private let googleMapViewFactory: GoogleMapViewFactory

    init() {
        let apiKey = Bundle.main.object(forInfoDictionaryKey: "GMSApiKey") as? String
        let isConfigured = apiKey?.isEmpty == false

        if let apiKey, isConfigured {
            GMSServices.provideAPIKey(apiKey)
        }

        googleMapViewFactory = GoogleMapViewFactory(isConfigured: isConfigured)
    }

    var body: some Scene {
        WindowGroup {
            ContentView(googleMapViewFactory: googleMapViewFactory)
        }
    }
}
