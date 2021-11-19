package model;


import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="member")
public class MemberBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="member_id")
	private Integer memberId;
	@Column(name = "password")
	private String memberPassword;
	@Column(name = "member_name")
	private String memberName;
	@Column(name= "email")
	private String email;
	@Column(name="phone")
	private String phone;
	@Column(name = "birthday")
	private Date birthday;
	@Column(name="role")
	private String role;
	@Column(name = "member_image")
	private Blob memberPicture;
	@Column(name = "create_time")
	private Timestamp registerTime;
	
	
	@OneToOne(cascade =CascadeType.PERSIST)
	@JoinColumn(name = "coach_id")
	CoachBean coach;
//	
//	
	@OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
	private Set<OrderBean> order = new HashSet<>();
	
	

	

	public MemberBean(Integer memberId, String memberPassword, String memberName, String email, String phone,
			Date birthday, String role, Blob memberPicture, Timestamp registerTime, CoachBean coach,
			Set<OrderBean> order) {
		super();
		this.memberId = memberId;
		this.memberPassword = memberPassword;
		this.memberName = memberName;
		this.email = email;
		this.phone = phone;
		this.birthday = birthday;
		this.role = role;
		this.memberPicture = memberPicture;
		this.registerTime = registerTime;
		this.coach = coach;
//		this.order = order;
	}

	
	
	public MemberBean() {
		super();
	}



	public Integer getMemberId() {
		return memberId;
	}

	public Set<OrderBean> getOrder() {
		return order;
	}

	public void setOrder(Set<OrderBean> order) {
		this.order = order;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Blob getMemberPicture() {
		return memberPicture;
	}

	public void setMemberPicture(Blob memberPicture) {
		this.memberPicture = memberPicture;
	}

	public Timestamp getRegisterTime() {
		
		return registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	public CoachBean getCoach() {
		return coach;
	}

	public void setCoach(CoachBean coach) {
		this.coach = coach;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
	
	
}
