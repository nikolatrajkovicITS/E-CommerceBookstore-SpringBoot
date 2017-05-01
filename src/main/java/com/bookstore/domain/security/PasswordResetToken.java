package com.bookstore.domain.security;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.bookstore.domain.User;

@Entity
public class PasswordResetToken {

	private static final int EXPIRATION = 60 * 24;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String token;
	
	/**
	 * Binding user in OneToOne relationship 
	 */
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable=false, name="user_id")
	private User user;
	
	private Date expiryDate;
	
	/**
	 * Generate token for user and <b>expire</b> date
	 * @param token
	 * @param user
	 */
	public PasswordResetToken(final String token, final User user) {
		super();                                                        // If we didn't add this, system will be automatically add, but this is more readable.
		
		this.token = token;
		this.user = user;
		this.expiryDate = caluculateExpiryDate(EXPIRATION);
	}
	
	/**
	 * This method is for calculate 
	 * <b>expire</b> date.
	 * @param expiryTimeInMinutes
	 * @return a Date representing the time value.
	 */
	private Date caluculateExpiryDate (final int expiryTimeInMinutes) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}
	
	/**
	 * This is method for <b>update</b> token. 
	 * @param token
	 */
	public void updateToken(final String token) {
		this.token = token;
		this.expiryDate = caluculateExpiryDate(EXPIRATION);
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public static int getExpiration() {
		return EXPIRATION;
	}

	@Override
	public String toString() {
		return "PasswordResetToken [id=" + id + ", token=" + token + ", user=" + user + ", expiryDate=" + expiryDate
				+ "]";
	}
	
	
}







