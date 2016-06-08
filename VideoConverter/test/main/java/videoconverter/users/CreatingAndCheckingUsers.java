package videoconverter.users;

/*
 * AUTHOR: Danail Tavcioski
 * v1.0b
 */

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import videoconverter.UserServiceImpl.UserServiceImpl;
import videoconverter.model.User;

public class CreatingAndCheckingUsers {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void CreateUserAndTestIfExists() throws ClassNotFoundException {

		String fName = "John";
		String lName = "Doe";
		String uName = "John_doe";
		String uPass = "secret";
		String uEmail = "john.doe@finki.ukim.mk";

		User u = new User(fName, lName, uName, uPass, uEmail);

		// clearing given username first
		RemoveUserFromDbHelper.removeUser(uName);

		// should cause no problem
		Assert.assertTrue(UserServiceImpl.createUser(u.getUsername(), u.getFirstName(), u.getLastName(),
				u.getPassword(), u.getEmail()));

		// creating the same info except the primary key which is username
		uName = "jon";
		// clearing given username first
		RemoveUserFromDbHelper.removeUser(uName);
		
		
		u = new User(fName, lName, uName, uPass, uEmail);
		

		// no problem should occurr
		Assert.assertTrue(UserServiceImpl.createUser(u.getUsername(), u.getFirstName(), u.getLastName(),
				u.getPassword(), u.getEmail()));

		
		Assert.assertEquals(0, UserServiceImpl.check(uName, uPass));

		// u = new User(fName, lName, uName, uPass, uEmail);
		//
		// //cannot create duplicate usernames //expected to fail
		// Assert.assertFalse(uSvcImpl.createUser(u.getUsername(),
		// u.getFirstName(), u.getLastName(), u.getPassword(), u.getEmail()));

	}
}
