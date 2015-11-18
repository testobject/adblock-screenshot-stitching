
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.Arrays;

/**
 * Appium test to screen capture an entire website and save it as a PNG.
 */
@RunWith(Parameterized.class)
public class CaptureWebPageTest {
	private static final String APPIUM_SERVER = getEnvOrDefault("APPIUM_SERVER", "https://app.testobject.com:443/api/appium/wd/hub");
	private static final String TESTOBJECT_DEVICE = getEnvOrDefault("TESTOBJECT_DEVICE", "iPhone_6_16GB_real");
	private static final String TESTOBJECT_APPIUM_VERSION = getEnvOrDefault("TESTOBJECT_APPIUM_VERSION", "1.3.7");
	private static String TESTOBJECT_API_KEY = getEnvOrDefault("TESTOBJECT_API_KEY", "");
	private static String TESTOBJECT_APP_ID = getEnvOrDefault("TESTOBJECT_APP_ID", "1");

	private static Instant startTime = null; // Used for screenshot folder
	private static TestObjectRemoteWebDriver driver;
	private final String url;

	public CaptureWebPageTest(String url) {
		this.url = url;
	}

	@Parameterized.Parameters
	public static Iterable websites() {
		return Arrays.asList(
			new String[][] {
					{ "http://15minutefashion.about.com/sitesearch.htm?q=weight+loss&boost=3&SUName=15minutefashion" }
/*					{ "http://247sports.com/Bolt/Owner-of-Packers-banner-that-Newton-removed-has-reported-theft--40932066" },
					{ "http://3d.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=3d" },
					{ "http://4wheeldrive.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=4wheeldrive" },
					{ "http://5newsonline.com/2015/11/09/mizzou-president-tim-wolfe-resigns-after-protests/" },
					{ "http://712educators.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=712educators" },
					{ "http://80music.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=80music" },
					{ "http://abcnews.go.com/US/university-missouri-president-tim-wolfe-resigns-amid-protests/story?id=35076098" },
					{ "http://accesorios.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=accesorios" },
					{ "http://accessories.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=accessories" },
					{ "http://accountingsoftware.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=accountingsoftware" },
					{ "http://acne.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=acne" },
					{ "http://actionfigures.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=actionfigures" },
					{ "http://actividadesfamilia.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=actividadesfamilia" },
					{ "http://add.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=add" },
					{ "http://addictions.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=addictions" },
					{ "http://adolescentes.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=adolescentes" },
					{ "http://adoption.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=adoption" },
					{ "http://adulted.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=adulted" },
					{ "http://adventuretravel.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=adventuretravel" },
					{ "http://advertising.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=advertising" },
					{ "http://africanhistory.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=africanhistory" },
					{ "http://afroamhistory.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=afroamhistory" },
					{ "http://ahorro.about.com/sitesearch.htm?q=lenovo&boost=3&SUName=ahorro" },
					{ "http://aids.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=aids" },
					{ "http://airtravel.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=airtravel" },
					{ "http://ajedrez.about.com/sitesearch.htm?q=iphone+6&boost=3&SUName=ajedrez" },
					{ "http://animals.mom.me/deter-raccoons-chickens-7305.html" },
					{ "http://annuaire.118712.fr/s/recherche/samsung" },
					{ "http://apcmag.com/ddr4-on-z170-reviews.htm/" },
					{ "http://bfmbusiness.bfmtv.com/monde/mort-de-helmut-schmidt-l-euro-perd-son-pere-929138.html" },
					{ "http://bgr.com/2015/11/09/nintendo-snes-playstation-sony/" },
					{ "http://cnnespanol.cnn.com/2015/11/09/el-plan-de-google-facebook-y-spacex-para-dominar-el-mundo/" },
					{ "http://de.ask.com/web?q=iphone+6&qsrc=0&o=312&l=dir&qo=homepageSearchBox" },
					{ "http://gearpatrol.com/2015/07/14/l-l-bean-makes-iconic-boots/" },
					{ "http://search.about.com/?q=lenovo" },
					{ "http://search2.virginmedia.com/index/site/?channel=homepage-prospect&vml=non-customer&vmt=0&submit-search=Search&q=iphone" },
					{ "http://shop.ziphip.com/search.pg?z=New+York%2C+NY&q=LENOVO" },
					{ "http://www.01net.com/actualites/sfr-lance-la-box-fibre-zive-qu-il-annonce-comme-la-plus-puissante-du-marche-928755.html" },
					{ "http://www.01net.com/search?q=lenovo&filter=0" },
					{ "http://www.11alive.com/story/news/2015/11/09/mizzou-faculty-walks-out-student-association-calls-presidents-removal/75448392/" },
					{ "http://www.12news.com/story/news/local/valley/2015/11/09/phoenix-homeowner-shoots-trespasser/75445698/" },
					{ "http://www.13newsnow.com/story/features/2015/11/09/mom-says-fight-to-keep-daughters-hair-curly-kept-her-off-cheer-team/75451442/" },
					{ "http://www.14news.com/story/30469083/4-killed-including-2-americans-in-jordan-police-shooting" },
					{ "http://www.2dehands.be/markt/2/BMW/?" },
					{ "http://www.2ememain.be/film/2/avengers/" },
					{ "http://www.abritel.fr/search/keywords:paris" },
					{ "http://www.airliners.net/aviation-forums/general_aviation/read.main/6544846/" },
					{ "http://www.aktualne.cz/hledani/?query=obama" },
					{ "http://www.alamaula.cl/q/phone/S0" },
					{ "http://www.alamaula.co.cr/q/ipad/S0" },
					{ "http://www.allocine.fr/video/video-19558701/" },
					{ "http://www.answerbag.com/q_view/3502147" },
					{ "http://www.apartmenttherapy.com/ikea-ribba-picture-ledges-211850" },
					{ "http://www.app.com/story/news/crime/jersey-mayhem/2015/11/08/fire-atlantis-golf-course/75436042/" },
					{ "http://www.avclub.com/article/best-funny-animals-ape-zazu-228078" },
					{ "http://www.azcentral.com/story/sports/ncaaf/asu/2015/11/09/despite-disappointing-season-asu-having-in-state-recruiting-success/75487826/" },
					{ "http://www.baltimoresun.com/news/maryland/baltimore-city/bs-md-barbers-60th-year-20151109-story.html" },
					{ "http://www.battlecreekenquirer.com/story/news/local/2015/11/09/miller-college-cuts-staff/75467812/" },
					{ "http://www.baxterbulletin.com/story/news/local/2015/11/09/sheriff-ids-missing-couple-continuing-search/75467304/" },
					{ "http://www.begeek.fr/nexus-5x-le-capteur-de-lappareil-photo-est-a-lenvers-184883" },
					{ "http://www.bellinghamherald.com/news/nation-world/national/article44046114.html" },
					{ "http://www.bradenton.com/latest-news/article43996188.html" },
					{ "http://www.businessinsider.com.au/why-australias-landmark-tax-ruling-against-chevron-is-a-first-battle-in-the-global-war-on-profit-shifting-2015-11" },
					{ "http://www.capitalgazette.com/sports/high_school_sports/ph-ac-cs-fhockey-sevpark-springbrook1110-20151109-story.html" },
					{ "http://www.celebritynetworth.com/richest-celebrities/rock-stars/gwen-stefani-net-worth/" },
					{ "http://www.channel3000.com/money/starbucks-plain-red-holiday-cups-stir-up-controversy/36334676" },
					{ "http://www.chartsinfrance.net/actualite/news-99681.html" },
					{ "http://www.cleveland19.com/story/30478455/ohio-state-qb-barrett-has-court-date-on-driving-citation" },
					{ "http://www.dallasnews.com/news/metro/20151109-da-hawk-defends-track-record-at-first-town-hall-meeting-since-absence.ece" },
					{ "http://www.ehow.com/list_6322010_cheap-halloween-costume-ideas-adults.html" },
					{ "http://www.fox19.com/story/30476415/appeals-court-rules-against-obama-immigration-plan" },
					{ "http://www.fox8live.com/story/30478456/marrero-man-is-wanted-for-murder-in-monday-night-shooting" },
					{ "http://www.foxbusiness.com/economy-policy/2015/11/10/keystone-pipeline-what-gop-candidates-are-saying/?intcmp=bigtopmarketfeatures" },
					{ "http://www.foxnews.com/politics/2015/11/10/after-scrappy-week-carson-in-debate/?intcmp=hpbt2" },
					{ "http://www.gutefrage.net/frage/was-sind-vorschuessig-gezahlte-steuerfrei-zuschlaege" },
					{ "http://www.hawaiinewsnow.com/story/30477616/occc-inmate-injured-after-alleged-stabbing" },
					{ "http://www.investing.com/news/economic-indicators/china-trade-data-underlines-slowdown-fears-370237" },
					{ "http://www.jeuxvideo.com/test/449231/fallout-4-le-post-apo-raisonnable.htm" },
					{ "http://www.kait8.com/story/30473458/seaworld-to-end-orca-shows-by-2017-in-san-diego" },
					{ "http://www.marketwatch.com/story/investors-are-ignoring-the-strong-dollar-at-their-peril-2015-11-06" },
					{ "http://www.msn.com/en-us/news/technology/iphone-7-rumors-point-to-upcoming-panic-mode-synaptics-screens/ar-CC2HWp" },
					{ "http://www.mysearch.com/web?q=LENOVO&o=&tpr=1&ts=1442744089778" },
					{ "http://www.news8000.com/news/crews-working-to-clean-mississippi-after-train-derailment/36352292" },
					{ "http://www.quoka.de/autoteile/vw-teile/c9649a159079542/autositze-vw-sharan.html?hback=TRUE" },
					{ "http://www.search-results.com/web?q=iphone+6&o=&tpr=1&ts=1442744027622" },
					{ "http://www.search.ask.com/web?q=iphone+6&o=&tpr=1&ts=1442744058279" },
					{ "http://www.slate.com/blogs/the_slatest/2015/11/05/george_h_w_bush_on_donald_rumsfeld_dick_cheney_george_w_iraq.html" },
					{ "http://www.vrbo.com/vacation-rentals/mexico/yucatan-mayan-riviera/quintana-roo/cancun?sleeps=2-plus&from-date=2016-02-01&to-date=2016-02-08" },
					{ "http://www.wdam.com/story/30467616/obama-netanyahu-minimize-differences-renew-call-for-peace" },
					{ "http://www.webcrawler.com/search/web?fcoid=417&fcop=topnav&fpid=27&aid=63f6dfaf-4ee7-4021-bb16-0c899beb1232&ridx=1&q=lenovo&ql=&ss=t" },
					{ "http://www.when.com/search?s_it=searchbox.webhome&v_t=na&q=LENOVO" },
					{ "http://www.willhaben.at/jobs/suche?region=AT&keyword=student&regionTitle=&perimeter=0" },
					{ "http://www.windstream.net/search/index.php?q=true+detective&context=homepage" },
					{ "http://www.windstreambusiness.net/search/index.php?context=homepage&tab=Web&q=iphone+6" },
					{ "http://www.yellowpages.com/search?search_terms=subway&geo_location_terms=cologne" },
					{ "http://www.yelp.com/search?find_desc=tacos&find_loc=San+Francisco%2C+CA&ns=1" },
					{ "http://www1.search-results.com/web?q=ipad&o=&tpr=1&ts=1442845303226" },
					{ "http://zena.centrum.cz/hledani.phtml?q=LENOVO&hledat=zena&time=relevance&section=0" },
					{ "https://au.news.yahoo.com/nsw/a/30018503/massive-storm-shelf-looms-over-bondi-beach-as-sydney-cops-wild-weather-drenching/" }*/
			}
		);
	}

