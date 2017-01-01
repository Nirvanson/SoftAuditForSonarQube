package traumtaenzer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import traumtaenzer.models.Attendee;
import traumtaenzer.models.AttendeeRepository;
import traumtaenzer.models.Course;
import traumtaenzer.models.CourseNotification;
import traumtaenzer.models.CourseNotificationRepository;
import traumtaenzer.models.CourseRepository;
import traumtaenzer.models.Product;
import traumtaenzer.models.Product.ProductType;
import traumtaenzer.models.ProductList;
import traumtaenzer.models.ReceiptItemRepository;
import traumtaenzer.models.ReceiptRepository;
import traumtaenzer.models.Reorder;
import traumtaenzer.models.ReorderRepository;
import traumtaenzer.models.ReservationRepository;
import traumtaenzer.models.SalaryManager;
import traumtaenzer.models.SalaryManagerStatus;
import traumtaenzer.models.SalaryManagerStatusRepository;
import traumtaenzer.models.SalesStatistics;
import traumtaenzer.models.StatisticsRecording;
import traumtaenzer.models.StatisticsRecording.RecordingType;
import traumtaenzer.models.Supplier;
import traumtaenzer.models.SupplierRepository;
import traumtaenzer.models.User;
import traumtaenzer.models.UserRepository;

@Component
public class TraumtaenzerDataInitializer implements DataInitializer {
	private final UserAccountManager userAccountManager;
	private final ProductList<Product> products;
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final AttendeeRepository attendeeRepository;
	private final ReservationRepository reservationRepository;
	private final SalesStatistics salesStatistics;
	private final ReorderRepository reorderRepository;
	private final SalaryManager salaryManager;
	private final SalaryManagerStatusRepository salaryManagerStatusRepository;
	private final ReceiptRepository receiptRepository;
	private final ReceiptItemRepository receiptItemRepository;
	private final SupplierRepository supplierRepository;
	private final CourseNotificationRepository courseNotificationRepository;

	@Autowired
	private Environment springEnvironment;

	@Autowired
	public TraumtaenzerDataInitializer(
			ReservationRepository reservationRepository,
			UserAccountManager userAccountManager,
			CourseRepository courseRepository,
			AttendeeRepository attendeeRepository,
			UserRepository userRepository,
			ProductList<Product> products,
			SalesStatistics salesStatistics,
			ReorderRepository reorderRepository,
			SalaryManager salaryManager,
			SalaryManagerStatusRepository salaryManagerStatusRepository,
			ReceiptRepository receiptRepository,
			ReceiptItemRepository receiptItemRepository,
			SupplierRepository supplierRepository,
			CourseNotificationRepository courseNotificationRepository) {

		Assert.notNull(userAccountManager, "UserAccountManager must not be null!");

		this.userAccountManager = userAccountManager;
		this.products = products;
		this.userRepository = userRepository;
		this.courseRepository = courseRepository;
		this.attendeeRepository = attendeeRepository;
		this.reservationRepository = reservationRepository;
		this.salesStatistics = salesStatistics;
		this.reorderRepository = reorderRepository;
		this.salaryManager = salaryManager;
		this.salaryManagerStatusRepository = salaryManagerStatusRepository;
		this.receiptRepository = receiptRepository;
		this.receiptItemRepository = receiptItemRepository;
		this.supplierRepository = supplierRepository;
		this.courseNotificationRepository = courseNotificationRepository;
	}

	/*
	 * (non-Javadoc)
	 * @see org.salespointframework.core.DataInitializer#initialize()
	 */
	@Override
	public void initialize() {

		initializeUsers(userAccountManager, userRepository);

		// Do not initialize anything if unit-testing
		if (springEnvironment.acceptsProfiles("test"))
			return;

		initializeProducts(products);
		initializeAttendeesAndCourses(attendeeRepository, courseRepository);
		initializeSalesStatistics(salesStatistics);
		initializeSalaryManager();
	}

