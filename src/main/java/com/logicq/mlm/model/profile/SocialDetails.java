package com.logicq.mlm.model.profile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "SOCIAL_DETAILS")
public class SocialDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 868552591757208866L;

	@Id
	@Column(name = "SOCIAL_ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long socialid;

	@Column(name = "FB_LINK")
	private String fblink;

	@Column(name = "YT_LINK")
	private String youtubelink;

	@Column(name = "TWITER_LINK")
	private String twiter;

	@Column(name = "LINKEDIN_LINK")
	private String linkedinlink;
	
	@Column(name = "GOOGLE_PLUS_LINK")
	private String googlePlusLink;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "PROFILE_ID")
	private UserProfile userprofile;

	public Long getSocialid() {
		return socialid;
	}

	public void setSocialid(Long socialid) {
		this.socialid = socialid;
	}

	public String getFblink() {
		return fblink;
	}

	public void setFblink(String fblink) {
		this.fblink = fblink;
	}

	public String getYoutubelink() {
		return youtubelink;
	}

	public void setYoutubelink(String youtubelink) {
		this.youtubelink = youtubelink;
	}

	public String getTwiter() {
		return twiter;
	}

	public void setTwiter(String twiter) {
		this.twiter = twiter;
	}

	public String getLinkedinlink() {
		return linkedinlink;
	}

	public void setLinkedinlink(String linkedinlink) {
		this.linkedinlink = linkedinlink;
	}

	public String getGooglePlusLink() {
		return googlePlusLink;
	}

	public void setGooglePlusLink(String googlePlusLink) {
		this.googlePlusLink = googlePlusLink;
	}

	public UserProfile getUserprofile() {
		return userprofile;
	}

	public void setUserprofile(UserProfile userprofile) {
		this.userprofile = userprofile;
	}

	@Override
	public String toString() {
		return "SocialDetails [socialid=" + socialid + ", fblink=" + fblink + ", youtubelink=" + youtubelink
				+ ", twiter=" + twiter + ", linkedinlink=" + linkedinlink + ", googlePlusLink=" + googlePlusLink
				+ ", userprofile=" + userprofile + "]";
	}

}
