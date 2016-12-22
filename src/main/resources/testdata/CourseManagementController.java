package traumtaenzer.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.web.LoggedIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import traumtaenzer.models.AttendeeRepository;
import traumtaenzer.models.Course;
import traumtaenzer.models.CourseNotification;
import traumtaenzer.models.CourseNotificationRepository;
import traumtaenzer.models.CourseRepository;
import traumtaenzer.models.Product;
import traumtaenzer.models.ProductList;
import traumtaenzer.models.User;
import traumtaenzer.models.UserRepository;

@Controller
public class CourseManagementController {

	@Autowired
	private CourseRepository courses;

	@Autowired
	private ProductList<Product> products;

	@Autowired
	private UserRepository users;

	@Autowired
	private AttendeeRepository attendees;

	@Autowired
	private CourseNotificationRepository courseNotifications;

	/**
	 * Creates course and redirects to its edit page
	 *
	 * @param model stores informations for the template
	 * @param loggedInUserAccount must not be {@literal null}.
	 *
	 * @return template editcourse
	 */
	@RequestMapping(value = "/course/create")
	@PreAuthorize("hasRole('ROLE_CAN_MANAGE_COURSES')")
	public String createCourse(Model model, @LoggedIn Optional<UserAccount> loggedInUserAccount) {

		model.addAttribute("products", products);
		model.addAttribute("users", users.findAll());
		model.addAttribute("user", users.findByUserAccount(loggedInUserAccount.get()));
		model.addAttribute("products", products);

		return "editcourse";
	}

	/**
	 * Deletes a course and redirects to the courselist
	 *
	 * @param id the id of the course to be deleted
	 * @param redirectAttributes stores informations for feedback
	 *
	 * @return redirection to courses template
	 */
	@RequestMapping(value = "/course/delete/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_CAN_MANAGE_COURSES')")
	public String removeCourse(@PathVariable("id") long id, final RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("course", courses.findOne(id));

		courses.delete(id);
		redirectAttributes.addFlashAttribute("deletion", "success");

		return "redirect:/courses";
	}

	/**
	 * Saves a course which has been edited or created
	 * Redirects to the courselist
	 *
	 * @param id the id of the course to be saved
	 * @param name the name of the course to be saved
	 * @param date the date of the course to be saved
	 * @param time the time of the course to be saved
	 * @param description the description of the course to be saved
	 * @param capacity the capacity of the course to be saved
	 * @param weekly boolean to check if the course takes place weekly, can be {@literal null}
	 * @param leaderid the id of the leader of the course to be saved
	 * @param materials a list of {@link Product}s to be added to the course, can be {@literal null}
	 * @param price the price of the course to be saved
	 * @param loggedInUserAccount the account of the user
	 * @param redirectAttributes stores informations for feedback
	 *
	 * @return redirection to courses template
	 */
	@RequestMapping(value = "/course/{id}", method = RequestMethod.POST)
	public String saveCourse(@PathVariable("id") String id,
			@RequestParam("name") String name,
			@RequestParam("date") String date,
			@RequestParam("time") String time,
			@RequestParam("description") String description,
			@RequestParam("capacity") int capacity,
			@RequestParam(name = "weekly", required = false) Boolean weekly,
			@RequestParam("leader") long leaderid,
			@RequestParam(name = "courseMaterials[]", required = false ) long[] materials,
			@RequestParam("price") float price,
			@LoggedIn Optional<UserAccount> loggedInUserAccount,
			final RedirectAttributes redirectAttributes) {

		User user = users.findByUserAccount(loggedInUserAccount.get());

		// Warning: DateTimeParseException can be thrown here.
		// Manipulating the HTML pattern requires hacking though, so the should be fine.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.y H:m");
		LocalDateTime datetime = LocalDateTime.parse(date + " " + time, formatter);

		boolean isweekly = (weekly != null);

		User leader = users.findOne(leaderid);
		try {
			if (leader == null) {
				throw new NullPointerException();
			}
		} catch(Exception e) {
			e.printStackTrace(System.out);
			return "redirect:/courses";
		}

		Course course = null;

		if (id.equalsIgnoreCase("new")) {
			if (!user.canManageCourses())
				return "redirect:/courses";

			course = new Course(name, description, capacity, datetime, isweekly, leader, price);
		} else {
			long longid = Long.valueOf(id);

			course = courses.findOne(longid);

			if (course == null)
				return "redirect:/courses";

			if (!user.canManageCourses() && user.getId() != course.getLeader().getId())
				return "redirect:/courses";

			course.setName(name);
			course.setDate(datetime);
			course.setDescription(description);
			course.setCapacity(capacity);
			course.setWeekly(isweekly);
			course.setPrice(price);
			course.setLeader(leader);

			// Remove all materials, so they can be filled later
			course.removeAllMaterials();
		}

		if (materials != null) {
			for (long materialID : materials) {
				Product prod = products.getProduct(materialID);
				if(prod == null) {
					continue;
				}

				course.addMaterial(prod);
			}
		}

		redirectAttributes.addFlashAttribute("course", course);
		courses.save(course);

		redirectAttributes.addFlashAttribute("saving", "success");

		return "redirect:/courses";
	}

