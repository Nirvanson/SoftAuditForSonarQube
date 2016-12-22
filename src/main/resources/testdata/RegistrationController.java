// package name
package traumtaenzer.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
// spring imports
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import traumtaenzer.models.Attendee;
import traumtaenzer.models.AttendeeRepository;
import traumtaenzer.models.Course;
import traumtaenzer.models.CourseRepository;
import traumtaenzer.models.Product;
import traumtaenzer.models.ProductList;
import traumtaenzer.models.SalesStatistics;
import traumtaenzer.models.StatisticsRecording;
import traumtaenzer.models.StatisticsRecording.RecordingType;

@Controller
@PreAuthorize("isAuthenticated()")
public class RegistrationController{

	@Autowired
	private ProductList<Product> productList;

	@Autowired
	private SalesStatistics salesstatistics;

	@Autowired
	private CourseRepository courses;

	@Autowired
	private AttendeeRepository attendees;

	/**
	 *Register {@link Attendee} for {@link Course}, decrease quantity of course-materials and create {@link StatisticsRecording}
	 * @param attendeeID the id of the {@link Attendee} to be registered
	 * @param courseID the id of the {@link Course} to which the {@link Attendee} is to be registered
	 * @param redirectAttributes stores informations for feedback
	 *
	 * @return redirection to editcourse template
	 */
	@RequestMapping(value = "/registerAttendee/{id}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_CAN_MANAGE_ATTENDEES')")
	public String registerAttendee(@PathVariable("id") long attendeeID, @RequestParam("cid") long courseID, final RedirectAttributes redirectAttributes) {

		Attendee attendee = attendees.findOne(attendeeID);
		if (attendee == null) {
			throw new NullPointerException("No attendee found with given ID");
		}

		Course course = courses.findOne(courseID);
		if (course == null) {
			throw new NullPointerException("No course found with given ID");
		}

		if (course.getCapacity()-course.getAttendees().size() > 0) {
			course.addAttendee(attendee);

			String recordingDescription = "Kursregistrierung: " + attendee.getName() + " in den Kurs " + course.getName() + " Produkte: ";

			float courseTotalCost = course.getPrice();
			boolean first = true;
			for (Product prod : course.getCourseMaterials()) {
				if (prod.getQuantity() == 0) {
					continue;
				}

				prod.decreaseQuantity(1);
				productList.saveProduct(prod);

				String detail = "1x " + prod.getName();

				if (!first)
					detail = ", " + detail;

				courseTotalCost += prod.getPrice();
				recordingDescription += ", " + detail;

				first = false;
			}

			if (courseTotalCost > 0.0f) {
				salesstatistics.addStatisticsRecording(new StatisticsRecording(LocalDateTime.now(), courseTotalCost, recordingDescription, RecordingType.COURSE_REGISTRATION));
			}
		}
		else {
			course.addToWaitingList(attendee);
		}

		courses.save(course);
		attendees.save(attendee);


		redirectAttributes.addFlashAttribute("registerAttendee", attendee.getName());
		redirectAttributes.addFlashAttribute("adding", "success");

		return "redirect:/course/" + courseID;
	}

	/**
	 * Gets the {@link Attendee} to be registered and shows all {@link Course}s available
	 *
	 * @param attendeeID the id of the {@link Attendee} to be registered
	 * @param model stores informations for the template
	 *
	 * @return template registerAttendee
	 */
	@RequestMapping(value = "/registerAttendee/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_CAN_MANAGE_ATTENDEES')")
	public String registerAttendee(@PathVariable("id") long attendeeID, Model model){
		model.addAttribute("courses", courses.findAll());
		model.addAttribute("attendee", attendees.findOne(attendeeID));
		return "registerattendee";
	}

	/**
	 * Unregisters an {@link Attendee} from a {@link Course}, and removes the {@link Course} from the {@link Attendee}s list of {@link Course}s
	 *
	 * @param attendeeID the id of the {@link Attendee} to be unregistered
	 * @param courseID the id of the {@link Course} from which the attendee is to be removed
	 * @param model stores informations for the template
	 * @param redirectAttributes stores informations for feedback
	 *
	 * @return redirection to editcourse template
	 */
	@RequestMapping(value = "/unregisterAttendee/{cid}/{aid}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_CAN_MANAGE_ATTENDEES')")
	public String unregisterAttendee(@PathVariable("cid") long courseID, @PathVariable("aid") long attendeeID, Model model, final RedirectAttributes redirectAttributes) {
		Course course = courses.findOne(courseID);
		if (course == null) {
			throw new NullPointerException("No course found with given ID");
		}

		Attendee attendee = attendees.findOne(attendeeID);
		if (attendee == null) {
			throw new NullPointerException("No attendee found with given ID");
		}

		if (course.hasAttendee(attendee)) {
			course.removeAttendee(attendee);
		}

		if (course.isAttendeeOnWaitingList(attendee)) {
			course.removeFromWaitingList(attendee);
		}

		courses.save(course);

		redirectAttributes.addFlashAttribute("unRegisterAttendee", attendee.getName());
		redirectAttributes.addFlashAttribute("deletion", "success");

		return "redirect:/course/" + courseID;
	}
}
