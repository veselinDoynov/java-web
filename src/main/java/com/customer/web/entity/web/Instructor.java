package com.customer.web.entity.web;

import com.customer.web.entity.web.transformed.CourseTransformed;
import com.customer.web.listeners.AuditTrailListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="instructor")
@EntityListeners(AuditTrailListener.class)
public class Instructor {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	@Column(name="email")
	private String email;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="instructor_detail_id")
	private InstructorDetail instructorDetail;

	@OneToMany(fetch=FetchType.EAGER, mappedBy="instructor",
			cascade= CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Course> courses;


	public Instructor() {

	}

	public Instructor(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public InstructorDetail getInstructorDetail() {
		return instructorDetail;
	}

	public void setInstructorDetail(InstructorDetail instructorDetail) {
		this.instructorDetail = instructorDetail;
	}

	@Override
	public String toString() {
		return "Instructor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", instructorDetail=" + instructorDetail + "]";
	}

	public List<Course> getCourses() {
		return courses;
	}

	public List<CourseTransformed> getCoursesTransformed() {
		if(courses == null || courses.isEmpty()) {
			return null;
		}
		return courses.stream().map( course -> {
			return new CourseTransformed(course);
		}).collect(Collectors.toList());
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public void add(Course tempCourse) {

		if (courses == null) {
			courses = new ArrayList<>();
		}

		courses.add(tempCourse);
	}

	public String getInstructorDetails() {
		return  this.getFirstName() + " " + this.getLastName() + " with email : " + this.getEmail();
	}
}