	private void initializeUsers(UserAccountManager userAccountManager, UserRepository userRepository) {

		// Skip creation if database was already populated
		if (userAccountManager.findByUsername("boss").isPresent()) {
			return;
		}



		UserAccount bossUserAccount = userAccountManager.create("boss", "123",
				User.ROLE_CAN_CHECKOUT,
				User.ROLE_CAN_MANAGE_ATTENDEES,
				User.ROLE_CAN_MANAGE_COURSES,
				User.ROLE_CAN_MANAGE_USERS,
				User.ROLE_CAN_MANAGE_PRODUCTS,
				User.ROLE_CAN_MANAGE_RESERVATIONS,
				User.ROLE_CAN_MANAGE_STATISTICS);
		bossUserAccount.setFirstname("Klaus");
		bossUserAccount.setLastname("Traumt\u00e4nzer");

		UserAccount maxUserAccount = userAccountManager.create("max", "123",
				User.ROLE_CAN_CHECKOUT);
		maxUserAccount.setFirstname("Max");
		maxUserAccount.setLastname("Imum");

		userAccountManager.save(bossUserAccount);
		userAccountManager.save(maxUserAccount);

		User u1 = new User(bossUserAccount, 123.0f, "0123-123-123-12");
		User u2 = new User(maxUserAccount, 321.0f, "0345-345-345-34");
		userRepository.save(u1);
		userRepository.save(u2);
	}