	/**
	 * Request mapping to show the course detail and edit fields,
	 * if the user is allowed to edit courses
	 *
	 * @param id the id of the course to show
	 * @param model stores the informations for the feedback
	 * @param loggedInUserAccount can be {@literal null}.
	 *
	 * @return template editcourse if the user is logged in, else template coursedetails
	 */
	@RequestMapping(value = "/course/{id}")
	public String showCourse(@PathVariable("id") long id, Model model, @LoggedIn Optional <UserAccount> loggedInUserAccount) {

		if (!loggedInUserAccount.isPresent()) {
			Course course = courses.findOne(id);
			model.addAttribute("course", course);

			return "coursedetails";
		}

		Course course = courses.findOne(id);
		model.addAttribute("course", course);
		model.addAttribute("products", products);
		model.addAttribute("users", users.findAll());
		model.addAttribute("attendees", attendees.findAll());
		model.addAttribute("user", users.findByUserAccount(loggedInUserAccount.get()));
		model.addAttribute("date", getDateString(course.getDate()));
		model.addAttribute("time", getTimeString(course.getDate()));

		return "editcourse";
	}

	/**
	 * Request mapping to show the courselist
	 *
	 * @param model stores informations for the template
	 *
	 * @return template courses
	 */
	@RequestMapping(value = "/courses")
	@PreAuthorize("isAuthenticated()")
	public String showCourseList(Model model) {

		model.addAttribute("courses", courses.findAll());

		return "courses";
	}

	/**
	 * Removes the given {@link CourseNotification} from the given {@link Course}
	 *
	 * @param courseId the id of the {@link Course} to remove the {@link CourseNotification} from
	 * @param notificationId the id of the {@link CourseNotification} to be removed
	 * @param loggedInUserAccount must not be {@literal null}
	 *
	 * @return redirection to the editcourse template
	 */
	@RequestMapping(value = "course/{courseId}/notification/delete/{notificationId}")
	public String removeCourseNotification(@PathVariable("courseId") long courseId,
										   @PathVariable("notificationId") long notificationId,
										   @LoggedIn Optional<UserAccount> loggedInUserAccount) {

		CourseNotification courseNotification;

		try {
			long courseNotificationIdLong = Long.valueOf(notificationId);

			courseNotification = courseNotifications.findOne(courseNotificationIdLong);

			if (courseNotification == null) {
				throw new NullPointerException("no courseNotification found");
			}
		} catch (NumberFormatException e) {
			return "redirect:/course/" + courseId;
		}

		Course course;

		try {
			long courseIdLong = Long.valueOf(courseId);

			course = courses.findOne(courseIdLong);

			if (course == null) {
				throw new NullPointerException("no course found");
			}
		} catch (NumberFormatException e) {
			return "redirect:/course/" + courseId;
		}

		User user = users.findByUserAccount(loggedInUserAccount.get());
		if (!user.canManageProducts() && user.getId() != course.getLeader().getId())
			return "redirect:/course/" + courseId;

		course.removeNotification(courseNotification);
		courseNotifications.delete(courseNotification);

		return "redirect:/course/" + courseId;
	}

	/**
	 * Shows the editcoursenotification formular for the new {@link CourseNotification}
	 *
	 * @param courseId the id of the {@link Course} to create the {@link CourseNotification} for
	 * @param loggedInUserAccount must not be {@literal null}
	 * @param model stores informations for the template
	 *
	 * @return template editcoursenotification
	 */
	@RequestMapping(value = "/course/{courseId}/notification/create", method = RequestMethod.GET)
	public String createCourseNotification(@PathVariable("courseId") String courseId, Model model, @LoggedIn Optional<UserAccount> loggedInUserAccount) {
		Course course;

		try {
			long courseIdLong = Long.valueOf(courseId);

			course = courses.findOne(courseIdLong);

			if (course == null) {
				throw new NullPointerException("no course found");
			}
		} catch (NumberFormatException e) {
			return "redirect:/course/" + courseId;
		}

		User user = users.findByUserAccount(loggedInUserAccount.get());
		if (!user.canManageProducts() && user.getId() != course.getLeader().getId())
				return "redirect:/course/" + courseId;

		model.addAttribute("course", course);

		return "editcoursenotification";
	}

