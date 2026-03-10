package com.mycontactsapp.contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactBuilder {

	private String type = "person";
	private String name;
	private String organizationName;
	private List<String> phones = new ArrayList<>();
	private List<String> emails = new ArrayList<>();

	public ContactBuilder setType(String type) {
		this.type = type;
		return this;
	}

	public ContactBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public ContactBuilder setOrganizationName(String org) {
		this.organizationName = org;
		return this;
	}

	public ContactBuilder addPhone(String phone) {
		phones.add(phone);
		return this;
	}

	public ContactBuilder addEmail(String email) {
		emails.add(email);
		return this;
	}

	public Contact build() {
		return ContactFactory.createContact(type, name, organizationName, phones, emails);
	}
}