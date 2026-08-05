import GoogleMaps
import Shared
import UIKit

final class GoogleMapViewFactory: IosGoogleMapViewFactory {
    private let isConfigured: Bool

    init(isConfigured: Bool) {
        self.isConfigured = isConfigured
    }

    func createView(latitude: Double, longitude: Double, zoom: Float) -> UIView {
        guard isConfigured else {
            return makeMissingApiKeyView()
        }

        let options = GMSMapViewOptions()
        options.camera = GMSCameraPosition(
            latitude: latitude,
            longitude: longitude,
            zoom: zoom
        )
        return GMSMapView(options: options)
    }

    private func makeMissingApiKeyView() -> UIView {
        let label = UILabel()
        label.backgroundColor = .systemBackground
        label.numberOfLines = 0
        label.text = "Google Maps API key is missing. Add GOOGLE_MAPS_API_KEY to Configuration/Local.xcconfig."
        label.textAlignment = .center
        label.textColor = .secondaryLabel
        return label
    }
}
