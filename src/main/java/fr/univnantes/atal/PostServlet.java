package fr.univnantes.atal;

/*
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.jdo.Transaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.org.joda.time.LocalDateTime;
import com.google.gson.JsonObject;
*/
/**
 * Servlet implementation class PostServlet
 */
/*
@SuppressWarnings("serial")
@WebServlet(name = "ImagesServlet", urlPatterns = { "/createpost" })
*/
public class PostServlet /*extends HttpServlet */{
	/*
}
	final String BUCKETPATH = "tinyinsta-image-storage";
	
	//Cloud Services
	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(new RetryParams.Builder()
			.initialRetryDelayMillis(10)
			.retryMaxAttempts(10)
			.totalRetryPeriodMillis(15000)
			.build()
					);
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		ServletFileUpload upload = new ServletFileUpload();
		try {
			FileItemIterator iterator = upload.getItemIterator(request);
			
			/* Get img file ***
			FileItemStream imageObjectStream = iterator.next();
			String imageExtension = imageExtension(imageObjectStream.getContentType());
			
			InputStream imageFileStream = imageObjectStream.openStream();
			byte[] imageBytes = IOUtils.toByteArray(imageFileStream);
			
			/* Get description field ***
			InputStream descriptionStream = iterator.next().openStream();
			byte[] descriptionBytes = IOUtils.toByteArray(descriptionStream);
			String description = new String(descriptionBytes);
			
			/* Get userid field ***
			InputStream userIdStream = iterator.next().openStream();
			byte[] userIdBytes = IOUtils.toByteArray(userIdStream);
			Streing userId = new String(userIdBytes);
			
			Entity author = Profile.findById(userId);
			if(author == null) {userNotFound(response);}
			else {
				//Put img to the Cloud
				String filename = Util.normalize(LocalDateTime.now().toString()) + "." + imageExtension;
				saveImageToBucket(imageBytes, filename, imageObjectStream.getContentType());
				
				String url = getImageUrl(filename);
				
				String finalFilename = BUCKETPATH + "/" + filename;
				Entity post = savePost(finalFilename, description, url, author.getKey());
				successResponse(response, url, post);
			}
		}catch(FileUploadException | IOException e) {
			
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		ServletFileUploat upload = new ServletFileUploat();
		try {
			FileItemIterator iterator = upload.getItemIterator(request);
			
			/* Get img file ***
			FileItemStream imageObjectStream = iterator.next();
			String imageExtension = "";
			String contentType = imageObjectStream.getContentType();
			if(contentType != null) {imageExtension = imageExtension(contentType);}
			InputStream imageFileStream = imageObjectStream.openStream();
			byte[] imageBytes = IOUtils.toByteArray(imageFileStream);
			boolean isImageUpdated = true;
			String imageValue = new String(imageBytes);
			
			if(imageValue.equals("nochange")) {isImageUpdated = false;}
			
			/* Get description field ***
			InputStream descriptionStream = iterator.next().openStream();
			byte[] descriptionBytes = IOUtils.toByteArray(descriptionStream);
			String description = new String(descriptionBytes);
			
			/* Get post key ***
			InputStream postKeyStream = iterator.next().openStream();
			byte[] userIdBytes = IOUtils.toByteArray(postKeyStream);
			String postKey = new String(userIdBytes);
			
			Entity post = Post.findByKey(postKey);
			if(post == null) {userNotFound(response);}
			else {
				/* write image to cloud storage ***
				String url = (String)post.getProperties().get("imageUrl");
				if(isImageUpdated) {
					String filename = Util.normalize(LocalDateTime.now().toString()) + "." + imageExtension;
					saveImageToBucket(imageBytes, filename, imageObjectStream.getContentType());
					
					url = getImageUrl(filename);
					
					String finalFilename = BUCKETPATH + "/" + filename;
					post.setProperty("imageName", filename);
					post.setProperty("imageUrl", url);
				}
				
				post.setProperty("description", description);
				
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Transaction transaction = datastore.beginTransaction();
				
				datastore.put(post);
				
				transaction.commit();
				
				successResponse(response, url, post);
			}
		}catch(FileUploadException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * write success message to the response stream
	 * @param response
	 * @param finalFilename
	 * @param post
	 * @throws IOException
	 ****
	private void successResponse(HttpServletResponse response, String finalFilename, Entity post) throws IOException {
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("status", "sucess");
		jsonResponse.addProperty("message", "The post have been validated");
		jsonResponse.addProperty("imageUrl", finalFilename);
		ObjectMapper objectMapper = new ObjectMapper();
		
		jsonResponse.addProperty("data", objectMapper.writeValueAsString(post));
		response.getWriter().write(jsonResponse.toString());
	}
	
	/**
	 * save image to the cloud datastorage bucket
	 * @param imageBytes
	 * @param filename
	 * @param contentType
	 * @throws IOException
	 ***
	private void saveImageToBucket(byte[] imageBytes, String filename, String contentType) throws IOException{
		gcsService.createOrReplace(
				new GcsFilename(BUCKETPATH, filename),
				new GcsFileOptions.Builder().mimeType(contenType).build(),
				ByteBuffer.wrap(imageBytes);
		);
	}
	
	/**
	 * write user not found message to the response stream
	 * @param response
	 * @throws IOException
	 ***
	private void userNotFound(HttpServletResponse response) throws IOException{
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("status", "failed");
		jsonResponse.addProperty("message", "The User is not found");
		
		response.getWriter().println(jsonResponse.toString());
	}
	
	/**
	 * Get image file extension
	 * @param filetype image filename
	 * return
	 ***
	private String imageExtention(String filetype) {
		switch(filetype) {
		case "image/jpeg": return "jpg";
		default: return filetype.split("/")[1];
		}
	}
	
	/**
	 * Save post to the datastore
	 * @param imageName image filename
	 * @param description image description
	 * @param authorKey author key
	 * @return
	 ***
	
	private Entity savePost(String imageName, String description, String imageUrl, Key authorKey) {
		Entity entity = new Entity(Post.class.getCanonicalName(), Long.MAX_VALUE - (new Date()).getTime() + authorKey.getName());
		
		entity.setProperty("author", authorKey.getName());
		entity.setProperty("description", description);
		entity.setProperty("imageName", imageName);
		entity.setProperty("imageUrl", imageUrl);
		entity.setProperty("likeCounter", 0);
		entity.setProperty("created_at", new Date());
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction transaction = datastore.beginTransaction();
		
		datastore.put(entity);
		
		transaction.commit();
		
		return entity;
	}
*/
}

