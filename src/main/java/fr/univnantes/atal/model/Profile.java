/**
 * 
 */
package fr.univnantes.atal.model;

//import java.util.ArrayList;
/*import java.util.Map;
import java.util.Optional;
import java.util.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.cloud.storage.Acl.Entity;
import static com.google.appengine.api.datastore.KeyFactory.createKey;
import com.google.appengine.api.users.*;
import com.google.appengine.api.datastore.*;
*/

/**
 * @author DIALLO Ciré
 *
 */
public class Profile {
	/*
	public String googleId; //Google User id
	public String pseudo; //user pseudo
	public String givenName; //user given name
	public String familyName; //user family name
	public String imageUrl; //user profile image url
	public String email; //user email
	public Date created_at;
	
	/**
	 * Get single user profile by specific field
	 * @param field
	 * @param value
	 * @return 
	 ***
	private static Entity find(String field, Object value) {
		Query q = new Query(Profile.class.getCanonicalName()).setFilter(
																	new Query.FilterPredicate(field, Query.FilterOperator.EQUAL, value)
																);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQueryOptional<T>dQuery = datastore.prepare(q);
		
		Optional<Entity> result = preparedQuery.asQueryResultList(FetchOptions.Builder.withDefaults()).stream().findFirst();
		
		return result.orElse(null);
	}
	
	/**
	 * Get user profile by id
	 * @param id
	 * 
	 * @return 
	 ***
	public static Entity findById(String id) { return find("googleId", id);}
	
	/**
	 * Get user profile by key
	 * @param key
	 * 
	 * @return 
	 ***
	public static Entity findByKey(String key) {
		Key encodedKey = KeyFactory.createKey(Profile.class.getCanonicalName(), key);
		
		return find("__key__", encodedKey);
	}
	
	public static Entity follow(String followed, String follower) {
		Entity profile = Profile.findById(followed);
		Map properties = profile.getProperties();
		ArrayList<String> subscribers = (ArrayList<String>) properties.get("subscribers");
		if(subscribers == null) {subscribers = new ArrayList<>();}
		subscribers.add(follower);
		profile.setProperty("subscribers", subscribers);
		Long counter = (Long) properties.get("subscriberCounter");
		profile.setProperty("subscriberCounter", counter + 1);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction transaction = datastore.beginTransaction();
		
		datastore.put(profile);
		
		transcation.commit();
		
		return profile;
	}
	
	public static Entity unfollow(String followed, String follower) {
		Entity profile = Profile.findById(followed);
		Map properties = profile.getProperties();
		ArrayList<String> subscribers = (ArrayList<String>) properties.get("subscribers");
		if(subscribers == null) {subscribers = new ArrayList<>();}
		if(subscribers.contains(follower)) {
			subscribers.remove(follower);
			profile.setProperty("subscribers", subscribers);
			Long counter = (Long) properties.get("subscriberCounter");
			profile.setProperty("subscriberCounter", counter - 1);
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Transcation transaction = datastore.beginTransaction();
			
			datastore.put(profile);
			
			transaction.commit();
		}
		return profile;
	}
	*/
}
