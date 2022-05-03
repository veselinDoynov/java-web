package com.customer.web.entity.web;

import com.customer.web.entity.web.transformed.StudentTransformed;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name="course")
public class Course {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="title")
	private String title;

	@ManyToOne(fetch=FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="instructor_id")
	@JsonIgnore
	private Instructor instructor;

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="course_id")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Review> reviews;

	@ManyToMany(fetch=FetchType.EAGER,
			cascade= {CascadeType.PERSIST, CascadeType.MERGE,
					CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
			name="course_student",
			joinColumns=@JoinColumn(name="course_id"),
			inverseJoinColumns=@JoinColumn(name="student_id")
	)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Student> students;


	public Course() {

	}

	public Course(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	// add a convenience method

	public void addReview(Review theReview) {

		if (reviews == null) {
			reviews = new ArrayList<>();
		}

		reviews.add(theReview);
	}

	public List<Student> getStudents() {
		return students;
	}

	public List<StudentTransformed> getStudentsTransformed() {

		if(students == null || students.isEmpty()) {
			return null;
		}
		return students.stream().map(student -> {
			return new StudentTransformed(student);
		}).collect(Collectors.toList());
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	// add a convenience method

	public void addStudent(Student theStudent) {

		if (students == null) {
			students = new ArrayList<>();
		}

		students.add(theStudent);
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + "]";
	}
}
