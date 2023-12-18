package com.emsi.entities;

import java.util.Objects;

public class Stadium {
    private Integer id;
    private String name;
    private String city;
    private String address;
    private int constructionYear;
    private double capacity;
    public static final String path = "./src/main/resources/com/emsi/Data/";

	public Stadium ()
	{
	
	}

	public Stadium(Integer id, String name, String city, String address, int constructionYear, double capacity) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.address = address;
		this.constructionYear = constructionYear;
		this.capacity = capacity;
	}
    public Stadium(String name, String city, String address, int constructionYear, double capacity) {
		this.name = name;
		this.city = city;
		this.address = address;
		this.constructionYear = constructionYear;
		this.capacity = capacity;
	}

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getConstructionYear() {
		return constructionYear;
	}

	public void setConstructionYear(int constructionYear) {
		this.constructionYear = constructionYear;
	}

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() { 
		return id + " | " + name + " | " + city + " | " + address + " | " + constructionYear + " | " + capacity;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stadium other = (Stadium) obj;
		return Objects.equals(address, other.address)
				&& Double.doubleToLongBits(capacity) == Double.doubleToLongBits(other.capacity)
				&& Objects.equals(city, other.city) && constructionYear == other.constructionYear
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	
	
}