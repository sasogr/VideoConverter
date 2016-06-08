package videoconverter.users;

/*
 * AUTHOR: Danail Tavcioski
 * v1.0b
 */

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import junit.framework.Assert;
import videoconverter.businesslogic.UserFolderStructure;

public class CreatingAndCheckingUserFolders {

	@Test
	public void CreateUserFoldersAndCheckExistance() {
		UserFolderStructure u = new UserFolderStructure("John_doe");

		u.CreateUserFolders();

		Assert.assertTrue(u.CheckUserFolders());

	}

	@Test
	public void CreateUserFoldersAndDeleteCheck() throws IOException {
		UserFolderStructure u = new UserFolderStructure("John_doe");

		u.CreateUserFolders();
		
		Assert.assertTrue(u.CheckUserFolders());
		
		File dir = new File("/home/videoconverter/John_doe");
		FileUtils.deleteDirectory(dir);

		Assert.assertFalse(u.CheckUserFolders());
	}

}
