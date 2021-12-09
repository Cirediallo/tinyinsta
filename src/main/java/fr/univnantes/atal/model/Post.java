/**
 * 
 */
package fr.univnantes.atal.model;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.*;

import java.util.Date;
import java.util.Optional;

/**
 * @author DIALLO Ciré
 *
 */



public class Post {
	String author; //author key
	String description; //post description
	
	/* post image name */
	public String imageName;
	
	public String imageURL; //imageURL
	Integer likeCounter; //likeCounter
	public Date created_at; //date of post
	
	/**
	 * Get single post by specific field
	 * @param field
	 * @param value
	 * @return 
	 */
	private static Entity find(String field, Object value) {
		Query q = new Query(Post.class.getCanonicalName())
							.setFilter(new Query.FilterPredicate(field, Query.FilterOperator.EQUAL, value));
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery preparedQuery = datastore.prepare(q);
		
		Optional<Entity> result = preparedQuery.asQueryResultList(FetchOptions.Builder.withDefaults()).stream().findFirst();
		
		return result.orElse(null);
	}
	
	/**
	 * Get user profile by id
	 * @param id
	 * @return 
	 */
	public static Entity findById(String id) {
		return find("postId", id);
	}
	
	/**
	 * Get user profile by key
	 * @param key
	 * @return 
	 */
	public static Entity findByKey(String key) {
		Key encodedKey = KeyFactory.createKey(Post.class.getCanonicalName(), key);
		return find("__key__", encodedKey);
	}
	
	/**
	 * Delete single post by key
	 * @param key
	 */
	public static void delete(Key key) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.delete(key);
	}
	
}
