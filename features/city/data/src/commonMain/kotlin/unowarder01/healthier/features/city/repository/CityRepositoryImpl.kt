package unowarder01.healthier.features.city.repository

import unowarder01.healthier.features.city.model.CityDomain
import unowarder01.healthier.features.city.model.CityDomainStatus.READY
import unowarder01.healthier.features.city.model.CityDomainStatus.SOON
import unowarder01.healthier.network.BaseRepository

class CityRepositoryImpl: BaseRepository(), CityRepository {
    override suspend fun getCities() = previewCities
}

val previewCities = listOf(
    CityDomain(1, "Tbilisi", 1_309_028, READY),
    CityDomain(2, "Batumi", 246_267, READY),
    CityDomain(3, "Kutaisi", 153_799, READY),
    CityDomain(4, "Rustavi", 130_174, READY),
    CityDomain(5, "Gori", 49_381, SOON),
    CityDomain(6, "Zugdidi", 45_739, SOON),
    CityDomain(7, "Poti", 38_492, SOON),
    CityDomain(8, "Khashuri", 25_447, SOON),
    CityDomain(9, "Samtredia", 24_310, SOON),
    CityDomain(10, "Marneuli", 23_239, SOON),
    CityDomain(11, "Zestafoni", 21_715, SOON),
    CityDomain(12, "Telavi", 20_049, SOON),
    CityDomain(13, "Kobuleti", 19_907, SOON),
    CityDomain(14, "Senaki", 19_887, SOON),
    CityDomain(15, "Akhaltsikhe", 19_247, SOON),
    CityDomain(16, "Ozurgeti", 15_550, SOON),
    CityDomain(17, "Chiatura", 14_056, SOON),
    CityDomain(18, "Gardabani", 12_955, SOON),
    CityDomain(19, "Kaspi", 12_671, SOON),
    CityDomain(20, "Akhalkalaki", 11_154, SOON),
    CityDomain(21, "Borjomi", 10_763, SOON),
    CityDomain(22, "Sagarejo", 10_540, SOON),
    CityDomain(23, "Tskaltubo", 10_136, SOON),
    CityDomain(24, "Bolnisi", 9_521, SOON),
    CityDomain(25, "Khoni", 8_948, SOON),
    CityDomain(26, "Tkibuli", 8_402, SOON),
    CityDomain(27, "Mtskheta", 7_970, SOON),
    CityDomain(28, "Kvareli", 7_923, SOON),
    CityDomain(29, "Gurjaani", 7_749, SOON),
    CityDomain(30, "Kareli", 6_965, SOON),
    CityDomain(31, "Sachkhere", 6_522, SOON),
    CityDomain(32, "Akhmeta", 6_424, SOON),
    CityDomain(33, "Lanchkhuti", 6_296, SOON),
    CityDomain(34, "Dusheti", 6_198, SOON),
    CityDomain(35, "Lagodekhi", 5_794, SOON),
    CityDomain(36, "Dedoplistskaro", 5_770, SOON),
    CityDomain(37, "Tsalenjikha", 5_737, SOON),
    CityDomain(38, "Ninotsminda", 5_193, SOON),
    CityDomain(39, "Tsnori", 4_829, SOON),
    CityDomain(40, "Abasha", 4_704, SOON),
    CityDomain(41, "Terjola", 4_511, SOON),
    CityDomain(42, "Martvili", 4_100, SOON),
    CityDomain(43, "Khobi", 4_087, SOON),
    CityDomain(44, "Vani", 3_663, SOON),
    CityDomain(45, "Baghdati", 3_537, SOON),
    CityDomain(46, "Vale", 3_452, SOON),
    CityDomain(47, "Jvari", 3_171, SOON),
    CityDomain(48, "Dmanisi", 3_164, SOON),
    CityDomain(49, "Tsalka", 3_060, SOON),
    CityDomain(50, "Tetritskaro", 2_973, SOON),
    CityDomain(51, "Oni", 2_658, SOON),
    CityDomain(52, "Ambrolauri", 2_227, SOON),
    CityDomain(53, "Sighnaghi", 1_651, SOON),
    CityDomain(54, "Tsageri", 1_252, SOON),
)