	/**
	 * Shows the edit formular for a {@link CourseNotification}
	 *
	 * @param courseId the id of the {@link Course}
	 * @param notificationId the id of the {@link CourseNotification}
	 * @param loggedInUserAccount must not be {@literal null}
	 * @param model stores informations for the template
	 *
	 * @return template editcoursenotification
	 */
	@RequestMapping(value = "/course/{courseId}/notification/{notificationId}", method = RequestMethod.GET)
	public String editCourseNotification(@PathVariable("courseId") String courseId,
										 @PathVariable("notificationId") String notificationId,
										 @LoggedIn Optional<UserAccount> loggedInUserAccount,
										 Model model) {
		CourseNotification courseNotification;

		try {
			long courseNotificationIdLong = Long.valueOf(notificationId);

			courseNotification = courseNotifications.findOne(courseNotificationIdLong);

			if (courseNotification == null) {
				throw new NullPointerException("no courseNotification found");
			}
		} catch (NumberFormatException e) {
			return "redirect:/course/" + courseId;
		}

		Course course;

		try {
			long courseIdLong = Long.valueOf(courseId);

			course = courses.findOne(courseIdLong);

			if (course == null) {
				throw new NullPointerException("no course found");
			}
		} catch (NumberFormatException e) {
			return "redirect:/course/" + courseId;
		}

		User user = users.findByUserAccount(loggedInUserAccount.get());
		if (!user.canManageProducts() && user.getId() != course.getLeader().getId())
			return "redirect:/course/" + courseId;

		model.addAttribute("course", course);
		model.addAttribute("courseNotification", courseNotification);

		return "editcoursenotification";
	}

	/**
	 * Saves the given {@link CourseNotification}
	 *
	 * @param courseId the id of the {@link Course}
	 * @param notificationId the id of the {@link CourseNotification}
	 * @param loggedInUserAccount the account of the current {@link User}
	 * @param message the message to be saved
	 * @param topic the topic to be saved
	 *
	 * @return redirection to the editcourse template
	 */
	@RequestMapping(value = "/course/{courseId}/notification/{notificationId}", method = RequestMethod.POST)
	public String saveCourseNotification(@PathVariable("courseId") String courseId,
										 @PathVariable("notificationId") String notificationId,
										 @LoggedIn Optional<UserAccount> loggedInUserAccount,
										 @RequestParam("message") String message,
										 @RequestParam("topic") String topic) {

		UserAccount userAccount = null;

		if (loggedInUserAccount.isPresent()) {
			userAccount = loggedInUserAccount.get();
		}

		if (userAccount == null) {
			throw new NullPointerException("no userAccount found");
		}

		User user = users.findByUserAccountUserAccountIdentifier(userAccount.getId());

		if (user == null) {
			throw new NullPointerException("no user found");
		}

		Course course;

		try {
			long courseIdLong = Long.valueOf(courseId);

			course = courses.findOne(courseIdLong);

			if (course == null) {
				throw new NullPointerException("no course found");
			}
		} catch (NumberFormatException e) {
			return "redirect:/course/" + courseId;
		}

		if (!user.canManageProducts() && user.getId() != course.getLeader().getId())
			return "redirect:/course/" + courseId;

		if (notificationId.equalsIgnoreCase("new")) {
			CourseNotification courseNotification = new CourseNotification(course, topic, message, user.getFullName());

			course.addNotification(courseNotification);
			courseNotifications.save(courseNotification);
		} else {
			CourseNotification courseNotification;

			try {
				long courseNotificationIdLong = Long.valueOf(notificationId);

				courseNotification = courseNotifications.findOne(courseNotificationIdLong);

				if (courseNotification == null) {
					throw new NullPointerException("no courseNotification found");
				}
			} catch (NumberFormatException e) {
				return "redirect:/course/" + courseId;
			}

			courseNotification.setMessage(message);
			courseNotification.setCreatorName(user.getFullName());
			courseNotification.setTopic(topic);

			courseNotifications.save(courseNotification);
		}

		return "redirect:/course/" + courseId;
	}

	private String getDateString(LocalDateTime date) {
		String day  = "";
		String month = "";

		day = "" + date.getDayOfMonth();
		if (date.getDayOfMonth() < 10)
			day = "0" + day;

		month = "" + date.getMonthValue();
		if (date.getMonthValue() < 10)
			month = "0" + month;

		return day + "." + month + "." + date.getYear();
	}

	private String getTimeString(LocalDateTime time) {
		String hour  = "";
		String minute = "";

		hour = "" + time.getHour();
		if (time.getHour() < 10)
			hour = "0" + hour;

		minute = "" + time.getMinute();
		if (time.getMinute() < 10)
			minute = "0" + minute;

		return hour + ":" + minute;
	}
}