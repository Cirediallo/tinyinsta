/**
 * 
 */
package fr.univnantes.atal;

/**
 * @author DIALLO Ciré
 *
 */

import java.util.*;
import fr.univnantes.atal.model.Post;
import fr.univnantes.atal.utilitaires.*;
import fr.univnantes.atal.model.Profile;
import fr.univnantes.atal.utilitaires.*;
import fr.univnantes.atal.source.Data;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Entity;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.api.server.spi.config.*;

import com.google.gson.JsonObject;

//import com.google.cloud.datastore.Entity;

@Api(name = Configuration.API_NAME, //"tinyinsta",
	/*
	version = "v1",
	audiences = "889389031579-nlgjm7p6g9i09g7jcnmp5vlsr32fs8th.apps.googleusercontent.com",
	clientIds = "889389031579-nlgjm7p6g9i09g7jcnmp5vlsr32fs8th.apps.googleusercontent.com",
	*/
	version = Configuration.API_VERSION,
	audiences = Configuration.API_AUDIENCES,
	clientIds = Configuration.API_CLIENTID,
	namespace =
	@ApiNamespace(
			/*
		   ownerDomain = "helloworld.example.com",
		   ownerName = "helloworld.example.com",
		   packagePath = "")
		   */
			ownerDomain = Configuration.OWNERDOMAIN,
			ownerName = Configuration.OWNERNAME,
			packagePath = Configuration.PACKAGE_PATH)
)

public class ApiEndPoint {
	/**
	 * Get user profile by key
	 * @param key
	 * @return
	 * @throws UnauthorizedException
	 */
	/*
	@ApiMethod(name = "retrieveProfileByKey", httpMethod = HttpMethod.GET)
	public Entity retrieveProfileByKey(@Named("userKey") String key) {
		return Profile.findByKey(key);
	}
	
	/**
	 * Get user profile by id
	 * @param userId
	 * @return
	 * @throws UnauthorizedException
	 */
	@ApiMethod(name = "retrieveProfileById", httpMethod = HttpMethod.GET)
	public Entity retrieveProfileById(@Named("userId") String userId) {
		return Profile.findById(userId);
	}
	
	
	/**
	 * Create a profile for a user
	 * @param user
	 * @param profile
	 * @return
	 * @throws UnauthorizedException
	 */
	@ApiMethod(name = "createprofile", httpMethod = HttpMethod.POST)
	public Entity createprofile(User user, Profile profile) throws UnauthorizedException {
		if (user == null) {
			throw new UnauthorizedException("Invalid credentials");
		}
		
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

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		//Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		Transaction txn = datastore.beginTransaction();
		
		//Datastore key for the new entity
		//Key taskKey = datastore.newKeyFactory().setKind("Task").newKey(entity);
		//Prepare the entity
		//Entity task = Entity.newBuilder(taskKey).built();

		datastore.put(entity);

		txn.commit();

		return entity;
	}
	
	
	
	
	
	@ApiMethod(name = "posts", httpMethod = HttpMethod.GET)
	public CollectionResponse<Entity> posts(@Named("googleId") String googleId,@Nullable @Named("userKey") String userKey, @Nullable @Named("next") String cursorString) {
		if(userKey == null){
			Entity profile = Profile.findById(googleId);
			userKey = profile.getKey().getName();
		}

		Query q = new Query(Post.class.getCanonicalName());
				//.setFilter(new Query.FilterPredicate("author", Query.FilterOperator.EQUAL, userKey));

		
		q.addSort("created_at", Query.SortDirection.DESCENDING);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);

		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(1);

		if (cursorString != null) {
			fetchOptions.startCursor(Cursor.fromWebSafeString(cursorString));
		}

		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		cursorString = results.getCursor().toWebSafeString();

		return CollectionResponse.<Entity>builder().setItems(results).setNextPageToken(cursorString).build();

	}
	
    @ApiMethod(name = "follow", httpMethod = HttpMethod.GET)
    public Entity follow(User user, @Named("followerKey") String followerKey) throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Invalid credentials");
        }
        return  Profile.follow(user.getId(), followerKey);

    }
    @ApiMethod(name = "unfollow", httpMethod = HttpMethod.GET)
    public Entity unfollow(User user, @Named("followerKey") String followerKey) throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Invalid credentials");
        }
        return  Profile.unfollow(user.getId(), followerKey);

    }
	
	/**
	 * Remove post by key
	 * @param postKey
	 * @return
	 * @throws UnauthorizedException
	 */
	@ApiMethod(name = "postdelete", httpMethod = HttpMethod.GET)
	public Object postdelete(@Named("postKey") String postKey) {
		Entity post = Post.findByKey(postKey);
		JsonObject jsonResponse = new JsonObject();
		if(post == null){
			jsonResponse.addProperty("status", "failed");
			jsonResponse.addProperty("message", "User not found");
		}else{
			Post.delete(post.getKey());
			jsonResponse.addProperty("status", "success");
			jsonResponse.addProperty("message", "Post deleted");
		}
		return jsonResponse.toString();
	}
}
