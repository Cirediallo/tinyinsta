/**
 * 
 */
package fr.univnantes.atal;

/*
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.*;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.QueryResultList;
import com.sun.tools.javac.jvm.Profile;
*/
/**
 * @author DIALLO Ciré
 *
 */
/*
public class ExploreEndPoint {
	@ApiMethod(name = "users", httpMethod = HttpMethod.GET);
	public CollectionResponse<Entity> users(User user, @Nullable @Named("keyword") String keyword,
											@Nullable @Named("next") String cursorString
			) throws UnauthorizedException {
		
		if(user == null) {throw new UnauthorizedException("Invalid credentials");}
		Query q = new Query(Profile.class.getCanonicalName()).setFilter(new Query.FilterPredicate("givenName", Query.FilterOperator.GREATER_THAN_OR_EQUAL, keyword))
															.setFilter(new Query.FilterPredicate("givenName", Query.FilterOperator.LESS_THAN, keyword+"\ufffd"))
															.setFilter(new Query.FilterPredicate("googleId", Query.FilterOperator.NOT_EQUAL, user.getId()));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery preparedQ = datastore.prepare(q);
		
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(Config.FETCH_PROFILE_LIMIT);
		
		QueryResultList<Entity> results = preparedQ.asQueryResultList(fetchOptions);
		
		if(cursorString != null){
			fetchOptions.startCursor(Cursor.fromWebSafeString(cursorString));
			cursorString = results.getCursor().toWebSafeString();
		}
		
		return CollectionResponse.<Entity>builder().setItems(results).setNextPageToken(cursorString).build();
	}
	
	@ApiMethod(name = "follow", httpMethod = HttpMethod.GET)
	public Entity follow(User user, @Named("followKey") String followerKey) throws UnauthorizedException {
		if(user == null) { throw new UnauthorizedException("Invalid credentials");}
		return Profile.follow(user.getId(), followerKey);
	}
	
	@ApiMethod(name = "unfollow", httpMethod = HttpMethod.Get)
	public Entity unfollow(User user, @Named("followerKey") String followerKey) throws UnauthorizedException {
		if(user == null) { throw new UnauthorizedException("Invalid credentials");}
		
		return Profile.unfollow(user.getId(), followerKey);
	}
	
	
}
*/
