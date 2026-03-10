package com.mycontactsapp.contacts;

import java.util.List;

public class ContactFactory {

	public static Contact createContact(
			String type,
			String name,
			String orgName,
			List<String> phones,
			List<String> emails
			){

		if(type.equalsIgnoreCase("organization"))
			return new OrganizationContact(name, orgName, phones, emails);

		return new PersonContact(name, phones, emails);
	}
}