	private void initializeProducts(ProductList<Product> products){

		if (products.count() > 0) {
			// When the application starts, no products must be in the cart
			for (Product p : products) {
				if (p.isInCart()) {
					p.setInCart(false);
					products.saveProduct(p);
				}
			}

			return;
		}

		Product[] initProducts = new Product[] {
				/*0*/ new Product(ProductType.SHOPPRODUCT, "Aquarellpinsel", 9.99f, 100, "JAX\u00a9-Hair Synthetik-Pinsel, flach", "", Arrays.asList("Pinsel", "Malen")),
				/*1*/ new Product(ProductType.SHOPPRODUCT, "Grundierpinsel", 8.79f, 100, "Von extraschmal bis extrabreit reicht das Spektrum des flachen Borstenpinsels f\u00FCr Kindergarten, Schule, Atelier, Akademie oder Malkurs. Der vielseitige Klassiker ist f\u00FCr praktisch allen K\u00fcnstlerfarben anwendbar.", "", Arrays.asList("Pinsel", "Malen")),
				/*2*/ new Product(ProductType.SHOPPRODUCT, "Aquarellkasten", 10.99f, 1, "Schmincke HORADAM - Holzkasten mit 24 ganzen N\u00e4pfchen", "", Arrays.asList("Farbe", "Malen")),
				/*3*/ new Product(ProductType.SHOPPRODUCT, "Gouache", 5.89f, 100, "Gouache Farbe in Tube", "", Arrays.asList("Farbe", "Malen")),
				/*4*/ new Product(ProductType.SHOPPRODUCT, "Zeichenkohle", 9.69f, 100, "Faber Castell Zeichen Kohle", "", Arrays.asList("Zeichnen")),
				/*5*/ new Product(ProductType.SHOPPRODUCT, "Malkarton", 7.99f, 100, "Qualitativer Malkarton, wei\u00DF", "", Arrays.asList("Malen")),
				/*6*/ new Product(ProductType.SHOPPRODUCT, "Pastellpapier", 9.99f, 100, "", ""),
				/*7*/ new Product(ProductType.SHOPPRODUCT, "Keilrahmen", 10.99f, 100, "Keilrahmen fuer den K\u00FCnstler", "", Arrays.asList("Malen")),
				/*8*/ new Product(ProductType.SHOPPRODUCT, "Acrylfarben Set", 12.99f, 100, "Verschiedenste Acrylfarben im Tubenset", "", Arrays.asList("Farbe", "Malen")),
				/*9*/ new Product(ProductType.SHOPPRODUCT, "Zeichentinte", 3.99f, 100, "Qualitativ hochwertige Zeichentinte von Schmincke", "", Arrays.asList("Zeichnen")),
				/*10*/ new Product(ProductType.SHOPPRODUCT, "Universalgrundierung", 9.99f, 100, "Universalgrundierung von Schmincke", "", Arrays.asList("Malen")),
				/*11*/ new Product(ProductType.SHOPPRODUCT, "Tischstaffeleien", 8.99f, 100, "Aus Holz gefertigte Tischstaffeleien f\u00FCr s\u00E4mtliche Keilrahmengr\u00F6\u00DFen", "", Arrays.asList("Malen")),
				/*12*/ new Product(ProductType.SHOPPRODUCT, "Bleistifte", 9.99f, 100, "Bleistifte in verschiedenen St\u00E4rken - von 6B bis 6H.", "", Arrays.asList("Zeichnen")),

				/*13*/ new Product(ProductType.PAINTING, "Mona Lisa", 580.0f, 1, "Die ber\u00FChmt ber\u00FCchtigte Mona Lisa. Dieses edle Gem\u00E4lde ist eine impressionistische Interpretation des Werkes von Leonardo da Vinci", ""),
				/*14*/ new Product(ProductType.PAINTING, "Der traurige Baum", 420.0f, 1, "Auch B\u00E4ume haben Gef\u00FChle. Und das habe ich versucht, in diesem Gem\u00E4lde mit inpressionistischer, kr\u00E4ftiger Strichf\u00FChrung zum Ausdruck zu bringen.", ""),
				/*15*/ new Product(ProductType.PAINTING, "Fallender Stern", 1100.0f, 1, "Diese Fotografie zeigt einen fallenden Stern. Aufgenommen in einer kalten Freitagnacht mit klarem Himmel und bei Vollmond.", "")
		};

		Supplier s1 = new Supplier("Faber Castell", "+49 (0)911 9965-0", "info@faber-castell.de", "N\u00fcrnberger Stra\u00DFe 2, 90546 Stein", 0.5f);
		Supplier s2 = new Supplier("Pelikan", "+49 (0)511 6969-0", "info@pelikan.de", "Werftstra\u00DFe 9, 30163 Hannover", 0.1f);
		Supplier s3 = new Supplier("Artservice & Tube", "+49-211-905990", "artservice.dus@t-online.de", "Himmelgeister Str. 14-16, 40225 D\u00fcsseldorf", 0.7f);
		Supplier s4 = new Supplier("Schmincke", "+49(0)211/2509-0", "info@schmincke.de", "Otto-Hahn-Str. 2, 40699 Erkrath", 0.3f);

		registerSupplierProducts(s1, initProducts[0], initProducts[1], initProducts[4], initProducts[12]);
		registerSupplierProducts(s2, initProducts[0], initProducts[1], initProducts[2], initProducts[3], initProducts[8], initProducts[9], initProducts[10]);
		registerSupplierProducts(s3, initProducts[5], initProducts[6], initProducts[7], initProducts[6], initProducts[11]);
		registerSupplierProducts(s4, initProducts[2], initProducts[3], initProducts[8], initProducts[9], initProducts[10]);

		products.addProducts(Arrays.asList(initProducts));

		supplierRepository.save(s1);
		supplierRepository.save(s2);
		supplierRepository.save(s3);
		supplierRepository.save(s4);

		Optional<UserAccount> userAccount = userAccountManager.findByUsername("boss");
		if (userAccount != null) {
			User user = userRepository.findByUserAccount(userAccount.get());
			int i = 0;
			for (Product product : products) {
				if (product.getType() == ProductType.SHOPPRODUCT && i < 5) {
					initializeReorder(reorderRepository, product, userAccountManager, user, s1);
					i++;
				}
			}
		}
	}

	private static int randomInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	private static boolean randomBoolean() {
		return ThreadLocalRandom.current().nextBoolean();
	}

	private static float randomFloat(float min, float max) {
		return ThreadLocalRandom.current().nextFloat() * (max - min) + min;
	}