	@BeforeClass
	public static void setup() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("testobject_device", TESTOBJECT_DEVICE);
		capabilities.setCapability("testobject_api_key", TESTOBJECT_API_KEY);
		capabilities.setCapability("testobject_app_id", TESTOBJECT_APP_ID);
		capabilities.setCapability("testobject_appium_version", TESTOBJECT_APPIUM_VERSION);

		URL endpoint = new URL(APPIUM_SERVER);

		driver = new TestObjectRemoteWebDriver(endpoint, capabilities);

		if (driver != null) {
			System.out.println(driver.getCapabilities().getCapability("testobject_test_report_url"));
			System.out.println(driver.getCapabilities().getCapability("testobject_test_live_view_url"));
		}

		if (startTime == null) {
			startTime = Instant.now();
		}
	}

	@AfterClass
	public static void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void openWebPageAndTakeScreenshot() throws Exception {

		driver.get(url);

		System.out.println("Requesting screenshot");
		driver.getStitchedScreenshotAs(OutputType.FILE);

	}

	protected static String getEnvOrDefault(String environmentVariable, String defaultValue) {
		String value = System.getenv(environmentVariable);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	public String getFilename() {
		String filename = url;
		filename = filename.replace("https://", "");
		filename = filename.replace("http://", "");
		filename = filename.replaceAll("\\.", "");
		return filename + ".png";
	}
}
