/**
 * 
 */
package fr.univnantes.atal;

/*import javax.jdo.Transaction;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.gson.JsonObject;
import com.google.storage.onestore.v3.OnestoreEntity.User;
import com.sun.tools.javac.jvm.Profile;
*/
/**
 * @author DIALLO Ciré
 *
 */
/*
public class PostAndProfileEndPoint {
	
	/*
	 * Get user profile by key
	 * @param key
	 * @return
	 * throws UnauthorizedException
	 ****
	@ApiMethod(name = "retrieveProfilByKey", httpMethod = HttpMethod.GET)
	public Entity retrieveProfilByKey(@Named("userKey") String key) { return Profile.findByKey(key);}
	
	/*
	 * Get user profile by Id
	 * @param userId
	 * @return
	 * throws UnauthorizedException
	 ****
	@ApiMethod(name="retrieveProfilById", httpMethod=HttpMethod.GET)
	public Entity retrieveProfilById(@Named("userId") String userId) {
		return Profile.findById(userId);
	}
	
	/*
	 * Create a profil for a user
	 * @param user
	 * @return
	 * throws UnauthorizedException
	 ****
	
	@ApiMethod(name = "createprofile", httpMethod = HttpMethod.POST)
	public Entity createprofile(User user, Profile profile) throws UnauthorizedException{
		if (user == null) { throw new UnauthorizedException("Invalid credentials");}
		
		Entity entity = new Entity(Profile.class.getCanonicalName(), Long.MAX_VALUE - (new Date()).getTime() + Util.normalize(user.getEmail()));
		entity.setProperty("googleId", user.getId());
		entity.setProperty("pseudo", profile.pseudo);
		entity.setProperty("givenName", profile.givenName);
		entity.setProperty("familyName", profile.familyName);
		entity.setProperty("imageUrl", profile.imageUrl);
		entity.setProperty("email", user.getEmail());
		entity.setProperty("subscriberCounter", 0);
		entity.setProperty("subscribers", new ArrayList<>());
		entity.setProperty("created_at", new Date());
		
		DatastoreService  datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction transaction = datastore.beginTransaction();
		
		datastore.put(entity);
		
		transaction.commit();
		
		return entity;
	}
	
	@ApiMethod(name = "posts", httpMethod = HttpMethod.GET)
	public CollectionResponse<Entity> posts(@Named("googleId") String googleId, @Nullable @Named("userKey") String userKey, @Nullable @Named("next") String cursorString){
		if(userKey == null) {
			Entity profile = Profile.findById(googleId);
			userKey = profile.getKey().getName();
		}
		
		Query q = new Query(Post.class.getCanonicalName());
		
		q.addSort("created_at", Query.SortDirection.DESCENDING);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery preparedQ = datastore.prepare(q);
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(Config.FETCH_POST_LIMIT);
		
		if(cursorString != null) { fetchOptions.startCursor(Cursor.fromWebSafeString(cursorString));}
		
		QueryResultList<Entity> results = preparedQ.asQueryResultList(fetchOptions);
		cursorString = results.getCursor().toWebSafeString();
		
		return CollectionResponse.<Entity>builder().setItems(results).setNextPageToken(cursorString).build();
	}
	
	/*
	 * Remove post by key
	 * @param postKey
	 * @return
	 * throws UnauthorizedException
	 ****
	
	@ApiMethod(name = "postdelete", httpMethod = HttpMethod.GET)
	public Object postdelete(@Named("postKey") String postKey) {
		Entity post = Post.findByKey(postKey);
		JsonObject jsonResponse = new JsonObject();
		if(post == null) {
			jsonResponse.addProperty("status", "failed");
			jsonResponse.addProperty("message", "The Useris not found");	
		}else {
			Post.delete(post.getKey());
			jsonResponse.addProperty("status", "success");
			jsonResponse.addProperty("message", "The post have been deleted");
		}
		
		return jsonResponse.toString();
	}
}
*/
