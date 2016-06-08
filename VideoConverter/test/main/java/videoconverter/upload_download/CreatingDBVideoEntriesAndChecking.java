package videoconverter.upload_download;

import org.junit.Test;

import junit.framework.Assert;
import videoconverter.UserServiceImpl.UserServiceImpl;
import videoconverter.businesslogic.UpdateUserVideo;
import videoconverter.users.RemoveUserFromDbHelper;

public class CreatingDBVideoEntriesAndChecking {
	
	@Test
	public void checkIfVideoNameUpdated(){
		
		RemoveUserFromDbHelper.removeUser("John_doe");
		UserServiceImpl.createUser("John_doe", "placeholder", "placeholder", "placeholder", "placeholder");
		
		UpdateUserVideo u = new UpdateUserVideo();
		u.SetUsername("John_doe");
		u.SetVideoName("Video.mp4");
		u.UpdateVideoEntry();
		
		Assert.assertTrue("Video.mp4".equals(u.GetVideoNameUploaded()));
	}
	
	@Test
	public void checkIfLinuxVideoNameUpdated(){
		RemoveUserFromDbHelper.removeUser("John_doe");
		UserServiceImpl.createUser("John_doe", "placeholder", "placeholder", "placeholder", "placeholder");
	
		UpdateUserVideo u = new UpdateUserVideo();
		u.SetUsername("John_doe");
		u.SetVideoName("Video a.mp4");
		u.UpdateVideoEntry();
		
		Assert.assertTrue("Video\\ a.mp4".equals(u.GetLinuxVideoNameUploaded()));
		Assert.assertFalse("Video a.mp4".equals(u.GetLinuxVideoNameUploaded()));
	
	}
	
	@Test
	public void checkIfDownloadVideoNameExistsFromStart(){
		RemoveUserFromDbHelper.removeUser("John_doe");
		UserServiceImpl.createUser("John_doe", "placeholder", "placeholder", "placeholder", "placeholder");

		UpdateUserVideo u = new UpdateUserVideo();
		u.SetUsername("John_doe");
		u.SetVideoName("Video.mp4");
		u.UpdateVideoEntry();
		
		Assert.assertTrue("No format!".equals(u.GetDownloadVideoFormat()));
	}
	
	@Test
	public void checkIfDownloadVideoNameExistsWhenAdded(){
		RemoveUserFromDbHelper.removeUser("John_doe");
		UserServiceImpl.createUser("John_doe", "placeholder", "placeholder", "placeholder", "placeholder");

		UpdateUserVideo u = new UpdateUserVideo();
		u.SetUsername("John_doe");
		u.SetVideoName("Video.mp4");
		u.SetVideoDownloadFormat("mkv");
		
		System.out.println(u.GetDownloadVideoFormat());
		
		Assert.assertFalse("mkv".equals(u.GetDownloadVideoFormat()));
		
		u.UpdateVideoDownloadFormat();
		
		Assert.assertTrue("mkv".equals(u.GetDownloadVideoFormat()));
	}
	
	@Test
	public void checkIfVideoDeletedCorrectly(){
		RemoveUserFromDbHelper.removeUser("John_doe");
		UserServiceImpl.createUser("John_doe", "placeholder", "placeholder", "placeholder", "placeholder");

		UpdateUserVideo u = new UpdateUserVideo();
		u.SetUsername("John_doe");
		u.SetVideoName("Video.mp4");
		u.UpdateVideoEntry();
		
		Assert.assertFalse("No video uploaded!".equals(u.GetVideoNameUploaded()));
		
		u.CheckAndDeleteExistingVideo();
		
		Assert.assertTrue("No video uploaded!".equals(u.GetVideoNameUploaded()));
	}
	
	@Test
	public void checkIfDownloadVideoDeletedCorrectly(){
		RemoveUserFromDbHelper.removeUser("John_doe");
		UserServiceImpl.createUser("John_doe", "placeholder", "placeholder", "placeholder", "placeholder");

		UpdateUserVideo u = new UpdateUserVideo();
		u.SetUsername("John_doe");
		u.SetVideoName("Video.mp4");
		u.SetVideoDownloadFormat("mkv");

		u.UpdateVideoEntry();
		u.UpdateVideoDownloadFormat();
		
		Assert.assertTrue("mkv".equals(u.GetDownloadVideoFormat()));
	
		u.CheckAndDeleteExistingDownloadVideo();
		
		Assert.assertFalse("mkv".equals(u.GetDownloadVideoFormat()));
	
	}
	
	
	@Test
	public void checkIfVideoUploadedCorrectly(){
		RemoveUserFromDbHelper.removeUser("John_doe");
		UserServiceImpl.createUser("John_doe", "placeholder", "placeholder", "placeholder", "placeholder");

		UpdateUserVideo u = new UpdateUserVideo();
		u.SetUsername("John_doe");
		u.SetVideoName("Video.mp4");
		
		u.UpdateVideoEntry();
		
		
		Assert.assertTrue(u.CheckVideoUploaded());
	
	}
	
	

	
	
}
