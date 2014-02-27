package com.sccl.framework.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;
import com.sccl.framework.entity.TableEntity;

@XmlRootElement
@Entity
@Table(name = "MS_PERSON", schema = "ITSM")
@NamedQueries({
    @NamedQuery(name="MsPerson.findByOrgId",query="SELECT p FROM MsPerson p WHERE p.organization.id=:orgId or p.organization.parentPath like :orgIdLike order by p.id, p.organization.id"),
    @NamedQuery(name="MsPerson.findByOrgIdCount",query="SELECT count(p) FROM MsPerson p WHERE p.organization.id=:orgId or p.organization.parentPath like :orgIdLike order by p.id, p.organization.id")
    })
public class MsPerson extends TableEntity implements Serializable {

	private static final long serialVersionUID = -629044386078040898L;

	@Expose private Integer id;
	@Expose private String personName;
	@Expose private String personCode;
	@Expose private String namePinyin;
	@Expose private String workCard;
	@Expose private String gender;
	@Expose private Date birthDay;
	@Expose private String originalPlace;
	@Expose private Integer cardType;
	@Expose private String cardNumber;
	@Expose private String nation;
	@Expose private String politicalVisage;
	@Expose private Date politicalDate;
	@Expose private String photo;
	@Expose private String bloodType;
	@Expose private String healthStatus;
	@Expose private String maritalStatus;
	@Expose private String homeAddress;
	@Expose private String postCode;
	@Expose private String householdPlace;
	@Expose private String mobileNumber;
	@Expose private String phoneNumber;
	@Expose private String qqNumber;
	@Expose private String eMail;
	@Expose private String education;
	@Expose private String degree;
	@Expose private String graduationSchool;
	@Expose private Date graduationDate;
	@Expose private Date workDate;
	@Expose private Date reachDate;
	@Expose private String employSource;
	@Expose private Date hiresureDate;
	@Expose private Date leaveDate;
	@Expose private Date insideLeaDate;
	@Expose private Date retireDate;
	@Expose private Date deadDate;
	@Expose private Integer personType;
	@Expose private Integer personStatus;
	@Expose private Integer wasSoldier;
	@Expose private Date createtime;
	@Expose private String remark;
	@Expose private Integer dr;
	@Expose private Organization organization;
	private MsPost msPost;
	private Set<MsUser> msUsers = new HashSet<MsUser>(0);

	public MsPerson() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_MS_PERSON")
	@SequenceGenerator(name = "GEN_MS_PERSON", sequenceName = "SEQ_MS_PERSON", allocationSize = 1)
	@Column(name = "PERSON_ID", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PERSON_NAME", length = 50, nullable = false)
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Column(name = "PERSON_CODE", length = 20)
	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	@Column(name = "NAME_PINYIN", length = 50, nullable = false)
	public String getNamePinyin() {
		return namePinyin;
	}

	public void setNamePinyin(String namePinyin) {
		this.namePinyin = namePinyin;
	}

	@Column(name = "WORK_CARD", length = 30, nullable = false)
	public String getWorkCard() {
		return workCard;
	}

	public void setWorkCard(String workCard) {
		this.workCard = workCard;
	}

	@Column(name = "GENDER", length = 10, nullable = false)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "BIRTH_DAY", nullable = false)
	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	@Column(name = "ORIGINAL_PLACE", length = 200, nullable = false)
	public String getOriginalPlace() {
		return originalPlace;
	}

	public void setOriginalPlace(String originalPlace) {
		this.originalPlace = originalPlace;
	}

