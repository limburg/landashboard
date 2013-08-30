package nl.topicus.lan.dashboard.entities.person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import nl.topicus.lan.dashboard.entities.BaseLanEntity;

@Entity
public class Profile extends BaseLanEntity
{
	private static final long serialVersionUID = 1L;

	public enum Gender
	{
		Male,
		Female,
		Undisclosed
	}

	@OneToOne
	@PrimaryKeyJoinColumn
	private Account account;

	/**
	 * Game Info
	 */
	@Column(nullable = false)
	private String nickname;

	/**
	 * Personal info
	 */
	@Column(nullable = false)
	private String name;

	@Column(nullable = true)
	private String surname;

	@Column(nullable = false)
	private String lastname;

	@Column(nullable = false)
	private int tableNumber;

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSurname()
	{
		return surname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public int getTableNumber()
	{
		return tableNumber;
	}

	public void setTableNumber(int tableNumber)
	{
		this.tableNumber = tableNumber;
	}

}
