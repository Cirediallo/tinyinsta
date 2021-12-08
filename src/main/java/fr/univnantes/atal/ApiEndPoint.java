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

import com.google.api.server.spi.config.*;

@Api(name = "tinyinsta",
	version = "v1",
	audiences = "889389031579-nlgjm7p6g9i09g7jcnmp5vlsr32fs8th.apps.googleusercontent.com",
	clientIds = "889389031579-nlgjm7p6g9i09g7jcnmp5vlsr32fs8th.apps.googleusercontent.com",
	namespace =
	@ApiNamespace(
		   ownerDomain = "helloworld.example.com",
		   ownerName = "helloworld.example.com",
		   packagePath = "")
)

public class ApiEndPoint {
	/**
	 * Get user profile by key
	 * @param key
	 * @return
	 * @throws UnauthorizedException
	 */
	/*
	@ApiMethod(name = "retrieveProfile", httpMethod = HttpMethod.GET)
	public Entity retrieveProfile(@Named("userId") String userId) {
		Query q = new Query(Profile.class.getCanonicalName()).setFilter(
				new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, userId));

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery preparedQuery = datastore.prepare(q);
		Optional<Entity> result = preparedQuery.asList(FetchOptions.Builder.withDefaults()).stream().findFirst();

		return result.orElse(null);
	 }
	*/
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
		//Entity entity = new Entity(Profile.class.getCanonicalName(), Long.MAX_VALUE-(new Date()).getTime()+":"+user.getEmail());
		Entity entity = new Entity(Profile.class.getCanonicalName(),
				Long.MAX_VALUE-(new Date()).getTime()+ Util.normalize(user.getEmail()));
		
		//entity.setProperty("id", user.getId());
		entity.setProperty("googleId", user.getId());
		entity.setProperty("pseudo", profile.pseudo);
		entity.setProperty("givenName", profile.givenName);
		entity.setProperty("familyName", profile.familyName);
		entity.setProperty("imageUrl", profile.imageUrl);
		entity.setProperty("email", user.getEmail());
		entity.setProperty("subscribers", new ArrayList<>());
		entity.setProperty("created_at", new Date());

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();

		datastore.put(entity);

		txn.commit();

		return entity;
	}
	
	@ApiMethod(name = "posts", httpMethod = HttpMethod.GET)
	public CollectionResponse<Entity> posts(@Named("userKey") String userKey, @Nullable @Named("next") String cursorString) {

		Query q = new Query(Post.class.getCanonicalName())
				.setFilter(new Query.FilterPredicate("author", Query.FilterOperator.EQUAL, userKey));

		
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
}