	@Column(name = "CARD_TYPE", nullable = false)
	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	@Column(name = "CARD_NUMBER", length = 30, nullable = false)
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Column(name = "NATION", length = 10, nullable = false)
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "POLITICAL_VISAGE", length = 10)
	public String getPoliticalVisage() {
		return politicalVisage;
	}

	public void setPoliticalVisage(String politicalVisage) {
		this.politicalVisage = politicalVisage;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "POLITICAL_DATE")
	public Date getPoliticalDate() {
		return politicalDate;
	}

	public void setPoliticalDate(Date politicalDate) {
		this.politicalDate = politicalDate;
	}

	@Column(name = "PHOTO", length = 100)
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Column(name = "BLOOD_TYPE", length = 10)
	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	@Column(name = "HEALTH_STATUS", length = 50)
	public String getHealthStatus() {
		return healthStatus;
	}

	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
	}

	@Column(name = "MARITAL_STATUS", length = 50)
	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	@Column(name = "HOME_ADDRESS", length = 200, nullable = false)
	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	@Column(name = "POST_CODE", length = 10, nullable = false)
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Column(name = "HOUSEHOLD_PLACE", length = 200, nullable = false)
	public String getHouseholdPlace() {
		return householdPlace;
	}

	public void setHouseholdPlace(String householdPlace) {
		this.householdPlace = householdPlace;
	}

	@Column(name = "MOBILE_NUMBER", length = 20)
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Column(name = "PHONE_NUMBER", length = 20)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "QQ_NUMBER", length = 20)
	public String getQqNumber() {
		return qqNumber;
	}

	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}

	@Column(name = "E_MAIL", length = 100)
	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	@Column(name = "EDUCATION", length = 20)
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name = "DEGREE", length = 20)
	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Column(name = "GRADUATION_SCHOOL", length = 100)
	public String getGraduationSchool() {
		return graduationSchool;
	}

	public void setGraduationSchool(String graduationSchool) {
		this.graduationSchool = graduationSchool;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "GRADUATION_DATE")
	public Date getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "WORK_DATE")
	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "REACH_DATE")
	public Date getReachDate() {
		return reachDate;
	}

	public void setReachDate(Date reachDate) {
		this.reachDate = reachDate;
	}

	@Column(name = "EMPLOY_SOURCE", length = 100, nullable = false)
	public String getEmploySource() {
		return employSource;
	}

	public void setEmploySource(String employSource) {
		this.employSource = employSource;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "HIRESURE_DATE")
	public Date getHiresureDate() {
		return hiresureDate;
	}

	public void setHiresureDate(Date hiresureDate) {
		this.hiresureDate = hiresureDate;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "LEAVE_DATE")
	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "INSIDE_LEA_DATE")
	public Date getInsideLeaDate() {
		return insideLeaDate;
	}

	public void setInsideLeaDate(Date insideLeaDate) {
		this.insideLeaDate = insideLeaDate;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "RETIRE_DATE")
	public Date getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(Date retireDate) {
		this.retireDate = retireDate;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "DEAD_DATE")
	public Date getDeadDate() {
		return deadDate;
	}

	public void setDeadDate(Date deadDate) {
		this.deadDate = deadDate;
	}

	@Column(name = "PERSON_TYPE", nullable = false)
	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	@Column(name = "PERSON_STATUS", nullable = false)
	public Integer getPersonStatus() {
		return personStatus;
	}

	public void setPersonStatus(Integer personStatus) {
		this.personStatus = personStatus;
	}

	@Column(name = "WAS_SOLDIER")
	public Integer getWasSoldier() {
		return wasSoldier;
	}

	public void setWasSoldier(Integer wasSoldier) {
		this.wasSoldier = wasSoldier;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "CREATE_TIME")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "DR", nullable = false)
	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	@ManyToOne
	@JoinColumn(name = "ORG_ID", nullable = false)
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@OneToOne
	@JoinColumn(name = "POST_ID")
	public MsPost getMsPost() {
		return msPost;
	}

	public void setMsPost(MsPost msPost) {
		this.msPost = msPost;
	}
	
	@OneToMany(mappedBy = "msPerson", fetch=FetchType.EAGER)
	public Set<MsUser> getMsUsers() {
		return this.msUsers;
	}

	public void setMsUsers(Set<MsUser> msUsers) {
		this.msUsers = msUsers;
	}
}