	private void initializeSalesStatistics(SalesStatistics salesStatistics) {

		if (salesStatistics.count() > 0)
			return;

		int numMonths = 30;
		YearMonth month = YearMonth.now().minusMonths(1);
		for (int n = 0, i = numMonths; i >= 0; --i) {

			int numRecordings = randomInt(8, 12);
			int dayDiff = (int)Math.floor(month.lengthOfMonth() / (double)numRecordings);
			int day = 1;
			for (int k = 0; k < numRecordings; ++k) {
				int hour = randomInt(0, 23);
				int minute = randomInt(0, 59);
				LocalDateTime dateTime = LocalDateTime.of(month.getYear(), month.getMonthValue(), day, hour, minute);

				day += dayDiff;

				float minimumAmount = 200.0f;
				float randomAmount = randomFloat(-10.0f, 10.0f);

				// Increase minimum amount with each month to produce more realistic output
				if (randomAmount > 0) {
					minimumAmount += i * 20.0f;
				}

				randomAmount += (randomAmount < 0) ? -minimumAmount : minimumAmount;


				String typeStr = "";
				RecordingType type;
				if (randomAmount < 0) {
					typeStr = "Ausgabe";
					type = RecordingType.PAID_REORDER;
				} else {
					typeStr = "Einnahme";
					type = (randomBoolean()) ? RecordingType.SOLD_PAINTING : RecordingType.SOLD_SHOPPRODUCT;
				}

				StatisticsRecording recording = new StatisticsRecording(dateTime, randomAmount, "Zufaellige " + typeStr + " #" + n, type);
				salesStatistics.addStatisticsRecording(recording);

				++n;
			}

			month = month.minusMonths(1);
		}
	}

	private void initializeAttendeesAndCourses(AttendeeRepository attendeeRepository, CourseRepository courseRepository){

		if (courseRepository.count() > 0 || attendeeRepository.count() > 0)
			return;

		Optional<UserAccount> userAccount = userAccountManager.findByUsername("boss");
		if (userAccount == null || userAccount.get() == null) {
			return;
		}

		User user = userRepository.findByUserAccount(userAccount.get());

		// Setup attendees
		Attendee a1 = new Attendee("Max Mustermann", "muster@muster.de", "Dresden, Blumenstrasse, 1");
		Attendee a2 = new Attendee("Anna Frank", "anna@muster.de", "Dresden, Schoenestrasse, 7");
		attendeeRepository.save(Arrays.asList(a1, a2));

		// Setup courses
		Course c1 = new Course("Malen nach Musik", "Ausgezeichneter Kurs, empfehlenswert", 25, LocalDateTime.of(2015, Month.DECEMBER, 12, 18, 00, 00), true, user, 1000);
		c1.addAttendee(a1);

		Course c2 = new Course("Bob Ross Tutorial", "Guter Kurs", 20, LocalDateTime.of(2015, Month.DECEMBER, 20, 19, 30, 00), false, user, 1500);
		c2.addAttendee(a1);
		c2.addAttendee(a2);

		courseRepository.save(Arrays.asList(c1, c2));

		// Setup Course notifications
		CourseNotification[] notifications = new CourseNotification[] {
				new CourseNotification(c1, "Vorbereitung", "Sie koennnen sich auf diesen Kurs vorbereiten, indem Sie ein paar Youtube Videos dazu schauen.", user.getFullName()),
				new CourseNotification(c2, "Materialien f\u00FCr den Kurs", "Bitte bringen Sie noch einen Lappen mit. Wir verkaufen leider keine Lappen.", user.getFullName())
		};

		courseNotificationRepository.save(Arrays.asList(notifications));
	}

	private void initializeReorder(ReorderRepository reorderRepository, Product product, UserAccountManager userAccountManager, User user, Supplier supplier) {

		Reorder reorder = new Reorder(user, product, 50, supplier);
		reorderRepository.save(reorder);
	}

	private void initializeSalaryManager() {
		if (salaryManagerStatusRepository.count() == 0) {
			SalaryManagerStatus status = new SalaryManagerStatus();
			status.setLastSalaryDate(LocalDate.of(LocalDate.now().getYear(), 1, 1));
			salaryManagerStatusRepository.save(status);
			salaryManager.setStatus(status);
		}
		else {
			salaryManager.setStatus(salaryManagerStatusRepository.findAll().iterator().next());
		}
	}

	private void registerSupplierProducts(Supplier supplier, Product ... products) {
		for (Product product : products) {
			supplier.addProvidedProduct(product);
		}
	}
}